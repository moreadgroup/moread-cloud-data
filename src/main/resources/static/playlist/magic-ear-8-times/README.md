# Link 
```shell
cd /Users/CC/github/moreadgroup/moread-cloud-functions/src/main/resources/static/playlist 

ln -s /Users/CC/github/moreadgroup/moread-cloud-data/src/main/resources/static/playlist/magic-ear-8-times magic-ear-8-times

```
# install webvtt tools
```html
$ cd magic-ear-8-times 
$ npm i node-webvtt-youtube

```
# Playlist Introduction
## gen media hls
```shell
cd introduction

sh ../genhls.sh introduction 1
sh ../genhls.sh introduction 2 
sh ../genhls.sh introduction 3

```

## Segment subtile webvtt
```
cd introduction



$ ../node_modules/node-webvtt-youtube/bin/webvtt-segment.js -v --target-duration 10 -o ./1/subtitles/zh introduction-1.webvtt
$ ../node_modules/node-webvtt-youtube/bin/webvtt-segment.js -v --target-duration 10 -o ./2/subtitles/zh introduction-2.webvtt
$ ../node_modules/node-webvtt-youtube/bin/webvtt-segment.js -v --target-duration 10 -o ./3/subtitles/zh introduction-3.webvtt

```

# Playlist Junior 
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
