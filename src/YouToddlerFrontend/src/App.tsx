import React, { ChangeEvent, createContext, useRef, useState } from 'react';
import './App.css';
import { Video } from './models';

export const ThemeContext = createContext<string>("light")

function App() {

  const [ytLink, setYtLink] = useState<string>("")
  const [newLink, setNewLink] = useState<string>("")

  const [isUrlValid, setIsUrlValid] = useState<string>("aaa")
  const regExp = /^.*(youtu.be\/|v\/|u\/\w\/|embed\/|watch\?v=|\&v=|\?v=)([^#\&\?]*).*/;

  const validateUrl = (url: string) => {
    if (regExp.test(url)) {
      setIsUrlValid("true")
      return true
    } else {
      setIsUrlValid("false")
      return false
    }
  }

  const [theme, setTheme] = useState("dark")

  const toggleTheme = () => {
    setTheme((curr) => (curr === "light" ? "dark": "light"))
  }

  //Dummy data
  const test1 = {
    videoCodec: "mp3",
    fps: 30,
    vbr: 20000
  } as Video
  const test2 = {
    videoCodec: "mp4",
    fps: 60,
    vbr: 30000
  } as Video
  const [placeholderInfo, setPlaceholderInfo] = useState([test1, test2])
  //end of dummy

  const handleChange = (event: ChangeEvent<HTMLInputElement>): void => {
    validateUrl(newLink)
    setNewLink(event.target.value)
  }

  const addLink = (): void => {
    if (validateUrl(newLink)){
      setYtLink(newLink)
    } else {
      alert("Invalid Youtube URL")
    }
  }

  return (
    <ThemeContext.Provider value={theme}>
    <div className="App" id={theme}>
      <div className='header'>
        <div id='ytoddler'>YouToddler</div>
        <button className="navbtn">Home</button>
        <button className="navbtn">History</button>
        <button className="navbtn" id="mode" onClick={toggleTheme}>Mode</button>
      </div>
      <h1>Download Youtube videos</h1>
      <p>(without clicking any ads or viruses)</p>
      <div className="linkQuery">
        <input placeholder="Enter youtube URL here" onChange={handleChange}/>
        <button id='submitbtn' onClick={addLink}>Convert</button>
      </div>
      <div className='details'>
        <div>
          <h1>{ytLink}</h1>
          <p>{isUrlValid}</p>
        </div>
        {ytLink !== "" &&
          <table>
            <thead>
              <tr>
                <td>Codec</td>
                <td>Fps</td>
                <td>Bitrate</td>
                <td></td>
              </tr>
            </thead>
            <tbody>
              {placeholderInfo.map((msg) => {
                return (
                  <tr>
                  <td>{msg.videoCodec}</td>
                  <td>{msg.fps}</td>
                  <td>{msg.vbr}</td>
                  <td><button>donwload</button></td>
                  </tr>
                )
              })}
            </tbody>
          </table>}
      </div>
      <div className='resources'>
        <p><a href="https://github.com/cant0r/YouToddler/blob/master/CONTRIBUTING.md">Contribute</a> to the <a href="https://github.com/cant0r/YouToddler">project</a></p>
      </div>
    </div>
    </ThemeContext.Provider>
  );
}

export default App;
