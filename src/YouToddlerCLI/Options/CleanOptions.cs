using CommandLine;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace YouToddlerCLI.Options
{
    [Verb("clean", HelpText = "Cleans up working directories set in the appsetting.json file.")]
    public class CleanOptions
    {
    }
}
