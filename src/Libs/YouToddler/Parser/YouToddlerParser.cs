using Serilog;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json.Nodes;
using System.Threading.Tasks;
using YouToddler.Models;
using YouToddler.Runner;

namespace YouToddler.Parser
{
    public class YouToddlerParser : IYouToddlerParser
    {

        public YouToddlerMediaContent[] ParseMetadata(FileInfo infoJson)
        {
            string jsonText = File.ReadAllText(infoJson.FullName);
            JsonNode? root = JsonNode.Parse(jsonText);

            if (root is null)
            {
                string msg = "Couldn't parse any JSON objects from *info.json!";
                Log.Fatal(msg);
                throw new InvalidOperationException(msg);
            }

            string mediaTitle = root["title"]!.GetValue<string>();
            Uri mediaThumbnailUri = root["thumbnail"]!.GetValue<Uri>();

            JsonArray formatObjects = root["formats"]!.AsArray();

            return formatObjects.Where(o => o["format_node"]!.GetValue<string>() != "storyboard")
                .Select(m =>
                {
                    var audio = new YouToddlerAudioFormat(m["acodec"]!.GetValue<string>(), m["abr"]!.GetValue<int>());
                    var video = new YouToddlerVideoFormat(m["vcodec"]!.GetValue<string>(), m["vbr"]!.GetValue<int>(), m["fps"]!.GetValue<int>());
                    return new YouToddlerMediaContent(
                        m["format_id"]!.GetValue<int>(),
                        mediaTitle,
                        m["ext"]!.GetValue<string>(),
                        mediaThumbnailUri,
                        m["filesize"]!.GetValue<int>(),
                        m["tbr"]!.GetValue<int>(),
                        m["moreinfo"]!.GetValue<string>(),
                        audio,
                        video);
                }).ToArray();
        }
    }
}
