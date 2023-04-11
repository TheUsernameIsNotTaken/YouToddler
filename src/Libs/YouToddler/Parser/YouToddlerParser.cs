using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using YouToddler.Models;
using YouToddler.Runner;

namespace YouToddler.Parser
{
    internal class YouToddlerParser : IYouToddlerParser<YouToddlerContent>
    {
        public YouToddlerContent? ParsedContent { get; set; }

        public YouToddlerParser()
        {

        }

        public YouToddlerContent ParseMetadata(string ytDlpOutput)
        {
            throw new NotImplementedException();
        }
    }
}
