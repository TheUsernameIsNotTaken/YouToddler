using Serilog;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
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

        private YouToddlerParser _youToddlerParser;
        private Dictionary<Uri, YouToddlerContent> _youToddlerContentCache;
        public YouToddlerDownloader(YouToddlerParser youToddlerParser)
        {
            _youToddlerParser = youToddlerParser;
            _youToddlerContentCache = new Dictionary<Uri, YouToddlerContent>();
        }

        public bool DownloadContent(Uri content)
        {
            return false;
        }
        public bool DownloadContent(Uri content, YouToddlerDownloaderArguments args)
        {
            return false;
        }

        public YouToddlerContent DownloadContentMetadata(Uri content)
        {
            YouToddlerContent contentMetadata;
            try
            {
                contentMetadata = _youToddlerContentCache[content];
            }
            catch (KeyNotFoundException kfe)
            {
                Log.Debug("Metadata not in cache.", kfe);
                (int exitCode, string output) result = YouToddlerRunner.ExecuteYtDlp(
                    $"-o \"{_outputTemplate}\" {_writeMetadataArgument} {_geoBypassArgument} {content}");

                if (result.exitCode != 0)
                {
                    string msg = $"Failed to retrieve metadata for: {content}";
                    Log.Fatal(msg);
                    throw new InvalidOperationException(msg);
                }

                contentMetadata = _youToddlerParser.ParseMetadata(result.output);

                _youToddlerContentCache.TryAdd(content, contentMetadata);
            }

            return contentMetadata;
        }
    }
}
