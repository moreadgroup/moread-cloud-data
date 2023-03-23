# 汉字

- [汉字拼音数据](https://github.com/mozillazg/pinyin-data)
- [中华新华字典数据库。包括歇后语，成语，词语，汉字。](https://github.com/pwxcoo/chinese-xinhua)
- 

## 资料
汉字应用水平测试字表 来自自国家2016年发布的《汉字应用水平等级及测试大纲》
- 汉字应用水平测试字表: hzc甲表4000.csv 由 cnchar.csv裁剪出来
- 汉字应用水平测试字表: hzc乙表500.csv 由 cnchar.csv裁剪出来
- 汉字应用水平测试字表: hzc丙表1000.csv 由 cnchar.csv裁剪出来
- cnchar.csv 由CnCharFixTest 整理而来
- 


您可以使用 ls 命令和一些 shell 脚本技巧来按照文件名和文件名长度排序。以下是一个示例命令：
```shell
cd hzc/chatgpt

ls hzc丙表1000chatgpt*.jsonl | awk '{print length($0) " " $0}' | sort -n | awk '{$1="";print $0}' > temp.txt
```
该命令使用 ls 命令列出当前目录中的所有文件。 然后，awk 命令在每个文件名前打印文件名的长度和空格，以便后续的排序。 接下来，sort 命令按该长度进行数值排序 -n。 最后，第二个 awk 命令删除每行前的空格并打印文件名。