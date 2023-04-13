using CommandLine;
using Microsoft.Extensions.Configuration;
using Serilog;
using System.Text.Json;
using System.Text.Json.Nodes;
using System.Text.RegularExpressions;
using YouToddler.Configuration;
using YouToddler.Downloader;
using YouToddler.History;
using YouToddler.Models;
using YouToddler.Parser;
using YouToddlerCLI.Options;


IConfiguration configuration = new ConfigurationBuilder()
        .SetBasePath (Directory.GetCurrentDirectory ())
        .AddJsonFile("appsettings.json")
        .Build ();

Log.Logger = new LoggerConfiguration()
                .ReadFrom.Configuration(configuration)
                .CreateLogger();

Parser.Default.ParseArguments<DescribeOptions, DownloadOptions, HistoryOptions>(args)
    .MapResult((DescribeOptions dopts) => RunDescribeCommand(dopts, configuration),
               (DownloadOptions dopts) => RunDownloadCommand(dopts, configuration),
               (HistoryOptions hopts) => RunHistoryCommand(hopts, configuration),
               _ => -1);

static int RunHistoryCommand(HistoryOptions hopts, IConfiguration configuration)
{
    YouToddlerConfiguration youToddlerConfiguration = configuration.GetSection("YouToddlerConfiguration").Get<YouToddlerConfiguration>();
    YouToddlerParser youToddlerParser = new YouToddlerParser();
    YouToddlerDownloader youToddlerDownloader = new YouToddlerDownloader(configuration, youToddlerParser);

    if (hopts.DeleteHistory)
    {
        Log.Information("Deleting logfiles from disk!");
        int filesDeleted = YouToddlerHistorian.EraseLogs(new DirectoryInfo(Directory.GetCurrentDirectory()));
        Log.Information($"Deleted {filesDeleted} logfiles from disk.");
    }
    else
    {
        Log.Information("Downloading log history.");
        Log.Warning("All log files will be merged into a single JSON file! Prepare your system!");
        Log.Information($"History will be saved to {Path.GetFullPath("history/history.json")}");

        string history = YouToddlerHistorian.AggregateLogs(new DirectoryInfo(Directory.GetCurrentDirectory()));
        Directory.CreateDirectory("history");
        File.WriteAllText($"history/history.json", history);
    }
    return 0;
}
static int RunDescribeCommand(DescribeOptions dopts, IConfiguration configuration)
{
    YouToddlerConfiguration youToddlerConfiguration = configuration.GetSection("YouToddlerConfiguration").Get<YouToddlerConfiguration>();
    YouToddlerParser youToddlerParser = new YouToddlerParser();
    YouToddlerDownloader youToddlerDownloader = new YouToddlerDownloader(configuration, youToddlerParser);

    if (!string.IsNullOrEmpty(dopts.VideoUriLink)) 
    {
        if(ValidateYouTubeUrl(dopts.VideoUriLink)) 
        {
            var metadataEntries = youToddlerDownloader.DownloadContentMetadata(new Uri(dopts.VideoUriLink));
            Log.Information("Serializing metadat data to JSON.");
            Directory.CreateDirectory("output");
            using (FileStream jsonOutput = File.Create($"output/format_metadata.json"))
            {
                JsonSerializerOptions jopts = new JsonSerializerOptions();
                jopts.WriteIndented = true;
                JsonSerializer.Serialize(jsonOutput, metadataEntries, jopts);
            }
            Log.Information($"Serialized metadata to: {Path.GetFullPath("output/format_metadata.json")}");
        }
        else
        {
            Log.Fatal($"Invalid YouTube URL received! {dopts.VideoUriLink}");
            return -1;
        }
    }

    return 0;
}

static int RunDownloadCommand(DownloadOptions dopts, IConfiguration configuration)
{
    YouToddlerConfiguration youToddlerConfiguration = configuration.GetSection("YouToddlerConfiguration").Get<YouToddlerConfiguration>();
    YouToddlerParser youToddlerParser = new YouToddlerParser();
    YouToddlerDownloader youToddlerDownloader = new YouToddlerDownloader(configuration, youToddlerParser);

    return 0;
}

static bool ValidateYouTubeUrl(string url)
{
    return Regex.IsMatch(url, "^https:\\/\\/(www\\.)?(youtu.*be.*)\\/(watch\\?v=|embed\\/|v|shorts|)(.*?((?=[&#?])|$))");
}