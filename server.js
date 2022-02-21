const fs = require('fs')

const content = document.getElementById("codeTextArea").value

function writeToFile() {fs.writeFile('./resources/code.java', content, err => {
  if (err) {
    console.error(err)
    return
  }
})}
