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
```shell
cd introduction



../node_modules/node-webvtt-youtube/bin/webvtt-segment.js -v --target-duration 10 -o ./1/subtitles/zh introduction-1.webvtt

../node_modules/node-webvtt-youtube/bin/webvtt-segment.js -v --target-duration 10 -o ./2/subtitles/zh introduction-2.webvtt

../node_modules/node-webvtt-youtube/bin/webvtt-segment.js -v --target-duration 10 -o ./3/subtitles/zh introduction-3.webvtt


```

# Playlist Junior 
# gen junior's hls
```shell
cd junior

sh ../genhls.sh junior 01
sh ../genhls.sh junior 02 
sh ../genhls.sh junior 03

```
## [Synchronizing WebVTT Captions](https://sdks.support.brightcove.com/features/synchronizing-webvtt-captions.html)
  > use the ffprobe command to get the offset value. ffprobe is a multimedia stream analyzer, which is part of the FFmpeg framework. You will need to download and install this on your computer.
  > ```shell
  > ffprobe -show_frames seg.ts
  > 
  > [FRAME]
  > media_type=audio
  > stream_index=0
  > key_frame=1
  > pkt_pts=126000
  > pkt_pts_time=1.400000
  > pkt_dts=126000
  > pkt_dts_time=1.400000
  > ```
  > 
  
## OCR
- [在线文字识别转换](https://ocr.wdku.net/)
- [Subtitle and captions editor](https://subtitle-horse.com/editor/create-captions)
- [在线Unicode编码转换工具](http://www.jsons.cn/unicode)


## WEBTORRENT
```shell

magnet:?xt=urn:btih:7449c475095a599d67ec173a8ec31ff2de4cf4de&dn=introduction-3.mp3&tr=wss%3A%2F%2Ftracker.btorrent.xyz&tr=wss%3A%2F%2Ftracker.openwebtorrent.com&tr=udp%3A%2F%2Ftracker.leechers-paradise.org%3A6969&tr=udp%3A%2F%2Ftracker.coppersurfer.tk%3A6969&tr=udp%3A%2F%2Ftracker.opentrackr.org%3A1337&tr=udp%3A%2F%2Fexplodie.org%3A6969&tr=udp%3A%2F%2Ftracker.empire-js.us%3A1337

```

