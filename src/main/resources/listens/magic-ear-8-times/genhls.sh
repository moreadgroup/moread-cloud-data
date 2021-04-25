echo "The File Name is: $0"
echo "The First argument is: $1"
echo "The Second argument is: $2"

mkdir $2

ffmpeg -y -i $1-$2.mp3 -preset slow -g 48 -sc_threshold 0 \
-map 0:0 \
-c:a copy \
-var_stream_map "a:0" \
-master_pl_name master.m3u8 \
-f hls -hls_time 6 -hls_list_size 0 \
-hls_segment_filename "$2/v%v/fileSequence%d.ts" \
$2/v%v/prog_index.m3u8
