using CommandLine;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace YouToddlerCLI.Options
{
    [Verb("describe", false , new[] { "ds", "d" }, HelpText = "Retrieves metadata about the input YouTube content.")]
    public class DescribeOptions
    {
        [Option('t', "target", HelpText = "The URI that points to the input YouTube content.", Required = true)]
        public string? VideoUriLink { get; set; }
    }
}
