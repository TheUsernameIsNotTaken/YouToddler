using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace YouToddler.History
{
    public record class YouToddlerLogScroll(DateTime TimeStamp, string Sender, string Message);
}
