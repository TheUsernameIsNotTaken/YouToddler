using CommandLine;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace YouToddlerCLI.Options
{
    [Verb("history", false, new[] { "h" }, HelpText = "Returns log history.")]
    public class HistoryOptions
    {
        [Option('d', "delete", HelpText = "Erase logs", Default = false, Required = false)]
        public bool DeleteHistory { get; set; }
    }
}
