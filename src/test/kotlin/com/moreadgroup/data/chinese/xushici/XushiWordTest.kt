package com.moreadgroup.data.chinese.xushici

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import com.moreadgroup.data.english.YasiYuminghong
import org.junit.Test
import java.io.File
import java.util.regex.Pattern

/**
 * @Author conan8chan@yahoo.com
 * @Date 2/28/23T4:36 PM-Tuesday
 */
class XushiWordTest {


    private val ROOT_FOLDER = "/Users/CC/github/moreadgroup/moread-cloud-data/src/main/resources/"

    private val srcFolder = ROOT_FOLDER + "chinese/xushici/"
    private val destFolder = ROOT_FOLDER + "chinese/xushici/out/"
    private val quizzesFolder = ROOT_FOLDER + "chinese/xushici/quizzes/"

    val shiWordsFiles = listOf<Pair<String, Boolean>>(
        Pair("高考高频实词001-爱.yaml", true),
        Pair("高考高频实词002-安.yaml", true),
        Pair("高考高频实词003-懊.yaml", true),
        Pair("高考高频实词004-倍.yaml", true),
        Pair("高考高频实词005-被.yaml", true),
        Pair("高考高频实词006-本.yaml", true),
        Pair("高考高频实词007-比.yaml", true),
        Pair("高考高频实词008-鄙.yaml", true),
        Pair("高考高频实词009-毕.yaml", true),
        Pair("高考高频实词010-辟.yaml", true),
        Pair("高考高频实词011-壁.yaml", true),
        Pair("高考高频实词012-便.yaml", true),
        Pair("高考高频实词013-兵.yaml", true),
        Pair("高考高频实词014-病.yaml", true),
        Pair("高考高频实词015-薄.yaml", true),
        Pair("高考高频实词016-哺.yaml", true),
        Pair("高考高频实词017-策.yaml", true),
        Pair("高考高频实词018-曾.yaml", true),
        Pair("高考高频实词019-察.yaml", true),
        Pair("高考高频实词020-常.yaml", true),
        Pair("高考高频实词021-朝.yaml", true),
        Pair("高考高频实词022-诚.yaml", true),
        Pair("高考高频实词023-乘.yaml", true),
        Pair("高考高频实词024-冲.yaml", true),
        Pair("高考高频实词025-除.yaml", true),
        Pair("高考高频实词026-创.yaml", true),
        Pair("高考高频实词027-辞.yaml", true),
        Pair("高考高频实词028-刺.yaml", true),
        Pair("高考高频实词029-从.yaml", true),
        Pair("高考高频实词030-卒.yaml", false),
        Pair("高考高频实词031-数.yaml", false),
        Pair("高考高频实词032-殆.yaml", true),
        Pair("高考高频实词033-待.yaml", true),
        Pair("高考高频实词034-啖.yaml", true),
        Pair("高考高频实词035-当.yaml", true),
        Pair("高考高频实词036-悼.yaml", true),
        Pair("高考高频实词037-道.yaml", true),
        Pair("高考高频实词038-得.yaml", true),
        Pair("高考高频实词039-吊.yaml", true),
        Pair("高考高频实词040-独.yaml", true),
        Pair("高考高频实词041-夺.yaml", true),
        Pair("高考高频实词041-度.yaml", true),
        Pair("高考高频实词042-尔.yaml", true),
        Pair("高考高频实词043-迩.yaml", true),
        Pair("高考高频实词044-发.yaml", true),
        Pair("高考高频实词045-伐.yaml", true),
        Pair("高考高频实词046-凡.yaml", true),
        Pair("高考高频实词047-方.yaml", true),
        Pair("高考高频实词048-非.yaml", true),
        Pair("高考高频实词049-废.yaml", true),
        Pair("高考高频实词050-封.yaml", true),
        Pair("高考高频实词051-奉.yaml", true),
        Pair("高考高频实词052-夫.yaml", true),
        Pair("高考高频实词053-负.yaml", true),
        Pair("高考高频实词054-复.yaml", true),
        Pair("高考高频实词055-盖.yaml", true),
        Pair("高考高频实词056-敢.yaml", true),
        Pair("高考高频实词057-膏.yaml", true),
        Pair("高考高频实词058-工.yaml", true),
        Pair("高考高频实词059-苟.yaml", true),
        Pair("高考高频实词060-构.yaml", true),
        Pair("高考高频实词061-购.yaml", true),
        Pair("高考高频实词062-鼓.yaml", true),
        Pair("高考高频实词063-固.yaml", true),
        Pair("高考高频实词064-故.yaml", true),
        Pair("高考高频实词065-顾.yaml", true),
        Pair("高考高频实词066-归.yaml", true),
        Pair("高考高频实词067-国.yaml", true),
        Pair("高考高频实词068-果.yaml", true),
        Pair("高考高频实词069-过.yaml", true),
        Pair("高考高频实词070-行.yaml", false),
        Pair("高考高频实词071-恨.yaml", true),
        Pair("高考高频实词072-胡.yaml", true),
        Pair("高考高频实词073-患.yaml", true),
        Pair("高考高频实词074-回.yaml", true),
        Pair("高考高频实词075-会.yaml", true),
        Pair("高考高频实词076-恚.yaml", true),
        Pair("高考高频实词077-晦.yaml", true),
        Pair("高考高频实词078-或.yaml", true),
        Pair("高考高频实词079-奇.yaml", false),
        Pair("高考高频实词080-期.yaml", false),
        Pair("高考高频实词081-及.yaml", true),
        Pair("高考高频实词082-即.yaml", true),
        Pair("高考高频实词083-疾.yaml", true),
        Pair("高考高频实词084-济.yaml", true),
        Pair("高考高频实词085-既.yaml", true),
        Pair("高考高频实词086-嘉.yaml", true),
        Pair("高考高频实词087-假.yaml", true),
        Pair("高考高频实词088-间.yaml", true),
        Pair("高考高频实词089-见.yaml", true),
        Pair("高考高频实词090-将.yaml", true),
        Pair("高考高频实词091-解.yaml", true),
        Pair("高考高频实词092-矜.yaml", true),
        Pair("高考高频实词093-进.yaml", true),
        Pair("高考高频实词094-景.yaml", true),
        Pair("高考高频实词095-竟.yaml", true),
        Pair("高考高频实词096-咎.yaml", true),
        Pair("高考高频实词097-就.yaml", true),
        Pair("高考高频实词098-举.yaml", true),
        Pair("高考高频实词099-绝.yaml", true),
        Pair("高考高频实词100-堪.yaml", true),
        Pair("高考高频实词101-抗.yaml", true),
        Pair("高考高频实词102-可.yaml", true),
        Pair("高考高频实词103-克.yaml", true),
        Pair("高考高频实词104-归.yaml", false),
        Pair("高考高频实词105-类.yaml", true),
        Pair("高考高频实词106-怜.yaml", true),
        Pair("高考高频实词107-聊.yaml", true),
        Pair("高考高频实词108-令.yaml", true),
        Pair("高考高频实词109-路.yaml", true),
        Pair("高考高频实词110-论.yaml", true),
        Pair("高考高频实词111-蒙.yaml", true),
        Pair("高考高频实词112-弥.yaml", true),
        Pair("高考高频实词113-靡.yaml", true),
        Pair("高考高频实词114-名.yaml", true),
        Pair("高考高频实词115-没.yaml", true),
        Pair("高考高频实词116-莫.yaml", true),
        Pair("高考高频实词117-牧.yaml", true),
        Pair("高考高频实词118-莫.yaml", false),
        Pair("高考高频实词119-慕.yaml", true),
        Pair("高考高频实词120-暮.yaml", true),
        Pair("高考高频实词121-内.yaml", true),
        Pair("高考高频实词122-拟.yaml", true),
        Pair("高考高频实词123-逆.yaml", true),
        Pair("高考高频实词124-宁.yaml", true),
        Pair("高考高频实词125-判.yaml", true),
        Pair("高考高频实词126-畔.yaml", true),
        Pair("高考高频实词127-披.yaml", true),
        Pair("高考高频实词128-被.yaml", false),
        Pair("高考高频实词129-否.yaml", true),
        Pair("高考高频实词130-辟.yaml", false),
        Pair("高考高频实词131-便.yaml", false),
        Pair("高考高频实词132-迫.yaml", true),
        Pair("高考高频实词133-仆.yaml", true),
        Pair("高考高频实词134-期.yaml", true),
        Pair("高考高频实词135-奇.yaml", true),
        Pair("高考高频实词136-启.yaml", true),
        Pair("高考高频实词137-起.yaml", true),
        Pair("高考高频实词138-迁.yaml", true),
        Pair("高考高频实词139-抢.yaml", true),
        Pair("高考高频实词140-将.yaml", false),
        Pair("高考高频实词141-窃.yaml", true),
        Pair("高考高频实词142-倾.yaml", true),
        Pair("高考高频实词143-请.yaml", true),
        Pair("高考高频实词144-穷.yaml", true),
        Pair("高考高频实词145-趋.yaml", true),
        Pair("高考高频实词146-去.yaml", true),
        Pair("高考高频实词147-劝.yaml", true),
        Pair("高考高频实词148-阙.yaml", false),
        Pair("高考高频实词149-却.yaml", true),
        Pair("高考高频实词150-阙.yaml", true),
        Pair("高考高频实词151-然.yaml", true),
        Pair("高考高频实词152-让.yaml", true),
        Pair("高考高频实词153-忍.yaml", true),
        Pair("高考高频实词154-如.yaml", true),
        Pair("高考高频实词155-色.yaml", true),
        Pair("高考高频实词156-善.yaml", true),
        Pair("高考高频实词157-尚.yaml", true),
        Pair("高考高频实词158-稍.yaml", true),
        Pair("高考高频实词159-少.yaml", true),
        Pair("高考高频实词160-涉.yaml", true),
        Pair("高考高频实词161-审.yaml", true),
        Pair("高考高频实词162-甚.yaml", true),
        Pair("高考高频实词163-胜.yaml", true),
        Pair("高考高频实词164-乘.yaml", false),
        Pair("高考高频实词165-施.yaml", true),
        Pair("高考高频实词166-识.yaml", true),
        Pair("高考高频实词167-食.yaml", true),
        Pair("高考高频实词168-使.yaml", true),
        Pair("高考高频实词169-始.yaml", true),
        Pair("高考高频实词170-示.yaml", true),
        Pair("高考高频实词171-市.yaml", true),
        Pair("高考高频实词172-势.yaml", true),
        Pair("高考高频实词173-事.yaml", true),
        Pair("高考高频实词174-视.yaml", true),
        Pair("高考高频实词175-是.yaml", true),
        Pair("高考高频实词176-适.yaml", true),
        Pair("高考高频实词177-收.yaml", true),
        Pair("高考高频实词178-书.yaml", true),
        Pair("高考高频实词179-孰.yaml", true),
        Pair("高考高频实词180-属.yaml", true),
        Pair("高考高频实词181-数.yaml", false),
        Pair("高考高频实词182-庶.yaml", true),
        Pair("高考高频实词183-数.yaml", true),
        Pair("高考高频实词184-率.yaml", true),
        Pair("高考高频实词185-爽.yaml", true),
        Pair("高考高频实词186-说.yaml", true),
        Pair("高考高频实词187-数.yaml", false),
        Pair("高考高频实词188-司.yaml", true),
        Pair("高考高频实词189-私.yaml", true),
        Pair("高考高频实词190-斯.yaml", true),
        Pair("高考高频实词191-食.yaml", false),
        Pair("高考高频实词192-夙.yaml", true),
        Pair("高考高频实词193-素.yaml", true),
        Pair("高考高频实词194-宿.yaml", true),
        Pair("高考高频实词195-岁.yaml", true),
        Pair("高考高频实词196-遂.yaml", true),
        Pair("高考高频实词197-飧.yaml", true),
        Pair("高考高频实词198-台.yaml", true),
        Pair("高考高频实词199-汤.yaml", true),
        Pair("高考高频实词200-当.yaml", false),
        Pair("高考高频实词201-特.yaml", true),
        Pair("高考高频实词202-提.yaml", true),
        Pair("高考高频实词203-涕.yaml", true),
        Pair("高考高频实词204-图.yaml", true),
        Pair("高考高频实词205-徒.yaml", true),
        Pair("高考高频实词206-说.yaml", false),
        Pair("高考高频实词207-亡.yaml", true),
        Pair("高考高频实词208-王.yaml", true),
        Pair("高考高频实词209-枉.yaml", true),
        Pair("高考高频实词210-王.yaml", false),
        Pair("高考高频实词211-望.yaml", true),
        Pair("高考高频实词212-危.yaml", true),
        Pair("高考高频实词213-微.yaml", true),
        Pair("高考高频实词214-唯.yaml", true),
        Pair("高考高频实词215-委.yaml", true),
        Pair("高考高频实词216-遗.yaml", false),
        Pair("高考高频实词217-闻.yaml", true),
        Pair("高考高频实词218-恶.yaml", false),
        Pair("高考高频实词219-亡.yaml", false),
        Pair("高考高频实词220-无.yaml", true),
        Pair("高考高频实词221-恶.yaml", true),
        Pair("高考高频实词222-息.yaml", true),
        Pair("高考高频实词223-奚.yaml", true),
        Pair("高考高频实词224-悉.yaml", true),
        Pair("高考高频实词225-徙.yaml", true),
        Pair("高考高频实词226-下.yaml", true),
        Pair("高考高频实词227-见.yaml", false),
        Pair("高考高频实词228-相.yaml", true),
        Pair("高考高频实词229-向.yaml", true),
        Pair("高考高频实词229-响.yaml", true),
        Pair("高考高频实词229-效.yaml", true),
        Pair("高考高频实词229-相.yaml", false),
        Pair("高考高频实词230-谢.yaml", true),
        Pair("高考高频实词231-解.yaml", false),
        Pair("高考高频实词232-信.yaml", true),
        Pair("高考高频实词233-兴.yaml", true),
        Pair("高考高频实词234-行.yaml", true),
        Pair("高考高频实词235-形.yaml", true),
        Pair("高考高频实词236-省.yaml", true),
        Pair("高考高频实词237-兴.yaml", false),
        Pair("高考高频实词238-幸.yaml", true),
        Pair("高考高频实词239-凶.yaml", true),
        Pair("高考高频实词240-休.yaml", true),
        Pair("高考高频实词241-修.yaml", true),
        Pair("高考高频实词242-须.yaml", true),
        Pair("高考高频实词243-徐.yaml", true),
        Pair("高考高频实词244-许.yaml", true),
        Pair("高考高频实词245-序.yaml", true),
        Pair("高考高频实词246-寻.yaml", true),
        Pair("高考高频实词247-训.yaml", true),
        Pair("高考高频实词248-延.yaml", true),
        Pair("高考高频实词249-严.yaml", true),
        Pair("高考高频实词250-厌.yaml", true),
        Pair("高考高频实词251-阳.yaml", true),
        Pair("高考高频实词252-佯.yaml", true),
        Pair("高考高频实词253-要.yaml", true),
        Pair("高考高频实词254-夷.yaml", true),
        Pair("高考高频实词255-宜.yaml", true),
        Pair("高考高频实词256-贻.yaml", true),
        Pair("高考高频实词257-遗.yaml", true),
        Pair("高考高频实词258-已.yaml", true),
        Pair("高考高频实词259-倚.yaml", true),
        Pair("高考高频实词260-异.yaml", true),
        Pair("高考高频实词261-易.yaml", true),
        Pair("高考高频实词262-益.yaml", true),
        Pair("高考高频实词263-意.yaml", true),
        Pair("高考高频实词264-阴.yaml", true),
        Pair("高考高频实词265-引.yaml", true),
        Pair("高考高频实词266-婴.yaml", true),
        Pair("高考高频实词267-盈.yaml", true),
        Pair("高考高频实词268-用.yaml", true),
        Pair("高考高频实词269-幽.yaml", true),
        Pair("高考高频实词270-尤.yaml", true),
        Pair("高考高频实词271-犹.yaml", true),
        Pair("高考高频实词272-游.yaml", true),
        Pair("高考高频实词273-右.yaml", true),
        Pair("高考高频实词274-愚.yaml", true),
        Pair("高考高频实词275-舆.yaml", true),
        Pair("高考高频实词276-语.yaml", true),
        Pair("高考高频实词277-狱.yaml", true),
        Pair("高考高频实词278-语.yaml", false),
        Pair("高考高频实词279-预.yaml", true),
        Pair("高考高频实词280-遇.yaml", true),
        Pair("高考高频实词281-寓.yaml", true),
        Pair("高考高频实词282-说.yaml", false),
        Pair("高考高频实词283-载.yaml", false),
        Pair("高考高频实词284-再.yaml", true),
        Pair("高考高频实词285-载.yaml", true),
        Pair("高考高频实词286-臧.yaml", true),
        Pair("高考高频实词287-造.yaml", true),
        Pair("高考高频实词288-贼.yaml", true),
        Pair("高考高频实词289-曾.yaml", false),
        Pair("高考高频实词290-彰.yaml", true),
        Pair("高考高频实词291-昭.yaml", true),
        Pair("高考高频实词292-朝.yaml", false),
        Pair("高考高频实词293-辄.yaml", true),
        Pair("高考高频实词294-征.yaml", true),
        Pair("高考高频实词295-知.yaml", true),
        Pair("高考高频实词296-志.yaml", true),
        Pair("高考高频实词297-识.yaml", false),
        Pair("高考高频实词298-制.yaml", true),
        Pair("高考高频实词299-知.yaml", false),
        Pair("高考高频实词300-质.yaml", true),
        Pair("高考高频实词301-治.yaml", true),
        Pair("高考高频实词302-致.yaml", true),
        Pair("高考高频实词303-置.yaml", true),
        Pair("高考高频实词304-中.yaml", true),
        Pair("高考高频实词305-诸.yaml", true),
        Pair("高考高频实词306-属.yaml", false),
        Pair("高考高频实词307-祝.yaml", true),
        Pair("高考高频实词308-专.yaml", true),
        Pair("高考高频实词309-转.yaml", true),
        Pair("高考高频实词310-状.yaml", true),
        Pair("高考高频实词311-追.yaml", true),
        Pair("高考高频实词312-咨.yaml", true),
        Pair("高考高频实词313-兹.yaml", true),
        Pair("高考高频实词314-资.yaml", true),
        Pair("高考高频实词315-从.yaml", false),
        Pair("高考高频实词316-走.yaml", true),
        Pair("高考高频实词317-奏.yaml", true),
        Pair("高考高频实词318-卒.yaml", true),
        Pair("高考高频实词319-族.yaml", true),
        Pair("高考高频实词320-祖.yaml", true),
        Pair("高考高频实词321-左.yaml", true),
        Pair("高考高频实词322-坐.yaml", true),

        )

