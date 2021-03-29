# Link 
```shell
cd /Users/CC/github/moreadgroup/moread-cloud-functions/src/main/resources/static/playlist 

ln -s /Users/CC/github/moreadgroup/moread-cloud-data/src/main/resources/static/playlist/magic-ear-8-times magic-ear-8-times

```

# gen introduction's hls
```shell
cd introduction

sh ../genhls.sh introduction 1
sh ../genhls.sh introduction 2 
sh ../genhls.sh introduction 3

```

# gen junior's hls
```shell
cd junior

sh ../genhls.sh junior 01
sh ../genhls.sh junior 02 
sh ../genhls.sh junior 03

```

## OCR
- [在线文字识别转换](https://ocr.wdku.net/)
- [Subtitle and captions editor](https://subtitle-horse.com/editor/create-captions)
- [在线Unicode编码转换工具](http://www.jsons.cn/unicode)
