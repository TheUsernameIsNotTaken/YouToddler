using YouToddler.Models;

namespace YouToddler.Parser
{
    public interface IYouToddlerParser
    {
        public YouToddlerMediaContent[] ParseMetadata(FileInfo infoJson);
    }
}
