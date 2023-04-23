import React, {Component, useState} from "react";
import { Format, Metadata, Resolution } from "../models";

type LinkProps = {
    url: string
}

export const AvailableFormats = (url: LinkProps) => {

    //dummy
    const test1 = {
        url: "https://youtu.be/dQw4w9WgXcQ",
        videoName: "Example Name gone wrong! | !!!NOT CLICKBAIT!!!",
        imageUrl: "https://i.ytimg.com/vi/dQw4w9WgXcQ/hq720.jpg",
        formats: [
          {
            id: 22,
            name: "mp4",
            resolution: {
              video: {
                videoCodec: "avc1",
                fps: 30,
                vbr: 446000
              },
              audio: {
                audioCodec: "mp4a",
                abr: 0
              },
              filesize: "1,39MiB",
              tbr: 446000
            }
          }
        ]
  } as Metadata
  const [placeholderInfo, setPlaceholderInfo] = useState<Metadata>(test1)

    return(
        <div>
            <div>{url.url}</div>
            <table>
            <tr>
                <td>Codec</td>
                <td>Fps</td>
                <td>Bitrate</td>
            </tr>
            </table>
        </div>
    )
}