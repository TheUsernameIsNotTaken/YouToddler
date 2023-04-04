using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace YouToddler.History
{
    internal static class Historian
    {
        internal static LogScroll AggregateLogs(DirectoryInfo logDirectory)
        {
            return new LogScroll(DateTime.Now, "", "");
        }

        internal static int EraseLogs(DirectoryInfo logDirectory)
        {
            return -1;
        }
    }
}