    val xuWordsFiles = listOf<Pair<String, Boolean>>(
        Pair("高考高频虚词001-而.yaml", true),
        Pair("高考高频虚词002-何.yaml", true),
        Pair("高考高频虚词003-乎.yaml", true),
        Pair("高考高频虚词004-乃.yaml", true),
        Pair("高考高频虚词005-其.yaml", true),
        Pair("高考高频虚词006-且.yaml", true),
        Pair("高考高频虚词007-若.yaml", true),
        Pair("高考高频虚词008-所.yaml", true),
        Pair("高考高频虚词009-为.yaml", true),
        Pair("高考高频虚词010-焉.yaml", true),
        Pair("高考高频虚词011-也.yaml", true),
        Pair("高考高频虚词012-以.yaml", true),
        Pair("高考高频虚词013-因.yaml", true),
        Pair("高考高频虚词014-于.yaml", true),
        Pair("高考高频虚词015-与.yaml", true),
        Pair("高考高频虚词016-则.yaml", true),
        Pair("高考高频虚词017-者.yaml", true),
        Pair("高考高频虚词018-之.yaml", true),

        )

    @Test
    fun testGenerateXuWordQuizzesAllThenOK() {
        val objectMapper = ObjectMapper(
            YAMLFactory().configure(YAMLGenerator.Feature.MINIMIZE_QUOTES, true)
                .configure(YAMLGenerator.Feature.SPLIT_LINES, false)
        ).findAndRegisterModules()

        var allXushiWordQuizzes = XushiWordQuizzes()

        xuWordsFiles.forEach { pair ->

            val xushiWords: GushiwenXushiWords = objectMapper.readValue(
                File(srcFolder + pair.first), GushiwenXushiWords::class.java
            )

            println("${pair.first} Words=${xushiWords.words.size}")
            val xushiWordQuizzes = convertFrom(xushiWords);
            // We write the `employee` into `person2.yaml`
            objectMapper.writeValue(File(quizzesFolder + "Quiz" + pair.first), xushiWordQuizzes);

            if (pair.second) {
                allXushiWordQuizzes.quizzes.addAll(xushiWordQuizzes.quizzes)
            }

        }
//        allXushiWordQuizzes.quizzes.shuffle()
        objectMapper.writeValue(File(quizzesFolder + "Quiz高考高频虚词000-全部.yaml"), allXushiWordQuizzes);

    }

