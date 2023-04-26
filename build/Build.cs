using Serilog;
using Nuke.Common;
using Nuke.Common.ProjectModel;
using Nuke.Common.Tools.DotNet;
using Nuke.Common.Tools.Docker;
using static Nuke.Common.Tools.DotNet.DotNetTasks;
using static Nuke.Common.Tools.Docker.DockerTasks;
using static Nuke.Common.Tools.PowerShell.PowerShellTasks;
using Nuke.Common.CI.GitHubActions;
using System;
using System.IO;
using Nuke.Common.Tooling;

[GitHubActions(
    "build-all-and-validate-nightly",
    GitHubActionsImage.Ubuntu2204,
    InvokedTargets = new[] {nameof(PublishAll)},
    OnCronSchedule = "* 0 * * *",
    ImportSecrets = new[] {nameof(DOCKER_USERNAME), nameof(DOCKER_PASSWORD)},
    AutoGenerate = true
)]
[GitHubActions(
    "pr-pipeline",
    GitHubActionsImage.UbuntuLatest,
    InvokedTargets = new[] {nameof(BuildAll)},
    OnPullRequestBranches = new[] { "master"},
    AutoGenerate = true
)]
partial class Build : NukeBuild
{
    /// Support plugins are available for:
    ///   - JetBrains ReSharper        https://nuke.build/resharper
    ///   - JetBrains Rider            https://nuke.build/rider
    ///   - Microsoft VisualStudio     https://nuke.build/visualstudio
    ///   - Microsoft VSCode           https://nuke.build/vscode

    public static int Main () => Execute<Build>(x => x.BuildAll);

    [LocalExecutable("/usr/bin/bash")]
    readonly Tool Bash;

    [Parameter("Configuration to build - Default is 'Debug' (local) or 'Release' (server)")]
    readonly Configuration Configuration = IsLocalBuild ? Configuration.Debug : Configuration.Release;

    [Parameter] [Secret]
    readonly string DOCKER_USERNAME;
    [Parameter] [Secret]
    readonly string DOCKER_PASSWORD;

    [Parameter]
    readonly bool IsContainerBuild = true;

    [Solution]
    readonly Solution Solution;

    Target BuildCli => _ => _
        .Executes(() => 
        {
            DotNetPublish(_ => _
                .SetConfiguration(Configuration)
                .SetRuntime(DetermineRFIdentifier())
                .SetSelfContained(true)
                .SetOutput(Path.Combine(Path.GetDirectoryName(YouToddlerCliCsprojPath), "publish/"))
                .SetProject(YouToddlerCliCsprojPath));
        });

    Target BuildWebApi => _ => _
        .Executes(() => 
        {            
            Log.Warning("Actual build will be handled by Docker");
        });

    Target BuildFrontend => _ => _
        .Executes(() => 
        {
            Log.Warning("Actual build will be handled by Docker");
        });

    Target BuildAll => _ => _
        .DependsOn(BuildCli, BuildWebApi, BuildFrontend)
        .Executes(() =>
        {
            DockerLogin(_ => _
                .SetUsername(DOCKER_USERNAME)
                .SetPassword(DOCKER_PASSWORD));

            DockerBuild(_ => _
                .SetPath(RootDirectory / "src/")
                .SetTag("youtoddler/backend:latest")
                .SetFile(Path.Join(YouToddlerWebApiPath, "Dockerfile"))
                );

            DockerBuild(_ => _
                .SetPath(YouToddlerFrontendPath)
                .SetTag("youtoddler/frontend:latest")
                .SetFile(Path.Join(YouToddlerFrontendPath, "Dockerfile")));

            Log.Information("All Docker images were built successfully.");
        });

    Target PublishAll => _ => _
        .DependsOn(BuildAll)
        .Executes(() => 
        {
            DockerLogin(_ => _
                .SetUsername(DOCKER_USERNAME)
                .SetPassword(DOCKER_PASSWORD));
            
            DockerPush(_ => _
                .SetName("youtoddler/backend:latest"));
                
            DockerPush(_ => _
                .SetName("youtoddler/frontend:latest"));

            Log.Information("All Docker images were published successfully.");
        });

        private string DetermineRFIdentifier()
        {
            if(OperatingSystem.IsLinux() || IsContainerBuild)
                return "linux-x64";
            else
                return "win-x64";
        }
}
