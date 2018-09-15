package com.example.forest.quickguessv2;

import android.app.Application;
import android.app.ProgressDialog;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.forest.quickguessv2.DB.Categories.QuestionCategoryRepositories;
import com.example.forest.quickguessv2.DB.DB;
import com.example.forest.quickguessv2.DB.Points.Points;
import com.example.forest.quickguessv2.DB.Points.PointsRepositories;
import com.example.forest.quickguessv2.DB.Questions.QuestionRepositories;
import com.example.forest.quickguessv2.DB.User.UserRepositories;
import com.example.forest.quickguessv2.Helpers.Connectivity;

public class ApplicationClass extends Application{

    QuestionCategoryRepositories questionCategoryRepositories;
    QuestionRepositories questionRepositories;
    PointsRepositories pointsRepositories;
    Points points;
  /*  private RefWatcher refWatcher;

    public static RefWatcher getRefWatcher (Context context)
    {
        ApplicationClass app = (ApplicationClass) context.getApplicationContext();
        return app.refWatcher;
    }*/
    //        refWatcher = LeakCanary.install(this);


    @Override
    public void onCreate() {
        super.onCreate();
        questionCategoryRepositories = new QuestionCategoryRepositories(this);
        questionRepositories = new QuestionRepositories(this);
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
            pointsRepositories.sendPoints(points);
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
            //TODO ANIMALS
            //0 Starfish
//Found
            questionRepositories.questionCreator("﻿Which of following creatures has the power to grow lost parts?","Crab","Starfish","Squirrel","Squid","Starfish","Some species of starfish have the ability to regenerate lost arms and can regrow an entire new limb given time. A few can regrow a complete new disc from a single arm, while others need at least part of the central disc to be attached to the detached part.","starfish_1.png",3);

//1 Duck-billed platypus
//Found
            questionRepositories.questionCreator("Which is the most primitive living mammal?","Seal","Duck-billed platypus","Weasel","Hedgehog","Duck-billed platypus","Egg-laying Mammals There are only five living monotreme species: the duck-billed platypus and four species of echidna (also known as spiny anteaters). In some ways, monotremes are very primitive for mammals because, like reptiles and birds, they lay eggs rather than having live birth.","duck_billed_platypus_2.png",3);

//2 Krait
//Found
            questionRepositories.questionCreator("Which of the following creatures has the most toxic venom?","Kukri snake","Krait","Scorpion","Cobra","Krait","Its venom is highly haemotoxic. The Saw-scaled Viper is responsible for more human deaths in Asia that all the other venomous Asian snakes combined. Its highly haemotoxic venom is said to be 5 times more toxic than cobras and 16 more toxic than the Russell\'s Viper.","krait_3.png",3);

//3 Blue whale
//Found
            questionRepositories.questionCreator("Which animal is the largest in size?","African elephant","Blue whale","Giraffe","Killer whale","Blue whale","Blue whales are the largest animals ever known to have lived on Earth. These magnificent marine mammals rule the oceans at up to 100 feet long and upwards of 200 tons. Their tongues alone can weigh as much as an elephant. Their hearts is as much as an automobile.","blue_whale_4.png",3);

//4 Sperm whale
//Found
            questionRepositories.questionCreator("Which living being has the heaviest brain?","African elephant","Killer whale","Sea cow","Sperm whale","Sperm whale","The average adult human brain weighs about 3 pounds - the same weight as the average brain of a dolphin (which is also a very intelligent animal). But there are animals with larger brains that are not considered to be as intelligent as a dolphin. For instance, a sperm whale has a brain that weighs about 17 pounds.","sperm_whale_5.png",3);

//5 Nautilus
//Found
            questionRepositories.questionCreator("Which is the mythical being believed to have become extinct?","Narwhal","Unicorn","Nautilus","Dodo","Nautilus","According to fossil records, animals similar to the chambered nautilus have existed for about 500 million years. Although no regulations currently exist to protect them, the six living species of chambered nautilus appear to be in decline.","nautilus_6.png",3);

//6 Beaver
//Found
            questionRepositories.questionCreator("Which living being is a skilled engineer?","Tailor-bird","Beaver","Termite","Honey bee","Beaver","Beavers have long been recognized as the engineers of the forest, constantly reshaping their surroundings","beaver_7.png",3);

//7 Bat
//Found
            questionRepositories.questionCreator("Which living being whistle?","Whale","Dolphin","Shark","Bat","Bat","Whistling Like a Bat: Development of an Ultrasonic Whistle to Deter Bats from Wind Turbines. The whistles will produce sounds mimicking the spectrotemporal patterns of bat echolocation pulses, thereby enhancing the bats\' ability to detect, localize and avoid the moving blades","bat_8.png",3);

//8 Sea otter
//Found
            questionRepositories.questionCreator("Which of the following living being has also been found to be a tool-user?","Sea otter","Gorilla","Beaver","Spider","Sea otter","Sea otters are known for their ability to use stones as anvils or hammers to facilitate access to hard-to-reach prey items","sea_otter_9.png",3);

//9 Lizards
//Found
            questionRepositories.questionCreator("Which category creatures contain a type that can fly?","Cats","Lizards","Hedgehogs","Rats","Lizards","Draco, also known as flying lizard or flying dragon, is a reptile that belongs to the family Agamidae. There are 31 species of flying lizards that can be found in South and Southeast Asia (Philippines, Borneo, India, Malaysia, Indonesia…). Flying lizards live in tropical rainforests.","lizards_10.png",3);

//10 Tortoise
//Found
            questionRepositories.questionCreator("Which living being has, on an average, the highest life-span?","Tortoise","Man","Pelican","Cat","Tortoise","Tortoises generally have one of the longest lifespans of any animal, and some individuals are known to have lived longer than 150 years. Because of this, they symbolize longevity in some cultures, such as China.","tortoise_11.png",3);

//11 Africa
//Found
            questionRepositories.questionCreator("Where are zebra found?","Africa","South America","China","New Zealand","Africa","Plains zebra are found on the savannas from Sudan to northern Zimbabwe in eastern Africa. Grevy\'s zebras are now mostly restricted to parts of northern Kenya. Mountain zebras occur in southwestern Africa with cape mountain zebras in South Africa and Hartmann\'s mountain zebras in Namibia and Angola.","africa_12.png",3);

//12 Great Indian rhinoceros
//Found
            questionRepositories.questionCreator("Which of the following animals is on the verge of extinction?","Badger","Kangaroo","Gibbon","Great Indian rhinoceros","Great Indian rhinoceros","There are about 2,600 Indian Rhino left in the wild, but their numbers were less than 200 early in the 20th century. This demise was mainly caused by poaching and habitat loss. There recovery is one of two success stories in rhino conservation, the other being the Southern White Rhino.","great_indian_rhinoceros_13.png",3);

//13 Asiatic elephant
//Found
            questionRepositories.questionCreator("Which has longest gestation period among mammals?","Giant panda","Pronghorn","Prairie dog","Asiatic elephant","Asiatic elephant","The largest of all land animals have the longest gestation period of all living mammals. Yes, the gestation period of elephants lasts for 18 to 22 months. For an African elephant, the gestation period is 22 months where the Asian elephant\'s is between 18 and 22 months.","asiatic_elephant_14.png",3);

//14 Domestic dog
//Found
            questionRepositories.questionCreator("Which animal can cross-breed with wolf?","Domestic dog","Dhole","Prairie dog","Fox","Domestic dog","Wolves and dogs are interfertile meaning they can breed and produce viable offspring. In other words, wolves can interbreed with any type of dog, and their offspring are capable of producing offspring themselves.","domestic_dog_15.png",3);

//15 Borneo
//Found
            questionRepositories.questionCreator("Where are orangutans found?","Italy","South America","North Africa","Borneo","Borneo","The orangutans are three extant species of great apes native to Indonesia and Malaysia. Orangutans are currently only found in the rainforests of Borneo and Sumatra. Classified in the genus Pongo, orangutans were originally considered to be one species.","borneo_16.png",3);

//16 Three-toed sloth
//Found
            questionRepositories.questionCreator("Which is the slowest moving animal?","Polar bear","Hippopotamus","Elephant seal","Three-toed sloth","Three-toed sloth","The Slowest Mammal in the World. Three-toed sloths are some of the slowest and seemingly laziest creatures in the world. Instead of evolving to eat more, they evolved to do less.","three_toed_sloth_17.png",3);

//17 Africa
//Found
            questionRepositories.questionCreator("Where are chimpanzees found?","Africa","South America","India","Afghanistan","Africa","Together with humans, gorillas, and orangutans they are part of the family Hominidae (the great apes). Native to sub-Saharan Africa, common chimpanzees and bonobos are currently both found in the Congo jungle, while only the common chimpanzee is also found further north in West Africa.","africa_18.png",3);

//18 Komodo dragon
//Found
            questionRepositories.questionCreator("Which is the world largest and rarest lizard?","Chameleon","Komodo dragon","Regal-horned lizard","Garden lizard","Komodo dragon","The Komodo dragon is the largest living lizard in the world. Komodo dragons are limited to a few Indonesian islands of the Lesser Sunda group, including Rintja, Padar and Flores, and of course the island of Komodo, the largest at 22 miles (35 kilometers) long.","komodo_dragon_19.png",3);

//19 South America
//Found
            questionRepositories.questionCreator("Where are giants anteaters found?","North America","South Africa","South America","Japan","South America","Wild giant anteaters live in grasslands, deciduous forests and rain forests of South and Central America. Though most common in South America, they can be found anywhere from the southern tip of Mexico through Central and South America","south_america_20.png",3);

//20 Beaver
//Found
            questionRepositories.questionCreator("Which of the following animal\'s teeth are strong enough to fell a tree?","Squirrel","Beaver","Chipmunk","Shrew","Beaver","Even without brushing their teeth or drinking fluoridated water, beavers have remarkably strong teeth good for gnawing on wood. A new study shows that their tough teeth are all thanks to a key component built into their chemical structure, and its iron.","beaver_21.png",3);

//21 Africa
//Found
            questionRepositories.questionCreator("Where are aardvarks found?","Africa","Australia","Denmark","Ireland","Africa","Aardvarks are found in sub-Saharan Africa, where suitable habitat and food is available. They spend the daylight hours in dark underground burrows to avoid the heat of the day.","africa_22.png",3);

//22 Jerboa
//Found
            questionRepositories.questionCreator("Which rodent is found in remote desert areas, in shifting sand dunes and extreme temperature?","Jerboa","Capybara","Hamster","Murree vole","Jerboa","Jerboas are hopping desert rodents found throughout Arabia, Northern Africa and Asia east to northern China and Manchuria. They tend to live in hot deserts.","jerboa_23.png",3);

//23 pangolin
//Found
            questionRepositories.questionCreator("Which animal female curls up around its baby to protect it from any attacking animal?","Tortoise","Armadillo","pangolin","hedgehog","pangolin","Pangolin also known as scaly anteaters because of their appearance, Pangolin Female curl up its baby to protect it from enemy","pangolin_24.png",3);

//24 North America
//Found
            questionRepositories.questionCreator("Where are skunks found?","North America","South America","Africa","Europe","North America","Striped skunks are native to North America, and can be found in Northern Mexico, throughout the United States, and as far north as Central Canada. Other species of skunks, such as the spotted skunk and the hog-nosed skunk, can be found further south, ranging from Canada to Central and South America.","north_america_25.png",3);

//25 Aardvark
//Found
            questionRepositories.questionCreator("Which of the following animals is the fastest burrower?","Mole","Aardvark","Kangaroo rat","Prairie dog","Aardvark","Aardvarks are not fast runners but they can quickly dig a defensive burrow. The aardvark\'s tail is thick and strong and they will use it as a club. Their sharp claws are formidable weapons, and if caught in the open, the aardvark will roll on its back to engage all four feet in the fight.","aardvark_26.png",3);

//26 Europe
//Found
            questionRepositories.questionCreator("Where are badgers found?","Australia","South Africa","Europe","Iraq","Europe","Badgers are found in much of North America, Ireland, Great Britain and most of the rest of Europe as far north as southern Scandinavia. They live as far east as Japan and China. The Javan ferret-badger lives in Indonesia, and the Bornean ferret-badger lives in Malaysia.","europe_27.png",3);

//27 Capybara
//Found
            questionRepositories.questionCreator("Which is the largest rodent in the world?","Kangaroo","Capybara","Rabbit","Squirrel","Capybara","The capybara is a mammal native to South America. It is the largest living rodent in the world","capybara_28.png",3);

//28 Australia
//Found
            questionRepositories.questionCreator("Where is koala bears found?","North America","Australia","Africa","South Africa","Australia","Koalas do not live in rainforests or desert areas. They live in the tall eucalypt forests and low eucalypt woodlands of mainland eastern Australia, and on some islands off the southern and eastern coasts. Queensland, NSW, Victoria and South Australia are the only states where Koalas are found naturally in the wild.","australia_29.png",3);

//29 Passenger pigeon
//Found
            questionRepositories.questionCreator("Which bird was once found in abundance but is today extinct species?","Flightless cormorant","Grey heron","Passenger pigeon","Kakapo","Passenger pigeon","Today only about 40 kakapos survive in the wild on two small islands off the coast of New Zealand\'s South Island. It is because of the hunter.","passenger_pigeon_30.png",3);

//30 South America
//Found
            questionRepositories.questionCreator("Where is rhea found?","North America","China","Ireland","South America","South America","This ratite is the largest bird in the Americas. It is a fast runner; and when it runs, its neck is almost horizontal to the ground. Rheas congregate in flocks of 20 to 30 birds. The lesser or Darwin\'s rhea, Rhea pennata, is mostly found in the southern part of South America","south_america_31.png",3);

//31 Kiwi
//Found
            questionRepositories.questionCreator("Which bird locates its prey by smell?","Woodpecker","Kiwi","Crow","Stonechat","Kiwi","A kiwi\'s olfactory bulb is the second largest among all birds relative to the size of its forebrain, giving it an exceptional sense of smell, just second to the condor. This helps kiwi locate food beneath the soil and in leaf lite","kiwi_32.png",3);

//32 Drongo
//Found
            questionRepositories.questionCreator("Which bird mimics the calls and even songs of other birds?","Koel","Eagle","Woodpecker","Drongo","Drongo","The Drongos are able to mimic the sounds made by many different species that inhabit its desert environment. Drongo, seen here in flight, impersonates the calls of other birds in order to steal food","drongo_33.png",3);

//33 Gentoo penguin
//Found
            questionRepositories.questionCreator("Which is the fastest swimming bird?","Gentoo penguin","Adelie penguin","Shelduck","Puffin","Gentoo penguin","The Gentoo penguin (pygoscelis Papua) is the world\'s fastest swimming bird. It can swim between 36-40 km. per hour. They are found in the Antarctic Islands.","gentoo_penguin_34.png",3);

//34 South America
//Found
            questionRepositories.questionCreator("Where is toucan found?","China","Australia","South America","England","South America","Toucans are native to the Neotropics, from Southern Mexico, through Central America, into South America south to northern Argentina. They mostly live in the lowland tropics, but the montane species from the genus Andigena reach temperate climates at high altitudes in the Andes and can be found up to the tree line.","south_america_35.png",3);

//35 Penguin
//Found
            questionRepositories.questionCreator("Which birds has flippers instead of wings?","Owl","Penguin","Goose","Albatross","Penguin","While penguins are the only birds that have true flippers, other pelagic birds that spend a good deal of time swimming also have some flipper-like characteristics to their wings. Puffins, murres and auks all have wings that more closely resemble flippers, but to a lesser degree than penguin wings.","penguin_36.png",3);

//36 Flamingo
//Found
            questionRepositories.questionCreator("Which bird uses its beak as a filter to gather food from water?","Grey heron","Ibis","Flamingo","Sarus crane","Flamingo","Flamingos are filter feeders. ... Because the flamingo must use its beak in an upside-down manner, the beak has evolved to reflect this. The flamingo\'s top beak functions like the bottom beak of most birds, and vice versa. Flamingos are among the very few animals that are able to move their top jaw while eating","flamingo_37.png",3);

//37 Nightjar
//Found
            questionRepositories.questionCreator("Which bird keeps its mouth open while flying so that it can catch flying insects?","Vulture","Nightjar","Owl","Crow","Nightjar","Nightjars are very agile in flight, able to hunt and catch aerial insects such as moths in those huge mouths. They also eat beetles, spiders and various other insects. The shape of a bird\'s beak almost always determines the type of food they will eat.","nightjar_38.png",3);

//38 Skua
//Found
            questionRepositories.questionCreator("Some birds do not catch their own prey. They steal it from another bird. Which bird is it?","Skua","Sparrow","River tem","Bulbul","Skua","Skuas steal much of their food from terns, puffins, and other birds that are carrying fish or other prizes back to their nests and young. The swashbuckling birds sometimes team up to overwhelm their victims, and they are relentless in chasing down their adversaries.","skua_39.png",3);

//39 Arctic tern
//Found
            questionRepositories.questionCreator("Which holds the record for longest migration?","Arctic tern","Shearwater","River tern","Bar headed geese","Arctic tern","Canada geese fly in a distinctive V-shaped flight formation, with an altitude of 1 km (3,000 feet) for migration flight. The maximum flight ceiling of Canada geese is unknown, but they have been reported at 9 km (29,000 feet)","arctic_tern_40.png",3);

//40 Andean condor
//Found
            questionRepositories.questionCreator("Which bird can travel very long distances without flapping its wings?","Andean condor","Peregrine falcon","Coot","Eagle","Andean condor","Condors can glide over large areas while using little energy. These huge birds are too heavy to fly without help. They use warm air currents (thermals) to help them gain altitude and soar through the sky. By gliding from thermal to thermal, a condor may need to flap its wings only once every hour","andean_condor_41.png",3);

//41 Kori bustard
//Found
            questionRepositories.questionCreator("Which is heaviest flying bird?","Ostrich","Kori bustard","Vulture","Condor","Kori bustard","The largest (heaviest) flying bird today is the Kori Bustard (Ardeotis kori) of Africa, males weigh about 18kg, females about half that. The largest bird ever to fly were the Teratorns (a type of Condor), the largest of which, Argentavis magnificens, had a wingspan of 3 metres, and weighed 120kg","kori_bustard_42.png",3);

//42 Woodpecker
//Found
            questionRepositories.questionCreator("Which birds helps in conditioning timber pests?","Indian grey shrike","Woodpecker","Tailor-bird","Hoopoe","Woodpecker","An interesting and familiar group of birds. Their ability to peck into trees in search of food or excavate nest cavities is well known. They prefer snags or partially dead trees for nesting sites, and readily peck holes in trees and wood structures in search of insects beneath the surface","woodpecker_43.png",3);

//43 Ostrich
//Found
            questionRepositories.questionCreator("Which bird has the biggest egg?","Tawny owl","Moorhen","Ostrich","Duck","Ostrich","On record weighed 2.589 kg (5 lb. 11.36 oz) and was laid by an ostrich (Struthio camelus) at a farm owned by Kerstin and Gunnar Sahlin (Sweden) in Borlange, Sweden, on 17 May 2008","ostrich_44.png",3);

//44 Keratin
//Found
            questionRepositories.questionCreator("The horns of a goat are made out of what substance?","Insulin","Amylase","Keratin","Progesterone","Keratin","The main composition of horns is keratin, the same material that makes up hair and fingernails. The core of a horn is, however, made of bone.","keratin_45.png",3);

//45 Monkey
//Found
            questionRepositories.questionCreator("The cotton top of tamarin is a small species of what type of animal?","Bear","Goat","Monkey","Sheep","Monkey","The Cotton top Tamarin (Saguinus oedipus), also known as the Pinche Tamarin, is a small New World monkey weighing less than 1lb (0.5 kg). It is an endangered species found in tropical forest edges and secondary forests where it is arboreal and diurnal.","monkey_46.png",3);

//46 Australia
//Found
            questionRepositories.questionCreator("The bandicoot is native to which country?","Africa","Australia","North America","Cambodia","Australia","There are also a few rare species such as the rabbit-eared bandicoots. Bandicoots are one of the few native mammals to have remained abundant close to the major cities of Australia. In suburban Sydney it is the long-nosed species that can be seen.","australia_47.png",3);

//47 Eating algae
//Found
            questionRepositories.questionCreator("Some flamingos are color pink because of what?","Eating shrimps","Eating algae","Eating plankton","Eating lotus","Eating algae","Actually, flamingos are not pink. They are born with grey feathers, which gradually turn pink in the wild because of a natural pink dye called canthaxanthin that they obtain from their diet of brine shrimp and blue-green algae.","eating_algae_48.png",3);

//48 Dog
//Found
            questionRepositories.questionCreator("A Finnish Spitz is what type of creature?","Dog","Snake","Cat","Bird","Dog","Finnish Spitz is a breed of dog originating in Finland.","dog_49.png",3);

//49 Fox
//Found
            questionRepositories.questionCreator("What is the common name for the animal of the genus vulpes.","Fox","Wolf","Owl","Dog","Fox","Vulpes is a genus of the Canidae the members of this genus are colloquially referred to as a true foxes, meaning they form a proper clade.","fox_50.png",3);

//50 three
//Found
            questionRepositories.questionCreator("How many hearts does an octopus have?","eight","one","three","six","three","Octopuses have a closed circulatory system, where the blood remains inside blood vessels. Octopuses have three hearts; a systemic heart that circulates blood round the body and two branchial hearts that pump it through each of the two gills","three_51.png",3);

//51 Black
//Found
            questionRepositories.questionCreator("What is the color of a polar bears skin?","Black","Pink","White","Brown","Black","The bear\'s stark white coat provides camouflage in surrounding snow and ice. But under their fur, polar bears have black skin the better to soak in the sun\'s warming rays. These powerful predators typically prey on seals.","black_52.png",3);

//52 Pig
//Found
            questionRepositories.questionCreator("Welsh and Tamworth are breeds of which animal?","Pig","Cow","Sheep","Dog","Pig","Tamworth is a breed of a domestic pig originating in its namesake Tamworth; it is among the oldest of pig breeds.","pig_53.png",3);

//53 twenty one mph
//Found
            questionRepositories.questionCreator("What is the top speed of a bottle nosed dolphin?","twenty one mph","twenty six mph","thirty one mph","fourty mph","twenty one mph","Bottlenose Dolphins typically swim at a speed of 5-11 kilometers per hour (3-6 mph); for short times, they can reach peak speeds of 35 kilometers per hour (21 mph).","twenty_one_mph_54.png",3);

//54 Cat
//Found
            questionRepositories.questionCreator("What kind of animal is Egyptian Mau?","Dog","Cat","Snake","Camel","Cat","This, along with the well-known chattiness of the Egyptian Mau, makes them popular pets today. As the Egyptian Mau is the only naturally spotted breed of domestic cat, the Egyptian Mau is often bred with other domestic cats in order to produce slightly spotted kittens","cat_55.png",3);

//55 Mammal
//Found
            questionRepositories.questionCreator("What type of animal is cuscus?","Bird","Mammal","Reptile","Amphibian","Mammal","The cuscus is a large marsupial native to the Northern forest of Australia and the large, tropical island of Papua New Guinea. The cuscus is a subspecies of possum with the cuscus being the largest of the world\'s possum species. The cuscus is an arboreal mammal and spends its life almost exclusively in the trees.","mammal_56.png",3);

//56 two hundred fifty
//Found
            questionRepositories.questionCreator("How many species of bumble bees are there around the world?","twenty-five","one hundred fifty","two hundred fifty","five hundred","two hundred fifty","There are about 25 British species according to Prys-Jones (19 species of Bombus and 6 species of cuckoo bumblebees). And about 65 species in Europe, and 250 species of bumblebee have been discovered so far worldwide","two_hundred_fifty_57.png",3);

//57 Anaconda
//Found
            questionRepositories.questionCreator("Which snake is a member of family boa and sometimes called \'water boa\'?","Cobra","Python","Rattle snake","Anaconda","Anaconda","There are about 25 British species according to Prys-Jones (19 species of Bombus and 6 species of cuckoo bumblebees). And about 65 species in Europe, and 250 species of bumblebee have been discovered so far worldwide","anaconda_58.png",3);

//58 Giraffe
//Found
            questionRepositories.questionCreator("Which animal do not make any sound?","Koala","Panda","Giraffe","Bear","Giraffe","Science Says This Is the Sound Giraffes Make. It\'s long been assumed that unlike other animals, giraffes are largely silent beasts. They don\'t oink, moo or roar. But new research suggests perhaps giraffes do have a distinct sound: They hum.","giraffe_59.png",3);

//59 Jelly fish
//Found
            questionRepositories.questionCreator("Which of the following has no skeleton at all?","Star fish","Sponge","Jelly fish","Silver fish","Jelly fish","A jellyfish has no ears or eyes or nose and no brain or heart! They do not even have a head. Their body is almost totally made of water and is soft having no bones at all. Jellyfish are invertebrate animals because they do not have a spine or backbone.","jelly_fish_60.png",3);

//60 Humpback whale
//Found
            questionRepositories.questionCreator("Which animal can create the loudest sound among any living creature?","Whale shark","Gibbon","Humpback whale","Howler monkey","Humpback whale","Produce one hundred eighty eight decibels of sound. That\'s louder than a one hundred fifty decibel rock concert, which can damage hearing.","humpback_whale_61.png",3);

//61 Sucker fish
//Found
            questionRepositories.questionCreator("Which of the following is not a true fish?","Silver fish","Saw fish","Hammer fish","Sucker fish","Sucker fish","Sawfishes, also known as carpenter sharks, are a family of rays characterized by a long, narrow, flattened rostrum, or nose extension, lined with sharp transverse teeth, arranged in a way that resembles a saw","sucker_fish_62.png",3);

//62 Honeybee
//Found
            questionRepositories.questionCreator("In which of the following kinds of organism is the phenomenon found wherein the female kills the male after copulation?","Dragonfly","Honeybee","Spider","Pit viper","Honeybee","The next male honey bee to mate with the queen will remove the previous endophallus and eventually lose his own after ejaculation. Male honey bees are only able to mate seven to 10 times during a mating flight, and after mating, a drone dies quickly, as his abdomen rips open when his endophallus is removed","honeybee_63.png",3);

//63 Goat
//Found
            questionRepositories.questionCreator("Pashmina shawl is made from hair of?","Sheep","Goat","Rabbit","Yak","Goat","Pashmina refers to a type of cashmere wool and textiles made from it. The name comes from Pashmineh, made from Persian pashm (= \"wool\"). This wool comes from changthangi or pashmina goat—a special breed of goat indigenous to high altitudes of the Himalayas","goat_64.png",3);

//64 Frog
//Found
            questionRepositories.questionCreator("In which of the following animals is respiration done by skin?","Flying fish","Sea horse","Frog","Chameleon","Frog","Though they have functional lungs, much of a frog\'s respiration occurs through the skin. A frog\'s moist skin is thin and marbled with blood vessels and capillaries close to the surface. The moisture on the skin dissolves oxygen from the air and water surrounding the frog and transmits it into the blood","frog_65.png",3);

//65 Dolphin
//Found
            questionRepositories.questionCreator("Which of the following is the National aquatic animal of India?","Sea turtle","Shark","Dolphin","Dugong","Dolphin","River Dolphin is the National Aquatic Animal of India. This mammal is also said to represent the purity of the holy Ganga as it can only survive in pure and fresh water. Platanista gangetica has a long pointed snout and also have visible teeth in both the upper and lower jaws.","dolphin_66.png",3);

//66 Catfish
//Found
            questionRepositories.questionCreator("The fish that can taste with its whole body is the?","Clown fish","Jelly fish","Catfish","Flying fish","Catfish","That\'s because this creature has taste buds not only in its mouth, but all over its body. Catfish (order Siluriformes), those beady-eyed fish named for their feline-like whiskers; typically have more than 100,000 taste buds. Some large catfish can have as many as 175,000.","catfish_67.png",3);

//67 Tibet
//Found
            questionRepositories.questionCreator("The country in which Yak is found?","Tibet","Africa","South America","Indian","Tibet","The domestic yak (Bos grunniens) is a long-haired domesticated bovid found throughout the Himalayan region of the Indian subcontinent, the Tibetan Plateau and as far north as Mongolia and Russia. It is descended from the wild yak (Bos mutus)","tibet_68.png",3);

//68 six
//Found
            questionRepositories.questionCreator("How many legs does a butterfly have?","two","four","six","eight","six","The scales, which are arranged in colorful designs unique to each species, are what give the butterfly its beauty. Like all other insects, butterflies have six legs and three main body parts: head, thorax (chest or mid-section) and abdomen (tail end). They also have two antennae and an exoskeleton","six_69.png",3);

//69 Haryana
//Found
            questionRepositories.questionCreator("The Bhindawas bird sanctuary is located in which country?","Madhya Pradesh","Bihar","Odisha","Haryana","Haryana","The Bhindawas Bird Sanctuary (BBS) is located in Jhajjar district, Haryana.","haryana_70.png",3);

//70 Mammal
//Found
            questionRepositories.questionCreator("The dolphin is?","Fish","Reptile","Mammal","Turtle","Mammal","Like every mammal, dolphins are warm blooded. Unlike fish, who breathe through gills, dolphins breathe air using lungs. Dolphins must make frequent trips to the surface of the water to catch a breath.","mammal_71.png",3);

//71 Owl
//Found
            questionRepositories.questionCreator("Which bird can turn head around to look backward?","Toucan","Parrot","Owl","Eagle","Owl","A tawny owl turning its head far around its neck, Owls don\'t need eyes in the back of their heads to see what\'s behind them - they can just swivel their heads all the way around","owl_72.png",3);

//72 Wild dog
//Found
            questionRepositories.questionCreator("What kind of animal is a dhole?","Wild cat","Wild ass","Wild dog","Wild buffalo","Wild dog","The dhole is a canid native to Central, South and Southeast Asia. Other English names for the species include Asiatic wild dog, Indian wild dog, whistling dog, red dog, and mountain wolf.","wild_dog_73.png",3);

//73 Hummingbird
//Found
            questionRepositories.questionCreator("Which is the only bird that can fly backwards?","Sunbird","Kingfisher","Honey eater","Hummingbird","Hummingbird","Hummingbirds are incredible flyers, with the ruby-throated hummingbird beating its wings 80 times every second, an ability that inspired this blog\'s name. These tiny birds can fly forwards, hover, and are the only known birds to fly backwards as well.","hummingbird_74.png",3);

//74 Sailfish
//Found
            questionRepositories.questionCreator("Which is the fastest swimming fish?","Dolphin","Sailfish","Catfish","Eel","Sailfish","Not all experts agree, but at top speeds of nearly 70 mph, the sailfish is widely considered the fastest fish in the ocean. Clocked at speeds in excess of 68 mph, some experts consider the sailfish the fastest fish in the world ocean.","sailfish_75.png",3);

//75 Glass snake
//Found
            questionRepositories.questionCreator("Which one of the following is not a true snake?","Blind snake","Glass snake","Sea snake","Tree snake","Glass snake","The glass lizards or glass snakes are a genus, Ophisaurus, of reptiles that resemble snakes, but are actually lizards. Although most species have no legs, their head shapes, movable eyelids, and external ear openings identify them as lizards.","glass_snake_76.png",3);

//76 New Zealand
//Found
            questionRepositories.questionCreator("Kiwi is the native bird of which of the following countries?","South America","New Zealand","Australia","Zimbabwe","New Zealand","There are two species of Kiwis in New Zealand. Brown Kiwis are found in forested areas in the North Island, Fiord land, South Westland and Stewart Island. Spotted Kiwis are found on offshore islands and forests in the North of the South Island","new_zealand_77.png",3);

//77 Cheetah
//Found
            questionRepositories.questionCreator("What is the fastest land animal in the world?","Kangaroo","Cheetah","Wolf","Dear","Cheetah","These graceful animals are identified by their unique black spots on gold or yellow coats and are known for their amazing speed. In fact, according to the Smithsonian National Zoological Park, the cheetah is the world\'s fastest land mammal. A sprinting cheetah can reach 45 mph (72 km/h) within 2.5 seconds","cheetah_78.png",3);

//78 Female deer
//Found
            questionRepositories.questionCreator("A \'doe\' is what kind of animal?","Female yak","Female deer","Female bear","Female camel","Female deer","Deer are a group of even-toed ungulate mammals. They form the family Cervidae. A male deer is called stag or buck, a female deer is called doe, and a young deer is called fawn. There are about 60 species of deer.","female_deer_79.png",3);

//79 Africa
//Found
            questionRepositories.questionCreator("What is the only continent on earth where Giraffes live in the wild?","North pole","Africa","Australia","South America","Africa","Giraffes can be found in central, eastern and southern Africa. Giraffes live in the savannas of Africa, where they roam freely among the tall trees, arid land, dense forests and open plains.","africa_80.png",3);

//80 eight
//Found
            questionRepositories.questionCreator("How many legs does a spider have?","seven","nine","ten","eight","eight","Spiders (order Araneae) are air-breathing arthropods that have eight legs and chelicerae with fangs that inject venom. They are the largest order of arachnids and rank seventh in total species diversity among all other orders of organisms.","eight_81.png",3);

//81 Lion
//Found
            questionRepositories.questionCreator("What animal is known as the king of the jungle?","Gorilla","Tiger","Elephant","Lion","Lion","Lion is known to be the king of the beast (\"king of the jungle\" would be a mismoner) across most cultures of the world. This is mostly because of lion\'s appearance and partly because of the social structure of a pride and the lion\'s role in the place.","lion_82.png",3);

//82 Dingo
//Found
            questionRepositories.questionCreator("What is largest predator found on the land of Australia?","Dingo","Coyote","Gray wolf","Australian Cattle dog","Dingo","The Dingo is the largest terrestrial predator in Australia.","dingo_83.png",3);

//83 sixty thousand
//Found
            questionRepositories.questionCreator("The Asian Elephants trunk contains up to how many muscles?","six hundred","six thousand","sixty thousand","ten thousand","sixty thousand","The trunk contains as many as 60,000 muscles, which consist of longitudinal and radiating sets. The longitudinal are mostly superficial and subdivided into anterior, lateral, and posterior","sixty_thousand_84.png",3);

//84 Dog
//Found
            questionRepositories.questionCreator("The Bichon Frise is a breed of what?","Dog","Cow","Sheep","Goat","Dog","The Bichon Frise is a very cheerful breed, with a happy, sometimes clownish disposition, accented by its tail curled up high on its rump. They were very much in vogue in sixteenth-century France; they displaced the Maltese in court as the favorite.","dog_85.png",3);

//85 Bird
//Found
            questionRepositories.questionCreator("What type of animal is an Avocet?","Bird","Reptile","Mammal","Amphibian","Bird","The avocet is a type of wading bird that is found across mudflats in the world\'s warmer climates. There are four different species of avocet which are the pied avocet, the American avocet, the Red-necked avocet and the Andean avocet","bird_86.png",3);

//86 Emperor penguin
//Found
            questionRepositories.questionCreator("Which is the largest type of penguin?","Emperor penguin","King penguin","Crested penguin","Gentoo penguin","Emperor penguin","The emperor penguin (Aptenodytes forsteri) is the tallest and heaviest of all living penguin species and is endemic to Antarctica. The male and female are similar in plumage and size, reaching 122 cm (48 in) in height and weighing from 22 to 45 kg (49 to 99 lb)","emperor_penguin_87.png",3);

//87 Ground pig
//Found
            questionRepositories.questionCreator("Aardvark is South Africa\'s Afrikaans language which means what?","Land animal","Sea mammal","Ground pig","Monkey eating","Ground pig","Aardvark is the first word in your English dictionary; however the name aardvark is not even English! It comes from South Africa\'s Afrikaans language and means \'earth pig\' or \'ground pig\'.","ground_pig_88.png",3);

//88 five minutes
//Found
            questionRepositories.questionCreator("How long can a hippo hold their breath in the water?","ten minutes","five minutes","one minute","fifteen minutes","five minutes","Their nostrils close, and they can hold their breath for 5 minutes or longer when submerged. Hippo can even underwater, using a reflex that allows them to bob up, take a breath, and sink and back down without waking up.","five_minutes_89.png",3);

//89 Sperm whale
//Found
            questionRepositories.questionCreator("What mammal can hold their breath in a longest time?","Sperm whale","Dolphin","Blue whale","Human","Sperm whale","Sperm whale makes some of the longest dives achieved by mammals, with some lasting up to 90 minutes, while dolphins and other whales can stay underwater for 20 minutes. The longest time a human has held their breath for under water is 19 minutes set by a Swiss free diver called Peter Colat.","sperm_whale_90.png",3);

//90 Bee hummingbird
//Found
            questionRepositories.questionCreator("Which is the smallest living bird?","Bee hummingbird","Sparrow","Pigeon","Parrot","Bee hummingbird","Bee Hummingbirds (Mellisuga helenae) are only 5 to 6 cm long and weigh just 1.6 to 1.9 g (a small coin such as a U.S. penny weighs around 2.5 to 3 g). The male Bee Hummingbird is the smallest of all birds and can easily be mistaken for a bee","bee_hummingbird_91.png",3);

//91 Airedale
//Found
            questionRepositories.questionCreator("What is the largest terrier?","Scottish","Bull","Boston","Airedale","Airedale","Airedale it is traditionally called \"King of Terriers\" because it is the largest of the terrier breeds. The Airedale was bred from the old English black and tan terrier (now extinct).","airedale_92.png",3);

//92 King cobra
//Found
            questionRepositories.questionCreator("What is the world\'s longest poisonous snake?","Tiger snake","Boomslang","King cobra","Eastern brown snake","King cobra","Today\'s longest venomous snakes, king cobra (ophiophagus Hannah), can grow to be about 18 feet (5.5m) long.","king_cobra_93.png",3);

//93 New Zealand
//Found
            questionRepositories.questionCreator("Where would find a tuatara?","Africa","New Zealand","Sweden","Australia","New Zealand","This New Zealand native has unique, ancient lineage that goes back to the time of the dinosaurs.","new_zealand_94.png",3);

//94 Colony
//Found
            questionRepositories.questionCreator("What is a group of bat called?","Colony","Comrade","Companion","Group bat","Colony","A group of bats is called a colony of bats. Within one colony of bats, there may be 10 to 1,000 bats or more. Bats tend to stick together in order to protect each other, to mate and to gather food.","colony_95.png",3);

//95 Dolphin
//Found
            questionRepositories.questionCreator("Which animal has the widest hearing range?","Lizard","Dolphin","Hyena","Lion","Dolphin","Bottlenose dolphins hear tones with a frequency up to 160 kHz with the greatest sensitivity ranging from 40 to 100 kHz. The average hearing for humans is about 0.02 to 20 kHz","dolphin_96.png",3);

//96 Male horse
//Found
            questionRepositories.questionCreator("What kind of animal is a stallion?","Male deer","Male horse","Male zebra","Male donkey","Male horse","Male horse is called a stallion. A stallion used for breeding is known as a stud. Formerly, stallion was employed as riding horses.","male_horse_97.png",3);

//97 four
//Found
            questionRepositories.questionCreator("How many different types of hyenas are there?","six","ten","five","four","four","There are 4 known species of hyena, the spotted hyena, the striped hyena, the brown hyena and the aardwolf.","four_98.png",3);

//98 Madagascar
//Found
            questionRepositories.questionCreator("In which country are lemurs found in nature?","Madagascar","U.S.A","Finland","New Zealand","Madagascar","Today the black lemur is an endangered species and is found only in a small area on Madagascar and on two small islands off its northwest coast. On one island they have the benefit of a reserve of natural forest.","madagascar_99.png",3);

//99 Earthworms
//Found
            questionRepositories.questionCreator("What is the main diet of a mole?","Beetle","Dragonfly","Ants","Earthworms","Earthworms","It is a misconception that moles burrow into gardens to eat the roots of plants. They are actually after the earthworms that are found in garden soil. Moles love earthworms so much that they eat nearly their body weight worth of earthworms per day. Moles also consume insect larvae.","earthworms_100.png",3);

            //TODO END OF ANIMALS

            //TODO GEOGRAPHY
            //0 Ljubljana
//Found
            questionRepositories.questionCreator("﻿What is the capital city of Slovenia?","Bucharest","Ljubljana","Kiev","Madrid","Ljubljana","There are 7 towns in Slovenia. According to the Local Self-Government Act of the Republic of Slovenia, a town is a larger urban settlement with more than 3,000 residents and differing from other settlements in its size, economical structure, population, population density and historical development","ljubljana_1.png",4);

//1 Madrid
//Found
            questionRepositories.questionCreator("What is the capital city of Spain?","Madrid","Barcelona","Seville","Helsinki ","Madrid","Spain is a sovereign state mostly located on the Iberian Peninsula in Europe. ","madrid_2.png",4);

//2 Stockholm
//Found
            questionRepositories.questionCreator("What is the capital city of Sweden?","Stockholm","Helsinki","Oslo","Vienna","Stockholm","Sweden is a Scandinavian nation with thousands of coastal islands and inland lakes, along with vast boreal forests and glaciated mountains.","stockholm_3.png",4);

//3 Bucharest
//Found
            questionRepositories.questionCreator("What is the capital city of Romania?","Bucharest","Budapest","Vienna ","Muscat","Bucharest","Romania is a southeastern European country known for the forested region of Transylvania, ringed by the Carpathian Mountains.","bucharest_4.png",4);

//4 Budapest
//Found
            questionRepositories.questionCreator("What is the capital city of Hungary?","Bucharest","Prague ","Budapest","Oslo","Budapest","No Funfacts","budapest_5.png",4);

//5 Berlin
//Found
            questionRepositories.questionCreator("What is the capital city of Germany?","Berlin","Bonn","Munich","Muscat","Berlin","Germany is a Western European country with a landscape of forests, rivers, mountain ranges and North Sea beaches.","berlin_6.png",4);

//6 Riga
//Found
            questionRepositories.questionCreator("What is the capital city of Latvia?","Riga","Tallinn","Vilnius ","Bonn","Riga","Latvia is a country on the Baltic Sea between Lithuania and Estonia. Its landscape is marked by wide beaches as well as dense, sprawling forests.","riga_7.png",4);

//7 Sofia
//Found
            questionRepositories.questionCreator("What is the capital city of Bulgaria?","Tirana ","Berlin","Sofia","Prague","Sofia","Bulgaria is a Balkan nation with diverse terrain encompassing Black Sea coastline, a mountainous interior and rivers, including the Danube.","sofia_8.png",4);

//8 Jordan
//Found
            questionRepositories.questionCreator("Petra is located in which country?","Iran","Jordan","Lebanon","Iraq","Jordan","Jordan, an Arab nation on the east bank of the Jordan River, is defined by ancient monuments, nature reserves and seaside resorts. Petra earns its nickname, the \"Rose City.\"","jordan_9.png",4);

//9 India
//Found
            questionRepositories.questionCreator("Taj Mahal is located in which country?","India","Pakistan","Indonesia","Singapore","India","India is a vast South Asian country with diverse terrain – from Himalayan peaks to Indian Ocean coastline – and history reaching back 5 millennia.","india_10.png",4);

//10 Mexico
//Found
            questionRepositories.questionCreator("Chichen Itza is located in which country?","Mexico","United States","Chile","Argentina","Mexico","Mexico is a country between the U.S. and Central America that\'s known for its Pacific and Gulf of Mexico beaches and its diverse landscape of mountains, deserts and jungles.","mexico_11.png",4);

//11 Italy
//Found
            questionRepositories.questionCreator("The Colosseum is located in which  country?","Italy","France","Norway","Germany","Italy","Italy, a European country with a long Mediterranean coastline, has left a powerful mark on Western culture and cuisine.","italy_12.png",4);

//12 Brazil
//Found
            questionRepositories.questionCreator("Christ The Redeemer Statue on Corcovado Mountain is located in which country?","China","Canada","Norway","Brazil","Brazil","Christ the Redeemer is an Art Deco statue of Jesus Christ in Rio de Janeiro, Brazil, created by French sculptor Paul Landowski and built by Brazilian engineer Heitor da Silva Costa, in collaboration with French engineer Albert Caquot. ","brazil_13.png",4);

//13 Kabul
//Found
            questionRepositories.questionCreator("What is the capital city of Afghanistan?","Tirana","Kabul","Luanda","Yerevan","Kabul","Afghanistan, officially the Islamic Republic of Afghanistan, is a landlocked country located within South Asia and Central Asia.","kabul_14.png",4);

//14 Algiers
//Found
            questionRepositories.questionCreator("What is the capital city of Algeria?","Baku","Vienna","Algiers","Canberra","Algiers","Algeria is a North African country with a Mediterranean coastline and a Saharan desert interior.","algiers_15.png",4);

//15 Bangladesh
//Found
            questionRepositories.questionCreator("What country has a capital of Dhaka?","Bangladesh","Barbados","Belarus","Belgium","Bangladesh","Bangladesh, to the east of India on the Bay of Bengal, is a South Asian country marked by lush greenery and many waterways.","bangladesh_16.png",4);

//16 Cambodia
//Found
            questionRepositories.questionCreator("What country has a capital of Phnom Penh?","Canada","Cape Verde","Cambodia","Cameroon","Cambodia","Cambodia is a Southeast Asian nation whose landscape spans low-lying plains, the Mekong Delta, mountains and Gulf of Thailand coastline.","cambodia_17.png",4);

//17 Cape Verde
//Found
            questionRepositories.questionCreator("What country has a capital of Praia?","Central African Republic","Chad","Canada","Cape Verde","Cape Verde","Cape Verde, or Cabo Verde, is a nation on a volcanic archipelago off the northwest coast of Africa.","cape_verde_18.png",4);

//18 Chile
//Found
            questionRepositories.questionCreator("What country has a capital of Santiago?","Comoros","Colombia","China","Chile","Chile","Chile is a long, narrow country stretching along South America\'s western edge, with more than ,000km of Pacific Ocean coastline.","chile_19.png",4);

//19 Colombia
//Found
            questionRepositories.questionCreator("What country has a capital of Bogota?","Colombia","Comoros","Costa Rica","Croatia","Colombia","Colombia is a country at the northern tip of South America. It\'s landscape is marked by rainforests, Andes mountains and numerous coffee plantations.","colombia_20.png",4);

//20 Belgium
//Found
            questionRepositories.questionCreator("What country has a capital of Brussels?","Belarus","Burundi","Brazil","Belgium","Belgium","Belgium, a country in Western Europe, is known for medieval towns, Renaissance architecture and as headquarters of the European Union and NATO.","belgium_21.png",4);

//21 Egypt
//Found
            questionRepositories.questionCreator("What country has a capital of Cairo?","Eritrea","Ethiopia","Egypt","Estonia","Egypt","Egypt, a country linking northeast Africa with the Middle East, dates to the time of the pharaohs. The capital, Cairo, is home to Ottoman landmarks like Muhammad Ali Mosque and the Egyptian Museum, a trove of antiquities.","egypt_22.png",4);

//22 France
//Found
            questionRepositories.questionCreator("What country has a capital of Paris?","Georgia","France","Fiji","Finland","France","France, in Western Europe, encompasses medieval cities, alpine villages and Mediterranean beaches. Paris, its capital, is famed for its fashion houses, classical art museums including the Louvre and monuments like the Eiffel Tower.","france_23.png",4);

//23 Korea, South
//Found
            questionRepositories.questionCreator("What country has a capital of Seoul?","Kosovo","Kenya","Korea, North","Korea, South","Korea, South","South Korea, an East Asian nation on the southern half of the Korean Peninsula, shares one of the world’s most heavily militarized borders with North Korea.","korea_south_24.png",4);

//24 Korea, North
//Found
            questionRepositories.questionCreator("What country has a capital of Pyongyang?","Kosovo","Kenya","Korea, North","Korea, South","Korea, North","North Korea, officially the Democratic People\'s Republic of Korea, is a country in East Asia constituting the northern part of the Korean Peninsula. Pyongyang is the capital and largest city.","korea_north_25.png",4);

//25 Maldives
//Found
            questionRepositories.questionCreator("What country has a capital of Malé?","Mali","Malaysia","Malawi","Maldives","Maldives","The Maldives is a tropical nation in the Indian Ocean composed of 2 ring-shaped atolls, which are made up of more than 1,000 coral islands.","maldives_26.png",4);

//26 New Zealand
//Found
            questionRepositories.questionCreator("What country has a capital of Wellington?","New Zealand","Netherlands","Niger","Nicaragua","New Zealand","New Zealand is a country in the southwestern Pacific Ocean consisting of 2 main islands, both marked by volcanoes and glaciation.","new_zealand_27.png",4);

//27 Russia
//Found
            questionRepositories.questionCreator("What country has a capital of Moscow?","Republic of the Congo","Rwanda","Russia","Romania","Russia","Russia, the world’s largest nation, borders European and Asian countries as well as the Pacific and Arctic oceans.","russia_28.png",4);

//28 Turkey
//Found
            questionRepositories.questionCreator("What country has a capital of Ankara?","Tuvalu","Tunisia","Turkmenistan","Turkey","Turkey","Turkey is a nation straddling Eastern Europe and western Asia with cultural connections to ancient Greek, Persian, Roman, Byzantine and Ottoman empires.","turkey_29.png",4);

//29 South Africa
//Found
            questionRepositories.questionCreator("What country has a capital of Pretoria?","South Sudan","Somalia","South Africa","Solomon Islands","South Africa","South Africa is a country on the southernmost tip of the African continent, marked by several distinct ecosystems.","south_africa_30.png",4);

//30 Oceania
//Found
            questionRepositories.questionCreator("What is the continent of Australia?","America","Europe","Oceania","Asia","Oceania","Oceania is a geographic region comprising Melanesia, Micronesia, Polynesia and Australasia. Spanning the eastern and western hemispheres, Oceania covers an area of 8,525,989 square kilometres and has a population of 40 million.","oceania_31.png",4);

//31 South America
//Found
            questionRepositories.questionCreator("What is the continent of Argentina?","South America","Europe","Oceania","Asia","South America","South America is a continent in the Western Hemisphere, mostly in the Southern Hemisphere, with a relatively small portion in the Northern Hemisphere.","south_america_32.png",4);

//32 North America
//Found
            questionRepositories.questionCreator("What is the continent of Costa Rica?","North America","Europe","Oceania","Asia","North America","North America is a continent entirely within the Northern Hemisphere and almost all within the Western Hemisphere; it is also considered by some to be a northern subcontinent of the Americas.","north_america_33.png",4);

//33 Africa
//Found
            questionRepositories.questionCreator("What is the continent of Comoros?","America","Africa","Oceania","Asia","Africa","Africa is the world\'s second largest and second most-populous continent. At about 30.3 million km² including adjacent islands, it covers % of Earth\'s total surface area and 20% of its land area.","africa_34.png",4);

//34 Africa
//Found
            questionRepositories.questionCreator("What is the continent of Ethiopia?","America","Africa","Oceania","Asia","Africa","Africa is the world\'s second largest and second most-populous continent. At about 30.3 million km² including adjacent islands, it covers % of Earth\'s total surface area and 20% of its land area.","africa_35.png",4);

//35 Asia
//Found
            questionRepositories.questionCreator("What is the continent of Israel?","America","Africa","Oceania","Asia","Asia","Asia is Earth\'s largest and most populous continent, located primarily in the Eastern and Northern Hemispheres.","asia_36.png",4);

//36 Africa
//Found
            questionRepositories.questionCreator("What is the continent of Libya?","America","Africa","Oceania","Asia","Africa","Africa is the world\'s second largest and second most-populous continent. At about 30.3 million km² including adjacent islands, it covers % of Earth\'s total surface area and 20% of its land area.","africa_37.png",4);

//37 Africa
//Found
            questionRepositories.questionCreator("What is the continent of Botswana?","America","Africa","Oceania","Asia","Africa","Republic of Botswana is a landlocked country located in Southern Africa. Formerly the British protectorate of Bechuanaland, Botswana adopted its new name after becoming independent within the Commonwealth on 30 September 19. ","africa_38.png",4);

//38 Europe
//Found
            questionRepositories.questionCreator("What is the continent of Norway?","America","Africa","Europe","Asia","Europe","Europe is a continent located entirely in the Northern Hemisphere and mostly in the Eastern Hemisphere. It is bordered by the Arctic Ocean to the north, the Atlantic Ocean to the west and the Mediterranean Sea to the south.","europe_39.png",4);

//39 Asia
//Found
            questionRepositories.questionCreator("What is the continent of Pakistan?","America","Africa","Oceania","Asia","Asia","Pakistan, officially the Islamic Republic of Pakistan, is a country in South Asia. It is the fifth-most populous country with a population exceeding 212,742,31 people.","asia_40.png",4);

//40 Europe
//Found
            questionRepositories.questionCreator("What is the continent of Romania?","America","Africa","Oceania","Europe","Europe","Europe is a continent located entirely in the Northern Hemisphere and mostly in the Eastern Hemisphere. It is bordered by the Arctic Ocean to the north, the Atlantic Ocean to the west and the Mediterranean Sea to the south.","europe_41.png",4);

//41 Asia
//Found
            questionRepositories.questionCreator("What is the continent of Qatar?","America","Africa","Oceania","Asia","Asia","Qatar is a peninsular Arab country whose terrain comprises arid desert and a long Persian (Arab) Gulf shoreline of beaches and dunes.","asia_42.png",4);

//42 Europe
//Found
            questionRepositories.questionCreator("What is the continent of Portugal?","America","Africa","Oceania","Europe","Europe","Portugal is a southern European country on the Iberian Peninsula, bordering Spain.","europe_43.png",4);

//43 Oceania
//Found
            questionRepositories.questionCreator("What is the continent of Palau?","America","Africa","Oceania","Asia","Oceania","Palau is an archipelago of over 500 islands, part of the Micronesia region in the western Pacific Ocean. Koror Island is home to the former capital, also named Koror, and is the islands’ commercial center. ","oceania_44.png",4);

//44 Asia
//Found
            questionRepositories.questionCreator("What is the continent of Myanmar?","America","Africa","Oceania","Asia","Asia","Myanmar (formerly Burma) is a Southeast Asian nation of more than 100 ethnic groups, bordering India, Bangladesh, China, Laos and Thailand.","asia_45.png",4);

//45 Europe
//Found
            questionRepositories.questionCreator("What is the continent of Netherlands?","America","Africa","Oceania","Europe","Europe","The Netherlands, a country in northwestern Europe, is known for a flat landscape of canals, tulip fields, windmills and cycling routes.","europe_46.png",4);

//46 North America
//Found
            questionRepositories.questionCreator("What is the continent of Jamaica?","North America","Africa","Oceania","Asia","North America","Jamaica, a Caribbean island nation, has a lush topography of mountains, rainforests and reef-lined beaches.","north_america_47.png",4);

//47 Europe
//Found
            questionRepositories.questionCreator("What is the continent of Ireland?","America","Africa","Oceania","Europe","Europe","Ireland is an island in the North Atlantic. It is separated from Great Britain to its east by the North Channel, the Irish Sea, and St George\'s Channel. Ireland is the third-largest island in Europe.","europe_48.png",4);

//48 North America
//Found
            questionRepositories.questionCreator("What is the continent of Honduras?","North America","Africa","Oceania","Asia","North America","Honduras is a Central American country with Caribbean Sea coastlines to the north and the Pacific Ocean to the south.","north_america_49.png",4);

//49 Turkey
//Found
            questionRepositories.questionCreator("Which country is in 2 continents?","Japan","Egypt","Turkey","Russia","Turkey","The larger portion of Turkey is located in Western Asia while the remaining portion is located in South Eastern Europe. The city of Istanbul lies on both sides of one of the Turkish Strait (Bosporus) hence making it a transcontinental city. Canakkale City in Turkey also situated in two continents, Europe and Asia.","turkey_50.png",4);

//50 Asia
//Found
            questionRepositories.questionCreator("The largest continent is?","North America","Africa","Australia","Asia","Asia","Asia spans 17,139,445 square miles – 29.1 percent of Earth\'s total land mass — and has a population of 4.1 billion people.","asia_51.png",4);

//51 Australia
//Found
            questionRepositories.questionCreator("The smallest continent is?","North America","Australia","Oceania","Asia","Australia","The continent of Australia, sometimes known in technical contexts by the names Sahul, Australinea or Meganesia to distinguish it from the country of Australia, consists of the land masses which sit on Australia\'s continental shelf. Situated in the geographical region of Oceania, it is the smallest of the seven traditional continents in the English conception.","australia_52.png",4);

//52 Angkor Wat
//Found
            questionRepositories.questionCreator("The largest religious monument in the world","Angkor Watt","St. Peter’s Basilica","Saint Anne in the Vatican","Angkor Wat","Angkor Wat","Angkor wat is originally constructed as a Hindu temple dedicated to the god Vishnu for the Khmer Empire, gradually transforming into a Buddhist temple towards the end of the 12th century.","angkor_wat_53.png",4);

//53 St.Peter Basilica
//Found
            questionRepositories.questionCreator("It is the largest church in the word","Saints Martin and Sebastian of the Swiss","St.Peter Basilica","Saint Anne in the Vatican","Sant'Egidio in Borgo","St.Peter Basilica","St. Peter\'s is the most renowned work of Renaissance architecture and the largest church in the world.  It has been described as \"holding a unique position in the Christian world and as \"the greatest of all churches of Christendom\".","st_peter_basilica_54.png",4);

//54 Congo River
//Found
            questionRepositories.questionCreator("Which of the following rivers is the longest? ","Congo River","Lena River","Niger River","Irtysh River","Congo River","At two-thousand nine-hundred twenty miles, the Congo River is the second longest river in Africa.","congo_river_55.png",4);

//55 Australia
//Found
            questionRepositories.questionCreator("In the following which country is the death penalty for crime forbidden?","India","The United States","Australia","Belarus","Australia","No Funfacts","australia_56.png",4);

//56 Russia
//Found
            questionRepositories.questionCreator("In what country are the world’s ten coldest cities located?","Russia","The United States","Chile","Canada","Russia","The enormous size of Russia and the remoteness of many areas from the sea result in the dominance of the humid continental climate, which is prevalent in all parts of the country except for the tundra and the extreme southwest.","russia_57.png",4);

//57 Sultanate
//Found
            questionRepositories.questionCreator("What kind of government is that of Oman?","Plebiscite","Sultanate","Democracy","Caliphate","Sultanate","Oman is a unitary state and an absolute monarchy, in which all legislative, executive and judiciary power ultimately rests in the hands of the hereditary Sultan. ","sultanate_58.png",4);

//58 Scotland
//Found
            questionRepositories.questionCreator("In which country you will find the Loch Ness monster?","Scotland","England","Canada","Belgium","Scotland","Scotland, the U.K.’s northernmost country, is a land of mountain wildernesses such as the Cairngorms and Northwest Highlands, interspersed with glacial glens (valleys) and lochs (lakes).","scotland_59.png",4);

//59 India
//Found
            questionRepositories.questionCreator("What country does not use the dollar?","New Zealand","Australia","Canada","India","India","India is a country in South Asia. It is the seventh-largest country by area, the second-most populous country (with over 1.2 billion people), and the most populous democracy in the world","india_60.png",4);

//60 Canada
//Found
            questionRepositories.questionCreator("What is the world’s second-largest country?","China ","Canada","India","Russia","Canada","Canada is a country located in the northern part of North America. Its ten provinces and three territories extend from the Atlantic to the Pacific and northward into the Arctic Ocean, covering 9.98 million square kilometres (3.85 million square miles), making it the world\'s second-largest country by total area.","canada_61.png",4);

//61 Maldives
//Found
            questionRepositories.questionCreator("Which of these is an Indian Ocean republic?","Maldives","Malta","Saint Helena","Easter Island","Maldives","Maldives is a country of South Asia, situated in the Indian Ocean, south-southwest of India.","maldives_62.png",4);

//62 England
//Found
            questionRepositories.questionCreator("In what country does Arsenal play?","Scotland","India","England","Ireland","England","Arsenal was the first club from the South of England to join The Football League, in 1893, and they reached the First Division in 1904.","england_63.png",4);

//63 Switzerland
//Found
            questionRepositories.questionCreator("What nation is divided into cantons?","Italy","France","Switzerland","Germany","Switzerland","Cantons of Switzerland are the member states of the Swiss Confederation. The nucleus of the Swiss Confederacy in the form of the first three confederate allies used to be referred to as the Waldstätte.","switzerland_64.png",4);

//64 Nippon
//Found
            questionRepositories.questionCreator("In the following what do Japanese people call Japan?","Honshu","Kyoto","Nippon","Nisshoki","Nippon","Nippon literally mean the sun\'s origin, that is, where the sun originates, and are often translated as the Land of the Rising Sun.","nippon_65.png",4);

//65 North America
//Found
            questionRepositories.questionCreator("On which continent will you find Guatemala?","Australia","North America","Africa","South America","North America","Guatemala, a Central American country south of Mexico, is home to volcanoes, rainforests and ancient Mayan sites.","north_america_66.png",4);

//66 North America
//Found
            questionRepositories.questionCreator("Which continent will you find Belize?","South America","North America","Europe","Africa","North America","Belize is a nation on the eastern coast of Central America, with Caribbean Sea shorelines to the east and dense jungle to the west.","north_america_67.png",4);

//67 North America
//Found
            questionRepositories.questionCreator("On which continent will you find Nicaragua?","South America","Asia","North America","Africa","North America","Nicaragua, set between the Pacific Ocean and the Caribbean Sea, is a Central American nation known for its dramatic terrain of lakes, volcanoes and beaches.","north_america_68.png",4);

//68 Asia
//Found
            questionRepositories.questionCreator("In the following which continent will you find Yemen?","Asia","Australia","Africa","Europe","Asia","Yemen, officially known as the Republic of Yemen, is an Arab sovereign state in Western Asia at the southern end of the Arabian Peninsula.","asia_69.png",4);

//69 Asia
//Found
            questionRepositories.questionCreator("Which continent will you find Turkey?","Europe","Africa","Asia","North America","Asia","No Funfacts","asia_70.png",4);

//70 Europe
//Found
            questionRepositories.questionCreator("In the following which continent will you find Portugal?","Europe","Austalia","North America","South America","Europe","Portugal is a southern European country on the Iberian Peninsula, bordering Spain.","europe_71.png",4);

//71 United Kingdom of Great Britain and Northern Ireland
//Found
            questionRepositories.questionCreator("In the following what’s the full title of the UK?","United Kingdom of Great Britain and Northern Ireland","United Kingdom of England, Wales, Scotland and Northern Ireland","United Kingdom of Great Britain, Scotland and Northern Ireland","United Kingdom of Great Britain and Scotland","United Kingdom of Great Britain and Northern Ireland","The name refers to the union of what were once four separate nations: England, Scotland, Wales and Ireland (though most of Ireland is now independent.","united_kingdom_of_great_britain_and_northern_ireland_72.png",4);

//72 South America
//Found
            questionRepositories.questionCreator("In which continent will you find Brazil?","Africa","Europe","South America","North America","South America","Officially the Federative Republic of Brazil  is the largest country in both South America and Latin America.","south_america_73.png",4);

//73 Africa
//Found
            questionRepositories.questionCreator("In which of the continent will you find Sierra Leone?","South America","North America","Europe","Africa","Africa","Officially the Republic of Sierra Leone, is a country in West Africa. It is bordered by Guinea to the northeast, Liberia to the southeast and the Atlantic Ocean to the southwest.","africa_74.png",4);

//74 Europe
//Found
            questionRepositories.questionCreator("Which of the following continent you find Malta?","Africa","Europe","South America","North America","Europe","Republic of Malta, is a Southern European island country consisting of an archipelago in the Mediterranean Sea.","europe_75.png",4);

//75 Asia
//Found
            questionRepositories.questionCreator("In which continent you find Laos?","Africa","Europe","Asia","North America","Asia","Laos is a landlocked country in the heart of the Indochinese peninsula of Mainland Southeast Asia, bordered by Myanmar (Burma) and China to the northwest, Vietnam to the east, Cambodia to the southwest, and Thailand to the west and southwest.","asia_76.png",4);

//76 Africa
//Found
            questionRepositories.questionCreator("On which continent will you find Egypt?","Africa","Europe","South America","North America","Africa","No Funfacts","africa_77.png",4);

//77 Asia
//Found
            questionRepositories.questionCreator("Which continent will you find Lesotho?","Africa","Europe","South America","Asia","Asia","Kingdom of Lesotho (Sotho: \'Muso oa Lesotho), is an enclaved country in southern Africa. It is just over  30,000 km2 (11,583 sq mi) in size and has a population of around 2 million. Its capital and largest city is Maseru.","asia_78.png",4);

//78 Brunei
//Found
            questionRepositories.questionCreator("In the following which country has “the Abode of Peace” as an official part of its name?","Brunei","Thailand","Bahrain","Saudia Arabia","Brunei","It was renamed \"Barunai\" in the 14th century, possibly influenced by the Sanskrit word \"varu?\" ,  meaning \"seafarers\". The word \"Borneo\" is of the same origin. In the country\'s full name, Negara Brunei Darussalam, darussalam means \"abode of peace\", while negara means \"country\" in Malay.","brunei_79.png",4);

//79 Aotearoa
//Found
            questionRepositories.questionCreator("Which of the following is the Maori name for New Zealand?","Nui","Moana","Aotearoa","Motu","Aotearoa","New Zealand is a sovereign island country in the southwestern Pacific Ocean.","aotearoa_80.png",4);

//80 United Mexican States
//Found
            questionRepositories.questionCreator("In the following what is the official name of Mexico?","Kingdom of Mexico","Greater Republic of Central America","United Mexican States","United Latin States","United Mexican States","The name of México has several hypotheses that entail the origin, history, and use of the name México, which dates back to 14th century Mesoamerica.","united_mexican_states_81.png",4);

//81 Democratic People Republic of Korea
//Found
            questionRepositories.questionCreator("In the following what is the official name of North Korea?","Republic of Korea","Democratic People Republic of Korea","Communist People’s Republic of Korea","Supreme Republic of Korea","Democratic People Republic of Korea","North Korea, officially the Democratic People\'s Republic of Korea, is a country in East Asia constituting the northern part of the Korean Peninsula. ","democratic_people_republic_of_korea_82.png",4);

//82 Ghana
//Found
            questionRepositories.questionCreator("Which country called by the name of “Gold Coast” ?","Ghana","South Africa","Zimbabwe                              ","Argentina","Ghana","The Gold Coast was originally known as the South Coast (because it was south of Brisbane). However, inflated prices for real estate and other goods and services led to the nickname of \"Gold Coast\" from 1950.","ghana_83.png",4);

//83 Republique Francaise
//Found
            questionRepositories.questionCreator("In the following what does France officially call itself?","Republique Francaise","Republique Caledonie","Frankreich","Republique Gaule","Republique Francaise","Officially the French Republic, is a sovereign state whose territory consists of metropolitan France in Western Europe, as well as several overseas regions and territories.","republique_francaise_84.png",4);

//84 Swaziland
//Found
            questionRepositories.questionCreator("In the following what is the familiar name of the Kingdom of Eswatini?","Swaziland","Estonia","Eritrea","The Gambia","Swaziland","The new name, eSwatini, means \"land of the Swazis\". The change was unexpected, but King Mswati has been referring to Swaziland for years as eSwatini.","swaziland_85.png",4);

//85 Federal Republic of Germany
//Found
            questionRepositories.questionCreator("Tell the official name of Germany in the following?","Grand Duchy of Prussia","Unified Republic of Germany","Federal Republic of Germany","German Democratic Republic","Federal Republic of Germany","\"Germany\" came from the Latin \"Germania\", \"Allemagne\" from the Alemanni tribe, and \"Deutschland\" from the old High German word \"diutisc\" meaning \"of the people\".","federal_republic_of_germany_86.png",4);

//86 Plurinational State of Bolivia
//Found
            questionRepositories.questionCreator("Which of the following is the official name of Bolivia?","Plurinational State of Bolivia","Co-operative Republic of Bolivia","Federative Republic of Bolivia","The democratic Socialist Republic of Bolivia","Plurinational State of Bolivia","The name was approved by the Republic on 3 October 1825. In 2009, a new constitution changed the country\'s official name to \"Plurinational State of Bolivia\" in recognition of the multi-ethnic nature of the country and the enhanced position of Bolivia\'s indigenous peoples under the new constitution.","plurinational_state_of_bolivia_87.png",4);

//87 Australie
//Found
            questionRepositories.questionCreator("In the following what do the French call Australia?","Australien","Australie","Nouveau Holland","Australe","Australie","No Funfacts","australie_88.png",4);

//88 Yokohama
//Found
            questionRepositories.questionCreator("Which of the following cities is not located in Russia? ","Saint Petersburg","Samara","Sochi","Yokohama","Yokohama","Yokohama - is the second largest city in Japan by population, after Tokyo, and the most populous municipality of Japan.","yokohama_89.png",4);

//89 Archipelago
//Found
            questionRepositories.questionCreator("In geography, what word describes a sea with numerous islands or a group of many islands? ","Riverine","Moraine","Archipelago","Presidio","Archipelago","Archipelago - sometimes called an island group or island chain, is a chain, cluster or collection of islands, or sometimes a sea containing a small number of scattered islands.","archipelago_90.png",4);

//90 Eighteen miles
//Found
            questionRepositories.questionCreator("At its broadest point, how wide is the \"Grand Canyon\"? ","Forty Four miles ","Eighteen miles","Nine miles","two miles ","Eighteen miles","eighteen miles - The Grand Canyon is a steep-sided canyon carved by the Colorado River in Arizona, United States. The Grand Canyon is 277 miles (44 km) long, up to 18 miles (29 km) wide and attains a depth of over a mile (,093 feet or 1,857 meters).","eighteen_miles_91.png",4);

//91 Peterborough
//Found
            questionRepositories.questionCreator("One of England\'s greatest Elizabethan houses, Burghley House is located in which English city? ","Plymouth","Oxford","Nottingham","Peterborough","Peterborough","Peterborough - Construction on the Burghley House was finished in 1587. Peterborough is a cathedral city in Cambridgeshire, England, with a population of 183,31 in 2011. ","peterborough_92.png",4);

//92 San Francisco
//Found
            questionRepositories.questionCreator("Which California city stands on the hilly peninsula immediately south of the Golden Gate Bridge? ","Sacramento","San Diego","San Francisco","San Jose","San Francisco","San Francisco - officially the City and County of San Francisco, is the cultural, commercial, and financial center of Northern California.","san_francisco_93.png",4);

//93 Cayenne
//Found
            questionRepositories.questionCreator("A region of France, what city is the capital of \"French Guiana\"? ","Versailles","Cayenne","Bordeaux","Paris","Cayenne","Cayenne - is the capital city of French Guiana, an overseas region and department of Francelocated in South America. The city stands on a former island at the mouth of the Cayenne River on the Atlantic coast. The city\'s motto is \"fert aurum industria\", which means \"work brings wealth\".","cayenne_94.png",4);

//94 Gobi Desert
//Found
            questionRepositories.questionCreator("Which great Asian desert covers parts of northern and northwestern China, and southern Mongolia? ","Kyzyl Kum Desert","Thar Desert","Kara Kum Desert","Gobi Desert","Gobi Desert","Gobi Desert - is a large desert region in Asia. It covers parts of northern and northwestern China, and of southern Mongolia.","gobi_desert_95.png",4);

//95 Caspian Sea
//Found
            questionRepositories.questionCreator("What is the largest saltwater lake or inland sea in the world? ","Lake Eyre","Caspian Sea","Lake Balkhash","Lake Van","Caspian Sea","Caspian Sea - is the largest enclosed inland body of water on Earth by area, variously classed as the world\'s largest lake or a full-fledged sea. ","caspian_sea_96.png",4);

//96 Isthmus
//Found
            questionRepositories.questionCreator("In geography, what name is given to a narrow strip of land that joins two large areas of land across an expanse of water? ","Fjord","Moraine","Isthmus","Lacustrine Plain","Isthmus","Isthmus - is a narrow piece of land connecting two larger areas across an expanse of water by which they are otherwise separated. A tombolo is an isthmus that consists of a spit or bar, and a strait is the sea counterpart of an isthmus.","isthmus_97.png",4);

//97 New South Wales
//Found
            questionRepositories.questionCreator("In which Australian state is the important mining center of \"Broken Hill\"? ","South Australia","Western Australia","New South Wales","Queensland","New South Wales","New South Wales - The closest major city to Broken Hill is Adelaideat, which is about 311 miles away.","new_south_wales_98.png",4);

//98 Vienna
//Found
            questionRepositories.questionCreator("In which Austrian city is the renowned Gothic cathedral of \"St. Stephen\"? ","Vienna","Graz","Klagenfurt","Salzburg","Vienna","Vienna - St. Stephen\'s Cathedral is the most important religious building in Vienna.","vienna_99.png",4);

//99 Antartica
//Found
            questionRepositories.questionCreator("In which continent can you find the active volcano \"Mount Erebus\"? ","Antartica","South America","Africa","Asia","Antartica","Antarctica - The volcano has been active for 1.3 million years ago.","antartica_100.png",4);

//100 Islands
//Found
            questionRepositories.questionCreator("Located in the United States, what kind of geographic feature are the \"Florida Keys\"? ","Mountains","Volcano Chain","Flatlands","Islands","Islands","Islands - The islands lie along the Florida Straits.","islands_101.png",4);
            // TODO END OF GEOGRAPHY

            //TODO ENTERTAINMENT
            //0 James Bond
//Found
            questionRepositories.questionCreator("﻿In Italian translation who is Mr. Kiss Kiss Bang Bang?","James Bond","Keanu Reeves","Chris Pratt","Adam Sandler","James Bond","James Bond is an intelligence officer in the Secret Intelligence Service, commonly known as MI6. Bond is known by his code number, 007, and was a Royal Naval Reserve Commander.","james_bond_1.png",8);

//1 Chris Martin
//Found
            questionRepositories.questionCreator("Which rock star was Jennifer Lawrence rumored to have been dating this year?","Chris Martin","Adam Lavine","Dave Grohl","Fabrizio Moretti","Chris Martin","Christopher Anthony John Martin is an English singer, songwriter, musician, record producer, and philanthropist. He is the lead singer and co-founder of the British rock band Coldplay. ","chris_martin_2.png",8);

//2 Barbados
//Found
            questionRepositories.questionCreator("Which island nation is pop star Rihanna from?","Barbados","Bahamas","Jamaica","Dominican Republic","Barbados","Rihanna is from the beautiful island of Barbados. Also, her first name is actually Robyn — Rihanna is her middle name!","barbados_3.png",8);

//3 Venice
//Found
            questionRepositories.questionCreator("Do you know which magical Italian city George Clooney and his new wife Amal decided to get married in?","Venice","Milan","Rome","Paris","Venice","Venice, of course! The two lovebirds invited their closest friends and family members to the beautiful city for a week to celebrate their nuptials.","venice_4.png",8);

//4 Hershlag
//Found
            questionRepositories.questionCreator("What\'s Natalie Portman\'s actual last name?","Hershlag","Douglas","Portman","Hershing","Hershlag","Natalie\'s real last name is Hershlag. She changed it to Portman, which is her grandmother\'s maiden name, when she started to act.","hershlag_5.png",8);

//5 Shailene Woodley
//Found
            questionRepositories.questionCreator("Which accomplished actor DIDN\'T change her first name?","Shailene Woodley","Julianne Moore","Meryl Streep","Tina Fey","Shailene Woodley","Rising star Shailene Woodley is the only star on this list to have kept her first name. Julianne\'s real name is Julie Anne Smith, Meryl\'s is Mary, and Tina\'s is Elizabeth StamatinaEmil.","shailene_woodley_6.png",8);

//6 Ben Gibbard
//Found
            questionRepositories.questionCreator("Do you know which Indie front man Zooey Deschanel used to be married to?","Ben Gibbard","Justin Vernon","Mat Kearney","Luke Pritchard","Ben Gibbard","Ben Gibbard is an American singer, songwriter and guitarist. He is best known as the lead vocalist and guitarist of the indie rock band Death Cab for Cutie, with which he has recorded eight studio albums, and as one half of the electronica act the Postal ","ben_gibbard_7.png",8);

//7 Julia Roberts
//Found
            questionRepositories.questionCreator("Which actor was country singer Lyle Lovett once married to?","Julia Roberts","Catherine Zeta-Jones","Melanie Griffith","Meg Ryan","Julia Roberts","Julia Roberts is an Academy Award-winning actress and one of Hollywood\'s top stars, known for such films as \'Steel Magnolias,\' \'Pretty Woman\' and \'Erin Brockovich.","julia_roberts_8.png",8);

//8 Two thousand Five
//Found
            questionRepositories.questionCreator("In what year did Angelina Jolie and Brad Pitt get together?","Two thousand Five","Two thousand Six","Two thousand Seven","Two thousand Four","Two thousand Five","Brad Pitt is said to be furious that his estranged wife Angelina Jolie has started going public with their divorce because it\'s \"terrible\" for their children.","two_thousand_five_9.png",8);

//9 Niall Horan
//Found
            questionRepositories.questionCreator("Which One Direction member is Ed Sheeran\'s \"Don\'t\" reportedly about?","Niall Horan","Harry Styles","Zayn Malik","Louis Tomlinson","Niall Horan","Niall James Horan was born on 13 September 1993 in Mullingar, Ireland. His parents, Bobby Horan and Maura Gallagher, divorced when he was five years old, so he and his brother Greg Horan lived with their mother for a year.","niall_horan_10.png",8);

//10 Jake Gyllenhaal
//Found
            questionRepositories.questionCreator("What about Taylor Swift\'s song \"We Are Never Ever Getting Back Together\"?","Jake Gyllenhaal","Joe Jonas","Conor Kennedy","Harry Styles","Jake Gyllenhaal","Jacob Benjamin Gyllenhaal was born in Los Angeles, California, to producer/screenwriter Naomi Foner (née Achs) and director Stephen Gyllenhaal. He is the brother of actress Maggie Gyllenhaal, who played his sister in Donnie Darko (2001).","jake_gyllenhaal_11.png",8);

//11 Janet Leigh
//Found
            questionRepositories.questionCreator("Which famous Hitchcock leading lady is the mother of Jamie Lee Curtis?","Janet Leigh","Judy Garland","Tippi Hedren","Grace Kelly","Janet Leigh","Janet Leigh was an American actress, singer, dancer, and author. Raised in Stockton, California, by working-class parents, Leigh was discovered at age eighteen by actress Norma Shearer, who helped her secure a contract with Metro-Goldwyn-Mayer.","janet_leigh_12.png",8);

//12 Jennifer Lopez
//Found
            questionRepositories.questionCreator("Who was the first person to have a No. 1 album and a No. 1 film in the same week?","Jennifer Lopez","Beyonce","Jennifer Hudson","Will Smith","Jennifer Lopez","Jennifer Lynn Lopez is an American singer, actress, dancer and producer. In 1991, Lopez began appearing as a Fly Girl dancer on In Living Color, where she remained a regular until she decided to pursue an acting career in 1993.","jennifer_lopez_13.png",8);

//13 Jonah Hill
//Found
            questionRepositories.questionCreator("Do you know which funnyman officiated Adam Levine\'s wedding?","Jonah Hill","Michael Cera","Channing Tatum","Seth Rogen","Jonah Hill","Turns out Jonah Hill and Adam Levine have been so close since high school that the Maroon 5 singer asked Hill to officiate his 2014 wedding to Victoria\'s Secret model Behati Prinsloo.","jonah_hill_14.png",8);

//14 Ryan Reynolds and Scarlett Johansson
//Found
            questionRepositories.questionCreator("Plenty of celebs find love through work. But which couple DIDN\'T meet on set?","Ryan Reynolds and Scarlett Johansson","Ben Affleck and Jennifer Garner","Bradley Cooper and Zoe Saldana","Matthew Rhys and Keri Russell","Ryan Reynolds and Scarlett Johansson","Ryan Reynolds and Scarlett Johansson — married from 2008 until 2011 — didn\'t meet while working together. Actually, how did they meet? Anyway, Ben and Jen met on Daredevil, though it took them a few years to figure out they were right for each other.","ryan_reynolds_and_scarlett_johansson_15.png",8);

//15 Dwyane Wade
//Found
            questionRepositories.questionCreator("Which NBA star did actor Gabrielle Union marry earlier this year?","Dwyane Wade","Kobe Bryant","LeBron James","Blake Griffin","Dwyane Wade","Well, considering the fact that Gabrielle is pretty smokin\', it only makes sense that her husband is Dwyane Wade (he plays for the Miami Heat). The two lovebirds got married on August 30, 2014.","dwyane_wade_16.png",8);

//16 Amy Adams
//Found
            questionRepositories.questionCreator("Do you know which of these actors was born outside of the United States?","Amy Adams","Lucy Liu","Brad Pitt","Zoe SalPitt","Amy Adams","Amy Lou Adams is an American actress. Known for both her comedic and dramatic performances, Adams is, as of 2017, among the highest-paid actresses in the world.","amy_adams_17.png",8);

//17 New Jersey
//Found
            questionRepositories.questionCreator("Meryl Streep, Shaquille O\'Neal, and Whitney Houston are all from which state?","New Jersey","New York","California","Massachusetts","New Jersey","New Jersey is a state in the Mid-Atlantic region of the Northeastern United States.","new_jersey_18.png",8);

//18 Santa Barbara
//Found
            questionRepositories.questionCreator("You know that Katy Perry is a California girl. But do you know which city she\'s from?","Santa Barbara","San Diego","Santa Cruz","San Francisco","Santa Barbara","Katheryn Elizabeth Hudson, known professionally as Katy Perry, is an American singer, songwriter, and television personality. After singing in church during her childhood, she pursued a career in gospel music as a teenager.","santa_barbara_19.png",8);

//19 Rob
//Found
            questionRepositories.questionCreator("Who\'s the youngest Kardashian?","Rob","Kendall","Kim","Khloe","Rob","Robert Arthur Kardashian is an American television personality and businessman. He is known for appearing on Keeping Up with the Kardashians, a reality television series that centers upon his family, as well as its spin-offs.","rob_20.png",8);

//20 Versace
//Found
            questionRepositories.questionCreator("What is the last name of fashion designer Donatella, sister of the murdered Gianni?","Versace","Gucci","Dior","Lacoste","Versace","Donatella Francesca Versace is an Italian fashion designer and current vice president of the Versace Group, as well as its chief designer.","versace_21.png",8);

//21 Elvis Presley
//Found
            questionRepositories.questionCreator("Who was the king of rock and roll?","Elvis Presley","Bon Jovi","Nirvana","Metallica","Elvis Presley","Elvis Aaron Presley was an American singer and actor. Regarded as one of the most significant cultural icons of the 20th century, he is often referred to as the \"King of Rock and Roll\" or simply \"the King\".","elvis_presley_22.png",8);

//22 Guns and Roses
//Found
            questionRepositories.questionCreator("In 1991, which famous rock n roll band resurrected Bob Dylan\'s Knockin on Heaven\'s Door?","Guns and Roses","Red hot chili pepper","Nirvana","Green Day","Guns and Roses","Guns N\' Roses, often abbreviated as GNR, is an American hard rock band from Los Angeles, California, formed in 1985. When they signed to Geffen Records in 1986, the band comprised vocalist Axl Rose, lead guitarist Slash, rhythm guitarist Izzy Stradlin, bassist Duff McKagan, and drummer Steven Adler.","guns_and_roses_23.png",8);

//23 Pia Wurtzbach
//Found
            questionRepositories.questionCreator("Miss Universe 2015","Pia Wurtzbach","Paulina Vega","Iris Mittenaere","Olivia Culpo","Pia Wurtzbach","Pia Alonzo Wurtzbach, formerly known in the Philippine entertainment and modeling industry as Pia Romero, is a German-Filipina beauty pageant titleholder, model and actress.","pia_wurtzbach_24.png",8);

//24 Tim Robbins
//Found
            questionRepositories.questionCreator("In the 1994 movie The Shawshank Redemption one of the lead male roles was played by?","Tim Robbins","Jack Robinson","Elijah Wood","Josh Hartnett","Tim Robbins","Timothy Francis Robbins is an American actor, screenwriter, director, producer, and musician. He is well known for his portrayal of Andy Dufresne in the prison drama film The Shawshank Redemption.","tim_robbins_25.png",8);

//25 Clint Eastwood
//Found
            questionRepositories.questionCreator("In the 1966 classic western The Good, the Bad and the Ugly, who played the key actor?","Clint Eastwood","Tom Felton","Chuck Norris J","James Caan","Clint Eastwood","Clinton Eastwood Jr. is an American actor, filmmaker, musician, and political figure. ","clint_eastwood_26.png",8);

//26 Marlon Brando
//Found
            questionRepositories.questionCreator("In the 1972 movie The Godfather who played the Godfather?","Marlon Brando","James Dean","Robert Duvall","James Caan","Marlon Brando","Marlon Brando Jr. was an American actor and film director.","marlon_brando_27.png",8);

//27 Heath Ledger
//Found
            questionRepositories.questionCreator("In the brilliant 2008 movie The Dark Knight who plays the bad guy The Joker?","Heath Ledger","Morgan Freeman","Michael Caine","Maggie Gyllenhaal","Heath Ledger","Heath Andrew Ledger  an Australian actor and director. After performing roles in several Australian television and film productions during the 1990s, Ledger left for the United States in 1998 to further develop his film career.","heath_ledger_28.png",8);

//28 Leonardo DiCaprio
//Found
            questionRepositories.questionCreator("In the movie Inception from 2010 who played the main actor?","Leonardo DiCaprio","Vin Diesel","Robert De Niro","Laurence Fishburne","Leonardo DiCaprio","Leonardo Wilhelm DiCaprio is an American actor and film producer. DiCaprio began his career by appearing in television commercials in the late 1980s.","leonardo_dicaprio_29.png",8);

//29 Keanu Reeves
//Found
            questionRepositories.questionCreator("In the 1999 movie The Matrix the lead male role was played by?","Keanu Reeves","Elijah Wood","Orlando Bloom","Andy Wachowski","Keanu Reeves","Keanu Charles Reeves is a Canadian actor, director, producer, and musician.","keanu_reeves_30.png",8);

//30 Gwyneth Paltrow
//Found
            questionRepositories.questionCreator("In the 1995 movie Se7en the lead female actress was?","Gwyneth Paltrow","Selena Gomez","Demi Lovato","Jane Fonda","Gwyneth Paltrow","Gwyneth Kate Paltrow is an American actress, singer, and food writer. ","gwyneth_paltrow_31.png",8);

//31 Tom Hanks
//Found
            questionRepositories.questionCreator("In the 1994 movie Forrest Gump, who played the lead actor?","Tom Hanks","Jean Reno","Clint Eastwood","Gary Oldman","Tom Hanks","Thomas Jeffrey Hanks is an American actor and filmmaker. Hanks is known for his comedic and dramatic roles in such films.","tom_hanks_32.png",8);

//32 Anthony Perkins
//Found
            questionRepositories.questionCreator("In the 1960 movie Psycho who played Norman Bates?","Anthony Perkins","Sam Loomis","Simon Oakland","Frank Albertson","Anthony Perkins","Anthony Perkins was an American actor and singer. He was nominated for the Academy Award for Best Supporting Actor for his second film, Friendly Persuasion.","anthony_perkins_33.png",8);

//33 Jean Reno
//Found
            questionRepositories.questionCreator("In the 1994 movie Leon who played the role of Leon?","Jean Reno","Danny Aiello","Peter Appel","Gary Oldman","Jean Reno","Juan Moreno y Herrera-Jiménez, known as Jean Reno, is a French actor of Spanish descent. ","jean_reno_34.png",8);

//34 Megan Mylan
//Found
            questionRepositories.questionCreator("The Oscar winning short documentary film ‘Smile Pinki’ (2008) was directed by which director?","Megan Mylan","Michael Curtiz","Charlie Chaplin","Alfred Hitchcock","Megan Mylan","Megan Mylan is an American documentary film director, known for her films Lost Boys of Sudan and the 2008 Academy Award-winning Smile Pinki.","megan_mylan_35.png",8);

//35 Nineteen ninety four-1994
//Found
            questionRepositories.questionCreator("Aishwarya Rai was crowned Miss World in which year?","Nineteen ninety four-1994","Nineteen ninety five-1995","Nineteen ninety three-1993","Nineteen ninety six-1996","Nineteen ninety four-1994","Aishwarya Rai, also known by her married name Aishwarya Rai Bachchan, is an Indian actress, model and the winner of the Miss World 1994 pageant.","nineteen_ninety_four_1994_36.png",8);

//36 Dadasaheb Phalke
//Found
            questionRepositories.questionCreator("Who is known as the \'father of Indian Cinema\'","Dadasaheb Phalke","Dadasaheb Torne","Satyajit Ray","V. Shantaram","Dadasaheb Phalke","Dhundiraj Govind Phalke, popularly known as Dadasaheb Phalke, was an Indian producer-director-screenwriter, known as the Father of Indian cinema.","dadasaheb_phalke_37.png",8);

//37 Prithviraj Kapoor
//Found
            questionRepositories.questionCreator("Who played the role of Emperor Akbar in the movie \'Mughal e Azam\'","Prithviraj Kapoor","Raj Kapoor","Dilip Kumar","Murad","Prithviraj Kapoor","Prithviraj Kapoor was an actor and director, known for Mughal-E-Azam (1960), Maharathi Karna (1944) and Bidyapati (1937). He was married to Rama Kapoor.","prithviraj_kapoor_38.png",8);

//38 Bhanu Athaiya
//Found
            questionRepositories.questionCreator("First Indian to win an Oscar award.","Bhanu Athaiya","Resul Pookutty","Amitabh Bachchan","AR Rahman","Bhanu Athaiya","Bhanu Athaiya  is an Indian costume designer, having worked in over 100 films, since the 1950s, with noted filmmakers like Guru Dutt, Yash Chopra, Raj Kapoor, Ashutosh Gowariker, and international directors like Conrad Rooks and Richard Attenborough.","bhanu_athaiya_39.png",8);

//39 Bradley Simpson
//Found
            questionRepositories.questionCreator("Who is the lead singer of The Vamps?","Bradley Simpson","James McVey","Connor Ball","Tistan Evans","Bradley Simpson","Bradley grew up in Birmingham and was born in The Royal Town of Sutton Coldfield, United Kingdom. His favorite band is Artic Monkeys.He is in a band with James McVey, Connor Ball, and Tristan Evans. He is the lead singer for The Vamps. ","bradley_simpson_40.png",8);

//40 Camila Cabello
//Found
            questionRepositories.questionCreator("Which American female singer features in Major Lazer\'s 2017 song \'Know No Better\'?","Camila Cabello","Katy Perry","Britney Spears","Shakira","Camila Cabello","Karla Camila Cabello Estrabao is a Cuban-American singer and songwriter. She rose to prominence as a member of the girl group Fifth Harmony, which was formed on the second season of the American edition of The X Factor in 2012.","camila_cabello_41.png",8);

//41 Twenty Sixteen
//Found
            questionRepositories.questionCreator("What year did Zayn Malik release an album called \' Mind of Mine\'?","Twenty Sixteen","Twenty Fifteen","Twenty Seventeen","Twenty Eighteen","Twenty Sixteen","Zain Javadd \"Zayn\" Malik, recording mononymously as Zayn, is an English singer and songwriter. Born and raised in Bradford, West Yorkshire, Malik auditioned as a solo contestant for the British music competition The X Factor in 2010.","twenty_sixteen_42.png",8);

//42 Five
//Found
            questionRepositories.questionCreator("How many members were there originally in the boy band One Direction?","Five","Four","Six","Seven","Five","One Direction are an English-Irish pop boy band based in London, composed of Niall Horan, Liam Payne, Harry Styles, Louis Tomlinson, and, until his departure from the band in 2015, Zayn Malik.","five_43.png",8);

//43 Hemmings
//Found
            questionRepositories.questionCreator("What is Luke\'s surname from 5 Seconds of Summer: ","Hemmings","Bradley","Irwin","Hood","Hemmings","Luke Robert Hemmings (born July 16, 1996) is the rhythm guitarist and lead vocalist of 5 Seconds of Summer, along with band members Calum Hood, Michael Clifford, and Ashton Irwin.","hemmings_44.png",8);

//44 Lil Wayne
//Found
            questionRepositories.questionCreator("Which American hip hop artist features in DJ Khaled\'s 2017 hit \'I\'m the One\' with Quavo, Chance the rapper and Justin Bieber?","Lil Wayne","Jay-Z","Kanye West","PSY","Lil Wayne","Lil Wayne. Lil Wayne, byname of Dwayne Michael Carter, Jr., also called Weezy, is an American rapper who became one of the top-selling artists in hip-hop in the early 21st century.","lil_wayne_45.png",8);

//45 Ed Sheeran
//Found
            questionRepositories.questionCreator("Which British male singer released the following songs \'Shape of You\', \'Perfect\' and \'Castle on the Hill\'?","Ed Sheeran","Charly Poth","Jason Derulo","Bruno Mars","Ed Sheeran","Edward Christopher Sheeran, MBE is an English singer, songwriter, guitarist, record producer, and actor. Sheeran was born in Halifax, West Yorkshire, and raised in Framlingham, Suffolk.","ed_sheeran_46.png",8);

//46 The X Factor
//Found
            questionRepositories.questionCreator("What talent show did Little Mix win?","The X Factor","The Voice","Britain Got Talent","None of the Above","The X Factor","The X Factor is a television music competition franchise created by British producer Simon Cowell and his company SYCOtv. It originated in the United Kingdom, where it was devised as a replacement for Pop Idol (2001–2003), and has been adapted in various countries.","the_x_factor_47.png",8);

//47 Ariana Grande
//Found
            questionRepositories.questionCreator("Who released a song in 2016 called \'Side to Side\'?","Ariana Grande","Camila Cabello","Selena Gomez","Becky G.","Ariana Grande","Ariana Grande-Butera is an American singer, songwriter and actress. She began her career in 2008 in the Broadway musical 13, before playing the role of Cat Valentine in the Nickelodeon television series Victorious and in the spinoff Sam & Cat.","ariana_grande_48.png",8);

//48 Lea Salonga
//Found
            questionRepositories.questionCreator("She won two Tonys for her performance as Kim in \"Miss Saigon\". You can also listen to her beautiful voice as Jasmine in Disney\'s \"Aladdin\" and singing Mulan\'s song, \"Reflections\".","Lea Salonga","Britney Spears","Regina Belle","Christina Aguilera","Lea Salonga","Maria Lea Carmen Imutan Salonga, KLD is a Filipina singer and actress best known for her roles in musical theatre, for supplying the singing voices of two Disney Princesses, and as a recording artist and television performer","lea_salonga_49.png",8);

//49 Floyd Mayweather
//Found
            questionRepositories.questionCreator("Top 1 World highest paid entertainer in year 2018.","Floyd Mayweather","Kylie Jenner","George Clooney","Dwayne Johnson","Floyd Mayweather","Floyd Joy Mayweather Jr. is an American professional boxing promoter and former professional boxer. He competed from 1996 to 2007 and 2009 to 2015, and made a one-fight comeback in 2017.","floyd_mayweather_50.png",8);

//50 Manny Pacquiao
//Found
            questionRepositories.questionCreator("Who is known as the greatest boxer of all time?","Manny Pacquiao","Floyd Mayweather","Nonito Donaire","Gerry Peñalosa","Manny Pacquiao","Emmanuel Dapidran Pacquiao, PLH is a Filipino professional boxer and politician, currently serving as a Senator of the Philippines. He currently ranks #4 in BoxRec\'s ranking of the greatest pound for pound boxers of all time.","manny_pacquiao_51.png",8);

//51 Dolphy Quizon
//Found
            questionRepositories.questionCreator("This hallmark of Philippine Cinema died after a long-time battle with Chronic Obstructive Pulmonary Disease.","Dolphy Quizon","Francis Magalona","Rudy Fernandez","Rico Yan","Dolphy Quizon","Rodolfo Vera Quizon Sr. known by his screen names Dolphy, Pidol, and Golay, was a Filipino comedian-actor in the Philippines and He is widely regarded as the country\'s King of Comedy.","dolphy_quizon_52.png",8);

//52 Zendee Tenefere
//Found
            questionRepositories.questionCreator("This Philippine singer won international attention by starring in an episode of The Ellen Degenere\'s Show.","Zendee Tenefere","KZ Tandingan","Jed Madela","Yeng Constantino","Zendee Tenefere","Zendee Rose Tenerefe is a Filipina singer, who rose to prominence after a video of her singing a karaoke version of Whitney Houston\'s \"I Will Always Love You\" was put on YouTube.","zendee_tenefere_53.png",8);

//53 Jennifer Lopez
//Found
            questionRepositories.questionCreator("According to Forbes 2012, who is the world\'s most powerful celebrity?","Jennifer Lopez","Justin Beiber","Ophrah Winfrey","Katty Perry","Jennifer Lopez","Jennifer Lynn Lopez is an American singer, actress, dancer and producer. In 1991, Lopez began appearing as a Fly Girl dancer on In Living Color, where she remained a regular until she decided to pursue an acting career in 1993.","jennifer_lopez_54.png",8);

//54 Praybeyt Benjamin
//Found
            questionRepositories.questionCreator("What is the top grossing Philippine film of all time?","Praybeyt Benjamin","Tanging Ina","No Other Woman","Bonifacio ang unang Pangulo","Praybeyt Benjamin","The Amazing Praybeyt Benjamin is a 2014 comedy film written and directed by Wenn V. Deramas. It is the sequel to The Unkabogable Praybeyt Benjamin. The film is one of the official entries to the 40th Metro Manila Film Festival. Vice Ganda reprised his role as Colonel Benjamin \"Benjie\" Santos VIII.","praybeyt_benjamin_55.png",8);

//55 Mel B
//Found
            questionRepositories.questionCreator("Which Spice girl was the first to marry?","Mel B","Victoria Beckham","Emma Bunton","Melanie C","Mel B","Melanie Janine Brown, also known as Mel B or Melanie B, is an English singer, songwriter, actress, and television personality.","mel_b_56.png",8);

//56 Fashion Designer
//Found
            questionRepositories.questionCreator("Giorgio Armani is famous in which field?","Fashion Designer","Director","Model","Actor","Fashion Designer","Giorgio Armani is an Italian fashion designer. He is known today for his clean, tailored lines. He formed his company, Armani, in 1975, and by 2001 was acclaimed as the most successful designer of Italian origin.","fashion_designer_57.png",8);

//57 Campbell
//Found
            questionRepositories.questionCreator("Which supermodel Naomi wrote a novel called Swan?","Campbell","Watts","Rowling","Austen","Campbell","Naomi Elaine Campbell is an English model, actress, and singer.","campbell_58.png",8);

//58 Enrique Iglesias
//Found
            questionRepositories.questionCreator("This Latin superstar took the whole world by storm in 1999. He is the \"hero\" you might wish to \"escape\" with, and he is also the son of a famous crooner and a Filipina.","Enrique Iglesias","Ricky Martin","Enrique Gil","Bamboo","Enrique Iglesias","Enrique Miguel Iglesias Preysler is a Spanish singer, songwriter, actor and record producer. He is widely regarded as the King of Latin Pop.","enrique_iglesias_59.png",8);

//59 Billy Crawford
//Found
            questionRepositories.questionCreator("He is American pop star made a sensation in Europe, and you\'ve probably kept on \"trackin\" with him. He started out, however, as a child star in the Philippines before moving to America.","Billy Crawford","Ricky Martin","Nick Carter","Justin Timberlake","Billy Crawford","Billy Joe Ledesma Crawford is a Filipino-American singer, dancer, songwriter, actor and TV host.","billy_crawford_60.png",8);

//60 Paolo Montalban
//Found
            questionRepositories.questionCreator("He kicked some butt in the TV Series \"Mortal Kombat\" and was Brandy\'s Prince Charming in \"Cinderella\", where Whitney Houston was the Fairy Godmother.","Paolo Montalban","Ryan Gosling","Zen Gesner","Krista Ranillo","Paolo Montalban","Paolo also made a Filipino film, set in the US, about Filipino migrants who search for the American Dream. Its title is \"American Adobo\".","paolo_montalban_61.png",8);

//61 Rob Schneider
//Found
            questionRepositories.questionCreator("This \"Male Gigolo\" and \"Animal\" is also in \"50 First Dates\"... and he happens to have Filipino blood, can you name him?","Rob Schneider","Nick Hawk","Brace Land","Vin Armani","Rob Schneider","Robert Michael Schneider is an American actor, comedian, screenwriter, and director.","rob_schneider_62.png",8);

//62 Fashion Designer
//Found
            questionRepositories.questionCreator("Calvin Klein best fits which of these descriptions?","Fashion Designer","Played Detective On ","NYPD Blue Newspaper Advice Columnist","19th Century English Novelist","Fashion Designer","Calvin Richard Klein is a Hungarian-American fashion designer who launched the company that would later become Calvin Klein Inc., in 1968. ","fashion_designer_63.png",8);

//63 Leonardo da Vinci
//Found
            questionRepositories.questionCreator("This genius was born in Italy in 1452 and died in France in 1519. He was a painter and inventor, among many other things. He is reported to have been left handed.","Leonardo da Vinci","Leonardo di Caprio","Leonardo Bistolfi","Leonardo Sandri","Leonardo da Vinci","Leonardo da Vinci, one of the most brilliant men in history, wrote from right to left and in mirror image. This is certainly easier for a person using their left hand, especially writing with ink, although it appears he may have been ambidextrous.","leonardo_da_vinci_64.png",8);

//64 Kim Kardashian
//Found
            questionRepositories.questionCreator("Who didn’t announce she was pregnant in 2017?","Kim Kardashian","Binky Felstead","Heidi Montag","Chrissy Teigen","Kim Kardashian","Kimberly Noel Kardashian West is an American reality television personality, entrepreneur and socialite","kim_kardashian_65.png",8);

//65 Andy Jordan
//Found
            questionRepositories.questionCreator("Which reality star didn’t make music in 2017?","Andy Jordan","Steph Davis","Megan McKenna","Chris Hughes and Kem Cetinay","Andy Jordan","Andrew Mark “Andy” Jordan is an English actor and singer-songwriter. ","andy_jordan_66.png",8);

//66 Meghan Markle
//Found
            questionRepositories.questionCreator("Who was the most googled celeb of 2017?","Meghan Markle","Beyoncé","Kevin Spacey","Selena Gomez","Meghan Markle","Meghan, Duchess of Sussex, is an American-born member of the British royal family and a former film and television actress. Markle was born and raised in Los Angeles, California, and is of mixed-race heritage.","meghan_markle_67.png",8);

//67 Moonlight
//Found
            questionRepositories.questionCreator("What film won Best Picture at the Oscars?","Moonlight","La La Land","Call Me By Your Name","Dunkirk","Moonlight","Moonlight is a 2016 American coming-of-age drama film written and directed by Barry Jenkins, based on Tarell Alvin McCraney\'s unpublished semi-autobiographical play In Moonlight Black Boys Look Blue.","moonlight_68.png",8);

//68 Selena Gomez
//Found
            questionRepositories.questionCreator("Which celeb produced 13 Reasons Why?","Selena Gomez","Demi Lovato","Taylor Swift","Katherine Langford","Selena Gomez","Selena Marie Gomez is an American singer, actress, and producer.","selena_gomez_69.png",8);

//69 Camila Cabello
//Found
            questionRepositories.questionCreator("Who was the most tweeted about celeb of 2017?","Camila Cabello","Donald Trump","Meghan Markle","Harry Styles","Camila Cabello","Camila Cabello is mainly a pop and R&B singer, influenced by Latin music. She incorporated elements of reggaeton, hip hop and dancehall in her first album.","camila_cabello_70.png",8);

//70 Beyonce
//Found
            questionRepositories.questionCreator("Who was announced to play Nala in the upcoming live action remake of The Lion King?","Beyonce","Meryl Streep","Blake Lively","Gigi Hadid","Beyonce","Beyoncé Giselle Knowles-Carter is an American singer, songwriter, performer, and actress. Born and raised in Houston, Texas, Beyoncé performed in various singing and dancing competitions as a child.","beyonce_71.png",8);

//71 Suits
//Found
            questionRepositories.questionCreator("Which US TV show did Meghan Markle announce she was leaving?","Suits","The Sinner","Dynasty","Grey's Anatomy","Suits","Suits is an American drama series which Meghan Markle plays Rachel Zane.","suits_72.png",8);

//72 Kendall Jenner
//Found
            questionRepositories.questionCreator("Who was the highest paid model of the year 2017?","Kendall Jenner","Jourdan Dunn","Gigi Hadid","Alessandra Ambrosia","Kendall Jenner","Kendall Nicole Jenner is an American model and television personality. ","kendall_jenner_73.png",8);

//73 Four Hundred Twenty Million
//Found
            questionRepositories.questionCreator("How much was Kylie Cosmetics estimated at in 2017?","Four Hundred Twenty Million","Three Hundred Million","One Billion","Seven Hunder Sixty Million","Four Hundred Twenty Million","Kylie Cosmetics... It has made Kylie a fortune—and the company is only two years old. Kylie\'s recent Forbes profile revealed that she\'s sold more than $630 million worth of makeup, \"including an estimated $330 million in 2017.\"","four_hundred_twenty_million_74.png",8);

//74 Captain America Civil War
//Found
            questionRepositories.questionCreator("What was the top-grossing film of 2016, according to Box Office Mojo?","Captain America Civil War","Deadpool","Zootopia","Finding Dory","Captain America Civil War","Iron Man and Captain America\'s main conflict in \"Civil War\" is sparked by Bucky Barnes (Sebastian Stan), also known as the Winter Soldier.","captain_america_civil_war_75.png",8);

//75 Jennifer Lawrence
//Found
            questionRepositories.questionCreator("Who does Forbes magazine list as the highest-paid actress of 2016?","Jennifer Lawrence","Julia Roberts","Gwyneth Paltrow","Jennifer Aniston","Jennifer Lawrence","Jennifer Shrader Lawrence is an American actress. Her films have grossed over $5.7 billion worldwide, and she was the highest-paid actress in the world in 2015 and 2016.","jennifer_lawrence_76.png",8);

//76 X-Men Apocalypse
//Found
            questionRepositories.questionCreator("Which X-Men film was released in 2016?","X-Men Apocalypse","X-Men: Days of Future Past","X-Men: First Class","X-Men: The Last Stand","X-Men Apocalypse","Apocalypse (En Sabah Nur) is a fictional supervillain appearing in comic books published by Marvel Comics. He is one of the world\'s first mutants, and was originally a principal villain for the original X-Factor team and now for the X-Men and related spinoff teams.","x_men_apocalypse_77.png",8);

//77 Tim Burton
//Found
            questionRepositories.questionCreator("Who directed “Miss Peregrine’s Home for Peculiar Children”?","Tim Burton","Wes Anderson","Martin Scorsese","Quentin Tarantino","Tim Burton","Tim Burton was born on August 25, 1958, in Burbank, California. After majoring in animation at the California Institute of Arts, he worked as a Disney animator for less than a year before striking out on his own.","tim_burton_78.png",8);

//78 Natalie Portman
//Found
            questionRepositories.questionCreator("Who stars as former First Lady Jacqueline Kennedy in the 2016 release “Jackie”?","Natalie Portman","Sandra Bullock","Renee Zellweger","Nicole Kidman","Natalie Portman","Natalie Portman is an actress, film producer and director with dual Israeli and American citizenship. She is the recipient of several accolades, including an Academy Award and two Golden Globe Awards.","natalie_portman_79.png",8);

//79 The Angry Birds Movie
//Found
            questionRepositories.questionCreator("Which 2016 film began life as a video game?","The Angry Birds Movie","Trolls","Pete’s Dragon","Storks","The Angry Birds Movie","Angry Birds will officially launch—via slingshot—onto the big screen for “The Angry Birds Movie,” starring the voices of comedians Jason Sudeikis, Josh Gad, Bill Hader, Maya Rudolph and Danny McBride.","the_angry_birds_movie_80.png",8);

//80 Chris Rock
//Found
            questionRepositories.questionCreator("Which comic hosted the 2016 Academy Awards?","Chris Rock","Steve Martin","Jon Stewart","Billy Crystal","Chris Rock","Christopher Julius Rock III is an American comedian, actor, writer, producer, and director.","chris_rock_81.png",8);

//81 Rob
//Found
            questionRepositories.questionCreator("Which Kardashian broke up with their partner following the birth of their child?","Rob","Kim","Kourtney","Khloe","Rob","Robert Arthur Kardashian is an American television personality and businessman. He is known for appearing on Keeping Up with the Kardashians, a reality television series that centers upon his family, as well as its spin-offs.","rob_82.png",8);

//82 Tom Hiddleston
//Found
            questionRepositories.questionCreator("What British actor was linked to Taylor Swift following her breakup with Calvin Harris?","Tom Hiddleston","Benedict Cumberbatch","James Corden","Daniel Craig","Tom Hiddleston","Thomas William Hiddleston is an English actor, film producer and musician.","tom_hiddleston_83.png",8);

//83 Negan
//Found
            questionRepositories.questionCreator("Who killed Glen on \"The Walking Dead\" in one of the worst-kept secrets in television?","Negan","Carol","The Governor","Rick Grimes","Negan","Negan is a fictional character in the comic book series The Walking Dead and in the television series of the same name.","negan_84.png",8);

//84 Jon Snow
//Found
            questionRepositories.questionCreator("Who returned from the dead on \"Game of Thrones\"?","Jon Snow","Jon Hamm","Ramsay","Michonne","Jon Snow","Jon Snow, born Aegon Targaryen, is the son of Lyanna Stark and Rhaegar Targaryen, the late Prince of Dragonstone.","jon_snow_85.png",8);

//85 Simone Biles
//Found
            questionRepositories.questionCreator("What Olympic gymnast took center stage at the Rio Olympics in 2016?","Simone Biles","Lebron James","Mary Lou Retton","Tonya Harding","Simone Biles","Simone Arianne Biles is an American artistic gymnast. Biles is the 2016 Olympic individual all-around, vault and floor gold medalist, and balance-beam bronze medalist.","simone_biles_86.png",8);

//86 Adele
//Found
            questionRepositories.questionCreator("Who had the top-grossing tour of 2016, according to StubHub?","Adele","Beyonce","Bruce Springsteen","Luke Bryan","Adele","Adele Laurie Blue Adkins MBE is an English singer and songwriter.","adele_87.png",8);

//87 Kate Beckinsale
//Found
            questionRepositories.questionCreator("British actress famous as the female star of the Underworld series of films.","Kate Beckinsale","Emily Blunt","Emma Watson","Julie Andrews","Kate Beckinsale","Kathrin Romary Beckinsale is an English actress.","kate_beckinsale_88.png",8);

//88 Titanic
//Found
            questionRepositories.questionCreator("Kate Winslet starred as Rose DeWitt Bukater in this story of an ill fated voyage. Name the film.","Titanic","The Reader","The Mountain between us","Divergent","Titanic","Kate Elizabeth Winslet is an English actress. She made her movie debut as Juliet Hulme in Peter Jackson\'s Heavenly Creatures (1994). She is best known for her starring role in the American movie, Titanic.","titanic_89.png",8);

//89 Marian Rivera
//Found
            questionRepositories.questionCreator("Asia’s Primetime Queen.","Marian Rivera","Barbie Forteza","Angel Locsin","Yassi Pressman","Marian Rivera","Marian Rivera Gracia-Dantes or Marian Rivera y Gracia y Dantes, known professionally as Marian Rivera, is a Spanish Filipino commercial model and actress","marian_rivera_90.png",8);

//90 Regine Velasquez
//Found
            questionRepositories.questionCreator("Asia’s Song Bird.","Regine Velasquez","Lea Salonga","Sarah Geronimo","Yeng Constantino","Regine Velasquez","Regina Encarnacion Ansong Velasquez is a Filipino singer, actress and record producer. She gained recognition by winning both the 1984 Ang Bagong Kampeon and the 1989 Asia-Pacific Song Contest, representing the Philippines in the latter.","regine_velasquez_91.png",8);

//91 Eat Bulaga
//Found
            questionRepositories.questionCreator("The longest running noontime show in the Philippines.","Eat Bulaga","Asap","Wowowie","It’s Showtime","Eat Bulaga","Eat Bulaga! is a Philippine television variety show broadcast by GMA Network. Produced by Television And Production Exponents Inc., it is the longest running noontime show in the Philippines. The show has been hosted since its inception by Tito Sotto, Vic Sotto and Joey De Leon.","eat_bulaga_92.png",8);

//92 TV Patrol
//Found
            questionRepositories.questionCreator("The longest-running Filipino news program in the Philippines.","TV Patrol","Saksi","Bandila","Aksyon","TV Patrol","TV Patrol is the longest-running Filipino news program has been on air since March 2, 1987. Its original anchors were Noli de Castro, Robert Arevalo, Mel Tiangco, and Angelique Lazo.  Currently, the show is now anchored by Noli de Castro ,Ted Failon, and Bernadette Sembrano .Noli de Castro ends the program with his famous catchphrase, “Magandang Gabi, Bayan”.","tv_patrol_93.png",8);

//93 January 26, 1958
//Found
            questionRepositories.questionCreator("On what day was Ellen DeGeneres born?","January 26, 1958","March 05, 1960","April 20, 1956","December 25, 1961","January 26, 1958","Ellen Lee DeGeneres is an American comedian, television host, actress, writer, producer, and LGBT activist.","january_26_1958_94.png",8);

//94 Mississippi
//Found
            questionRepositories.questionCreator("Where was Oprah Winfrey born?","Mississippi","New York  ","Illinois","Georgia","Mississippi","Oprah Winfrey was born in the rural town of Kosciusko, Mississippi, on January 29, 1954. In 1976, Winfrey moved to Baltimore, where she hosted a hit television chat show, People Are Talking.","mississippi_95.png",8);

//95 Prince William
//Found
            questionRepositories.questionCreator("Who is the eldest son of Princess Diana and Prince Charles of Wales?","Prince William","Prince Charles","Prince Harry","Prince George","Prince William","Prince William, Duke of Cambridge, KG, KT, PC, ADC is a member of the British royal family. He is the elder son of Charles, Prince of Wales, and Diana, Princess of Wales.","prince_william_96.png",8);

//96 Kris Aquino
//Found
            questionRepositories.questionCreator("The Filipino actress dubbed as Queen of all Media.","Kris Aquino","Sharon Cuneta","Mariel Rodriguez","Angel Locsin","Kris Aquino","Kristina Bernadette Cojuangco Aquino is a Filipino talk show host, actress and producer. She has hosted talk shows and game shows, and has also starred in films and television series.","kris_aquino_97.png",8);

            //TODO END OF ENTERTAINMENT


            //TODO MUSIC
            //0 Chance the Rapper
//Found
            questionRepositories.questionCreator("﻿Presented in February of 2017, who won the Grammy Award for Best New Artist?","Chance the Rapper","The Chainsmokers","Kelsea Ballerini","Maren Morris","Chance the Rapper","Chancelor Jonathan Bennett (born April 16, 1993), known professionally as Chance the Rapper, is an American rapper, singer, songwriter, actor, record producer, and philanthropist from the West Chatham neighborhood of Chicago, Illinois.","chance_the_rapper_1.png",6);

//1 Infinite
//Found
            questionRepositories.questionCreator("Their 20th, what studio album did Deep Purple release on April 1st, 2017?","Abandon","Purpendicular","Rapture of the Deep","Infinite","Infinite","Infinite is the 20th studio album by English rock band Deep Purple, released on 7 April 2017.","infinite_2.png",6);

//2 Back Road
//Found
            questionRepositories.questionCreator("Finish the title to this 2017 Sam Hunt hit song - \"Body Like a \".","Heavenly Goddess","Wooden Toothpick","Bronze God","Back Road","Back Road","The song was written by Sam Hunt, Zach Crowell, Shane McAnally and Josh Osborne.","back_road_3.png",6);

//3 Shape of You
//Found
            questionRepositories.questionCreator("What 2017 Billboard Top 100 song starts with the lyric - \"The club isn\'t the best place to find a lover, so the bar is where I go\"?","Tunnel Vision","Castle on the Hill","I Feel it Coming","Shape of You","Shape of You","\"Shape of You\" peaked at number-one on the singles charts of 34 countries, including the US Billboard Hot 100 (later becoming the best performing song of 2017), as well as the UK, Australian and Canadian singles charts.","shape_of_you_4.png",6);

//4 Drake
//Found
            questionRepositories.questionCreator("Which Canadian rapper had Billboard hits with the songs \"No Long Talk\", \"Get It Together\" and \"Blem\" in 2017?","Madchild","Checkmate","Kardinal Offishall","Drake","Drake","Aubrey Drake Graham (born October 24, 1986) is a Canadian rapper, singer, songwriter, record producer, actor, and entrepreneur. He is one of the most popular entertainers in the world, and one of the best-selling music artists of the 21st century.","drake_5.png",6);

//5 Summer
//Found
            questionRepositories.questionCreator("On the Billboard Hot Rock Songs chart for March/April 2017, finish the title to this Weezer song - \"Feels Like ...\".","Summer","Winter","Rain","Love","Summer","Summer - The song was released as a single in March of 2017.","summer_6.png",6);

//6 Chuck Berry
//Found
            questionRepositories.questionCreator("Which artist, who died in March of 2017, had his songs \"Maybellene\" and \"You Never Can Tell\" make a reappearance on the charts?","James Brown","Jerry Lee Lewis","Little Richard","Chuck Berry","Chuck Berry","Chuck was one of the first musicians to be inducted into the Rock and Roll Hall of Fame when it made its debut in 1986.","chuck_berry_7.png",6);

//7 Sib Hashian
//Found
            questionRepositories.questionCreator("Boston\'s drummer died on stage while playing the band\'s greatest hits in March of 2017. Name him.","Sib Hashian","Tracy Ferrie","Gary Pihl","Brad Delp","Sib Hashian","Released in 1976, Boston\'s self-titled debut album is one of the bestselling debut albums in U.S. history.","sib_hashian_8.png",6);

//8 Lady Gaga
//Found
            questionRepositories.questionCreator("Released on her fifth studio album, which American singer had a hit with the song \"Million Reasons\"?","Kesha","Katy Perry","Lady Gaga","Christina Aguilera","Lady Gaga","Stefani Joanne Angelina Germanotta, known professionally as Lady Gaga, is an American singer, songwriter, and actress. She is known for her unconventionality and provocative work as well as visual experimentation. Lady Gaga - Found on the album \'Joanne\'.","lady_gaga_9.png",6);

//9 Lorde
//Found
            questionRepositories.questionCreator("New Zealand singer and songwriter Ella Marija Lani Yelich-O\'Connor is better known as?","Kimbra","Charli XCX","Lorde","Lana Del Rey","Lorde","Ella Marija Lani Yelich-O\'Connor, known professionally as Lorde, is a New Zealand singer, songwriter, and record producer. She holds both New Zealand and Croatian citizenship. As of 2017, Lorde has earned two Grammy Awards.","lorde_10.png",6);

//10 Beauty and the Beast
//Found
            questionRepositories.questionCreator("What Disney film soundtrack peaked at number three on the Billboard 200 chart in March of 2017?","Coco","Cars Three","Ghost in the Shell","Beauty and the Beast","Beauty and the Beast","Beauty and the Beast is a live-action remake of Disney\'s 1991 animated film of the same name.","beauty_and_the_beast_11.png",6);

//11 George Harrison
//Found
            questionRepositories.questionCreator("Coinciding with what would have been his 74th birthday, which former Beatle\'s entire solo album collection was released in February of 2017?","Paul McCartney","John Lennon","Ringo Starr","George Harrison","George Harrison","George Harrison MBE was an English guitarist, singer-songwriter, and producer who achieved international fame as the lead guitarist of the Beatles. The boxed set is titled George Harrison - The Vinyl Collection.","george_harrison_12.png",6);

//12 Zayn Malik
//Found
            questionRepositories.questionCreator("Released on the film soundtrack \"Fifty Shades Darker\", who collaborated with Taylor Swift on the song \"I Don\'t Wanna Live Forever\"?","Shawn Mendes","Harry Styles","Zayn Malik","Justin Bieber","Zayn Malik","The song was written by Taylor Swift, Sam Dew and Jack Antonoff.","zayn_malik_13.png",6);

//13 Blackstar David Bowie
//Found
            questionRepositories.questionCreator("Presented posthumously, who won the 2017 Grammy Award for Best Rock Song?","Unspoken – Chuck Loeb","Blackstar David Bowie","Don't Hurt Yourself – Beyonce","Heathens - Tyler Joseph","Blackstar David Bowie","Blackstar was released on Bowie\'s final studio album of the same name.","blackstar_david_bowie_14.png",6);

//14 Brian Eno
//Found
            questionRepositories.questionCreator("Starting his music career as a member of Roxy Music in 1971, who released the album \"Reflection\" on New Year\'s Day, 2017?","Brian Eno","Bono","John Cale","Robert Fripp","Brian Eno","Brian Peter George St John le Baptiste de la Salle Eno, RDI is an English musician, composer, record producer, singer, writer, and visual artist. - Eno is widely regarded as an ambient music pioneer.","brian_eno_15.png",6);

//15 Twenty four thousand Magic Bruno Mars
//Found
            questionRepositories.questionCreator("Presented in January of 2018, who won the Grammy Award for Album of the Year? ","The Story of O.J. - Jay-Z","Humble - Kendrick Lamar","Redbone - Childish Gambino","Twenty four thousand Magic Bruno Mars","Twenty four thousand Magic Bruno Mars","Twenty four thousand Magic is the third studio album by American singer and songwriter Bruno Mars.","twenty_four_thousand_magic_bruno_mars_16.png",6);

//16 Firepower
//Found
            questionRepositories.questionCreator("Their 18th, what studio album did Judas Priest release in March of 2018? ","Redeemer of Souls","Angel of Retribution","Nostradamus","Firepower","Firepower","Firepower sold close to 50,000 copies in the United States during its first week of release.","firepower_17.png",6);

//17 Blood
//Found
            questionRepositories.questionCreator("Finish the title to this 2018 Shawn Mendes hit song - \"In My ...\". ","Backyard","Life","Blood","Car","Blood","The song chronicles Shawn\'s struggle with an anxiety disorder.","blood_18.png",6);

//18 Lucid Dreams
//Found
            questionRepositories.questionCreator("What 2018 Billboard Top 100 song starts with the lyric - \"I still see your shadows in my room. Can\'t take back the love that I gave you\"? ","Lucid Dreams","Girls Like You","No Tears Left to Cry","Sad!","Lucid Dreams","Lucid Dreams - Released in March of 2018, Lucid Dreams was recorded by American rapper Juice WRLD.","lucid_dreams_19.png",6);

//19 Drake
//Found
            questionRepositories.questionCreator("Which Canadian rapper had Billboard hits with the songs \"Nice For What\", \"God\'s Plan\" and \"I\'m Upset\" in 2018? ","Checkmate","Madchild","Drake","Kardinal Offishall","Drake","Drake - As of 2018, Drake holds the record for most charted songs among solo artists in the history of the Billboard Hot 100 at 154.","drake_20.png",6);

//20 Clouds
//Found
            questionRepositories.questionCreator("On the Billboard Hot Rock Songs chart for July 2018, finish the title for this song \"Panic! At The Disco\" song - \"King of the ...\". ","Sun","World","Clouds","Night","Clouds","Panic! at the Disco is an American rock band that formed in 2004.","clouds_21.png",6);

//21 Dolores O'Riordan
//Found
            questionRepositories.questionCreator("Which artist, who died at the age of 46 in January of 2018, was the lead singer for the Cranberries?? ","Dolores O'Riordan","Barbara Cope","Betty Willis","Claudia Fontaine","Dolores O'Riordan","Dolores Mary Eileen O\'Riordan was an Irish musician, singer and songwriter. She led the rock band The Cranberries from 1990 until their break-up in 2003; they reunited in 2009.","dolores_o_riordan_22.png",6);

//22 Cardi B
//Found
            questionRepositories.questionCreator("Released on her debut studio album, which American rapper had a hit with the song \"Be Careful\"? ","Nicki Minaj","Cardi B","Queen Latifah","Lil' Kim","Cardi B","Cardi B - Belcalis Marlenis Almanzar known professionally as Cardi B, is an American rapper. Born and raised in The Bronx, New York City","cardi_b_23.png",6);

//23 Pink
//Found
            questionRepositories.questionCreator("American singer and songwriter Alecia Beth Moore is better known as? ","Pink","Shakira","Beyonce","Rihanna","Pink","Pink was originally a member of the girl group Choice.","pink_24.png",6);

//24 Thirteen Reasons Why Season two
//Found
            questionRepositories.questionCreator("What TV show\'s soundtrack peaked at #26 on the Billboard 200 chart in June of 2018? ","The Good Place - Season one","Wild Wild Country - Season one","Thirteen Reasons Why Season two","The Good Fight - Season two","Thirteen Reasons Why Season two","Thirteen Reasons Why - Season 2 - The TV show is based on the 2007 novel Thirteen Reasons Why by Jay Asher.","thirteen_reasons_why_season_two_25.png",6);

//25 Drake
//Found
            questionRepositories.questionCreator("Released on the album \"Simi\", who collaborated with Blocboy JB on the song \"Look Alive\"? ","Zayn Malik","Harry Styles","Drake","Shawn Mendes","Drake","Look Alive peaked at number five on the Billboard Hot 100.","drake_26.png",6);

//26 Bruno Mars
//Found
            questionRepositories.questionCreator("Who won all six Grammy Awards for which he was nominated at the 60th Annual Grammy Awards? ","Rihanna","Jay-Z","Ed Sheeran","Bruno Mars","Bruno Mars","As of 2018, Mars has sold over 130 million records worldwide.","bruno_mars_27.png",6);

//27 Jack White
//Found
            questionRepositories.questionCreator("Winner of 12 Grammy Awards, what former member of the White Stripes released the album \"Boarding House Reach\" in March of 2018? ","Meg White","Alison Mosshart","Brendan Benson","Jack White","Jack White","Jack White Boarding House Reach is Jack\'s third solo studio album.","jack_white_28.png",6);

//28 Nokie Edwards
//Found
            questionRepositories.questionCreator("What longtime Venture\'s guitarist passed away in March 2018 at the age of 82? ","Bob Bogle","Jerry McGee","Nokie Edwards","Don Wilson","Nokie Edwards","Nole Floyd \"Nokie\" Edwards was an American musician and member of the Rock and Roll Hall of Fame. He was primarily a guitarist, best known for his work with The Ventures, and was known in Japan as the King of Guitars.","nokie_edwards_29.png",6);

//29 Kanye West
//Found
            questionRepositories.questionCreator("Making its debut in April of 2018, the studio album \"Ye\" was released by which American rapper? ","Kanye West","50 Cent","Jay-Z","Eminem","Kanye West","Kanye Omari West is an American rapper, singer, songwriter, record producer, entrepreneur and fashion designer. ","kanye_west_30.png",6);

//30 Baha Men
//Found
            questionRepositories.questionCreator("\"Who Let the Dogs Out\" in 2000? ","Snoopy","Baha Men","Shaggy","Lil Bow Wow","Baha Men","The Baha Men are a Bahamian band playing a modernized style of Bahamian music called junkanoo. They are best remembered for their Grammy Award-winning hit song \"Who Let the Dogs Out?\".","baha_men_31.png",6);

//31 Bruce Springsteen
//Found
            questionRepositories.questionCreator("Released in 1973, my debut album is titled \"Greetings from Asbury Park\". Who am I? ","Eric Clapton","Jon Bon Jovi","Bruce Springsteen","Bryan Adams","Bruce Springsteen","Bruce Frederick Joseph Springsteen is an American singer-songwriter and musician, known for his work with the E Street Band.","bruce_springsteen_32.png",6);

//32 Vera Lynn
//Found
            questionRepositories.questionCreator("\"The Forces Sweetheart\" was a nickname given to which 1940s female star? ","Alma Cogan","Kay Starr","Vera Lynn","Rosemary Clooney","Vera Lynn","Vera\'s musical recordings were popular during the Second World War.","vera_lynn_33.png",6);

//33 Hound Dog
//Found
            questionRepositories.questionCreator("Which of these Elvis Presley songs was a number one BEFORE the others? ","It's Now or Never","All Shook Up","Hound Dog","Stuck on You","Hound Dog","Elvis released his version of Hound Dog in July of 1956.","hound_dog_34.png",6);

//34 Geri Halliwell
//Found
            questionRepositories.questionCreator("This \"Spice Girl\" had a number one hit with \"It\'s Raining Men\" in 2001. ","Victoria Beckham","Emma Bunton","Geri Halliwell","Melanie C","Geri Halliwell","Geri Halliwell - The song was originally released by The Weather Girls in 1982.","geri_halliwell_35.png",6);

//35 Manic Street Preachers
//Found
            questionRepositories.questionCreator("Mysteriously disappearing in February of 1995, Richey Edwards was a member of which rock band? ","The Stone Roses","Stereophonics","Primal Scream","Manic Street Preachers","Manic Street Preachers","Richard James \"Richey\" Edwards was a Welsh musician who was the lyricist and rhythm guitarist of the alternative rock band Manic Street Preachers. Edwards was declared \'presumed dead\' in November of 2008.","manic_street_preachers_36.png",6);

//36 Pete Best
//Found
            questionRepositories.questionCreator("Who originally played the drums with \"The Beatles\"? ","Ringo Starr","George Harrison","Pete Best","Stuart Sutcliffe","Pete Best","Randolph Peter Best is an English musician, principally known as an original member and the first drummer of the Beatles, from 1960 to 1962. ","pete_best_37.png",6);

//37 (Hit Me) Baby One More Time
//Found
            questionRepositories.questionCreator("With what song did Britney Spears make her breakthrough in 1998? ","fOoops, I Did it Again","Circus","(Hit Me) Baby One More Time","Toxic","(Hit Me) Baby One More Time","Britney Jean Spears (born December 2, 1981) is an American singer, dancer, and actress. Born in McComb, Mississippi, and raised in Kentwood, Louisiana, she performed acting roles in stage productions and television shows as a child, before signing with Jive Recordsin 1997.","(hit_me)_baby_one_more_time_38.png",6);

//38 Avril Lavigne
//Found
            questionRepositories.questionCreator("Which Canadian singer-songwriter was \"Complicated\" in 2002? ","Shania Twain","Avril Lavigne","Joni Mitchell","Sarah McLachlan","Avril Lavigne","“Complicated” song is From Avril Lavigne\'s album \'Let Go\'.","avril_lavigne_39.png",6);

//39 Plain White T's
//Found
            questionRepositories.questionCreator("What is the correct name of this 2007 chart topping band? ","Plain White C's","Plain White B's","Plain White T's","Plain White D's","Plain White T's","Plain White T\'s is best known for the number-one hit song \'Hey There Delilah\'.","plain_white_t_s_40.png",6);

//40 Hips Don't Lie
//Found
            questionRepositories.questionCreator("2006: What popular song by Shakira hit the number #1 spot in over 50 different countries?","Hips Don't Lie","Hey You","How Do You Do","Higher Ground","Hips Don't Lie","Shakira created this hit song in 2006 with the help of another musical performer named Wyclef Jean. \"Hips Don\'t Lie\" was first released on Shakira\'s album \"Oral Fixation\".","hips_don_t_lie_41.png",6);

//41 Drums
//Found
            questionRepositories.questionCreator("Taylor Hawkins plays which instrument in the \"Foo Fighters\"? ","Keyboards","Guitar","Drums","Bass Guitar","Drums","Hawkins was the touring drummer for Alanis Morissette on her Jagged Little Pill and Can\'t Not Tours.","drums_42.png",6);

//42 Fats Domino
//Found
            questionRepositories.questionCreator("Who released a rock n\' roll version of the song \"Blueberry Hill\", which reached number two on the Billboard Top 40 chart in 1956? `","Little Richard","Ray Charles","Chuck Berry","Fats Domino","Fats Domino","Antoine \"Fats\" Domino Jr. was an American pianist and singer-songwriter. One of the pioneers of rock and roll music, Domino sold more than 65 million records.","fats_domino_43.png",6);

//43 Jay Z
//Found
            questionRepositories.questionCreator("Linkin Park teamed up with which Rap star to record the song \"Numb/Encore\"? ","Snoop Dogg","Jay Z","Ludacris","Nelly","Jay Z","Numb/Encore is from the 2004 album Collision Course.","jay_z_44.png",6);

//44 Touch
//Found
            questionRepositories.questionCreator("Released in 2006, complete the title to this Cascada number one hit - \"Everytime We...\"? ","Kiss","Smile","Touch","Meet","Touch","Everytime we touch song is from their debut album Everytime We Touch.","touch_45.png",6);

//45 Cello
//Found
            questionRepositories.questionCreator("Born in Oxford, England, \"Jacqueline du Pre\" was famous for the playing of which stringed instrument? ","Guitar","Cello","Violin","Double Bass","Cello","Jacqueline\'s career was cut short by multiple sclerosis at the age of 27.","cello_46.png",6);

//46 Sheryl Crow
//Found
            questionRepositories.questionCreator("Which American singer-songwriter won the 1994 Grammy for Record of the Year for with the song \"All I Wanna Do\"? ","Carly Simon","Madonna","Sheryl Crow","Dolly Parton","Sheryl Crow","\'All I Wanna Do\' song peaked at number two on the Billboard Hot 100.","sheryl_crow_47.png",6);

//47 All Saints
//Found
            questionRepositories.questionCreator("Sisters Nicole and Natalie Appleton are members of which all-girl group? ","Sugababes","Girls Aloud","Atomic Kitten","All Saints","All Saints","All Saints have sold over 12 million records","all_saints_48.png",6);

//48 Fourteen
//Found
            questionRepositories.questionCreator("Elton John spent how many weeks at the top of the US Charts with his 1997 rewritten version of the song \"Candle in the Wind\"? ","Four","Fourteen","Thirty four","Twenty four","Fourteen","The song was originally written in honor of Marilyn Monroe in 1973.","fourteen_49.png",6);

//49 The Clovers
//Found
            questionRepositories.questionCreator("The songs \"Ting a Ling\", \"Love Potion No. 9\" and \"Nip Sip\" were hits for which vocal group? ","The Clovers","The Platters","The Dominoes","The Cadillacs","The Clovers","The Clovers were one of the most popular acts of the 1950s.","the_clovers_50.png",6);

//50 South Africa
//Found
            questionRepositories.questionCreator("Dave Matthews of the \"Dave Matthews Band\" was born in which country? ","U.S.A.","South Africa","Canada","Germany","South Africa","From 2000 to 2010, the Dave Matthews Band earned more money than any other band in North America.","south_africa_51.png",6);

//51 Stevie Wonder
//Found
            questionRepositories.questionCreator("Released in 1974, and found on his \"Fulfillingness\' First Finale\" album, who recorded the funk song \"Boogie on Reggae Woman\"? ","Stevie Wonder","Marvin Gaye","Al Wilson","Bob Marley","Stevie Wonder","Stevland Hardaway Morris, known by his stage name Stevie Wonder, is an American singer, songwriter, record producer, and multi-instrumentalist.","stevie_wonder_52.png",6);

//52 Michael Nesmith
//Found
            questionRepositories.questionCreator("Who wrote the first hit song \"Different Drum\" for Stone Poneys and their lead-singer Linda Ronstadt? ","Michael Nesmith","Paul Simon","Neil Diamond","Bob Dylan","Michael Nesmith","The song reached number 13 on the Billboard Hot 100 in 1967.","michael_nesmith_53.png",6);

//53 Bleach
//Found
            questionRepositories.questionCreator("Released in June of 1989, what was the name of Nirvana\'s debut album? ","Nevermind","Smells Like Teen Spirit","Incesticide","Bleach","Bleach","Bleach has had sales of over 1.7 million units in the United States.","bleach_54.png",6);

//54 Say it Right
//Found
            questionRepositories.questionCreator("2006: What worldwide hit by Nelly Furtado was #1 on over 20 different music charts?","Say it again","Say it","Say it all","Say it Right","Say it Right","Nelly Furtado\'s song \"Say It Right\" was released in the United States on October 31st, 2006. The song \"Say It Right\" featured backup singers named Timothy Mills and Nate Hills.","say_it_right_55.png",6);

//55 Road to Forever
//Found
            questionRepositories.questionCreator("As of 2013, Don Felder has released two solo albums. Airborne and... ","Fly Higher","Rhythms in the Night","Guitar Man","Road to Forever","Road to Forever","Airborne was released in 1983 and Road to Forever in 2012.","road_to_forever_56.png",6);

//56 The Osmonds
//Found
            questionRepositories.questionCreator("Who had a hit in 1972 with the song \"Down by the Lazy River\"? ","Leif Garrett","The Osmonds","Partridge Family","Jackson Five","The Osmonds","The Osmonds have sold over 102 million records worldwide.","the_osmonds_57.png",6);

//57 Quincy Jones
//Found
            questionRepositories.questionCreator("Who conducted and produced the charity single \"We Are the World\"? ","Michael Jackson","Lionel Ritchie","Quincy Jones","Bruce Springsteen","Quincy Jones","The song was written by Michael Jackson and Lionel Richie.","quincy_jones_58.png",6);

//58 Vince Guaraldi
//Found
            questionRepositories.questionCreator("Which of these jazz composers penned, among other things, the well-known \"Charlie Brown Theme\"? ","David Benoit","Antonin Scarlatti","Vince Guaraldi","Eddie Daniels","Vince Guaraldi","Vince served as an Army cook in the Korean War.","vince_guaraldi_59.png",6);

//59 They all hit number six
//Found
            questionRepositories.questionCreator("What is unusual about Seals and Crofts\' hits \"Summer Breeze\", \"Diamond Girl\" and \"Get Closer\"? ","They were all hits on April Fool's Day","They were all hits in the same year","They all had backing vocals by John Denver","They all hit number six","They all hit number six","Seals and Crofts were an American soft rock duo made up of James \"Jim\" Seals and Darrell \"Dash\" Crofts. They are best known for their Hot 100 No. 6 hits \"Summer Breeze\", \"Diamond Girl\", and \"Get Closer\".","they_all_hit_number_six_60.png",6);

//60 Sound of Silence
//Found
            questionRepositories.questionCreator("What Simon and Garfunkel hit song opens with the line \"Hello darkness my old friend\"? ","The Boxer","Homeward Bound","Sound of Silence","Mrs. Robinson","Sound of Silence","Released in 1965, Sound of Silence was the duo\'s second most popular hit after the song \"Bridge Over Troubled Water\".","sound_of_silence_61.png",6);

//61 Trouble in Paradise
//Found
            questionRepositories.questionCreator("Which of the following songs was not a hit for \"The Everly Brothers\"? ","When Will I Be Loved","All I Have to Do Is Dream","Trouble in Paradise","Wake Up Little Susie","Trouble in Paradise","Trouble in Paradise, The Crests recorded \'Trouble in Paradise\' in 1960.","trouble_in_paradise_62.png",6);

//62 George Gershwin
//Found
            questionRepositories.questionCreator("Who composed the timeless hit \"Fascinating Rhythm\"? ","George Gershwin","Lionel Hampton","Count Basie","Benny Goodman","George Gershwin","Fascinating Rhythm Written in 1924, the song was first heard in the Broadway musical \'Lady Be Good\'.","george_gershwin_63.png",6);

//63 Kryptonite
//Found
            questionRepositories.questionCreator("What song is this from? \"If I go crazy then will you still Call me Superman If I\'m alive and well, will you be There a-holding my hand I\'ll keep you by my side With my superhuman might Kryptonite\"","Call me Superman","I’ll keep you by my Side","Kryptonite","My superhuman might Kryptonite","Kryptonite","The song, \"Kryptonite\", is sung by 3 Doors Down and was released on November 21, 2000.","kryptonite_64.png",6);

//64 Sammy Johns
//Found
            questionRepositories.questionCreator("Written in 1973, who was the one-hit wonder who recorded \"Chevy Van\"? ","Sammy Johns","Glen Campbell","Ricky Nelson","Starland Vocal Band","Sammy Johns","The song reached number five on the Billboard Hot 100 chart in 1975.","sammy_johns_65.png",6);

//65 Roar
//Found
            questionRepositories.questionCreator("Katy Perry has been announced as one of the live performers for the 2014 Grammy Awards. Additionally, she has been nominated for Song of the Year. Which song? ","Dark Horse","unconditionally","Part of me","Roar","Roar","Roar can be found on Katy\'s album, Prism.","roar_66.png",6);

//66 Miley Cyrus
//Found
            questionRepositories.questionCreator("Co-written and produced by Oren Yoel, who recorded the hit ballad \"Adore You\"? ","Taylor Swift","Beyonce Knowles","Miley Cyrus","Selena Gomez","Miley Cyrus","The song “Adore you” can be found on Cyrus\' number-one hit album \'Bangerz\'.","miley_cyrus_67.png",6);

//67 XO
//Found
            questionRepositories.questionCreator("Recorded by Beyonce Knowles and found on her fifth studio album \"Beyonce,\" what single features an audio sample from the Space Shuttle Challenger disaster? ","Drunk in Love","Flawless","Pretty Hurts","XO","XO","The audio sample\'s inclusion has been heavily criticized by the families of the lost crew and the media.","xo_68.png",6);

//68 Lorde
//Found
            questionRepositories.questionCreator("\"Royals\" has been nominated for Song of the Year at the upcoming 2014 Grammy Awards. Who recorded the song? ","Ariana Grande","Ellie Goulding","Lorde","Lana Del Rey","Lorde","Royals song can be found on Lorde\'s debut studio album, \'Pure Heroine\'.","lorde_69.png",6);

//69 Young Girls
//Found
            questionRepositories.questionCreator("Which song by Bruno Mars opens with the lyrics, \"I spend all my money on a big ol\' fancy car for these bright-eyed honeys. Oh yeah, you know who you are.\"? ","Gorillas","Young Girls","Moonshine","Treasure","Young Girls","Young Girls is from Mars\' second studio album \'Unorthodox Jukebox\'.","young_girls_70.png",6);

//70 Zendaya
//Found
            questionRepositories.questionCreator("Riding on the success of her pop hit single \"Replay,\" who recently completed her first \"Swag It Out\" concert tour? ","Debby Ryan","Zendaya","Stefanie Scott","Bella Thorne","Zendaya","As of January 2014, \'Replay\' has been certified gold for selling 500,000 copies in the United States.","zendaya_71.png",6);

//71 Story of my Life
//Found
            questionRepositories.questionCreator("Which of the following hit songs was written by Jamie Scott, Caolan Dooley, John Ryan, and the British-Irish boy band, One Direction? ","Hold On, We're Going Home","Stay The Night ","Drunk in Love","Story of my Life","Story of my Life","Released by One Direction, Story of my Life is the fifth track on the album \'Now 49\'.","story_of_my_life_72.png",6);

//72 Pompeii
//Found
            questionRepositories.questionCreator("What hit song by Bastille starts with the lyrics \"I was left to my own devices. Many days fell away with nothing to show.\"? ","Of the Night","Laura Palmer","Pompeii","Things We Lost in the Fire","Pompeii","Pompeii has been nominated for British Single of the Year at the 2014 BRIT Awards.","pompeii_73.png",6);

//73 Rihanna
//Found
            questionRepositories.questionCreator("Eminem and which star have a Billboard 100 hit with the song \"The Monster\"? ","Ke$ha","Lady Gaga","Miley Cyrus","Rihanna","Rihanna","\'The Monster\' marks the fourth collaboration among Eminem and Rihanna.","rihanna_74.png",6);

//74 Say Something
//Found
            questionRepositories.questionCreator("Which of the following songs was recorded by A Great Big World and Christina Aguilera? ","Counting Stars ","White Walls","Let It Go ","Say Something","Say Something","The song was originally released in 2011 without Christina Aguilera.","say_something_75.png",6);

//75 Wake Me Up
//Found
            questionRepositories.questionCreator("Peaking at number one in 22 countries to date, Avicii introduced which of the following songs for the first time at the Ultra Music Festival in Miami? ","Wake Me Up","You Make Me","Hey Brother","We Write the Story","Wake Me Up","The song can be found on Avicii\'s debut studio album, \'True\'.","wake_me_up_76.png",6);

//76 Piano
//Found
            questionRepositories.questionCreator("Achieving worldwide success in 2007 with the hit single \"Love Song,\" and currently in the charts with the song \"Brave,\" what instrument does Sara Bareilles play? ","Piano","Harp","Guitar","Violin","Piano","Sara has been nominated for a Grammy Award five times.","piano_77.png",6);

//77 Show Me
//Found
            questionRepositories.questionCreator("Recorded by American hip hop artist Kid Ink, which song features a hook and bridge by Chris Brown? ","Show Me","Iz U Down","Money and the Power","Bad Ass","Show Me","On January 7, 2014, Kid Ink made his television debut performing \"Show Me\" on Conan.","show_me_78.png",6);

//78 Sweater Weather
//Found
            questionRepositories.questionCreator("What hit by \"The Neighbourhood\" opens with the lyrics, \"All I am is a man - I want the world in my hands - I hate the beach - But I stand.\"? ","Sweater Weather","I Love You","Female Robbery","Afraid","Sweater Weather","Sweater Weather was the lead single from the Neighbourhood\'s debut studio album, \'I Love You\'.","sweater_weather_79.png",6);

//79 Lady Gaga
//Found
            questionRepositories.questionCreator("Slated to be released on January 1st, 2014, but delayed until March, which pop icon will release the album \"Cheek to Cheek\" with jazz singer Tony Bennett? ","Lady Gaga","Madonna","Britney Spears","Katy Perry","Lady Gaga","Cheek to cheek album is Lady Gaga\'s second collaboration with Tony, the first is a duet of song \'The Lady is a Tramp\'.","lady_gaga_80.png",6);

//80 The 20/20 Experience
//Found
            questionRepositories.questionCreator("What is the name of the album released by Justin Timberlake in March 2013? ","The 20/20 Experience","Goldenheart","Contrast","Vessel","The 20/20 Experience","Justin Randall Timberlake (born January 31, 1981) is an American singer-songwriter, actor, dancer, and record producer.","the_20_20_experience_81.png",6);

//81 Twenty-One Pilots
//Found
            questionRepositories.questionCreator("Which band released an album titled \"Vessel\" in 2013? ","Blink one hundred eighty two","Savage Garden","Savages","Twenty-One Pilots","Twenty-One Pilots","Twenty-One Pilots is a band formed in 2009 in Ohio.","twenty_one_pilots_82.png",6);

//82 Black Flag
//Found
            questionRepositories.questionCreator("Which of these bands officially reformed in January 2013? ","Green Day","Black Flag","Boston","Linkin Park","Black Flag","The band was originally formed in 1976. They broke up in 1986.","black_flag_83.png",6);

//83 Fight or Flight
//Found
            questionRepositories.questionCreator("Which of these bands was formed in 2013? ","Fight or Flight","Attack Attack","Lifehouse","Ghost Observatory","Fight or Flight","The band\'s debut single was released on the 21st of May 2013.","fight_or_flight_84.png",6);

//84 Wofmother
//Found
            questionRepositories.questionCreator("During 2013, which of these bands was on hiatus? ","The Replacements","The Mars Volta","Green Day","Wofmother","Wofmother","Wolfmother has been together since 2000.","wofmother_85.png",6);

//85 Animal
//Found
            questionRepositories.questionCreator("Which of these songs is on the album \"Contrast\" by Conor Maynard? ","The Way","Shut Up","NoNoNo","Animal","Animal","Animal song is the first song on the album.","animal_86.png",6);

//86 ASAP
//Found
            questionRepositories.questionCreator("Who recorded the album Long. Live.? ","Muse","ASAP","Dawn Richard","Bad Religion","ASAP","The album was released in January of 2013.","asap_87.png",6);

//87 True North
//Found
            questionRepositories.questionCreator("The phrase \"Unrepentant vagabond\" is the opening line of which of the following songs? ","True North","Troublemaker","Stutter","All Fired Up","True North","The song appears on the album True North by Bad Religion.","true_north_88.png",6);

//88 Chasing the Saturdays
//Found
            questionRepositories.questionCreator("Which of the following albums was released by \"The Saturdays\"? ","Chasing Monday","Finding the Saturdays","Waiting for Sunday","Chasing the Saturdays","Chasing the Saturdays","Chasing the Saturdays Released in 2013, it is their second album.","chasing_the_saturdays_89.png",6);

//89 Be a Man
//Found
            questionRepositories.questionCreator("Which of these songs appears on the album \"It Happens All the Time\" by Megan Hilty? ","Heat","The Next Day","Love is Lost","Be a Man","Be a Man","Be a Man song has three minutes and thirty-four seconds in length, the song is the album\'s second track.","be_a_man_90.png",6);

//90 All Around the World
//Found
            questionRepositories.questionCreator("\"MB... all around the world... Beautiful girl, girl, girl. Uh, girl.\" First line of what Justin Bieber song? ","The Way","Crash","Miss You More","All Around the World","All Around the World","This song appears on the album All Around the World, which was released in 2013.","all_around_the_world_91.png",6);

//91 Paramore
//Found
            questionRepositories.questionCreator("The songs \"Now\", \"Grow\" and \"Last Hope\" can be found on which of the following albums? ","Right Place Right Time","Willpower","Paramore","Save Rock and Roll","Paramore","The album was put out by the pop band Paramore.","paramore_92.png",6);

//92 Justin Bieber
//Found
            questionRepositories.questionCreator("Who sings with Will.i.am on his song #thatpower? ","Dido","Jessica Sanchez","Justin Bieber","Wing","Justin Bieber","The song was released in April, 2013.","justin_bieber_93.png",6);

//93 Me, You and the Music
//Found
            questionRepositories.questionCreator("What was the name of the album that Jessica Sanchez released in 2013? ","The Hands That Thieve","Life on a Rock","Wake Up","Me, You and the Music","Me, You and the Music","This was Jessica\'s first studio album.","me_you_and_the_music_94.png",6);

//94 Microphone
//Found
            questionRepositories.questionCreator("What is the first song on the album 2.0 by 98 Degrees? ","Microphone","Without the Love","Rootless","Get To Me","Microphone","The album was released in 2013.","microphone_95.png",6);

//95 Demi Lovato
//Found
            questionRepositories.questionCreator("Who recorded the song \"Heart Attack\" in 2013? ","Bad Rabbits","Little Mix","Marianas Trench","Demi Lovato","Demi Lovato","The song appears on the album Demi.","demi_lovato_96.png",6);

//96 DNA
//Found
            questionRepositories.questionCreator("\"Does he tell you he loves you when you least expect it?\" The first line of which song? ","DNA","Run","Last Hope","Close Your Eyes","DNA","The song appears on the album DNA along with the song Wings.","dna_97.png",6);

//97 Christina Aguilera
//Found
            questionRepositories.questionCreator("In 1999, who had a number one hit on the Billboard Hot 100 with \"Genie In A Bottle\"?","Christina Aguilera","Christina Perri","Rihanna","Celine Dion","Christina Aguilera","\"Genie In A Bottle \" was the only number one in the 09s by Christina Aguilera. The others all had number ones on the Hot 100 in 1999: \"Have You Ever\" (Brandy); \"Believe\" (Cher); and \"Angel Of Mine\" (Monica).","christina_aguilera_98.png",6);

//98 Two u
//Found
            questionRepositories.questionCreator("Sinead O\'Connor sang \"Nothing Compares\", but what was the official spelling of the remainder of that song title?","Two u","You and me","I love you","You and i","Two u","Some might say her abbreviation was ahead of its time, coming before the popular use of SMS text messages, internet chats and twitter. \"Nothing Compares 2 U\" charted at number three on the US Billboard list in 1990, behind Roxette\'s \"It Must Have Been Love\" (number two) and Wilson Phillips\' \"Hold On\" (number one).","two_u_99.png",6);

//99 It Must Have Been Love
//Found
            questionRepositories.questionCreator("Spending a total of seventeen weeks in the Billboard Top 40, which Roxette song was also on the soundtrack for the movie \"Pretty Woman\"?","It must love","Be love","It must be Love","It Must Have Been Love","It Must Have Been Love","Roxette consisted of Marie Fredriksson and Per Gessle. \"It Must Have Been Love (Christmas for the Broken Hearted)\" was on Swedish radio during 1987, however it was rewritten in 1990 (without the Christmas lyrics) and was included on the \"Pretty Woman\" soundtrack.","it_must_have_been_love_100.png",6);

//100 Deee-Lite
//Found
            questionRepositories.questionCreator("In 1990, which American dance group had an international hit with the song \"Groove Is in the Heart\"?","The Power","Deee-Lite","Day Lite","Black Box","Deee-Lite","The New York City band Deee-Lite achieved international stardom in late 1990 with the single \"Groove Is in the Heart.\" 103. In 1994, Nicki French released the Billboard Top 100 smash \"Total Eclipse of the Heart\".","deee_lite_101.png",6);

//101 Madonna
//Found
            questionRepositories.questionCreator("\"All you need is your own imagination/ so use it that\'s what it\'s for.\" Who sang these lyrics?","Beyonce","Celine Dion","Madonna","Whitney Houston","Madonna","Madonna\'s \"Vogue\" shot to number one in the UK singles chart in April 1990, commanding the top spot for four weeks. Along with Madonna, all of the other artists listed also reached the coveted number one spot that year.","madonna_102.png",6);

//102 Jason Mraz
//Found
            questionRepositories.questionCreator("Which artist recorded the song \"The Remedy?\"","Jason Mars","Jason Mraz","Jason Maz","Jesson Mraz","Jason Mraz","\"The Remedy\" was from Jason\'s first album, \"Waiting for my Rocket to Come.\" This was the first single released from this album.","jason_mraz_103.png",6);
            //TODO END OF MUSIC
        }



    }


}
