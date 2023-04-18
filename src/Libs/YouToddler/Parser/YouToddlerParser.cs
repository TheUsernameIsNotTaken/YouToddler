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
            Uri mediaThumbnailUri = new Uri(root["thumbnail"]!.GetValue<string>());

            JsonArray formatObjects = root["formats"]!.AsArray();

            return formatObjects.Where(o => o["format_note"]!.GetValue<string>() != "storyboard")
                .Select(m =>
                {
                    var audio = new YouToddlerAudioFormat(
                        m["acodec"]?.GetValue<string>() ?? "none",
                        (int)(m["abr"]?.GetValue<float>() ?? -1));

                    var video = new YouToddlerVideoFormat(
                        m["vcodec"]?.GetValue<string>() ?? "none",
                        (int)(m["vbr"]?.GetValue<float>() ?? -1),
                        (int)(m["fps"]?.GetValue<float>() ?? -1));

                    return new YouToddlerMediaContent(
                        Convert.ToInt32(m["format_id"]!.GetValue<string>()),
                        mediaTitle,
                        m["ext"]!.GetValue<string>(),
                        mediaThumbnailUri,
                        (m["filesize"]?.GetValue<int>() ?? m["filesize_approx"]?.GetValue<int>()) ?? -1,
                        (int)m["tbr"]!.GetValue<float>(),
                        m["moreinfo"]?.GetValue<string>() ?? "none",
                        audio,
                        video);
                }).ToArray();
        }
    }
}
