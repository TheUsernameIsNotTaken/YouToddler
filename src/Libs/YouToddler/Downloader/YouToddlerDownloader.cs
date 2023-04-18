using Microsoft.Extensions.Configuration;
using Serilog;
using YouToddler.Configuration;
using YouToddler.Models;
using YouToddler.Parser;
using YouToddler.Runner;

namespace YouToddler.Downloader
{
    public class YouToddlerDownloader
    {
        private const string _outputTemplate = "%(title)s.%(ext)s";
        private const string _geoBypassArgument = "--geo-bypass";
        private const string _writeMetadataArgument = "--write-info-json --skip-download";

        public YouToddlerConfiguration Configuration { get; private set; }
        private IYouToddlerParser _youToddlerParser;
        private Dictionary<Uri, YouToddlerMediaContent[]> _youToddlerContentCache;
        public YouToddlerDownloader(IConfiguration configuration, IYouToddlerParser youToddlerParser)
        {
            Configuration = configuration.GetSection("YouToddlerConfiguration").Get<YouToddlerConfiguration>();
            Directory.CreateDirectory(Configuration.StagingDirectory);

            _youToddlerParser = youToddlerParser;
            _youToddlerContentCache = new Dictionary<Uri, YouToddlerMediaContent[]>();
        }

        public void DownloadContent(Uri content)
        {
            CleanStagingDirectory();
            Log.Information("Downloading content in the best default format.", content);
            (int exitCode, string output) result = YouToddlerRunner.ExecuteYtDlp(
                    $"-o \"{Configuration.StagingDirectory}{Path.DirectorySeparatorChar}{_outputTemplate}\" {_geoBypassArgument} {content}");
            if (result.exitCode == 0)
            {
                Log.Information("Content downloaded to the staging directory.", result.output, content);
            }
            else
            {
                string msg = "Failed to download content. Please check the logs!";
                Log.Fatal(msg, result.output, content);
                throw new InvalidOperationException(msg);
            }
        }
        public void DownloadContent(Uri content, YouToddlerDownloaderArguments args)
        {
            CleanStagingDirectory();
            bool areArgsValid = ValidateDownloadArguments(content, args);

            if (!areArgsValid)
            {
                string msg = $"The requested media formats with ids {args.videoFormat} and {args.audioFormat} don't exist for the input content!";
                Log.Fatal(msg, args, content);
                throw new InvalidOperationException(msg );
            }

            Log.Information("Downloading content in the requested format.", content);
            (int exitCode, string output) result = YouToddlerRunner.ExecuteYtDlp(
                    $"-f {args.videoFormat}+{args.audioFormat} -o \"{Configuration.StagingDirectory}{Path.DirectorySeparatorChar}{_outputTemplate}\" {_geoBypassArgument} {content}");
            if (result.exitCode == 0)
            {
                Log.Information("Content downloaded to the staging directory.", result.output, content);
            }
            else
            {
                string msg = "Failed to download content. Please check the logs!";
                Log.Fatal(msg, result.output, content);
                throw new InvalidOperationException(msg);
            }
        }

        public YouToddlerMediaContent[] DownloadContentMetadata(Uri content)
        {
            Log.Information($"Retrieving metadata for {content}", content);
            YouToddlerMediaContent[] contentMetadata;
            try
            {
                Log.Debug("Found in local cache.");
                contentMetadata = _youToddlerContentCache[content];
            }
            catch (KeyNotFoundException kfe)
            {
                Log.Debug("Metadata not in cache.", kfe);
                (int exitCode, string output) result = YouToddlerRunner.ExecuteYtDlp(
                    $"-o \"{Configuration.StagingDirectory}{Path.DirectorySeparatorChar}{_outputTemplate}\" {_writeMetadataArgument} {_geoBypassArgument} {content}");

                if (result.exitCode != 0)
                {
                    string msg = $"Failed to retrieve metadata for: {content}";
                    Log.Fatal(msg);
                    throw new InvalidOperationException(msg);
                }

                FileInfo infoJson = new DirectoryInfo(Configuration.StagingDirectory).GetFiles("*info.json").First();
                contentMetadata = _youToddlerParser.ParseMetadata(infoJson);
                CleanStagingDirectory();
                _youToddlerContentCache.TryAdd(content, contentMetadata);
            }

            return contentMetadata;
        }

        private bool ValidateDownloadArguments(Uri content, YouToddlerDownloaderArguments args)
        {
            Log.Information("Validating download arguments.");
            YouToddlerMediaContent[] metadata = DownloadContentMetadata(content);
            bool valid = true;
            valid &= metadata.Any(m => m.id == args.videoFormat);
            valid &= metadata.Any(m => m.id == args.audioFormat);
            return valid;
        }

        private void CleanStagingDirectory()
        {
            Log.Information("Cleaning up staging area.");
            new DirectoryInfo(Configuration.StagingDirectory).EnumerateFiles("*info.json", SearchOption.AllDirectories).AsParallel().ForAll(f => f.Delete());
            Log.Information("Cleaned up staging area.");
        }
    }
}