    @Test
    fun testGenerateShiWordQuizzesAllThenOK() {
        val objectMapper = ObjectMapper(
            YAMLFactory().configure(YAMLGenerator.Feature.MINIMIZE_QUOTES, true)
                .configure(YAMLGenerator.Feature.SPLIT_LINES, false)
        ).findAndRegisterModules()

        var allXushiWordQuizzes = XushiWordQuizzes()
        shiWordsFiles.forEach { pair ->

            val xushiWords: GushiwenXushiWords = objectMapper.readValue(
                File(srcFolder + pair.first), GushiwenXushiWords::class.java
            )

            println("${pair.first} Words=${xushiWords.words.size}")
            val xushiWordQuizzes = convertFrom(xushiWords);
            // We write the `employee` into `person2.yaml`
            objectMapper.writeValue(File(quizzesFolder + "Quiz" + pair.first), xushiWordQuizzes);
            if (pair.second) {
                allXushiWordQuizzes.quizzes.addAll(xushiWordQuizzes.quizzes)
            }
        }
//        allXushiWordQuizzes.quizzes.shuffle()
        objectMapper.writeValue(File(quizzesFolder + "Quiz高考高频实词000-全部.yaml"), allXushiWordQuizzes);

    }

    private fun convertFrom(xushiWords: GushiwenXushiWords): XushiWordQuizzes {
        var result = XushiWordQuizzes()
        xushiWords.words.forEach { word ->
            word.samples?.forEach { trans ->
                trans.sentences?.forEach { sentence ->
                    result.quizzes.add(
                        XushiWordQuiz(
                            word.word,
                            word.type,
                            word.phonetic,
                            trans.trans,
                            sentence.ancient,
                            sentence.url,
                            sentence.vernacular
                        )
                    )
                }
            }
        }
        return result
    }


