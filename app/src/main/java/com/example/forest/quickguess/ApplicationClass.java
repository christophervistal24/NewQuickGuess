package com.example.forest.quickguess;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.forest.quickguess.APIsInterface.APIRanks;
import com.example.forest.quickguess.APIsInterface.APISendStatus;
import com.example.forest.quickguess.DB.Categories.QuestionCategoryRepositories;
import com.example.forest.quickguess.DB.DB;
import com.example.forest.quickguess.DB.Friends.FriendsRepositories;
import com.example.forest.quickguess.DB.Points.Points;
import com.example.forest.quickguess.DB.Points.PointsRepositories;
import com.example.forest.quickguess.DB.Questions.QuestionRepositories;
import com.example.forest.quickguess.DB.UserStatus.UserStatus;
import com.example.forest.quickguess.Helpers.Connectivity;
import com.example.forest.quickguess.Services.WebService.RanksRequest;
import com.example.forest.quickguess.Services.WebService.RanksResponse;
import com.example.forest.quickguess.Services.WebService.RanksService;
import com.example.forest.quickguess.Services.WebService.UserRegisterResponse;
import com.example.forest.quickguess.Services.WebService.UserStatusRequest;
import com.example.forest.quickguess.Services.WebService.UserStatusResponse;
import com.example.forest.quickguess.Services.WebService.UserStatusService;
import com.example.forest.quickguess.Utilities.EncryptUtil;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ApplicationClass extends Application{

    QuestionCategoryRepositories questionCategoryRepositories;
    QuestionRepositories questionRepositories;
    PointsRepositories pointsRepositories;
    FriendsRepositories friendsRepositories;
    Points points;


    @Override
    public void onCreate() {
        super.onCreate();
//        new LongOperation().execute("");
    /*    if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);*/
        questionCategoryRepositories = new QuestionCategoryRepositories(this);
        questionRepositories = new QuestionRepositories(this);
        friendsRepositories = new FriendsRepositories(this);
//        sendUserStatus()
        insertCategories();
        insertQuestions();
        insertUserPoints();
    }


    private void insertUserPoints()
    {
        if (Connectivity.isConnectedWifi(getApplicationContext()))
        {
            pointsRepositories = new PointsRepositories(getApplicationContext());
            points = new Points();
            pointsRepositories.sendPoints();
        }
    }

    private void insertCategories()
    {
        questionCategoryRepositories.categoryCreator("people","All about peoples"); //1
        questionCategoryRepositories.categoryCreator("plants","All about plants"); //2
        questionCategoryRepositories.categoryCreator("animals","All about animals"); //3
        questionCategoryRepositories.categoryCreator("geography","All about places"); //4
        questionCategoryRepositories.categoryCreator("sports","All about sports"); //5
        questionCategoryRepositories.categoryCreator("music","All about music"); //6
        questionCategoryRepositories.categoryCreator("technology","All about technologies"); //7
        questionCategoryRepositories.categoryCreator("entertainment","All about entertainment"); //8
    }

    private void insertQuestions()
    {
        //insert questions
        if (DB.getInstance(this).questionsDao().countQuestion() == 0)
        {
            //all questions
            //Difficult
//            questionRepositories.questionCreator("﻿What is the popular name for the flowering houseplant Impatiens Walleriana?","Busy Lizzie","Sampaguita","Carnation","Rose","Busy Lizzie","Impatiens Walleriana are well known for blooming for the most part of a year which is where the name is derived from \"BUSY\"","busy_lizzie_1.png",2,3);
//
////Difficult
//            questionRepositories.questionCreator("The love apple is the original name for what?","Tomato","Star apple","Pine apple","Bell pepper","Tomato","Tomato has seeds and grows from a flowering plant botanically it is classed as a fruit not a vegetable.","tomato_2.png",2,3);
//
////Difficult
//            questionRepositories.questionCreator("The Death Cap is the most poisonous variety of what?","Mushroom","Fruits","Vines","Flowers","Mushroom","Mushrooms are more closely related in DNA to humans than to plants. Like human skin, mushrooms can produce vitamin D by being exposed to sunlight.","mushroom_3.png",2,3);
//
////Easy
//            questionRepositories.questionCreator("What is the name given to the female reproductive organ of a flower?","Pistil","Stamen","Sepal","Petal","Pistil","The pistil has a bulbous base (the ovary) containing the ovules, which develop into seeds after fertilization of egg cell(s) in the ovule.","pistil_4.png",2,1);
//
////Medium
//            questionRepositories.questionCreator("A Kumquat is a small Japanese variety of what sort of fruit?","Orange","Cherry","Kiwi","Lemon","Orange","The fruit came before the color. The word \"orange\" derives from the Arabic \"naranj\" and arrived in English as \"narange\" in the 14th century, gradually losing the initial \"n.\"","orange_5.png",2,2);
//
////Medium
//            questionRepositories.questionCreator("Harry Wheat croft was a renowned breeder of what?","Roses","Tulip","Cherry  blossoms","Calla lily","Roses","Red rose is a symbol of love, yellow of friendship, orange of enthusiasm, white of purity and pink of joy. Wild roses have 5 petals and 5 sepals. Roses are well known by their prickles.","roses_6.png",2,2);
//
////1 Starfish
////Medium
//            questionRepositories.questionCreator("Which of following creatures has the power to grow lost parts?","Crab","Starfish","Squirrel","Squid","Starfish","Some species of starfish have the ability to regenerate lost arms and can regrow an entire new limb given time. A few can regrow a complete new disc from a single arm, while others need at least part of the central disc to be attached to the detached part.","starfish_1.png",3,2);
//
////2 Duck-billed platypus
////Difficult
//            questionRepositories.questionCreator("Which is the most primitive living mammal?","Seal","Duck-billed platypus","Weasel","Hedgehog","Duck-billed platypus","Egg-laying Mammals There are only five living monotreme species: the duck-billed platypus and four species of echidna (also known as spiny anteaters). In some ways, monotremes are very primitive for mammals because, like reptiles and birds, they lay eggs rather than having live birth.","duck_billed_platypus_2.png",3,3);
//
////3 Krait
////Medium
//            questionRepositories.questionCreator("Which of the following creatures has the most toxic venom?","Kukri snake","Krait","Scorpion","Cobra","Krait","Its venom is highly haemotoxic. The Saw-scaled Viper is responsible for more human deaths in Asia that all the other venomous Asian snakes combined. Its highly haemotoxic venom is said to be 5 times more toxic than cobras and 16 more toxic than the Russell\'s Viper.","krait_3.png",3,2);
//
////4 Blue whale
////Easy
//            questionRepositories.questionCreator("Which animal is the largest in size?","African elephant","Blue whale","Giraffe","Killer whale","Blue whale","Blue whales are the largest animals ever known to have lived on Earth. These magnificent marine mammals rule the oceans at up to 100 feet long and upwards of 200 tons. Their tongues alone can weigh as much as an elephant. Their hearts is as much as an automobile.","blue_whale_4.png",3,1);
//
////5 Sperm whale
////Difficult
//            questionRepositories.questionCreator("Which living being has the heaviest brain?","African elephant","Killer whale","Sea cow","Sperm whale","Sperm whale","The average adult human brain weighs about 3 pounds - the same weight as the average brain of a dolphin (which is also a very intelligent animal). But there are animals with larger brains that are not considered to be as intelligent as a dolphin. For instance, a sperm whale has a brain that weighs about 17 pounds.","sperm_whale_5.png",3,3);
//
////6 Nautilus
////Difficult
//            questionRepositories.questionCreator("Which is the mythical being believed to have become extinct?","Narwhal","Unicorn","Nautilus","Dodo","Nautilus","According to fossil records, animals similar to the chambered nautilus have existed for about 500 million years. Although no regulations currently exist to protect them, the six living species of chambered nautilus appear to be in decline.","nautilus_6.png",3,3);
//
////7 Beaver
////Easy
//            questionRepositories.questionCreator("Which living being is a skilled engineer?","Tailor-bird","Beaver","Termite","Honey bee","Beaver","Beavers have long been recognized as the engineers of the forest, constantly reshaping their surroundings","beaver_7.png",3,1);
//
////8 Bat
////Difficult
//            questionRepositories.questionCreator("Which living being whistle?","Whale","Dolphin","Shark","Bat","Bat","Whistling Like a Bat: Development of an Ultrasonic Whistle to Deter Bats from Wind Turbines. The whistles will produce sounds mimicking the spectrotemporal patterns of bat echolocation pulses, thereby enhancing the bats\' ability to detect, localize and avoid the moving blades","bat_8.png",3,3);
//
////9 Sea otter
////Difficult
//            questionRepositories.questionCreator("Which of the following living being has also been found to be a tool-user?","Sea otter","Gorilla","Beaver","Spider","Sea otter","Sea otters are known for their ability to use stones as anvils or hammers to facilitate access to hard-to-reach prey items","sea_otter_9.png",3,3);
//
////10 Lizards
////Difficult
//            questionRepositories.questionCreator("Which category creatures contain a type that can fly?","Cats","Lizards","Hedgehogs","Rats","Lizards","Draco, also known as flying lizard or flying dragon, is a reptile that belongs to the family Agamidae. There are 31 species of flying lizards that can be found in South and Southeast Asia (Philippines, Borneo, India, Malaysia, Indonesia…). Flying lizards live in tropical rainforests.","lizards_10.png",3,3);
//
////11 Tortoise
////Medium
//            questionRepositories.questionCreator("Which living being has, on an average, the highest life-span?","Tortoise","Man","Pelican","Cat","Tortoise","Tortoises generally have one of the longest lifespans of any animal, and some individuals are known to have lived longer than 150 years. Because of this, they symbolize longevity in some cultures, such as China.","tortoise_11.png",3,2);
//
////12 Africa
////Easy
//            questionRepositories.questionCreator("Where are zebra found?","Africa","South America","China","New Zealand","Africa","Plains zebra are found on the savannas from Sudan to northern Zimbabwe in eastern Africa. Grevy\'s zebras are now mostly restricted to parts of northern Kenya. Mountain zebras occur in southwestern Africa with cape mountain zebras in South Africa and Hartmann\'s mountain zebras in Namibia and Angola.","africa_12.png",3,1);
//
////13 Great Indian rhinoceros
////Difficult
//            questionRepositories.questionCreator("Which of the following animals is on the verge of extinction?","Badger","Kangaroo","Gibbon","Great Indian rhinoceros","Great Indian rhinoceros","There are about 2,600 Indian Rhino left in the wild, but their numbers were less than 200 early in the 20th century. This demise was mainly caused by poaching and habitat loss. There recovery is one of two success stories in rhino conservation, the other being the Southern White Rhino.","great_indian_rhinoceros_13.png",3,3);
//
////14 Asiatic elephant
////Difficult
//            questionRepositories.questionCreator("Which has longest gestation period among mammals?","Giant panda","Pronghorn","Prairie dog","Asiatic elephant","Asiatic elephant","The largest of all land animals have the longest gestation period of all living mammals. Yes, the gestation period of elephants lasts for 18 to 22 months. For an African elephant, the gestation period is 22 months where the Asian elephant\'s is between 18 and 22 months.","asiatic_elephant_14.png",3,3);
//
////15 Domestic dog
////Medium
//            questionRepositories.questionCreator("Which animal can cross-breed with wolf?","Domestic dog","Dhole","Prairie dog","Fox","Domestic dog","Wolves and dogs are interfertile meaning they can breed and produce viable offspring. In other words, wolves can interbreed with any type of dog, and their offspring are capable of producing offspring themselves.","domestic_dog_15.png",3,2);
//
////16 Borneo
////Medium
//            questionRepositories.questionCreator("Where are orangutans found?","Italy","South America","North Africa","Borneo","Borneo","The orangutans are three extant species of great apes native to Indonesia and Malaysia. Orangutans are currently only found in the rainforests of Borneo and Sumatra. Classified in the genus Pongo, orangutans were originally considered to be one species.","borneo_16.png",3,2);
//
////17 Three-toed sloth
////Medium
//            questionRepositories.questionCreator("Which is the slowest moving animal?","Polar bear","Hippopotamus","Elephant seal","Three-toed sloth","Three-toed sloth","The Slowest Mammal in the World. Three-toed sloths are some of the slowest and seemingly laziest creatures in the world. Instead of evolving to eat more, they evolved to do less.","three_toed_sloth_17.png",3,2);
//
////18 Africa
////Easy
//            questionRepositories.questionCreator("Where are chimpanzees found?","Africa","South America","India","Afghanistan","Africa","Together with humans, gorillas, and orangutans they are part of the family Hominidae (the great apes). Native to sub-Saharan Africa, common chimpanzees and bonobos are currently both found in the Congo jungle, while only the common chimpanzee is also found further north in West Africa.","africa_18.png",3,1);
//
////19 Komodo dragon
////Medium
//            questionRepositories.questionCreator("Which is the world largest and rarest lizard?","Chameleon","Komodo dragon","Regal-horned lizard","Garden lizard","Komodo dragon","The Komodo dragon is the largest living lizard in the world. Komodo dragons are limited to a few Indonesian islands of the Lesser Sunda group, including Rintja, Padar and Flores, and of course the island of Komodo, the largest at 22 miles (35 kilometers) long.","komodo_dragon_19.png",3,2);
//
////20 South America
////Difficult
//            questionRepositories.questionCreator("Where are giants anteaters found?","North America","South Africa","South America","Japan","South America","Wild giant anteaters live in grasslands, deciduous forests and rain forests of South and Central America. Though most common in South America, they can be found anywhere from the southern tip of Mexico through Central and South America","south_america_20.png",3,3);
//
////21 Beaver
////Medium
//            questionRepositories.questionCreator("Which of the following animal\'s teeth are strong enough to fell a tree?","Squirrel","Beaver","Chipmunk","Shrew","Beaver","Even without brushing their teeth or drinking fluoridated water, beavers have remarkably strong teeth good for gnawing on wood. A new study shows that their tough teeth are all thanks to a key component built into their chemical structure, and its iron.","beaver_21.png",3,2);
//
////22 Africa
////Difficult
//            questionRepositories.questionCreator("Where are aardvarks found?","Africa","Australia","Denmark","Ireland","Africa","Aardvarks are found in sub-Saharan Africa, where suitable habitat and food is available. They spend the daylight hours in dark underground burrows to avoid the heat of the day.","africa_22.png",3,3);
//
////23 Jerboa
////Difficult
//            questionRepositories.questionCreator("Which rodent is found in remote desert areas, in shifting sand dunes and extreme temperature?","Jerboa","Capybara","Hamster","Murree vole","Jerboa","Jerboas are hopping desert rodents found throughout Arabia, Northern Africa and Asia east to northern China and Manchuria. They tend to live in hot deserts.","jerboa_23.png",3,3);
//
////24 pangolin
////Difficult
//            questionRepositories.questionCreator("Which animal female curls up around its baby to protect it from any attacking animal?","Tortoise","Armadillo","pangolin","hedgehog","pangolin","Pangolin also known as scaly anteaters because of their appearance, Pangolin Female curl up its baby to protect it from enemy","pangolin_24.png",3,3);
//
////25 North America
////Medium
//            questionRepositories.questionCreator("Where are skunks found?","North America","South America","Africa","Europe","North America","Striped skunks are native to North America, and can be found in Northern Mexico, throughout the United States, and as far north as Central Canada. Other species of skunks, such as the spotted skunk and the hog-nosed skunk, can be found further south, ranging from Canada to Central and South America.","north_america_25.png",3,2);
//
////26 Aardvark
////Difficult
//            questionRepositories.questionCreator("Which of the following animals is the fastest burrower?","Mole","Aardvark","Kangaroo rat","Prairie dog","Aardvark","Aardvarks are not fast runners but they can quickly dig a defensive burrow. The aardvark\'s tail is thick and strong and they will use it as a club. Their sharp claws are formidable weapons, and if caught in the open, the aardvark will roll on its back to engage all four feet in the fight.","aardvark_26.png",3,3);
//
////27 Europe
////Difficult
//            questionRepositories.questionCreator("Where are badgers found?","Australia","South Africa","Europe","Iraq","Europe","Badgers are found in much of North America, Ireland, Great Britain and most of the rest of Europe as far north as southern Scandinavia. They live as far east as Japan and China. The Javan ferret-badger lives in Indonesia, and the Bornean ferret-badger lives in Malaysia.","europe_27.png",3,3);
//
////28 Capybara
////Difficult
//            questionRepositories.questionCreator("Which is the largest rodent in the world?","Kangaroo","Capybara","Rabbit","Squirrel","Capybara","The capybara is a mammal native to South America. It is the largest living rodent in the world","capybara_28.png",3,3);
//
////29 Australia
////Easy
//            questionRepositories.questionCreator("Where is koala bears found?","North America","Australia","Africa","South Africa","Australia","Koalas do not live in rainforests or desert areas. They live in the tall eucalypt forests and low eucalypt woodlands of mainland eastern Australia, and on some islands off the southern and eastern coasts. Queensland, NSW, Victoria and South Australia are the only states where Koalas are found naturally in the wild.","australia_29.png",3,1);
//
////30 Passenger pigeon
////Easy
//            questionRepositories.questionCreator("Which bird was once found in abundance but is today extinct species?","Flightless cormorant","Grey heron","Passenger pigeon","Kakapo","Passenger pigeon","Today only about 40 kakapos survive in the wild on two small islands off the coast of New Zealand\'s South Island. It is because of the hunter.","passenger_pigeon_30.png",3,1);
//
////31 South America
////Difficult
//            questionRepositories.questionCreator("Where is rhea found?","North America","China","Ireland","South America","South America","This ratite is the largest bird in the Americas. It is a fast runner; and when it runs, its neck is almost horizontal to the ground. Rheas congregate in flocks of 20 to 30 birds. The lesser or Darwin\'s rhea, Rhea pennata, is mostly found in the southern part of South America","south_america_31.png",3,3);
//
////32 Kiwi
////Difficult
//            questionRepositories.questionCreator("Which bird locates its prey by smell?","Woodpecker","Kiwi","Crow","Stonechat","Kiwi","A kiwi\'s olfactory bulb is the second largest among all birds relative to the size of its forebrain, giving it an exceptional sense of smell, just second to the condor. This helps kiwi locate food beneath the soil and in leaf lite","kiwi_32.png",3,3);
//
////33 Drongo
////Difficult
//            questionRepositories.questionCreator("Which bird mimics the calls and even songs of other birds?","Koel","Eagle","Woodpecker","Drongo","Drongo","The Drongos are able to mimic the sounds made by many different species that inhabit its desert environment. Drongo, seen here in flight, impersonates the calls of other birds in order to steal food","drongo_33.png",3,3);
//
////34 Gentoo penguin
////Easy
//            questionRepositories.questionCreator("Which is the fastest swimming bird?","Gentoo penguin","Adelie penguin","Shelduck","Puffin","Gentoo penguin","The Gentoo penguin (pygoscelis Papua) is the world\'s fastest swimming bird. It can swim between 36-40 km. per hour. They are found in the Antarctic Islands.","gentoo_penguin_34.png",3,1);
//
////35 South America
////Medium
//            questionRepositories.questionCreator("Where is toucan found?","China","Australia","South America","England","South America","Toucans are native to the Neotropics, from Southern Mexico, through Central America, into South America south to northern Argentina. They mostly live in the lowland tropics, but the montane species from the genus Andigena reach temperate climates at high altitudes in the Andes and can be found up to the tree line.","south_america_35.png",3,2);
//
////36 Penguin
////Easy
//            questionRepositories.questionCreator("Which birds has flippers instead of wings?","Owl","Penguin","Goose","Albatross","Penguin","While penguins are the only birds that have true flippers, other pelagic birds that spend a good deal of time swimming also have some flipper-like characteristics to their wings. Puffins, murres and auks all have wings that more closely resemble flippers, but to a lesser degree than penguin wings.","penguin_36.png",3,1);
//
////37 Flamingo
////Difficult
//            questionRepositories.questionCreator("Which bird uses its beak as a filter to gather food from water?","Grey heron","Ibis","Flamingo","Sarus crane","Flamingo","Flamingos are filter feeders. ... Because the flamingo must use its beak in an upside-down manner, the beak has evolved to reflect this. The flamingo\'s top beak functions like the bottom beak of most birds, and vice versa. Flamingos are among the very few animals that are able to move their top jaw while eating","flamingo_37.png",3,3);
//
////38 Nightjar
////Difficult
//            questionRepositories.questionCreator("Which bird keeps its mouth open while flying so that it can catch flying insects?","Vulture","Nightjar","Owl","Crow","Nightjar","Nightjars are very agile in flight, able to hunt and catch aerial insects such as moths in those huge mouths. They also eat beetles, spiders and various other insects. The shape of a bird\'s beak almost always determines the type of food they will eat.","nightjar_38.png",3,3);
//
////39 Skua
////Difficult
//            questionRepositories.questionCreator("Some birds do not catch their own prey. They steal it from another bird. Which bird is it?","Skua","Sparrow","River tem","Bulbul","Skua","Skuas steal much of their food from terns, puffins, and other birds that are carrying fish or other prizes back to their nests and young. The swashbuckling birds sometimes team up to overwhelm their victims, and they are relentless in chasing down their adversaries.","skua_39.png",3,3);
//
////40 Arctic tern
////Difficult
//            questionRepositories.questionCreator("Which holds the record for longest migration?","Arctic tern","Shearwater","River tern","Bar headed geese","Arctic tern","Canada geese fly in a distinctive V-shaped flight formation, with an altitude of 1 km (3,000 feet) for migration flight. The maximum flight ceiling of Canada geese is unknown, but they have been reported at 9 km (29,000 feet)","arctic_tern_40.png",3,3);
//
////41 Andean condor
////Difficult
//            questionRepositories.questionCreator("Which bird can travel very long distances without flapping its wings?","Andean condor","Peregrine falcon","Coot","Eagle","Andean condor","Condors can glide over large areas while using little energy. These huge birds are too heavy to fly without help. They use warm air currents (thermals) to help them gain altitude and soar through the sky. By gliding from thermal to thermal, a condor may need to flap its wings only once every hour","andean_condor_41.png",3,3);
//
////42 Kori bustard
////Difficult
//            questionRepositories.questionCreator("Which is heaviest flying bird?","Ostrich","Kori bustard","Vulture","Condor","Kori bustard","The largest (heaviest) flying bird today is the Kori Bustard (Ardeotis kori) of Africa, males weigh about 18kg, females about half that. The largest bird ever to fly were the Teratorns (a type of Condor), the largest of which, Argentavis magnificens, had a wingspan of 3 metres, and weighed 120kg","kori_bustard_42.png",3,3);
//
////43 Woodpecker
////Medium
//            questionRepositories.questionCreator("Which birds helps in conditioning timber pests?","Indian grey shrike","Woodpecker","Tailor-bird","Hoopoe","Woodpecker","An interesting and familiar group of birds. Their ability to peck into trees in search of food or excavate nest cavities is well known. They prefer snags or partially dead trees for nesting sites, and readily peck holes in trees and wood structures in search of insects beneath the surface","woodpecker_43.png",3,2);
//
////44 Ostrich
////Easy
//            questionRepositories.questionCreator("Which bird has the biggest egg?","Tawny owl","Moorhen","Ostrich","Duck","Ostrich","On record weighed 2.589 kg (5 lb. 11.36 oz) and was laid by an ostrich (Struthio camelus) at a farm owned by Kerstin and Gunnar Sahlin (Sweden) in Borlange, Sweden, on 17 May 2008","ostrich_44.png",3,1);
//
////45 Keratin
////Medium
//            questionRepositories.questionCreator("The horns of a goat are made out of what substance?","Insulin","Amylase","Keratin","Progesterone","Keratin","The main composition of horns is keratin, the same material that makes up hair and fingernails. The core of a horn is, however, made of bone.","keratin_45.png",3,2);
//
////46 Monkey
////Medium
//            questionRepositories.questionCreator("The cotton top of tamarin is a small species of what type of animal?","Bear","Goat","Monkey","Sheep","Monkey","The Cotton top Tamarin (Saguinus oedipus), also known as the Pinche Tamarin, is a small New World monkey weighing less than 1lb (0.5 kg). It is an endangered species found in tropical forest edges and secondary forests where it is arboreal and diurnal.","monkey_46.png",3,2);
//
////47 Australia
////Difficult
//            questionRepositories.questionCreator("The bandicoot is native to which country?","Africa","Australia","North America","Cambodia","Australia","There are also a few rare species such as the rabbit-eared bandicoots. Bandicoots are one of the few native mammals to have remained abundant close to the major cities of Australia. In suburban Sydney it is the long-nosed species that can be seen.","australia_47.png",3,3);
//
////48 Eating algae
////Medium
//            questionRepositories.questionCreator("Some flamingos are color pink because of what?","Eating shrimps","Eating algae","Eating plankton","Eating lotus","Eating algae","Actually, flamingos are not pink. They are born with grey feathers, which gradually turn pink in the wild because of a natural pink dye called canthaxanthin that they obtain from their diet of brine shrimp and blue-green algae.","eating_algae_48.png",3,2);
//
////49 Dog
////Medium
//            questionRepositories.questionCreator("A Finnish Spitz is what type of creature?","Dog","Snake","Cat","Bird","Dog","Finnish Spitz is a breed of dog originating in Finland.","dog_49.png",3,2);
//
////50 Fox
////Difficult
//            questionRepositories.questionCreator("What is the common name for the animal of the genus vulpes.","Fox","Wolf","Owl","Dog","Fox","Vulpes is a genus of the Canidae the members of this genus are colloquially referred to as a true foxes, meaning they form a proper clade.","fox_50.png",3,3);
//
////51 three
////Medium
//            questionRepositories.questionCreator("How many hearts does an octopus have?","eight","one","three","six","three","Octopuses have a closed circulatory system, where the blood remains inside blood vessels. Octopuses have three hearts; a systemic heart that circulates blood round the body and two branchial hearts that pump it through each of the two gills","three_51.png",3,2);
//
////52 Black
////Medium
//            questionRepositories.questionCreator("What is the color of a polar bears skin?","Black","Pink","White","Brown","Black","The bear\'s stark white coat provides camouflage in surrounding snow and ice. But under their fur, polar bears have black skin the better to soak in the sun\'s warming rays. These powerful predators typically prey on seals.","black_52.png",3,2);
//
////53 Pig
////Medium
//            questionRepositories.questionCreator("Welsh and Tamworth are breeds of which animal?","Pig","Cow","Sheep","Dog","Pig","Tamworth is a breed of a domestic pig originating in its namesake Tamworth; it is among the oldest of pig breeds.","pig_53.png",3,2);
//
////54 twenty one mph
////Medium
//            questionRepositories.questionCreator("What is the top speed of a bottle nosed dolphin?","twenty one mph","twenty six mph","thirty one mph","fourty mph","twenty one mph","Bottlenose Dolphins typically swim at a speed of 5-11 kilometers per hour (3-6 mph); for short times, they can reach peak speeds of 35 kilometers per hour (21 mph).","twenty_one_mph_54.png",3,2);
//
////55 Cat
////Easy
//            questionRepositories.questionCreator("What kind of animal is Egyptian Mau?","Dog","Cat","Snake","Camel","Cat","This, along with the well-known chattiness of the Egyptian Mau, makes them popular pets today. As the Egyptian Mau is the only naturally spotted breed of domestic cat, the Egyptian Mau is often bred with other domestic cats in order to produce slightly spotted kittens","cat_55.png",3,1);
//
////56 Mammal
////Difficult
//            questionRepositories.questionCreator("What type of animal is cuscus?","Bird","Mammal","Reptile","Amphibian","Mammal","The cuscus is a large marsupial native to the Northern forest of Australia and the large, tropical island of Papua New Guinea. The cuscus is a subspecies of possum with the cuscus being the largest of the world\'s possum species. The cuscus is an arboreal mammal and spends its life almost exclusively in the trees.","mammal_56.png",3,3);
//
////57 two hundred fifty
////Medium
//            questionRepositories.questionCreator("How many species of bumble bees are there around the world?","twenty-five","one hundred fifty","two hundred fifty","five hundred","two hundred fifty","There are about 25 British species according to Prys-Jones (19 species of Bombus and 6 species of cuckoo bumblebees). And about 65 species in Europe, and 250 species of bumblebee have been discovered so far worldwide","two_hundred_fifty_57.png",3,2);
//
////58 Anaconda
////Easy
//            questionRepositories.questionCreator("Which snake is a member of family boa and sometimes called \'water boa\'?","Cobra","Python","Rattle snake","Anaconda","Anaconda","There are about 25 British species according to Prys-Jones (19 species of Bombus and 6 species of cuckoo bumblebees). And about 65 species in Europe, and 250 species of bumblebee have been discovered so far worldwide","anaconda_58.png",3,1);
//
////59 Giraffe
////Easy
//            questionRepositories.questionCreator("Which animal do not make any sound?","Koala","Panda","Giraffe","Bear","Giraffe","Science Says This Is the Sound Giraffes Make. It\'s long been assumed that unlike other animals, giraffes are largely silent beasts. They don\'t oink, moo or roar. But new research suggests perhaps giraffes do have a distinct sound: They hum.","giraffe_59.png",3,1);
//
////60 Jelly fish
////Easy
//            questionRepositories.questionCreator("Which of the following has no skeleton at all?","Star fish","Sponge","Jelly fish","Silver fish","Jelly fish","A jellyfish has no ears or eyes or nose and no brain or heart! They do not even have a head. Their body is almost totally made of water and is soft having no bones at all. Jellyfish are invertebrate animals because they do not have a spine or backbone.","jelly_fish_60.png",3,1);
//
////61 Humpback whale
////Medium
//            questionRepositories.questionCreator("Which animal can create the loudest sound among any living creature?","Whale shark","Gibbon","Humpback whale","Howler monkey","Humpback whale","Produce one hundred eighty eight decibels of sound. That\'s louder than a one hundred fifty decibel rock concert, which can damage hearing.","humpback_whale_61.png",3,2);
//
////62 Sucker fish
////Medium
//            questionRepositories.questionCreator("Which of the following is not a true fish?","Silver fish","Saw fish","Hammer fish","Sucker fish","Sucker fish","Sawfishes, also known as carpenter sharks, are a family of rays characterized by a long, narrow, flattened rostrum, or nose extension, lined with sharp transverse teeth, arranged in a way that resembles a saw","sucker_fish_62.png",3,2);
//
////63 Honeybee
////Difficult
//            questionRepositories.questionCreator("In which of the following kinds of organism is the phenomenon found wherein the female kills the male after copulation?","Dragonfly","Honeybee","Spider","Pit viper","Honeybee","The next male honey bee to mate with the queen will remove the previous endophallus and eventually lose his own after ejaculation. Male honey bees are only able to mate seven to 10 times during a mating flight, and after mating, a drone dies quickly, as his abdomen rips open when his endophallus is removed","honeybee_63.png",3,3);
//
////64 Goat
////Medium
//            questionRepositories.questionCreator("Pashmina shawl is made from hair of?","Sheep","Goat","Rabbit","Yak","Goat","Pashmina refers to a type of cashmere wool and textiles made from it. The name comes from Pashmineh, made from Persian pashm (= \"wool\"). This wool comes from changthangi or pashmina goat—a special breed of goat indigenous to high altitudes of the Himalayas","goat_64.png",3,2);
//
////65 Frog
////Difficult
//            questionRepositories.questionCreator("In which of the following animals is respiration done by skin?","Flying fish","Sea horse","Frog","Chameleon","Frog","Though they have functional lungs, much of a frog\'s respiration occurs through the skin. A frog\'s moist skin is thin and marbled with blood vessels and capillaries close to the surface. The moisture on the skin dissolves oxygen from the air and water surrounding the frog and transmits it into the blood","frog_65.png",3,3);
//
////66 Dolphin
////Medium
//            questionRepositories.questionCreator("Which of the following is the National aquatic animal of India?","Sea turtle","Shark","Dolphin","Dugong","Dolphin","River Dolphin is the National Aquatic Animal of India. This mammal is also said to represent the purity of the holy Ganga as it can only survive in pure and fresh water. Platanista gangetica has a long pointed snout and also have visible teeth in both the upper and lower jaws.","dolphin_66.png",3,2);
//
////67 Catfish
////Medium
//            questionRepositories.questionCreator("The fish that can taste with its whole body is the?","Clown fish","Jelly fish","Catfish","Flying fish","Catfish","That\'s because this creature has taste buds not only in its mouth, but all over its body. Catfish (order Siluriformes), those beady-eyed fish named for their feline-like whiskers; typically have more than 100,000 taste buds. Some large catfish can have as many as 175,000.","catfish_67.png",3,2);
//
////68 Tibet
////Difficult
//            questionRepositories.questionCreator("The country in which Yak is found?","Tibet","Africa","South America","Indian","Tibet","The domestic yak (Bos grunniens) is a long-haired domesticated bovid found throughout the Himalayan region of the Indian subcontinent, the Tibetan Plateau and as far north as Mongolia and Russia. It is descended from the wild yak (Bos mutus)","tibet_68.png",3,3);
//
////69 six
////Easy
//            questionRepositories.questionCreator("How many legs does a butterfly have?","two","four","six","eight","six","The scales, which are arranged in colorful designs unique to each species, are what give the butterfly its beauty. Like all other insects, butterflies have six legs and three main body parts: head, thorax (chest or mid-section) and abdomen (tail end). They also have two antennae and an exoskeleton","six_69.png",3,1);
//
////70 Haryana
////Difficult
//            questionRepositories.questionCreator("The Bhindawas bird sanctuary is located in which country?","Madhya Pradesh","Bihar","Odisha","Haryana","Haryana","The Bhindawas Bird Sanctuary (BBS) is located in Jhajjar district, Haryana.","haryana_70.png",3,3);
//
////71 Mammal
////Easy
//            questionRepositories.questionCreator("The dolphin is?","Fish","Reptile","Mammal","Turtle","Mammal","Like every mammal, dolphins are warm blooded. Unlike fish, who breathe through gills, dolphins breathe air using lungs. Dolphins must make frequent trips to the surface of the water to catch a breath.","mammal_71.png",3,1);
//
////72 Owl
////Easy
//            questionRepositories.questionCreator("Which bird can turn head around to look backward?","Toucan","Parrot","Owl","Eagle","Owl","A tawny owl turning its head far around its neck, Owls don\'t need eyes in the back of their heads to see what\'s behind them - they can just swivel their heads all the way around","owl_72.png",3,1);
//
////73 Wild dog
////Medium
//            questionRepositories.questionCreator("What kind of animal is a dhole?","Wild cat","Wild ass","Wild dog","Wild buffalo","Wild dog","The dhole is a canid native to Central, South and Southeast Asia. Other English names for the species include Asiatic wild dog, Indian wild dog, whistling dog, red dog, and mountain wolf.","wild_dog_73.png",3,2);
//
////74 Hummingbird
////Medium
//            questionRepositories.questionCreator("Which is the only bird that can fly backwards?","Sunbird","Kingfisher","Honey eater","Hummingbird","Hummingbird","Hummingbirds are incredible flyers, with the ruby-throated hummingbird beating its wings 80 times every second, an ability that inspired this blog\'s name. These tiny birds can fly forwards, hover, and are the only known birds to fly backwards as well.","hummingbird_74.png",3,2);
//
////75 Sailfish
////Easy
//            questionRepositories.questionCreator("Which is the fastest swimming fish?","Dolphin","Sailfish","Catfish","Eel","Sailfish","Not all experts agree, but at top speeds of nearly 70 mph, the sailfish is widely considered the fastest fish in the ocean. Clocked at speeds in excess of 68 mph, some experts consider the sailfish the fastest fish in the world ocean.","sailfish_75.png",3,1);
//
////76 Glass snake
////Medium
//            questionRepositories.questionCreator("Which one of the following is not a true snake?","Blind snake","Glass snake","Sea snake","Tree snake","Glass snake","The glass lizards or glass snakes are a genus, Ophisaurus, of reptiles that resemble snakes, but are actually lizards. Although most species have no legs, their head shapes, movable eyelids, and external ear openings identify them as lizards.","glass_snake_76.png",3,2);
//
////77 New Zealand
////Medium
//            questionRepositories.questionCreator("Kiwi is the native bird of which of the following countries?","South America","New Zealand","Australia","Zimbabwe","New Zealand","There are two species of Kiwis in New Zealand. Brown Kiwis are found in forested areas in the North Island, Fiord land, South Westland and Stewart Island. Spotted Kiwis are found on offshore islands and forests in the North of the South Island","new_zealand_77.png",3,2);
//
////78 Cheetah
////Easy
//            questionRepositories.questionCreator("What is the fastest land animal in the world?","Kangaroo","Cheetah","Wolf","Dear","Cheetah","These graceful animals are identified by their unique black spots on gold or yellow coats and are known for their amazing speed. In fact, according to the Smithsonian National Zoological Park, the cheetah is the world\'s fastest land mammal. A sprinting cheetah can reach 45 mph (72 km/h) within 2.5 seconds","cheetah_78.png",3,1);
//
////79 Female deer
////Easy
//            questionRepositories.questionCreator("A \'doe\' is what kind of animal?","Female yak","Female deer","Female bear","Female camel","Female deer","Deer are a group of even-toed ungulate mammals. They form the family Cervidae. A male deer is called stag or buck, a female deer is called doe, and a young deer is called fawn. There are about 60 species of deer.","female_deer_79.png",3,1);
//
////80 Africa
////Easy
//            questionRepositories.questionCreator("What is the only continent on earth where Giraffes live in the wild?","North pole","Africa","Australia","South America","Africa","Giraffes can be found in central, eastern and southern Africa. Giraffes live in the savannas of Africa, where they roam freely among the tall trees, arid land, dense forests and open plains.","africa_80.png",3,1);
//
////81 eight
////Easy
//            questionRepositories.questionCreator("How many legs does a spider have?","seven","nine","ten","eight","eight","Spiders (order Araneae) are air-breathing arthropods that have eight legs and chelicerae with fangs that inject venom. They are the largest order of arachnids and rank seventh in total species diversity among all other orders of organisms.","eight_81.png",3,1);
//
////82 Lion
////Easy
//            questionRepositories.questionCreator("What animal is known as the king of the jungle?","Gorilla","Tiger","Elephant","Lion","Lion","Lion is known to be the king of the beast (\"king of the jungle\" would be a mismoner) across most cultures of the world. This is mostly because of lion\'s appearance and partly because of the social structure of a pride and the lion\'s role in the place.","lion_82.png",3,1);
//
////83 Dingo
////Difficult
//            questionRepositories.questionCreator("What is largest predator found on the land of Australia?","Dingo","Coyote","Gray wolf","Australian Cattle dog","Dingo","The Dingo is the largest terrestrial predator in Australia.","dingo_83.png",3,3);
//
////84 sixty thousand
////Medium
//            questionRepositories.questionCreator("The Asian Elephants trunk contains up to how many muscles?","six hundred","six thousand","sixty thousand","ten thousand","sixty thousand","The trunk contains as many as 60,000 muscles, which consist of longitudinal and radiating sets. The longitudinal are mostly superficial and subdivided into anterior, lateral, and posterior","sixty_thousand_84.png",3,2);
//
////85 Dog
////Medium
//            questionRepositories.questionCreator("The Bichon Frise is a breed of what?","Dog","Cow","Sheep","Goat","Dog","The Bichon Frise is a very cheerful breed, with a happy, sometimes clownish disposition, accented by its tail curled up high on its rump. They were very much in vogue in sixteenth-century France; they displaced the Maltese in court as the favorite.","dog_85.png",3,2);
//
////86 Bird
////Difficult
//            questionRepositories.questionCreator("What type of animal is an Avocet?","Bird","Reptile","Mammal","Amphibian","Bird","The avocet is a type of wading bird that is found across mudflats in the world\'s warmer climates. There are four different species of avocet which are the pied avocet, the American avocet, the Red-necked avocet and the Andean avocet","bird_86.png",3,3);
//
////87 Emperor penguin
////Easy
//            questionRepositories.questionCreator("Which is the largest type of penguin?","Emperor penguin","King penguin","Crested penguin","Gentoo penguin","Emperor penguin","The emperor penguin (Aptenodytes forsteri) is the tallest and heaviest of all living penguin species and is endemic to Antarctica. The male and female are similar in plumage and size, reaching 122 cm (48 in) in height and weighing from 22 to 45 kg (49 to 99 lb)","emperor_penguin_87.png",3,1);
//
////88 Ground pig
////Difficult
//            questionRepositories.questionCreator("Aardvark is South Africa\'s Afrikaans language which means what?","Land animal","Sea mammal","Ground pig","Monkey eating","Ground pig","Aardvark is the first word in your English dictionary; however the name aardvark is not even English! It comes from South Africa\'s Afrikaans language and means \'earth pig\' or \'ground pig\'.","ground_pig_88.png",3,3);
//
////89 five minutes
////Medium
//            questionRepositories.questionCreator("How long can a hippo hold their breath in the water?","ten minutes","five minutes","one minute","fifteen minutes","five minutes","Their nostrils close, and they can hold their breath for 5 minutes or longer when submerged. Hippo can even underwater, using a reflex that allows them to bob up, take a breath, and sink and back down without waking up.","five_minutes_89.png",3,2);
//
////90 Sperm whale
////Easy
//            questionRepositories.questionCreator("What mammal can hold their breath in a longest time?","Sperm whale","Dolphin","Blue whale","Human","Sperm whale","Sperm whale makes some of the longest dives achieved by mammals, with some lasting up to 90 minutes, while dolphins and other whales can stay underwater for 20 minutes. The longest time a human has held their breath for under water is 19 minutes set by a Swiss free diver called Peter Colat.","sperm_whale_90.png",3,1);
//
////91 Bee hummingbird
////Easy
//            questionRepositories.questionCreator("Which is the smallest living bird?","Bee hummingbird","Sparrow","Pigeon","Parrot","Bee hummingbird","Bee Hummingbirds (Mellisuga helenae) are only 5 to 6 cm long and weigh just 1.6 to 1.9 g (a small coin such as a U.S. penny weighs around 2.5 to 3 g). The male Bee Hummingbird is the smallest of all birds and can easily be mistaken for a bee","bee_hummingbird_91.png",3,1);
//
////92 Airedale
////Difficult
//            questionRepositories.questionCreator("What is the largest terrier?","Scottish","Bull","Boston","Airedale","Airedale","Airedale it is traditionally called \"King of Terriers\" because it is the largest of the terrier breeds. The Airedale was bred from the old English black and tan terrier (now extinct).","airedale_92.png",3,3);
//
////93 King cobra
////Easy
//            questionRepositories.questionCreator("What is the world\'s longest poisonous snake?","Tiger snake","Boomslang","King cobra","Eastern brown snake","King cobra","Today\'s longest venomous snakes, king cobra (ophiophagus Hannah), can grow to be about 18 feet (5.5m) long.","king_cobra_93.png",3,1);
//
////94 New Zealand
////Difficult
//            questionRepositories.questionCreator("Where would find a tuatara?","Africa","New Zealand","Sweden","Australia","New Zealand","This New Zealand native has unique, ancient lineage that goes back to the time of the dinosaurs.","new_zealand_94.png",3,3);
//
////95 Colony
////Easy
//            questionRepositories.questionCreator("What is a group of bat called?","Colony","Comrade","Companion","Group bat","Colony","A group of bats is called a colony of bats. Within one colony of bats, there may be 10 to 1,000 bats or more. Bats tend to stick together in order to protect each other, to mate and to gather food.","colony_95.png",3,1);
//
////96 Dolphin
////Easy
//            questionRepositories.questionCreator("Which animal has the widest hearing range?","Lizard","Dolphin","Hyena","Lion","Dolphin","Bottlenose dolphins hear tones with a frequency up to 160 kHz with the greatest sensitivity ranging from 40 to 100 kHz. The average hearing for humans is about 0.02 to 20 kHz","dolphin_96.png",3,1);
//
////97 Male horse
////Easy
//            questionRepositories.questionCreator("What kind of animal is a stallion?","Male deer","Male horse","Male zebra","Male donkey","Male horse","Male horse is called a stallion. A stallion used for breeding is known as a stud. Formerly, stallion was employed as riding horses.","male_horse_97.png",3,1);
//
////98 four
////Medium
//            questionRepositories.questionCreator("How many different types of hyenas are there?","six","ten","five","four","four","There are 4 known species of hyena, the spotted hyena, the striped hyena, the brown hyena and the aardwolf.","four_98.png",3,2);
//
////99 Madagascar
////Easy
//            questionRepositories.questionCreator("In which country are lemurs found in nature?","Madagascar","U.S.A","Finland","New Zealand","Madagascar","Today the black lemur is an endangered species and is found only in a small area on Madagascar and on two small islands off its northwest coast. On one island they have the benefit of a reserve of natural forest.","madagascar_99.png",3,1);
//
////100 Earthworms
////Easy
//            questionRepositories.questionCreator("What is the main diet of a mole?","Beetle","Dragonfly","Ants","Earthworms","Earthworms","It is a misconception that moles burrow into gardens to eat the roots of plants. They are actually after the earthworms that are found in garden soil. Moles love earthworms so much that they eat nearly their body weight worth of earthworms per day. Moles also consume insect larvae.","earthworms_100.png",3,1);
//
//            //TODO GEOGRAPHY
//            //1 Ljubljana
////Difficult
//            questionRepositories.questionCreator("What is the capital city of Slovenia?","Bucharest","Ljubljana","Kiev","Madrid","Ljubljana","There are 7 towns in Slovenia. According to the Local Self-Government Act of the Republic of Slovenia, a town is a larger urban settlement with more than 3,000 residents and differing from other settlements in its size, economical structure, population, population density and historical development","ljubljana_1.png",4,3);
//
////2 Madrid
////Easy
//            questionRepositories.questionCreator("What is the capital city of Spain?","Madrid","Barcelona","Seville","Helsinki ","Madrid","Spain is a sovereign state mostly located on the Iberian Peninsula in Europe. ","madrid_2.png",4,1);
//
////3 Stockholm
////Difficult
//            questionRepositories.questionCreator("What is the capital city of Sweden?","Stockholm","Helsinki","Oslo","Vienna","Stockholm","Sweden is a Scandinavian nation with thousands of coastal islands and inland lakes, along with vast boreal forests and glaciated mountains.","stockholm_3.png",4,3);
//
////4 Bucharest
////Difficult
//            questionRepositories.questionCreator("What is the capital city of Romania?","Bucharest","Budapest","Vienna ","Muscat","Bucharest","Romania is a southeastern European country known for the forested region of Transylvania, ringed by the Carpathian Mountains.","bucharest_4.png",4,3);
//
////5 Budapest
////Difficult
//            questionRepositories.questionCreator("What is the capital city of Hungary?","Bucharest","Prague ","Budapest","Oslo","Budapest","The Budapest metropolitan area had a GDP of $141.0 billion (€129.4 billion) in 2016[citation needed], accounting for 49.6 percent of the GDP of Hungary. GDP per capita in the city is $64,283, which means 148% of the EU average measured on purchasing power parity. Thereby the city is among the top 100 GDP performing cities in the world","budapest_5.png",4,3);
//
////6 Berlin
////Difficult
//            questionRepositories.questionCreator("What is the capital city of Germany?","Berlin","Bonn","Munich","Muscat","Berlin","Germany is a Western European country with a landscape of forests, rivers, mountain ranges and North Sea beaches.","berlin_6.png",4,3);
//
////7 Riga
////Difficult
//            questionRepositories.questionCreator("What is the capital city of Latvia?","Riga","Tallinn","Vilnius ","Bonn","Riga","Latvia is a country on the Baltic Sea between Lithuania and Estonia. Its landscape is marked by wide beaches as well as dense, sprawling forests.","riga_7.png",4,3);
//
////8 Sofia
////Difficult
//            questionRepositories.questionCreator("What is the capital city of Bulgaria?","Tirana ","Berlin","Sofia","Prague","Sofia","Bulgaria is a Balkan nation with diverse terrain encompassing Black Sea coastline, a mountainous interior and rivers, including the Danube.","sofia_8.png",4,3);
//
////9 Jordan
////Medium
//            questionRepositories.questionCreator("Petra is located in which country?","Iran","Jordan","Lebanon","Iraq","Jordan","Jordan, an Arab nation on the east bank of the Jordan River, is defined by ancient monuments, nature reserves and seaside resorts. Petra earns its nickname, the \"Rose City.\"","jordan_9.png",4,2);
//
////10 India
////Easy
//            questionRepositories.questionCreator("Taj Mahal is located in which country?","India","Pakistan","Indonesia","Singapore","India","India is a vast South Asian country with diverse terrain – from Himalayan peaks to Indian Ocean coastline – and history reaching back 5 millennia.","india_10.png",4,1);
//
////11 Mexico
////Difficult
//            questionRepositories.questionCreator("Chichen Itza is located in which country?","Mexico","United States","Chile","Argentina","Mexico","Mexico is a country between the U.S. and Central America that\'s known for its Pacific and Gulf of Mexico beaches and its diverse landscape of mountains, deserts and jungles.","mexico_11.png",4,3);
//
////12 Italy
////Medium
//            questionRepositories.questionCreator("The Colosseum is located in which  country?","Italy","France","Norway","Germany","Italy","Italy, a European country with a long Mediterranean coastline, has left a powerful mark on Western culture and cuisine.","italy_12.png",4,2);
//
////13 Brazil
////Difficult
//            questionRepositories.questionCreator("Christ The Redeemer Statue on Corcovado Mountain is located in which country?","China","Canada","Norway","Brazil","Brazil","Christ the Redeemer is an Art Deco statue of Jesus Christ in Rio de Janeiro, Brazil, created by French sculptor Paul Landowski and built by Brazilian engineer Heitor da Silva Costa, in collaboration with French engineer Albert Caquot. ","brazil_13.png",4,3);
//
////14 Kabul
////Difficult
//            questionRepositories.questionCreator("What is the capital city of Afghanistan?","Tirana","Kabul","Luanda","Yerevan","Kabul","Afghanistan, officially the Islamic Republic of Afghanistan, is a landlocked country located within South Asia and Central Asia.","kabul_14.png",4,3);
//
////15 Algiers
////Difficult
//            questionRepositories.questionCreator("What is the capital city of Algeria?","Baku","Vienna","Algiers","Canberra","Algiers","Algeria is a North African country with a Mediterranean coastline and a Saharan desert interior.","algiers_15.png",4,3);
//
////16 Bangladesh
////Difficult
//            questionRepositories.questionCreator("What country has a capital of Dhaka?","Bangladesh","Barbados","Belarus","Belgium","Bangladesh","Bangladesh, to the east of India on the Bay of Bengal, is a South Asian country marked by lush greenery and many waterways.","bangladesh_16.png",4,3);
//
////17 Cambodia
////Difficult
//            questionRepositories.questionCreator("What country has a capital of Phnom Penh?","Canada","Cape Verde","Cambodia","Cameroon","Cambodia","Cambodia is a Southeast Asian nation whose landscape spans low-lying plains, the Mekong Delta, mountains and Gulf of Thailand coastline.","cambodia_17.png",4,3);
//
////18 Cape Verde
////Difficult
//            questionRepositories.questionCreator("What country has a capital of Praia?","Central African Republic","Chad","Canada","Cape Verde","Cape Verde","Cape Verde, or Cabo Verde, is a nation on a volcanic archipelago off the northwest coast of Africa.","cape_verde_18.png",4,3);
//
////19 Chile
////Difficult
//            questionRepositories.questionCreator("What country has a capital of Santiago?","Comoros","Colombia","China","Chile","Chile","Chile is a long, narrow country stretching along South America\'s western edge, with more than ,000km of Pacific Ocean coastline.","chile_19.png",4,3);
//
////20 Colombia
////Difficult
//            questionRepositories.questionCreator("What country has a capital of Bogota?","Colombia","Comoros","Costa Rica","Croatia","Colombia","Colombia is a country at the northern tip of South America. It\'s landscape is marked by rainforests, Andes mountains and numerous coffee plantations.","colombia_20.png",4,3);
//
////21 Belgium
////Difficult
//            questionRepositories.questionCreator("What country has a capital of Brussels?","Belarus","Burundi","Brazil","Belgium","Belgium","Belgium, a country in Western Europe, is known for medieval towns, Renaissance architecture and as headquarters of the European Union and NATO.","belgium_21.png",4,3);
//
////22 Egypt
////Difficult
//            questionRepositories.questionCreator("What country has a capital of Cairo?","Eritrea","Ethiopia","Egypt","Estonia","Egypt","Egypt, a country linking northeast Africa with the Middle East, dates to the time of the pharaohs. The capital, Cairo, is home to Ottoman landmarks like Muhammad Ali Mosque and the Egyptian Museum, a trove of antiquities.","egypt_22.png",4,3);
//
////23 France
////Medium
//            questionRepositories.questionCreator("What country has a capital of Paris?","Georgia","France","Fiji","Finland","France","France, in Western Europe, encompasses medieval cities, alpine villages and Mediterranean beaches. Paris, its capital, is famed for its fashion houses, classical art museums including the Louvre and monuments like the Eiffel Tower.","france_23.png",4,2);
//
////24 Korea, South
////Easy
//            questionRepositories.questionCreator("What country has a capital of Seoul?","Kosovo","Kenya","Korea, North","Korea, South","Korea, South","South Korea, an East Asian nation on the southern half of the Korean Peninsula, shares one of the world’s most heavily militarized borders with North Korea.","korea_south_24.png",4,1);
//
////25 Korea, North
////Difficult
//            questionRepositories.questionCreator("What country has a capital of Pyongyang?","Kosovo","Kenya","Korea, North","Korea, South","Korea, North","North Korea, officially the Democratic People\'s Republic of Korea, is a country in East Asia constituting the northern part of the Korean Peninsula. Pyongyang is the capital and largest city.","korea_north_25.png",4,3);
//
////26 Maldives
////Difficult
//            questionRepositories.questionCreator("What country has a capital of Malé?","Mali","Malaysia","Malawi","Maldives","Maldives","The Maldives is a tropical nation in the Indian Ocean composed of 2 ring-shaped atolls, which are made up of more than 1,000 coral islands.","maldives_26.png",4,3);
//
////27 New Zealand
////Difficult
//            questionRepositories.questionCreator("What country has a capital of Wellington?","New Zealand","Netherlands","Niger","Nicaragua","New Zealand","New Zealand is a country in the southwestern Pacific Ocean consisting of 2 main islands, both marked by volcanoes and glaciation.","new_zealand_27.png",4,3);
//
////28 Russia
////Medium
//            questionRepositories.questionCreator("What country has a capital of Moscow?","Republic of the Congo","Rwanda","Russia","Romania","Russia","Russia, the world’s largest nation, borders European and Asian countries as well as the Pacific and Arctic oceans.","russia_28.png",4,2);
//
////29 Turkey
////Difficult
//            questionRepositories.questionCreator("What country has a capital of Ankara?","Tuvalu","Tunisia","Turkmenistan","Turkey","Turkey","Turkey is a nation straddling Eastern Europe and western Asia with cultural connections to ancient Greek, Persian, Roman, Byzantine and Ottoman empires.","turkey_29.png",4,3);
//
////30 South Africa
////Difficult
//            questionRepositories.questionCreator("What country has a capital of Pretoria?","South Sudan","Somalia","South Africa","Solomon Islands","South Africa","South Africa is a country on the southernmost tip of the African continent, marked by several distinct ecosystems.","south_africa_30.png",4,3);
//
////31 Oceania
////Medium
//            questionRepositories.questionCreator("What is the continent of Australia?","America","Europe","Oceania","Asia","Oceania","Oceania is a geographic region comprising Melanesia, Micronesia, Polynesia and Australasia. Spanning the eastern and western hemispheres, Oceania covers an area of 8,525,989 square kilometres and has a population of 40 million.","oceania_31.png",4,2);
//
////32 South America
////Medium
//            questionRepositories.questionCreator("What is the continent of Argentina?","South America","Europe","Oceania","Asia","South America","South America is a continent in the Western Hemisphere, mostly in the Southern Hemisphere, with a relatively small portion in the Northern Hemisphere.","south_america_32.png",4,2);
//
////33 North America
////Medium
//            questionRepositories.questionCreator("What is the continent of Costa Rica?","North America","Europe","Oceania","Asia","North America","North America is a continent entirely within the Northern Hemisphere and almost all within the Western Hemisphere; it is also considered by some to be a northern subcontinent of the Americas.","north_america_33.png",4,2);
//
////34 Africa
////Difficult
//            questionRepositories.questionCreator("What is the continent of Comoros?","America","Africa","Oceania","Asia","Africa","Africa is the world\'s second largest and second most-populous continent. At about 30.3 million km² including adjacent islands, it covers % of Earth\'s total surface area and 20% of its land area.","africa_34.png",4,3);
//
////35 Africa
////Difficult
//            questionRepositories.questionCreator("What is the continent of Ethiopia?","America","Africa","Oceania","Asia","Africa","Africa is the world\'s second largest and second most-populous continent. At about 30.3 million km² including adjacent islands, it covers % of Earth\'s total surface area and 20% of its land area.","africa_35.png",4,3);
//
////36 Asia
////Easy
//            questionRepositories.questionCreator("What is the continent of Israel?","America","Africa","Oceania","Asia","Asia","Asia is Earth\'s largest and most populous continent, located primarily in the Eastern and Northern Hemispheres.","asia_36.png",4,1);
//
////37 Africa
////Medium
//            questionRepositories.questionCreator("What is the continent of Libya?","America","Africa","Oceania","Asia","Africa","Africa is the world\'s second largest and second most-populous continent. At about 30.3 million km² including adjacent islands, it covers % of Earth\'s total surface area and 20% of its land area.","africa_37.png",4,2);
//
////38 Africa
////Difficult
//            questionRepositories.questionCreator("What is the continent of Botswana?","America","Africa","Oceania","Asia","Africa","Republic of Botswana is a landlocked country located in Southern Africa. Formerly the British protectorate of Bechuanaland, Botswana adopted its new name after becoming independent within the Commonwealth on 30 September 19. ","africa_38.png",4,3);
//
////39 Europe
////Medium
//            questionRepositories.questionCreator("What is the continent of Norway?","America","Africa","Europe","Asia","Europe","Europe is a continent located entirely in the Northern Hemisphere and mostly in the Eastern Hemisphere. It is bordered by the Arctic Ocean to the north, the Atlantic Ocean to the west and the Mediterranean Sea to the south.","europe_39.png",4,2);
//
////40 Asia
////Medium
//            questionRepositories.questionCreator("What is the continent of Pakistan?","America","Africa","Oceania","Asia","Asia","Pakistan, officially the Islamic Republic of Pakistan, is a country in South Asia. It is the fifth-most populous country with a population exceeding 212,742,31 people.","asia_40.png",4,2);
//
////41 Europe
////Medium
//            questionRepositories.questionCreator("What is the continent of Romania?","America","Africa","Oceania","Europe","Europe","Europe is a continent located entirely in the Northern Hemisphere and mostly in the Eastern Hemisphere. It is bordered by the Arctic Ocean to the north, the Atlantic Ocean to the west and the Mediterranean Sea to the south.","europe_41.png",4,2);
//
////42 Asia
////Medium
//            questionRepositories.questionCreator("What is the continent of Qatar?","America","Africa","Oceania","Asia","Asia","Qatar is a peninsular Arab country whose terrain comprises arid desert and a long Persian (Arab) Gulf shoreline of beaches and dunes.","asia_42.png",4,2);
//
////43 Europe
////Medium
//            questionRepositories.questionCreator("What is the continent of Portugal?","America","Africa","Oceania","Europe","Europe","Portugal is a southern European country on the Iberian Peninsula, bordering Spain.","europe_43.png",4,2);
//
////44 Oceania
////Difficult
//            questionRepositories.questionCreator("What is the continent of Palau?","America","Africa","Oceania","Asia","Oceania","Palau is an archipelago of over 500 islands, part of the Micronesia region in the western Pacific Ocean. Koror Island is home to the former capital, also named Koror, and is the islands’ commercial center. ","oceania_44.png",4,3);
//
////45 Asia
////Medium
//            questionRepositories.questionCreator("What is the continent of Myanmar?","America","Africa","Oceania","Asia","Asia","Myanmar (formerly Burma) is a Southeast Asian nation of more than 100 ethnic groups, bordering India, Bangladesh, China, Laos and Thailand.","asia_45.png",4,2);
//
////46 Europe
////Medium
//            questionRepositories.questionCreator("What is the continent of Netherlands?","America","Africa","Oceania","Europe","Europe","The Netherlands, a country in northwestern Europe, is known for a flat landscape of canals, tulip fields, windmills and cycling routes.","europe_46.png",4,2);
//
////47 North America
////Medium
//            questionRepositories.questionCreator("What is the continent of Jamaica?","North America","Africa","Oceania","Asia","North America","Jamaica, a Caribbean island nation, has a lush topography of mountains, rainforests and reef-lined beaches.","north_america_47.png",4,2);
//
////48 Europe
////Difficult
//            questionRepositories.questionCreator("What is the continent of Ireland?","America","Africa","Oceania","Europe","Europe","Ireland is an island in the North Atlantic. It is separated from Great Britain to its east by the North Channel, the Irish Sea, and St George\'s Channel. Ireland is the third-largest island in Europe.","europe_48.png",4,3);
//
////49 North America
////Difficult
//            questionRepositories.questionCreator("What is the continent of Honduras?","North America","Africa","Oceania","Asia","North America","Honduras is a Central American country with Caribbean Sea coastlines to the north and the Pacific Ocean to the south.","north_america_49.png",4,3);
//
////50 Turkey
////Difficult
//            questionRepositories.questionCreator("Which country is in 2 continents?","Japan","Egypt","Turkey","Russia","Turkey","The larger portion of Turkey is located in Western Asia while the remaining portion is located in South Eastern Europe. The city of Istanbul lies on both sides of one of the Turkish Strait (Bosporus) hence making it a transcontinental city. Canakkale City in Turkey also situated in two continents, Europe and Asia.","turkey_50.png",4,3);
//
////51 Asia
////Easy
//            questionRepositories.questionCreator("The largest continent is?","North America","Africa","Australia","Asia","Asia","Asia spans 17,139,445 square miles – 29.1 percent of Earth\'s total land mass — and has a population of 4.1 billion people.","asia_51.png",4,1);
//
////52 Australia
////Easy
//            questionRepositories.questionCreator("The smallest continent is?","North America","Australia","Oceania","Asia","Australia","The continent of Australia, sometimes known in technical contexts by the names Sahul, Australinea or Meganesia to distinguish it from the country of Australia, consists of the land masses which sit on Australia\'s continental shelf. Situated in the geographical region of Oceania, it is the smallest of the seven traditional continents in the English conception.","australia_52.png",4,1);
//
////53 Angkor Wat
////Difficult
//            questionRepositories.questionCreator("The largest religious monument in the world","Angkor Watt","St. Peter’s Basilica","Saint Anne in the Vatican","Angkor Wat","Angkor Wat","Angkor wat is originally constructed as a Hindu temple dedicated to the god Vishnu for the Khmer Empire, gradually transforming into a Buddhist temple towards the end of the 12th century.","angkor_wat_53.png",4,3);
//
////54 St.Peter Basilica
////Medium
//            questionRepositories.questionCreator("It is the largest church in the word","Saints Martin and Sebastian of the Swiss","St.Peter Basilica","Saint Anne in the Vatican","Sant'Egidio in Borgo","St.Peter Basilica","St. Peter\'s is the most renowned work of Renaissance architecture and the largest church in the world.  It has been described as \"holding a unique position in the Christian world and as \"the greatest of all churches of Christendom\".","st_peter_basilica_54.png",4,2);
//
////55 Congo River
////Medium
//            questionRepositories.questionCreator("Which of the following rivers is the longest? ","Congo River","Lena River","Niger River","Irtysh River","Congo River","At two-thousand nine-hundred twenty miles, the Congo River is the second longest river in Africa.","congo_river_55.png",4,2);
//
////56 Australia
////Difficult
//            questionRepositories.questionCreator("In the following which country is the death penalty for crime forbidden?","India","The United States","Australia","Belarus","Australia","Capital punishment in Australia has been abolished in all jurisdictions. Queensland abolished the death penalty in 1922. Tasmania did the same in 1968, the federal government abolished the death penalty in 1973, with application also in the Australian Capital Territory and the Northern Territory. Victoria did so in 1975, South Australia in 1976, and Western Australia in 1984. New South Wales abolished the death penalty for murder in 1955, and for all crimes in 1985. In 2010, the federal government passed legislation prohibiting the re-establishment of capital punishment by any state or territory.","australia_56.png",4,3);
//
////57 Russia
////Difficult
//            questionRepositories.questionCreator("In what country are the world’s ten coldest cities located?","Russia","The United States","Chile","Canada","Russia","The enormous size of Russia and the remoteness of many areas from the sea result in the dominance of the humid continental climate, which is prevalent in all parts of the country except for the tundra and the extreme southwest.","russia_57.png",4,3);
//
////58 Sultanate
////Difficult
//            questionRepositories.questionCreator("What kind of government is that of Oman?","Plebiscite","Sultanate","Democracy","Caliphate","Sultanate","Oman is a unitary state and an absolute monarchy, in which all legislative, executive and judiciary power ultimately rests in the hands of the hereditary Sultan. ","sultanate_58.png",4,3);
//
////59 Scotland
////Difficult
//            questionRepositories.questionCreator("In which country you will find the Loch Ness monster?","Scotland","England","Canada","Belgium","Scotland","Scotland, the U.K.’s northernmost country, is a land of mountain wildernesses such as the Cairngorms and Northwest Highlands, interspersed with glacial glens (valleys) and lochs (lakes).","scotland_59.png",4,3);
//
////60 India
////Medium
//            questionRepositories.questionCreator("What country does not use the dollar?","New Zealand","Australia","Canada","India","India","India is a country in South Asia. It is the seventh-largest country by area, the second-most populous country (with over 1.2 billion people), and the most populous democracy in the world","india_60.png",4,2);
//
////61 Canada
////Difficult
//            questionRepositories.questionCreator("What is the world’s second-largest country?","China ","Canada","India","Russia","Canada","Canada is a country located in the northern part of North America. Its ten provinces and three territories extend from the Atlantic to the Pacific and northward into the Arctic Ocean, covering 9.98 million square kilometres (3.85 million square miles), making it the world\'s second-largest country by total area.","canada_61.png",4,3);
//
////62 Maldives
////Difficult
//            questionRepositories.questionCreator("Which of these is an Indian Ocean republic?","Maldives","Malta","Saint Helena","Easter Island","Maldives","Maldives is a country of South Asia, situated in the Indian Ocean, south-southwest of India.","maldives_62.png",4,3);
//
////63 England
////Difficult
//            questionRepositories.questionCreator("In what country does Arsenal play?","Scotland","India","England","Ireland","England","Arsenal was the first club from the South of England to join The Football League, in 1893, and they reached the First Division in 1904.","england_63.png",4,3);
//
////64 Switzerland
////Difficult
//            questionRepositories.questionCreator("What nation is divided into cantons?","Italy","France","Switzerland","Germany","Switzerland","Cantons of Switzerland are the member states of the Swiss Confederation. The nucleus of the Swiss Confederacy in the form of the first three confederate allies used to be referred to as the Waldstätte.","switzerland_64.png",4,3);
//
////65 Nippon
////Difficult
//            questionRepositories.questionCreator("In the following what do Japanese people call Japan?","Honshu","Kyoto","Nippon","Nisshoki","Nippon","Nippon literally mean the sun\'s origin, that is, where the sun originates, and are often translated as the Land of the Rising Sun.","nippon_65.png",4,3);
//
////66 North America
////Difficult
//            questionRepositories.questionCreator("On which continent will you find Guatemala?","Australia","North America","Africa","South America","North America","Guatemala, a Central American country south of Mexico, is home to volcanoes, rainforests and ancient Mayan sites.","north_america_66.png",4,3);
//
////67 North America
////Difficult
//            questionRepositories.questionCreator("Which continent will you find Belize?","South America","North America","Europe","Africa","North America","Belize is a nation on the eastern coast of Central America, with Caribbean Sea shorelines to the east and dense jungle to the west.","north_america_67.png",4,3);
//
////68 North America
////Difficult
//            questionRepositories.questionCreator("On which continent will you find Nicaragua?","South America","Asia","North America","Africa","North America","Nicaragua, set between the Pacific Ocean and the Caribbean Sea, is a Central American nation known for its dramatic terrain of lakes, volcanoes and beaches.","north_america_68.png",4,3);
//
////69 Asia
////Medium
//            questionRepositories.questionCreator("In the following which continent will you find Yemen?","Asia","Australia","Africa","Europe","Asia","Yemen, officially known as the Republic of Yemen, is an Arab sovereign state in Western Asia at the southern end of the Arabian Peninsula.","asia_69.png",4,2);
//
////70 Asia
////Medium
//            questionRepositories.questionCreator("Which continent will you find Turkey?","Europe","Africa","Asia","North America","Asia","Turkey is a nation straddling eastern Europe and western Asia with cultural connections to ancient Greek, Persian, Roman, Byzantine and Ottoman empires. Cosmopolitan Istanbul, on the Bosphorus Strait, is home to the iconic Hagia Sophia, with its soaring dome and Christian mosaics, the massive 17th-century Blue Mosque and the circa-1460 Topkapı Palace, former home of sultans. Ankara is Turkey’s modern capital.","asia_70.png",4,2);
//
////71 Europe
////Medium
//            questionRepositories.questionCreator("In the following which continent will you find Portugal?","Europe","Austalia","North America","South America","Europe","Portugal is a southern European country on the Iberian Peninsula, bordering Spain.","europe_71.png",4,2);
//
////72 United Kingdom of Great Britain and Northern Ireland
////Easy
//            questionRepositories.questionCreator("In the following what’s the full title of the UK?","United Kingdom of Great Britain and Northern Ireland","United Kingdom of England, Wales, Scotland and Northern Ireland","United Kingdom of Great Britain, Scotland and Northern Ireland","United Kingdom of Great Britain and Scotland","United Kingdom of Great Britain and Northern Ireland","The name refers to the union of what were once four separate nations: England, Scotland, Wales and Ireland (though most of Ireland is now independent.","united_kingdom_of_great_britain_and_northern_ireland_72.png",4,1);
//
////73 South America
////Easy
//            questionRepositories.questionCreator("In which continent will you find Brazil?","Africa","Europe","South America","North America","South America","Officially the Federative Republic of Brazil  is the largest country in both South America and Latin America.","south_america_73.png",4,1);
//
////74 Africa
////Difficult
//            questionRepositories.questionCreator("In which of the continent will you find Sierra Leone?","South America","North America","Europe","Africa","Africa","Officially the Republic of Sierra Leone, is a country in West Africa. It is bordered by Guinea to the northeast, Liberia to the southeast and the Atlantic Ocean to the southwest.","africa_74.png",4,3);
//
////75 Europe
////Medium
//            questionRepositories.questionCreator("Which of the following continent you find Malta?","Africa","Europe","South America","North America","Europe","Republic of Malta, is a Southern European island country consisting of an archipelago in the Mediterranean Sea.","europe_75.png",4,2);
//
////76 Asia
////Easy
//            questionRepositories.questionCreator("In which continent you find Laos?","Africa","Europe","Asia","North America","Asia","Laos is a landlocked country in the heart of the Indochinese peninsula of Mainland Southeast Asia, bordered by Myanmar (Burma) and China to the northwest, Vietnam to the east, Cambodia to the southwest, and Thailand to the west and southwest.","asia_76.png",4,1);
//
////77 Africa
////Easy
//            questionRepositories.questionCreator("On which continent will you find Egypt?","Africa","Europe","South America","North America","Africa","Africa is the world's second largest and second most-populous continent. At about 30.3 million km² including adjacent islands, it covers 6% of Earth's total surface area and 20% of its land area. With 1.2 billion people as of 2016, it accounts for about 16% of the world's human population.","africa_77.png",4,1);
//
////78 Asia
////Medium
//            questionRepositories.questionCreator("Which continent will you find Lesotho?","Africa","Europe","South America","Asia","Asia","Kingdom of Lesotho (Sotho: \'Muso oa Lesotho), is an enclaved country in southern Africa. It is just over  30,000 km2 (11,583 sq mi) in size and has a population of around 2 million. Its capital and largest city is Maseru.","asia_78.png",4,2);
//
////79 Brunei
////Medium
//            questionRepositories.questionCreator("In the following which country has “the Abode of Peace” as an official part of its name?","Brunei","Thailand","Bahrain","Saudia Arabia","Brunei","It was renamed \"Barunai\" in the 14th century, possibly influenced by the Sanskrit word \"varu?\" ,  meaning \"seafarers\". The word \"Borneo\" is of the same origin. In the country\'s full name, Negara Brunei Darussalam, darussalam means \"abode of peace\", while negara means \"country\" in Malay.","brunei_79.png",4,2);
//
////80 Aotearoa
////Medium
//            questionRepositories.questionCreator("Which of the following is the Maori name for New Zealand?","Nui","Moana","Aotearoa","Motu","Aotearoa","New Zealand is a sovereign island country in the southwestern Pacific Ocean.","aotearoa_80.png",4,2);
//
////81 United Mexican States
////Easy
//            questionRepositories.questionCreator("In the following what is the official name of Mexico?","Kingdom of Mexico","Greater Republic of Central America","United Mexican States","United Latin States","United Mexican States","The name of México has several hypotheses that entail the origin, history, and use of the name México, which dates back to 14th century Mesoamerica.","united_mexican_states_81.png",4,1);
//
////82 Democratic People Republic of Korea
////Easy
//            questionRepositories.questionCreator("In the following what is the official name of North Korea?","Republic of Korea","Democratic People Republic of Korea","Communist People’s Republic of Korea","Supreme Republic of Korea","Democratic People Republic of Korea","North Korea, officially the Democratic People\'s Republic of Korea, is a country in East Asia constituting the northern part of the Korean Peninsula. ","democratic_people_republic_of_korea_82.png",4,1);
//
////83 Ghana
////Medium
//            questionRepositories.questionCreator("Which country called by the name of “Gold Coast” ?","Ghana","South Africa","Zimbabwe                              ","Argentina","Ghana","The Gold Coast was originally known as the South Coast (because it was south of Brisbane). However, inflated prices for real estate and other goods and services led to the nickname of \"Gold Coast\" from 1950.","ghana_83.png",4,2);
//
////84 Republique Francaise
////Medium
//            questionRepositories.questionCreator("In the following what does France officially call itself?","Republique Francaise","Republique Caledonie","Frankreich","Republique Gaule","Republique Francaise","Officially the French Republic, is a sovereign state whose territory consists of metropolitan France in Western Europe, as well as several overseas regions and territories.","republique_francaise_84.png",4,2);
//
////85 Swaziland
////Medium
//            questionRepositories.questionCreator("In the following what is the familiar name of the Kingdom of Eswatini?","Swaziland","Estonia","Eritrea","The Gambia","Swaziland","The new name, eSwatini, means \"land of the Swazis\". The change was unexpected, but King Mswati has been referring to Swaziland for years as eSwatini.","swaziland_85.png",4,2);
//
////86 Federal Republic of Germany
////Medium
//            questionRepositories.questionCreator("Tell the official name of Germany in the following?","Grand Duchy of Prussia","Unified Republic of Germany","Federal Republic of Germany","German Democratic Republic","Federal Republic of Germany","\"Germany\" came from the Latin \"Germania\", \"Allemagne\" from the Alemanni tribe, and \"Deutschland\" from the old High German word \"diutisc\" meaning \"of the people\".","federal_republic_of_germany_86.png",4,2);
//
////87 Plurinational State of Bolivia
////Medium
//            questionRepositories.questionCreator("Which of the following is the official name of Bolivia?","Plurinational State of Bolivia","Co-operative Republic of Bolivia","Federative Republic of Bolivia","The democratic Socialist Republic of Bolivia","Plurinational State of Bolivia","The name was approved by the Republic on 3 October 1825. In 2009, a new constitution changed the country\'s official name to \"Plurinational State of Bolivia\" in recognition of the multi-ethnic nature of the country and the enhanced position of Bolivia\'s indigenous peoples under the new constitution.","plurinational_state_of_bolivia_87.png",4,2);
//
////88 Australie
////Medium
//            questionRepositories.questionCreator("In the following what do the French call Australia?","Australien","Australie","Nouveau Holland","Australe","Australie","Australia is a country and continent surrounded by the Indian and Pacific oceans. Its major cities – Sydney, Brisbane, Melbourne, Perth, Adelaide – are coastal. Its capital, Canberra, is inland. The country is known for its Sydney Opera House, the Great Barrier Reef, a vast interior desert wilderness called the Outback, and unique animal species like kangaroos and duck-billed platypuses.","australie_88.png",4,2);
//
////89 Yokohama
////Easy
//            questionRepositories.questionCreator("Which of the following cities is not located in Russia? ","Saint Petersburg","Samara","Sochi","Yokohama","Yokohama","Yokohama - is the second largest city in Japan by population, after Tokyo, and the most populous municipality of Japan.","yokohama_89.png",4,1);
//
////90 Archipelago
////Easy
//            questionRepositories.questionCreator("In geography, what word describes a sea with numerous islands or a group of many islands? ","Riverine","Moraine","Archipelago","Presidio","Archipelago","Archipelago - sometimes called an island group or island chain, is a chain, cluster or collection of islands, or sometimes a sea containing a small number of scattered islands.","archipelago_90.png",4,1);
//
////91 Eighteen miles
////Medium
//            questionRepositories.questionCreator("At its broadest point, how wide is the \"Grand Canyon\"? ","Forty Four miles ","Eighteen miles","Nine miles","two miles ","Eighteen miles","eighteen miles - The Grand Canyon is a steep-sided canyon carved by the Colorado River in Arizona, United States. The Grand Canyon is 277 miles (44 km) long, up to 18 miles (29 km) wide and attains a depth of over a mile (,093 feet or 1,857 meters).","eighteen_miles_91.png",4,2);
//
////92 Peterborough
////Easy
//            questionRepositories.questionCreator("One of England\'s greatest Elizabethan houses, Burghley House is located in which English city? ","Plymouth","Oxford","Nottingham","Peterborough","Peterborough","Peterborough - Construction on the Burghley House was finished in 1587. Peterborough is a cathedral city in Cambridgeshire, England, with a population of 183,31 in 2011. ","peterborough_92.png",4,1);
//
////93 San Francisco
////Easy
//            questionRepositories.questionCreator("Which California city stands on the hilly peninsula immediately south of the Golden Gate Bridge? ","Sacramento","San Diego","San Francisco","San Jose","San Francisco","San Francisco - officially the City and County of San Francisco, is the cultural, commercial, and financial center of Northern California.","san_francisco_93.png",4,1);
//
////94 Cayenne
////Medium
//            questionRepositories.questionCreator("A region of France, what city is the capital of \"French Guiana\"? ","Versailles","Cayenne","Bordeaux","Paris","Cayenne","Cayenne - is the capital city of French Guiana, an overseas region and department of Francelocated in South America. The city stands on a former island at the mouth of the Cayenne River on the Atlantic coast. The city\'s motto is \"fert aurum industria\", which means \"work brings wealth\".","cayenne_94.png",4,2);
//
////95 Gobi Desert
////Medium
//            questionRepositories.questionCreator("Which great Asian desert covers parts of northern and northwestern China, and southern Mongolia? ","Kyzyl Kum Desert","Thar Desert","Kara Kum Desert","Gobi Desert","Gobi Desert","Gobi Desert - is a large desert region in Asia. It covers parts of northern and northwestern China, and of southern Mongolia.","gobi_desert_95.png",4,2);
//
////96 Caspian Sea
////Medium
//            questionRepositories.questionCreator("What is the largest saltwater lake or inland sea in the world? ","Lake Eyre","Caspian Sea","Lake Balkhash","Lake Van","Caspian Sea","Caspian Sea - is the largest enclosed inland body of water on Earth by area, variously classed as the world\'s largest lake or a full-fledged sea. ","caspian_sea_96.png",4,2);
//
////97 Isthmus
////Medium
//            questionRepositories.questionCreator("In geography, what name is given to a narrow strip of land that joins two large areas of land across an expanse of water? ","Fjord","Moraine","Isthmus","Lacustrine Plain","Isthmus","Isthmus - is a narrow piece of land connecting two larger areas across an expanse of water by which they are otherwise separated. A tombolo is an isthmus that consists of a spit or bar, and a strait is the sea counterpart of an isthmus.","isthmus_97.png",4,2);
//
////98 New South Wales
////Medium
//            questionRepositories.questionCreator("In which Australian state is the important mining center of \"Broken Hill\"? ","South Australia","Western Australia","New South Wales","Queensland","New South Wales","New South Wales - The closest major city to Broken Hill is Adelaideat, which is about 311 miles away.","new_south_wales_98.png",4,2);
//
////99 Vienna
////
//            questionRepositories.questionCreator("In which Austrian city is the renowned Gothic cathedral of \"St. Stephen\"? ","Vienna","Graz","Klagenfurt","Salzburg","Vienna","Vienna - St. Stephen\'s Cathedral is the most important religious building in Vienna.","vienna_99.png",4,2);
//
////100 Antartica
////Easy
//            questionRepositories.questionCreator("In which continent can you find the active volcano \"Mount Erebus\"? ","Antartica","South America","Africa","Asia","Antartica","Antarctica - The volcano has been active for 1.3 million years ago.","antartica_100.png",4,1);
//
////101 Islands
////Easy
//            questionRepositories.questionCreator("Located in the United States, what kind of geographic feature are the \"Florida Keys\"? ","Mountains","Volcano Chain","Flatlands","Islands","Islands","Islands - The islands lie along the Florida Straits.","islands_101.png",4,1);
//
//            //TODO END OF GEOGRAPHY
//
//            //TODO MUSIC
//            //1 Chance the Rapper
////Difficult
//            questionRepositories.questionCreator("Presented in February of 2017, who won the Grammy Award for Best New Artist?","Chance the Rapper","The Chainsmokers","Kelsea Ballerini","Maren Morris","Chance the Rapper","Chancelor Jonathan Bennett (born April 16, 1993), known professionally as Chance the Rapper, is an American rapper, singer, songwriter, actor, record producer, and philanthropist from the West Chatham neighborhood of Chicago, Illinois.","chance_the_rapper_1.png",6,3);
//
////2 Infinite
////Difficult
//            questionRepositories.questionCreator("Their 20th, what studio album did Deep Purple release on April 1st, 2017?","Abandon","Purpendicular","Rapture of the Deep","Infinite","Infinite","Infinite is the 20th studio album by English rock band Deep Purple, released on 7 April 2017.","infinite_2.png",6,3);
//
////3 Back Road
////Difficult
//            questionRepositories.questionCreator("Finish the title to this 2017 Sam Hunt hit song - \"Body Like a \".","Heavenly Goddess","Wooden Toothpick","Bronze God","Back Road","Back Road","The song was written by Sam Hunt, Zach Crowell, Shane McAnally and Josh Osborne.","back_road_3.png",6,3);
//
////4 Shape of You
////Easy
//            questionRepositories.questionCreator("What 2017 Billboard Top 100 song starts with the lyric - \"The club isn\'t the best place to find a lover, so the bar is where I go\"?","Tunnel Vision","Castle on the Hill","I Feel it Coming","Shape of You","Shape of You","\"Shape of You\" peaked at number-one on the singles charts of 34 countries, including the US Billboard Hot 100 (later becoming the best performing song of 2017), as well as the UK, Australian and Canadian singles charts.","shape_of_you_4.png",6,1);
//
////5 Drake
////Medium
//            questionRepositories.questionCreator("Which Canadian rapper had Billboard hits with the songs \"No Long Talk\", \"Get It Together\" and \"Blem\" in 2017?","Madchild","Checkmate","Kardinal Offishall","Drake","Drake","Aubrey Drake Graham (born October 24, 1986) is a Canadian rapper, singer, songwriter, record producer, actor, and entrepreneur. He is one of the most popular entertainers in the world, and one of the best-selling music artists of the 21st century.","drake_5.png",6,2);
//
////6 Summer
////Medium
//            questionRepositories.questionCreator("On the Billboard Hot Rock Songs chart for March/April 2017, finish the title to this Weezer song - \"Feels Like ...\".","Summer","Winter","Rain","Love","Summer","Summer - The song was released as a single in March of 2017.","summer_6.png",6,2);
//
////7 Chuck Berry
////Difficult
//            questionRepositories.questionCreator("Which artist, who died in March of 2017, had his songs \"Maybellene\" and \"You Never Can Tell\" make a reappearance on the charts?","James Brown","Jerry Lee Lewis","Little Richard","Chuck Berry","Chuck Berry","Chuck was one of the first musicians to be inducted into the Rock and Roll Hall of Fame when it made its debut in 1986.","chuck_berry_7.png",6,3);
//
////8 Sib Hashian
////Difficult
//            questionRepositories.questionCreator("Boston\'s drummer died on stage while playing the band\'s greatest hits in March of 2017. Name him.","Sib Hashian","Tracy Ferrie","Gary Pihl","Brad Delp","Sib Hashian","Released in 1976, Boston\'s self-titled debut album is one of the bestselling debut albums in U.S. history.","sib_hashian_8.png",6,3);
//
////9 Lady Gaga
////Easy
//            questionRepositories.questionCreator("Released on her fifth studio album, which American singer had a hit with the song \"Million Reasons\"?","Kesha","Katy Perry","Lady Gaga","Christina Aguilera","Lady Gaga","Stefani Joanne Angelina Germanotta, known professionally as Lady Gaga, is an American singer, songwriter, and actress. She is known for her unconventionality and provocative work as well as visual experimentation. Lady Gaga - Found on the album \'Joanne\'.","lady_gaga_9.png",6,1);
//
////10 Lorde
////Easy
//            questionRepositories.questionCreator("New Zealand singer and songwriter Ella Marija Lani Yelich-O\'Connor is better known as?","Kimbra","Charli XCX","Lorde","Lana Del Rey","Lorde","Ella Marija Lani Yelich-O\'Connor, known professionally as Lorde, is a New Zealand singer, songwriter, and record producer. She holds both New Zealand and Croatian citizenship. As of 2017, Lorde has earned two Grammy Awards.","lorde_10.png",6,1);
//
////11 Beauty and the Beast
////Easy
//            questionRepositories.questionCreator("What Disney film soundtrack peaked at number three on the Billboard 200 chart in March of 2017?","Coco","Cars Three","Ghost in the Shell","Beauty and the Beast","Beauty and the Beast","Beauty and the Beast is a live-action remake of Disney\'s 1991 animated film of the same name.","beauty_and_the_beast_11.png",6,1);
//
////12 George Harrison
////Medium
//            questionRepositories.questionCreator("Coinciding with what would have been his 74th birthday, which former Beatle\'s entire solo album collection was released in February of 2017?","Paul McCartney","John Lennon","Ringo Starr","George Harrison","George Harrison","George Harrison MBE was an English guitarist, singer-songwriter, and producer who achieved international fame as the lead guitarist of the Beatles. The boxed set is titled George Harrison - The Vinyl Collection.","george_harrison_12.png",6,2);
//
////13 Zayn Malik
////Easy
//            questionRepositories.questionCreator("Released on the film soundtrack \"Fifty Shades Darker\", who collaborated with Taylor Swift on the song \"I Don\'t Wanna Live Forever\"?","Shawn Mendes","Harry Styles","Zayn Malik","Justin Bieber","Zayn Malik","The song was written by Taylor Swift, Sam Dew and Jack Antonoff.","zayn_malik_13.png",6,1);
//
////14 Blackstar David Bowie
////Difficult
//            questionRepositories.questionCreator("Presented posthumously, who won the 2017 Grammy Award for Best Rock Song?","Unspoken – Chuck Loeb","Blackstar David Bowie","Don't Hurt Yourself – Beyonce","Heathens - Tyler Joseph","Blackstar David Bowie","Blackstar was released on Bowie\'s final studio album of the same name.","blackstar_david_bowie_14.png",6,3);
//
////15 Brian Eno
////Difficult
//            questionRepositories.questionCreator("Starting his music career as a member of Roxy Music in 1971, who released the album \"Reflection\" on New Year\'s Day, 2017?","Brian Eno","Bono","John Cale","Robert Fripp","Brian Eno","Brian Peter George St John le Baptiste de la Salle Eno, RDI is an English musician, composer, record producer, singer, writer, and visual artist. - Eno is widely regarded as an ambient music pioneer.","brian_eno_15.png",6,3);
//
////16 Twenty four thousand Magic Bruno Mars
////Easy
//            questionRepositories.questionCreator("Presented in January of 2018, who won the Grammy Award for Album of the Year? ","The Story of O.J. - Jay-Z","Humble - Kendrick Lamar","Redbone - Childish Gambino","Twenty four thousand Magic Bruno Mars","Twenty four thousand Magic Bruno Mars","Twenty four thousand Magic is the third studio album by American singer and songwriter Bruno Mars.","twenty_four_thousand_magic_bruno_mars_16.png",6,1);
//
////17 Firepower
////Difficult
//            questionRepositories.questionCreator("Their 18th, what studio album did Judas Priest release in March of 2018? ","Redeemer of Souls","Angel of Retribution","Nostradamus","Firepower","Firepower","Firepower sold close to 50,000 copies in the United States during its first week of release.","firepower_17.png",6,3);
//
////18 Blood
////Medium
//            questionRepositories.questionCreator("Finish the title to this 2018 Shawn Mendes hit song - \"In My ...\". ","Backyard","Life","Blood","Car","Blood","The song chronicles Shawn\'s struggle with an anxiety disorder.","blood_18.png",6,2);
//
////19 Lucid Dreams
////Difficult
//            questionRepositories.questionCreator("What 2018 Billboard Top 100 song starts with the lyric - \"I still see your shadows in my room. Can\'t take back the love that I gave you\"? ","Lucid Dreams","Girls Like You","No Tears Left to Cry","Sad!","Lucid Dreams","Lucid Dreams - Released in March of 2018, Lucid Dreams was recorded by American rapper Juice WRLD.","lucid_dreams_19.png",6,3);
//
////20 Drake
////Medium
//            questionRepositories.questionCreator("Which Canadian rapper had Billboard hits with the songs \"Nice For What\", \"God\'s Plan\" and \"I\'m Upset\" in 2018? ","Checkmate","Madchild","Drake","Kardinal Offishall","Drake","Drake - As of 2018, Drake holds the record for most charted songs among solo artists in the history of the Billboard Hot 100 at 154.","drake_20.png",6,2);
//
////21 Clouds
////Difficult
//            questionRepositories.questionCreator("On the Billboard Hot Rock Songs chart for July 2018, finish the title for this song \"Panic! At The Disco\" song - \"King of the ...\". ","Sun","World","Clouds","Night","Clouds","Panic! at the Disco is an American rock band that formed in 2004.","clouds_21.png",6,3);
//
////22 Dolores O'Riordan
////Difficult
//            questionRepositories.questionCreator("Which artist, who died at the age of 46 in January of 2018, was the lead singer for the Cranberries?? ","Dolores O'Riordan","Barbara Cope","Betty Willis","Claudia Fontaine","Dolores O'Riordan","Dolores Mary Eileen O\'Riordan was an Irish musician, singer and songwriter. She led the rock band The Cranberries from 1990 until their break-up in 2003; they reunited in 2009.","dolores_o_riordan_22.png",6,3);
//
////23 Cardi B
////Medium
//            questionRepositories.questionCreator("Released on her debut studio album, which American rapper had a hit with the song \"Be Careful\"? ","Nicki Minaj","Cardi B","Queen Latifah","Lil' Kim","Cardi B","Cardi B - Belcalis Marlenis Almanzar known professionally as Cardi B, is an American rapper. Born and raised in The Bronx, New York City","cardi_b_23.png",6,2);
//
////24 Pink
////Easy
//            questionRepositories.questionCreator("American singer and songwriter Alecia Beth Moore is better known as? ","Pink","Shakira","Beyonce","Rihanna","Pink","Pink was originally a member of the girl group Choice.","pink_24.png",6,1);
//
////25 Thirteen Reasons Why Season two
////Easy
//            questionRepositories.questionCreator("What TV show\'s soundtrack peaked at #26 on the Billboard 200 chart in June of 2018? ","The Good Place - Season one","Wild Wild Country - Season one","Thirteen Reasons Why Season two","The Good Fight - Season two","Thirteen Reasons Why Season two","Thirteen Reasons Why - Season 2 - The TV show is based on the 2007 novel Thirteen Reasons Why by Jay Asher.","thirteen_reasons_why_season_two_25.png",6,1);
//
////26 Drake
////Medium
//            questionRepositories.questionCreator("Released on the album \"Simi\", who collaborated with Blocboy JB on the song \"Look Alive\"? ","Zayn Malik","Harry Styles","Drake","Shawn Mendes","Drake","Look Alive peaked at number five on the Billboard Hot 100.","drake_26.png",6,2);
//
////27 Bruno Mars
////Easy
//            questionRepositories.questionCreator("Who won all six Grammy Awards for which he was nominated at the 60th Annual Grammy Awards? ","Rihanna","Jay-Z","Ed Sheeran","Bruno Mars","Bruno Mars","As of 2018, Mars has sold over 130 million records worldwide.","bruno_mars_27.png",6,1);
//
////28 Jack White
////Difficult
//            questionRepositories.questionCreator("Winner of 12 Grammy Awards, what former member of the White Stripes released the album \"Boarding House Reach\" in March of 2018? ","Meg White","Alison Mosshart","Brendan Benson","Jack White","Jack White","Jack White Boarding House Reach is Jack\'s third solo studio album.","jack_white_28.png",6,3);
//
////29 Nokie Edwards
////Difficult
//            questionRepositories.questionCreator("What longtime Venture\'s guitarist passed away in March 2018 at the age of 82? ","Bob Bogle","Jerry McGee","Nokie Edwards","Don Wilson","Nokie Edwards","Nole Floyd \"Nokie\" Edwards was an American musician and member of the Rock and Roll Hall of Fame. He was primarily a guitarist, best known for his work with The Ventures, and was known in Japan as the King of Guitars.","nokie_edwards_29.png",6,3);
//
////30 Kanye West
////Medium
//            questionRepositories.questionCreator("Making its debut in April of 2018, the studio album \"Ye\" was released by which American rapper? ","Kanye West","50 Cent","Jay-Z","Eminem","Kanye West","Kanye Omari West is an American rapper, singer, songwriter, record producer, entrepreneur and fashion designer. ","kanye_west_30.png",6,2);
//
////31 Baha Men
////Difficult
//            questionRepositories.questionCreator("\"Who Let the Dogs Out\" in 2000? ","Snoopy","Baha Men","Shaggy","Lil Bow Wow","Baha Men","The Baha Men are a Bahamian band playing a modernized style of Bahamian music called junkanoo. They are best remembered for their Grammy Award-winning hit song \"Who Let the Dogs Out?\".","baha_men_31.png",6,3);
//
////32 Bruce Springsteen
////Difficult
//            questionRepositories.questionCreator("Released in 1973, my debut album is titled \"Greetings from Asbury Park\". Who am I? ","Eric Clapton","Jon Bon Jovi","Bruce Springsteen","Bryan Adams","Bruce Springsteen","Bruce Frederick Joseph Springsteen is an American singer-songwriter and musician, known for his work with the E Street Band.","bruce_springsteen_32.png",6,3);
//
////33 Vera Lynn
////Difficult
//            questionRepositories.questionCreator("\"The Forces Sweetheart\" was a nickname given to which 1940s female star? ","Alma Cogan","Kay Starr","Vera Lynn","Rosemary Clooney","Vera Lynn","Vera\'s musical recordings were popular during the Second World War.","vera_lynn_33.png",6,3);
//
////34 Hound Dog
////Medium
//            questionRepositories.questionCreator("Which of these Elvis Presley songs was a number one BEFORE the others? ","It's Now or Never","All Shook Up","Hound Dog","Stuck on You","Hound Dog","Elvis released his version of Hound Dog in July of 1956.","hound_dog_34.png",6,2);
//
////35 Geri Halliwell
////Difficult
//            questionRepositories.questionCreator("This \"Spice Girl\" had a number one hit with \"It\'s Raining Men\" in 2001. ","Victoria Beckham","Emma Bunton","Geri Halliwell","Melanie C","Geri Halliwell","Geri Halliwell - The song was originally released by The Weather Girls in 1982.","geri_halliwell_35.png",6,3);
//
////36 Manic Street Preachers
////Difficult
//            questionRepositories.questionCreator("Mysteriously disappearing in February of 1995, Richey Edwards was a member of which rock band? ","The Stone Roses","Stereophonics","Primal Scream","Manic Street Preachers","Manic Street Preachers","Richard James \"Richey\" Edwards was a Welsh musician who was the lyricist and rhythm guitarist of the alternative rock band Manic Street Preachers. Edwards was declared \'presumed dead\' in November of 2008.","manic_street_preachers_36.png",6,3);
//
////37 Pete Best
////Medium
//            questionRepositories.questionCreator("Who originally played the drums with \"The Beatles\"? ","Ringo Starr","George Harrison","Pete Best","Stuart Sutcliffe","Pete Best","Randolph Peter Best is an English musician, principally known as an original member and the first drummer of the Beatles, from 1960 to 1962. ","pete_best_37.png",6,2);
//
////38 (Hit Me) Baby One More Time
////Easy
//            questionRepositories.questionCreator("With what song did Britney Spears make her breakthrough in 1998? ","fOoops, I Did it Again","Circus","(Hit Me) Baby One More Time","Toxic","(Hit Me) Baby One More Time","Britney Jean Spears (born December 2, 1981) is an American singer, dancer, and actress. Born in McComb, Mississippi, and raised in Kentwood, Louisiana, she performed acting roles in stage productions and television shows as a child, before signing with Jive Recordsin 1997.","(hit_me)_baby_one_more_time_38.png",6,1);
//
////39 Avril Lavigne
////Easy
//            questionRepositories.questionCreator("Which Canadian singer-songwriter was \"Complicated\" in 2002? ","Shania Twain","Avril Lavigne","Joni Mitchell","Sarah McLachlan","Avril Lavigne","“Complicated” song is From Avril Lavigne\'s album \'Let Go\'.","avril_lavigne_39.png",6,1);
//
////40 Plain White T's
////Medium
//            questionRepositories.questionCreator("What is the correct name of this 2007 chart topping band? ","Plain White C's","Plain White B's","Plain White T's","Plain White D's","Plain White T's","Plain White T\'s is best known for the number-one hit song \'Hey There Delilah\'.","plain_white_t_s_40.png",6,2);
//
////41 Hips Don't Lie
////Easy
//            questionRepositories.questionCreator("2006: What popular song by Shakira hit the number #1 spot in over 50 different countries?","Hips Don't Lie","Hey You","How Do You Do","Higher Ground","Hips Don't Lie","Shakira created this hit song in 2006 with the help of another musical performer named Wyclef Jean. \"Hips Don\'t Lie\" was first released on Shakira\'s album \"Oral Fixation\".","hips_don_t_lie_41.png",6,1);
//
////42 Drums
////Difficult
//            questionRepositories.questionCreator("Taylor Hawkins plays which instrument in the \"Foo Fighters\"? ","Keyboards","Guitar","Drums","Bass Guitar","Drums","Hawkins was the touring drummer for Alanis Morissette on her Jagged Little Pill and Can\'t Not Tours.","drums_42.png",6,3);
//
////43 Fats Domino
////Difficult
//            questionRepositories.questionCreator("Who released a rock n\' roll version of the song \"Blueberry Hill\", which reached number two on the Billboard Top 40 chart in 1956? `","Little Richard","Ray Charles","Chuck Berry","Fats Domino","Fats Domino","Antoine \"Fats\" Domino Jr. was an American pianist and singer-songwriter. One of the pioneers of rock and roll music, Domino sold more than 65 million records.","fats_domino_43.png",6,3);
//
////44 Jay Z
////Easy
//            questionRepositories.questionCreator("Linkin Park teamed up with which Rap star to record the song \"Numb/Encore\"? ","Snoop Dogg","Jay Z","Ludacris","Nelly","Jay Z","Numb/Encore is from the 2004 album Collision Course.","jay_z_44.png",6,1);
//
////45 Touch
////Medium
//            questionRepositories.questionCreator("Released in 2006, complete the title to this Cascada number one hit - \"Everytime We...\"? ","Kiss","Smile","Touch","Meet","Touch","Everytime we touch song is from their debut album Everytime We Touch.","touch_45.png",6,2);
//
////46 Cello
////Difficult
//            questionRepositories.questionCreator("Born in Oxford, England, \"Jacqueline du Pre\" was famous for the playing of which stringed instrument? ","Guitar","Cello","Violin","Double Bass","Cello","Jacqueline\'s career was cut short by multiple sclerosis at the age of 27.","cello_46.png",6,3);
//
////47 Sheryl Crow
////Medium
//            questionRepositories.questionCreator("Which American singer-songwriter won the 1994 Grammy for Record of the Year for with the song \"All I Wanna Do\"? ","Carly Simon","Madonna","Sheryl Crow","Dolly Parton","Sheryl Crow","\'All I Wanna Do\' song peaked at number two on the Billboard Hot 100.","sheryl_crow_47.png",6,2);
//
////48 All Saints
////Medium
//            questionRepositories.questionCreator("Sisters Nicole and Natalie Appleton are members of which all-girl group? ","Sugababes","Girls Aloud","Atomic Kitten","All Saints","All Saints","All Saints have sold over 12 million records","all_saints_48.png",6,2);
//
////49 Fourteen
////Difficult
//            questionRepositories.questionCreator("Elton John spent how many weeks at the top of the US Charts with his 1997 rewritten version of the song \"Candle in the Wind\"? ","Four","Fourteen","Thirty four","Twenty four","Fourteen","The song was originally written in honor of Marilyn Monroe in 1973.","fourteen_49.png",6,3);
//
////50 The Clovers
////Medium
//            questionRepositories.questionCreator("The songs \"Ting a Ling\", \"Love Potion No. 9\" and \"Nip Sip\" were hits for which vocal group? ","The Clovers","The Platters","The Dominoes","The Cadillacs","The Clovers","The Clovers were one of the most popular acts of the 1950s.","the_clovers_50.png",6,2);
//
////51 South Africa
////Difficult
//            questionRepositories.questionCreator("Dave Matthews of the \"Dave Matthews Band\" was born in which country? ","U.S.A.","South Africa","Canada","Germany","South Africa","From 2000 to 2010, the Dave Matthews Band earned more money than any other band in North America.","south_africa_51.png",6,3);
//
////52 Stevie Wonder
////Medium
//            questionRepositories.questionCreator("Released in 1974, and found on his \"Fulfillingness\' First Finale\" album, who recorded the funk song \"Boogie on Reggae Woman\"? ","Stevie Wonder","Marvin Gaye","Al Wilson","Bob Marley","Stevie Wonder","Stevland Hardaway Morris, known by his stage name Stevie Wonder, is an American singer, songwriter, record producer, and multi-instrumentalist.","stevie_wonder_52.png",6,2);
//
////53 Michael Nesmith
////Difficult
//            questionRepositories.questionCreator("Who wrote the first hit song \"Different Drum\" for Stone Poneys and their lead-singer Linda Ronstadt? ","Michael Nesmith","Paul Simon","Neil Diamond","Bob Dylan","Michael Nesmith","The song reached number 13 on the Billboard Hot 100 in 1967.","michael_nesmith_53.png",6,3);
//
////54 Bleach
////Difficult
//            questionRepositories.questionCreator("Released in June of 1989, what was the name of Nirvana\'s debut album? ","Nevermind","Smells Like Teen Spirit","Incesticide","Bleach","Bleach","Bleach has had sales of over 1.7 million units in the United States.","bleach_54.png",6,3);
//
////55 Say it Right
////Difficult
//            questionRepositories.questionCreator("2006: What worldwide hit by Nelly Furtado was #1 on over 20 different music charts?","Say it again","Say it","Say it all","Say it Right","Say it Right","Nelly Furtado\'s song \"Say It Right\" was released in the United States on October 31st, 2006. The song \"Say It Right\" featured backup singers named Timothy Mills and Nate Hills.","say_it_right_55.png",6,3);
//
////56 Road to Forever
////Medium
//            questionRepositories.questionCreator("As of 2013, Don Felder has released two solo albums. Airborne and... ","Fly Higher","Rhythms in the Night","Guitar Man","Road to Forever","Road to Forever","Airborne was released in 1983 and Road to Forever in 2012.","road_to_forever_56.png",6,2);
//
////57 The Osmonds
////Difficult
//            questionRepositories.questionCreator("Who had a hit in 1972 with the song \"Down by the Lazy River\"? ","Leif Garrett","The Osmonds","Partridge Family","Jackson Five","The Osmonds","The Osmonds have sold over 102 million records worldwide.","the_osmonds_57.png",6,3);
//
////58 Quincy Jones
////Easy
//            questionRepositories.questionCreator("Who conducted and produced the charity single \"We Are the World\"? ","Michael Jackson","Lionel Ritchie","Quincy Jones","Bruce Springsteen","Quincy Jones","The song was written by Michael Jackson and Lionel Richie.","quincy_jones_58.png",6,1);
//
////59 Vince Guaraldi
////Difficult
//            questionRepositories.questionCreator("Which of these jazz composers penned, among other things, the well-known \"Charlie Brown Theme\"? ","David Benoit","Antonin Scarlatti","Vince Guaraldi","Eddie Daniels","Vince Guaraldi","Vince served as an Army cook in the Korean War.","vince_guaraldi_59.png",6,3);
//
////60 They all hit number six
////Difficult
//            questionRepositories.questionCreator("What is unusual about Seals and Crofts\' hits \"Summer Breeze\", \"Diamond Girl\" and \"Get Closer\"? ","They were all hits on April Fool's Day","They were all hits in the same year","They all had backing vocals by John Denver","They all hit number six","They all hit number six","Seals and Crofts were an American soft rock duo made up of James \"Jim\" Seals and Darrell \"Dash\" Crofts. They are best known for their Hot 100 No. 6 hits \"Summer Breeze\", \"Diamond Girl\", and \"Get Closer\".","they_all_hit_number_six_60.png",6,3);
//
////61 Sound of Silence
////Medium
//            questionRepositories.questionCreator("What Simon and Garfunkel hit song opens with the line \"Hello darkness my old friend\"? ","The Boxer","Homeward Bound","Sound of Silence","Mrs. Robinson","Sound of Silence","Released in 1965, Sound of Silence was the duo\'s second most popular hit after the song \"Bridge Over Troubled Water\".","sound_of_silence_61.png",6,2);
//
////62 Trouble in Paradise
////Medium
//            questionRepositories.questionCreator("Which of the following songs was not a hit for \"The Everly Brothers\"? ","When Will I Be Loved","All I Have to Do Is Dream","Trouble in Paradise","Wake Up Little Susie","Trouble in Paradise","Trouble in Paradise, The Crests recorded \'Trouble in Paradise\' in 1960.","trouble_in_paradise_62.png",6,2);
//
////63 George Gershwin
////Difficult
//            questionRepositories.questionCreator("Who composed the timeless hit \"Fascinating Rhythm\"? ","George Gershwin","Lionel Hampton","Count Basie","Benny Goodman","George Gershwin","Fascinating Rhythm Written in 1924, the song was first heard in the Broadway musical \'Lady Be Good\'.","george_gershwin_63.png",6,3);
//
////64 Kryptonite
////Difficult
//            questionRepositories.questionCreator("What song is this from? \"If I go crazy then will you still Call me Superman If I\'m alive and well, will you be There a-holding my hand I\'ll keep you by my side With my superhuman might Kryptonite\"","Call me Superman","I’ll keep you by my Side","Kryptonite","My superhuman might Kryptonite","Kryptonite","The song, \"Kryptonite\", is sung by 3 Doors Down and was released on November 21, 2000.","kryptonite_64.png",6,3);
//
////65 Sammy Johns
////Difficult
//            questionRepositories.questionCreator("Written in 1973, who was the one-hit wonder who recorded \"Chevy Van\"? ","Sammy Johns","Glen Campbell","Ricky Nelson","Starland Vocal Band","Sammy Johns","The song reached number five on the Billboard Hot 100 chart in 1975.","sammy_johns_65.png",6,3);
//
////66 Roar
////Easy
//            questionRepositories.questionCreator("Katy Perry has been announced as one of the live performers for the 2014 Grammy Awards. Additionally, she has been nominated for Song of the Year. Which song? ","Dark Horse","unconditionally","Part of me","Roar","Roar","Roar can be found on Katy\'s album, Prism.","roar_66.png",6,1);
//
////67 Miley Cyrus
////Easy
//            questionRepositories.questionCreator("Co-written and produced by Oren Yoel, who recorded the hit ballad \"Adore You\"? ","Taylor Swift","Beyonce Knowles","Miley Cyrus","Selena Gomez","Miley Cyrus","The song “Adore you” can be found on Cyrus\' number-one hit album \'Bangerz\'.","miley_cyrus_67.png",6,1);
//
////68 XO
////Medium
//            questionRepositories.questionCreator("Recorded by Beyonce Knowles and found on her fifth studio album \"Beyonce,\" what single features an audio clockTick from the Space Shuttle Challenger disaster? ","Drunk in Love","Flawless","Pretty Hurts","XO","XO","The audio clockTick\'s inclusion has been heavily criticized by the families of the lost crew and the media.","xo_68.png",6,2);
//
////69 Lorde
////Easy
//            questionRepositories.questionCreator("\"Royals\" has been nominated for Song of the Year at the upcoming 2014 Grammy Awards. Who recorded the song? ","Ariana Grande","Ellie Goulding","Lorde","Lana Del Rey","Lorde","Royals song can be found on Lorde\'s debut studio album, \'Pure Heroine\'.","lorde_69.png",6,1);
//
////70 Young Girls
////Medium
//            questionRepositories.questionCreator("Which song by Bruno Mars opens with the lyrics, \"I spend all my money on a big ol\' fancy car for these bright-eyed honeys. Oh yeah, you know who you are.\"? ","Gorillas","Young Girls","Moonshine","Treasure","Young Girls","Young Girls is from Mars\' second studio album \'Unorthodox Jukebox\'.","young_girls_70.png",6,2);
//
////71 Zendaya
////Easy
//            questionRepositories.questionCreator("Riding on the success of her pop hit single \"Replay,\" who recently completed her first \"Swag It Out\" concert tour? ","Debby Ryan","Zendaya","Stefanie Scott","Bella Thorne","Zendaya","As of January 2014, \'Replay\' has been certified gold for selling 500,000 copies in the United States.","zendaya_71.png",6,1);
//
////72 Story of my Life
////Easy
//            questionRepositories.questionCreator("Which of the following hit songs was written by Jamie Scott, Caolan Dooley, John Ryan, and the British-Irish boy band, One Direction? ","Hold On, We're Going Home","Stay The Night ","Drunk in Love","Story of my Life","Story of my Life","Released by One Direction, Story of my Life is the fifth track on the album \'Now 49\'.","story_of_my_life_72.png",6,1);
//
////73 Pompeii
////Medium
//            questionRepositories.questionCreator("What hit song by Bastille starts with the lyrics \"I was left to my own devices. Many days fell away with nothing to show.\"? ","Of the Night","Laura Palmer","Pompeii","Things We Lost in the Fire","Pompeii","Pompeii has been nominated for British Single of the Year at the 2014 BRIT Awards.","pompeii_73.png",6,2);
//
////74 Rihanna
////Easy
//            questionRepositories.questionCreator("Eminem and which star have a Billboard 100 hit with the song \"The Monster\"? ","Ke$ha","Lady Gaga","Miley Cyrus","Rihanna","Rihanna","\'The Monster\' marks the fourth collaboration among Eminem and Rihanna.","rihanna_74.png",6,1);
//
////75 Say Something
////Easy
//            questionRepositories.questionCreator("Which of the following songs was recorded by A Great Big World and Christina Aguilera? ","Counting Stars ","White Walls","Let It Go ","Say Something","Say Something","The song was originally released in 2011 without Christina Aguilera.","say_something_75.png",6,1);
//
////76 Wake Me Up
////Medium
//            questionRepositories.questionCreator("Peaking at number one in 22 countries to date, Avicii introduced which of the following songs for the first time at the Ultra Music Festival in Miami? ","Wake Me Up","You Make Me","Hey Brother","We Write the Story","Wake Me Up","The song can be found on Avicii\'s debut studio album, \'True\'.","wake_me_up_76.png",6,2);
//
////77 Piano
////Difficult
//            questionRepositories.questionCreator("Achieving worldwide success in 2007 with the hit single \"Love Song,\" and currently in the charts with the song \"Brave,\" what instrument does Sara Bareilles play? ","Piano","Harp","Guitar","Violin","Piano","Sara has been nominated for a Grammy Award five times.","piano_77.png",6,3);
//
////78 Show Me
////Medium
//            questionRepositories.questionCreator("Recorded by American hip hop artist Kid Ink, which song features a hook and bridge by Chris Brown? ","Show Me","Iz U Down","Money and the Power","Bad Ass","Show Me","On January 7, 2014, Kid Ink made his television debut performing \"Show Me\" on Conan.","show_me_78.png",6,2);
//
////79 Sweater Weather
////Difficult
//            questionRepositories.questionCreator("What hit by \"The Neighbourhood\" opens with the lyrics, \"All I am is a man - I want the world in my hands - I hate the beach - But I stand.\"? ","Sweater Weather","I Love You","Female Robbery","Afraid","Sweater Weather","Sweater Weather was the lead single from the Neighbourhood\'s debut studio album, \'I Love You\'.","sweater_weather_79.png",6,3);
//
////80 Lady Gaga
////Easy
//            questionRepositories.questionCreator("Slated to be released on January 1st, 2014, but delayed until March, which pop icon will release the album \"Cheek to Cheek\" with jazz singer Tony Bennett? ","Lady Gaga","Madonna","Britney Spears","Katy Perry","Lady Gaga","Cheek to cheek album is Lady Gaga\'s second collaboration with Tony, the first is a duet of song \'The Lady is a Tramp\'.","lady_gaga_80.png",6,1);
//
////81 The 20/20 Experience
////Medium
//            questionRepositories.questionCreator("What is the name of the album released by Justin Timberlake in March 2013? ","The 20/20 Experience","Goldenheart","Contrast","Vessel","The 20/20 Experience","Justin Randall Timberlake (born January 31, 1981) is an American singer-songwriter, actor, dancer, and record producer.","the_20_20_experience_81.png",6,2);
//
////82 Twenty-One Pilots
////Medium
//            questionRepositories.questionCreator("Which band released an album titled \"Vessel\" in 2013? ","Blink one hundred eighty two","Savage Garden","Savages","Twenty-One Pilots","Twenty-One Pilots","Twenty-One Pilots is a band formed in 2009 in Ohio.","twenty_one_pilots_82.png",6,2);
//
////83 Black Flag
////Medium
//            questionRepositories.questionCreator("Which of these bands officially reformed in January 2013? ","Green Day","Black Flag","Boston","Linkin Park","Black Flag","The band was originally formed in 1976. They broke up in 1986.","black_flag_83.png",6,2);
//
////84 Fight or Flight
////Difficult
//            questionRepositories.questionCreator("Which of these bands was formed in 2013? ","Fight or Flight","Attack Attack","Lifehouse","Ghost Observatory","Fight or Flight","The band\'s debut single was released on the 21st of May 2013.","fight_or_flight_84.png",6,3);
//
////85 Wofmother
////Difficult
//            questionRepositories.questionCreator("During 2013, which of these bands was on hiatus? ","The Replacements","The Mars Volta","Green Day","Wofmother","Wofmother","Wolfmother has been together since 2000.","wofmother_85.png",6,3);
//
////86 Animal
////Difficult
//            questionRepositories.questionCreator("Which of these songs is on the album \"Contrast\" by Conor Maynard? ","The Way","Shut Up","NoNoNo","Animal","Animal","Animal song is the first song on the album.","animal_86.png",6,3);
//
////87 ASAP
////Medium
//            questionRepositories.questionCreator("Who recorded the album Long. Live.? ","Muse","ASAP","Dawn Richard","Bad Religion","ASAP","The album was released in January of 2013.","asap_87.png",6,2);
//
////88 True North
////Medium
//            questionRepositories.questionCreator("The phrase \"Unrepentant vagabond\" is the opening line of which of the following songs? ","True North","Troublemaker","Stutter","All Fired Up","True North","The song appears on the album True North by Bad Religion.","true_north_88.png",6,2);
//
////89 Chasing the Saturdays
////Difficult
//            questionRepositories.questionCreator("Which of the following albums was released by \"The Saturdays\"? ","Chasing Monday","Finding the Saturdays","Waiting for Sunday","Chasing the Saturdays","Chasing the Saturdays","Chasing the Saturdays Released in 2013, it is their second album.","chasing_the_saturdays_89.png",6,3);
//
////90 Be a Man
////Difficult
//            questionRepositories.questionCreator("Which of these songs appears on the album \"It Happens All the Time\" by Megan Hilty? ","Heat","The Next Day","Love is Lost","Be a Man","Be a Man","Be a Man song has three minutes and thirty-four seconds in length, the song is the album\'s second track.","be_a_man_90.png",6,3);
//
////91 All Around the World
////Easy
//            questionRepositories.questionCreator("\"MB... all around the world... Beautiful girl, girl, girl. Uh, girl.\" First line of what Justin Bieber song? ","The Way","Crash","Miss You More","All Around the World","All Around the World","This song appears on the album All Around the World, which was released in 2013.","all_around_the_world_91.png",6,1);
//
////92 Paramore
////Easy
//            questionRepositories.questionCreator("The songs \"Now\", \"Grow\" and \"Last Hope\" can be found on which of the following albums? ","Right Place Right Time","Willpower","Paramore","Save Rock and Roll","Paramore","The album was put out by the pop band Paramore.","paramore_92.png",6,1);
//
////93 Justin Bieber
////Easy
//            questionRepositories.questionCreator("Who sings with Will.i.am on his song #thatpower? ","Dido","Jessica Sanchez","Justin Bieber","Wing","Justin Bieber","The song was released in April, 2013.","justin_bieber_93.png",6,1);
//
////94 Me, You and the Music
////Easy
//            questionRepositories.questionCreator("What was the name of the album that Jessica Sanchez released in 2013? ","The Hands That Thieve","Life on a Rock","Wake Up","Me, You and the Music","Me, You and the Music","This was Jessica\'s first studio album.","me_you_and_the_music_94.png",6,1);
//
////95 Microphone
////Medium
//            questionRepositories.questionCreator("What is the first song on the album 2.0 by 98 Degrees? ","Microphone","Without the Love","Rootless","Get To Me","Microphone","The album was released in 2013.","microphone_95.png",6,2);
//
////96 Demi Lovato
////Easy
//            questionRepositories.questionCreator("Who recorded the song \"Heart Attack\" in 2013? ","Bad Rabbits","Little Mix","Marianas Trench","Demi Lovato","Demi Lovato","The song appears on the album Demi.","demi_lovato_96.png",6,1);
//
////97 DNA
////Difficult
//            questionRepositories.questionCreator("\"Does he tell you he loves you when you least expect it?\" The first line of which song? ","DNA","Run","Last Hope","Close Your Eyes","DNA","The song appears on the album DNA along with the song Wings.","dna_97.png",6,3);
//
////98 Christina Aguilera
////Easy
//            questionRepositories.questionCreator("In 1999, who had a number one hit on the Billboard Hot 100 with \"Genie In A Bottle\"?","Christina Aguilera","Christina Perri","Rihanna","Celine Dion","Christina Aguilera","\"Genie In A Bottle \" was the only number one in the 09s by Christina Aguilera. The others all had number ones on the Hot 100 in 1999: \"Have You Ever\" (Brandy); \"Believe\" (Cher); and \"Angel Of Mine\" (Monica).","christina_aguilera_98.png",6,1);
//
////99 Two u
////Difficult
//            questionRepositories.questionCreator("Sinead O\'Connor sang \"Nothing Compares\", but what was the official spelling of the remainder of that song title?","Two u","You and me","I love you","You and i","Two u","Some might say her abbreviation was ahead of its time, coming before the popular use of SMS text messages, internet chats and twitter. \"Nothing Compares 2 U\" charted at number three on the US Billboard list in 1990, behind Roxette\'s \"It Must Have Been Love\" (number two) and Wilson Phillips\' \"Hold On\" (number one).","two_u_99.png",6,3);
//
////100 It Must Have Been Love
////Medium
//            questionRepositories.questionCreator("Spending a total of seventeen weeks in the Billboard Top 40, which Roxette song was also on the soundtrack for the movie \"Pretty Woman\"?","It must love","Be love","It must be Love","It Must Have Been Love","It Must Have Been Love","Roxette consisted of Marie Fredriksson and Per Gessle. \"It Must Have Been Love (Christmas for the Broken Hearted)\" was on Swedish radio during 1987, however it was rewritten in 1990 (without the Christmas lyrics) and was included on the \"Pretty Woman\" soundtrack.","it_must_have_been_love_100.png",6,2);
//
////101 Deee-Lite
////Medium
//            questionRepositories.questionCreator("In 1990, which American dance group had an international hit with the song \"Groove Is in the Heart\"?","The Power","Deee-Lite","Day Lite","Black Box","Deee-Lite","The New York City band Deee-Lite achieved international stardom in late 1990 with the single \"Groove Is in the Heart.\" 103. In 1994, Nicki French released the Billboard Top 100 smash \"Total Eclipse of the Heart\".","deee_lite_101.png",6,2);
//
////102 Madonna
////Medium
//            questionRepositories.questionCreator("\"All you need is your own imagination/ so use it that\'s what it\'s for.\" Who sang these lyrics?","Beyonce","Celine Dion","Madonna","Whitney Houston","Madonna","Madonna\'s \"Vogue\" shot to number one in the UK singles chart in April 1990, commanding the top spot for four weeks. Along with Madonna, all of the other artists listed also reached the coveted number one spot that year.","madonna_102.png",6,2);
//
////103 Jason Mraz
////Easy
//            questionRepositories.questionCreator("Which artist recorded the song \"The Remedy?\"","Jason Mars","Jason Mraz","Jason Maz","Jesson Mraz","Jason Mraz","\"The Remedy\" was from Jason\'s first album, \"Waiting for my Rocket to Come.\" This was the first single released from this album.","jason_mraz_103.png",6,1);
//
//            //TODO END OF MUSIC
//
//            //TODO ENTERTAINMENT
//            //1 James Bond
////Medium
//            questionRepositories.questionCreator("In Italian translation who is Mr. Kiss Kiss Bang Bang?","James Bond","Keanu Reeves","Chris Pratt","Adam Sandler","James Bond","James Bond is an intelligence officer in the Secret Intelligence Service, commonly known as MI6. Bond is known by his code number, 007, and was a Royal Naval Reserve Commander.","james_bond_1.png",8,2);
//
////2 Chris Martin
////Difficult
//            questionRepositories.questionCreator("Which rock star was Jennifer Lawrence rumored to have been dating this year?","Chris Martin","Adam Lavine","Dave Grohl","Fabrizio Moretti","Chris Martin","Christopher Anthony John Martin is an English singer, songwriter, musician, record producer, and philanthropist. He is the lead singer and co-founder of the British rock band Coldplay. ","chris_martin_2.png",8,3);
//
////3 Barbados
////Easy
//            questionRepositories.questionCreator("Which island nation is pop star Rihanna from?","Barbados","Bahamas","Jamaica","Dominican Republic","Barbados","Rihanna is from the beautiful island of Barbados. Also, her first name is actually Robyn — Rihanna is her middle name!","barbados_3.png",8,1);
//
////4 Venice
////Difficult
//            questionRepositories.questionCreator("Do you know which magical Italian city George Clooney and his new wife Amal decided to get married in?","Venice","Milan","Rome","Paris","Venice","Venice, of course! The two lovebirds invited their closest friends and family members to the beautiful city for a week to celebrate their nuptials.","venice_4.png",8,3);
//
////5 Hershlag
////Difficult
//            questionRepositories.questionCreator("What\'s Natalie Portman\'s actual last name?","Hershlag","Douglas","Portman","Hershing","Hershlag","Natalie\'s real last name is Hershlag. She changed it to Portman, which is her grandmother\'s maiden name, when she started to act.","hershlag_5.png",8,3);
//
////6 Shailene Woodley
////Difficult
//            questionRepositories.questionCreator("Which accomplished actor DIDN\'T change her first name?","Shailene Woodley","Julianne Moore","Meryl Streep","Tina Fey","Shailene Woodley","Rising star Shailene Woodley is the only star on this list to have kept her first name. Julianne\'s real name is Julie Anne Smith, Meryl\'s is Mary, and Tina\'s is Elizabeth StamatinaEmil.","shailene_woodley_6.png",8,3);
//
////7 Ben Gibbard
////Difficult
//            questionRepositories.questionCreator("Do you know which Indie front man Zooey Deschanel used to be married to?","Ben Gibbard","Justin Vernon","Mat Kearney","Luke Pritchard","Ben Gibbard","Ben Gibbard is an American singer, songwriter and guitarist. He is best known as the lead vocalist and guitarist of the indie rock band Death Cab for Cutie, with which he has recorded eight studio albums, and as one half of the electronica act the Postal ","ben_gibbard_7.png",8,3);
//
////8 Julia Roberts
////Difficult
//            questionRepositories.questionCreator("Which actor was country singer Lyle Lovett once married to?","Julia Roberts","Catherine Zeta-Jones","Melanie Griffith","Meg Ryan","Julia Roberts","Julia Roberts is an Academy Award-winning actress and one of Hollywood\'s top stars, known for such films as \'Steel Magnolias,\' \'Pretty Woman\' and \'Erin Brockovich.","julia_roberts_8.png",8,3);
//
////9 Two thousand Five
////Easy
//            questionRepositories.questionCreator("In what year did Angelina Jolie and Brad Pitt get together?","Two thousand Five","Two thousand Six","Two thousand Seven","Two thousand Four","Two thousand Five","Brad Pitt is said to be furious that his estranged wife Angelina Jolie has started going public with their divorce because it\'s \"terrible\" for their children.","two_thousand_five_9.png",8,1);
//
////10 Niall Horan
////Easy
//            questionRepositories.questionCreator("Which One Direction member is Ed Sheeran\'s \"Don\'t\" reportedly about?","Niall Horan","Harry Styles","Zayn Malik","Louis Tomlinson","Niall Horan","Niall James Horan was born on 13 September 1993 in Mullingar, Ireland. His parents, Bobby Horan and Maura Gallagher, divorced when he was five years old, so he and his brother Greg Horan lived with their mother for a year.","niall_horan_10.png",8,1);
//
////11 Jake Gyllenhaal
////Difficult
//            questionRepositories.questionCreator("What about Taylor Swift\'s song \"We Are Never Ever Getting Back Together\"?","Jake Gyllenhaal","Joe Jonas","Conor Kennedy","Harry Styles","Jake Gyllenhaal","Jacob Benjamin Gyllenhaal was born in Los Angeles, California, to producer/screenwriter Naomi Foner (née Achs) and director Stephen Gyllenhaal. He is the brother of actress Maggie Gyllenhaal, who played his sister in Donnie Darko (2001).","jake_gyllenhaal_11.png",8,3);
//
////12 Janet Leigh
////
//            questionRepositories.questionCreator("Which famous Hitchcock leading lady is the mother of Jamie Lee Curtis?","Janet Leigh","Judy Garland","Tippi Hedren","Grace Kelly","Janet Leigh","Janet Leigh was an American actress, singer, dancer, and author. Raised in Stockton, California, by working-class parents, Leigh was discovered at age eighteen by actress Norma Shearer, who helped her secure a contract with Metro-Goldwyn-Mayer.","janet_leigh_12.png",8,2);
//
////13 Jennifer Lopez
////Easy
//            questionRepositories.questionCreator("Who was the first person to have a No. 1 album and a No. 1 film in the same week?","Jennifer Lopez","Beyonce","Jennifer Hudson","Will Smith","Jennifer Lopez","Jennifer Lynn Lopez is an American singer, actress, dancer and producer. In 1991, Lopez began appearing as a Fly Girl dancer on In Living Color, where she remained a regular until she decided to pursue an acting career in 1993.","jennifer_lopez_13.png",8,1);
//
////14 Jonah Hill
////Difficult
//            questionRepositories.questionCreator("Do you know which funnyman officiated Adam Levine\'s wedding?","Jonah Hill","Michael Cera","Channing Tatum","Seth Rogen","Jonah Hill","Turns out Jonah Hill and Adam Levine have been so close since high school that the Maroon 5 singer asked Hill to officiate his 2014 wedding to Victoria\'s Secret model Behati Prinsloo.","jonah_hill_14.png",8,3);
//
////15 Ryan Reynolds and Scarlett Johansson
////Medium
//            questionRepositories.questionCreator("Plenty of celebs find love through work. But which couple DIDN\'T meet on set?","Ryan Reynolds and Scarlett Johansson","Ben Affleck and Jennifer Garner","Bradley Cooper and Zoe Saldana","Matthew Rhys and Keri Russell","Ryan Reynolds and Scarlett Johansson","Ryan Reynolds and Scarlett Johansson — married from 2008 until 2011 — didn\'t meet while working together. Actually, how did they meet? Anyway, Ben and Jen met on Daredevil, though it took them a few years to figure out they were right for each other.","ryan_reynolds_and_scarlett_johansson_15.png",8,2);
//
////16 Dwyane Wade
////Easy
//            questionRepositories.questionCreator("Which NBA star did actor Gabrielle Union marry earlier this year?","Dwyane Wade","Kobe Bryant","LeBron James","Blake Griffin","Dwyane Wade","Well, considering the fact that Gabrielle is pretty smokin\', it only makes sense that her husband is Dwyane Wade (he plays for the Miami Heat). The two lovebirds got married on August 30, 2014.","dwyane_wade_16.png",8,1);
//
////17 Amy Adams
////Difficult
//            questionRepositories.questionCreator("Do you know which of these actors was born outside of the United States?","Amy Adams","Lucy Liu","Brad Pitt","Zoe SalPitt","Amy Adams","Amy Lou Adams is an American actress. Known for both her comedic and dramatic performances, Adams is, as of 2017, among the highest-paid actresses in the world.","amy_adams_17.png",8,3);
//
////18 New Jersey
////Medium
//            questionRepositories.questionCreator("Meryl Streep, Shaquille O\'Neal, and Whitney Houston are all from which state?","New Jersey","New York","California","Massachusetts","New Jersey","New Jersey is a state in the Mid-Atlantic region of the Northeastern United States.","new_jersey_18.png",8,2);
//
////19 Santa Barbara
////Easy
//            questionRepositories.questionCreator("You know that Katy Perry is a California girl. But do you know which city she\'s from?","Santa Barbara","San Diego","Santa Cruz","San Francisco","Santa Barbara","Katheryn Elizabeth Hudson, known professionally as Katy Perry, is an American singer, songwriter, and television personality. After singing in church during her childhood, she pursued a career in gospel music as a teenager.","santa_barbara_19.png",8,1);
//
////20 Rob
////Easy
//            questionRepositories.questionCreator("Who\'s the youngest Kardashian?","Rob","Kendall","Kim","Khloe","Rob","Robert Arthur Kardashian is an American television personality and businessman. He is known for appearing on Keeping Up with the Kardashians, a reality television series that centers upon his family, as well as its spin-offs.","rob_20.png",8,1);
//
////21 Versace
////Medium
//            questionRepositories.questionCreator("What is the last name of fashion designer Donatella, sister of the murdered Gianni?","Versace","Gucci","Dior","Lacoste","Versace","Donatella Francesca Versace is an Italian fashion designer and current vice president of the Versace Group, as well as its chief designer.","versace_21.png",8,2);
//
////22 Elvis Presley
////Easy
//            questionRepositories.questionCreator("Who was the king of rock and roll?","Elvis Presley","Bon Jovi","Nirvana","Metallica","Elvis Presley","Elvis Aaron Presley was an American singer and actor. Regarded as one of the most significant cultural icons of the 20th century, he is often referred to as the \"King of Rock and Roll\" or simply \"the King\".","elvis_presley_22.png",8,1);
//
////23 Guns and Roses
////Easy
//            questionRepositories.questionCreator("In 1991, which famous rock n roll band resurrected Bob Dylan\'s Knockin on Heaven\'s Door?","Guns and Roses","Red hot chili pepper","Nirvana","Green Day","Guns and Roses","Guns N\' Roses, often abbreviated as GNR, is an American hard rock band from Los Angeles, California, formed in 1985. When they signed to Geffen Records in 1986, the band comprised vocalist Axl Rose, lead guitarist Slash, rhythm guitarist Izzy Stradlin, bassist Duff McKagan, and drummer Steven Adler.","guns_and_roses_23.png",8,1);
//
////24 Pia Wurtzbach
////Easy
//            questionRepositories.questionCreator("Miss Universe 2015","Pia Wurtzbach","Paulina Vega","Iris Mittenaere","Olivia Culpo","Pia Wurtzbach","Pia Alonzo Wurtzbach, formerly known in the Philippine entertainment and modeling industry as Pia Romero, is a German-Filipina beauty pageant titleholder, model and actress.","pia_wurtzbach_24.png",8,1);
//
////25 Tim Robbins
////Difficult
//            questionRepositories.questionCreator("In the 1994 movie The Shawshank Redemption one of the lead male roles was played by?","Tim Robbins","Jack Robinson","Elijah Wood","Josh Hartnett","Tim Robbins","Timothy Francis Robbins is an American actor, screenwriter, director, producer, and musician. He is well known for his portrayal of Andy Dufresne in the prison drama film The Shawshank Redemption.","tim_robbins_25.png",8,3);
//
////26 Clint Eastwood
////Difficult
//            questionRepositories.questionCreator("In the 1966 classic western The Good, the Bad and the Ugly, who played the key actor?","Clint Eastwood","Tom Felton","Chuck Norris J","James Caan","Clint Eastwood","Clinton Eastwood Jr. is an American actor, filmmaker, musician, and political figure. ","clint_eastwood_26.png",8,3);
//
////27 Marlon Brando
////Medium
//            questionRepositories.questionCreator("In the 1972 movie The Godfather who played the Godfather?","Marlon Brando","James Dean","Robert Duvall","James Caan","Marlon Brando","Marlon Brando Jr. was an American actor and film director.","marlon_brando_27.png",8,2);
//
////28 Heath Ledger
////Easy
//            questionRepositories.questionCreator("In the brilliant 2008 movie The Dark Knight who plays the bad guy The Joker?","Heath Ledger","Morgan Freeman","Michael Caine","Maggie Gyllenhaal","Heath Ledger","Heath Andrew Ledger  an Australian actor and director. After performing roles in several Australian television and film productions during the 1990s, Ledger left for the United States in 1998 to further develop his film career.","heath_ledger_28.png",8,1);
//
////29 Leonardo DiCaprio
////Easy
//            questionRepositories.questionCreator("In the movie Inception from 2010 who played the main actor?","Leonardo DiCaprio","Vin Diesel","Robert De Niro","Laurence Fishburne","Leonardo DiCaprio","Leonardo Wilhelm DiCaprio is an American actor and film producer. DiCaprio began his career by appearing in television commercials in the late 1980s.","leonardo_dicaprio_29.png",8,1);
//
////30 Keanu Reeves
////Easy
//            questionRepositories.questionCreator("In the 1999 movie The Matrix the lead male role was played by?","Keanu Reeves","Elijah Wood","Orlando Bloom","Andy Wachowski","Keanu Reeves","Keanu Charles Reeves is a Canadian actor, director, producer, and musician.","keanu_reeves_30.png",8,1);
//
////31 Gwyneth Paltrow
////Difficult
//            questionRepositories.questionCreator("In the 1995 movie Se7en the lead female actress was?","Gwyneth Paltrow","Selena Gomez","Demi Lovato","Jane Fonda","Gwyneth Paltrow","Gwyneth Kate Paltrow is an American actress, singer, and food writer. ","gwyneth_paltrow_31.png",8,3);
//
////32 Tom Hanks
////Medium
//            questionRepositories.questionCreator("In the 1994 movie Forrest Gump, who played the lead actor?","Tom Hanks","Jean Reno","Clint Eastwood","Gary Oldman","Tom Hanks","Thomas Jeffrey Hanks is an American actor and filmmaker. Hanks is known for his comedic and dramatic roles in such films.","tom_hanks_32.png",8,2);
//
////33 Anthony Perkins
////Difficult
//            questionRepositories.questionCreator("In the 1960 movie Psycho who played Norman Bates?","Anthony Perkins","Sam Loomis","Simon Oakland","Frank Albertson","Anthony Perkins","Anthony Perkins was an American actor and singer. He was nominated for the Academy Award for Best Supporting Actor for his second film, Friendly Persuasion.","anthony_perkins_33.png",8,3);
//
////34 Jean Reno
////Difficult
//            questionRepositories.questionCreator("In the 1994 movie Leon who played the role of Leon?","Jean Reno","Danny Aiello","Peter Appel","Gary Oldman","Jean Reno","Juan Moreno y Herrera-Jiménez, known as Jean Reno, is a French actor of Spanish descent. ","jean_reno_34.png",8,3);
//
////35 Megan Mylan
////Difficult
//            questionRepositories.questionCreator("The Oscar winning short documentary film ‘Smile Pinki’ (2008) was directed by which director?","Megan Mylan","Michael Curtiz","Charlie Chaplin","Alfred Hitchcock","Megan Mylan","Megan Mylan is an American documentary film director, known for her films Lost Boys of Sudan and the 2008 Academy Award-winning Smile Pinki.","megan_mylan_35.png",8,3);
//
////36 Nineteen ninety four-1994
////Medium
//            questionRepositories.questionCreator("Aishwarya Rai was crowned Miss World in which year?","Nineteen ninety four-1994","Nineteen ninety five-1995","Nineteen ninety three-1993","Nineteen ninety six-1996","Nineteen ninety four-1994","Aishwarya Rai, also known by her married name Aishwarya Rai Bachchan, is an Indian actress, model and the winner of the Miss World 1994 pageant.","nineteen_ninety_four_1994_36.png",8,2);
//
////37 Dadasaheb Phalke
////Difficult
//            questionRepositories.questionCreator("Who is known as the \'father of Indian Cinema\'","Dadasaheb Phalke","Dadasaheb Torne","Satyajit Ray","V. Shantaram","Dadasaheb Phalke","Dhundiraj Govind Phalke, popularly known as Dadasaheb Phalke, was an Indian producer-director-screenwriter, known as the Father of Indian cinema.","dadasaheb_phalke_37.png",8,3);
//
////38 Prithviraj Kapoor
////Difficult
//            questionRepositories.questionCreator("Who played the role of Emperor Akbar in the movie \'Mughal e Azam\'","Prithviraj Kapoor","Raj Kapoor","Dilip Kumar","Murad","Prithviraj Kapoor","Prithviraj Kapoor was an actor and director, known for Mughal-E-Azam (1960), Maharathi Karna (1944) and Bidyapati (1937). He was married to Rama Kapoor.","prithviraj_kapoor_38.png",8,3);
//
////39 Bhanu Athaiya
////Difficult
//            questionRepositories.questionCreator("First Indian to win an Oscar award.","Bhanu Athaiya","Resul Pookutty","Amitabh Bachchan","AR Rahman","Bhanu Athaiya","Bhanu Athaiya  is an Indian costume designer, having worked in over 100 films, since the 1950s, with noted filmmakers like Guru Dutt, Yash Chopra, Raj Kapoor, Ashutosh Gowariker, and international directors like Conrad Rooks and Richard Attenborough.","bhanu_athaiya_39.png",8,3);
//
////40 Bradley Simpson
////Easy
//            questionRepositories.questionCreator("Who is the lead singer of The Vamps?","Bradley Simpson","James McVey","Connor Ball","Tistan Evans","Bradley Simpson","Bradley grew up in Birmingham and was born in The Royal Town of Sutton Coldfield, United Kingdom. His favorite band is Artic Monkeys.He is in a band with James McVey, Connor Ball, and Tristan Evans. He is the lead singer for The Vamps. ","bradley_simpson_40.png",8,1);
//
////41 Camila Cabello
////Easy
//            questionRepositories.questionCreator("Which American female singer features in Major Lazer\'s 2017 song \'Know No Better\'?","Camila Cabello","Katy Perry","Britney Spears","Shakira","Camila Cabello","Karla Camila Cabello Estrabao is a Cuban-American singer and songwriter. She rose to prominence as a member of the girl group Fifth Harmony, which was formed on the second season of the American edition of The X Factor in 2012.","camila_cabello_41.png",8,1);
//
////42 Twenty Sixteen
////Easy
//            questionRepositories.questionCreator("What year did Zayn Malik release an album called \' Mind of Mine\'?","Twenty Sixteen","Twenty Fifteen","Twenty Seventeen","Twenty Eighteen","Twenty Sixteen","Zain Javadd \"Zayn\" Malik, recording mononymously as Zayn, is an English singer and songwriter. Born and raised in Bradford, West Yorkshire, Malik auditioned as a solo contestant for the British music competition The X Factor in 2010.","twenty_sixteen_42.png",8,1);
//
////43 Five
////Easy
//            questionRepositories.questionCreator("How many members were there originally in the boy band One Direction?","Five","Four","Six","Seven","Five","One Direction are an English-Irish pop boy band based in London, composed of Niall Horan, Liam Payne, Harry Styles, Louis Tomlinson, and, until his departure from the band in 2015, Zayn Malik.","five_43.png",8,1);
//
////44 Hemmings
////Easy
//            questionRepositories.questionCreator("What is Luke\'s surname from 5 Seconds of Summer: ","Hemmings","Bradley","Irwin","Hood","Hemmings","Luke Robert Hemmings (born July 16, 1996) is the rhythm guitarist and lead vocalist of 5 Seconds of Summer, along with band members Calum Hood, Michael Clifford, and Ashton Irwin.","hemmings_44.png",8,1);
//
////45 Lil Wayne
////Easy
//            questionRepositories.questionCreator("Which American hip hop artist features in DJ Khaled\'s 2017 hit \'I\'m the One\' with Quavo, Chance the rapper and Justin Bieber?","Lil Wayne","Jay-Z","Kanye West","PSY","Lil Wayne","Lil Wayne. Lil Wayne, byname of Dwayne Michael Carter, Jr., also called Weezy, is an American rapper who became one of the top-selling artists in hip-hop in the early 21st century.","lil_wayne_45.png",8,1);
//
////46 Ed Sheeran
////Easy
//            questionRepositories.questionCreator("Which British male singer released the following songs \'Shape of You\', \'Perfect\' and \'Castle on the Hill\'?","Ed Sheeran","Charly Poth","Jason Derulo","Bruno Mars","Ed Sheeran","Edward Christopher Sheeran, MBE is an English singer, songwriter, guitarist, record producer, and actor. Sheeran was born in Halifax, West Yorkshire, and raised in Framlingham, Suffolk.","ed_sheeran_46.png",8,1);
//
////47 The X Factor
////
//            questionRepositories.questionCreator("What talent show did Little Mix win?","The X Factor","The Voice","Britain Got Talent","None of the Above","The X Factor","The X Factor is a television music competition franchise created by British producer Simon Cowell and his company SYCOtv. It originated in the United Kingdom, where it was devised as a replacement for Pop Idol (2001–2003), and has been adapted in various countries.","the_x_factor_47.png",8,1);
//
////48 Ariana Grande
////Easy
//            questionRepositories.questionCreator("Who released a song in 2016 called \'Side to Side\'?","Ariana Grande","Camila Cabello","Selena Gomez","Becky G.","Ariana Grande","Ariana Grande-Butera is an American singer, songwriter and actress. She began her career in 2008 in the Broadway musical 13, before playing the role of Cat Valentine in the Nickelodeon television series Victorious and in the spinoff Sam & Cat.","ariana_grande_48.png",8,1);
//
////49 Lea Salonga
////Easy
//            questionRepositories.questionCreator("She won two Tonys for her performance as Kim in \"Miss Saigon\". You can also listen to her beautiful voice as Jasmine in Disney\'s \"Aladdin\" and singing Mulan\'s song, \"Reflections\".","Lea Salonga","Britney Spears","Regina Belle","Christina Aguilera","Lea Salonga","Maria Lea Carmen Imutan Salonga, KLD is a Filipina singer and actress best known for her roles in musical theatre, for supplying the singing voices of two Disney Princesses, and as a recording artist and television performer","lea_salonga_49.png",8,1);
//
////50 Floyd Mayweather
////Easy
//            questionRepositories.questionCreator("Top 1 World highest paid entertainer in year 2018.","Floyd Mayweather","Kylie Jenner","George Clooney","Dwayne Johnson","Floyd Mayweather","Floyd Joy Mayweather Jr. is an American professional boxing promoter and former professional boxer. He competed from 1996 to 2007 and 2009 to 2015, and made a one-fight comeback in 2017.","floyd_mayweather_50.png",8,1);
//
////51 Manny Pacquiao
////Easy
//            questionRepositories.questionCreator("Who is known as the greatest boxer of all time?","Manny Pacquiao","Floyd Mayweather","Nonito Donaire","Gerry Peñalosa","Manny Pacquiao","Emmanuel Dapidran Pacquiao, PLH is a Filipino professional boxer and politician, currently serving as a Senator of the Philippines. He currently ranks #4 in BoxRec\'s ranking of the greatest pound for pound boxers of all time.","manny_pacquiao_51.png",8,1);
//
////52 Dolphy Quizon
////Easy
//            questionRepositories.questionCreator("This hallmark of Philippine Cinema died after a long-time battle with Chronic Obstructive Pulmonary Disease.","Dolphy Quizon","Francis Magalona","Rudy Fernandez","Rico Yan","Dolphy Quizon","Rodolfo Vera Quizon Sr. known by his screen names Dolphy, Pidol, and Golay, was a Filipino comedian-actor in the Philippines and He is widely regarded as the country\'s King of Comedy.","dolphy_quizon_52.png",8,1);
//
////53 Zendee Tenefere
////Medium
//            questionRepositories.questionCreator("This Philippine singer won international attention by starring in an episode of The Ellen Degenere\'s Show.","Zendee Tenefere","KZ Tandingan","Jed Madela","Yeng Constantino","Zendee Tenefere","Zendee Rose Tenerefe is a Filipina singer, who rose to prominence after a video of her singing a karaoke version of Whitney Houston\'s \"I Will Always Love You\" was put on YouTube.","zendee_tenefere_53.png",8,2);
//
////54 Jennifer Lopez
////Medium
//            questionRepositories.questionCreator("According to Forbes 2012, who is the world\'s most powerful celebrity?","Jennifer Lopez","Justin Beiber","Ophrah Winfrey","Katty Perry","Jennifer Lopez","Jennifer Lynn Lopez is an American singer, actress, dancer and producer. In 1991, Lopez began appearing as a Fly Girl dancer on In Living Color, where she remained a regular until she decided to pursue an acting career in 1993.","jennifer_lopez_54.png",8,2);
//
////55 Praybeyt Benjamin
////Medium
//            questionRepositories.questionCreator("What is the top grossing Philippine film of all time?","Praybeyt Benjamin","Tanging Ina","No Other Woman","Bonifacio ang unang Pangulo","Praybeyt Benjamin","The Amazing Praybeyt Benjamin is a 2014 comedy film written and directed by Wenn V. Deramas. It is the sequel to The Unkabogable Praybeyt Benjamin. The film is one of the official entries to the 40th Metro Manila Film Festival. Vice Ganda reprised his role as Colonel Benjamin \"Benjie\" Santos VIII.","praybeyt_benjamin_55.png",8,2);
//
////56 Mel B
////Medium
//            questionRepositories.questionCreator("Which Spice girl was the first to marry?","Mel B","Victoria Beckham","Emma Bunton","Melanie C","Mel B","Melanie Janine Brown, also known as Mel B or Melanie B, is an English singer, songwriter, actress, and television personality.","mel_b_56.png",8,2);
//
////57 Fashion Designer
////Medium
//            questionRepositories.questionCreator("Giorgio Armani is famous in which field?","Fashion Designer","Director","Model","Actor","Fashion Designer","Giorgio Armani is an Italian fashion designer. He is known today for his clean, tailored lines. He formed his company, Armani, in 1975, and by 2001 was acclaimed as the most successful designer of Italian origin.","fashion_designer_57.png",8,2);
//
////58 Campbell
////Difficult
//            questionRepositories.questionCreator("Which supermodel Naomi wrote a novel called Swan?","Campbell","Watts","Rowling","Austen","Campbell","Naomi Elaine Campbell is an English model, actress, and singer.","campbell_58.png",8,3);
//
////59 Enrique Iglesias
////Medium
//            questionRepositories.questionCreator("This Latin superstar took the whole world by storm in 1999. He is the \"hero\" you might wish to \"escape\" with, and he is also the son of a famous crooner and a Filipina.","Enrique Iglesias","Ricky Martin","Enrique Gil","Bamboo","Enrique Iglesias","Enrique Miguel Iglesias Preysler is a Spanish singer, songwriter, actor and record producer. He is widely regarded as the King of Latin Pop.","enrique_iglesias_59.png",8,2);
//
////60 Billy Crawford
////Medium
//            questionRepositories.questionCreator("He is American pop star made a sensation in Europe, and you\'ve probably kept on \"trackin\" with him. He started out, however, as a child star in the Philippines before moving to America.","Billy Crawford","Ricky Martin","Nick Carter","Justin Timberlake","Billy Crawford","Billy Joe Ledesma Crawford is a Filipino-American singer, dancer, songwriter, actor and TV host.","billy_crawford_60.png",8,2);
//
////61 Paolo Montalban
////Medium
//            questionRepositories.questionCreator("He kicked some butt in the TV Series \"Mortal Kombat\" and was Brandy\'s Prince Charming in \"Cinderella\", where Whitney Houston was the Fairy Godmother.","Paolo Montalban","Ryan Gosling","Zen Gesner","Krista Ranillo","Paolo Montalban","Paolo also made a Filipino film, set in the US, about Filipino migrants who search for the American Dream. Its title is \"American Adobo\".","paolo_montalban_61.png",8,2);
//
////62 Rob Schneider
////Difficult
//            questionRepositories.questionCreator("This \"Male Gigolo\" and \"Animal\" is also in \"50 First Dates\"... and he happens to have Filipino blood, can you name him?","Rob Schneider","Nick Hawk","Brace Land","Vin Armani","Rob Schneider","Robert Michael Schneider is an American actor, comedian, screenwriter, and director.","rob_schneider_62.png",8,3);
//
////63 Fashion Designer
////Medium
//            questionRepositories.questionCreator("Calvin Klein best fits which of these descriptions?","Fashion Designer","Played Detective On","Newspaper Advice Columnist","19th Century English Novelist","Fashion Designer","Calvin Richard Klein is a Hungarian-American fashion designer who launched the company that would later become Calvin Klein Inc., in 1968. ","fashion_designer_63.png",8,2);
//
////64 Leonardo da Vinci
////Medium
//            questionRepositories.questionCreator("This genius was born in Italy in 1452 and died in France in 1519. He was a painter and inventor, among many other things. He is reported to have been left handed.","Leonardo da Vinci","Leonardo di Caprio","Leonardo Bistolfi","Leonardo Sandri","Leonardo da Vinci","Leonardo da Vinci, one of the most brilliant men in history, wrote from right to left and in mirror image. This is certainly easier for a person using their left hand, especially writing with ink, although it appears he may have been ambidextrous.","leonardo_da_vinci_64.png",8,2);
//
////65 Kim Kardashian
////Difficult
//            questionRepositories.questionCreator("Who didn’t announce she was pregnant in 2017?","Kim Kardashian","Binky Felstead","Heidi Montag","Chrissy Teigen","Kim Kardashian","Kimberly Noel Kardashian West is an American reality television personality, entrepreneur and socialite","kim_kardashian_65.png",8,3);
//
////66 Andy Jordan
////Difficult
//            questionRepositories.questionCreator("Which reality star didn’t make music in 2017?","Andy Jordan","Steph Davis","Megan McKenna","Chris Hughes and Kem Cetinay","Andy Jordan","Andrew Mark “Andy” Jordan is an English actor and singer-songwriter. ","andy_jordan_66.png",8,3);
//
////67 Meghan Markle
////Medium
//            questionRepositories.questionCreator("Who was the most googled celeb of 2017?","Meghan Markle","Beyoncé","Kevin Spacey","Selena Gomez","Meghan Markle","Meghan, Duchess of Sussex, is an American-born member of the British royal family and a former film and television actress. Markle was born and raised in Los Angeles, California, and is of mixed-race heritage.","meghan_markle_67.png",8,2);
//
////68 Moonlight
////Difficult
//            questionRepositories.questionCreator("What film won Best Picture at the Oscars?","Moonlight","La La Land","Call Me By Your Name","Dunkirk","Moonlight","Moonlight is a 2016 American coming-of-age drama film written and directed by Barry Jenkins, based on Tarell Alvin McCraney\'s unpublished semi-autobiographical play In Moonlight Black Boys Look Blue.","moonlight_68.png",8,3);
//
////69 Selena Gomez
////Medium
//            questionRepositories.questionCreator("Which celeb produced 13 Reasons Why?","Selena Gomez","Demi Lovato","Taylor Swift","Katherine Langford","Selena Gomez","Selena Marie Gomez is an American singer, actress, and producer.","selena_gomez_69.png",8,2);
//
////70 Camila Cabello
////Medium
//            questionRepositories.questionCreator("Who was the most tweeted about celeb of 2017?","Camila Cabello","Donald Trump","Meghan Markle","Harry Styles","Camila Cabello","Camila Cabello is mainly a pop and R&B singer, influenced by Latin music. She incorporated elements of reggaeton, hip hop and dancehall in her first album.","camila_cabello_70.png",8,2);
//
////71 Beyonce
////Medium
//            questionRepositories.questionCreator("Who was announced to play Nala in the upcoming live action remake of The Lion King?","Beyonce","Meryl Streep","Blake Lively","Gigi Hadid","Beyonce","Beyoncé Giselle Knowles-Carter is an American singer, songwriter, performer, and actress. Born and raised in Houston, Texas, Beyoncé performed in various singing and dancing competitions as a child.","beyonce_71.png",8,2);
//
////72 Suits
////Difficult
//            questionRepositories.questionCreator("Which US TV show did Meghan Markle announce she was leaving?","Suits","The Sinner","Dynasty","Grey's Anatomy","Suits","Suits is an American drama series which Meghan Markle plays Rachel Zane.","suits_72.png",8,3);
//
////73 Kendall Jenner
////Medium
//            questionRepositories.questionCreator("Who was the highest paid model of the year 2017?","Kendall Jenner","Jourdan Dunn","Gigi Hadid","Alessandra Ambrosia","Kendall Jenner","Kendall Nicole Jenner is an American model and television personality. ","kendall_jenner_73.png",8,2);
//
////74 Four Hundred Twenty Million
////Difficult
//            questionRepositories.questionCreator("How much was Kylie Cosmetics estimated at in 2017?","Four Hundred Twenty Million","Three Hundred Million","One Billion","Seven Hunder Sixty Million","Four Hundred Twenty Million","Kylie Cosmetics... It has made Kylie a fortune—and the company is only two years old. Kylie\'s recent Forbes profile revealed that she\'s sold more than $630 million worth of makeup, \"including an estimated $330 million in 2017.\"","four_hundred_twenty_million_74.png",8,3);
//
////75 Captain America Civil War
////Easy
//            questionRepositories.questionCreator("What was the top-grossing film of 2016, according to Box Office Mojo?","Captain America Civil War","Deadpool","Zootopia","Finding Dory","Captain America Civil War","Iron Man and Captain America\'s main conflict in \"Civil War\" is sparked by Bucky Barnes (Sebastian Stan), also known as the Winter Soldier.","captain_america_civil_war_75.png",8,1);
//
////76 Jennifer Lawrence
////Difficult
//            questionRepositories.questionCreator("Who does Forbes magazine list as the highest-paid actress of 2016?","Jennifer Lawrence","Julia Roberts","Gwyneth Paltrow","Jennifer Aniston","Jennifer Lawrence","Jennifer Shrader Lawrence is an American actress. Her films have grossed over $5.7 billion worldwide, and she was the highest-paid actress in the world in 2015 and 2016.","jennifer_lawrence_76.png",8,3);
//
////77 X-Men Apocalypse
////Easy
//            questionRepositories.questionCreator("Which X-Men film was released in 2016?","X-Men Apocalypse","X-Men: Days of Future Past","X-Men: First Class","X-Men: The Last Stand","X-Men Apocalypse","Apocalypse (En Sabah Nur) is a fictional supervillain appearing in comic books published by Marvel Comics. He is one of the world\'s first mutants, and was originally a principal villain for the original X-Factor team and now for the X-Men and related spinoff teams.","x_men_apocalypse_77.png",8,1);
//
////78 Tim Burton
////Difficult
//            questionRepositories.questionCreator("Who directed “Miss Peregrine’s Home for Peculiar Children”?","Tim Burton","Wes Anderson","Martin Scorsese","Quentin Tarantino","Tim Burton","Tim Burton was born on August 25, 1958, in Burbank, California. After majoring in animation at the California Institute of Arts, he worked as a Disney animator for less than a year before striking out on his own.","tim_burton_78.png",8,3);
//
////79 Natalie Portman
////Difficult
//            questionRepositories.questionCreator("Who stars as former First Lady Jacqueline Kennedy in the 2016 release “Jackie”?","Natalie Portman","Sandra Bullock","Renee Zellweger","Nicole Kidman","Natalie Portman","Natalie Portman is an actress, film producer and director with dual Israeli and American citizenship. She is the recipient of several accolades, including an Academy Award and two Golden Globe Awards.","natalie_portman_79.png",8,3);
//
////80 The Angry Birds Movie
////Easy
//            questionRepositories.questionCreator("Which 2016 film began life as a video game?","The Angry Birds Movie","Trolls","Pete’s Dragon","Storks","The Angry Birds Movie","Angry Birds will officially launch—via slingshot—onto the big screen for “The Angry Birds Movie,” starring the voices of comedians Jason Sudeikis, Josh Gad, Bill Hader, Maya Rudolph and Danny McBride.","the_angry_birds_movie_80.png",8,1);
//
////81 Chris Rock
////Difficult
//            questionRepositories.questionCreator("Which comic hosted the 2016 Academy Awards?","Chris Rock","Steve Martin","Jon Stewart","Billy Crystal","Chris Rock","Christopher Julius Rock III is an American comedian, actor, writer, producer, and director.","chris_rock_81.png",8,3);
//
////82 Rob
////Difficult
//            questionRepositories.questionCreator("Which Kardashian broke up with their partner following the birth of their child?","Rob","Kim","Kourtney","Khloe","Rob","Robert Arthur Kardashian is an American television personality and businessman. He is known for appearing on Keeping Up with the Kardashians, a reality television series that centers upon his family, as well as its spin-offs.","rob_82.png",8,3);
//
////83 Tom Hiddleston
////Medium
//            questionRepositories.questionCreator("What British actor was linked to Taylor Swift following her breakup with Calvin Harris?","Tom Hiddleston","Benedict Cumberbatch","James Corden","Daniel Craig","Tom Hiddleston","Thomas William Hiddleston is an English actor, film producer and musician.","tom_hiddleston_83.png",8,2);
//
////84 Negan
////Difficult
//            questionRepositories.questionCreator("Who killed Glen on \"The Walking Dead\" in one of the worst-kept secrets in television?","Negan","Carol","The Governor","Rick Grimes","Negan","Negan is a fictional character in the comic book series The Walking Dead and in the television series of the same name.","negan_84.png",8,3);
//
////85 Jon Snow
////Medium
//            questionRepositories.questionCreator("Who returned from the dead on \"Game of Thrones\"?","Jon Snow","Jon Hamm","Ramsay","Michonne","Jon Snow","Jon Snow, born Aegon Targaryen, is the son of Lyanna Stark and Rhaegar Targaryen, the late Prince of Dragonstone.","jon_snow_85.png",8,2);
//
////86 Simone Biles
////Difficult
//            questionRepositories.questionCreator("What Olympic gymnast took center stage at the Rio Olympics in 2016?","Simone Biles","Lebron James","Mary Lou Retton","Tonya Harding","Simone Biles","Simone Arianne Biles is an American artistic gymnast. Biles is the 2016 Olympic individual all-around, vault and floor gold medalist, and balance-beam bronze medalist.","simone_biles_86.png",8,3);
//
////87 Adele
////Medium
//            questionRepositories.questionCreator("Who had the top-grossing tour of 2016, according to StubHub?","Adele","Beyonce","Bruce Springsteen","Luke Bryan","Adele","Adele Laurie Blue Adkins MBE is an English singer and songwriter.","adele_87.png",8,2);
//
////88 Kate Beckinsale
////Medium
//            questionRepositories.questionCreator("British actress famous as the female star of the Underworld series of films.","Kate Beckinsale","Emily Blunt","Emma Watson","Julie Andrews","Kate Beckinsale","Kathrin Romary Beckinsale is an English actress.","kate_beckinsale_88.png",8,2);
//
////89 Titanic
////Easy
//            questionRepositories.questionCreator("Kate Winslet starred as Rose DeWitt Bukater in this story of an ill fated voyage. Name the film.","Titanic","The Reader","The Mountain between us","Divergent","Titanic","Kate Elizabeth Winslet is an English actress. She made her movie debut as Juliet Hulme in Peter Jackson\'s Heavenly Creatures (1994). She is best known for her starring role in the American movie, Titanic.","titanic_89.png",8,1);
//
////90 Marian Rivera
////Easy
//            questionRepositories.questionCreator("Asia’s Primetime Queen.","Marian Rivera","Barbie Forteza","Angel Locsin","Yassi Pressman","Marian Rivera","Marian Rivera Gracia-Dantes or Marian Rivera y Gracia y Dantes, known professionally as Marian Rivera, is a Spanish Filipino commercial model and actress","marian_rivera_90.png",8,1);
//
////91 Regine Velasquez
////Easy
//            questionRepositories.questionCreator("Asia’s Song Bird.","Regine Velasquez","Lea Salonga","Sarah Geronimo","Yeng Constantino","Regine Velasquez","Regina Encarnacion Ansong Velasquez is a Filipino singer, actress and record producer. She gained recognition by winning both the 1984 Ang Bagong Kampeon and the 1989 Asia-Pacific Song Contest, representing the Philippines in the latter.","regine_velasquez_91.png",8,1);
//
////92 Eat Bulaga
////Easy
//            questionRepositories.questionCreator("The longest running noontime show in the Philippines.","Eat Bulaga","Asap","Wowowie","It’s Showtime","Eat Bulaga","Eat Bulaga! is a Philippine television variety show broadcast by GMA Network. Produced by Television And Production Exponents Inc., it is the longest running noontime show in the Philippines. The show has been hosted since its inception by Tito Sotto, Vic Sotto and Joey De Leon.","eat_bulaga_92.png",8,1);
//
////93 TV Patrol
////Easy
//            questionRepositories.questionCreator("The longest-running Filipino news program in the Philippines.","TV Patrol","Saksi","Bandila","Aksyon","TV Patrol","TV Patrol is the longest-running Filipino news program has been on air since March 2, 1987. Its original anchors were Noli de Castro, Robert Arevalo, Mel Tiangco, and Angelique Lazo.  Currently, the show is now anchored by Noli de Castro ,Ted Failon, and Bernadette Sembrano .Noli de Castro ends the program with his famous catchphrase, “Magandang Gabi, Bayan”.","tv_patrol_93.png",8,1);
//
////94 January 26, 1958
////Difficult
//            questionRepositories.questionCreator("On what day was Ellen DeGeneres born?","January 26, 1958","March 05, 1960","April 20, 1956","December 25, 1961","January 26, 1958","Ellen Lee DeGeneres is an American comedian, television host, actress, writer, producer, and LGBT activist.","january_26_1958_94.png",8,3);
//
////95 Mississippi
////Medium
//            questionRepositories.questionCreator("Where was Oprah Winfrey born?","Mississippi","New York  ","Illinois","Georgia","Mississippi","Oprah Winfrey was born in the rural town of Kosciusko, Mississippi, on January 29, 1954. In 1976, Winfrey moved to Baltimore, where she hosted a hit television chat show, People Are Talking.","mississippi_95.png",8,2);
//
////96 Prince William
////Easy
//            questionRepositories.questionCreator("Who is the eldest son of Princess Diana and Prince Charles of Wales?","Prince William","Prince Charles","Prince Harry","Prince George","Prince William","Prince William, Duke of Cambridge, KG, KT, PC, ADC is a member of the British royal family. He is the elder son of Charles, Prince of Wales, and Diana, Princess of Wales.","prince_william_96.png",8,1);
//
////97 Kris Aquino
////Easy
//            questionRepositories.questionCreator("The Filipino actress dubbed as Queen of all Media.","Kris Aquino","Sharon Cuneta","Mariel Rodriguez","Angel Locsin","Kris Aquino","Kristina Bernadette Cojuangco Aquino is a Filipino talk show host, actress and producer. She has hosted talk shows and game shows, and has also starred in films and television series.","kris_aquino_97.png",8,1);
//
////98 Rosa del Rosario
////Easy
//            questionRepositories.questionCreator("First darna of the Philippines.","Rosa del Rosario","Marneth Ann","Rodelyn Sanoria","Michelle Biol","Rosa del Rosario","Two Darna films were made by Royal Films, both starring Rosa del Rosario in \"Darna\" (1951) and \"Darna at ang Babaing Lawin\" (1952). A year after Darna was first serialized in 1950 in Bulaklak Komiks, Filipinos witnessed the first marvelous flight of Darna in the big screen.","Rosa_del_Rosario_98.png",8,1);
//
//
////99 Ang Probinsyano
////Easy
//            questionRepositories.questionCreator("A television adaptation of Fernando Poe, Jr.'s action drama film, topbilled by Coco Martin.","Ang Probinsyano","Akala mo lang wala","Sino ka?","Bakit ako mahihiya","Ang Probinsyano","Ang Probinsyano has three story arcs. Book 1, which contained the first and second seasons, ran from its debut on September 28, 2015 until May 24, 2017. Book 2 opened with the third season and fourth season which officially began on May 25, 2017. Book 3 opened with the series' fifth season and focuses on a larger political drama, now involving the President and Vice President of the Philippines.","coco_99.png",8,1);
//
//
////100 Meteor Garden
////Medium
//            questionRepositories.questionCreator("A 2001 Taiwanese drama starring Barbie Shu, Jerry Yan, Vic Zhou, Vanness Wu and Ken Chu. It is loosely based on Japanese shōjo manga.","Meteor Garden","The Grudge","Sadako","The Ring","Meteor Garden","The story centers around a poor teenage girl Shān Cài (Barbie Shu), who at the insistence of her parents goes to a university for rich people. The university is dominated by Dào Míng Sì (Jerry Yan), Huā Zé Lèi (Vic Zhou), Měi Zuò (Vanness Wu) and Xī Mén (Ken Chu)—four rich, handsome but arrogant students collectively known as the 'F4', short for 'Flower 4'. They are heirs to four rich and influential families in Taiwan. They terrorize the school by handing out red cards to those they do not like, which allows other students to bully the victims until they leave the school.","meteor_100.png",8,2);
//            //TODO END OF ENTERTAINMENT
//
//            //TODO PEOPLE
//            //0 Alan Turing
//            questionRepositories.questionCreator("Father of Artificial Intelligence.","Alan Turing","John von Neuman","Warren Mc cullach","John mc Carthy","Alan Turing","Alan turing was a logician mathematician and comp. scientist was born in London in 1912,","alan_turing_1.png",1,2);
//
////1 James Hutton
//            questionRepositories.questionCreator("Who was known as the Father of geology?","James Hutton","Alfred Wegener","Charles Lyell","Hillon","James Hutton","A Scottish geologist , physician chemical manu8facturer , naturalist and experimental , agriculture","james_hutton_2.png",1,2);
//
////2 Norbert Wiener
//            questionRepositories.questionCreator("Considered to be the father of cybernatics.","Norbert Wiener","Claude Shannon","Konrad Zuse","Alan turing","Norbert Wiener","American mathematician was born on Novemb er 26, 1894 at Cambridge.","norbert_wiener_3.png",1,2);
//
////3 Susruta
//            questionRepositories.questionCreator("Father of plastic surgery.","Susruta","Hippocrates","Galen","Charaka","Susruta","Ancient Indian physician during BCE to 1000 BCE.","susruta_4.png",1,1);
//
////4 Freederic Joliot – Curie
//            questionRepositories.questionCreator("Who discovered artificial radioactivity?","Freederic Joliot – Curie","Henri Bacquerel","Marie Curie","Pierre Curie","Freederic Joliot – Curie","A French physicist","freederic_joliot_–_curie_5.png",1,1);
//
////5 G.W Leibniz
//            questionRepositories.questionCreator("Who is independently discovered calculus?","G.W Leibniz","Francis Bacon","Christian Guygens","Leonardo Fibonacci","G.W Leibniz","A german rationalist philosopher and one of the great dendissance men of Western thought.","g_w_leibniz_6.png",1,1);
//
////6 Harvey Cushing
//            questionRepositories.questionCreator("Father of brain surgery.","Harvey Cushing","Pierre Broca","Galen","Christian Barnard","Harvey Cushing","American neuron surgeon, pathologist, writer and draftsman.","harvey_cushing_7.png",1,1);
//
////7 Linus Pauling
//            questionRepositories.questionCreator("Who claimed that Vitamin c can prevent common cold?","Linus Pauling","Paul Muller","Rebe Dubos","G. natta","Linus Pauling","American chemist,biochemist peace activist,author and educator.","linus_pauling_8.png",1,2);
//
////8 William whewell
//            questionRepositories.questionCreator("Who invented the term “SCIENCE”?","William whewell","Francis Bacon","Roger Bacon","Plato","William whewell","An English polymath scientist, Anglican priest, philosopher and theologian.","william_whewell_9.png",1,2);
//
////9 Erasistratus
//            questionRepositories.questionCreator("Father of physiology.","Erasistratus","Thales","Herophilus","Anaximander","Erasistratus","A Greek anatomist and royal physician. He founded a school of anatomy in Alexandria.","erasistratus_10.png",1,3);
//
////10 Karl Jansky
//            questionRepositories.questionCreator("Who discovered radio waves coming from the sky?","Karl Jansky","Max Weber","J.S Hey","Robert Watson-Watt","Karl Jansky","An American physicist and radio engineer.","karl_jansky_11.png",1,1);
//
////11 Phan tuan
//            questionRepositories.questionCreator("Who was the first Asian astronaut to go into space?","Phan tuan","John Glenn","Ravish Malhotra","Rakesh Sharma","Phan tuan","a retired Vietnam airforce aviator.","phan_tuan_12.png",1,1);
//
////12 Alexer Leonov
//            questionRepositories.questionCreator("Who was the first man to walk in space?","Alexer Leonov","John Glenn","John Young","Alan Shepard","Alexer Leonov","A retired Soviet/Russian cosmonaut , Air Force Major general, writer and artist.","alexer_leonov_13.png",1,1);
//
////13 Valentina Tereshkova
//            questionRepositories.questionCreator("Who was the first Woman in space?","Valentina Tereshkova","Sally Ride","Anna Fisher","Rhea Seddon","Valentina Tereshkova","Retired Russian  cosmonaut, engineer and politician.","valentina_tereshkova_14.png",1,1);
//
////14 Fabian Gottlieb Bellingshausen
//            questionRepositories.questionCreator("Who was the first man to reach antartica?","Fabian Gottlieb Bellingshausen","Thomas Cook","Paul Scott","John young","Fabian Gottlieb Bellingshausen","One of Russia’s most celebrated explorers and the discovered of the continent of Antarctica.","fabian_gottlieb_bellingshausen_15.png",1,1);
//
////15 Henry Ford
//            questionRepositories.questionCreator("Who was the father of the automobile?","Henry Ford","Rudolf Diesel","Gottlieb Daimler","Carl Benz","Henry Ford","He was so much more interested in machines and building things.","henry_ford_16.png",1,1);
//
////16 Dmitri Mendeleev
//            questionRepositories.questionCreator("Who was the father of Periodic Table of elements?","Dmitri Mendeleev","Alfred Nobel","Johann Baeyer","John Glenn","Dmitri Mendeleev","He is a Russian chemist and inventor.","dmitri_mendeleev_17.png",1,1);
//
////17 Paul Ehrlich
//            questionRepositories.questionCreator("Who was the father of science chemotherapy, using chemical to treat diseases?","Paul Ehrlich","Leo Szilard","Hippocrates","Avicenna","Paul Ehrlich","He is a German , and won the Nobel prize in Physiology or Medicine for his exemplary work on immunology","paul_ehrlich_18.png",1,1);
//
////18 Edward Teller
//            questionRepositories.questionCreator("Who was the father of  hydrogen bomb?","Edward Teller","Leo Szilard","J. Robert Oppenheimer","Otto Hahn","Edward Teller","On it’s 100th birthday in 1959, Edward Teller warned the oil industry about global warming.","edward_teller_19.png",1,1);
//
////19 D.N Wadia
//            questionRepositories.questionCreator("Who was the Father of geology in India?","D.N Wadia","Mihir Sen","K.S Valdiya","M.K Bose","D.N Wadia","One among the great geologist in geological.","d_n_wadia_20.png",1,1);
//
////20 Andreas Vesalius
//            questionRepositories.questionCreator("Who was the founding Father of modern anatomy?","Andreas Vesalius","Hippocrates","Avicenna","Galen","Andreas Vesalius","Humani Corporis Fabrica (On the fabric of the human body) revolutionized the science of medicine and laid foundation for the modern human anatomy.","andreas_vesalius_21.png",1,3);
//
////21 Francis Galton
//            questionRepositories.questionCreator("Who considered to be the father of eugenics?","Francis Galton","Karl Pearson","Charles Darwin","Avicenna","Francis Galton","English explorer, anthropologist, eugenicists, geographer and meteorologist.","francis_galton_22.png",1,3);
//
////22 Walther Nernst
//            questionRepositories.questionCreator("Who discovered the Third Law of thermodynamics?","Walther Nernst","L.E Boltzmann","Count Rumford","P.M.S Blackett","Walther Nernst","The 3rd law was developed by the chemist Walther Nernst during the years 1906–12, and is therefore often referred to as Nernst\'s theorem or Nernst\'s postulate. The third law of thermodynamics states that the entropy of a system at absolute zero is a well-defined constant.","walther_nernst_23.png",1,1);
//
////23 Gerhard Domagk
//            questionRepositories.questionCreator("Who discovered sulpha drugs?","Gerhard Domagk","Robert Wilkins","Howard Florey","Alexander Fleming","Gerhard Domagk","a German pathologist and bacteriologist","gerhard_domagk_24.png",1,3);
//
////24 Anton Leeuwenhoek
//            questionRepositories.questionCreator("Who discovered one-celled animals?","Anton Leeuwenhoek","Galen","Marcello Malpighi","Charaka","Anton Leeuwenhoek","who invented or greatly improved the microscope and discovered living organisms,.","anton_leeuwenhoek_25.png",1,3);
//
////25 Autor Singh Paintal
//            questionRepositories.questionCreator("Who discovered J-receptor-the nerve terminals in lungs that cause breathlessness?","Autor Singh Paintal","Peter Medawar","B.S Anand","A.L. Hodgkin","Autor Singh Paintal","Indian Medical Scientist","autor_singh_paintal_26.png",1,3);
//
////26 Ronald Ross
//            questionRepositories.questionCreator("Who discovered that malaria is caused by a particular type of mosquito?","Ronald Ross","Christian Eijman","Charaka","Louis Pasteur","Ronald Ross","A British medical doctor who Eijkman receive Nobel Prize for physiology or Medicine in 1902 for his work in transmission of malaria.","ronald_ross_27.png",1,3);
//
////27 Christopher Vistal
//            questionRepositories.questionCreator("Programmer of this game.”?","Christopher Vistal","Gwyn Paul Dapiton","Michelle Biol","Rodelyn Sanoria","Christopher Vistal","Austrian zoologist, ethnologist and ornithologist."," Christopher_Vistal _28.png",1,1);
//
////28 I.P Pavlov
//            questionRepositories.questionCreator("Who discovered condition reflex through his experiments on dogs?","I.P Pavlov","Sigmund Freud","B.F Skinner","Carl Jung","I.P Pavlov","A Russian physiologist known primarily for his work in classical conditioning","i_p_pavlov_29.png",1,3);
//
////29 Oliver Wendell holmes
//            questionRepositories.questionCreator("Who coined the term ‘anesthesia’  for any pain-killer given during an operation?","Oliver Wendell holmes","Paul Ehrlich","Susruta","Carl Jung","Oliver Wendell holmes","The word \"anesthesia\", coined by Oliver Wendell Holmes (1809–1894) in 1846 from the Greek ἀν-, an-, \"without\"; and αἴσθησις, aisthēsis, \"sensation\"), refers to the inhibition of sensation.","oliver_wendell_holmes_30.png",1,3);
//
////30 S. Chandrasekhar
//            questionRepositories.questionCreator("Who came close to predicting the presence of black holes?","S. Chandrasekhar","A.S Eddington","Albert Einstein","Harlow Shapley","S. Chandrasekhar","Indian American astrophysicist, who spend his professional list in the US","s_chandrasekhar_31.png",1,3);
//
////31 Wilhelm von Biela
//            questionRepositories.questionCreator("The coment known after him split into two who is he?","Wilhelm von Biela","Francis Baily","David Brewster","Heinrich Olbers","Wilhelm von Biela","A German Austrian military officer and amateur astronomer.","wilhelm_von_biela_32.png",1,3);
//
////32 Samuel Morse
//            questionRepositories.questionCreator("The telegraphic code is known after him, who is he?","Samuel Morse","Joseph henry","Andre ampere","Jean fourier","Samuel Morse","American painter and inventor.","samuel_morse_33.png",1,3);
//
////33 Hans Bethe
//
////34 Claude E. Shannon
//            questionRepositories.questionCreator("Who coined the term ‘Bit’ for a unit of information?","Claude E. Shannon","Ren Descartes","Albert Einstein","uigi Galvani","Claude E. Shannon","American mathematician electrical engineer and cryptographer, known as the father of information theory.","claude_e_shannon_35.png",1,3);
//
////35 Richard Dawking
//            questionRepositories.questionCreator("Who coined the term ‘meme’ any bit of information, whether it is a fact, fad or rumor?","Richard Dawking","Charles Darwin","William Hamilton","Ren Descartes","Richard Dawking","English ethnologist, evolutionary biologist and Author.","richard_dawking_36.png",1,3);
//
////36 Robert Millikan
//            questionRepositories.questionCreator("Who devised an equipment to measure the charge on an electron?","Robert Millikan","S. Ramanujan","Richard Dawking","Susruta","Robert Millikan","American experimental physicist, honored with the Nobel Prize for physics.","robert_millikan_37.png",1,3);
//
////37 Georg Cantos
//            questionRepositories.questionCreator("Who gave some revolutionary concepts about infinity?","Georg Cantos","Kurt Goedel","G.H Hardy","S. Ramanujan","Georg Cantos","A German mathematician, created set theory which has become a fundamental theory in mathematics.","georg_cantos_38.png",1,2);
//
////38 Louis Pasteur
//            questionRepositories.questionCreator("Who game the Germ Theory of diseases?","Louis Pasteur","Claude Bernard","Hugo de Vries","Luigi Galvani","Louis Pasteur","A French biologist, microbiologist and chemist","louis_pasteur_39.png",1,3);
//
////39 Sigmund Freud
//            questionRepositories.questionCreator("Father of Psychoanalysis.","Sigmund Freud","Anna Freud","Carl Rogers","Karen Horney","Sigmund Freud","Austrian neurologist","sigmund_freud_1.png",1,2);
//
////40 Jean Piaget
//            questionRepositories.questionCreator("Theorist of Cognitive development.","Jean Piaget","Jerome Bruner","John Bowlby","Charles Lyell","Jean Piaget","Swiss psychologist and he was placed great importance on the education of children.","jean_piaget_2.png",1,2);
//
////41 Erik Erikson
//            questionRepositories.questionCreator("Theorist of Psycho Social learning.","Erik Erikson","Lev Vygotsky","Enrico Fermi","Jack Kelby","Erik Erikson","A German-American developmental psychologist and psychoanalysis.","erik_erikson_3.png",1,2);
//
////42 Bill Gates
//            questionRepositories.questionCreator("Founder of Microsoft Corporation.","Bill Gates","Steve Jobs","Charles Babbage","Jerome Bruner","Bill Gates","William Henry Gates III an American Business magnate, investor, author, philanthropist and humanitarian.","bill_gates_4.png",1,1);
//
////43 Steve Paul Jobs
//            questionRepositories.questionCreator("Chairman, chief execute officer, and a co-founder of Apple Inc.","Steve Paul Jobs","Thomas Savery","Charles Babbage","Henry Ford","Steve Paul Jobs","An American entrepreneur, business magnate, chairman and majority shareholder of Pixar.","steve_paul_jobs_5.png",1,1);
//
////44 Leonardo da Vinci
//            questionRepositories.questionCreator("He was the painter of Mona Lisa.","Leonardo da Vinci","Michael Angelo","Alexander Bell","Karl Marx","Leonardo da Vinci","Was an Italian polymath of the Renaissance, whose areas of interest included invention, painting sculpting and architecture.","leonardo_da_vinci_6.png",1,1);
//
////45 Emilio Aguinaldo
//            questionRepositories.questionCreator("First president of the Philippines.","Emilio Aguinaldo","Manuel L. Quezon","Apolinario Mabini","Manuel L. Roxas","Emilio Aguinaldo","He held that office until 1901 When he was captured by United Stated forces during the Philippine – American war (1899-1902)","emilio_aguinaldo_7.png",1,1);
//
////46 Marie Curie
//            questionRepositories.questionCreator("Conducted pioneering research on radioactivity.","Marie Curie","Dmitri Mendeleev","Fritz Haber","Ernest R.","Marie Curie","Was a polish and naturalized-French Physicist and Chemist.","marie_curie_8.png",1,2);
//
////47 Ernest Rutherford
//            questionRepositories.questionCreator("Father of Nuclear Physics.","Ernest Rutherford","Michael Faraday","OttoHahn","Frederic Sanger","Ernest Rutherford","a New Zealand born British physicist and 1st baron Rutherford of Nelson, OM , FRS , HFRSELLD.","ernest_rutherford_9.png",1,2);
//
////48 Otto Hahn
//            questionRepositories.questionCreator("Father of Nuclear Chemistry.","Otto Hahn","Carl Rogers.","Frederic Sanger","Glenn Seaborg","Otto Hahn","A german chemist and pioneer in the fields of radioactivity and radiochemistry.","otto_hahn_10.png",1,3);
//
////49 Albert Einstein
//            questionRepositories.questionCreator("Who published the theory of special relativity on 1905.","Albert Einstein","Steven Weinberg.","Niels Bohr","Max Planck","Albert Einstein","a German-born theoretical physicist.","albert_einstein_11.png",1,1);
//
////50 Galileo Galilei
//            questionRepositories.questionCreator("Father of Modern Science, astronomer and physicist.","Galileo Galilei","Hands Bethe","James Chadwik","Lev Landau","Galileo Galilei","who invented telescope","galileo_galilei_12.png",1,1);
//
////51 William Shakespeare
//            questionRepositories.questionCreator("English poet and playwright.","William Shakespeare","Winston Churchill","Mikhail Gorbachev","Thomas Jefferson","William Shakespeare","greatest writer in English Language","william_shakespeare_13.png",1,1);
//
////52 Abraham Lincoln
//            questionRepositories.questionCreator("American President during civil War.","Abraham Lincoln","Isaac Newton","Charaka","Steven S.","Abraham Lincoln","help end slavery","abraham_lincoln_14.png",1,1);
//
////53 George Washington
//            questionRepositories.questionCreator("First President of USA (1732 0 1799).","George Washington","George W. Bush","Bill Clinton","Gerald Ford","George Washington","Was the Commander in chief of the continental forces during the American wars of independence","george_washington_15.png",1,1);
//
////54 Socrates
//            questionRepositories.questionCreator("Classical Greek Philosopher.","Socrates","John Locke","David hume","Ludwig","Socrates","one of the founders of western Philosophy.","socrates_16.png",1,1);
//
////55 Aristotle
//            questionRepositories.questionCreator("Ancient Greek Philosopher and Scientist.","Aristotle","Immanuel Kant","Albert Camus","Thomas Aquinas","Aristotle","Aristotle was an ancient Greek philosopher and scientist born in the city of Stagira, Chalkidiki, in the north of Classical Greece","aristotle_17.png",1,1);
//
////56 Charles Darwin
//            questionRepositories.questionCreator("Developed theory of evolution.","Charles Darwin","Karl Marx","Martin Luther","Nelson Mandela","Charles Darwin","An English Natural Scientist.","charles_darwin_18.png",1,1);
//
////57 Confucius
//
////58 Mohammed Ali
//            questionRepositories.questionCreator("American Boxer human rights activist , and philanthropist.","Mohammed Ali","Mike Tyson","Joe Louis","Jack Johnson","Mohammed Ali","He is widely regarded as one of the most significant celebrated sports figures of the 20th century.","mohammed_ali_20.png",1,1);
//
////59 Indira Gandhi
//            questionRepositories.questionCreator("She was India’s First female Prime Minister.","Indira Gandhi","Uma Bharti","Sucheta Kriplani","Sushma Swaraj","Indira Gandhi","She was the Prime Minister of the Republic of Indian From Nov. 1917 to October 1984 a total of 15 years until her assassination.","indira_gandhi_21.png",1,3);
//
////60 Ludwig van beethaven
//            questionRepositories.questionCreator("According to him Music is a higher revelation than all Wisdom and Philosophy.","Ludwigvan beethoven","Henry Ford","Lao Tzu","Nikola Tesla","Ludwigvan beethoven","A german Composer and pianist.","ludwig_van_beethaven_22.png",1,1);
//
////61 Eva Peron
//            questionRepositories.questionCreator("First lady of Argentina (1946 – 1952).","Eva Peron","Juliana awada","Isabel De peron","Cristina Ferandez","Eva Peron","She became a powerful political figure w/a large of support.","eva_peron_23.png",1,3);
//
////62 Rodrigo Duterte
//            questionRepositories.questionCreator("The 16th president of the Philippines.","Rodrigo Duterte","Ninoy Aquino","Gloria Macapagal Arroyo","Joseph Estrada","Rodrigo Duterte","The is known as Digong , and the 1st president from the southernmost island of country, Mindanao","rodrigo_duterte_24.png",1,1);
//
////63 Andre’s Bonifacio
//            questionRepositories.questionCreator("Father of the Philippine Revolution.","Andre’s Bonifacio","Emilio Jacinto","Juan Luna","Pedro Patemo","Andre’s Bonifacio","A Filipino Revolutionary leader and the president of the Tagalog Republic.","andre’s_bonifacio_25.png",1,1);
//
////64 M.S Swaminathan
//            questionRepositories.questionCreator("Father of Indian green revolution is:","M.S Swaminathan","Normal Borlang","K.C Mehta","B.P Pal","M.S Swaminathan","Swaminathan is known as the \"Father of Indian Green Revolution\" for his leadership and success in introducing and further developing high-yielding varieties of wheat in India. He is the founder and chairman of the About Chairman.","m_s_swaminathan_26.png",1,3);
//
////27 Louis Pastuer
//
//            questionRepositories.questionCreator("Who gave the Germ Theory of diseases?","Louis Pastuer","Claude Bernard","Hugo de Vries","Luigi Galvani","Louis Pastuer","A French biologist Microbiologist and Chemist","louis_pastuer_27.png",1,3);
//
////28 P.A.M Dirac
//
//            questionRepositories.questionCreator("Who proposed the theory of anti-particles?","P.A.M Dirac","Max Planck","Plato","Albert Einstein","P.A.M Dirac","Paul Adrien Maurice Diract an English theoritical physicist and one of the most significant physicist of the 20th century","p_a_m_dirac_28.png",1,3);
//
////29 Otto Von Guericke
//
//            questionRepositories.questionCreator("Who condution the famous experiment to prove that nature abhors vacuum?","Otto Von Guericke","Joseph Black","John Dalton","Evangelista Torricelli","Otto Von Guericke","A German scientist, inventor and politician.","otto_von_guericke_29.png",1,3);
//
////30 John Glenn
//
//            questionRepositories.questionCreator("Which astronaut has become a politician?","John Glenn","rank Borman","Thomas Stafford","German Titov","John Glenn","US Marine Corops aviator, engineer, astronaut and United State senator from Ohio, the first American to orit the earth.","john_glenn_30.png",1,3);
//
////31 Judit Resnik
//
//            questionRepositories.questionCreator("Who was the first woman astronaut to die during a space flight?","Judit Resnik","Mary Cleave","Rhea Seddon","Anna Fisher","Judit Resnik","An American engineer, she died when the space shuttle challenger was destroyed during the launch of mission.","judit_resnik_31.png",1,3);
//
////32 Eugene Cernan
//
//            questionRepositories.questionCreator("The last astronaut who left the Moon?","Eugene Cernan","Neil Armstrong","Harrison Schmitt","Edwin Aldrin","Eugene Cernan","An American NASA astronaut, Navan aviator and fighter pilot.","eugene_cernan_32.png",1,3);
//
////33 David Livingstone
//
//            questionRepositories.questionCreator("Who explored the central and southern Africa and discovered Victoria Falls, among other things.","David Livingstone","Alfred Wallace","Richard Leakey","Henry Norton Stanley","David Livingstone","A scottist christian congregationalist and pioneer medical missionary.","david_livingstone_33.png",1,3);
//
////34 K.D Naegamvala
//
//            questionRepositories.questionCreator("Who led the first Indian solar eclipse expedition of Juer in 1898?","K.D Naegamvala","M.K Vainu Bappu","A.L Narayan","C. Nagaraja Iyer","K.D Naegamvala","Kavasji Dadabha Naegamvala was an astrophysicist and the director of Takhtasingji observatory.","k_d_naegamvala_34.png",1,3);
//
////35 Robert E. Peary
//
//            questionRepositories.questionCreator("Who led the expedition to Greenland and later proved it to be an island?","Robert E. Peary","John Milne","Dicaearchus","Daubree","Robert E. Peary","Rear Admiral Robert Edwin Peary Sr. was an American explorer, United States Navy officer and Master Mason, who made several expeditions to the Arctic in the late 19th and early 20th centuries. He is best known for claiming to have reached the geographic North Pole with his expedition on April 6, 1909.","robert_e_peary_35.png",1,3);
//
////36 Ferdinand Magellan
//
//            questionRepositories.questionCreator("Who led the first marine expedition to navigate around the earth?","Ferdinand Magellan","Marco Polo","Ptolemy","Nicholas of Celsa","Ferdinand Magellan","A portuguese explorer who organized the spanish expedition to the East Indies from 1519-1522","ferdinand_magellan_36.png",1,1);
//
////37 Ronald Amundsen
//
//            questionRepositories.questionCreator("Who is the first man to reach the South Pole?","Ronald Amundsen","Francis Drake","Ferdinand Magellan","Robert Scott","Ronald Amundsen","A norwegian explorer of polar regions and leader of the Antartic expedition of 1910-1912","ronald_amundsen_37.png",1,3);
//
////38 Adrien de Gerlache
//
//            questionRepositories.questionCreator("Who led the first expedition to stay in Antartica during the winter?","Adrien de Gerlache","Leonard Kristensen","James Clark Ross","E.Borchgrevink","Adrien de Gerlache","An officer in the Belgian Royal Navy","adrien_de_gerlache_38.png",1,3);
//
////39 Richard E. Byrd
//
//            questionRepositories.questionCreator("Who is the first man to stay alone in Antarctica for five months?","Richard E. Byrd","Ernest Shackleton","Cherry Garrard","None of the above","Richard E. Byrd","American Naval officer and explorer, He receive Medal of Honor highest honor for valor given by united states.","richard_e_byrd_39.png",1,3);
//
////40 Francis Bacon
//
//            questionRepositories.questionCreator("Who said knowledge is power?","Francis Bacon","Aristotle","Euclid","Albert Einstein","Francis Bacon","An English philosopher statesman,scientist,jurist, orator and Author.","francis_bacon_40.png",1,2);
//
////41 Thomas A. Edison
//
//            questionRepositories.questionCreator("Who said genius is one 1% inspiration and 99% perspiration?","Thomas A. Edison","Albert Einstein","Isaac Newton","Thomas Jefferson","Thomas A. Edison","American inventor and bussinessman.","thomas_a_edison_41.png",1,2);
//
////42 J.Bhabh
//
//            questionRepositories.questionCreator("Who laid the foundation of nuclear science the country?","J.Bhabha","Kram Sarabha","Raja Ramanna","M.G.K Menon","J.Bhabha","An Indian nuclear physicist, founding director and professor of physics at Tata Institute","j_bhabh_42.png",1,3);
//
////43 Shrinivas Kulkarna
//
//            questionRepositories.questionCreator("Who discovered millisecond pulsars?","Shrinivas Kulkarna","Shiv Kumar","Govind Swarup","Charaka","Shrinivas Kulkarna","An Indian Astronomer.","shrinivas_kulkarna_43.png",1,3);
//
////44 Michael Crichton
//
//            questionRepositories.questionCreator("Who wrote the novel Jurassic Park?","Michael Crichton","Stanley Kubrick","George Lucas","Steven Spielberg","Michael Crichton","An American, Author , Screen writer, film director, film producer, and televesion producer.","michael_crichton_44.png",1,3);
//
////45 Isaac Animov
//
//            questionRepositories.questionCreator("Who gave the laws of robotics?","Isaac Animov","Voltaire","Karel Capek","Douglas Adams","Isaac Animov","An American writter and professor of biochemistry at Boston University.","isaac_animov_45.png",1,3);
//
////46 Euclid
//
//            questionRepositories.questionCreator("Father of Geometry.","Euclid","Max Born","Isaac Newton","Marco Polo","Euclid","Sometimes given the name Euclid of Alexandria to distinguish him from Euclides of Megara, and a Greek mathematician.","euclid_46.png",1,2);
//
////47 Carl Linnaeus
//
//            questionRepositories.questionCreator("Father of modern taxonomy.","Carl Linnaeus","Nikolai Varilov","Gregor Mendel","Agnes Arber","Carl Linnaeus","A Swedist botanist, physician, and zoologist and formalised binomial nomenclature, the modern system of naming organism.","carl_linnaeus_47.png",1,2);
//
////48 Alexander Braun
//
//            questionRepositories.questionCreator("Father of economics.","Alexander Braun","Erik Acharius","Michael Adanson","Andrea Cesalpino","Alexander Braun","Alexander Carl Heinrich Braun was a German botanist from reqensburg Bavaria.","alexander_braun_48.png",1,2);
//
////49 Jose Rizal
//
//            questionRepositories.questionCreator("He is widely considered one of the greatest heroes of the Philippines.","Jose Rizal","Ferdinand Magellan","Lapu-Lapu","Erik Acharius","Jose Rizal","Also known as jose Protasio Rizal Mercado Y  Alonso Realonda, A Filipino nationalist and polymath during the tailend of the spanish colonial period.","jose_rizal_49.png",1,1);
//
////50 Ferdinand Marcos
//
//            questionRepositories.questionCreator("Who was the 10th President of the Philippines?","Ferdinand Marcos","Diosdado Macapagal Arroyo","Benigno Aquino III","Fidel V. Ramos","Ferdinand Marcos","A Filipino politician and kleptocrat. He ruled as a dictator.","ferdinand_marcos_50.png",1,1);
//
////51 Francisco Quisumbing
//
//            questionRepositories.questionCreator("Who was the inventor of Quink Ink used by the Parker Pen company?","Francisco Quisumbing","Ramon Barba","Dado Banatao","Jose Rizal","Francisco Quisumbing","A Filipino botanist and gained his Ph.D in Plant Taxonomy, Systematics and Morphology from the University of Chicago in 1923.","francisco_quisumbing_51.png",1,2);
//
////52 Gregorio Y. Zara
//
//            questionRepositories.questionCreator("Who was the inventor of the first two-way videophone?","Gregorio Y. Zara","Daniel Dingel","Roberto del Rosario","Ramon Barba","Gregorio Y. Zara","A Filipino engineer and physicist. A native of Liopa, Batangas, Philippine Islands.","gregorio_y_zara_52.png",1,2);
//
////53 Aisa Mijeno
//
//            questionRepositories.questionCreator("Inventor of SALT lamp.","Aisa Mijeno","Pedro Flores","Thomas Edison","Magdalena Villarus","Aisa Mijeno","A Filipino scientist and entrepreneur.","aisa_mijeno_53.png",1,1);
//
////54 Ernesto Baron
//
//            questionRepositories.questionCreator("Who was the Filipino broadcaster and inventor?","Ernesto Baron","Hery Omaga-Diaz","Noli de Castro","Dong Puno","Ernesto Baron","Best known as the weatherman in ABS-CBN new program TV Patrol and also known as the walking Encyclopedia.","ernesto_baron_54.png",1,1);
//
////55 Angel C. Alcala
//
//            questionRepositories.questionCreator("Who create the artificial coral reefs that help a lot to the Philippines aquatic ecosystem.","Angel C. Alcala","Pedro Flores","Dong Puno","Ernesto Baron",",Angel C. Alcala","A Filipino biologist who was named a National Scientist of the Philippines.","angel_c_alcala_55.png",1,3);
//
////56 Margaret Burbridge
//
//            questionRepositories.questionCreator("Woman astronomer discovered six comets.","Margaret Burbridge","Hypatia","Caroline Herschel","Christine Wilson","Margaret Burbridge","A British-born American astrophysicist noted for original research and holding mand administrative posts.","margaret_burbridge_56.png",1,3);
//
////57 Tim Berners-Lee
//
//            questionRepositories.questionCreator("Father of Web.","Tim Berners-Lee","Bill Gates","Henry Ford","Alfred Russel","Tim Berners-Lee","British Engineer, computer scientist and Professor at MIT in 1991.","tim_berners_lee_57.png",1,2);
//
////58 Alfred Adler
//
//            questionRepositories.questionCreator("Father of Psychology.","Alfred Adler","Jean Piaget","Lev Vygotsky","Robert Koch","Alfred Adler","Austrian media doctor, and psychotherapist","alfred_adler_58.png",1,2);
//
////59 Lawrence Kohlberg
//
//            questionRepositories.questionCreator("Theorist of moral Development.","Lawrence Kohlberg","B.F Skinner","Rudolf Diesel","Carl Benz","Lawrence Kohlberg","American psycologist and born on October 25,1927","lawrence_kohlberg_59.png",1,2);
//
////60 Albert Bandura
//
//            questionRepositories.questionCreator("Theorist of Social Learning.","Albert Bandura","Ramon Barbara","Galen","Francis Bacon","Albert Bandura","A psycologist who is the David Star Jordan Professor Emeritus of Social Science in Psychology at stanford University.","albert_bandura_60.png",1,2);
////0 Alan Turing
//            questionRepositories.questionCreator("Father of Artificial Intelligence.","Alan Turing","John von Neuman","Warren Mc cullach","John mc Carthy","Alan Turing","Alan turing was a logician mathematician and comp. scientist was born in London in 1912,","alan_turing_1.png",1,1);
//
////1 James Hutton
//            questionRepositories.questionCreator("Who was known as the Father of geology?","James Hutton","Alfred Wegener","Charles Lyell","Hillon","James Hutton","A Scottish geologist , physician chemical manu8facturer , naturalist and experimental , agriculture","james_hutton_2.png",1,2);
//
////2 Norbert Wiener
//            questionRepositories.questionCreator("Considered to be the father of cybernatics.","Norbert Wiener","Claude Shannon","Konrad Zuse","Alan turing","Norbert Wiener","American mathematician was born on Novemb er 26, 1894 at Cambridge.","norbert_wiener_3.png",1,2);
//
////3 Susruta
//            questionRepositories.questionCreator("Father of plastic surgery.","Susruta","Hippocrates","Galen","Charaka","Susruta","Ancient Indian physician during BCE to 1000 BCE.","susruta_4.png",1,3);
//
////4 Freederic Joliot – Curie
//            questionRepositories.questionCreator("Who discovered artificial radioactivity?","Freederic Joliot – Curie","Henri Bacquerel","Marie Curie","Pierre Curie","Freederic Joliot – Curie","A French physicist","freederic_joliot_–_curie_5.png",1,3);
//
////5 G.W Leibniz
//            questionRepositories.questionCreator("Who is independently discovered calculus?","G.W Leibniz","Francis Bacon","Christian Guygens","Leonardo Fibonacci","G.W Leibniz","A german rationalist philosopher and one of the great dendissance men of Western thought.","g_w_leibniz_6.png",1,2);
//
////6 Harvey Cushing
//            questionRepositories.questionCreator("Father of brain surgery.","Harvey Cushing","Pierre Broca","Galen","Christian Barnard","Harvey Cushing","American neuron surgeon, pathologist, writer and draftsman.","harvey_cushing_7.png",1,3);
//
////7 Linus Pauling
//            questionRepositories.questionCreator("Who claimed that Vitamin c can prevent common cold?","Linus Pauling","Paul Muller","Rebe Dubos","G. natta","Linus Pauling","American chemist,biochemist peace activist,author and educator.","linus_pauling_8.png",1,3);
//
////8 William whewell
//            questionRepositories.questionCreator("Who invented the term “SCIENCE”?","William whewell","Francis Bacon","Roger Bacon","Plato","William whewell","An English polymath scientist, Anglican priest, philosopher and theologian.","william_whewell_9.png",1,1);
//
////9 Erasistratus
//            questionRepositories.questionCreator("Father of physiology.","Erasistratus","Thales","Herophilus","Anaximander","Erasistratus","A Greek anatomist and royal physician. He founded a school of anatomy in Alexandria.","erasistratus_10.png",1,3);
//
////10 Karl Jansky
//            questionRepositories.questionCreator("Who discovered radio waves coming from the sky?","Karl Jansky","Max Weber","J.S Hey","Robert Watson-Watt","Karl Jansky","An American physicist and radio engineer.","karl_jansky_11.png",1,3);
//
////11 Phan tuan
//            questionRepositories.questionCreator("Who was the first Asian astronaut to go into space?","Phan tuan","John Glenn","Ravish Malhotra","Rakesh Sharma","Phan tuan","a retired Vietnam airforce aviator.","phan_tuan_12.png",1,3);
//
////12 Alexer Leonov
//            questionRepositories.questionCreator("Who was the first man to walk in space?","Alexer Leonov","John Glenn","John Young","Alan Shepard","Alexer Leonov","A retired Soviet/Russian cosmonaut , Air Force Major general, writer and artist.","alexer_leonov_13.png",1,3);
//
////13 Valentina Tereshkova
//            questionRepositories.questionCreator("Who was the first Woman in space?","Valentina Tereshkova","Sally Ride","Anna Fisher","Rhea Seddon","Valentina Tereshkova","Retired Russian  cosmonaut, engineer and politician.","valentina_tereshkova_14.png",1,3);
//
////14 Fabian Gottlieb Bellingshausen
//            questionRepositories.questionCreator("Who was the first man to reach antartica?","Fabian Gottlieb Bellingshausen","Thomas Cook","Paul Scott","John young","Fabian Gottlieb Bellingshausen","One of Russia’s most celebrated explorers and the discovered of the continent of Antarctica.","fabian_gottlieb_bellingshausen_15.png",1,3);
//
////15 Henry Ford
//            questionRepositories.questionCreator("Who was the father of the automobile?","Henry Ford","Rudolf Diesel","Gottlieb Daimler","Carl Benz","Henry Ford","He was so much more interested in machines and building things.","henry_ford_16.png",1,2);
//
////16 Dmitri Mendeleev
//            questionRepositories.questionCreator("Who was the father of Periodic Table of elements?","Dmitri Mendeleev","Alfred Nobel","Johann Baeyer","John Glenn","Dmitri Mendeleev","He is a Russian chemist and inventor.","dmitri_mendeleev_17.png",1,1);
//
////17 Paul Ehrlich
//            questionRepositories.questionCreator("Who was the father of science chemotherapy, using chemical to treat diseases?","Paul Ehrlich","Leo Szilard","Hippocrates","Avicenna","Paul Ehrlich","He is a German , and won the Nobel prize in Physiology or Medicine for his exemplary work on immunology","paul_ehrlich_18.png",1,3);
//
////18 Edward Teller
//            questionRepositories.questionCreator("Who was the father of  hydrogen bomb?","Edward Teller","Leo Szilard","J. Robert Oppenheimer","Otto Hahn","Edward Teller","On it’s 100th birthday in 1959, Edward Teller warned the oil industry about global warming.","edward_teller_19.png",1,3);
//
////19 D.N Wadia
//            questionRepositories.questionCreator("Who was the Father of geology in India?","D.N Wadia","Mihir Sen","K.S Valdiya","M.K Bose","D.N Wadia","One among the great geologist in geological.","d_n_wadia_20.png",1,3);
//
////20 Andreas Vesalius
//            questionRepositories.questionCreator("Who was the founding Father of modern anatomy?","Andreas Vesalius","Hippocrates","Avicenna","Galen","Andreas Vesalius","Humani Corporis Fabrica (On the fabric of the human body) revolutionized the science of medicine and laid foundation for the modern human anatomy.","andreas_vesalius_21.png",1,2);
//
////21 Francis Galton
//            questionRepositories.questionCreator("Who considered to be the father of eugenics?","Francis Galton","Karl Pearson","Charles Darwin","Avicenna","Francis Galton","English explorer, anthropologist, eugenicists, geographer and meteorologist.","francis_galton_22.png",1,3);
//
////22 Walther Nernst
//            questionRepositories.questionCreator("Who discovered the Third Law of thermodynamics?","Walther Nernst","L.E Boltzmann","Count Rumford","P.M.S Blackett","Walther Nernst","The 3rd law was developed by the chemist Walther Nernst during the years 1906–12, and is therefore often referred to as Nernst\'s theorem or Nernst\'s postulate. The third law of thermodynamics states that the entropy of a system at absolute zero is a well-defined constant.","walther_nernst_23.png",1,3);
//
////23 Gerhard Domagk
//            questionRepositories.questionCreator("Who discovered sulpha drugs?","Gerhard Domagk","Robert Wilkins","Howard Florey","Alexander Fleming","Gerhard Domagk","a German pathologist and bacteriologist","gerhard_domagk_24.png",1,3);
//
////24 Anton Leeuwenhoek
//            questionRepositories.questionCreator("Who discovered one-celled animals?","Anton Leeuwenhoek","Galen","Marcello Malpighi","Charaka","Anton Leeuwenhoek","who invented or greatly improved the microscope and discovered living organisms,.","anton_leeuwenhoek_25.png",1,3);
//
////25 Autor Singh Paintal
//            questionRepositories.questionCreator("Who discovered J-receptor-the nerve terminals in lungs that cause breathlessness?","Autor Singh Paintal","Peter Medawar","B.S Anand","A.L. Hodgkin","Autor Singh Paintal","Indian Medical Scientist","autor_singh_paintal_26.png",1,3);
//
////26 Ronald Ross
//            questionRepositories.questionCreator("Who discovered that malaria is caused by a particular type of mosquito?","Ronald Ross","Christian Eijman","Charaka","Louis Pasteur","Ronald Ross","A British medical doctor who Eijkman receive Nobel Prize for physiology or Medicine in 1902 for his work in transmission of malaria.","ronald_ross_27.png",1,2);
//
////27 Konrad Lorenz
//            questionRepositories.questionCreator("Who discovered \"in printing\"?","Konrad Lorenz","Charles Darwin","B.F Skinner","Alfred Russel","Konrad Lorenz","Austrian zoologist, ethnologist and ornithologist.","konrad_lorenz_28.png",1,2);
//
////28 I.P Pavlov
//            questionRepositories.questionCreator("Who discovered condition reflex through his experiments on dogs?","I.P Pavlov","Sigmund Freud","B.F Skinner","Carl Jung","I.P Pavlov","A Russian physiologist known primarily for his work in classical conditioning","i_p_pavlov_29.png",1,3);
//
////29 Oliver Wendell holmes
//            questionRepositories.questionCreator("Who coined the term ‘anesthesia’  for any pain-killer given during an operation?","Oliver Wendell holmes","Paul Ehrlich","Susruta","Carl Jung","Oliver Wendell holmes","The word \"anesthesia\", coined by Oliver Wendell Holmes (1809–1894) in 1846 from the Greek ἀν-, an-, \"without\"; and αἴσθησις, aisthēsis, \"sensation\"), refers to the inhibition of sensation.","oliver_wendell_holmes_30.png",1,3);
//
////30 S. Chandrasekhar
//            questionRepositories.questionCreator("Who came close to predicting the presence of black holes?","S. Chandrasekhar","A.S Eddington","Albert Einstein","Harlow Shapley","S. Chandrasekhar","Indian American astrophysicist, who spend his professional list in the US","s_chandrasekhar_31.png",1,3);
//
////31 Wilhelm von Biela
//            questionRepositories.questionCreator("The coment known after him split into two who is he?","Wilhelm von Biela","Francis Baily","David Brewster","Heinrich Olbers","Wilhelm von Biela","A German Austrian military officer and amateur astronomer.","wilhelm_von_biela_32.png",1,3);
//
////32 Samuel Morse
//            questionRepositories.questionCreator("The telegraphic code is known after him, who is he?","Samuel Morse","Joseph henry","Andre ampere","Jean fourier","Samuel Morse","American painter and inventor.","samuel_morse_33.png",1,3);
//
////33 Hans Bethe
//            questionRepositories.questionCreator("Who proposed the idea that stars generate energy by nuclear fusion?","Hans Bethe","Albert Einstein","M.N Sana","Bertrand Rusell","Hans Bethe","German – American nuclear physicist.","hans_bethe_34.png",1,3);
//
////34 Claude E. Shannon
//            questionRepositories.questionCreator("Who coined the term ‘Bit’ for a unit of information?","Claude E. Shannon","Ren Descartes","Albert Einstein","uigi Galvani","Claude E. Shannon","American mathematician electrical engineer and cryptographer, known as the father of information theory.","claude_e_shannon_35.png",1,3);
//
////35 Richard Dawking
//            questionRepositories.questionCreator("Who coined the term ‘meme’ any bit of information, whether it is a fact, fad or rumor?","Richard Dawking","Charles Darwin","William Hamilton","Ren Descartes","Richard Dawking","English ethnologist, evolutionary biologist and Author.","richard_dawking_36.png",1,3);
//
////36 Robert Millikan
//            questionRepositories.questionCreator("Who devised an equipment to measure the charge on an electron?","Robert Millikan","S. Ramanujan","Richard Dawking","Susruta","Robert Millikan","American experimental physicist, honored with the Nobel Prize for physics.","robert_millikan_37.png",1,3);
//
////37 Georg Cantos
//            questionRepositories.questionCreator("Who gave some revolutionary concepts about infinity?","Georg Cantos","Kurt Goedel","G.H Hardy","S. Ramanujan","Georg Cantos","A German mathematician, created set theory which has become a fundamental theory in mathematics.","georg_cantos_38.png",1,3);
//
////38 Louis Pasteur
//            questionRepositories.questionCreator("Who game the Germ Theory of diseases?","Louis Pasteur","Claude Bernard","Hugo de Vries","Luigi Galvani","Louis Pasteur","A French biologist, microbiologist and chemist","louis_pasteur_39.png",1,2);
//
////39 Sigmund Freud
//            questionRepositories.questionCreator("Father of Psychoanalysis.","Sigmund Freud","Anna Freud","Carl Rogers","Karen Horney","Sigmund Freud","Austrian neurologist","sigmund_freud_1.png",1,2);
//            //TODO END OF PEOPLE
//
//            //TODO OF SPORTS
//            //0 Six
//            questionRepositories.questionCreator("How many players are there in an ice hockey team?","Six","Ten","Twenty","Five","Six","Hockey is played with six (6) players on the ice for each team; five (5) skaters, one (1) goaltender. The typical roster size of a hockey team is twenty (20) players; twelve (12) forwards, 6 defensemen, and two (2) goaltenders","six_1.png",5,3);
//
////1 5'8
//            questionRepositories.questionCreator("In darts, how high off the floor must the bulls eye measure?","5'8","6'1","4'3","10'1","5'8","Standard height from the floor to the bull seye on the dartboard is 5 feet 8 inches, while the oche (distance between the front of the board and the toe line) should measure 7 feet 9.25inches.","5_8_2.png",5,3);
//
////2 Six
//            questionRepositories.questionCreator("How many players are there in volleyball team?","Six","Seven","Eight","Ten","Six","Volleyball is a team sport in which two teams of six players are separated by a net. Each team tries to score points by grounding a ball on the other team\'s court under organized rules. It has been a part of the official program of the Summer Olympic Games since 1964.","six_3.png",5,1);
//
////3 Five
//            questionRepositories.questionCreator("How many players are there in basketball team?","Five","Four","Nine","Six","Five","Basketball is played with two teams, with 5 players from each team on the court at one time. The maximum number of players on the bench differs by league. In international play, a maximum of 7 players are allowed on the bench, resulting in a roster of 12 players.","five_4.png",5,1);
//
////4 Eleven
//            questionRepositories.questionCreator("How many players are there in football team?","Eleven","Twelve","Eighteen","Nine","Eleven","“A match is played by two teams, each with a maximum of eleven players; one must be the goalkeeper. A match may not start or continue if either team has fewer than seven players”","eleven_5.png",5,2);
//
////5 Fifteen
//            questionRepositories.questionCreator("How many red balls are used in a game of snooker?","Fifteen","Twenty","Ten","Five","Fifteen","“It is played using a cue and snooker balls: one white cue ball, fifteen red balls worth one point each (sometimes played with fewer red balls, commonly6 or 10), and six balls of different colors: yellow (2 points), green (3), brown (4), blue (5), pink (6), black (7)","fifteen_6.png",5,3);
//
////6 One hundred eighty
//            questionRepositories.questionCreator("What is the highest possible score with three darts?","One hundred eighty","Two hundred","Twenty","Thirty","One hundred eighty","“The highest score possible with three darts is one hundred eighty, commonly known as a “ton eighty” (one hundred is called a ton), obtained when all three darts land in the triple 20. In televised game, the referee frequently announces a score of one hundred eighty exuberant style”","one_hundred_eighty_7.png",5,3);
//
////7 Thirteen
//            questionRepositories.questionCreator("In rugby league team, how many players are there?","Thirteen","Fifteen","Twenty","Ten","Thirteen","“Thirteen players. Rugby league football is a full-contact sport played by two teams of thirteen players on a rectangular field. One of the two codes of rugby, it originated in Northern England in 1895 as a split from the Rugby Football Union over the issue of payments to players”","thirteen_8.png",5,2);
//
////8 Golf
//            questionRepositories.questionCreator("Which sport is associated with Constantino Rocca?","Golf","Dart","Hockey","Cheese","Golf","“Constantino Rocca is a former player. He was long known as the most successful male golfer that Italy has produced, until the 2018 success of Francesco Molinari, who credited Rocca as an inspiration to him following his open victory”","golf_9.png",5,3);
//
////9 Thirty six
//            questionRepositories.questionCreator("What is highest number on a roulette wheel?","Thirty six","Ten","Forty","Twenty","Thirty six","“The pockets of the roulette wheel are numbered from zero to thirty six. In number ranges from one to ten and nineteen to twenty-eight, odd numbers are red and even are black. In ranges from eleven to eighteen and twenty nine to thirty six, odd numbers are black and even are red. There is a green pocket numbered zero”","thirty_six_10.png",5,3);
//
////10 Three feet
//            questionRepositories.questionCreator("What height is the center of tennis net in feet?","Three feet","One feet","Two feet","Four feet","Three feet","“If a double net is used, then the net shall be supported, at a height of three and half feet (1.07 m), by two singles sticks, the centers of which shall be three feet outside the singles court on each side. The net posts shall not be more than six inches square or six inches diameter.","three_feet_11.png",5,2);
//
////11 USA
//            questionRepositories.questionCreator("Which country invented volleyball?","USA","China","Africa","Japan","USA","“Volleyball has come a long way from the dusty-old YMCA gymnasium of Holyoke, Massachusetts, USA, where visionary William G. Morgan invented the sport back in 1895.”","usa_12.png",5,2);
//
////12 Frisbee
//            questionRepositories.questionCreator("What is Pluto platter now known as?","Frisbee","Shot put","Javelin handwrite","Discus handwrite","Frisbee","“In 1955, Walter F. Morrison’s company PIPCO manufactured and sold the PLUTO PLATTER flying disc. In 1957, Walter F. Morrison signs all rights to the PLUTO PLATTER over to Wham-O. Later in 1957, Wham-O changes the name to FRISBEE.”","frisbee_13.png",5,2);
//
////13 Gold
//            questionRepositories.questionCreator("Which color is an archery target center?","Gold","Red","Black","Silver","Gold","“The target faces are arranged with 5 colors Gold, Red, Blue, Black and white. But gold is best for target. Yellow, referred to as “gold”, is the world Archery standard.”","gold_14.png",5,2);
//
////14 Mexico
//            questionRepositories.questionCreator("Where was the FIFA world cup held in 1986?","Mexico","Canada","Africa","America","Mexico","“The 1986 FIFA world cup, the 13th FIFA World Cup, was held in Mexico from 31 May to 29 June 1986","mexico_15.png",5,3);
//
////15 Queen
//            questionRepositories.questionCreator("Which is the most powerful piece in chess?","Queen","King","Horse","Bishop","Queen","“The queen is the most powerful piece in the game of chess, able to move any number of squares vertically, horizontally or diagonally. Each player starts the game with one queen, placed in the middle of the first rank next to the king.”","queen_16.png",5,1);
//
////16 Six
//            questionRepositories.questionCreator("How many pockets does a snooker table have?","Six","Seven","Ten","Four","Six","“The playing surface, eleven feet 8.5 inches by five feet ten inches for a standard full-size table, with six pocket holes, one at each corner and one at the center of each of the longer side cushion. For further information see Billiard table, specially the section Snooker and English billiards tables.”","six_17.png",5,2);
//
////17 Red, blue, black, yellow and green
//            questionRepositories.questionCreator("What color are in the five Olympics ring are?","Red, blue, black, yellow and green","Brown, blue, orange, peach and yellow","Pink, white, green, violet and red","Maroon, navy blue, yellow green, purple and red","Red, blue, black, yellow and green","“The Olympic flag has a white background, with five interlaced rings in the center: Blue, Yellow, Black, Green and Red. This design is symbolic; it represent the five continents of the world, united by Olympics, while the six colors are those that appear on all the national flags of the world at the present time.”","red_blue_black_yellow_and_green_18.png",5,1);
//
////18 Yellow
//            questionRepositories.questionCreator("Which color belt comes after white in karate?","Yellow","Blue","Red","Black","Yellow","“Originally, the white belt was simply dyed to a new color. This repeated dying process dictated the type of belt color and the order of the colors. The standard belt color system is white, yellow, gold, orange, green, blue, purple, brown, red, and black.”","yellow_19.png",5,1);
//
////19 Fifty
//            questionRepositories.questionCreator("In darts, how many points do you get if you hit a bulls eye?","Fifty","One hundred","Ten","Sixty","Fifty","“An inner bullseye (sometimes referred to as a “double bullseye” in amateur play) is a smaller, inner circle and counts for fifty points while an outer bull is worth twenty five points. Two treble 20s when combined with an inner bullseye is worth 170 points in darts which is the highest possible checkout.”","fifty_20.png",5,3);
//
////20 Eighteen inches
//            questionRepositories.questionCreator("In inches, how big is the diameter of a basketball hoop?","Eighteen inches","Twenty inches","Nine inches","Eleven inches","Eighteen inches","“Regulation backgrounds are seventy two inches wide by forty two inches tall. All basketball rims (hoops) are eighteen inches in diameter. The inner rectangle on the backboard is twenty four inches wide by eighteen inches tall.”","eighteen_inches_21.png",5,3);
//
////21 Long jump
//            questionRepositories.questionCreator("What’s the second event on day one of a men’s decathlon?","Long jump","Tennis","Basketball","Volleyball","Long jump","“Competitors earn points for their performance in each discipline and the overall winner is the man who accrues the most points. The first day consists of (in order): One hundred meter, long jump, shot put, high jump, and four hundred. The second day’s events are one hundred ten meter hurdles, discus, pole vault, javelin and one thousand five hundred.”","long_jump_22.png",5,3);
//
////22 Table tennis
//            questionRepositories.questionCreator("Britain’s Desmond Douglas was world number seven in which sport during the 1980s?","Table tennis","Long jump","Darts","Chess","Table tennis","“Douglas was eleven times English table tennis champion, who peaked at equal world No. seven and the European No. three.","table_tennis_23.png",5,3);
//
////23 1877
//            questionRepositories.questionCreator("In which year was the first Wimbledon tournament held?","1877","1899","1874","1888","1877","“The 1877 Wimbledon Championship was a men’s tennis tournament held at the All England croquet and lawn tennis club (AEC & LTC) in Wimbledon, London. It was the world’s first official lawn tennis tournament, and was later recognized as the first Grand Slam tournament or “Major”. “","1877_24.png",5,3);
//
////24 Ten feet
//            questionRepositories.questionCreator("In feet, how high is a basketball hoop?","Ten feet","Twelve feet","Eleven feet","Twenty feet","Ten feet","“The top of the hoop is ten feet above the ground. Regulation backboards are seventy two inches wide by forty two inches tall. All basketball rims (hoops) are eighteen inches in diameter.”","ten_feet_25.png",5,2);
//
////25 Polo
//            questionRepositories.questionCreator("Which sport is played on the biggest pitch in terms of area?","Polo","Hockey","Golf","Football","Polo","“The sport with largest pitch would be polo of the horse variety. If we exclude horses then cricket has large ovals and Australian football is played on large cricket grounds or even larger Australian Football fields. Some sports don’t have standardized fields.”","polo_26.png",5,3);
//
////26 5 minute
//            questionRepositories.questionCreator("What is the maximum time limit allowed to look for a lost ball in golf?","5 minutes","2 minutes","1 minute","10 minutes","5 minutes","“So keep your golfing woes to a minimum with our guide to losing your ball. A player has five minutes to look for a ball before it is deemed to be lost.”","5_minute_27.png",5,2);
//
////27 Willow
//            questionRepositories.questionCreator("What type of wood is a cricket bat traditionally made from?","Willow","Acacia","Narra","Mahogany","Willow","“The bat is traditionally made from willow wood, specifically from a variety of white willow called cricket bat willow (Salix Alba var. caerulea), treated with raw (unboiled) linseed oil, which has a protective function.”","willow_28.png",5,3);
//
////28 Cycling
//            questionRepositories.questionCreator("Which sport is Sarah Hammer associated with?","Cycling","Snow boarding","Hockey","Race","Cycling","“Sarah Kathryn Hammer (born August 18, 1983) is a former American professional racing cyclist and four-time Olympic silver medalist.”","cycling_29.png",5,3);
//
////29 January
//            questionRepositories.questionCreator("Which month is the Australian open tennis tournament held?","January","February","September","June","January","“The Australian open is a tennis tournament held annually over the last fortnight of January in Melbourne, Australia.","january_30.png",5,3);
//
////30 Strike
//            questionRepositories.questionCreator("In baseball, if a batter swings at a ball and misses a pitch, it is considered a what?","Strike","Misses ball","Ball","Mistake","Strike","“A strike in baseball results when a batter swings at and misses a pitch, does not swing at a pitch in the strike zone or hits a foul ball that is not caught.”","strike_31.png",5,1);
//
////31 Four
//            questionRepositories.questionCreator("How many bases are there on a baseball field including the home plate?","Four","Five","Six","Three","Four","“The bases are perhaps the most important part of the baseball field. There are four bases: home plate, first base, second base, and third base.","four_32.png",5,1);
//
////32 Nine
//            questionRepositories.questionCreator("How many players are there in a baseball team, on the field at one time?","Nine","Eleven","Ten","Twelve","Nine","“A baseball game is played between two teams, each composed of nine players, that take turns playing offense (batting and baserunning) and defense (pitching and fielding).”","nine_33.png",5,1);
//
////33 Javelin
//            questionRepositories.questionCreator("Which sport is ‘Mariya Abakumova’ associated with?","Javelin","Shot put","Discus handwrite","Hockey","Javelin","“Mariya Vasiliyevna Abakumova born fifteen January 1986) is a Russian track and field athlete who competes in the javelin handwrite. She won gold at the 2011 world.”","javelin_34.png",5,2);
//
////34 Seattle supersonics
//            questionRepositories.questionCreator("What was the former name of the Oklahoma City thunder basketball team?","Seattle supersonics","Thunder bolt","The beetles","Seagull of the Sea","Seattle supersonics","“The team was originally established as the Seattle SuperSonics, an expansion team that joined the NBA for the 1967-68 season.”","seattle_supersonics_35.png",5,2);
//
////35 Two
//            questionRepositories.questionCreator("How many players are there in beach volleyball game?","Two","Four","Six","Five","Two","“Beach Volleyball is a team sport played by two teams of two players on a sand court divided by a net.”","two_36.png",5,1);
//
////36 Four
//            questionRepositories.questionCreator("How many players are there in polo team?","Four","Ten","Five","Eight","Four","“Each team consists of four mounted players, which can be mixed teams of both men and women.”","four_37.png",5,3);
//
////37 General Choi Hong Hi
//            questionRepositories.questionCreator("Who is founder of Tae Kwon Do?","General Choi Hong Hi","Jigoro Kano","Dennis Hong Hi","Vincent Kano","General Choi Hong Hi","“General Choi Hong Hi (9 November 1918-15 June 2002) was a South Korean Army general and martial artist who is a controversial figure in the history of the Korean martial art of taekwondo. Choi is regarded by many as the “founder of Taekwon-Do federation (ITF) organizations.”","general_choi_hong_hi_38.png",5,2);
//
////38 Red belt
//            questionRepositories.questionCreator("What belt is the highest in karate?","Red belt","Black belt","Pink belt","Yellow belt","Red belt","“In some schools, a red belt signifies ninth or tenth degree Dan rank, where the tenth degree is the highest rank attainable. In karate, even though grandmaster and non-black belts (according to whatever system) may both wear a “red belt”, the difference is obvious.”","red_belt_39.png",5,1);
//
////39 1945
//            questionRepositories.questionCreator("When did Taekwondo begin?","1945","1899","1955","1995","1945","“In 1945 Korea was liberated. In the last few years before liberation, there were many different variations of subak/Taek Kyon in Korea. This was due to all of the other martial arts influence on it. The first Taekwondo School (Kwan) was started in Yong Chun, Seoul, Korea in 1945.”","1945_40.png",5,3);
//
////40 Japan
//            questionRepositories.questionCreator("What place sumo wrestling originated from?","Japan","Thailand","Korea","Vietnam","Japan","“Sumo means “striking one another”. The sport originated in japan, the only country where it is practiced professionally.”","japan_41.png",5,1);
//
////41 Jigoro Kano
//            questionRepositories.questionCreator("Who made judo?","Jigoro Kano","Remy Presas","Christopher Vistal","Crisogono Vistal","Jigoro Kano","“He took jujitsu and adapted it to the times. His new methodology was called Judo. In 1882, Dr. Jigoro Kano (The father of judo) made a comprehensive study of these ancient self-defense forms and integrated the best of these forms into a sport which is known as Kodokan Judo.”","jigoro_kano_42.png",5,3);
//
////42 Remy Presas
//            questionRepositories.questionCreator("Who created Arnis?","Remy Presas","Malulan Ramon","Patricio Malulan","Policarpio Agustin","Remy Presas","“Remy Amador Presas (December 19, 1936 – August 28, 2001) was the founder of Modern Arnis, a popular Filipino martial art. Born in the Philippines, he moved to the united states in 1974, where he taught his art via seminars and camps.”","remy_presas_43.png",5,2);
//
////43 Philippine
//            questionRepositories.questionCreator("Where did Arnis originate?","Philippine","Spain","France","China","Philippine","“The origin of Arnis can be traced back to native fighting techniques during conflicts among the various Prehispanic Filipino tribes or kingdoms, though the current from has Spanish influence from old fencing which originated in Spain in the 15th century.”","philippine_44.png",5,1);
//
////44 December 11. 2009
//            questionRepositories.questionCreator("When did Arnis become national sports?","December 11. 2009","January 26, 2000","November 5, 1941","June 2, 1936","December 11. 2009","“Arnis was declared as the Philippine National Martial Art and Sport on December 11, 2009 through Republic Act 9850 signed by Pres. Gloria Macapagal - Arroyo .”","december_11_2009_45.png",5,3);
//
////45 Indonesian Rattan
//            questionRepositories.questionCreator("What are arnis sticks made of?","Indonesian Rattan","Filipino Rattan","Philippine Narra","Acacia","Indonesian Rattan","“All Kali/Arnis, Eskrima sticks are made of hard Indonesian Rattan which is considerably harder than Filipino Rattan.”","indonesian_rattan_46.png",5,3);
//
////46 Friedrich Ludwig Jahn
//            questionRepositories.questionCreator("Who is the creator of gymnasium","Friedrich Ludwig Jahn","Patricio Malulan Jr.","Patricio Malulan Sr.","Policarpio Agustin","Friedrich Ludwig Jahn","“Friedrich Ludwig Jahn (August 11, 1778 - October 15, 1852) was a German gymnastics educator and nationalist his admirers know him as Turnvater Jahn, roughly meaning “father of gymnastics” Jahn.”","friedrich_ludwig_jahn_47.png",5,3);
//
////47 Indian
//            questionRepositories.questionCreator("Where did Chess originate?","Indian","France","Spain","Japan","Indian","“Many countries claim to have invented the chess game in some incipient form. The most commonly held belief is that chess originated in India, where it was called Chaturanga, which appears to have been invented in the 6th century AD.”","indian_48.png",5,3);
//
////48 France
//            questionRepositories.questionCreator("Where was fencing invented?","France","Paris","England","Spain","France","“ The foil was invented in France as a training weapon in the middle of the 18th century to practice fast and elegant thrust fencing. “","france_49.png",5,2);
//
////49 Eighteen
//            questionRepositories.questionCreator("How many holes are there in a full round of golf?","Eighteen","Nine","Five","Twenty","Eighteen","“A traditional golf course is eighteen holes, but nine-hole courses are common and can be played twice through for a full round of eighteen holes.","eighteen_50.png",5,3);
//
////50 Philippine
//            questionRepositories.questionCreator("Where can you find arnis being widely practice?","Philippine","India","China","France","Philippine","“Arnis is a very old Filipino martial art. It uses swords, daggers and sticks, as well as weaponless techniques using the hands and feet.”","philippine_51.png",5,1);
//
////51 Archery
//            questionRepositories.questionCreator("In what sport would one find an Albion round?","Archery","Darts","Javelin handwrite","Shot pot","Archery","“In women’s archery, an Albion round involves firing 36 arrows at targets at each of three distances.”","archery_52.png",5,3);
//
////52 Chukka
//            questionRepositories.questionCreator("In Polo, what is a period of play called?","Chukka","Inning","Game","On","Chukka","“In polo, periods of play are called chukkas. Most often there are six chukkas to a game, but sometimes there are four or eight.”","chukka_53.png",5,3);
//
////53 Australian doubles
//            questionRepositories.questionCreator("What variation might one play in tennis?","Australian doubles","American doubles","African doubles","China doubles","Australian doubles","“In Australian doubles tennis, a teammate can play on the same side of the court as the server. In standard tennis, this is forbidden.”","australian_doubles_54.png",5,3);
//
////54 Advantage
//            questionRepositories.questionCreator("In tennis, what follows a deuce?","Advantage","Forwarded","Tie","Advance","Advantage","“Advantage, in tennis, is the point scored immediately after deuce. If the player scoring it also scores the next point, he or she is the winner of the game.”","advantage_55.png",5,3);
//
////55 Twenty six
//            questionRepositories.questionCreator("How many points is required for a win in association croquet?","Twenty six","Fifty","Thirty five","Fourteen","Twenty six","“Association croquet is a game between sides of one or two players each. The first to earn twenty six points is the winner.”","twenty_six_56.png",5,3);
//
////56 Three
//            questionRepositories.questionCreator("How many years old are horses that run the Kentucky Derby?","Three","Ten","Four","Six","Three","“The Kentucky Derby is limited to three-year-old horses. Some of the most famous horses in racing history have run in the race.”","three_57.png",5,3);
//
////57 Rubber
//            questionRepositories.questionCreator("What is a golf ball made of?","Rubber","Aluminum","Wood","Cotton","Rubber","“A golf ball is made mostly of rubber and has a thin, plastic cover.”","rubber_58.png",5,2);
//
////58 Fourteen
//            questionRepositories.questionCreator("What is the maximum number of clubs that can be used in tournament golf?","Fourteen","Twelve","Ten","Twenty","Fourteen","“No more than fourteen clubs may be used in tournament play.”","fourteen_59.png",5,3);
//
////59 Scotland
//            questionRepositories.questionCreator("In which country was golf invented?","Scotland","Paris","America","France","Scotland","“Although its name is originally Dutch, the game of golf is believed to have been invented in Scotland.”","scotland_60.png",5,2);
//
////60 Three
//            questionRepositories.questionCreator("How many holes are there in a bowling ball?","Three","Two","Four","One","Three","“There are three holes in a regulation bowling ball: one for the thumb and two for the first two fingers.”","three_61.png",5,1);
//
////61 Ninety centimeters
//            questionRepositories.questionCreator("How long must a billiard cue be?","Ninety centimeters","Eighty centimeters","Seventy centimeters","Sixty centimeters","Ninety centimeters","“A billiard cue must be at least thirty five inches (ninety centimeters) long. In regulation play, there is no maximum length.”","ninety_centimeters_62.png",5,3);
//
////62 Bowling
//            questionRepositories.questionCreator("Which sport is also known as tenpins?","Bowling","Tennis","Chess","Darts","Bowling","“Bowling is called tenpins, because the object of the game is to knock down ten pins that are arranged in a triangle.”","bowling_63.png",5,2);
//
////63 Lady paramount
//            questionRepositories.questionCreator("What is the president of a woman’s archery tournament called?","Lady paramount","Mocking jay","Woman archer","Archeries","Lady paramount","“A lady paramount presides over a woman’s archery tournament. Sometimes the term is also used for a sponsor of an amateur match.”","lady_paramount_64.png",5,3);
//
////64 Billiards
//            questionRepositories.questionCreator("What was the first sport to have a world championship?","Billiards","Athlete","Tennis","Basketball","Billiards","“The first world championships event for any sport was for billiards. It was held in 1873.”","billiards_65.png",5,3);
//
////65 Jack
//            questionRepositories.questionCreator("In lawn bowling, what is the target ball called?","Jack","Jockey","Point","Target","Jack","“In lawn bowling, players roll bowls over a grass surface toward a small white ball called a jack.”","jack_66.png",5,3);
//
////66 The Stanley Cup
//            questionRepositories.questionCreator("What is the name of the prize awarded the National Hockey League champions?","The Stanley Cup","The Mayor's Cup","The Janna Cup","The Aisk Cup","The Stanley Cup","“Lord Stanley awarded the first prize named for him 1893 to Canada’s championship hockey team. The Stanley Cup was made international in 1914.”","the_stanley_cup_67.png",5,3);
//
////67 Puck
//            questionRepositories.questionCreator("What is the object that hockey players send across the ice called?","Puck","Pack","Puckey","Pecker","Puck","“Hockey players use a rubber disc called a puck. Getting it into the opposing team’s goal earns a point.”","puck_68.png",5,3);
//
////68 Figure Skating
//            questionRepositories.questionCreator("What sport was the first to be made an event in the Winter Olympic Games?","Figure Skating","Hockey","Skiing","Snow boarding","Figure Skating","“Figure skating was named an official sport of the winter Olympics in 1908.”","figure_skating_69.png",5,3);
//
////69 Skiing
//            questionRepositories.questionCreator("In what sport would one find a loppet?","Skiing","Skating","Hockey","Snow boarding","Skiing","“A loppet is a cross-country ski run.”","skiing_70.png",5,3);
//
////70 Germany
//            questionRepositories.questionCreator("What country hosted the 2006 FIFA World cup?","Germany","Australia","Mexico","America","Germany","“Germany hosted the 2006 FIFA World Cup football competition. The winner was Italy.”","germany_71.png",5,3);
//
////71 1930
//            questionRepositories.questionCreator("When was the first World Cup played?","1930","1940","1890","2000","1930","“In 1904 the Fédération International de Football Association (FIFA) was formed to regulate the rules of soccer. In 1930, FIFA organized the first world cup.”","1930_72.png",5,3);
//
////72 Algeria
//            questionRepositories.questionCreator("In what country does Mouloudia Club d’oran play?","Algeria","Afghanistan","Africa","America","Algeria","“Mouloudia Club d’oran is a well-known football team. Its headquarters are in the Algeria port city of Oran.”","algeria_73.png",5,3);
//
////73 Forty five
//            questionRepositories.questionCreator("How many players are on an America football team?","Forty five","Fifty five","Eleven","Ten","Forty five","“National Football league (NFL) teams are allowed to have to forty five players. Only eleven of them can be o the field at any one time.”","forty_five_74.png",5,2);
//
////74 1967
//            questionRepositories.questionCreator("When was the first Super Bowl played?","1967","1980","1970","1950","1967","“The Super Bowl, the professional American football championship game, was first held in 1967. It has been played every year since then.”","1967_75.png",5,3);
//
////75 Five
//            questionRepositories.questionCreator("How many rings are there on the Olympic flag?","Five","Three","Four","Six","Five","“The Olympic Flag has five linked rings representing the five parts of the world joined by the Games: the Americas, Europe, Asia, Africa, and Australia.”","five_76.png",5,1);
//
////76 776 BCE
//            questionRepositories.questionCreator("What is the date of the first known Olympic Games?","776 BCE","600 BCE","770 BCE","800 BCE","776 BCE","“The first Olympic Games, for which three are written records, took place in Greece in 776 BCE. Milon of Croton was a wrestler who six championships.”","776_bce_77.png",5,3);
//
////77 1896 CE
//            questionRepositories.questionCreator("What year did the modern Olympics start?","1896 CE","1999 CE","1890 CE","1870 CE","1896 CE","“The First modern Olympic Games were held in 1896 in Athens, Greece.”","1896_ce_78.png",5,3);
//
////78 Four years
//            questionRepositories.questionCreator("How often are the Olympic Games held?","Four years","Three years","Five years","Two years","Four years","“The summer Games and the Winter Games are held once every four years. Until the early 1990s the summer and winter games were held in the same year. Today they are separated by two years.”","four_years_79.png",5,2);
//
////79 All out
//            questionRepositories.questionCreator("What term applies when all batsmen have all been retired?","All out","Default","Strike","Pass out","All out","“In cricket, the term “all out” means that a side’s batsmen have all been retired.”","all_out_80.png",5,2);
//
////80 Bowler
//            questionRepositories.questionCreator("In cricket, what is the name of the player who delivers the ball?","Bowler","Pitcher","Thrower","Shooter","Bowler","“The bowler, delivering the ball with a straight arm, tries to hit the wicket with the ball so that the bails fall. The one of several ways that the batsman is dismissed.”","bowler_81.png",5,2);
//
////81 1975
//            questionRepositories.questionCreator("In what year was the first World Cup of cricket held?","1975","1967","1980","1990","1975","“In 1975 the first World Cup of cricket was played in England. The event was a great success and continued at four-year intervals.”","1975_82.png",5,3);
//
////82 Six
//            questionRepositories.questionCreator("In cricket, how many throws make an over?","Six","Three","Four","Five","Six","“When the bowler has thrown the ball six times, an “over” is completed.”","six_83.png",5,3);
//
////83 Baseball
//            questionRepositories.questionCreator("In the US, what sport is known as “the pastime”?","Baseball","Basketball","Chess","Archery","Baseball","“Baseball, a nine-player game involving throwing and hitting a ball, is one of the most popular professional sports in the US.”","baseball_84.png",5,1);
//
////84 Bullfight
//            questionRepositories.questionCreator("In what context might one see a matador?","Bullfight","Judo","Karate","Taekwondo","Bullfight","“The matador is the central human figure in a bullfight. Bullfighting was once popular in Spain and other Hispanic nations, but is less popular today.”","bullfight_85.png",5,3);
//
////85 Cricket
//            questionRepositories.questionCreator("In what game would one find a googly?","Cricket","Hockey","Tennis","Badminton","Cricket","“A googly is a kind of delivery featured in the game of cricket.”","cricket_86.png",5,2);
//
////86 Delhi
//            questionRepositories.questionCreator("In what city were the Asian Games first held?","Delhi","Rio","Jamaica","Mexico","Delhi","“The first Asian Games competition was held in Delhi, India, in 1951. The city also hosted the games of 1982.”","delhi_87.png",5,3);
//
////87 Badminton
//            questionRepositories.questionCreator("Which of the following sports does not involve a ball?","Badminton","Tennis","Squash","Table tennis","Badminton","Badminton is a racquet sport played using racquets to hit a shuttlecock across a net. Although it may be played with larger teams, the most common forms of the game are \"singles\" and \"doubles\"","badminton_88.png",5,1);
//
////88 Long jump
//            questionRepositories.questionCreator("In which sport do you run and jump as far as possible into a sandpit?","Long jump","Volleyball","Javelin handwrite","Discus handwrite","Long jump","“Long jump is a track and field event in which athletes combine speed, strength and agility in an attempt to leap as far as possible from a take-off point”","long_jump_89.png",5,1);
//
////89 Cricket
//            questionRepositories.questionCreator("Which sport involves a bat, a ball and wickets?","Cricket","Baseball","Softball","Badminton","Cricket","“Cricket is a bat-and-ball game played between two teams of eleven players. It is set on a cricket centered on a twenty-meter twenty two-yard) pitch with two wickets each comprising a bail balanced on three stumps”.","cricket_90.png",5,2);
//
////90 Table tennis
//            questionRepositories.questionCreator("Which sport uses the lightest ball?","Table tennis","Softball","Baseball","Basketball","Table tennis","“Table tennis, also known as Ping-Pong, is a sport in which two or four players hit a lightweight ball back and forth across a table using small bats”","table_tennis_91.png",5,1);
//
////91 Golf
//            questionRepositories.questionCreator("Which outdoor sport involves a club, a ball and holes in the ground?","Golf","Baseball","Softball","Cricket","Golf","“Golf is a club-and-ball sport in which players use various clubs to hit balls into a series of holes on a course in as few strokes as possible”","golf_92.png",5,1);
//
////92 Boxing
//            questionRepositories.questionCreator("Which sport did Mike Tyson played?","Boxing","Dance sport","Sumo wrestling","Judo","Boxing","“Mike Tyson dominated boxing in the 1980s like no other in his sport ever did. With explosive power and fast knockouts, Tyson rose to stardom to become the youngest heavyweight champion in boxing history”","boxing_93.png",5,1);
//
////93 Football
//            questionRepositories.questionCreator("Which the national game of Brazil?","Football","Baseball","Hockey","Fencing","Football","“Football is the most popular sport in Brazil. Other than football, sports like volleyball, mixed martial arts, basketball, and motor sports, especially Formula One, enjoy high levels of popularity”","football_94.png",5,1);
//
////94 Canada
//            questionRepositories.questionCreator("Which country has Lacrosse as its national summer sport?","Canada","America","France","Australia","Canada","“It was declared a national summer sport by an act of parliament in 1994. Lacrosse was an Olympic event in 1904 and 1908 (a men\'s competition, both gold were won by Canada).”","canada_95.png",5,3);
//
////95 China
//            questionRepositories.questionCreator("Which country hosted 2008 Olympics?","China","India","America","Mexico","China","“The 2008 Summer Olympic Games, officially known as the Games of the XXIX Olympiad and commonly known as Beijing 2008, was an international multi-sport event that was held from 8 to 24 August 2008 in Beijing, China”","china_96.png",5,2);
//
////96 James Naismith
//            questionRepositories.questionCreator("Who invented basketball?","James Naismith","Bladder Buddy","William G. Morgan","George Hancock","James Naismith","“James Naismith was a Canadian-American physical educator, physician, chaplain, sports coach and innovator. He invented the game of basketball at age 30 in 1891”","james_naismith_97.png",5,3);
//
////97 West Germany
//            questionRepositories.questionCreator("Who were the runners-up in the 1966 World Cup?","West Germany","Africa","Australia","China","West Germany","The 1966 FIFA World Cup was the eighth FIFA World Cup and was held in England from 11 to 30 July 1966. England beat West Germany 4–2 in the final, winning the Jules Rimet Trophy.","west_germany_98.png",5,3);
//
////98 Milan
//            questionRepositories.questionCreator("In which city the San Siro Stadium is located in Italy?","Milan","Jamaica","Rio","India","Milan","“San Siro. The Giuseppe Meazza commonly known as San Siro, is a football stadium in the San Siro district of Milan, Italy, which is the home of A.C. Milan and Inter”","milan_99.png",5,3);
//
////99 1900
//            questionRepositories.questionCreator("When did women begin competing in the Olympics?","1900","1989","1980","1889","1900","“Even in the early years of the modern Olympics, women were not well represented (consequently a rival Women\'s Olympics was held). Women participated for the first time at the 1900 Paris Games with the inclusion of women\'s events in lawn tennis and golf. Women\'s athletics and gymnastics debuted at the 1928 Olympics.”","1900_100.png",5,3);
//            //TODO END OF SPORTS
//
//            //TODO TECHNOLOGY
//
////1 Year-1880s
//            questionRepositories.questionCreator("In which decade was the American Institute of Electrical Engineers (AIEE) founded?","Year-1880s","1850s","1930s","1950s","Year-1880s","The IEEE (Institute of electrical and Electronics Engineer) was formed in 1963 by the merge of the institute of radio Engineer (IRE, founded 1912) and the American institute of electrical engineers (AIEE, founded 1884)","year_1880s_1.png",7,3);
//
////2 Year-1900
//            questionRepositories.questionCreator("In which decade with the first transatlantic radio broadcast occur?","Year-1900","1850s","1860s","1870s","Year-1900","On December 12, 1901, a radio transmission received by Gulielmo Marcon resulted in the first transmission of a transatlantic wireless signal (Morse code) from Poldhu Cornwall, To St. John’s Newfoundland","year_1900_2.png",7,3);
//
////3 1970's
//            questionRepositories.questionCreator("In which decade was the SPICE simulator introduced?","1970's","1980's","1950's","1960's","1970's","SPICE (Simulation Program with Integrated Circuit Emphasis) was introduced in May 1972 by University of Berkeley California.","1970_s_3.png",7,3);
//
////4 Variant Voltage Variable Frequency
//            questionRepositories.questionCreator("What does VVVF stand for?","Variant Voltage Variable Frequency","Variable voltage Vile Frequency","Variable Velocity Variable Fun","Very Very Vicious Frequency","Variant Voltage Variable Frequency","it is a method of controlling the speed of an AC induction motor, whereby speed, current and torque can all be accurately controlled.","variant_voltage_variable_frequency_4.png",7,2);
//
////5 Programmable Logic Computer
//            questionRepositories.questionCreator("What does the term PLC stand for?","Programmable Logic Computer","Programmable Lift Controller","Programmable List Control","Piezo Lamp Connector","Programmable Logic Computer","Used in manufacturing, engineering, and process operations.","programmable_logic_computer_5.png",7,2);
//
////6 Amplitude modulation
//            questionRepositories.questionCreator("What Does AM mean?","Amplitude modulation","Angelo Marconi","Amperes","Anno median","Amplitude modulation","Amplitude modulation was the first type to be used in radio it works well with HF and Morse code.","amplitude_modulation_6.png",7,1);
//
////7 Sundar Pichai
//            questionRepositories.questionCreator("Who is the present CEO of Google?","Sundar Pichai","Lawrence E. Page","Paul Allen","Larry Ellison","Sundar Pichai","“Pichai Sundararajan, also known as Sundar Pichai, is an Indian American business executive. Pichai is the chief executive officer of Google Inc.”","sundar_pichai_7.png",7,1);
//
////8 Mark Hurd & Safra Catz
//            questionRepositories.questionCreator("Who is the present CEO of ORACLE?","Mark Hurd & Safra Catz","Larry Ellison","Paul Allen","Richard Stallman","Mark Hurd & Safra Catz","“Ellison, who is believed to be the fifth-wealthiest man in the world, will be replaced by Mark Hurd and Safra Catz, Oracle said. In an unusual move, Hurd and Catz will both be named CEO of the company—not co-CEOs. Ellison will become executive chairman and chief technology officer September 18, 2014.”","mark_hurd_&_safra_catz_8.png",7,3);
//
////9 James Gosling
//            questionRepositories.questionCreator("Who invented JAVA?","James Gosling","Sabeer Bhatia","Richard Stallman","Larry Wall","James Gosling","“James Arthur Gosling, OC (born May 19, 1955) is a Canadian computer scientist, best known as the founder and lead designer behind the Java programming language.”","james_gosling_9.png",7,2);
//
////10 Larry Wall
//            questionRepositories.questionCreator("Who invented PERL programming language?","Larry Wall","Sergey Brin","Sam Pitroda","Aaron Swartz","Larry Wall","“Larry Wall (born September 27, 1954) is an American Computer programmer and author. He created the PERL programming language.”","larry_wall_10.png",7,3);
//
////11 Semiconductor
//            questionRepositories.questionCreator("What is a transistor?","Semiconductor","Conductor","Insulator","Superconductor","Semiconductor","“A transistor is a semiconductor device used to amplify or switch electronic signals and electrical power. It is composed of semiconductor material usually with at least three terminals for connection to an external circuit.”","semiconductor_11.png",7,1);
//
////12 URL
//            questionRepositories.questionCreator("What is the formal term for a web address?","URL","Netsite","Website","FTP","URL","“Web Address is a URL with HTTP/HTTPS. The term \"web address\" is a synonym for a URL that uses the HTTP or HTTPS protocol. The Uniform Resource Locator (URL) was developed by Tim Berners-Lee in 1994 and the Internet Engineering insertQuestionAsync Force (IETF) URI working group. Today, the format of the URL has not changed.”","url_12.png",7,1);
//
////13 Year 2007
//            questionRepositories.questionCreator("When did the Apple iPhone first become available?","Year 2007","Year 2005","Year 1999","Year 2000","Year 2007","“On January 9, 2007, Steve Jobs announced iPhone at the Macworld convention, receiving substantial media attention. Jobs announced that the first iPhone would be released later that year. On June 29, 2007, the first iPhone was released”","year_2007_13.png",7,2);
//
////14 Central Processing Unit
//            questionRepositories.questionCreator("In terms of computing, what does CPU stand for?","Central Processing Unit","Center Process Unit","Collide Part Unit","Care Prepare Undone","Central Processing Unit","“A central processing unit (CPU) is the electronic circuitry within a computer that carries out the instructions of a computer program by performing the basic arithmetic, logical, control and input/output (I/O) operations specified by the instructions”","central_processing_unit_14.png",7,1);
//
////15 Year 1889
//            questionRepositories.questionCreator("Nintendo was founded after the year of?","Year 1889","Year 1990","Year 1900","Year 1880","Year 1889","“Nintendo is founded, September 23, 1889. Founded in 1889 by Fusajiro Yamauchi in Kyoto, Japan, The Nintendo Playing Card Company was a small business that would go on to revolutionize gaming around the world”","year_1889_15.png",7,3);
//
////16 Random Access Memory
//            questionRepositories.questionCreator("What does RAM stand for?","Random Access Memory","Rent Accurate Memory","Random Act Memory","Raise Access Memo","Random Access Memory","“Random-access memory RAM is a form of computer data storage that stores data and machine code currently being used”","random_access_memory_16.png",7,1);
//
////17 Read Only Memory
//            questionRepositories.questionCreator("What does ROM stand for?","Read Only Memory","React Own Memo","Right Of Memory","Read own Memory","Read Only Memory","“Read-only memory (ROM) is a type of non-volatile memory used in computers and other electronic devices”","read_only_memory_17.png",7,1);
//
////18 World Wide Web
//            questionRepositories.questionCreator("What does the abbreviation WWW stand for?","World Wide Web","Work Web Work","World Work Web","Work Wide Web","World Wide Web","“The World Wide Web dramatically increased the use of the internet. Tim Berners-Lee established the convention in 1989”","world_wide_web_18.png",7,1);
//
////19 Hyper Text Transfer Protocol
//            questionRepositories.questionCreator("What does http stand for?","Hyper Text Transfer Protocol","Hide Text Transfer Protocol","Hyper Translate Transfer Protocol","Highlight Text Translate Protocol","Hyper Text Transfer Protocol","“The Hypertext Transfer Protocol (HTTP) is an application protocol for distributed, collaborative, and hypermedia information systems. HTTP is the foundation of data communication for the World Wide Web”","hyper_text_transfer_protocol_19.png",7,2);
//
////20 Archie
//            questionRepositories.questionCreator("Which one is the first search engine in internet?","Archie","Google","Altavista","WAIS","Archie","“The first few hundred web sites began in 1993 and most of them were at colleges, but long before most of them existed came Archie. The first search engine created was Archie, created in 1990 by Alan Emtage, a student at McGill University in Montreal.”","archie_20.png",7,2);
//
////21 One hundred twenty eight bit
//            questionRepositories.questionCreator("Number of bit used by the IPv6 address?","One hundred twenty eight bit","Sixty four bit","Thirty two bit","Two hundred fifty six bit","One hundred twenty eight bit","“In IPv4, each octet consists of a decimal number ranging from 0 to 255. These numbers are typically separated by periods. In IPv6, addresses are expressed as a series of eight 4-character hexadecimal numbers, which represent 16 bits each (for a total of 128 bits)”","one_hundred_twenty_eight_bit_21.png",7,3);
//
////22 Nexus
//            questionRepositories.questionCreator("Which one is the first web browser invented in 1990?","Nexus","Internet Explorer","Mosaic","Mozilla","Nexus","“The first web browser was invented in 1990 by Tim Berners-Lee. It was called WorldWideWeb (no spaces) and was later renamed Nexus”","nexus_22.png",7,3);
//
////23 Creeper virus
//            questionRepositories.questionCreator("First computer virus is known as?","Creeper virus","Rabbit","Elk Cloner","SCA virus","Creeper virus","“Creeper virus is a computer virus that is most commonly recognized as the first computer virus. In 1971, Bob Thomas at BBN created Creeper as an experimental self-duplicating program that was intended not to inflict damage on, but to illustrate a mobile application. Creeper corrupted DEC PDP-10 computers operating on the TENEX operating system by messing around the installed printers, displaying the message “I’m the creeper, catch me if you can!””","creeper_virus_23.png",7,3);
//
////24 Security
//            questionRepositories.questionCreator("Firewall in computer is used for?","Security","Data Transmission","Authentication","Monitoring","Security","“A firewall is a system designed to prevent unauthorized access to or from a private network. You can implement a firewall in either hardware or software form, or a combination of both. Firewalls prevent unauthorized internet users from accessing private networks connected to the internet, especially intranets.”","security_24.png",7,2);
//
////25 COBOL
//            questionRepositories.questionCreator("Which of the following is not a database management software?","COBOL","MySQL","Sybase","Oracle","COBOL","“COBOL an acronym for \"common business-oriented language\" is a compiled English-like computer programming language designed for business use”","cobol_25.png",7,2);
//
////26 Apple
//            questionRepositories.questionCreator("Mac operating system is developed by which company?","Apple","IBM","Microsoft","Samsung","Apple","“Mac OS, operating system (OS) developed by the American computer company Apple Inc. The OS was introduced in 1984 to run the company\'s Macintosh line of personal computers (PCs).”","apple_26.png",7,1);
//
////27 Washington
//            questionRepositories.questionCreator("Where is the headquarters of Microsoft office located?","Washington","Texas","California","New York","Washington","“Microsoft Corporation (abbreviated as MS) is an American multinational technology company with headquarters in Redmond, Washington”","washington_27.png",7,2);
//
////28 Image file
//            questionRepositories.questionCreator(".gif is an extension of?","Image file","Video file","Audio file","Word file","Image file","“GIF, or Graphic Interchange Format, is a file extension for an often animated raster graphics file and is the second most common image format used on the World Wide Web after JPEG. GIF uses the LZW compression algorithm and is owned by Unisys”","image_file_28.png",7,2);
//
////29 Language 2000
//            questionRepositories.questionCreator("How many computer languages are in use?","Language 2000","Language 2500","Language 3000","Language 4000","Language 2000","“There are about 2,000 computer languages in active use, whereas there were only 15 in use in 1970”","language_2000_29.png",7,3);
//
////30 Steve Jobs
//            questionRepositories.questionCreator("Who founded Apple Computer?","Steve Jobs","Paul Allen","Mark Zuckerberg","Bill Gates","Steve Jobs","“Steve Jobs founded Apple Computer with Steve Wozniak and another partner in 1976. Jobs became the chairman and CEO of apple in 1996”","steve_jobs_30.png",7,2);
//
////31 Mouse
//            questionRepositories.questionCreator("What is the name for a computer pointing device?","Mouse","Hand","Pointer","Insertion Point","Mouse","“A mouse allows a computer user to move to different parts of the screen without keyboard”","mouse_31.png",7,1);
//
////32 Year 1995
//            questionRepositories.questionCreator("When was the DVD introduced?","Year 1995","Year 1997","Year 2000","Year 1889","Year 1995","“Compact discs that stored sound were introduced in 1982. By the mid-1980’s new discs called CD-ROMs could store pictures and computer programs”","year_1995_32.png",7,3);
//
////33 Apple
//            questionRepositories.questionCreator("Who is the maker of iPhone?","Apple","Samsung","Cherry mobile","Huawei","Apple","“Apple computer is the maker of the iPhone. The telephone and miniature computer first went on sale in 2007”","apple_33.png",7,1);
//
////34 Year 2001
//            questionRepositories.questionCreator("In what year was the iPod first sold?","Year 2001","Year 2000","Year 1999","Year 1998","Year 2001","“Apple’s iPod portable music device was first released in 2001. The initial version had a 5GB hard drive”","year_2001_34.png",7,2);
//
////35 Mosaic
//            questionRepositories.questionCreator("What was the first graphical browser for the World Wide Web?","Mosaic","Nexus","Internet Explorer","Mozilla","Mosaic","“Invented by Marc Andreesen in 1993, Mosaic was the first graphical browser for the World Wide Web”","mosaic_35.png",7,3);
//
////36 Chess
//            questionRepositories.questionCreator("What game was Deep Blue skilled at?","Chess","Basketball","Darts","Volleyball","Chess","“Deep Blue, a computer built by IBM, was a whiz at chess. In fact, it beat world chess champion Garry Kasparov twice”","chess_36.png",7,3);
//
////37 Microsoft
//            questionRepositories.questionCreator("What company created XBOX?","Microsoft","Sony","Nintendo","Apple","Microsoft","“Xbox is a video gaming brand created and owned by Microsoft of the United States. It represents a series of video game consoles developed by Microsoft, with three consoles released in the sixth, seventh and eighth generations, respectively.”","microsoft_37.png",7,2);
//
////38 Honda
//            questionRepositories.questionCreator("Which car company produces the Civic?","Honda","Skoda","Audi","Nissan","Honda","“The Civic is a line of compact cars developed and manufactured by Honda. In North America, the Civic is the second-longest continuously running nameplate from a Japanese manufacturer; only its perennial rival, the Toyota Corolla, introduced in 1968, has been in production longer.”","honda_38.png",7,2);
//
////39 Year 2013
//            questionRepositories.questionCreator("When was the Sony PlayStation 4 released?","Year 2013","Year 1995","Year 2016","Year 2010","Year 2013","“The console was released on November 15, 2013, in the United States and Canada, followed by further releases on November 29, 2013. By the end of 2013, the PS4 was launched in more European, Asian and South American countries The PS4 released in Japan at ¥39,980 on February 22, 2014.”","year_2013_39.png",7,3);
//
////40 Safari
//            questionRepositories.questionCreator("Which of the following web browser is developed by Apple?","Safari","Opera","Firefox","Chrome","Safari","“Safari is a web browser developed by Apple based on the WebKit engine. First released in 2003 with Mac OS X Panther, a mobile version has been included in iOS devices since the introduction of the iPhone in 2007”","safari_40.png",7,1);
//
////41 London
//            questionRepositories.questionCreator("Where did the world’s first underground railway system open?","London","France","Hungary","U.S.A","London","“The London Underground first opened as an \"underground railway\" in 1863 and its first electrified underground line opened in 1890, making it the world\'s first metro system.”","london_41.png",7,3);
//
////42 Motorola
//            questionRepositories.questionCreator("What was the first company to produce a handheld mobile telephone?","Motorola","Nokia","Sony Ericson","Samsung","Motorola","“Motorola was the first company to produce a handheld mobile phone. On April 3, 1973, Martin Cooper, a Motorola researcher and executive, made the first mobile telephone call from handheld subscriber equipment, placing a call to Dr. Joel S. Engel of Bell Labs, his rival.”","motorola_42.png",7,2);
//
////43 Intel 386DX
//            questionRepositories.questionCreator("What was the first 32 bit microprocessor from Intel?","Intel 386DX","Intel Pentium 80 MHz","Intel 486SI","Intel Athlon XP","Intel 386DX","“The Intel 80386, also known as i386 or just 386, is a 32-bit microprocessor introduced in 1985. The first versions had 275,000 transistors and were the CPU of many work stations and high-end personal computers of the time.”","intel_386dx_43.png",7,3);
//
////44 Graham Bell
//            questionRepositories.questionCreator("Which inventor introduced the telephone, in 1876?","Graham Bell","Thomas Watson","Michael Faraday","Thomas Edison","Graham Bell","“Alexander Graham Bell was a Scottish-born scientist, inventor, engineer, and innovator who is credited with inventing and patenting the first practical telephone. He also founded the American Telephone and Telegraph Company in 1885”","graham_bell_44.png",7,3);
//
////45 Machines
//            questionRepositories.questionCreator("What does M in I.B.M stand for?","Machines","Memory","Model","Management","Machines","“International Business Machines Corporation is an American multinational technology company headquartered in Armonk, New York, United States, with operations in over 170 countries.”","machines_45.png",7,2);
//
////46 CD-ROM
//            questionRepositories.questionCreator("Which of the following is not a part of the internet?","CD-ROM","Youtube","World wide web","Email","CD-ROM","“CD-ROM (Compact Disc, read-only-memory) is an adaptation of the CD that is designed to store computer data in the form of text and graphics, as well as hi-fi stereo sound”","cd_rom_46.png",7,1);
//
////47 Database
//            questionRepositories.questionCreator("What does DB stand for?","Database","Data Boot","Driver Base","Driver Boot","Database","“A database is an organized collection of data, stored and accessed electronically. Database designers typically organize the data to model aspects of reality in a way that supports processes requiring information, such as (for example) modelling the availability of rooms in hotels in a way that supports finding a hotel with vacancies.”","database_47.png",7,2);
//
////48 Louis Blériot
//            questionRepositories.questionCreator("Who was first to fly over the English Channel?","Louis Blériot","Harry Houdini","Howard Hughes","Charles Lindbergh","Louis Blériot","“First airplane flight across the English Channel: Louis Blériot crossed the Channel on July 25, 1909, winning the Daily Mail prize of £1,000.”","louis_blériot_48.png",7,3);
//
////49 Gameboy
//            questionRepositories.questionCreator("Which of the following game consoles was developed by Nintendo?","Gameboy","PlayStation","Xbox","Saturn","Gameboy","“In 1989, Nintendo released the Game Boy, which became the first handheld console to sell in large numbers”","gameboy_49.png",7,3);
//
////50 Nintendo
//            questionRepositories.questionCreator("What company developed “The Legends of Zelda” series of video game?","Nintendo","Ubisoft","Electronic Arts","Microsoft","Nintendo","“The Legend of Zelda: A Link to the Past is an action-adventure video game developed and published by Nintendo for the Super Nintendo Entertainment System video game console. It is the third installment in The Legend of Zelda series and was released in 1991 in Japan and 1992 in North America and Europe”","nintendo_50.png",7,3);
//
////51 Liquid Crystal Display
//            questionRepositories.questionCreator("What is LCD an acronym of?","Liquid Crystal Display","Liquid Cable Display","Liquid Creative Display","Liquid Clear Display","Liquid Crystal Display","“A liquid-crystal display is a flat-panel display or other electronically modulated optical device that uses the light-modulating properties of liquid crystals”","liquid_crystal_display_51.png",7,2);
//
////52 Rudolf Diesel
//            questionRepositories.questionCreator("Who invented the diesel engine?","Rudolf Diesel","Shawn Diesel","Bill Diesel","Vin Diesel","Rudolf Diesel","“Rudolf Christian Karl Diesel 18 March 1858 – 29 September 1913) was a German inventor and mechanical engineer famous for the invention of the diesel engine, and for his mysterious death at sea. Diesel was the subject of the 1942 film Diesel”","rudolf_diesel_52.png",7,3);
//
////53 Language
//            questionRepositories.questionCreator("What is “L” in SQL stand for?","Language","Local","Light","Layer","Language","“SQL is a domain-specific language used in programming and designed for managing data held in a relational database management system, or for stream processing in a relational data stream management system.”","language_53.png",7,2);
//
////54 PlayStation
//            questionRepositories.questionCreator("Which of the following game consoles was developed by Sony?","PlayStation","Wii","Xbox","Gameboy","PlayStation","“It is created and owned by Sony Interactive Entertainment since December 3, 1994, with the launch of the original PlayStation in Japan. ... The first handheld game console in the PlayStation series, the PlayStation Portable or PSP, sold a total of 80 million units worldwide by November 2013”","playstation_54.png",7,2);
//
////55 Image file
//            questionRepositories.questionCreator("What type of file is .JPG file?","Image file","Movie file","System file","Text file","Image file","“JPEG is a commonly used method of lossy compression for digital images, particularly for those images produced by digital photography. The degree of compression can be adjusted, allowing a selectable tradeoff between storage size and image quality”","image_file_55.png",7,2);
//
////56 Kodak
//            questionRepositories.questionCreator("What company invented the first digital camera?","Kodak","Cannon","Nikon","Olympus","Kodak","“Eastman Kodak; world without iPhones or Instagram, where one company reigned supreme. Such a world existed in 1973, when Steven Sasson, a young engineer, went to work for Eastman Kodak. Two years later he invented digital photography and made the first digital camera.”","kodak_56.png",7,1);
//
////57 Sweden
//            questionRepositories.questionCreator("In what country is Ericsson’s headquarters located?","Sweden","China","Finland","U.S.A","Sweden","“The company was founded in 1876 by Lars Magnus Ericsson as of 2016 it is headquartered in Stockholm, Sweden”","sweden_57.png",7,2);
//
////58 Samsung
//            questionRepositories.questionCreator("Which manufactured released the galaxy tab series?","Samsung","Sony","Motorola","LG","Samsung","“The Samsung Galaxy Tab is a line of Android-based tablet computers produced by Samsung Electronics. The first model in the series, the 7-inch Samsung Galaxy Tab, was presented to the public on 2 September 2010 at the IFA in Berlin”","samsung_58.png",7,1);
//
////59 Downloading
//            questionRepositories.questionCreator("Saving a file from the internet onto your desktop is called?","Downloading","Uploading","Transferring","Storing","Downloading","“In computer networks, to download (abbreviation DL) is to receive data from a remote system, typically a server such as a web server, an FTP server, an email server, or other similar systems. This contrasts with uploading, where data is sent to a remote server”","downloading_59.png",7,1);
//
////60 Bugs
//            questionRepositories.questionCreator("Errors in computing programs are called?","Bugs","Follies","Mistake","Spam","Bugs","“A software bug is an error, flaw, failure or fault in a computer program or system that causes it to produce an incorrect or unexpected result, or to behave in unintended ways”","bugs_60.png",7,2);
//
////61 IBM
//            questionRepositories.questionCreator("Which American Computer Company is also known by the nick name “Big blue”?","IBM","Microsoft","Apple","Compaq Corporation","IBM","“International Business Machines Corporation, perhaps better known by its acronym IBM, is called the Big Blue. Among the blue-chip stocks on the US stock market, IBM was a steady and consistently high performer over the years”","ibm_61.png",7,3);
//
////62 Raymond Samuel Tomlinson
//            questionRepositories.questionCreator("Who invented mail system and @ symbol for address?","Raymond Samuel Tomlinson","Ian Murdock","David Bowie","Paul Buchheit","Raymond Samuel Tomlinson","“Ray Tomlinson is the reason your e-mail address includes a \'@\' symbol. For this reason – and many others – you wouldn\'t be remiss in calling Tomlinson the inventor of e-mail”","raymond_samuel_tomlinson_62.png",7,2);
//
////63 Light Fidelity
//            questionRepositories.questionCreator("What is the full form of Li-Fi network?","Light Fidelity","Laser Frequency","Limitless finite","Light field","Light Fidelity","“Li-Fi is a technology for wireless communication between devices using light to transmit data and position. In its present state only LED lamps can be used for the transmission of visible light”","light_fidelity_63.png",7,3);
//
////64 Charles Babbage
//            questionRepositories.questionCreator("Who considered as the father of computing?","Charles Babbage","Von Neumann","Graham Bell","Claude Shannon","Charles Babbage","“Charles Babbage was considered to be the father of computing after his invention and concept of the Analytical Engine in 1837. The Analytical Engine contained an Arithmetic Logic Unit (ALU), basic flow control, and integrated memory; hailed as the first general-purpose computer concept”","charles_babbage_64.png",7,3);
//
////65 Matti Makkonen
//            questionRepositories.questionCreator("Who is known as the father of SMS?","Matti Makkonen","Samir Bhatia","Anuj Gupta","Thomas Kurian","Matti Makkonen","“Father of SMS passes away. Matti Makkonen, known as the father of SMS after developing the idea of sending messages via mobile networks, has died. LONDON Matti Makkonen, known as the father of SMS after developing the idea of sending messages via mobile networks, has died”","matti_makkonen_65.png",7,3);
//
////66 Internet of things
//            questionRepositories.questionCreator("‘IoT’ refers to?","Internet of things","Intranet of Teleservices","Internet Organism of Teleservices","Internet Organism of Telecommunication","Internet of things","“The Internet of Things (IoT) is the network of physical devices, vehicles, home appliances, and other items embedded with electronics, software, sensors, actuators, and connectivity which enables these things to connect and exchange data, creating opportunities for more direct integration of the physical world into computer-based systems, resulting in efficiency improvements, economic benefits, and reduced human exertions”","internet_of_things_66.png",7,3);
//
////67 Mouse
//            questionRepositories.questionCreator("Which is an Input device?","Mouse","Printer","Monitor","Speaker","Mouse","“In computing, an input device is a piece of computer hardware equipment used to provide data and control signals to an information processing system such as a computer or information appliance. Examples of input devices include keyboards, mouse, scanners, digital cameras and joysticks”","mouse_67.png",7,1);
//
////68 LOTUS
//            questionRepositories.questionCreator("Which one of the following is not a computer language?","LOTUS","BASIC","C++","JAVA","LOTUS","“A spreadsheet program designed for IBM-compatible personal computers by Lotus Corporation in 1982. Lotus 1-2-3 was the first publicly available program to combine graphics, spreadsheet functions and data management (three functions, hence the name).”","lotus_68.png",7,2);
//
////69 Microsoft
//            questionRepositories.questionCreator("Which company bought the popular video teleconferencing software ‘Skype’","Microsoft","Google","Accenture","Oracle","Microsoft","“Microsoft bought Skype in May 2011 for $8.5 billion. Skype division headquarters are in Luxembourg, but most of the development team and 44% of all the division\'s employees are still situated in Tallinn and Tartu, Estonia.”","microsoft_69.png",7,3);
//
////70 Facebook
//            questionRepositories.questionCreator("Mark Zuckerberg is the owner of?","Facebook","Google","Linux","Linkedln","Facebook","“Mark Elliot Zuckerberg is an American technology entrepreneur and philanthropist. He is known for co-founding and leading Facebook as its chairman and chief executive officer”","facebook_70.png",7,1);
//
////71 Secure
//            questionRepositories.questionCreator("What does the letter “S” stand for in the Web terminology “HTTPS”?","Secure","Safe","Short","Shorter","Secure","“Hypertext Transfer Protocol Secure is an extension of the Hypertext Transfer Protocol for secure communication over a computer network, and is widely used on the Internet”","secure_71.png",7,2);
//
////72 Ericson
//            questionRepositories.questionCreator("Who is the founder of the Bluetooth?","Ericson","IBM","Apple","Dell","Ericson","“Dr. Jaap Haartsen, who invented Bluetooth while working at Ericsson in the 1990s, has been nominated as a finalist by the European Patent Office in the industry category for its European Inventor Award.”","ericson_72.png",7,2);
//
////73 Port 110
//            questionRepositories.questionCreator("What is the port number of POP3?","Port 110","Port 58","Port 80","Port 21","Port 110","“Post Office Protocol version 3 (POP3) is a standard mail protocol used to receive emails from a remote server to a local email client”","port_110_73.png",7,3);
//
////74 Silicon
//            questionRepositories.questionCreator("IC chips for computer are usually made of?","Silicon","Silver","Aluminum","Copper","Silicon","“An integrated circuit or monolithic integrated circuit (also referred to as an IC, a chip, or a microchip) is a set of electronic circuits on one small flat piece (or \"chip\") of semiconductor material, normally silicon”","silicon_74.png",7,3);
//
////75 Julian Assange
//            questionRepositories.questionCreator("Which one of the following is the founder of ‘WikiLeaks’","Julian Assange","Evans Williams","Jack Dorsey","Noah Glass","Julian Assange","“Julian Paul Assange is an Australian computer programmer and the editor of WikiLeaks. Assange founded WikiLeaks in 2006, but came to international attention in 2010, when WikiLeaks published a series of leaks provided by Chelsea Manning”","julian_assange_75.png",7,3);
//
////76 ASCII 128
//            questionRepositories.questionCreator("ASCII has how many codes?","ASCII 128","ASCII64","ASCII256","ASCII382","ASCII 128","“ASCII (American Standard Code for Information Interchange) is the most common format for text files in computers and on the Internet. In an ASCII file, each alphabetic, numeric, or special character is represented with a 7-bit binary number (a string of seven 0s or 1s). 128 possible characters are defined”","ascii_128_76.png",7,3);
//
////77 John Napier
//            questionRepositories.questionCreator("Logarithm table was invented by?","John Napier","John Douglas","John Doe","John Harrison","John Napier","“John Napier invented logarithms, but many other scientists and mathematicians helped develop Napier\'s logarithms to the system we use today. The first table of common logarithms was compiled by the English mathematician Henry Briggs”","john_napier_77.png",7,3);
//
////78 Uninterruptible Power Supply
//            questionRepositories.questionCreator("UPS stands for?","Uninterruptible Power Supply","Uninterruptible Power Standby","Universal Power Supply","Universal Power","Uninterruptible Power Supply","“An uninterruptible power supply or uninterruptible power source (UPS) is an electrical apparatus that provides emergency power to a load when the input power source or mains power fails”","uninterruptible_power_supply_78.png",7,3);
//
////79 Douglas Engelbart
//            questionRepositories.questionCreator("Who is known as the inventor of Computer mouse?","Douglas Engelbart","Herman Hollerith","Tom Cranston","Jack Kilby","Douglas Engelbart","“Jack Kilby and Robert Noyce invented the integrated circuit, which is otherwise known as the chip. Invented by Douglas Engelbart of Stanford Research Center in 1964, the first prototype computer mouse was made to use with a graphical user interface \'windows\'.”","douglas_engelbart_79.png",7,3);
//
////80 Pen Drive
//            questionRepositories.questionCreator("Which of the following is not a component in multimedia?","Pen Drive","Video","Data","Audio","Pen Drive","“A USB flash drive, also variously known as a thumb drive, pen drive, gig stick, flash stick, jump drive, disk key, disk on key flash-drive, memory stick USB stick or USB memory, is a data storage device that includes flash memory with an integrated USB interface”","pen_drive_80.png",7,3);
//
////81 SRAMS
//            questionRepositories.questionCreator("Caches are usually built out of?","SRAMS","DRAMS","PROM","EEPROM","SRAMS","“The Intel 80486 microprocessor, for example, contains an 8K memory cache, and the Pentium has a 16K cache. Such internal caches are often called Level 1 (L1) caches. Most modern PCs also come with external cache memory, called Level 2 (L2) caches. Like L1 caches, L2 caches are composed of SRAM but they are much larger.”","srams_81.png",7,3);
//
////82 Universal Serial Bus Port
//            questionRepositories.questionCreator("USB port stand for?","Universal Serial Bus Port","United Serial Bus Port","Universal Sequential Bus Port","Universal Serial BIOS Port","Universal Serial Bus Port","“A Universal Serial Bus (USB) is a common interface that enables communication between devices and a host controller such as a personal computer (PC). It connects peripheral devices such as digital cameras, mice, keyboards, printers, scanners, media devices, external hard drives and flash drives.”","universal_serial_bus_port_82.png",7,1);
//
////83 File Allocation Table
//            questionRepositories.questionCreator("In context of Computers, FAT stands for?","File Allocation Table","Folder Access Table","File Access Table","Folder Allocation Table","File Allocation Table","‘FAT\" stands for \"File Allocation Table,\" which keeps track of all your files and helps the computer locate them on the disk. FAT32 is an improvement to the original FAT system, since it uses more bits to identify each cluster on the disk.’”","file_allocation_table_83.png",7,2);
//
////84 On client
//            questionRepositories.questionCreator("Where are cookies stored?","On client","In HTML","In web.xml","None of these","On client","“A cookie is information stored on your computer by a website you visit. In some browsers, each cookie is a small file, but in Firefox, all cookies are stored in a single file, located in the Firefox profile folder. Cookies often store your settings for a website, such as your preferred language or location”","on_client_84.png",7,3);
//
////85 Guglielmo Marconi
//            questionRepositories.questionCreator("Who invented the radio?","Guglielmo Marconi","Sanjay Mehrotra","Eli Harari","Jack Yuan","Guglielmo Marconi","“Guglielmo Marconi, 1st Marquis of Marconi; 25 April 1874 – 20 July 1937) was an Italian inventor and electrical engineer known for his pioneering work on long-distance radio transmission and for his development of Marconi\'s law and a radio telegraph system”","guglielmo_marconi_85.png",7,3);
//
////86 Erasable Programmable Read Only Memory
//            questionRepositories.questionCreator("EPROM stand for?","Erasable Programmable Read Only Memory","Enter Programmable Read Only Memory","Execute Program Read Only Memory","Erasable Program Read Only Memory","Erasable Programmable Read Only Memory","“An EPROM, or erasable programmable read-only memory, is a type of memory chip that retains its data when its power supply is switched off”","erasable_programmable_read_only_memory_86.png",7,3);
//
////87 Fourth Generation Language
//            questionRepositories.questionCreator("4GL stand for?","Fourth Generation Language","Fourth Generation Light","Fourth Giving Life","Fourth Giving Light","Fourth Generation Language","“A fourth generation (programming) language (4GL) is a grouping of programming languages that attempt to get closer than 3GLs to human language, form of thinking and conceptualization. 4GLs are designed to reduce the overall time, effort and cost of software development.”","fourth_generation_language_87.png",7,2);
//
////88 Thomas Knoll
//            questionRepositories.questionCreator("Who invented Photoshop?","Thomas Knoll","Alexander Popov","Samuel Morse","Thomas Edison","Thomas Knoll","“Photoshop was developed in 1987 by the American brothers Thomas and John Knoll, who sold the distribution license to Adobe Systems Incorporated in 1988.Thomas Knoll, a PhD student at the University of Michigan, began writing a program on his Macintosh Plus to display grayscale images on a monochrome display.”","thomas_knoll_88.png",7,3);
//
////89 George Devol
//            questionRepositories.questionCreator("Who invented the first robot in the world?","George Devol","Thomas Edison","Samuel Morse","Alexander Popov","George Devol","“In 1954 George Devol invented the first digitally operated and a programmable robot called the Unimate. In 1956, Devol and his partner Joseph Engelberger formed the world\'s first robot company. In 1961, the first industrial robot, Unimate, went online in a General Motors automobile factory in New Jersey”","george_devol_89.png",7,3);
//
////90 Stanford University
//            questionRepositories.questionCreator("The expert system was developed by which university?","Stanford University","Harvard  University","University of Oxford","University of Cambridge","Stanford University","“The first expert system was developed in 1965 by Edward Feigenbaum and Joshua Lederberg of Stanford University in California, U.S. Dendral, as their expert system was later known, was designed to analyze chemical compounds.”","stanford_university_90.png",7,2);
//
////91 John McCarthy
//            questionRepositories.questionCreator("Who is the father of Artificial Intelligence?","John McCarthy","Thomas Edison","George Devol","Thomas Knoll","John McCarthy","“John McCarthy is one of the \"founding fathers\" of artificial intelligence, together with Marvin Minsky, Allen Newell and Herbert A. Simon. McCarthy coined the term \"artificial intelligence\" in 1955, and organized the famous Dartmouth Conference in summer 1956. This conference started AI as a field.”","john_mccarthy_91.png",7,2);
//
////92 Kevin Systrom
//            questionRepositories.questionCreator("Who invented Instagram?","Kevin Systrom","Larry page","Sergey Brin","Biz Stone","Kevin Systrom","“In 2010, Systrom co founded the photo-sharing and, later, video-sharing social networking service Instagram with Mike Krieger in San Francisco, California”","kevin_systrom_92.png",7,3);
//
////93 Susan Wojcicki
//            questionRepositories.questionCreator("Who is the CEO of Youtube?","Susan Wojcicki","Jawed karim","Anne Wojcicki","Steve Chen","Susan Wojcicki","“Susan Diane Wojcicki is an American technology executive. She has been the CEO of YouTube since February 2014. She is from Los Altos, California, and has a net worth of $410 million”","susan_wojcicki_93.png",7,3);
//
////94 Ben Silbermann
//            questionRepositories.questionCreator("Who is the founder of Pinterest?","Ben Silbermann","Brian Chesky","Drew Houston","John Collison","Ben Silbermann","“Ben Silbermann (born 1982)[1] is an American Internet entrepreneur who is the co-founder and CEO of Pinterest, a virtual pinboard which lets users organize images, links, recipes and other things”","ben_silbermann_94.png",7,3);
//
////95 Drew Houston
//            questionRepositories.questionCreator("Who is the founder of Dropbox?","Drew Houston","John Collison","Brian Chesky","Ben Silbermann","Drew Houston","“Dropbox was founded in 2007 by MIT students Drew Houston and Arash Ferdowsi as a startup company, with initial funding from seed accelerator Y Combinator”","drew_houston_95.png",7,3);
//
////96 Personal Computer Advance Technology
//            questionRepositories.questionCreator("PC/AT stands for?","Personal Computer Advance Technology","Programming Computer Advance Technology","Program Create Advance Teachnology","Personal Cite Advance technology","Personal Computer Advance Technology","“The IBM Personal Computer AT, more commonly known as the IBM AT and also sometimes called the PC AT or PC/AT, was IBM\'s second-generation PC, designed around the 6 MHz Intel 80286 microprocessor and released in 1984 as System Unit 5170.”","personal_computer_advance_technology_96.png",7,3);
//
////97 Sixty Four bits
//            questionRepositories.questionCreator("Word length of Super computers?","Sixty Four bits","Sixty Three bits","Eighty bits","Ninety bits","Sixty Four bits","“\"Word size\" refers to the number of bits processed by a computer\'s CPU in one go (these days, typically 32 bits or 64 bits).”","sixty_four_bits_97.png",7,3);
//
////98 Zhang Yiming
//            questionRepositories.questionCreator("Who is the founder of tik tok?","Zhang Yiming","Yi Xing","Zhang Heng","Bi Sheng","Zhang Yiming","“TikTok, also known as Douyin (Chinese: 抖音短视频; pinyin: Dǒuyīn duǎnshìpín; literally \"vibrato short video\") or Tik Tok, is a Chinese music video platform and social network that was launched in September 2016 by Zhang Yiming, founder of Toutiao.”","zhang_yiming_98.png",7,3);
//
////99 Satya Nadella
//            questionRepositories.questionCreator("Who is the CEO of Microsoft?","Satya Nadella","John Collison","Drew Houston","Brian Chesky","Satya Nadella","“Satya Nadella. Satya Narayana Nadella (born 19 August 1967) is an American business executive of Indian descent. He is the Chief Executive Officer (CEO) of Microsoft, succeeding Steve Ballmer in 2014”","satya_nadella_99.png",7,3);
//
////100 Integrated Services Digital Networks
//            questionRepositories.questionCreator("ISDN stands for?","Integrated Services Digital Networks","Integrated Service Drive Networks","Income Services Drive Networks","Integrated Success Digital Networks","Integrated Services Digital Networks","“ISDN stands for Integrated Services Digital Network. It is a design for a completely digital telephone/telecommunications network. It is designed to carry voice, data, images, and video, everything you could ever need.”","integrated_services_digital_networks_100.png",7,3);
//            //TODO END OF TECHLOGY
        }
    }

//    private class LongOperation extends AsyncTask<String, Void, String> {
//
//        @Override
//        protected String doInBackground(String... params) {
//
//            Log.d("Sample","Execute Sample");
//            return "Executed";
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//        }
//
//        @Override
//        protected void onPreExecute() {}
//
//    }

}

