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

        static YouToddlerHistorian()
        {
            IConfiguration configuration = new ConfigurationBuilder()
                .SetBasePath(Directory.GetCurrentDirectory())
                .AddJsonFile("appsettings.json")
                .Build();

            Log.Logger = new LoggerConfiguration()
                .ReadFrom.Configuration(configuration)
                .CreateLogger();
        }

        public static YouToddlerLogScroll AggregateLogs(DirectoryInfo logDirectory)
        {
            Log.Information("I'm about to head out");
            Log.Information("Wtf");

            throw new NotImplementedException();
        }

        public static int EraseLogs(DirectoryInfo logDirectory)
        {
            var logFiles = logDirectory.EnumerateFiles("*.log", SearchOption.AllDirectories).ToArray();

            foreach (var logFile in logFiles)
            {
                logFile.Delete();
            }

            return logFiles.Length;
        }
    }
}