    @Test
    fun testFixXuWordAllThenOK() {
        val objectMapper = ObjectMapper(
            YAMLFactory().configure(YAMLGenerator.Feature.MINIMIZE_QUOTES, true)
                .configure(YAMLGenerator.Feature.SPLIT_LINES, false)
        ).findAndRegisterModules()

        xuWordsFiles.forEach { pair ->

            val xushiWords: GushiwenXushiWords = objectMapper.readValue(
                File(srcFolder + pair.first), GushiwenXushiWords::class.java
            )

            println("${pair.first} Words=${xushiWords.words.size}")
            val xushiWordsFixed = fixSentenceWithAnchor(xushiWords);
            // We write the `employee` into `person2.yaml`
            objectMapper.writeValue(File(destFolder + pair.first), xushiWordsFixed);
        }
    }


    @Test
    fun testFixShiWordAllThenOK() {
        val objectMapper = ObjectMapper(
            YAMLFactory().configure(YAMLGenerator.Feature.MINIMIZE_QUOTES, true)
                .configure(YAMLGenerator.Feature.SPLIT_LINES, false)
        ).findAndRegisterModules()


        shiWordsFiles.forEach { pair ->

            val xushiWords: GushiwenXushiWords = objectMapper.readValue(
                File(srcFolder + pair.first), GushiwenXushiWords::class.java
            )

            println("${pair.first} Words=${xushiWords.words.size}")
            val xushiWordsFixed = fixSentenceWithAnchor(xushiWords);
            // We write the `employee` into `person2.yaml`
            objectMapper.writeValue(File(destFolder + pair.first), xushiWordsFixed);
        }
    }


    private fun fixSentenceWithAnchor(xushiWords: GushiwenXushiWords): GushiwenXushiWords {
        xushiWords.words.forEach { word ->
            word.samples?.forEach { trans ->
                trans.sentences?.forEach { sentence ->
                    if (!sentence.ancient.contains("<${word.word}>")) {

                        val matcher = Pattern.compile("${word.word}").matcher(sentence.ancient)
                        var counter = 0

                        while (matcher.find()) {
                            counter++
                        }

                        if (counter > 1) {
                            println("please fix: [${sentence.ancient}]")
                        } else {
                            sentence.ancient = sentence.ancient.replace("${word.word}", "<${word.word}>")
                        }
                    }
                    if (sentence.url == null) {
                        sentence.url = "xxxx"
                    }
                    if (sentence.vernacular == null) {
                        sentence.vernacular = "xxxx"
                    }
                }
            }
        }
        return xushiWords
    }


}