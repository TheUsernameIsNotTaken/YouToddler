using Microsoft.Extensions.Configuration;
using Serilog;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace YouToddler.History
{
    public static class YouToddlerHistorian
    {
        public static string AggregateLogs(DirectoryInfo logDirectory)
        {
            StringBuilder aggregatedLogs = new StringBuilder();
            aggregatedLogs.AppendLine("[");
            var logFiles = logDirectory.EnumerateFiles("*jlog", SearchOption.AllDirectories).Select(f => f.FullName);
            foreach (var logFile in logFiles)
            {
                foreach (var logEntry in File.ReadLines(logFile))
                {
                    aggregatedLogs.Append(logEntry);
                    aggregatedLogs.AppendLine(",");
                }
            }
            aggregatedLogs.AppendLine("]");
            return aggregatedLogs.ToString();
        }

        public static int EraseLogs(DirectoryInfo logDirectory)
        {
            var logFiles = logDirectory.EnumerateFiles("*jlog", SearchOption.AllDirectories).ToArray();
            int logsDeleted = 0;
            foreach (var logFile in logFiles)
            {
                logFile.Delete();
                logsDeleted++;
            }

            return logsDeleted;
        }
    }
}
