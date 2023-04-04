using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace YouToddler.Parser
{
    public interface IHttpBasedYouToddlerParser<T> where T : class
    {
        public T ParseMetadata(Uri url);
    }
}
