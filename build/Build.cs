using System;
using System.Linq;
using Serilog;
using Nuke.Common;
using Nuke.Common.CI;
using Nuke.Common.Execution;
using Nuke.Common.IO;
using Nuke.Common.ProjectModel;
using Nuke.Common.Tooling;
using Nuke.Common.Tools.DotNet;
using static Nuke.Common.Tools.DotNet.DotNetTasks;
using Nuke.Common.Utilities.Collections;
using static Nuke.Common.EnvironmentInfo;
using static Nuke.Common.IO.FileSystemTasks;
using static Nuke.Common.IO.PathConstruction;
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
            Log.Warning("Here you'll be calling your build scripts for the WebAPI.");
        });

    Target CompileFrontend => _ => _
        .Executes(() =>
        {
            Log.Warning("Here you'll be calling your build scripts for the Frontend.");            
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
            .SetProjectFile(YouToddlerCliCsprojPath));
        });

}
