# Domain Task 
Task Management 
![Using Spaced Learning to Combat the Forgetting Curve](document/TaskFlowDiagram.png)

# Read, write and segment WebVTT caption files in Python.
- [https://github.com/glut23/webvtt-py](https://github.com/glut23/webvtt-py)

# Build & Run
## You need to install root pom to maven local repository
```shell
cd moread-cloud-functions
mvn --non-recursive clean compile install
```

## Build
```
mvn --settings ./settings.xml   -Dmaven.test.skip=true clean compile install 
mvn --settings ../settings.xml   -Dmaven.test.skip=true compile spring-boot:build-image
```

## Start Infrastructure
```shell
docker run -e POSTGRES_PASSWORD=123456 -p 5432:5432 postgres:9.6.12 & 
docker run -p 6379:6379 redis:6.0.10 & 
docker run -p 8761:8761 moreadgroup/springcloud-eureka &

```

## Run with Docker
```shell
docker run -p 18050:18050 moreadgroup/domain-task
```

## check data
```shell script
http://localhost:18050/explorer/index.html#uri=/books
```
![HAL Explorer](document/HAL-EXPLORER.png)

## play video
```shell
You can simply run the project and type http://localhost:18060/audiovideo/audios/audio1.mp3 on browser.
You can simply run the project and type http://localhost:18060/audiovideo/videos/video1.mp4 on browser.

Prepare:
http://d2zihajmogu5jn.cloudfront.net/bipbop-advanced/bipbop_16x9_variant.m3u8
http://d2zihajmogu5jn.cloudfront.net/bipbop-advanced/gear1/prog_index.m3u8
http://d2zihajmogu5jn.cloudfront.net/bipbop-advanced/gear1/main.ts
http://d2zihajmogu5jn.cloudfront.net/bipbop-advanced/subtitles/eng/prog_index.m3u8
http://d2zihajmogu5jn.cloudfront.net/bipbop-advanced/subtitles/eng/fileSequence0.webvtt
http://d2zihajmogu5jn.cloudfront.net/bipbop-advanced/subtitles/eng/fileSequence1.webvtt
http://d2zihajmogu5jn.cloudfront.net/bipbop-advanced/subtitles/eng/fileSequence2.webvtt
http://d2zihajmogu5jn.cloudfront.net/bipbop-advanced/subtitles/eng/fileSequence3.webvtt
http://d2zihajmogu5jn.cloudfront.net/bipbop-advanced/subtitles/eng/fileSequence4.webvtt
http://d2zihajmogu5jn.cloudfront.net/bipbop-advanced/subtitles/eng/fileSequence5.webvtt


```

## [Serve Static Resources with Spring](https://www.baeldung.com/spring-mvc-static-resources)
> By default, this handler serves static content from any of /static, /public, /resources, and /META-INF/resources directories that are on the classpath.

## play vue3  
```shell

You can simply run the project and type http://localhost:18060 on browser to vist vuejs web app.

http://localhost:18060/rest/movies/1  to get json which is accessed by HelloWorld.vue

```

## play graphql api with Altair
```shell
http://localhost:18050/domain-useraccount/graphql
```
![Graphql-Altair](document/Graphql-Altair.png)

## postgres on Mac
run the docker postgres - make sure the port is published, I use alpine because it's lightweight.
```shell

docker run --rm -P -p 127.0.0.1:5432:5432 -e POSTGRES_PASSWORD="123456" --name pg postgres:alpine

brew install pgcli

using another terminal, access the database from the host using the postgres uri

pgcli postgresql://postgres:123456@localhost:5432/postgres

postgres> drop schema domaintaskdb cascade

postgres> set search_path to domaintaskdb

for mac users, replace psql with pgcli
```

# Reference
- [A custom data type (CDT) is a designer-defined data structure that represents a logical grouping of related data, such as Employee and Contract. ](https://docs.appian.com/suite/help/20.4/Custom_Data_Types.html)
- [Creating a new property for custom types and aspects](https://docs.alfresco.com/6.1/tasks/admintools-ct-properties-create.html)
- [what is type and aspect in alfresco?](https://stackoverflow.com/questions/4077039/what-is-type-and-aspect-in-alfresco)  
- [Dealing with Properties by Martin Fowler](document/properties_by_MartinFowler.pdf)
![dynamic-property-knowledge-level](document/dynamic-property-knowledge-level.png)
- [Spring Boot WebFlux + Thymeleaf reactive example](https://mkyong.com/spring-boot/spring-boot-webflux-thymeleaf-reactive-example/)  
- [Spring Boot, Vue.js, Axios and Thymeleaf with Bootstrap in 4 commits](https://dev.to/brunodrugowick/spring-boot-vue-js-axios-and-thymeleaf-with-bootstrap-in-4-commits-2b0l) 
- [Marrying Vue.js and Thymeleaf: Embedding Javascript Components in Server-Side Templates](https://reflectoring.io/reusable-vue-components-in-thymeleaf/)
- [WebJars are client side dependencies packaged into JAR archive files](https://www.baeldung.com/maven-webjars)
- [How to build UI in Vue.js for JAVA Springboot backend](https://js.plainenglish.io/creating-a-simple-vue-js-website-for-our-backend-1d1ef8839c27)
- [Spring Boot + Vue.js + PostgreSQL: CRUD example](https://dev.to/tienbku/spring-boot-vue-js-postgresql-crud-example-4klb)
- [A Lovely Spring View: Spring Boot & Vue.js](https://blog.codecentric.de/en/2018/04/spring-boot-vuejs/)
- [VideoJS vs JWPlayer - Choose the Best](https://aaryanadil.com/videojs-vs-jwplayer)
- [Should use ipfs instead: Building a video service using Spring Framework](https://melgenek.github.io/spring-video-service)
- [Part6 – VideoJS Player for HLS Streaming](https://www.selimatmaca.com/143-videojs-player-for-hls-streaming/)
- [Experimenting with HLS Video Streaming and IPFS](https://blog.fission.codes/experimenting-with-hls-video-streaming-and-ipfs/)
- [First release of IPFS Portable User Settings App](https://blog.fission.codes/ipfs-user-settings-app/)
- [Serving image file in Spring Boot](https://zetcode.com/springboot/serveimage/)
- [Streaming audio/video with Spring Boot REST api example example](https://technicalsand.com/streaming-data-spring-boot-restful-web-service/)
- [Rabbit Lyrics is an audio and timed lyrics synchronizer for web.](https://guoyunhe.gitlab.io/rabbit-lyrics/#install)
- [Videojs Captions Test](https://www.nuevodevel.com/nuevo/showcase/captions)
- [A vue m3u8 video player plugin using video.js 7](https://vuejsexamples.com/a-vue-m3u8-video-player-plugin-using-video-js-7/)
- [videojs播放器插件使用详解](https://cloud.tencent.com/developer/article/1615717)
- [在Video.js播放器中定製自己的組件](https://www.mdeditor.tw/pl/g2rA/zh-hk)
- [Video.js插件切换视频源并操作m3u8格式视频](https://pianshen.com/article/3145122161/)
- [FULL STACK JAVA DEVELOPMENT WITH SPRING BOOT AND VUEJS](https://www.danvega.dev/blog/2021/01/22/full-stack-java-vue/)
- [Create m3u8 file from list of ts files](https://stackoverflow.com/questions/52052883/create-m3u8-file-from-list-of-ts-files)
- [HLS Packaging using FFmpeg – Easy Step-by-Step Tutorial](https://ottverse.com/hls-packaging-using-ffmpeg-live-vod/)
- [How to create .mpd or .m3u8 video file on the server using FFMPEG for Adaptive Streaming](https://mayur-solanki.medium.com/how-to-create-mpd-or-m3u8-video-file-from-server-using-ffmpeg-97e9e1fbf6a3)
- [Design Ideas and Code Realization of Ebbinghaus English Memory Program](https://programmersought.com/article/19634839034/)
- [Design and Implementation of Memory Assistant Based on Ebbinghaus Forgetting Curve](https://iopscience.iop.org/article/10.1088/1755-1315/687/1/012187/pdf)
- [The best spaced repetition app](https://www.edapp.com/blog/spaced-repetition/)
  > Based on the highly-regarded [Supermemo SM-2 interval algorithm](https://www.supermemo.com/en/archives1990-2015/english/ol/sm2), EdApp’s Brain Boost feature repeats any course material that the learner has not completed successfully more frequently to encourage retention until it’s locking into their long-term memory.
  > 
- [有效背单词的一个简单算法(一)——SugarMemo算法学习记录](https://blog.csdn.net/hnliuwx/article/details/5519354)
  > 依据SuperMemo(下面简称Super)的分级(SM2),单词从最熟悉到最不熟悉分为6个级别.0级到5级
  > 
  > 这6个级别的描述分别为: [SM-2 is a simple spaced repetition algorithm. It calculates the number of days to wait before reviewing a piece of information based on how easily the information was remembered today.](https://github.com/thyagoluciano/sm2)
  > 
  >- 5 - perfect response 单词记得非常好
  >- 4 - correct response after a hesitation 回想一下,可以正确回忆出单词
  >- 3 - correct response recalled with serious difficulty 稍微吃力的回想一下,可以正确回忆出单词
  >- 2 - incorrect response; where the correct one seemed easy to recall 在提示的情况下,能想起正确的单词
  >- 1 - incorrect response; the correct one remembered 看到答案,对正确单词有印象
  >- 0 - complete blackout. 完全一摸黑*_*
- [SM-15](https://github.com/slaypni/SM-15) 
- [Spaced repetition algorithm 2 implementation in Kotlin](https://blog.mestwin.net/spaced-repetition-algorithm-implementation-in-kotlin/)

## [Creating a Master Playlist with Ffmpeg](http://hlsbook.net/creating-a-master-playlist-with-ffmpeg/)
1. The first thing to do is determine what streams are in the video, which we can do with the following command:
```shell
ffmpeg -i sintel_trailer-1080p.mp4 -hide_banner
```
2. Here’s the ffmpeg command to generate the variants and the master playlist: 
```shell
ffmpeg -y -i symphony-no-6.m4a -preset slow -g 48 -sc_threshold 0 \
-map 0:0 \
-c:a copy \
-var_stream_map "a:0" \
-master_pl_name master.m3u8 \
-f hls -hls_time 6 -hls_list_size 0 \
-hls_segment_filename "v%v/fileSequence%d.ts" \
v%v/prog_index.m3u8
```

- [Vue Apollo](https://hasura.io/learn/graphql/vue/apollo-client/)
- [Handling authentication in your GraphQL-powered Vue app](https://blog.logrocket.com/handling-authentication-in-your-graphql-powered-vue-app/)
- [Part 3: Client-side GraphQL with Vue.js](https://www.vuemastery.com/blog/part-3-client-side-graphql-with-vuejs/)
- [How To Build a Blog With Vue, GraphQL, and Apollo Client](https://www.digitalocean.com/community/tutorials/how-to-build-a-blog-with-vue-graphql-and-apollo-client)
- [Integrate graphQL with vue using vue-apollo](https://medium.com/aubergine-solutions/integrate-graphql-with-vue-using-vue-apollo-665ed29a993d)
- [** Build a blog with Vue, Strapi and Apollo](https://strapi.io/blog/build-a-blog-with-vue-strapi-and-apollo)


## 使用nrm 切换镜像地址： 
```
npm ---- https://registry.npmjs.org/
  cnpm --- http://r.cnpmjs.org/
* taobao - http://registry.npm.taobao.org/
  edunpm - http://registry.enpmjs.org/
  eu ----- http://registry.npmjs.eu/
  au ----- http://registry.npmjs.org.au/
  sl ----- http://npm.strongloop.com/
  nj ----- https://registry.nodejitsu.com/
  pt ----- http://registry.npmjs.pt/
使用nrm 切换镜像地址：

nrm use taobao
```


# EC Dict
- [English-Chinese Dictionary](https://github.com/bg1fpx/English-Chinese-Dictionary)
- [Free English to Chinese Dictionary Database](https://github.com/skywind3000/ECDICT)