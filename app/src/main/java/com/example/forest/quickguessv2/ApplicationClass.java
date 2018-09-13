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
        questionCategoryRepositories.categoryCreator("people","All about");
        questionCategoryRepositories.categoryCreator("plants","All about");
        questionCategoryRepositories.categoryCreator("animals","All about");
        questionCategoryRepositories.categoryCreator("geography","All abontrolout");
        questionCategoryRepositories.categoryCreator("sports","All about");
        questionCategoryRepositories.categoryCreator("music","All about");
        questionCategoryRepositories.categoryCreator("technology","All about");
        questionCategoryRepositories.categoryCreator("entertainment","All about");

    }

    private void insertQuestions()
    {
        //insert questions
        if (DB.getInstance(this).questionsDao().countQuestion() == 0)
        {
            /*Animals*/
            questionRepositories.questionCreator("Which of following creatures has the power to grow lost parts?","Crab","Starfish","Squirrel","Squid","Starfish","Some species of starfish have the ability to regenerate lost arms and can regrow an entire new limb given time. A few can regrow a complete new disc from a single arm, while others need at least part of the central disc to be attached to the detached part.","1_starfish.png",3);

            questionRepositories.questionCreator("Which is the most primitive living mammal?","Seal","Duck-billed platypus","Weasel","Hedgehog","Duck-billed platypus","Egg-laying Mammals There are only five living monotreme species: the duck-billed platypus and four species of echidna (also known as spiny anteaters). In some ways, monotremes are very primitive for mammals because, like reptiles and birds, they lay eggs rather than having live birth.","2_duck_billed_platypus.png",3);

            questionRepositories.questionCreator("Which of the following creatures has the most toxic venom?","Kukri snake","Krait","Scorpion","Cobra","Krait","Its venom is highly haemotoxic. The Saw-scaled Viper is responsible for more human deaths in Asia that all the other venomous Asian snakes combined. Its highly haemotoxic venom is said to be 5 times more toxic than cobras and 16 more toxic than the Russell\'s Viper.","3_krait.png",3);

            questionRepositories.questionCreator("Which animal is the largest in size?","African elephant","Blue whale","Giraffe","Killer whale","Blue whale","Blue whales are the largest animals ever known to have lived on Earth. These magnificent marine mammals rule the oceans at up to 100 feet long and upwards of 200 tons. Their tongues alone can weigh as much as an elephant. Their hearts is as much as an automobile.","4_blue_whale.png",3);

            questionRepositories.questionCreator("Which living being has the heaviest brain?","African elephant","Killer whale","Sea cow","Sperm whale","Sperm whale","The average adult human brain weighs about 3 pounds - the same weight as the average brain of a dolphin (which is also a very intelligent animal). But there are animals with larger brains that are not considered to be as intelligent as a dolphin. For instance, a sperm whale has a brain that weighs about 17 pounds.","5_sperm_whale.png",3);

            questionRepositories.questionCreator("Which is the mythical being believed to have become extinct?","Narwhal","Unicorn","Nautilus","Dodo","Nautilus","According to fossil records, animals similar to the chambered nautilus have existed for about 500 million years. Although no regulations currently exist to protect them, the six living species of chambered nautilus appear to be in decline.","6_nautilus.png",3);

            questionRepositories.questionCreator("Which living being is a skilled engineer?","Tailor-bird","Beaver","Termite","Honey bee","Beaver","Beavers have long been recognized as the engineers of the forest, constantly reshaping their surroundings","7_beaver.png",3);

            questionRepositories.questionCreator("Which living being whistle?","Whale","Dolphin","Shark","Bat","Bat","Whistling Like a Bat: Development of an Ultrasonic Whistle to Deter Bats from Wind Turbines. The whistles will produce sounds mimicking the spectrotemporal patterns of bat echolocation pulses, thereby enhancing the bats\' ability to detect, localize and avoid the moving blades","8_bat.png",3);

            questionRepositories.questionCreator("Which of the following living being has also been found to be a tool-user?","Sea otter","Gorilla","Beaver","Spider","Sea otter","Sea otters are known for their ability to use stones as anvils or hammers to facilitate access to hard-to-reach prey items","9_sea_otter.png",3);

            questionRepositories.questionCreator("Which category creatures contain a type that can fly?","Cats","Lizards","Hedgehogs","Rats","Lizards","Draco, also known as flying lizard or flying dragon, is a reptile that belongs to the family Agamidae. There are 31 species of flying lizards that can be found in South and Southeast Asia (Philippines, Borneo, India, Malaysia, Indonesia…). Flying lizards live in tropical rainforests.","10_lizards.png",3);

            questionRepositories.questionCreator("Which living being has, on an average, the highest life-span?","Tortoise","Man","Pelican","Cat","Tortoise","Tortoises generally have one of the longest lifespans of any animal, and some individuals are known to have lived longer than 150 years. Because of this, they symbolize longevity in some cultures, such as China.","11_tortoise.png",3);

            questionRepositories.questionCreator("Where are zebra found?","Africa","South America","China","New Zealand","Africa","Plains zebra are found on the savannas from Sudan to northern Zimbabwe in eastern Africa. Grevy\'s zebras are now mostly restricted to parts of northern Kenya. Mountain zebras occur in southwestern Africa with cape mountain zebras in South Africa and Hartmann\'s mountain zebras in Namibia and Angola.","12_africa.png",3);

            questionRepositories.questionCreator("Which of the following animals is on the verge of extinction?","Badger","Kangaroo","Gibbon","Great Indian rhinoceros","Great Indian rhinoceros","There are about 2,600 Indian Rhino left in the wild, but their numbers were less than 200 early in the 20th century. This demise was mainly caused by poaching and habitat loss. There recovery is one of two success stories in rhino conservation, the other being the Southern White Rhino.","13_great_indian_rhinoceros.png",3);

            questionRepositories.questionCreator("Which has longest gestation period among mammals?","Giant panda","Pronghorn","Prairie dog","Asiatic elephant","Asiatic elephant","The largest of all land animals have the longest gestation period of all living mammals. Yes, the gestation period of elephants lasts for 18 to 22 months. For an African elephant, the gestation period is 22 months where the Asian elephant\'s is between 18 and 22 months.","14_asiatic_elephant.png",3);

            questionRepositories.questionCreator("Which animal can cross-breed with wolf?","Domestic dog","Dhole","Prairie dog","Fox","Domestic dog","Wolves and dogs are interfertile meaning they can breed and produce viable offspring. In other words, wolves can interbreed with any type of dog, and their offspring are capable of producing offspring themselves.","15_domestic_dog.png",3);

            questionRepositories.questionCreator("Where are orangutans found?","Italy","South America","North Africa","Borneo","Borneo","The orangutans are three extant species of great apes native to Indonesia and Malaysia. Orangutans are currently only found in the rainforests of Borneo and Sumatra. Classified in the genus Pongo, orangutans were originally considered to be one species.","16_borneo.png",3);

            questionRepositories.questionCreator("Which is the slowest moving animal?","Polar bear","Hippopotamus","Elephant seal","Three-toed sloth","Three-toed sloth","The Slowest Mammal in the World. Three-toed sloths are some of the slowest and seemingly laziest creatures in the world. Instead of evolving to eat more, they evolved to do less.","17_three_toed_sloth.png",3);

            questionRepositories.questionCreator("Where are chimpanzees found?","Africa","South America","India","Afghanistan","Africa","Together with humans, gorillas, and orangutans they are part of the family Hominidae (the great apes). Native to sub-Saharan Africa, common chimpanzees and bonobos are currently both found in the Congo jungle, while only the common chimpanzee is also found further north in West Africa.","18_africa.png",3);

            questionRepositories.questionCreator("Which is the world largest and rarest lizard?","Chameleon","Komodo dragon","Regal-horned lizard","Garden lizard","Komodo dragon","The Komodo dragon is the largest living lizard in the world. Komodo dragons are limited to a few Indonesian islands of the Lesser Sunda group, including Rintja, Padar and Flores, and of course the island of Komodo, the largest at 22 miles (35 kilometers) long.","19_komodo_dragon.png",3);

            questionRepositories.questionCreator("Where are giants anteaters found?","North America","South Africa","South America","Japan","South America","Wild giant anteaters live in grasslands, deciduous forests and rain forests of South and Central America. Though most common in South America, they can be found anywhere from the southern tip of Mexico through Central and South America","20_south_america.png",3);

            questionRepositories.questionCreator("Which of the following animal\'s teeth are strong enough to fell a tree?","Squirrel","Beaver","Chipmunk","Shrew","Beaver","Even without brushing their teeth or drinking fluoridated water, beavers have remarkably strong teeth good for gnawing on wood. A new study shows that their tough teeth are all thanks to a key component built into their chemical structure, and its iron.","21_beaver.png",3);

            questionRepositories.questionCreator("Where are aardvarks found?","Africa","Australia","Denmark","Ireland","Africa","Aardvarks are found in sub-Saharan Africa, where suitable habitat and food is available. They spend the daylight hours in dark underground burrows to avoid the heat of the day.","22_africa.png",3);

            questionRepositories.questionCreator("Which rodent is found in remote desert areas, in shifting sand dunes and extreme temperature?","Jerboa","Capybara","Hamster","Murree vole","Jerboa","Jerboas are hopping desert rodents found throughout Arabia, Northern Africa and Asia east to northern China and Manchuria. They tend to live in hot deserts.","23_jerboa.png",3);

            questionRepositories.questionCreator("Which animal female curls up around its baby to protect it from any attacking animal?","Tortoise","Armadillo","pangolin","hedgehog","pangolin","Pangolin also known as scaly anteaters because of their appearance, Pangolin Female curl up its baby to protect it from enemy","24_pangolin.png",3);

            questionRepositories.questionCreator("Where are skunks found?","North America","South America","Africa","Europe","North America","Striped skunks are native to North America, and can be found in Northern Mexico, throughout the United States, and as far north as Central Canada. Other species of skunks, such as the spotted skunk and the hog-nosed skunk, can be found further south, ranging from Canada to Central and South America.","25_north_america.png",3);

            questionRepositories.questionCreator("Which of the following animals is the fastest burrower?","Mole","Aardvark","Kangaroo rat","Prairie dog","Aardvark","Aardvarks are not fast runners but they can quickly dig a defensive burrow. The aardvark\'s tail is thick and strong and they will use it as a club. Their sharp claws are formidable weapons, and if caught in the open, the aardvark will roll on its back to engage all four feet in the fight.","26_aardvark.png",3);

            questionRepositories.questionCreator("Where are badgers found?","Australia","South Africa","Europe","Iraq","Europe","Badgers are found in much of North America, Ireland, Great Britain and most of the rest of Europe as far north as southern Scandinavia. They live as far east as Japan and China. The Javan ferret-badger lives in Indonesia, and the Bornean ferret-badger lives in Malaysia.","27_europe.png",3);

            questionRepositories.questionCreator("Which is the largest rodent in the world?","Kangaroo","Capybara","Rabbit","Squirrel","Capybara","The capybara is a mammal native to South America. It is the largest living rodent in the world","28_capybara.png",3);

            questionRepositories.questionCreator("Where is koala bears found?","North America","Australia","Africa","South Africa","Australia","Koalas do not live in rainforests or desert areas. They live in the tall eucalypt forests and low eucalypt woodlands of mainland eastern Australia, and on some islands off the southern and eastern coasts. Queensland, NSW, Victoria and South Australia are the only states where Koalas are found naturally in the wild.","29_australia.png",3);

            questionRepositories.questionCreator("Which bird was once found in abundance but is today extinct species?","Flightless cormorant","Grey heron","Passenger pigeon","Kakapo","Passenger pigeon","Today only about 40 kakapos survive in the wild on two small islands off the coast of New Zealand\'s South Island. It is because of the hunter.","30_passenger_pigeon.png",3);

            questionRepositories.questionCreator("Where is rhea found?","North America","China","Ireland","South America","South America","This ratite is the largest bird in the Americas. It is a fast runner; and when it runs, its neck is almost horizontal to the ground. Rheas congregate in flocks of 20 to 30 birds. The lesser or Darwin\'s rhea, Rhea pennata, is mostly found in the southern part of South America","31_south_america.png",3);

            questionRepositories.questionCreator("Which bird locates its prey by smell?","Woodpecker","Kiwi","Crow","Stonechat","Kiwi","A kiwi\'s olfactory bulb is the second largest among all birds relative to the size of its forebrain, giving it an exceptional sense of smell, just second to the condor. This helps kiwi locate food beneath the soil and in leaf lite","32_kiwi.png",3);

            questionRepositories.questionCreator("Which bird mimics the calls and even songs of other birds?","Koel","Eagle","Woodpecker","Drongo","Drongo","The Drongos are able to mimic the sounds made by many different species that inhabit its desert environment. Drongo, seen here in flight, impersonates the calls of other birds in order to steal food","33_drongo.png",3);

            questionRepositories.questionCreator("Which is the fastest swimming bird?","Gentoo penguin","Adelie penguin","Shelduck","Puffin","Gentoo penguin","The Gentoo penguin (pygoscelis Papua) is the world\'s fastest swimming bird. It can swim between 36-40 km. per hour. They are found in the Antarctic Islands.","34_gentoo_penguin.png",3);

            questionRepositories.questionCreator("Where is toucan found?","China","Australia","South America","England","South America","Toucans are native to the Neotropics, from Southern Mexico, through Central America, into South America south to northern Argentina. They mostly live in the lowland tropics, but the montane species from the genus Andigena reach temperate climates at high altitudes in the Andes and can be found up to the tree line.","35_south_america.png",3);

            questionRepositories.questionCreator("Which birds has flippers instead of wings?","Owl","Penguin","Goose","Albatross","Penguin","While penguins are the only birds that have true flippers, other pelagic birds that spend a good deal of time swimming also have some flipper-like characteristics to their wings. Puffins, murres and auks all have wings that more closely resemble flippers, but to a lesser degree than penguin wings.","36_penguin.png",3);

            questionRepositories.questionCreator("Which bird uses its beak as a filter to gather food from water?","Grey heron","Ibis","Flamingo","Sarus crane","Flamingo","Flamingos are filter feeders. ... Because the flamingo must use its beak in an upside-down manner, the beak has evolved to reflect this. The flamingo\'s top beak functions like the bottom beak of most birds, and vice versa. Flamingos are among the very few animals that are able to move their top jaw while eating","37_flamingo.png",3);

            questionRepositories.questionCreator("Which bird keeps its mouth open while flying so that it can catch flying insects?","Vulture","Nightjar","Owl","Crow","Nightjar","Nightjars are very agile in flight, able to hunt and catch aerial insects such as moths in those huge mouths. They also eat beetles, spiders and various other insects. The shape of a bird\'s beak almost always determines the type of food they will eat.","38_nightjar.png",3);

            questionRepositories.questionCreator("Some birds do not catch their own prey. They steal it from another bird. Which bird is it?","Skua","Sparrow","River tem","Bulbul","Skua","Skuas steal much of their food from terns, puffins, and other birds that are carrying fish or other prizes back to their nests and young. The swashbuckling birds sometimes team up to overwhelm their victims, and they are relentless in chasing down their adversaries.","39_skua.png",3);

            questionRepositories.questionCreator("Which holds the record for longest migration?","Arctic tern","Shearwater","River tern","Bar headed geese","Arctic tern","Canada geese fly in a distinctive V-shaped flight formation, with an altitude of 1 km (3,000 feet) for migration flight. The maximum flight ceiling of Canada geese is unknown, but they have been reported at 9 km (29,000 feet)","40_arctic_tern.png",3);

            questionRepositories.questionCreator("Which bird can travel very long distances without flapping its wings?","Andean condor","Peregrine falcon","Coot","Eagle","Andean condor","Condors can glide over large areas while using little energy. These huge birds are too heavy to fly without help. They use warm air currents (thermals) to help them gain altitude and soar through the sky. By gliding from thermal to thermal, a condor may need to flap its wings only once every hour","41_andean_condor.png",3);

            questionRepositories.questionCreator("Which is heaviest flying bird?","Ostrich","Kori bustard","Vulture","Condor","Kori bustard","The largest (heaviest) flying bird today is the Kori Bustard (Ardeotis kori) of Africa, males weigh about 18kg, females about half that. The largest bird ever to fly were the Teratorns (a type of Condor), the largest of which, Argentavis magnificens, had a wingspan of 3 metres, and weighed 120kg","42_kori_bustard.png",3);

            questionRepositories.questionCreator("Which birds helps in conditioning timber pests?","Indian grey shrike","Woodpecker","Tailor-bird","Hoopoe","Woodpecker","An interesting and familiar group of birds. Their ability to peck into trees in search of food or excavate nest cavities is well known. They prefer snags or partially dead trees for nesting sites, and readily peck holes in trees and wood structures in search of insects beneath the surface","43_woodpecker.png",3);

            questionRepositories.questionCreator("Which bird has the biggest egg?","Tawny owl","Moorhen","Ostrich","Duck","Ostrich","On record weighed 2.589 kg (5 lb. 11.36 oz) and was laid by an ostrich (Struthio camelus) at a farm owned by Kerstin and Gunnar Sahlin (Sweden) in Borlange, Sweden, on 17 May 2008","44_ostrich.png",3);

            questionRepositories.questionCreator("The horns of a goat are made out of what substance?","Insulin","Amylase","Keratin","Progesterone","Keratin","The main composition of horns is keratin, the same material that makes up hair and fingernails. The core of a horn is, however, made of bone.","45_keratin.png",3);

            questionRepositories.questionCreator("The cotton top of tamarin is a small species of what type of animal?","Bear","Goat","Monkey","Sheep","Monkey","The Cotton top Tamarin (Saguinus oedipus), also known as the Pinche Tamarin, is a small New World monkey weighing less than 1lb (0.5 kg). It is an endangered species found in tropical forest edges and secondary forests where it is arboreal and diurnal.","46_monkey.png",3);

            questionRepositories.questionCreator("The bandicoot is native to which country?","Africa","Australia","North America","Cambodia","Australia","There are also a few rare species such as the rabbit-eared bandicoots. Bandicoots are one of the few native mammals to have remained abundant close to the major cities of Australia. In suburban Sydney it is the long-nosed species that can be seen.","47_australia.png",3);

            questionRepositories.questionCreator("Some flamingos are color pink because of what?","Eating shrimps","Eating algae","Eating plankton","Eating lotus","Eating algae","Actually, flamingos are not pink. They are born with grey feathers, which gradually turn pink in the wild because of a natural pink dye called canthaxanthin that they obtain from their diet of brine shrimp and blue-green algae.","48_eating_algae.png",3);

            questionRepositories.questionCreator("A Finnish Spitz is what type of creature?","Dog","Snake","Cat","Bird","Dog","Finnish Spitz is a breed of dog originating in Finland.","49_dog.png",3);

            questionRepositories.questionCreator("What is the common name for the animal of the genus vulpes.","Fox","Wolf","Owl","Dog","Fox","Vulpes is a genus of the Canidae the members of this genus are colloquially referred to as a true foxes, meaning they form a proper clade.","50_fox.png",3);

            questionRepositories.questionCreator("How many hearts does an octopus have?","eight","one","three","six","three","Octopuses have a closed circulatory system, where the blood remains inside blood vessels. Octopuses have three hearts; a systemic heart that circulates blood round the body and two branchial hearts that pump it through each of the two gills","51_three.png",3);

            questionRepositories.questionCreator("What is the color of a polar bears skin?","Black","Pink","White","Brown","Black","The bear\'s stark white coat provides camouflage in surrounding snow and ice. But under their fur, polar bears have black skin the better to soak in the sun\'s warming rays. These powerful predators typically prey on seals.","52_black.png",3);

            questionRepositories.questionCreator("Welsh and Tamworth are breeds of which animal?","Pig","Cow","Sheep","Dog","Pig","Tamworth is a breed of a domestic pig originating in its namesake Tamworth; it is among the oldest of pig breeds.","53_pig.png",3);

            questionRepositories.questionCreator("What is the top speed of a bottle nosed dolphin?","twenty one mph","twenty six mph","thirty one mph","fourty mph","twenty one mph","Bottlenose Dolphins typically swim at a speed of 5-11 kilometers per hour (3-6 mph); for short times, they can reach peak speeds of 35 kilometers per hour (21 mph).","54_twenty_one_mph.png",3);

            questionRepositories.questionCreator("What kind of animal is Egyptian Mau?","Dog","Cat","Snake","Camel","Cat","This, along with the well-known chattiness of the Egyptian Mau, makes them popular pets today. As the Egyptian Mau is the only naturally spotted breed of domestic cat, the Egyptian Mau is often bred with other domestic cats in order to produce slightly spotted kittens","55_cat.png",3);

            questionRepositories.questionCreator("What type of animal is cuscus?","Bird","Mammal","Reptile","Amphibian","Mammal","The cuscus is a large marsupial native to the Northern forest of Australia and the large, tropical island of Papua New Guinea. The cuscus is a subspecies of possum with the cuscus being the largest of the world\'s possum species. The cuscus is an arboreal mammal and spends its life almost exclusively in the trees.","56_mammal.png",3);

            questionRepositories.questionCreator("How many species of bumble bees are there around the world?","twenty-five","one hundred fifty","two hundred fifty","five hundred","two hundred fifty","There are about 25 British species according to Prys-Jones (19 species of Bombus and 6 species of cuckoo bumblebees). And about 65 species in Europe, and 250 species of bumblebee have been discovered so far worldwide","57_two_hundred_fifty.png",3);

            questionRepositories.questionCreator("Which snake is a member of family boa and sometimes called \'water boa\'?","Cobra","Python","Rattle snake","Anaconda","Anaconda","There are about 25 British species according to Prys-Jones (19 species of Bombus and 6 species of cuckoo bumblebees). And about 65 species in Europe, and 250 species of bumblebee have been discovered so far worldwide","58_anaconda.png",3);

            questionRepositories.questionCreator("Which animal do not make any sound?","Koala","Panda","Giraffe","Bear","Giraffe","Science Says This Is the Sound Giraffes Make. It\'s long been assumed that unlike other animals, giraffes are largely silent beasts. They don\'t oink, moo or roar. But new research suggests perhaps giraffes do have a distinct sound: They hum.","59_giraffe.png",3);

            questionRepositories.questionCreator("Which of the following has no skeleton at all?","Star fish","Sponge","Jelly fish","Silver fish","Jelly fish","A jellyfish has no ears or eyes or nose and no brain or heart! They do not even have a head. Their body is almost totally made of water and is soft having no bones at all. Jellyfish are invertebrate animals because they do not have a spine or backbone.","60_jelly_fish.png",3);

            questionRepositories.questionCreator("Which animal can create the loudest sound among any living creature?","Whale shark","Gibbon","Humpback whale","Howler monkey","Humpback whale","Produce one hundred eighty eight decibels of sound. That\'s louder than a one hundred fifty decibel rock concert, which can damage hearing.","61_humpback_whale.png",3);

            questionRepositories.questionCreator("Which of the following is not a true fish?","Silver fish","Saw fish","Hammer fish","Sucker fish","Sucker fish","Sawfishes, also known as carpenter sharks, are a family of rays characterized by a long, narrow, flattened rostrum, or nose extension, lined with sharp transverse teeth, arranged in a way that resembles a saw","62_sucker_fish.png",3);

            questionRepositories.questionCreator("In which of the following kinds of organism is the phenomenon found wherein the female kills the male after copulation?","Dragonfly","Honeybee","Spider","Pit viper","Honeybee","The next male honey bee to mate with the queen will remove the previous endophallus and eventually lose his own after ejaculation. Male honey bees are only able to mate seven to 10 times during a mating flight, and after mating, a drone dies quickly, as his abdomen rips open when his endophallus is removed","63_honeybee.png",3);

            questionRepositories.questionCreator("Pashmina shawl is made from hair of?","Sheep","Goat","Rabbit","Yak","Goat","Pashmina refers to a type of cashmere wool and textiles made from it. The name comes from Pashmineh, made from Persian pashm (= \"wool\"). This wool comes from changthangi or pashmina goat—a special breed of goat indigenous to high altitudes of the Himalayas","64_goat.png",3);

            questionRepositories.questionCreator("In which of the following animals is respiration done by skin?","Flying fish","Sea horse","Frog","Chameleon","Frog","Though they have functional lungs, much of a frog\'s respiration occurs through the skin. A frog\'s moist skin is thin and marbled with blood vessels and capillaries close to the surface. The moisture on the skin dissolves oxygen from the air and water surrounding the frog and transmits it into the blood","65_frog.png",3);

            questionRepositories.questionCreator("Which of the following is the National aquatic animal of India?","Sea turtle","Shark","Dolphin","Dugong","Dolphin","River Dolphin is the National Aquatic Animal of India. This mammal is also said to represent the purity of the holy Ganga as it can only survive in pure and fresh water. Platanista gangetica has a long pointed snout and also have visible teeth in both the upper and lower jaws.","66_dolphin.png",3);

            questionRepositories.questionCreator("The fish that can taste with its whole body is the?","Clown fish","Jelly fish","Catfish","Flying fish","Catfish","That\'s because this creature has taste buds not only in its mouth, but all over its body. Catfish (order Siluriformes), those beady-eyed fish named for their feline-like whiskers; typically have more than 100,000 taste buds. Some large catfish can have as many as 175,000.","67_catfish.png",3);

            questionRepositories.questionCreator("The country in which Yak is found?","Tibet","Africa","South America","Indian","Tibet","The domestic yak (Bos grunniens) is a long-haired domesticated bovid found throughout the Himalayan region of the Indian subcontinent, the Tibetan Plateau and as far north as Mongolia and Russia. It is descended from the wild yak (Bos mutus)","68_tibet.png",3);

            questionRepositories.questionCreator("How many legs does a butterfly have?","two","four","six","eight","fix","The scales, which are arranged in colorful designs unique to each species, are what give the butterfly its beauty. Like all other insects, butterflies have six legs and three main body parts: head, thorax (chest or mid-section) and abdomen (tail end). They also have two antennae and an exoskeleton","69_fix.png",3);

            questionRepositories.questionCreator("The Bhindawas bird sanctuary is located in which country?","Madhya Pradesh","Bihar","Odisha","Haryana","Haryana","The Bhindawas Bird Sanctuary (BBS) is located in Jhajjar district, Haryana.","70_haryana.png",3);

            questionRepositories.questionCreator("The dolphin is?","Fish","Reptile","Mammal","Turtle","Mammal","Like every mammal, dolphins are warm blooded. Unlike fish, who breathe through gills, dolphins breathe air using lungs. Dolphins must make frequent trips to the surface of the water to catch a breath.","71_mammal.png",3);

            questionRepositories.questionCreator("Which bird can turn head around to look backward?","Toucan","Parrot","Owl","Eagle","Owl","A tawny owl turning its head far around its neck, Owls don\'t need eyes in the back of their heads to see what\'s behind them - they can just swivel their heads all the way around","72_owl.png",3);

            questionRepositories.questionCreator("What kind of animal is a dhole?","Wild cat","Wild ass","Wild dog","Wild buffalo","Wild dog","The dhole is a canid native to Central, South and Southeast Asia. Other English names for the species include Asiatic wild dog, Indian wild dog, whistling dog, red dog, and mountain wolf.","73_wild_dog.png",3);

            questionRepositories.questionCreator("Which is the only bird that can fly backwards?","Sunbird","Kingfisher","Honey eater","Hummingbird","Hummingbird","Hummingbirds are incredible flyers, with the ruby-throated hummingbird beating its wings 80 times every second, an ability that inspired this blog\'s name. These tiny birds can fly forwards, hover, and are the only known birds to fly backwards as well.","74_hummingbird.png",3);

            questionRepositories.questionCreator("Which is the fastest swimming fish?","Dolphin","Sailfish","Catfish","Eel","Sailfish","Not all experts agree, but at top speeds of nearly 70 mph, the sailfish is widely considered the fastest fish in the ocean. Clocked at speeds in excess of 68 mph, some experts consider the sailfish the fastest fish in the world ocean.","75_sailfish.png",3);

            questionRepositories.questionCreator("Which one of the following is not a true snake?","Blind snake","Glass snake","Sea snake","Tree snake","Glass snake","The glass lizards or glass snakes are a genus, Ophisaurus, of reptiles that resemble snakes, but are actually lizards. Although most species have no legs, their head shapes, movable eyelids, and external ear openings identify them as lizards.","76_glass_snake.png",3);

            questionRepositories.questionCreator("Kiwi is the native bird of which of the following countries?","South America","New Zealand","Australia","Zimbabwe","New Zealand","There are two species of Kiwis in New Zealand. Brown Kiwis are found in forested areas in the North Island, Fiord land, South Westland and Stewart Island. Spotted Kiwis are found on offshore islands and forests in the North of the South Island","77_new_zealand.png",3);

            questionRepositories.questionCreator("What is the fastest land animal in the world?","Kangaroo","Cheetah","Wolf","Dear","Cheetah","These graceful animals are identified by their unique black spots on gold or yellow coats and are known for their amazing speed. In fact, according to the Smithsonian National Zoological Park, the cheetah is the world\'s fastest land mammal. A sprinting cheetah can reach 45 mph (72 km/h) within 2.5 seconds","78_cheetah.png",3);

            questionRepositories.questionCreator("A \'doe\' is what kind of animal?","Female yak","Female deer","Female bear","Female camel","Female deer","Deer are a group of even-toed ungulate mammals. They form the family Cervidae. A male deer is called stag or buck, a female deer is called doe, and a young deer is called fawn. There are about 60 species of deer.","79_female_deer.png",3);

            questionRepositories.questionCreator("What is the only continent on earth where Giraffes live in the wild?","North pole","Africa","Australia","South America","Africa","Giraffes can be found in central, eastern and southern Africa. Giraffes live in the savannas of Africa, where they roam freely among the tall trees, arid land, dense forests and open plains.","80_africa.png",3);

            questionRepositories.questionCreator("How many legs does a spider have?","seven","nine","ten","eight","eight","Spiders (order Araneae) are air-breathing arthropods that have eight legs and chelicerae with fangs that inject venom. They are the largest order of arachnids and rank seventh in total species diversity among all other orders of organisms.","81_eight.png",3);

            questionRepositories.questionCreator("What animal is known as the king of the jungle?","Gorilla","Tiger","Elephant","Lion","Lion","Lion is known to be the king of the beast (\"king of the jungle\" would be a mismoner) across most cultures of the world. This is mostly because of lion\'s appearance and partly because of the social structure of a pride and the lion\'s role in the place.","82_lion.png",3);

            questionRepositories.questionCreator("What is largest predator found on the land of Australia?","Dingo","Coyote","Gray wolf","Australian Cattle dog","Dingo","The Dingo is the largest terrestrial predator in Australia.","83_dingo.png",3);

            questionRepositories.questionCreator("The Asian Elephants trunk contains up to how many muscles?","six hundred","six thousand","sixty thousand","ten thousand","sixty thousand","The trunk contains as many as 60,000 muscles, which consist of longitudinal and radiating sets. The longitudinal are mostly superficial and subdivided into anterior, lateral, and posterior","84_sixty_thousand.png",3);

            questionRepositories.questionCreator("The Bichon Frise is a breed of what?","Dog","Cow","Sheep","Goat","Dog","The Bichon Frise is a very cheerful breed, with a happy, sometimes clownish disposition, accented by its tail curled up high on its rump. They were very much in vogue in sixteenth-century France; they displaced the Maltese in court as the favorite.","85_dog.png",3);

            questionRepositories.questionCreator("What type of animal is an Avocet?","Bird","Reptile","Mammal","Amphibian","Bird","The avocet is a type of wading bird that is found across mudflats in the world\'s warmer climates. There are four different species of avocet which are the pied avocet, the American avocet, the Red-necked avocet and the Andean avocet","86_bird.png",3);

            questionRepositories.questionCreator("Which is the largest type of penguin?","Emperor penguin","King penguin","Crested penguin","Gentoo penguin","Emperor penguin","The emperor penguin (Aptenodytes forsteri) is the tallest and heaviest of all living penguin species and is endemic to Antarctica. The male and female are similar in plumage and size, reaching 122 cm (48 in) in height and weighing from 22 to 45 kg (49 to 99 lb)","87_emperor_penguin.png",3);

            questionRepositories.questionCreator("Aardvark is South Africa\'s Afrikaans language which means what?","Land animal","Sea mammal","Ground pig","Monkey eating","Ground pig","Aardvark is the first word in your English dictionary; however the name aardvark is not even English! It comes from South Africa\'s Afrikaans language and means \'earth pig\' or \'ground pig\'.","88_ground_pig.png",3);

            questionRepositories.questionCreator("How long can a hippo hold their breath in the water?","ten minutes","five minutes","one minute","fifteen minutes","five minutes","Their nostrils close, and they can hold their breath for 5 minutes or longer when submerged. Hippo can even underwater, using a reflex that allows them to bob up, take a breath, and sink and back down without waking up.","89_five_minutes.png",3);

            questionRepositories.questionCreator("What mammal can hold their breath in a longest time?","Sperm whale","Dolphin","Blue whale","Human","Sperm whale","Sperm whale makes some of the longest dives achieved by mammals, with some lasting up to 90 minutes, while dolphins and other whales can stay underwater for 20 minutes. The longest time a human has held their breath for under water is 19 minutes set by a Swiss free diver called Peter Colat.","90_sperm_whale.png",3);

            questionRepositories.questionCreator("Which is the smallest living bird?","Bee hummingbird","Sparrow","Pigeon","Parrot","Bee hummingbird","Bee Hummingbirds (Mellisuga helenae) are only 5 to 6 cm long and weigh just 1.6 to 1.9 g (a small coin such as a U.S. penny weighs around 2.5 to 3 g). The male Bee Hummingbird is the smallest of all birds and can easily be mistaken for a bee","91_bee_hummingbird.png",3);

            questionRepositories.questionCreator("What is the largest terrier?","Scottish","Bull","Boston","Airedale","Airedale","Airedale it is traditionally called \"King of Terriers\" because it is the largest of the terrier breeds. The Airedale was bred from the old English black and tan terrier (now extinct).","92_airedale.png",3);

            questionRepositories.questionCreator("What is the world\'s longest poisonous snake?","Tiger snake","Boomslang","King cobra","Eastern brown snake","King cobra","Today\'s longest venomous snakes, king cobra (ophiophagus Hannah), can grow to be about 18 feet (5.5m) long.","93_king_cobra.png",3);

            questionRepositories.questionCreator("Where would find a tuatara?","Africa","New Zealand","Sweden","Australia","New Zealand","This New Zealand native has unique, ancient lineage that goes back to the time of the dinosaurs.","94_new_zealand.png",3);

            questionRepositories.questionCreator("What is a group of bat called?","Colony","Comrade","Companion","Group bat","Colony","A group of bats is called a colony of bats. Within one colony of bats, there may be 10 to 1,000 bats or more. Bats tend to stick together in order to protect each other, to mate and to gather food.","95_colony.png",3);

            questionRepositories.questionCreator("Which animal has the widest hearing range?","Lizard","Dolphin","Hyena","Lion","Dolphin","Bottlenose dolphins hear tones with a frequency up to 160 kHz with the greatest sensitivity ranging from 40 to 100 kHz. The average hearing for humans is about 0.02 to 20 kHz","96_dolphin.png",3);

            questionRepositories.questionCreator("What kind of animal is a stallion?","Male deer","Male horse","Male zebra","Male donkey","Male horse","Male horse is called a stallion. A stallion used for breeding is known as a stud. Formerly, stallion was employed as riding horses.","97_male_horse.png",3);

            questionRepositories.questionCreator("How many different types of hyenas are there?","six","ten","five","four","four","There are 4 known species of hyena, the spotted hyena, the striped hyena, the brown hyena and the aardwolf.","98_four.png",3);

            questionRepositories.questionCreator("In which country are lemurs found in nature?","Madagascar","U.S.A","Finland","New Zealand","Madagascar","Today the black lemur is an endangered species and is found only in a small area on Madagascar and on two small islands off its northwest coast. On one island they have the benefit of a reserve of natural forest.","99_madagascar.png",3);

            questionRepositories.questionCreator("What is the main diet of a mole?","Beetle","Dragonfly","Ants","Earthworms","Earthworms","It is a misconception that moles burrow into gardens to eat the roots of plants. They are actually after the earthworms that are found in garden soil. Moles love earthworms so much that they eat nearly their body weight worth of earthworms per day. Moles also consume insect larvae.","100_earthworms.png",3);
            /*END OF ANIMALS*/


            /*MUSIC*/
            questionRepositories.questionCreator("Presented in February of 2017, who won the Grammy Award for Best New Artist?","Chance the Rapper","The Chainsmokers","Kelsea Ballerini","Maren Morris","Chance the Rapper","Chancelor Jonathan Bennett (born April 16, 1993), known professionally as Chance the Rapper, is an American rapper, singer, songwriter, actor, record producer, and philanthropist from the West Chatham neighborhood of Chicago, Illinois.","1_chance_the_rapper.png",6);

            questionRepositories.questionCreator("Their 20th, what studio album did Deep Purple release on April 1st, 2017?","Abandon","Purpendicular","Rapture of the Deep","Infinite","Infinite","Infinite is the 20th studio album by English rock band Deep Purple, released on 7 April 2017.","2_infinite.png",6);

            questionRepositories.questionCreator("Finish the title to this 2017 Sam Hunt hit song - \"Body Like a \".","Heavenly Goddess","Wooden Toothpick","Bronze God","Back Road","Back Road","The song was written by Sam Hunt, Zach Crowell, Shane McAnally and Josh Osborne.","3_back_road.png",6);

            questionRepositories.questionCreator("What 2017 Billboard Top 100 song starts with the lyric - \"The club isn\'t the best place to find a lover, so the bar is where I go\"?","Tunnel Vision","Castle on the Hill","I Feel it Coming","Shape of You","Shape of You","\"Shape of You\" peaked at number-one on the singles charts of 34 countries, including the US Billboard Hot 100 (later becoming the best performing song of 2017), as well as the UK, Australian and Canadian singles charts.","4_shape_of_you.png",6);

            questionRepositories.questionCreator("Which Canadian rapper had Billboard hits with the songs \"No Long Talk\", \"Get It Together\" and \"Blem\" in 2017?","Madchild","Checkmate","Kardinal Offishall","Drake","Drake","Aubrey Drake Graham (born October 24, 1986) is a Canadian rapper, singer, songwriter, record producer, actor, and entrepreneur. He is one of the most popular entertainers in the world, and one of the best-selling music artists of the 21st century.","5_drake.png",6);

            questionRepositories.questionCreator("On the Billboard Hot Rock Songs chart for March/April 2017, finish the title to this Weezer song - \"Feels Like ...\".","Summer","Winter","Rain","Love","Summer","Summer - The song was released as a single in March of 2017.","6_summer.png",6);

            questionRepositories.questionCreator("Which artist, who died in March of 2017, had his songs \"Maybellene\" and \"You Never Can Tell\" make a reappearance on the charts?","James Brown","Jerry Lee Lewis","Little Richard","Chuck Berry","Chuck Berry","Chuck was one of the first musicians to be inducted into the Rock and Roll Hall of Fame when it made its debut in 1986.","7_chuck_berry.png",6);

            questionRepositories.questionCreator("Boston\'s drummer died on stage while playing the band\'s greatest hits in March of 2017. Name him.","Sib Hashian","Tracy Ferrie","Gary Pihl","Brad Delp","Sib Hashian","Released in 1976, Boston\'s self-titled debut album is one of the bestselling debut albums in U.S. history.","8_sib_hashian.png",6);

            questionRepositories.questionCreator("Released on her fifth studio album, which American singer had a hit with the song \"Million Reasons\"?","Kesha","Katy Perry","Lady Gaga","Christina Aguilera","Lady Gaga","Stefani Joanne Angelina Germanotta, known professionally as Lady Gaga, is an American singer, songwriter, and actress. She is known for her unconventionality and provocative work as well as visual experimentation. Lady Gaga - Found on the album \'Joanne\'.","9_lady_gaga.png",6);

            questionRepositories.questionCreator("New Zealand singer and songwriter Ella Marija Lani Yelich-O\'Connor is better known as?","Kimbra","Charli XCX","Lorde","Lana Del Rey","Lorde","Ella Marija Lani Yelich-O\'Connor, known professionally as Lorde, is a New Zealand singer, songwriter, and record producer. She holds both New Zealand and Croatian citizenship. As of 2017, Lorde has earned two Grammy Awards.","10_lorde.png",6);

            questionRepositories.questionCreator("What Disney film soundtrack peaked at number three on the Billboard 200 chart in March of 2017?","Coco","Cars Three","Ghost in the Shell","Beauty and the Beast","Beauty and the Beast","Beauty and the Beast is a live-action remake of Disney\'s 1991 animated film of the same name.","11_beauty_and_the_beast.png",6);

            questionRepositories.questionCreator("Coinciding with what would have been his 74th birthday, which former Beatle\'s entire solo album collection was released in February of 2017?","Paul McCartney","John Lennon","Ringo Starr","George Harrison","George Harrison","George Harrison MBE was an English guitarist, singer-songwriter, and producer who achieved international fame as the lead guitarist of the Beatles. The boxed set is titled George Harrison - The Vinyl Collection.","12_george_harrison.png",6);

            questionRepositories.questionCreator("Released on the film soundtrack \"Fifty Shades Darker\", who collaborated with Taylor Swift on the song \"I Don\'t Wanna Live Forever\"?","Shawn Mendes","Harry Styles","Zayn Malik","Justin Bieber","Zayn Malik","The song was written by Taylor Swift, Sam Dew and Jack Antonoff.","13_zayn_malik.png",6);

            questionRepositories.questionCreator("Presented posthumously, who won the 2017 Grammy Award for Best Rock Song?","Unspoken – Chuck Loeb","Blackstar – David Bowie","Don't Hurt Yourself – Beyonce","Heathens - Tyler Joseph","Blackstar  David Bowie","Blackstar was released on Bowie\'s final studio album of the same name.","14_blackstar_david_bowie.png",6);

            questionRepositories.questionCreator("Starting his music career as a member of Roxy Music in 1971, who released the album \"Reflection\" on New Year\'s Day, 2017?","Brian Eno","Bono","John Cale","Robert Fripp","Brian Eno","Brian Peter George St John le Baptiste de la Salle Eno, RDI is an English musician, composer, record producer, singer, writer, and visual artist. - Eno is widely regarded as an ambient music pioneer.","15_brian_eno.png",6);

            questionRepositories.questionCreator("Presented in January of 2018, who won the Grammy Award for Album of the Year? ","The Story of O.J. - Jay-Z","Humble - Kendrick Lamar","Redbone - Childish Gambino","Twenty four thousand Magic - Bruno Mars","Twenty four thousand Magic Bruno Mars","Twenty four thousand Magic is the third studio album by American singer and songwriter Bruno Mars.","16_twenty_four_thousand_magic_bruno_mars.png",6);

            questionRepositories.questionCreator("Their 18th, what studio album did Judas Priest release in March of 2018? ","Redeemer of Souls","Angel of Retribution","Nostradamus","Firepower","Firepower","Firepower sold close to 50,000 copies in the United States during its first week of release.","17_firepower.png",6);

            questionRepositories.questionCreator("Finish the title to this 2018 Shawn Mendes hit song - \"In My ...\". ","Backyard","Life","Blood","Car","Blood","The song chronicles Shawn\'s struggle with an anxiety disorder.","18_blood.png",6);

            questionRepositories.questionCreator("What 2018 Billboard Top 100 song starts with the lyric - \"I still see your shadows in my room. Can\'t take back the love that I gave you\"? ","Lucid Dreams","Girls Like You","No Tears Left to Cry","Sad!","Lucid Dreams","Lucid Dreams - Released in March of 2018, Lucid Dreams was recorded by American rapper Juice WRLD.","19_lucid_dreams.png",6);

            questionRepositories.questionCreator("Which Canadian rapper had Billboard hits with the songs \"Nice For What\", \"God\'s Plan\" and \"I\'m Upset\" in 2018? ","Checkmate","Madchild","Drake","Kardinal Offishall","Answer Drake","Drake - As of 2018, Drake holds the record for most charted songs among solo artists in the history of the Billboard Hot 100 at 154.","20_answer_drake.png",6);

            questionRepositories.questionCreator("On the Billboard Hot Rock Songs chart for July 2018, finish the title for this song \"Panic! At The Disco\" song - \"King of the ...\". ","Sun","World","Clouds","Night","Clouds","Panic! at the Disco is an American rock band that formed in 2004.","21_clouds.png",6);

            questionRepositories.questionCreator("Which artist, who died at the age of 46 in January of 2018, was the lead singer for the Cranberries?? ","Dolores O'Riordan","Barbara Cope","Betty Willis","Claudia Fontaine","Dolores O'Riordan","Dolores Mary Eileen O\'Riordan was an Irish musician, singer and songwriter. She led the rock band The Cranberries from 1990 until their break-up in 2003; they reunited in 2009.","22_dolores_o_riordan.png",6);

            questionRepositories.questionCreator("Released on her debut studio album, which American rapper had a hit with the song \"Be Careful\"? ","Nicki Minaj","Cardi B","Queen Latifah","Lil' Kim","Cardi B ","Cardi B - Belcalis Marlenis Almanzar known professionally as Cardi B, is an American rapper. Born and raised in The Bronx, New York City","23_cardi_b .png",6);

            questionRepositories.questionCreator("American singer and songwriter Alecia Beth Moore is better known as? ","Pink","Shakira","Beyonce","Rihanna","Pink","Pink was originally a member of the girl group Choice.","24_pink.png",6);

            questionRepositories.questionCreator("What TV show\'s soundtrack peaked at #26 on the Billboard 200 chart in June of 2018? ","The Good Place - Season one","Wild Wild Country - Season one","Thirteen Reasons Why - Season two","The Good Fight - Season two","Thirteen Reasons Why Season two","Thirteen Reasons Why - Season 2 - The TV show is based on the 2007 novel Thirteen Reasons Why by Jay Asher.","25_thirteen_reasons_why_season_two.png",6);

            questionRepositories.questionCreator("Released on the album \"Simi\", who collaborated with Blocboy JB on the song \"Look Alive\"? ","Zayn Malik","Harry Styles","Drake","Shawn Mendes","Drake","Look Alive peaked at number five on the Billboard Hot 100.","26_drake.png",6);

            questionRepositories.questionCreator("Who won all six Grammy Awards for which he was nominated at the 60th Annual Grammy Awards? ","Rihanna","Jay-Z","Ed Sheeran","Bruno Mars","Bruno Mars","As of 2018, Mars has sold over 130 million records worldwide.","27_bruno_mars.png",6);

            questionRepositories.questionCreator("Winner of 12 Grammy Awards, what former member of the White Stripes released the album \"Boarding House Reach\" in March of 2018? ","Meg White","Alison Mosshart","Brendan Benson","Jack White","Jack White ","Jack White Boarding House Reach is Jack\'s third solo studio album.","28_jack_white .png",6);

            questionRepositories.questionCreator("What longtime Venture\'s guitarist passed away in March 2018 at the age of 82? ","Bob Bogle","Jerry McGee","Nokie Edwards","Don Wilson","Nokie Edwards","Nole Floyd \"Nokie\" Edwards was an American musician and member of the Rock and Roll Hall of Fame. He was primarily a guitarist, best known for his work with The Ventures, and was known in Japan as the King of Guitars.","29_nokie_edwards.png",6);

            questionRepositories.questionCreator("Making its debut in April of 2018, the studio album \"Ye\" was released by which American rapper? ","Kanye West","50 Cent","Jay-Z","Eminem","Kanye West ","Kanye Omari West is an American rapper, singer, songwriter, record producer, entrepreneur and fashion designer. ","30_kanye_west .png",6);

            questionRepositories.questionCreator("\"Who Let the Dogs Out\" in 2000? ","Snoopy","Baha Men","Shaggy","Lil Bow Wow","Baha Men ","The Baha Men are a Bahamian band playing a modernized style of Bahamian music called junkanoo. They are best remembered for their Grammy Award-winning hit song \"Who Let the Dogs Out?\".","31_baha_men .png",6);

            questionRepositories.questionCreator("Released in 1973, my debut album is titled \"Greetings from Asbury Park\". Who am I? ","Eric Clapton","Jon Bon Jovi","Bruce Springsteen","Bryan Adams","Bruce Springsteen","Bruce Frederick Joseph Springsteen is an American singer-songwriter and musician, known for his work with the E Street Band.","32_bruce_springsteen.png",6);

            questionRepositories.questionCreator("\"The Forces Sweetheart\" was a nickname given to which 1940s female star? ","Alma Cogan","Kay Starr","Vera Lynn","Rosemary Clooney","Vera Lynn ","Vera\'s musical recordings were popular during the Second World War.","33_vera_lynn .png",6);

            questionRepositories.questionCreator("Which of these Elvis Presley songs was a number one BEFORE the others? ","It's Now or Never","All Shook Up","Hound Dog","Stuck on You","Hound Dog ","Elvis released his version of Hound Dog in July of 1956.","34_hound_dog .png",6);

            questionRepositories.questionCreator("This \"Spice Girl\" had a number one hit with \"It\'s Raining Men\" in 2001. ","Victoria Beckham","Emma Bunton","Geri Halliwell","Melanie C","Geri Halliwell ","Geri Halliwell - The song was originally released by The Weather Girls in 1982.","35_geri_halliwell .png",6);

            questionRepositories.questionCreator("Mysteriously disappearing in February of 1995, Richey Edwards was a member of which rock band? ","The Stone Roses","Stereophonics","Primal Scream","Manic Street Preachers","Manic Street Preachers ","Richard James \"Richey\" Edwards was a Welsh musician who was the lyricist and rhythm guitarist of the alternative rock band Manic Street Preachers. Edwards was declared \'presumed dead\' in November of 2008.","36_manic_street_preachers .png",6);

            questionRepositories.questionCreator("Who originally played the drums with \"The Beatles\"? ","Ringo Starr","George Harrison","Pete Best","Stuart Sutcliffe","Pete Best ","Randolph Peter Best is an English musician, principally known as an original member and the first drummer of the Beatles, from 1960 to 1962. ","37_pete_best .png",6);

            questionRepositories.questionCreator("With what song did Britney Spears make her breakthrough in 1998? ","Ooops, I Did it Again","Circus","(Hit Me) Baby One More Time","Toxic","(Hit Me) Baby One More Time ","Britney Jean Spears (born December 2, 1981) is an American singer, dancer, and actress. Born in McComb, Mississippi, and raised in Kentwood, Louisiana, she performed acting roles in stage productions and television shows as a child, before signing with Jive Recordsin 1997.","38_(hit_me)_baby_one_more_time .png",6);

            questionRepositories.questionCreator("Which Canadian singer-songwriter was \"Complicated\" in 2002? ","Shania Twain","Avril Lavigne","Joni Mitchell","Sarah McLachlan","Avril Lavigne ","“Complicated” song is From Avril Lavigne\'s album \'Let Go\'.","39_avril_lavigne .png",6);

            questionRepositories.questionCreator("What is the correct name of this 2007 chart topping band? ","Plain White C's","Plain White B's","Plain White T's","Plain White D's","Plain White T's ","Plain White T\'s is best known for the number-one hit song \'Hey There Delilah\'.","40_plain_white_t_s .png",6);

            questionRepositories.questionCreator("2006: What popular song by Shakira hit the number #1 spot in over 50 different countries?","Hips Don't Lie","Hey You","How Do You Do","Higher Ground","Hips Don't Lie","Shakira created this hit song in 2006 with the help of another musical performer named Wyclef Jean. \"Hips Don\'t Lie\" was first released on Shakira\'s album \"Oral Fixation\".","41_hips_don_t_lie.png",6);

            questionRepositories.questionCreator("Taylor Hawkins plays which instrument in the \"Foo Fighters\"? ","Keyboards","Guitar","Drums","Bass Guitar","Drums","Hawkins was the touring drummer for Alanis Morissette on her Jagged Little Pill and Can\'t Not Tours.","42_drums.png",6);

            questionRepositories.questionCreator("Who released a rock n\' roll version of the song \"Blueberry Hill\", which reached number two on the Billboard Top 40 chart in 1956? `","Little Richard","Ray Charles","Chuck Berry","Fats Domino","Fats Domino ","Antoine \"Fats\" Domino Jr. was an American pianist and singer-songwriter. One of the pioneers of rock and roll music, Domino sold more than 65 million records.","43_fats_domino .png",6);

            questionRepositories.questionCreator("Linkin Park teamed up with which Rap star to record the song \"Numb/Encore\"? ","Snoop Dogg","Jay Z","Ludacris","Nelly","Jay Z ","Numb/Encore is from the 2004 album Collision Course.","44_jay_z .png",6);

            questionRepositories.questionCreator("Released in 2006, complete the title to this Cascada number one hit - \"Everytime We...\"? ","Kiss","Smile","Touch","Meet","Touch ","Everytime we touch song is from their debut album Everytime We Touch.","45_touch .png",6);

            questionRepositories.questionCreator("Born in Oxford, England, \"Jacqueline du Pre\" was famous for the playing of which stringed instrument? ","Guitar","Cello","Violin","Double Bass","Cello","Jacqueline\'s career was cut short by multiple sclerosis at the age of 27.","46_cello.png",6);

            questionRepositories.questionCreator("Which American singer-songwriter won the 1994 Grammy for Record of the Year for with the song \"All I Wanna Do\"? ","Carly Simon","Madonna","Sheryl Crow","Dolly Parton","Sheryl Crow","\'All I Wanna Do\' song peaked at number two on the Billboard Hot 100.","47_sheryl_crow.png",6);

            questionRepositories.questionCreator("Sisters Nicole and Natalie Appleton are members of which all-girl group? ","Sugababes","Girls Aloud","Atomic Kitten","All Saints","All Saints","All Saints have sold over 12 million records","48_all_saints.png",6);

            questionRepositories.questionCreator("Elton John spent how many weeks at the top of the US Charts with his 1997 rewritten version of the song \"Candle in the Wind\"? ","Four","Fourteen","Thirty four","Twenty four","Fourteen","The song was originally written in honor of Marilyn Monroe in 1973.","49_fourteen.png",6);

            questionRepositories.questionCreator("The songs \"Ting a Ling\", \"Love Potion No. 9\" and \"Nip Sip\" were hits for which vocal group? ","The Clovers","The Platters","The Dominoes","The Cadillacs","The Clovers ","The Clovers were one of the most popular acts of the 1950s.","50_the_clovers .png",6);

            questionRepositories.questionCreator("Dave Matthews of the \"Dave Matthews Band\" was born in which country? ","U.S.A.","South Africa","Canada","Germany","South Africa","From 2000 to 2010, the Dave Matthews Band earned more money than any other band in North America.","51_south_africa.png",6);

            questionRepositories.questionCreator("Released in 1974, and found on his \"Fulfillingness\' First Finale\" album, who recorded the funk song \"Boogie on Reggae Woman\"? ","Stevie Wonder","Marvin Gaye","Al Wilson","Bob Marley","Stevie Wonder ","Stevland Hardaway Morris, known by his stage name Stevie Wonder, is an American singer, songwriter, record producer, and multi-instrumentalist.","52_stevie_wonder .png",6);

            questionRepositories.questionCreator("Who wrote the first hit song \"Different Drum\" for Stone Poneys and their lead-singer Linda Ronstadt? ","Michael Nesmith","Paul Simon","Neil Diamond","Bob Dylan","Michael Nesmith ","The song reached number 13 on the Billboard Hot 100 in 1967.","53_michael_nesmith .png",6);

            questionRepositories.questionCreator("Released in June of 1989, what was the name of Nirvana\'s debut album? ","Nevermind","Smells Like Teen Spirit","Incesticide","Bleach","Bleach","Bleach has had sales of over 1.7 million units in the United States.","54_bleach.png",6);

            questionRepositories.questionCreator("2006: What worldwide hit by Nelly Furtado was #1 on over 20 different music charts?","Say it again","Say it","Say it all","Say it Right","Say it Right","Nelly Furtado\'s song \"Say It Right\" was released in the United States on October 31st, 2006. The song \"Say It Right\" featured backup singers named Timothy Mills and Nate Hills.","55_say_it_right.png",6);

            questionRepositories.questionCreator("As of 2013, Don Felder has released two solo albums. Airborne and... ","Fly Higher","Rhythms in the Night","Guitar Man","Road to Forever","Road to Forever","Airborne was released in 1983 and Road to Forever in 2012.","56_road_to_forever.png",6);

            questionRepositories.questionCreator("Who had a hit in 1972 with the song \"Down by the Lazy River\"? ","Leif Garrett","The Osmonds","Partridge Family","Jackson Five","The Osmonds ","The Osmonds have sold over 102 million records worldwide.","57_the_osmonds .png",6);

            questionRepositories.questionCreator("Who conducted and produced the charity single \"We Are the World\"? ","Michael Jackson","Lionel Ritchie","Quincy Jones","Bruce Springsteen","Quincy Jones","The song was written by Michael Jackson and Lionel Richie.","58_quincy_jones.png",6);

            questionRepositories.questionCreator("Which of these jazz composers penned, among other things, the well-known \"Charlie Brown Theme\"? ","David Benoit","Antonin Scarlatti","Vince Guaraldi","Eddie Daniels","Vince Guaraldi ","Vince served as an Army cook in the Korean War.","59_vince_guaraldi .png",6);

            questionRepositories.questionCreator("What is unusual about Seals and Crofts\' hits \"Summer Breeze\", \"Diamond Girl\" and \"Get Closer\"? ","They were all hits on April Fool's Day","They were all hits in the same year","They all had backing vocals by John Denver","They all hit number six on the Billboard charts","They all hit number six on the Billboard charts ","Seals and Crofts were an American soft rock duo made up of James \"Jim\" Seals and Darrell \"Dash\" Crofts. They are best known for their Hot 100 No. 6 hits \"Summer Breeze\", \"Diamond Girl\", and \"Get Closer\".","60_they_all_hit_number_six_on_the_billboard_charts .png",6);

            questionRepositories.questionCreator("What Simon and Garfunkel hit song opens with the line \"Hello darkness my old friend\"? ","The Boxer","Homeward Bound","Sound of Silence","Mrs. Robinson","Sound of Silence ","Released in 1965, Sound of Silence was the duo\'s second most popular hit after the song \"Bridge Over Troubled Water\".","61_sound_of_silence .png",6);

            questionRepositories.questionCreator("Which of the following songs was not a hit for \"The Everly Brothers\"? ","When Will I Be Loved","All I Have to Do Is Dream","Trouble in Paradise","Wake Up Little Susie","Trouble in Paradise","Trouble in Paradise, The Crests recorded \'Trouble in Paradise\' in 1960.","62_trouble_in_paradise.png",6);

            questionRepositories.questionCreator("Who composed the timeless hit \"Fascinating Rhythm\"? ","George Gershwin","Lionel Hampton","Count Basie","Benny Goodman","George Gershwin ","Fascinating Rhythm Written in 1924, the song was first heard in the Broadway musical \'Lady Be Good\'.","63_george_gershwin .png",6);

            questionRepositories.questionCreator("What song is this from? \"If I go crazy then will you still Call me Superman If I\'m alive and well, will you be There a-holding my hand I\'ll keep you by my side With my superhuman might Kryptonite\"","Call me Superman","I’ll keep you by my Side","Kryptonite","My superhuman might Kryptonite","Kryptonite","The song, \"Kryptonite\", is sung by 3 Doors Down and was released on November 21, 2000.","64_kryptonite.png",6);

            questionRepositories.questionCreator("Written in 1973, who was the one-hit wonder who recorded \"Chevy Van\"? ","Sammy Johns","Glen Campbell","Ricky Nelson","Starland Vocal Band","Sammy Johns ","The song reached number five on the Billboard Hot 100 chart in 1975.","65_sammy_johns .png",6);

            questionRepositories.questionCreator("Katy Perry has been announced as one of the live performers for the 2014 Grammy Awards. Additionally, she has been nominated for Song of the Year. Which song? ","Dark Horse","unconditionally","Part of me","Roar","Roar ","Roar can be found on Katy\'s album, Prism.","66_roar .png",6);

            questionRepositories.questionCreator("Co-written and produced by Oren Yoel, who recorded the hit ballad \"Adore You\"? ","Taylor Swift","Beyonce Knowles","Miley Cyrus","Selena Gomez","Miley Cyrus","The song “Adore you” can be found on Cyrus\' number-one hit album \'Bangerz\'.","67_miley_cyrus.png",6);

            questionRepositories.questionCreator("Recorded by Beyonce Knowles and found on her fifth studio album \"Beyonce,\" what single features an audio sample from the Space Shuttle Challenger disaster? ","Drunk in Love","Flawless","Pretty Hurts","XO","XO ","The audio sample\'s inclusion has been heavily criticized by the families of the lost crew and the media.","68_xo .png",6);

            questionRepositories.questionCreator("\"Royals\" has been nominated for Song of the Year at the upcoming 2014 Grammy Awards. Who recorded the song? ","Ariana Grande","Ellie Goulding","Lorde","Lana Del Rey","Lorde ","Royals song can be found on Lorde\'s debut studio album, \'Pure Heroine\'.","69_lorde .png",6);

            questionRepositories.questionCreator("Which song by Bruno Mars opens with the lyrics, \"I spend all my money on a big ol\' fancy car for these bright-eyed honeys. Oh yeah, you know who you are.\"? ","Gorillas","Young Girls","Moonshine","Treasure","Young Girls","Young Girls is from Mars\' second studio album \'Unorthodox Jukebox\'.","70_young_girls.png",6);

            questionRepositories.questionCreator("Riding on the success of her pop hit single \"Replay,\" who recently completed her first \"Swag It Out\" concert tour? ","Debby Ryan","Zendaya","Stefanie Scott","Bella Thorne","Zendaya","As of January 2014, \'Replay\' has been certified gold for selling 500,000 copies in the United States.","71_zendaya.png",6);

            questionRepositories.questionCreator("Which of the following hit songs was written by Jamie Scott, Caolan Dooley, John Ryan, and the British-Irish boy band, One Direction? ","Hold On, We're Going Home","Stay The Night ","Drunk in Love","Story of my Life","Story of my Life ","Released by One Direction, Story of my Life is the fifth track on the album \'Now 49\'.","72_story_of_my_life .png",6);

            questionRepositories.questionCreator("What hit song by Bastille starts with the lyrics \"I was left to my own devices. Many days fell away with nothing to show.\"? ","Of the Night","Laura Palmer","Pompeii","Things We Lost in the Fire","Pompeii ","Pompeii has been nominated for British Single of the Year at the 2014 BRIT Awards.","73_pompeii .png",6);

            questionRepositories.questionCreator("Eminem and which star have a Billboard 100 hit with the song \"The Monster\"? ","Ke$ha","Lady Gaga","Miley Cyrus","Rihanna","Rihanna ","\'The Monster\' marks the fourth collaboration among Eminem and Rihanna.","74_rihanna .png",6);

            questionRepositories.questionCreator("Which of the following songs was recorded by A Great Big World and Christina Aguilera? ","Counting Stars ","White Walls","Let It Go ","Say Something","Say Something ","The song was originally released in 2011 without Christina Aguilera.","75_say_something .png",6);

            questionRepositories.questionCreator("Peaking at number one in 22 countries to date, Avicii introduced which of the following songs for the first time at the Ultra Music Festival in Miami? ","Wake Me Up","You Make Me","Hey Brother","We Write the Story","Wake Me Up ","The song can be found on Avicii\'s debut studio album, \'True\'.","76_wake_me_up .png",6);

            questionRepositories.questionCreator("Achieving worldwide success in 2007 with the hit single \"Love Song,\" and currently in the charts with the song \"Brave,\" what instrument does Sara Bareilles play? ","Piano","Harp","Guitar","Violin","Piano ","Sara has been nominated for a Grammy Award five times.","77_piano .png",6);

            questionRepositories.questionCreator("Recorded by American hip hop artist Kid Ink, which song features a hook and bridge by Chris Brown? ","Show Me","Iz U Down","Money and the Power","Bad Ass","Show Me ","On January 7, 2014, Kid Ink made his television debut performing \"Show Me\" on Conan.","78_show_me .png",6);

            questionRepositories.questionCreator("What hit by \"The Neighbourhood\" opens with the lyrics, \"All I am is a man - I want the world in my hands - I hate the beach - But I stand.\"? ","Sweater Weather","I Love You","Female Robbery","Afraid","Sweater Weather ","Sweater Weather was the lead single from the Neighbourhood\'s debut studio album, \'I Love You\'.","79_sweater_weather .png",6);

            questionRepositories.questionCreator("Slated to be released on January 1st, 2014, but delayed until March, which pop icon will release the album \"Cheek to Cheek\" with jazz singer Tony Bennett? ","Lady Gaga","Madonna","Britney Spears","Katy Perry","Lady Gaga ","Cheek to cheek album is Lady Gaga\'s second collaboration with Tony, the first is a duet of song \'The Lady is a Tramp\'.","80_lady_gaga .png",6);

            questionRepositories.questionCreator("What is the name of the album released by Justin Timberlake in March 2013? ","The 20/20 Experience","Goldenheart","Contrast","Vessel","The 20/20 Experience ","Justin Randall Timberlake (born January 31, 1981) is an American singer-songwriter, actor, dancer, and record producer.","81_the_20_20_experience .png",6);

            questionRepositories.questionCreator("Which band released an album titled \"Vessel\" in 2013? ","Blink one hundred eighty two","Savage Garden","Savages","Twenty-One Pilots","Twenty-One Pilots ","Twenty-One Pilots is a band formed in 2009 in Ohio.","82_twenty_one_pilots .png",6);

            questionRepositories.questionCreator("Which of these bands officially reformed in January 2013? ","Green Day","Black Flag","Boston","Linkin Park","Black Flag ","The band was originally formed in 1976. They broke up in 1986.","83_black_flag .png",6);

            questionRepositories.questionCreator("Which of these bands was formed in 2013? ","Fight or Flight","Attack Attack","Lifehouse","Ghost Observatory","Fight or Flight ","The band\'s debut single was released on the 21st of May 2013.","84_fight_or_flight .png",6);

            questionRepositories.questionCreator("During 2013, which of these bands was on hiatus? ","The Replacements","The Mars Volta","Green Day","Wofmother","Wofmother","Wolfmother has been together since 2000.","85_wofmother.png",6);

            questionRepositories.questionCreator("Which of these songs is on the album \"Contrast\" by Conor Maynard? ","The Way","Shut Up","NoNoNo","Animal","Animal ","Animal song is the first song on the album.","86_animal .png",6);

            questionRepositories.questionCreator("Who recorded the album Long. Live.? ","Muse","ASAP","Dawn Richard","Bad Religion","ASAP ","The album was released in January of 2013.","87_asap .png",6);

            questionRepositories.questionCreator("The phrase \"Unrepentant vagabond\" is the opening line of which of the following songs? ","True North","Troublemaker","Stutter","All Fired Up","True North ","The song appears on the album True North by Bad Religion.","88_true_north .png",6);

            questionRepositories.questionCreator("Which of the following albums was released by \"The Saturdays\"? ","Chasing Monday","Finding the Saturdays","Waiting for Sunday","Chasing the Saturdays","Chasing the Saturdays","Chasing the Saturdays Released in 2013, it is their second album.","89_chasing_the_saturdays.png",6);

            questionRepositories.questionCreator("Which of these songs appears on the album \"It Happens All the Time\" by Megan Hilty? ","Heat","The Next Day","Love is Lost","Be a Man","Be a Man ","Be a Man song has three minutes and thirty-four seconds in length, the song is the album\'s second track.","90_be_a_man .png",6);

            questionRepositories.questionCreator("\"MB... all around the world... Beautiful girl, girl, girl. Uh, girl.\" First line of what Justin Bieber song? ","The Way","Crash","Miss You More","All Around the World","All Around the World ","This song appears on the album All Around the World, which was released in 2013.","91_all_around_the_world .png",6);

            questionRepositories.questionCreator("The songs \"Now\", \"Grow\" and \"Last Hope\" can be found on which of the following albums? ","Right Place Right Time","Willpower","Paramore","Save Rock and Roll","Paramore ","The album was put out by the pop band Paramore.","92_paramore .png",6);

            questionRepositories.questionCreator("Who sings with Will.i.am on his song #thatpower? ","Dido","Jessica Sanchez","Justin Bieber","Wing","Justin Bieber ","The song was released in April, 2013.","93_justin_bieber .png",6);

            questionRepositories.questionCreator("What was the name of the album that Jessica Sanchez released in 2013? ","The Hands That Thieve","Life on a Rock","Wake Up","Me, You and the Music","Me, You and the Music ","This was Jessica\'s first studio album.","94_me_you_and_the_music .png",6);

            questionRepositories.questionCreator("What is the first song on the album 2.0 by 98 Degrees? ","Microphone","Without the Love","Rootless","Get To Me","Microphone","The album was released in 2013.","95_microphone.png",6);

            questionRepositories.questionCreator("Who recorded the song \"Heart Attack\" in 2013? ","Bad Rabbits","Little Mix","Marianas Trench","Demi Lovato","Demi Lovato ","The song appears on the album Demi.","96_demi_lovato .png",6);

            questionRepositories.questionCreator("\"Does he tell you he loves you when you least expect it?\" The first line of which song? ","DNA","Run","Last Hope","Close Your Eyes","DNA ","The song appears on the album DNA along with the song Wings.","97_dna .png",6);

            questionRepositories.questionCreator("In 1999, who had a number one hit on the Billboard Hot 100 with \"Genie In A Bottle\"?","Christina Aguilera ","Christina Perri","Rihanna","Celine Dion","Christina Aguilera ","\"Genie In A Bottle \" was the only number one in the 09s by Christina Aguilera. The others all had number ones on the Hot 100 in 1999: \"Have You Ever\" (Brandy); \"Believe\" (Cher); and \"Angel Of Mine\" (Monica).","98_christina_aguilera .png",6);

            questionRepositories.questionCreator("Sinead O\'Connor sang \"Nothing Compares\", but what was the official spelling of the remainder of that song title?","Two u","You and me","I love you","You and i","Two U","Some might say her abbreviation was ahead of its time, coming before the popular use of SMS text messages, internet chats and twitter. \"Nothing Compares 2 U\" charted at number three on the US Billboard list in 1990, behind Roxette\'s \"It Must Have Been Love\" (number two) and Wilson Phillips\' \"Hold On\" (number one).","99_two_u.png",6);

            questionRepositories.questionCreator("Which singer had a 1999 number one hit song in the USA with \"Genie in a Bottle\"?","Christina Aguilera","Christina Perri","Celine Dion","Rihanna","Christina Aguilera","Christina Aguilera recorded the smash hit song \"Genie in a Bottle\" in 1999. \"Genie\" climbed to the top of the U.S. Billboard charts in the summer of 1999. Aguilera, who was born in New York, sang these lyrics:","100_christina_aguilera.png",6);

            questionRepositories.questionCreator("Spending a total of seventeen weeks in the Billboard Top 40, which Roxette song was also on the soundtrack for the movie \"Pretty Woman\"?","It must love","Be love","It must be Love","It Must Have Been Love","It Must Have Been Love","Roxette consisted of Marie Fredriksson and Per Gessle. \"It Must Have Been Love (Christmas for the Broken Hearted)\" was on Swedish radio during 1987, however it was rewritten in 1990 (without the Christmas lyrics) and was included on the \"Pretty Woman\" soundtrack.","101_it_must_have_been_love.png",6);

            questionRepositories.questionCreator("In 1990, which American dance group had an international hit with the song \"Groove Is in the Heart\"?","The Power","Deee-Lite","Day Lite","Black Box","Deee-Lite","The New York City band Deee-Lite achieved international stardom in late 1990 with the single \"Groove Is in the Heart.\" 103. In 1994, Nicki French released the Billboard Top 100 smash \"Total Eclipse of the Heart\".","102_deee_lite.png",6);

            questionRepositories.questionCreator("\"All you need is your own imagination/ so use it that\'s what it\'s for.\" Who sang these lyrics?","Beyonce","Celine Dion","Madonna","Whitney Houston","Madonna","Madonna\'s \"Vogue\" shot to number one in the UK singles chart in April 1990, commanding the top spot for four weeks. Along with Madonna, all of the other artists listed also reached the coveted number one spot that year.","103_madonna.png",6);

            questionRepositories.questionCreator("Which artist recorded the song \"The Remedy?\"","Jason Mars","Jason Mraz","Jason Maz","Jesson Mraz","Jason Mraz","\"The Remedy\" was from Jason\'s first album, \"Waiting for my Rocket to Come.\" This was the first single released from this album.","104_jason_mraz.png",6);
            /*END OF MUSIC*/


        }



    }


}
