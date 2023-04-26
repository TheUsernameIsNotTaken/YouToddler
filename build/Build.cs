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

[GitHubActions(
    "build-all-and-validate-nightly",
    GitHubActionsImage.Ubuntu2204,
    GitHubActionsImage.WindowsLatest,
    InvokedTargets = new[] { nameof(CompileAll), nameof(ValidateCLI), nameof(PublishAll)},
    OnCronSchedule = "* 0 * * *",
    ImportSecrets = new[] {nameof(DOCKER_USERNAME), nameof(DOCKER_PASSWORD)},
    AutoGenerate = true
)]
[GitHubActions(
    "pr-pipeline",
    GitHubActionsImage.UbuntuLatest,
    GitHubActionsImage.WindowsLatest,
    InvokedTargets = new[] { nameof(CompileAll), nameof(ValidateCLI)},
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

    public static int Main () => Execute<Build>(x => x.CompileAll);

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

    Target CompileBackend => _ => _
        .Executes(() =>
        {
            Log.Information("Building .NET Backend..");
            DotNetBuild(_ => _
                .SetProjectFile(Solution)
                .SetConfiguration(Configuration)); 
            Log.Information("Built .NET Backend.");
        });

    Target CompileWebApi => _ => _
        .Executes(() =>
        {
            Log.Information("Here you'll be calling your build scripts for the WebAPI.");
        });

    Target CompileFrontend => _ => _
        .Executes(() =>
        {
            Log.Information("Here you'll be calling your build scripts for the Frontend.");            
        });

    Target CompileAll => _ => _
        .DependsOn(CompileBackend, CompileWebApi, CompileFrontend)
        .Executes(() =>
        {
        });

    Target ValidateCLI => _ => _
        .DependsOn(CompileBackend)
        .Executes(() => {
            DotNetRun(_ => _
            .EnableNoBuild()
            .EnableNoRestore()
            .SetConfiguration(Configuration)
            .SetProjectFile(YouToddlerCliCsprojPath));
        });

    Target ReleaseCli => _ => _
        .Executes(() => 
        {
            DotNetPublish(_ => _
                .SetConfiguration(Configuration)
                .SetRuntime(DetermineRFIdentifier())
                .SetSelfContained(true)
                .SetOutput(Path.Combine(Path.GetDirectoryName(YouToddlerCliCsprojPath), "publish/"))
                .SetProject(YouToddlerCliCsprojPath));
        });

    Target ReleaseWebApi => _ => _
        .Executes(() => 
        {
            PowerShell(@".\mvnw clean package spring-boot:repackage", YouToddlerWebApiPath);
        });

    Target ReleaseFrontend => _ => _
        .Executes(() => 
        {
            Log.Warning("Actual deployment will be handled by Docker");
        });

    Target PublishAll => _ => _
        .DependsOn(ReleaseCli, ReleaseWebApi, ReleaseFrontend)
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
            
            DockerPush(_ => _
                .SetName("youtoddler/backend:latest"));
                
            DockerPush(_ => _
                .SetName("youtoddler/frontend:latest"));
        });

        private string DetermineRFIdentifier()
        {
            if(OperatingSystem.IsLinux() || IsContainerBuild)
                return "linux-x64";
            else
                return "win-x64";
        }
}
