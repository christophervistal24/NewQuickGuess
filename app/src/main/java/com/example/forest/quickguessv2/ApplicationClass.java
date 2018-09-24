package com.example.forest.quickguessv2;

import android.app.Application;

import com.example.forest.quickguessv2.DB.Categories.QuestionCategoryRepositories;
import com.example.forest.quickguessv2.DB.DB;
import com.example.forest.quickguessv2.DB.Friends.FriendsRepositories;
import com.example.forest.quickguessv2.DB.Points.Points;
import com.example.forest.quickguessv2.DB.Points.PointsRepositories;
import com.example.forest.quickguessv2.DB.Questions.QuestionRepositories;
import com.example.forest.quickguessv2.Helpers.Connectivity;

public class ApplicationClass extends Application{

    QuestionCategoryRepositories questionCategoryRepositories;
    QuestionRepositories questionRepositories;
    PointsRepositories pointsRepositories;
    FriendsRepositories friendsRepositories;
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
        friendsRepositories = new FriendsRepositories(this);
        insertCategories();
        insertQuestions();
        insertUserPoints();
        insertAllFriends();
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
            questionRepositories.questionCreator("﻿Which of following creatures has the power to grow lost parts?","Crab","Starfish","Squirrel","Squid","Starfish","Some species of starfish have the ability to regenerate lost arms and can regrow an entire new limb given time. A few can regrow a complete new disc from a single arm, while others need at least part of the central disc to be attached to the detached part.","starfish_1.png",3);
            questionRepositories.questionCreator("Which is the most primitive living mammal?","Seal","Duck-billed platypus","Weasel","Hedgehog","Duck-billed platypus","Egg-laying Mammals There are only five living monotreme species: the duck-billed platypus and four species of echidna (also known as spiny anteaters). In some ways, monotremes are very primitive for mammals because, like reptiles and birds, they lay eggs rather than having live birth.","duck_billed_platypus_2.png",3);
            //2 Krait
//Found
            questionRepositories.questionCreator("Which of the following creatures has the most toxic venom?","Kukri snake","Krait","Scorpion","Cobra","Krait","Its venom is highly haemotoxic. The Saw-scaled Viper is responsible for more human deaths in Asia that all the other venomous Asian snakes combined. Its highly haemotoxic venom is said to be 5 times more toxic than cobras and 16 more toxic than the Russell\'s Viper.","krait_3.png",3);

//3 Blue whale
//Found
            /*questionRepositories.questionCreator("Which animal is the largest in size?","African elephant","Blue whale","Giraffe","Killer whale","Blue whale","Blue whales are the largest animals ever known to have lived on Earth. These magnificent marine mammals rule the oceans at up to 100 feet long and upwards of 200 tons. Their tongues alone can weigh as much as an elephant. Their hearts is as much as an automobile.","blue_whale_4.png",3);

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
            questionRepositories.questionCreator("Where is rhea found?","North America","China","Ireland","South America","South America","This ratite is the largest bird in the Americas. It is a fast runner; and when it runs, its neck is almost horizontal to the ground. Rheas congregate in flocks of 20 to 30 birds. The lesser or Darwin\'s rhea, Rhea pennata, is mostly found in the southern part of South America","south_america_31.png",3);*/
        }
    }

    private void insertAllFriends()
    {
        if (DB.getInstance(getApplicationContext()).friendsDao().countFriends() == 0)
        {
            friendsRepositories.friendsCreator("Patrick",1);
            friendsRepositories.friendsCreator("Steven",1);
            friendsRepositories.friendsCreator("Howard",1);
            friendsRepositories.friendsCreator("Louis",2);
            friendsRepositories.friendsCreator("Bruce",2);
            friendsRepositories.friendsCreator("Earl",2);
            friendsRepositories.friendsCreator("Roger",3);
            friendsRepositories.friendsCreator("Gary",3);
            friendsRepositories.friendsCreator("Joshua",3);
            friendsRepositories.friendsCreator("Anthony",4);
            friendsRepositories.friendsCreator("Steve",4);
            friendsRepositories.friendsCreator("Wayne",4);
            friendsRepositories.friendsCreator("Robert",5);
            friendsRepositories.friendsCreator("Gerald",5);
            friendsRepositories.friendsCreator("Matthew",5);
            friendsRepositories.friendsCreator("Philip",6);
            friendsRepositories.friendsCreator("Thomas",6);
            friendsRepositories.friendsCreator("Justin",6);
            friendsRepositories.friendsCreator("Martin",7);
            friendsRepositories.friendsCreator("Ronald",7);
            friendsRepositories.friendsCreator("Alan",7);
            friendsRepositories.friendsCreator("Russell",8);
            friendsRepositories.friendsCreator("Jason",8);
            friendsRepositories.friendsCreator("Albert",8);
        }

    }

}
