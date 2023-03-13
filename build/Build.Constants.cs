using System;
using System.Linq;
using Nuke.Common;
using Nuke.Common.CI;
using Nuke.Common.Execution;
using Nuke.Common.IO;
using Nuke.Common.ProjectModel;
using Nuke.Common.Tooling;
using Nuke.Common.Utilities.Collections;
using static Nuke.Common.EnvironmentInfo;
using static Nuke.Common.IO.FileSystemTasks;
using static Nuke.Common.IO.PathConstruction;

partial class Build
{
    public Build()
    {
        Logging.Level = LogLevel.Normal;
    }

    public static readonly string YouToddlerCliCsprojPath = RootDirectory / "src/YouToddlerCLI/YouToddlerCLI.csproj";
}
