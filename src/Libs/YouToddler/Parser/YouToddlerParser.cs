using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using YouToddler.Models;
using YouToddler.Runner;

namespace YouToddler.Parser
{
    public class YouToddlerParser : IYouToddlerParser<YouToddlerContent>
    {

        public YouToddlerParser()
        {

        }

        public YouToddlerContent ParseMetadata(string ytDlpOutput)
        {
            throw new NotImplementedException();
        }
    }
}
