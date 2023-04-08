using Microsoft.Extensions.Configuration;
using NUnit.Framework;
using Serilog;
using Serilog.Formatting.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using YouToddler.History;

namespace YouToddlerLib.Tests.Unit
{
    [TestFixture]
    public class YouToddlerHistorianTests
    {
        private readonly string TestLogFilesDirectory = "TestLogs";

        [OneTimeSetUp] public void SetUp() 
        {
            IConfiguration configuration = new ConfigurationBuilder()
                .SetBasePath(Directory.GetCurrentDirectory())
                .AddJsonFile("appsettings.json")
                .Build();

            Log.Logger = new LoggerConfiguration()
                .WriteTo.File(new JsonFormatter(), $"{TestLogFilesDirectory}/youtoddler.jlog", shared: true, rollOnFileSizeLimit: true, fileSizeLimitBytes: 8)
                .CreateLogger();


            Log.Information("Is there a way out of here?");
            Log.Error("Hey, how long you been here?");
            Log.Error("I wanna go home");
            Log.Error("Hey, hahaha?");
            Log.Fatal("Hahaha");
            Log.Information("Excuse me, could you tell how to get out of here?");
            Log.Fatal("Hahaha");
            Log.Fatal("I want to go home");
            Log.Information("Hahaha");
            Log.Information("Mommy, mommy");
            Log.Warning("Hello {Now}. Read the following {@List}", DateTime.UtcNow, new List<int>() { 1,2,3 });
            Log.CloseAndFlush();
        }

        [OneTimeTearDown] public void TearDown() 
        {
            Directory.Delete(TestLogFilesDirectory, true);
        }

        [Test]
        public void AggregateLogs_CorectlyAggregatesLogs_NoContentLost()
        {
            var logFiles = new DirectoryInfo(TestLogFilesDirectory)
                    .EnumerateFiles("*jlog", SearchOption.AllDirectories).ToArray();
            var logDirectorySize = logFiles.Sum(l => l.Length);
            var aggregatedLogs = YouToddlerHistorian.AggregateLogs(new DirectoryInfo(TestLogFilesDirectory));
            var actualDirectorySize = aggregatedLogs.Length;

            //Minus the array characters, newlines
            actualDirectorySize -= 6;

            //Minus the added ','
            actualDirectorySize -= logFiles.Length;
            Assert.That(aggregatedLogs, Is.Not.Empty);
            Assert.That(actualDirectorySize, Is.EqualTo(logDirectorySize));
        }

        [Test]
        public void EraseLogs_DeletesJlogfiles_AllJlogsAreDeleted()
        {
            var logFiles = new DirectoryInfo(TestLogFilesDirectory)
                    .EnumerateFiles("*jlog", SearchOption.AllDirectories).Count();
            var logsDeleted = YouToddlerHistorian.EraseLogs(new DirectoryInfo(TestLogFilesDirectory));

            Assert.That(logsDeleted, Is.Not.Zero);
            Assert.That(logsDeleted, Is.EqualTo(logFiles));
        }
    }
}
