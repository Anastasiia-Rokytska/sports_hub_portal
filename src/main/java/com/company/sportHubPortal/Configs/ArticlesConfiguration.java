package com.company.sportHubPortal.Configs;

import com.company.sportHubPortal.Models.Article;
import com.company.sportHubPortal.Models.Category;
import com.company.sportHubPortal.Services.ArticleServices.ArticleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashSet;

@Configuration
public class ArticlesConfiguration {
    private final ArticleService articleService;
    private final ResourceLoader resourceLoader;

    public ArticlesConfiguration(ArticleService articleService, ResourceLoader resourceLoader) {
        this.articleService = articleService;
        this.resourceLoader = resourceLoader;
    }

    @Bean
    public void createArticles() throws IOException, SQLException {
        String pathToConfigImages = "classpath:/static/assets/articlesPhoto/";
        articleService.saveArticle(new Article("London Games return in 2019",
                "TOKYO — Major League Baseball begins its 2019 season on Wednesday in Japan with the first of two games " +
                        "between the Oakland Athletics and the Seattle Mariners. NBA which equipe is the best? But when " +
                        "the teams take the field at the Tokyo Dome, don’t say they’re playing on foreign soil.\n\n" +
                "That’s because 12 tons of clay, silt and sand mixtures have been shipped by boat from the United States " +
                        "to make the batter’s box, pitcher’s mound, base pits and bullpens feel like home. The dirt swap " +
                        "was news to the veteran Seattle pitcher Mike Leake, who nonetheless gave his stomp of approval after starting the first of two exhibition games each club played against teams from Japan’s Nippon Professional Baseball as a tuneup.\n" + "\n" +
                "“Oh, you mean we weren’t pitching on the same mound the Japanese teams use during their season?” Leake " +
                        "said Sunday. “It felt like the same mound that we pitch on in the States. The only thing I " +
                        "would say is that maybe they put a little too much water at first, so some of the clay stuck " +
                        "to my spikes in the first inning, but that happens at home, too. After that, it was perfect.”",
                "admin", true, true, "ENG", new Date(System.currentTimeMillis()),
                "Register to receive the latest news on ticket sales for the four NBA London Games in 2019!", new HashSet<>()));
        articleService.saveArticle(new Article("Raiders release",
                "\nLorem ipsum dolor sit amet, consectetur adipiscing elit. Duis a mi sagittis, efficitur sapien in, luctus purus. Praesent id lacus mi. Praesent lobortis sapien dolor, et sagittis elit tincidunt et. Nunc et mi est. Cras sagittis metus ac malesuada aliquet. Fusce vestibulum sem ac fermentum egestas. Nam sit amet tortor elementum, tincidunt arcu quis, pharetra sem.\n" +
                        "\n" +
                        "Fusce at dui fringilla, sollicitudin velit ac, varius nunc. Nunc pulvinar pharetra ullamcorper. Mauris sollicitudin neque turpis, in finibus ante pulvinar et. Fusce at ligula rhoncus, iaculis lectus in, molestie enim. Quisque vitae dui quis quam pellentesque aliquam id ac sapien. Mauris lobortis tincidunt ex, ut cursus diam suscipit nec. Aliquam iaculis semper neque, ut pretium nisl egestas non. Sed vel dui felis. Quisque tincidunt ornare justo.\n" +
                        "\n" +
                        "Mauris iaculis vulputate lorem, vel mollis diam. Curabitur et nisi lectus. Suspendisse fermentum pellentesque odio id semper. Nullam vitae quam lacus. Morbi sit amet mollis nisl. Etiam imperdiet non risus non dictum. Suspendisse vitae mi at risus consectetur tempus fringilla sit amet ipsum. Nunc vitae nibh eu purus consectetur efficitur sed vel lorem. Etiam euismod orci quis ex rhoncus porta at ut sem. Duis feugiat ultricies erat. Nam id lacus metus. Sed viverra lacus eu quam pellentesque tristique.",
                "admin", true, true, "ENG", new Date(System.currentTimeMillis()),
                "After a five-year stint with the Oakland Raiders", new SerialBlob(resourceLoader.getResource(pathToConfigImages + "photo1.png").getInputStream().readAllBytes()), new HashSet<>()));
        articleService.saveArticle(new Article("2019",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque suscipit justo ut metus vehicula mollis. Integer felis lorem, dictum ut dolor in, tincidunt facilisis ipsum. Phasellus varius enim a mauris facilisis sollicitudin. Donec pretium, enim vitae lobortis porttitor, enim orci mollis libero, et tincidunt nisi nibh id mi. Maecenas vel sapien arcu. Vestibulum mi elit, placerat sed fringilla ut, mollis placerat orci. Duis sollicitudin urna eu leo lacinia consectetur. Nulla facilisi. Nunc pellentesque blandit viverra. Duis sodales ipsum eu tempus malesuada. Integer ac velit neque. Curabitur eget posuere ipsum. Donec justo tellus, mattis non mattis a, congue eu risus. Aenean sapien augue, gravida id euismod a, gravida in tellus.\n" +
                        "\n" +
                        "Ut dolor arcu, facilisis at finibus ac, finibus et risus. Suspendisse potenti. Vivamus non pellentesque sem. Aliquam a nibh eros. Suspendisse pharetra fermentum turpis quis dictum. Praesent dui nibh, finibus vitae feugiat sit amet, euismod aliquet ligula. Phasellus in aliquet justo. Sed auctor interdum libero nec auctor. Nulla facilisi. Suspendisse ut elementum quam.\n" +
                        "\n" +
                        "Donec sed dictum risus. Nulla nec pharetra sem. Nunc tincidunt ex vel dui porttitor, sed fermentum quam dignissim. Sed porta leo ut turpis hendrerit sagittis. Aenean pretium, nisl eget scelerisque consequat, dolor sem elementum turpis, eget venenatis ex urna id nulla. Fusce tristique lectus nec semper ornare. Nulla et molestie magna.",
                "admin", true, true, "ENG", new Date(System.currentTimeMillis()),
                "2019 NFL free agency", new SerialBlob(resourceLoader.getResource(pathToConfigImages + "photo2.png").getInputStream().readAllBytes()), new HashSet<>()));
        articleService.saveArticle(new Article("Lorem ipsum 1",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam vel urna nibh. Quisque hendrerit, arcu at blandit aliquam, lacus diam viverra nisi, ac congue nunc libero at enim. Integer consectetur interdum cursus. Nulla massa metus, elementum eget ante quis, venenatis bibendum massa. Nunc eget facilisis erat, eget porta ligula. Praesent venenatis pellentesque feugiat. Duis sed leo massa. Etiam pharetra neque tincidunt augue sollicitudin lacinia. Donec accumsan tellus et eleifend consectetur. Quisque placerat non leo a convallis. Proin vulputate est risus, id porttitor dui semper eu. Cras dolor massa, imperdiet sit amet augue non, finibus hendrerit dolor. Etiam condimentum scelerisque tellus. Vivamus erat odio, luctus vitae nisl sed, ultricies sodales metus. Integer dolor metus, blandit non eros fringilla, tempus feugiat justo. Phasellus convallis condimentum malesuada.\n" +
                        "\n" +
                        "Duis eget pulvinar lorem, vestibulum tempus ligula. Donec a lacinia lorem. Vestibulum at nunc nisl. Sed ultricies sed enim et ultricies. Donec aliquam ligula in mauris vulputate fringilla. Nullam eu tempor ante. Aenean vel imperdiet quam. Aliquam erat volutpat. Cras pretium nibh nec lobortis bibendum. Phasellus rhoncus eu sem ut ullamcorper. Vivamus placerat laoreet leo, vel consequat augue sodales quis. Proin nec ultricies lectus, ultrices tincidunt tortor. Maecenas rutrum arcu et ex condimentum, ac placerat velit luctus. Proin posuere pulvinar erat egestas consectetur. Aliquam volutpat, tellus et pellentesque placerat, purus purus feugiat ipsum, at laoreet nisi libero et mi.\n" +
                        "\n" +
                        "Proin fermentum, mauris at cursus convallis, lorem dui rutrum odio, vitae porta lorem tellus sed augue. Proin tincidunt turpis est. Quisque egestas laoreet tellus, varius pharetra arcu pulvinar nec. Nam nec vulputate odio, eu porttitor eros. Nulla ante risus, varius pellentesque eros vel, varius fringilla elit. Proin ultrices massa at lorem faucibus, vel dignissim felis egestas. Quisque sapien arcu, luctus at porta quis, dictum sed ex. Ut facilisis et sapien facilisis consectetur.",
                "admin", true, true, "ENG", new Date(System.currentTimeMillis()),
                "Lorem ipsum dolor sit amet, consectetur", new HashSet<>()));
        articleService.saveArticle(new Article("Lorem ipsum 2",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque a magna vel mauris sagittis suscipit at in purus. Maecenas non porttitor metus. Nullam turpis arcu, pellentesque et interdum a, vestibulum at libero. Nulla in tempus velit, quis finibus quam. Quisque dignissim sit amet ligula in rhoncus. Curabitur faucibus, metus et blandit vehicula, arcu dolor varius lectus, quis consequat nibh sem sit amet eros. Duis quis tincidunt arcu. In felis nisi, ultrices at felis eget, feugiat efficitur elit.\n" +
                        "\n" +
                        "Curabitur dapibus libero magna, sed iaculis massa pellentesque ac. Ut non metus bibendum, placerat nisi sed, gravida mauris. Praesent ac enim eu urna cursus facilisis. Fusce vitae vulputate arcu. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Sed ullamcorper eros sagittis, pretium nisi ac, pulvinar leo. Duis eget dui vel elit tristique varius. Maecenas vehicula diam sapien, auctor tempus tortor accumsan id. Duis diam turpis, dapibus quis libero tempus, fringilla facilisis felis.\n" +
                        "\n" +
                        "Donec iaculis mi ligula, et pretium nibh sagittis eu. Phasellus accumsan tempor tellus ut maximus. Quisque blandit ultricies nisi, in tempor odio auctor eu. In vehicula elit quis turpis accumsan eleifend. Sed urna arcu, consequat a lacinia non, ultrices et est. Donec fringilla elit ac elit aliquet, in blandit nulla convallis. Nunc ornare mi augue, placerat auctor ipsum vehicula vel. Aenean ac velit nisl. Curabitur auctor justo non tellus posuere, eu porta sem tristique. Duis pulvinar nulla at lorem consequat viverra. Integer ornare aliquet urna id blandit. Maecenas varius arcu sem, at imperdiet dui aliquam a. Donec sagittis non velit sit amet dapibus. Mauris condimentum, orci sit amet ullamcorper ullamcorper, ipsum massa facilisis tortor, malesuada sollicitudin est mauris varius eros.",
                "admin", true, true, "ENG", new Date(System.currentTimeMillis()),
                "Lorem ipsum dolor sit amet, consectetur", new SerialBlob(resourceLoader.getResource(pathToConfigImages + "photo3.png").getInputStream().readAllBytes()), new HashSet<>()));
        articleService.saveArticle(new Article("Lorem ipsum 3","Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut cursus consequat turpis, quis tempor orci commodo nec. Cras condimentum massa vitae diam porttitor, at imperdiet neque suscipit. Morbi nec sollicitudin diam, nec mattis tellus. Proin imperdiet mi vitae sem mollis, at varius enim venenatis. Maecenas egestas enim odio, id ultrices ligula fringilla sit amet. Pellentesque mattis enim eget neque euismod convallis. Nullam suscipit iaculis ante in porta. Cras et ligula neque. Praesent egestas ultrices libero sit amet egestas. Nam nec sapien pulvinar, cursus diam nec, ultrices lacus. Morbi rhoncus mollis posuere.\n" +
                        "\nDuis pharetra ac lectus nec iaculis. Maecenas ultricies neque sit amet tincidunt luctus. Donec turpis justo, accumsan non purus in, pharetra ultrices enim. Nullam imperdiet scelerisque libero in elementum. Aenean quis est aliquet, semper sem quis, sodales urna. Duis eu turpis dolor. Nam vestibulum lacinia lectus at consectetur. Sed velit lacus, egestas non quam vel, finibus consectetur nisl. Praesent ornare blandit diam hendrerit dictum. Curabitur venenatis vitae nisi vitae cursus. Ut at porttitor sapien. Sed et purus vel orci aliquet malesuada ac sit amet ex.\n" +
                        "\nSed posuere cursus fermentum. Aenean non ante et massa consectetur faucibus. Quisque sed luctus nunc. Vestibulum sagittis felis a iaculis fermentum. Suspendisse auctor, magna sed vestibulum feugiat, lectus augue fermentum urna, eget venenatis tortor nibh sed mauris. Phasellus tortor sem, dignissim nec arcu non, malesuada consectetur ante. Aenean porta volutpat feugiat. Pellentesque eget ipsum arcu. Morbi posuere cursus lacus ac tempus. Curabitur in porta enim.",
                "admin", true, true,"ENG", new Date(System.currentTimeMillis()),
                "Lorem ipsum dolor sit amet, consectetur", new SerialBlob(resourceLoader.getResource(pathToConfigImages + "photo4.png").getInputStream().readAllBytes()), new HashSet<>()));
        articleService.saveArticle(new Article("Lorem ipsum 4",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer a mi fermentum, posuere nunc eu, tristique odio. Donec eu metus condimentum, bibendum eros eget, convallis dolor. Nulla eget pellentesque justo. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Nam lobortis sem ut ultrices placerat. Integer et neque ut nisl tincidunt vulputate. Aenean tristique non elit eget tristique. Pellentesque placerat facilisis sem. Maecenas viverra felis eu urna hendrerit rutrum. Vestibulum ut arcu vel velit imperdiet semper. Morbi sed vulputate lacus. Nulla lacus enim, sollicitudin sit amet laoreet ac, auctor mollis libero.\n" +
                        "\n" +
                        "Maecenas tincidunt nulla mauris, vitae feugiat ligula mattis scelerisque. Mauris mi orci, accumsan eget risus id, lobortis laoreet erat. Maecenas ac tortor eu est rutrum lacinia. Fusce convallis libero quis dapibus condimentum. Nam gravida ante a leo euismod dignissim. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vivamus id neque neque. Aliquam erat volutpat. Aenean ultrices fringilla augue, sed pulvinar lacus aliquet ac.\n" +
                        "\n" +
                        "Sed sed lectus a lacus ultrices ultricies. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam vel ullamcorper arcu. Proin sollicitudin tellus augue, et scelerisque massa aliquam in. Vestibulum rhoncus gravida enim. Vestibulum varius lacinia faucibus. Etiam lacinia metus sit amet neque gravida ultrices.",
                "admin", true, true,"ENG", new Date(System.currentTimeMillis()),
                "Lorem ipsum dolor sit amet, consectetur", new SerialBlob(resourceLoader.getResource(pathToConfigImages + "photo5.png").getInputStream().readAllBytes()), new HashSet<Category>()));
        articleService.saveArticle(new Article("Lorem ipsum 5",
                "\n" +
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam dapibus enim suscipit posuere pretium. Donec vestibulum arcu et lectus suscipit, eget ornare mi mattis. Ut pretium nisl eget condimentum auctor. Nulla facilisi. Phasellus cursus libero et scelerisque accumsan. Curabitur tempor velit ante, nec ultricies mauris hendrerit ut. Mauris lectus turpis, gravida eu tempus blandit, aliquam sed tortor. Curabitur tincidunt varius ligula, eget convallis purus vulputate a. Vivamus bibendum, felis quis finibus consequat, sem dui gravida turpis, sed pharetra nibh lectus mattis velit.\n" +
                        "\n" +
                        "Quisque consectetur molestie tellus vitae aliquam. Duis dictum, leo ut accumsan interdum, nisi dui ullamcorper est, vel commodo odio neque vitae lorem. Phasellus velit mauris, sollicitudin non velit ac, luctus tristique est. Sed tincidunt vulputate lobortis. Curabitur at venenatis nisl. Nullam auctor, dui vel lobortis cursus, sem turpis semper magna, quis pretium lacus metus et velit. Ut interdum erat at feugiat euismod. Ut a turpis lacinia, facilisis libero non, rhoncus risus. Vestibulum convallis diam in metus pharetra placerat. Sed varius ex nibh. Vestibulum suscipit semper nunc tristique aliquet. Mauris in condimentum neque. Ut vel mi elit.",
                "admin", true, true,"ENG", new Date(System.currentTimeMillis()),
                "Lorem ipsum dolor sit amet, consectetur", new SerialBlob(resourceLoader.getResource(pathToConfigImages + "photo6.png").getInputStream().readAllBytes()), new HashSet<>()));
        articleService.saveArticle(new Article("Lorem ipsum 6",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur mollis, ante eget suscipit molestie, dolor dolor suscipit metus, ac pretium dui urna a nisi. Aenean in facilisis nunc. Proin at nulla ultricies, venenatis urna vel, posuere dui. Curabitur sollicitudin tellus id diam scelerisque iaculis. Phasellus nec ante ut erat vestibulum porttitor. Suspendisse bibendum auctor elit, sit amet tincidunt orci placerat eget. Morbi nec nisl vel turpis malesuada molestie eget eget augue. Proin volutpat sapien odio, eu aliquet dui elementum pulvinar. Vestibulum sem dolor, mollis ac porta vitae, tincidunt ut sem. Integer sagittis pellentesque felis. Vestibulum venenatis efficitur neque accumsan maximus. Vivamus et elementum nulla.\n" +
                        "\n" +
                        "Phasellus ornare mauris pulvinar neque sodales, in luctus lectus feugiat. Cras imperdiet metus quis felis condimentum dignissim. Etiam dolor odio, cursus vitae est eu, tempus cursus lorem. Donec tincidunt molestie lorem, id vulputate urna sodales quis. Sed nec mi quis urna porttitor imperdiet. Cras tempus purus nulla, eget luctus quam imperdiet sed. Maecenas sit amet tempor magna. Suspendisse suscipit cursus sapien, vitae tincidunt elit feugiat in. Nullam eget nisl et magna semper vulputate. In nisi nisi, blandit sed elit a, lacinia faucibus felis. Praesent mattis sagittis lorem a maximus.\n" +
                        "\n" +
                        "Morbi viverra neque sed justo vehicula, in venenatis mauris eleifend. Duis id porta libero. Nulla semper mi vel nibh consectetur pharetra. Fusce id accumsan augue. Pellentesque convallis non est maximus ornare. Integer vel lacus at lorem bibendum venenatis nec vitae nunc. Sed mauris tortor, pellentesque vitae nisl ut, imperdiet finibus enim. Integer in imperdiet sapien, vel iaculis metus.",
                "admin", true, true,"ENG", new Date(System.currentTimeMillis()),
                "Lorem ipsum dolor sit amet, consectetur", new SerialBlob(resourceLoader.getResource(pathToConfigImages + "photo4.png").getInputStream().readAllBytes()),  new HashSet<>()));
        articleService.saveArticle(new Article("Lorem ipsum 7",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis quis tellus ac massa accumsan gravida. Donec ullamcorper, risus id pellentesque rhoncus, lorem odio maximus diam, eget consectetur diam nisi nec lectus. Mauris lobortis sit amet tortor non cursus. Nulla hendrerit augue vitae leo feugiat, eget gravida erat lobortis. Phasellus dictum nibh in tellus molestie egestas. Aliquam quis mi mollis, ullamcorper orci ut, mollis ligula. Cras ac felis et ex molestie dapibus. Phasellus et rhoncus ante.\n" +
                        "\n" +
                        "Fusce porttitor velit sit amet magna facilisis placerat. Donec sagittis enim varius velit cursus, vitae semper magna pellentesque. Nam imperdiet arcu et augue rhoncus, sit amet lacinia turpis mollis. Duis at metus augue. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Mauris a ante arcu. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Aliquam non nisi sit amet arcu pharetra ullamcorper a sit amet elit. Phasellus finibus venenatis quam sollicitudin pharetra. Morbi et suscipit nulla. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut quis tincidunt quam. Nunc leo metus, sodales vel lobortis in, venenatis ac quam. Vivamus tincidunt sagittis urna, id luctus eros auctor vel.\n" +
                        "\n" +
                        "Donec nec dolor non neque rutrum fermentum. Lorem ipsum dolor sit amet, consectetur adipiscing elit. In sed neque non lectus semper posuere. Ut nec vulputate mauris. Suspendisse auctor pulvinar risus vel suscipit. Etiam tortor sapien, commodo vitae pretium tempus, sodales vitae nulla. Pellentesque non blandit elit. Integer nec fermentum velit. Suspendisse et turpis eu ante ultricies vestibulum.",
                "admin", true, true,"ENG", new Date(System.currentTimeMillis()),
                "Lorem ipsum dolor sit amet, consectetur", new SerialBlob(resourceLoader.getResource(pathToConfigImages + "photo1.png").getInputStream().readAllBytes()), new HashSet<>()));
        articleService.saveArticle(new Article("Lorem ipsum 8",
                "\n" +
                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam ut nibh at tellus volutpat mollis in sit amet urna. Nunc quis luctus orci, eget eleifend neque. Fusce vitae velit eget nulla vestibulum aliquam ultrices molestie lorem. Mauris eleifend nulla ante, eu interdum sapien placerat eget. Proin dictum lectus semper leo tincidunt finibus. Maecenas suscipit pulvinar nulla. Nunc gravida ex tristique sodales rhoncus. Duis rhoncus odio ante, a placerat nisl egestas ut.\n" +
                        "\n" +
                        "Etiam sapien purus, ultrices vel ex a, efficitur aliquam ante. Ut viverra convallis magna nec cursus. Suspendisse ut fringilla odio, eu molestie neque. Nam dolor libero, porttitor nec justo non, hendrerit luctus dui. Proin condimentum eget orci et feugiat. Quisque congue accumsan tincidunt. Nunc nec dolor quis velit pellentesque viverra vel non nulla. Donec at nisl efficitur magna ultricies sollicitudin id eget orci. Nunc luctus urna id pretium euismod. Pellentesque ultricies, turpis ac mollis tempus, velit libero fermentum tortor, nec hendrerit ipsum neque eu turpis. Aliquam lacinia posuere efficitur. Nullam egestas convallis elit, sit amet accumsan eros sollicitudin sit amet. Donec neque mauris, aliquam luctus congue vitae, iaculis quis ante.\n" +
                        "\n" +
                        "Quisque tincidunt semper quam. Duis gravida, massa eu venenatis dapibus, ante ex tristique sem, ut sodales ligula nulla ut arcu. Sed tempor eros quis eros porta, quis feugiat ex elementum. Maecenas fermentum ligula ut ex fermentum dapibus. Donec enim ante, luctus id condimentum quis, imperdiet in eros. Sed vitae ultrices odio. Donec interdum dignissim ornare. Proin tincidunt convallis hendrerit. Nam hendrerit tincidunt nibh, nec rutrum mi egestas et.",
                "admin", true, true,"ENG", new Date(System.currentTimeMillis()),
                "Lorem ipsum dolor sit amet, consectetur", new HashSet<>()));
    }
}
