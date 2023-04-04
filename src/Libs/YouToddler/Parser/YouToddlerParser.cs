using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using YouToddler.Models;
using YouToddler.Runner;

namespace YouToddler.Parser
{
    internal class YouToddlerParser : IHttpBasedYouToddlerParser<YouToddlerContent>
    {
        private YouToddlerRunner _youToddlerRunner;
        public YouToddlerContent? ParsedContent { get; set; }

        public YouToddlerParser(YouToddlerRunner youToddlerRunner)
        {
            _youToddlerRunner = youToddlerRunner;
        }

        public YouToddlerContent ParseMetadata(Uri url)
        {
            throw new NotImplementedException();
        }
    }
}
