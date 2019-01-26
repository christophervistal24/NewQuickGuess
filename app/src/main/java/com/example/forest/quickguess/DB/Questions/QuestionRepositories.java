package com.example.forest.quickguess.DB.Questions;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.example.forest.quickguess.AnswerQuestion;
import com.example.forest.quickguess.CategoriesActivity;
import com.example.forest.quickguess.DB.DB;
import com.example.forest.quickguess.FinishCategoryActivity;
import com.example.forest.quickguess.Helpers.RedirectHelper;
import com.example.forest.quickguess.Utilities.EncryptUtil;
import com.example.forest.quickguess.Utilities.RandomizeUtil;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

public class QuestionRepositories {

    public Context context;
    public static int class_id = 1;
    public QuestionRepositories(Context context)
    {
        this.context = context;
    }

    public void questionCreator(String question, String a, String b, String c , String d , String correct , String fun_facts , String fun_facts_image, int category_id,int class_id)
    {
        //encrypt
        try {
            Questions questions = new Questions();
            questions.setQuestion(EncryptUtil.encryptMethod(question));
            questions.setChoice_a(EncryptUtil.encryptMethod(a));
            questions.setChoice_b(EncryptUtil.encryptMethod(b));
            questions.setChoice_c(EncryptUtil.encryptMethod(c));
            questions.setChoice_d(EncryptUtil.encryptMethod(d));
            questions.setCorrect_answer(EncryptUtil.encryptMethod(correct));
            questions.setFun_facts(EncryptUtil.encryptMethod(fun_facts));
            questions.setFun_facts_image(EncryptUtil.encryptMethod(fun_facts_image));
            questions.setCategory_id(category_id);
            questions.setClass_id(class_id);
            DB.getInstance(context).questionsDao().insert(questions);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private List<Questions> getQuestions(int id,int class_id)
    {
        return DB.getInstance(context).questionsDao().getQuestionsByCategoryAndClass(id,class_id);
    }


  public Questions selectQuestion(int category_id)
  {
     List<Questions> questionsList = RandomizeUtil.questions(getQuestions(category_id,class_id),getQuestions(category_id,class_id).size());
      if (questionsList.size() != 0 ) //checking if there's a question
      {
          return questionsList.get(0);
      } else { //otherwise the category is finish
          if (context instanceof AnswerQuestion){
              ((AnswerQuestion) context).clockTick.release();
              ((AnswerQuestion) context).finish();
          }
          new RedirectHelper(context, FinishCategoryActivity.class);
      }
      return null;
  }

    public int questionClassier(int category_id) {
        class_id = 1;
        if (checkEasyQuestions(category_id)) // if true proceed to moderate
        {
            class_id = 2;
            if (checkModerateQuestions(category_id)) { // if true proceed to difficult
                  class_id = 3;
              }
        }
        return class_id;
    }

    private boolean checkModerateQuestions(int category_id) {
        int countAllModerate = DB.getInstance(context).questionsDao().countAllByClassification(category_id,2);
        int getAnsweredByClass = DB.getInstance(context).userStatusDao().countAnsweredByClassAndCategoryId(category_id,2);
        return (countAllModerate - getAnsweredByClass) == 0;
    }

    private boolean checkEasyQuestions(int category_id) {
        int countAllEasy = DB.getInstance(context).questionsDao().countAllByClassification(category_id,1);
        int getAnsweredByClass = DB.getInstance(context).userStatusDao().countAnsweredByClassAndCategoryId(category_id,1);
        return (countAllEasy - getAnsweredByClass) == 0;
    }




    public List<String> randomizeChoices(List<String> choices)
  {
      return RandomizeUtil.choices(choices);
  }

    private void addAnimalsEasy()
    {
        //check

//Found
        this.questionCreator("The dog is the most widely abundant what?","Carnivore","Herbivore","Omnivore","None of the above","Carnivore","A carnivore, meaning \"meat eater\", is an organism that derives its energy and nutrient requirements from a diet consisting mainly or exclusively of animal tissue.","e_a_1.webp",3,1);

//Found
        this.questionCreator("What is a domestic dog?","member of genus Canis","member of Canis dirus","member of Canis lupus","member of Canis latrans","member of genus Canis","The domestic dog is a member of genus Canis (canines) that forms part of the wolf-like cani","e_a_2.webp",3,1);

//Found
        this.questionCreator("What type of animal is a seahorse?","Crustacean","Arachnid","Fish","Shell","Crustacean","The seahorse is a bony fish with their bones on the outside of its body.","e_a_3.webp",3,1);

//Found
        this.questionCreator("Which of the following dogs is the smallest?","Chihuahua","Dachshund","Poodle","Pomeranian","Chihuahua","The smallest dog breed belongs to the chihuhua, who cannot weigh more than six pounds according to the American Kennel Club.","e_a_4.webp",3,1);

//Found
        this.questionCreator("What color are zebras?","Black with white stripes.","White with black stripes.","Both of the above.","None of the above.","Black with white stripes.","Zebras have a black base with white stripes.","e_a_5.webp",3,1);

//Found
        this.questionCreator("What existing bird has the largest wingspan?","Albatross","Stork","Swan","Condor","Albatross","The wandering albatross has a wingspan that ranges from 8 to 11.5 feet.","e_a_6.webp",3,1);

//Found
        this.questionCreator("What is the biggest animal that has ever lived?","Blue whale","African elephant","Apatosaurus (aka brontosaurus)","Spinosaurus","Blue whale","Blue whale is the largest animal to have lived, with recorded lengths up to 100 feet. Their average length is about 70 to 90 feet (three school buses), with an average weight of 100 to 150 tons.","e_a_7.webp",3,1);

//Found
        this.questionCreator("What pets do more families own?","Cats","Birds","Dogs","Horses","Cats","As of 2017, there are about 94.2 million pet cats in the US compared to 89.7 million pet dogs. There are also 20.3 million pet birds and 7.6 million pet horses.","e_a_8.webp",3,1);

//Found
        this.questionCreator("What animal lives the longest?","Ocean quahog (clam)","Red sea urchin","Galapagos tortois","Rougheye rockfish","Ocean quahog (clam)","The Ocean quahog lives up to 400 years versus the Galapagos tortoise that lives only 177 years.","e_a_9.webp",3,1);

//Found
        this.questionCreator("What are female elephants called?","Cows","Mares","Sows","Dams","Cows","Female elephants are called cows. Males are called bulls. And the babies are called calves.","e_a_10.webp",3,1);

//Found
        this.questionCreator("Which of the following animals sleep standing up?","Flamingos","Gorillas","Camels","Ravens","Flamingos","Flamingos sleep standing up because the salt flats they live on are too caustic to sit down in.","e_a_11.webp",3,1);

//Found
        this.questionCreator("What is the fastest water animal?","Sailfish","Porpoise","Flying fish","Tuna","Sailfish","The sailfish is the fastest water animal, reaching speed up to 68 miles per hour.","e_a_12.webp",3,1);

//Found
        this.questionCreator("Normal adult dogs have how many teeth?","42","24","38","32","42","The average adult dog has about a third more teeth than his human counterpart. Adult dogs have 42 permanent teeth compared to a measly 32 average human teeth (not counting any wisdom teeth. Those are \"bonus.\"). Puppies possess 28 baby teeth while human babies will have 20 deciduous or “baby” teeth","e_a_13.webp",3,1);

//Found
        this.questionCreator("Through what part of the body do dogs sweat?","Paws","Mouth","Ears","Nose","Paws","Dogs only produce sweat on areas not covered with fur, such as the nose and paw pads, unlike humans who sweat almost everywhere. However, they do have sweat glands, called apocrine glands, associated with every hair follicle on their body.","e_a_14.webp",3,1);

//Found
        this.questionCreator("What is the most common training command taught to dog?","Sit","Stay","Beg","Dance","Sit","This is one of the easiest dog obedience commands to teach, so it’s a good one to start with.","e_a_15.webp",3,1);

//Found
        this.questionCreator("What is a dog's most highly developed sense?","Smell","Taste","Sight","Touch","Smell","Smell, hearing, taste, touch and sight. Smell: \"The sense of smell is very different between dogs and people. The percentage of the dog's brain that is devoted to analyze smells is 40 times larger than that of a human!\"","e_a_16.webp",3,1);

//Found
        this.questionCreator("Puppies are delivered how many weeks after conception?","9","36","22","16","9","The main difference with a false pregnancy is you probably won't see them occur until at least four weeks after the heat cycle ends, and they may not even occur until nine weeks afterward. In a true pregnancy, you'll notice several signs within the first two weeks of gestation.","e_a_17.webp",3,1);

//Found
        this.questionCreator("What is the favorite dog breed of the Queen of England?","Corgi","Basenji","Poodle","Pomeranian","Corgi","Queen's favourite dog breed, the corgi, at risk of dying out. Corgis have been the favourite pet of Queen Elizabeth for decades. LONDON — They're best known for their association with Queen Elizabeth, but despite being the breed of choice for the British monarch, the corgi is in trouble.","e_a_18.webp",3,1);

//Found
        this.questionCreator("Which dog breed is the smallest of them all?","Chihuahua","Dachshund","Shih tzu","Pomeranian","Chihuahua","With a maximum weight of 2.7 kg the Chihuahua is the smallest breed of dog in the world. The Chihuahua breed is originated in Mexico.","e_a_19.webp",3,1);

//Found
        this.questionCreator("Which dog breed has a black tongue?","Labrador","Husky","Weimaraner","Chow chow","Labrador","We do know that the Chow is not the only breed with a blue-black tongue. ... We also know that blue-black spots on tongues are very common in dogs - more than 30 pure breeds are known to have members with spotted tongues. Spots on tongues are simply deposits of extra pigment, like birthmarks and freckles on people.","e_a_20.webp",3,1);

//Found
        this.questionCreator("The first dog registered in the American Kennel Club belonged to what group?","Sporting","Herding","Working","Hound","Sporting","Sporting dogs make likeable, well-rounded companions. First developed to work closely with hunters to locate and/or retrieve quarry. There are four basic types of Sporting dogs; spaniels, pointers, retrievers and setters. Known for their superior instincts in water and woods, many of these breeds enjoy hunting and other field activities.","e_a_21.webp",3,1);

//Found
        this.questionCreator("Which dog yodels instead of barks?","Basenji","Komondor","Otterhound","Basset hound","Basenji","Both dingoes and Basenji lack a distinctive odor, and are prone to howls, yodels, and other vocalizations over the characteristic bark of modern dog breeds.","e_a_22.webp",3,1);

//Found
        this.questionCreator("What breed of dog is the smallest used in hunting?","Miniature Dachshund","Chihuahua","Toy poodle","Smooth fox terrier","Miniature Dachshund","Are a smaller breed of the standard dachshund. Some of their nicknames include \"wiener dogs\", \"hot dogs\", or \"sausage dogs.\"","e_a_23.webp",3,1);

//Found
        this.questionCreator("What is the name of the dog on the front of the Cracker Jack Box?","Bingo","Jack","Max","Fido","Bingo","Cracker Jack's mascots Sailor Jack and his dog Bingo were introduced as early as 1916 and registered as a trademark in 1919. Sailor Jack was modeled after Robert Rueckheim, grandson of Frederick.","e_a_24.webp",3,1);

//Found
        this.questionCreator("How old was the world's oldest dog, an Australian cattle hound named Bluey,in human years?","29","32","27","30","29","Bluey (7 June 1910 – 14 November 1939) was an Australian cattle dog owned by Les and Esma Hall of Rochester, Victoria, Australia. According to Guinness World Records, Bluey lived 29 years, 5 months and is the oldest dog ever verified.","e_a_25.webp",3,1);

//Found
        this.questionCreator("What is the most popular breed of dog, according to the American Kennel Club's registrations?","Labrador","Golder retriever","Beagle","German Shepherd","Labrador","The Labrador Retriever is the most popular breed in the United States. And if you’ve ever met a Labrador Retriever, it’s easy to see why. They’re incredibly friendly and loyal, great for families, and perfect companion dogs.","e_a_26.webp",3,1);

//Found
        this.questionCreator("Calico cats are almost always:","Female","Left pawed","Friendly","Finicky","Female","Calico is not a breed of cat, it's a three-color pattern. Calico cats are nearly always females, for complicated genetic reasons (just Google it, okay?) About one in 3,000 calico cats are male, and they're usually sterile.","e_a_27.webp",3,1);

//Found
        this.questionCreator("A group of cats is called a:","Clowder","Pack","Hoard","Nothing. Cats don\'t congregate in groups.","Clowder","And a group of kittens is called a kindle.","e_a_28.webp",3,1);

//Found
        this.questionCreator("What is a cat doing when it's \"making biscuit?\"","Kneading with its paws","Playing with bread dough","Training to be a chef","Auditing for a Food Network show","Kneading with its paws","Some call this \"making biscuits\" because the motion","e_a_29.webp",3,1);

//Found
        this.questionCreator("A cat with Pica does which of the following:","Eats strange non-food items like wool","Measures things","Sheds profesuly","Hides from strangers","Eats strange non-food items like wool","Dogs and people can have this eating disorder too.","e_a_30.webp",3,1);

//Found
        this.questionCreator("What is the scientific name for hair in cats?","Alopecia","Balding Disorder","Minoxidil","Lymphadenopathy","Alopecia","This term is also used for hair loss in humans.","e_a_31.webp",3,1);

//Found
        this.questionCreator("Former \"Price is Right\" host Bob Barker recently helped rescue:","25 circus lions from Bolivia","10 monkeys from a movie set","65 rats from a research facility","A three-legged shelter dog","25 circus lions from Bolivia","Animal advocate Barker helped finance the $200,000 bill to bring the lions to a Colorado sanctuary.","e_a_32.webp",3,1);

//Found
        this.questionCreator("Hemingway Cats are feline that have:","Extra toes","Written a best-selling book","An abonormally large head","A cropped tail","Extra toes","When a cat has more than 18 toes, it's called a \"polydactyl,\" but nicknames include mitten cats, thumb cats, boxing cats and Hemingway cats.","e_a_33.webp",3,1);

//Found
        this.questionCreator("What is the scientific name for \"Fear of Cats?\"","Ailurophobia","Felineophobia","Get-it-away-phobia","There isn\'t one because it\'s not a recognized fear","Ailurophobia","\"Love of Cats\" is called Ailurophilia.","e_a_34.webp",3,1);

//Found
        this.questionCreator("Giving your kitty catnip might cause it to:","All","Be sleepy","Be energetic","Do nothing","All","Catnip makes some cats wildly energetic, while others get sleepy. Most kittens under 3 months and some older cats are not affected by the herb at all.","e_a_35.webp",3,1);

//Found
        this.questionCreator("What is the collective name for a group of lions?","Pride","Kennel","a litter of pup","lock of sheep","Pride","Characteristic of any member of the cat family, you call a group of lions a \"pride\". More specifically, a group of female lions is called a pride, while a group of male lions is called a \"coalition\". ... Group of female lions is called a Pride which contains upto 25 lioness and cubs.","e_a_36.webp",3,1);

//Found
        this.questionCreator("What are the only two mammals that lay eggs?","The spiny anteater and the duck billed platypus.","duck billed platypus","spiny anteater","none of the above","The spiny anteater and the duck billed platypus.","Only two kinds of egg-laying mammals are left on the planet today—the duck-billed platypus and the echidna, or spiny anteater. These odd “monotremes” once dominated Australia, until their pouch-bearing cousins, the marsupials, invaded the land down under 71 million to 54 million years ago and swept them away.","e_a_37.webp",3,1);

//Found
        this.questionCreator("What type of animal is a Mexican hairless?","dog","cat","duck","rabbit","dog","The Xoloitzcuintli, or Xolo for short, is a hairless breed of dog, found in toy, miniature, and standard sizes. The Xolo also comes in a coated variety and coated and hairless can be born in the same litte","e_a_38.webp",3,1);

//Found
        this.questionCreator("How many walking legs does a lobster have?","8","10","6","5","8","Lobsters have 8 walking legs; the front three pairs bear claws, the first of which are larger than the others.","e_a_39.webp",3,1);

//Found
        this.questionCreator("What type of animal is a Flemish giant?","rabbit","dog","cat","tiger","rabbit","The Flemish Giant rabbit is a very large breed of domestic rabbit, and is normally considered to be the largest breed of the species. Flemish giants are a utility breed, and are most commonly bred for fur and meat.","e_a_40.webp",3,1);

//Found
        this.questionCreator("How many arms do most starfish have?","5","3","2","4","5","Starfish are marine invertebrates. They typically have a central disc and five arms, though some species have a larger number of arms. The aboral or upper surface may be smooth, granular or spiny, and is covered with overlapping plates.","e_a_41.webp",3,1);

//Found
        this.questionCreator("Which large mammal's tail is so strong it can stand on it and lift its hind legs off the ground?","Kangaroo","Tiger","Lion","Wolf","Kangaroo","The kangaroo is a marsupial from the family Macropodidae. In common use the term is used to describe the largest species from this family, especially those of the genus Macropus: the red kangaroo, antilopine kangaroo, eastern grey kangaroo, and western grey kangaroo. Kangaroos are indigenous to Australia","e_a_42.webp",3,1);

//Found
        this.questionCreator("What is a cabbage white?","butterfly","bird","fish","dog","butterfly","Pieris rapae, the small white, is a small- to medium-sized butterfly species of the whites-and-yellows family Pieridae. It is also known as the small cabbage white and in New Zealand, simply as white butterfly. The names \"cabbage butterfly\" and \"cabbage white\" can also refer to the large white","e_a_43.webp",3,1);

//Found
        this.questionCreator("Which ape gets its name from the Malay word meaning \"man of the forest\"?","orangutan","gorilla","tamarin","tufted capuchins","orangutan","The name \"orangutan\" (also written orang-utan, orang utan, orangutang, and ourang-outang) is derived from the Malay and Indonesian words orang meaning \"person\" and hutan meaning \"forest\", thus \"person of the forest\".","e_a_44.webp",3,1);

//Found
        this.questionCreator("Which is the Fastest flying bird?","Peregrine Falcon","Eagle","Seagull","Kite","Peregrine Falcon","The peregrine falcon, also known as the peregrine, and historically as the duck hawk in North America, is a widespread bird of prey in the family Falconidae. A large, crow-sized falcon, it has a blue-grey back, barred white underparts, and a black head.","e_a_45.webp",3,1);

//Found
        this.questionCreator("What is another name for a Pigeon?","Rock Dove","Common Dove","City Dove","It has no other name","Rock Dove","Pigeons are actually called \"Rock Doves\", too. in fact, mose birding book have them listed under \"Rock Dove\"","e_a_46.webp",3,1);

//Found
        this.questionCreator("Which of the following birds has only two less?","Ostrich","Cardinal","Finch","Robin","Ostrich","The ostrich is a flightless bird and is the largest and strongest of all living birds.","e_a_47.webp",3,1);

//Found
        this.questionCreator("Which of these animals walks like a camel?","Cat","Dog","Elephant","Giraffe","Cat","The cat, often referred to as the domestic cat to distinguish from other felids and felines, is a ..... Cats are capable of walking very precisely because, like all felines, they directly register; that is, they ... This trait is shared with camels and giraffes.","e_a_48.webp",3,1);

//Found
        this.questionCreator("What is the gestation period of an African elephant?","Twenty two months","One year","Six months","Nine months","Twenty two months","Commenting on the study, he said: \"Not only is the long gestation of elephants unusual (22 months), but the long birth interval (4-5 years between calves) along with a long interval between generations of elephants (average approximately 20 plus years).\"","e_a_49.webp",3,1);

//Found
        this.questionCreator("Which bird's eye are larger than its brain?","Ostrich","Eagle","Owl","Parrot","Ostrich","The eyes of the ostrich are about the size of billiard balls -- their brains are smaller, meaning they're not very smart birds. Although ostriches aren't brilliant, they are capable of running at tremendous speeds of up to 40 miles per hour to escape their natural predators.","e_a_50.webp",3,1);

//Found
        this.questionCreator("Name the bird that enters a saltware crocodile's mouth to pick out the parasites and food particles?","Nile plover","Sparrow","Kingfisher","Crow","Nile plover","The Egyptian plover, also known as the crocodile bird, is a wader, the only member of the genus Pluvianus. Formerly placed in the pratincole and courser family, Glareolidae, it is now regarded as the sole member of its own monotypic family Pluvianidae.","e_a_51.webp",3,1);

//Found
        this.questionCreator("What is a gecko?","Common house lizard","Dinosaur","Bat","Snake","Common house lizard","Geckos are lizards belonging to the infraorder Gekkota, found in warm climates throughout the world. They range from 1.6 to 60 cm. Most geckos cannot blink, but they often lick their eyes to keep them clean and moist. They have a fixed lens within each iris that enlarges in darkness to let in more light.","e_a_52.webp",3,1);

//Found
        this.questionCreator("To which animal is the bear closely related?","Dog","Cat","Monkey","Bats","Dog","Bears are carnivoran mammals of the family Ursidae. They are classified as caniforms, or doglike carnivorans. Although only eight species of bears are extant, they are widespread, appearing in a wide variety of habitats throughout the Northern Hemisphere and partially in the Southern Hemisphere.","e_a_53.webp",3,1);

//Found
        this.questionCreator("Which of these is an aquatic animal?","Painted turtle","Mud turtle","Box turtle","Swamp turtle","Painted turtle","Semi aquatics turtles require a swimming area an a dry basking spot. The box and mud tutrle are terrestrial tutrles, which means they are land dwelling creatures.","e_a_54.webp",3,1);

//Found
        this.questionCreator("Which is the Tallest dog (breed)?","Great Dane","Dobberman","Greyhound","Dalmatian","Great Dane","Though they aren't the heaviest dogs, reaching around 100-120 pounds, they are among the tallest. The average Great Dane stands around 28-30 inches tall but often they can be taller. The world record holder for tallest dog was a Great Dane named Zeus who stood an astounding 44 inches tall","e_a_55.webp",3,1);

//Found
        this.questionCreator("Which is the Heaviest dog (breed)?","English Mastiff or St. Bernard","German Shepherd","Irish Wolfhound","Bull Dog","English Mastiff or St. Bernard","Both English Mastiff and St. Bernard are considered to be among the heaviest dogs in the world. Males from both the breeds have been regularly weighed in at 170-200 lb.","e_a_56.webp",3,1);

//Found
        this.questionCreator("Which is the Largest carnivore on land?","Polar Bear","Tiger","Leopard","Lion","Polar Bear","The largest of all the carnivores is the polar bear (Ursus maritimus). Adult males typically weigh 400-600 kg (880-1,320 lb), and have a nose-to-tail length of 2.4-2.6 m (7 ft 10 in-8 ft 6 in).","e_a_57.webp",3,1);

//Found
        this.questionCreator("Which is the Heavist snake?","Anaconda","Viper","Reticulated Python","Boa Constrictor","Anaconda","Weighing in at 550 pounds, the aptly named giant anaconda (Eunectes murinus) is the largest snake in the world considering its length-to-weight ratio. This species, also known as the green anaconda, averages about 17 feet in length, though some individuals grow to as long as 30 feet.","e_a_58.webp",3,1);

//Found
        this.questionCreator("Which is the tallest land animal?","Giraffe","Elephant","Rhinoceros","Polar bear","Giraffe","The giraffe (Giraffa camelopardalis) is an African even-toed ungulate mammal and the tallest living terrestrial animal in the world. It stands 5–6 m (16–20 ft) tall and has an average weight of 1,600 kg (3,500 lb) for males and 830 kg (1,800 lb) for females.","e_a_59.webp",3,1);

//Found
        this.questionCreator("In the wild, which animal is found only China?","Panda","Tiger","Asian elephant","Crocodile","Panda","The giant panda, also known as panda bear or simply panda, is a bear native to south central China. It is easily recognized by the large, distinctive black patches around its eyes, over the ears, and across its round body. The name \"giant panda\" is sometimes used to distinguish it from the unrelated red panda","e_a_60.webp",3,1);

//Found
        this.questionCreator("Which of these land animals moves most slowly?","Snail","Turtle","Rabiit","Three-toed-sloth","Snail","It would take a garden snail over 30 hours to go a mile.","e_a_61.webp",3,1);

//Found
        this.questionCreator("Which is the biggest of the Great Apes?","Gorilla","Orangutan","Chimpanzee","Monkey","Gorilla","Adult male gorillas weigh about 400 pounds and can be 6 feet tall when standing. Adult female gorillas weigh about 200 pounds and can be 5 feel tall when standing.","e_a_62.webp",3,1);

//Found
        this.questionCreator("What color is the tufted titimouse?","Gray","Black","Blue","Red","Gray","The tufted titimouse is a tame bird. It can be attracted by sunflower seeds.","e_a_63.webp",3,1);

//Found
        this.questionCreator("How many toes do ostriches have on each foot?","2","4","5","3","2","Their feet with two toes help them to run at fast speeds.","e_a_64.webp",3,1);

//Found
        this.questionCreator("Where do you find tigers?","Asia","Africa","South America","Austraillia","Asia","William Blake wrote a beautiful poem about this powerful creature.","e_a_65.webp",3,1);

//Found
        this.questionCreator("Which animal has the biggest eye--ten times bigger than a human eye?","Giant Squid","Whale","Mongoose","Bush Baby","Giant Squid","A giant squid, which lives in the ocean, has the biggest eye.","e_a_66.webp",3,1);

//Found
        this.questionCreator("What do you call animals that only eat plants?","Herbivores","Carnivores","Omnivores","Vegetarians","Herbivores","Animals that eat only plants are called herbivores. Humans that eat only plants are called vegetarians.","e_a_67.webp",3,1);

//Found
        this.questionCreator("The tallest animal can grow up to 5.5 metres. What is it?","Giraffe","Elephant","Camel","Zebra","Giraffe","The tallest animal is the giraffe. They have to spread their legs wide to bend down to take a drink.","e_a_68.webp",3,1);

//Found
        this.questionCreator("What animal, besides snakes, can go for two years without food?","Crocodile","Eel","Camel","Zebra","Crocodile","A crocodile can wait for two years for a meal! They obviously haven't heard of McDonald's.","e_a_69.webp",3,1);

//Found
        this.questionCreator("The life span of a bullfrog is about how many years?","15","1","6","10","15","The average bullfrog lives seven to nine years in the wild. The record lifespan of an animal in captivity is 16 years.","e_a_70.webp",3,1);

//Found
        this.questionCreator("How many claws does a house cat have?","18","20","16","14","18","Normal cats have a total of 18 toes, with five toes on each fore paw, and four toes on each hind paw; polydactyl cats may have as many as eight digits on their front and/or hind paws.","e_a_71.webp",3,1);

//Found
        this.questionCreator("Which one of these whales has teeth?","Sperm Whale","Sei Whale","Right Whale","Blue Whale","Sperm Whale","The sperm whale's lower jaw is very narrow and underslung. The sperm whale has 18 to 26 teeth on each side of its lower jaw which fit into sockets in the upper jaw.The teeth are cone-shaped and weigh up to 1 kilogram (2.2 lb) each.","e_a_72.webp",3,1);

//Found
        this.questionCreator("What kind of animal is a kitten?","Juvenile cat","Vertebrate","Amphibians","Mammals","Juvenile cat","A kitten is a juvenile cat. After being born, kittens are totally dependent on their mother for survival and they do not normally open their eyes until after seven to ten days. After about two weeks, kittens quickly develop and begin to explore the world outside the nest.","e_a_73.webp",3,1);

//Found
        this.questionCreator("Which of the following birds is the state bird of three Indian states?","Blue Jay (Indian Roller)","Hill Mynah","Greater Flamingo","Great Hornbill","Blue Jay (Indian Roller)","The 3 states are Andhra Pradesh,Karnataka and Odisha.","e_a_74.webp",3,1);

//Found
        this.questionCreator("What was the name of the first cloned sheep?","Dolly","Jolly","Molly","Polly","Dolly","Dolly the Sheep. Dolly (July 5, 1996 - February 14, 2003), a ewe, was the first mammal to have been successfully cloned from an adult cell. She was cloned at the Roslin Institute in Midlothian, Scotland, and lived there until her death when she was six years old.","e_a_75.webp",3,1);

//Found
        this.questionCreator("Which one among the following is not an ape?","Langur","Gibbon","Gorilla","Orangutan","Langur","Gray langurs or Hanuman langurs, the most widespread langurs of the Indian Subcontinent, are a group of Old World monkeys constituting the entirety of the genus Semnopithecus.","e_a_76.webp",3,1);

//Found
        this.questionCreator("Which one of the following is not a true fish?","Starfish","Shark","Eel","Sea-horse","Starfish","Sea stars live underwater, but that is where their resemblance to fish ends. They do not have gills, scales, or fins. Sea stars live only in saltwater. Sea water, instead of blood, is actually used to pump nutrients through their bodies via a 'water vascular system.'","e_a_77.webp",3,1);

//Found
        this.questionCreator("The common animal with the scientific name canis lupus is –","Dog","Cat","Cow","Buffalo","Dog","The domestic dog (Canis lupus familiaris when considered a subspecies of the wolf or Canis familiaris when considered a distinct species)[4] is a member of the genus Canis (canines), which forms part of the wolf-like canids.","e_a_78.webp",3,1);

//Found
        this.questionCreator("Which of the following is not a mammal?","Sea horse","Sea Lion","Sea Otter","Sea Cow","Sea horse","It's certainly not a mammal, since it doesn't suckle its young. They are classified as fishes, in the genus Hippocampus. There are many species of seahorse. Mammals breath air; fish get their oxygen through the water.","e_a_79.webp",3,1);

//Found
        this.questionCreator("which mammal lays eggs?","Platypus","Bat","Dolphin","Whale","Platypus","The platypus, found only in Australia is one of the five mammal species of that lay eggs instead of giving birth to live young. The other egg-laying mammals are four species of echidna.","e_a_80.webp",3,1);

//Found
        this.questionCreator("Which of the following snakes builds a nest?","King Cobra","Python","Anaconda","Rattlesnake","King Cobra","Building the Nest. The king cobra can lay up to 40 eggs at a time, which means she has a lot to protect while waiting for them to hatch. She builds her nest by pushing together loose vegetation with her head, giving her a place to store the eggs together.","e_a_81.webp",3,1);

//Found
        this.questionCreator("If the mother is a horse, what do we call the baby?","Foal","Calf","Cub","Kid","Foal","The mother horse is called a mare","e_a_82.webp",3,1);

//Found
        this.questionCreator("Lions are big cats. But their babies are called?","Cubs","Kittens","Leopards","Calves","Cubs","Lions cubs love to play , just like kittens","e_a_83.webp",3,1);

//Found
        this.questionCreator("Puppies are young?","Dogs","Cats","Goats","Rabbits","Dogs","Puppies soon grow up","e_a_84.webp",3,1);

//Found
        this.questionCreator("What is a baby sheep called?","Lamb","Puppy","Calf","Chick","Lamb","Many lambs are born in spring","e_a_85.webp",3,1);

//Found
        this.questionCreator("If the mother is a cow, what do we call the baby?","Calf","Piglet","Cub","Foal","Calf","The plural of calf is calves","e_a_86.webp",3,1);

//Found
        this.questionCreator("kitten is a baby. Kittens are baby?","Cats","Dogs","Lions","Tiger","Cats","Kittens love to play with ball of wool","e_a_87.webp",3,1);

//Found
        this.questionCreator("What are baby pigs called?","Piglets","Puppies","Poppies","Pansies","Piglets","The mother pig is called a sow","e_a_88.webp",3,1);

//Found
        this.questionCreator("Cats like to play. They like to play with balls of wool. Which one of these will play most?","Kitten","Adult cat","Old cat","Mother cat","Kitten","Older cats sleep a lot","e_a_89.webp",3,1);

//Found
        this.questionCreator("Which one of these is an adult animal?","Hen","Kitten","Chick","Duckling","Hen","Don't forget, hen is another word for a grown-up chicken","e_a_90.webp",3,1);

//Found
        this.questionCreator("Which one of these is an adult animal?","Horse","Cub","Gosling","Foal","Horse","Horses can sleep both lying down and standing up.","e_a_91.webp",3,1);

//Found
        this.questionCreator("Which one of these is a grown-up animal?","Bull","Puppy","Calf","Foal","Bull","Male animal .Be careful. Bulls can be dangerous!","e_a_92.webp",3,1);

//Found
        this.questionCreator("Which animal is the largest in size?","Blue whale","African elephant","Giraffe","Killer whale","Blue whale","Blue whales are the largest animals ever known to have lived on Earth. These magnificent marine mammals rule the oceans at up to 100 feet long and upwards of 200 tons. Their tongues alone can weigh as much as an elephant. Their hearts is as much as an automobile.","e_a_93.webp",3,1);

//Found
        this.questionCreator("Which living being is a skilled engineer?","Beaver","Tailor-bird","Termite","Honey bee","Beaver","Beavers have long been recognized as the engineers of the forest, constantly reshaping their surroundings","e_a_94.webp",3,1);

//Found
        this.questionCreator("Where are zebra found?","Africa","South America","China","New Zealand","Africa","Plains zebra are found on the savannas from Sudan to northern Zimbabwe in eastern Africa. Grevy's zebras are now mostly restricted to parts of northern Kenya. Mountain zebras occur in southwestern Africa with cape mountain zebras in South Africa and Hartmann's mountain zebras in Namibia and Angola.","e_a_95.webp",3,1);

//Found
        this.questionCreator("Where are chimpanzees found?","Africa","South America","India","Afghanistan","Africa","Together with humans, gorillas, and orangutans they are part of the family Hominidae (the great apes). Native to sub-Saharan Africa, common chimpanzees and bonobos are currently both found in the Congo jungle, while only the common chimpanzee is also found further north in West Africa.","e_a_96.webp",3,1);

//Found
        this.questionCreator("Where is koala bears found?","Australia","North America","Africa","South Africa","Australia","Koalas do not live in rainforests or desert areas. They live in the tall eucalypt forests and low eucalypt woodlands of mainland eastern Australia, and on some islands off the southern and eastern coasts. Queensland, NSW, Victoria and South Australia are the only states where Koalas are found naturally in the wild.","e_a_97.webp",3,1);

//Found
        this.questionCreator("Which bird was once found in abundance but is today extinct species?","Passenger pigeon","Flightless cormorant","Grey heron","Kakapo","Passenger pigeon","Today only about 40 kakapos survive in the wild on two small islands off the coast of New Zealand's South Island. It is because of the hunter.","e_a_98.webp",3,1);

//Found
        this.questionCreator("Which is the fastest swimming bird?","Gentoo penguin","Adelie penguin","Shelduck","Puffin","Gentoo penguin","The Gentoo penguin (pygoscelis Papua) is the world's fastest swimming bird. It can swim between 36-40 km. per hour. They are found in the Antarctic Islands.","e_a_99.webp",3,1);

//Found
        this.questionCreator("Which birds has flippers instead of wings?","Penguin","Owl","Goose","Albatross","Penguin","While penguins are the only birds that have true flippers, other pelagic birds that spend a good deal of time swimming also have some flipper-like characteristics to their wings. Puffins, murres and auks all have wings that more closely resemble flippers, but to a lesser degree than penguin wings.","e_a_100.webp",3,1);

//Found
        this.questionCreator("Which bird has the biggest egg?","Ostrich","Tawny owl","Moorhen","Duck","Ostrich","On record weighed 2.589 kg (5 lb. 11.36 oz) and was laid by an ostrich (Struthio camelus) at a farm owned by Kerstin and Gunnar Sahlin (Sweden) in Borlange, Sweden, on 17 May 2008","e_a_101.webp",3,1);

//Found
        this.questionCreator("What kind of animal is Egyptian Mau?","Cat","Dog","Snake","Camel","Cat","This, along with the well-known chattiness of the Egyptian Mau, makes them popular pets today. As the Egyptian Mau is the only naturally spotted breed of domestic cat, the Egyptian Mau is often bred with other domestic cats in order to produce slightly spotted kittens","e_a_102.webp",3,1);

//Found
        this.questionCreator("Which snake is a member of family boa and sometimes called 'water boa'?","Anaconda","Cobra","Python","Rattle snake","Anaconda","There are about 25 British species according to Prys-Jones (19 species of Bombus and 6 species of cuckoo bumblebees). And about 65 species in Europe, and 250 species of bumblebee have been discovered so far worldwide","e_a_103.webp",3,1);

//Found
        this.questionCreator("Which animal do not make any sound?","Giraffe","Koala","Panda","Bear","Giraffe","Science Says This Is the Sound Giraffes Make. It's long been assumed that unlike other animals, giraffes are largely silent beasts. They don't oink, moo or roar. But new research suggests perhaps giraffes do have a distinct sound: They hum.","e_a_104.webp",3,1);

//Found
        this.questionCreator("Which of the following has no skeleton at all?","Jelly fish","Star fish","Sponge","Silver fish","Jelly fish","A jellyfish has no ears or eyes or nose and no brain or heart! They do not even have a head. Their body is almost totally made of water and is soft having no bones at all. Jellyfish are invertebrate animals because they do not have a spine or backbone.","e_a_105.webp",3,1);

//Found
        this.questionCreator("How many legs does a butterfly have?","six","two","four","eight","six","The scales, which are arranged in colorful designs unique to each species, are what give the butterfly its beauty. Like all other insects, butterflies have six legs and three main body parts: head, thorax (chest or mid-section) and abdomen (tail end). They also have two antennae and an exoskeleton","e_a_106.webp",3,1);

//Found
        this.questionCreator("The dolphin is?","Mammal","Fish","Reptile","Turtle","Mammal","Like every mammal, dolphins are warm blooded. Unlike fish, who breathe through gills, dolphins breathe air using lungs. Dolphins must make frequent trips to the surface of the water to catch a breath.","e_a_107.webp",3,1);

//Found
        this.questionCreator("Which bird can turn head around to look backward?","Owl","Toucan","Parrot","Eagle","Owl","A tawny owl turning its head far around its neck, Owls don't need eyes in the back of their heads to see what's behind them - they can just swivel their heads all the way around","e_a_108.webp",3,1);

//Found
        this.questionCreator("Which is the fastest swimming fish?","Sailfish","Dolphin","Catfish","Eel","Sailfish","Not all experts agree, but at top speeds of nearly 70 mph, the sailfish is widely considered the fastest fish in the ocean. Clocked at speeds in excess of 68 mph, some experts consider the sailfish the fastest fish in the world ocean.","e_a_109.webp",3,1);

//Found
        this.questionCreator("What is the fastest land animal in the world?","Cheetah","Kangaroo","Wolf","Dear","Cheetah","These graceful animals are identified by their unique black spots on gold or yellow coats and are known for their amazing speed. In fact, according to the Smithsonian National Zoological Park, the cheetah is the world's fastest land mammal. A sprinting cheetah can reach 45 mph (72 km/h) within 2.5 seconds","e_a_110.webp",3,1);

//Found
        this.questionCreator("A 'doe' is what kind of animal?","Female deer","Female bear","Female yak","Female camel","Female deer","Deer are a group of even-toed ungulate mammals. They form the family Cervidae. A male deer is called stag or buck, a female deer is called doe, and a young deer is called fawn. There are about 60 species of deer.","e_a_111.webp",3,1);

//Found
        this.questionCreator("What is the only continent on earth where Giraffes live in the wild?","Africa","North pole","Australia","South America","Africa","Giraffes can be found in central, eastern and southern Africa. Giraffes live in the savannas of Africa, where they roam freely among the tall trees, arid land, dense forests and open plains.","e_a_112.webp",3,1);

//Found
        this.questionCreator("How many legs does a spider have?","eight","seven","nine","ten","eight","Spiders (order Araneae) are air-breathing arthropods that have eight legs and chelicerae with fangs that inject venom. They are the largest order of arachnids and rank seventh in total species diversity among all other orders of organisms.","e_a_113.webp",3,1);

//Found
        this.questionCreator("What animal is known as the king of the jungle?","Lion","Gorilla","Tiger","Elephant","Lion","Lion is known to be the king of the beast (\"king of the jungle\" would be a mismoner) across most cultures of the world. This is mostly because of lion's appearance and partly because of the social structure of a pride and the lion's role in the place.","e_a_114.webp",3,1);

//Found
        this.questionCreator("Which is the largest type of penguin?","Emperor penguin","King penguin","Crested penguin","Gentoo penguin","Emperor penguin","The emperor penguin (Aptenodytes forsteri) is the tallest and heaviest of all living penguin species and is endemic to Antarctica. The male and female are similar in plumage and size, reaching 122 cm (48 in) in height and weighing from 22 to 45 kg (49 to 99 lb)","e_a_115.webp",3,1);

//Found
        this.questionCreator("What mammal can hold their breath in a longest time?","Sperm whale","Dolphin","Blue whale","Human","Sperm whale","Sperm whale makes some of the longest dives achieved by mammals, with some lasting up to 90 minutes, while dolphins and other whales can stay underwater for 20 minutes. The longest time a human has held their breath for under water is 19 minutes set by a Swiss free diver called Peter Colat.","e_a_116.webp",3,1);

//Found
        this.questionCreator("Which is the smallest living bird?","Bee hummingbird","Sparrow","Pigeon","Parrot","Bee hummingbird","Bee Hummingbirds (Mellisuga helenae) are only 5 to 6 cm long and weigh just 1.6 to 1.9 g (a small coin such as a U.S. penny weighs around 2.5 to 3 g). The male Bee Hummingbird is the smallest of all birds and can easily be mistaken for a bee","e_a_117.webp",3,1);

//Found
        this.questionCreator("What is the world's longest poisonous snake?","King cobra","Tiger snake","Boomslang","Eastern brown snake","King cobra","Today's longest venomous snakes, king cobra (ophiophagus Hannah), can grow to be about 18 feet (5.5m) long.","e_a_118.webp",3,1);

//Found
        this.questionCreator("What is a group of bat called?","Colony","Comrade","Companion","Group bat","Colony","A group of bats is called a colony of bats. Within one colony of bats, there may be 10 to 1, 000 bats or more. Bats tend to stick together in order to protect each other, to mate and to gather food.","e_a_119.webp",3,1);

//Found
        this.questionCreator("Which animal has the widest hearing range?","Dolphin","Lizard","Hyena","Lion","Dolphin","Bottlenose dolphins hear tones with a frequency up to 160 kHz with the greatest sensitivity ranging from 40 to 100 kHz. The average hearing for humans is about 0.02 to 20 kHz","e_a_120.webp",3,1);

//Found
        this.questionCreator("What kind of animal is a stallion?","Male horse","Male deer","Male zebra","Male donkey","Male horse","Male horse is called a stallion. A stallion used for breeding is known as a stud. Formerly, stallion was employed as riding horses.","e_a_121.webp",3,1);

//Found
        this.questionCreator("In which country are lemurs found in nature?","Madagascar","U.S.A","Finland","New Zealand","Madagascar","Today the black lemur is an endangered species and is found only in a small area on Madagascar and on two small islands off its northwest coast. On one island they have the benefit of a reserve of natural forest.","e_a_122.webp",3,1);

//Found
        this.questionCreator("What is the main diet of a mole?","Earthworms","Beetle","Dragonfly","Ants","Earthworms","It is a misconception that moles burrow into gardens to eat the roots of plants. They are actually after the earthworms that are found in garden soil. Moles love earthworms so much that they eat nearly their body weight worth of earthworms per day. Moles also consume insect larvae.","e_a_123.webp",3,1);

    }

    private void addAnimalsModerate()
    {
        //check

//Found
        this.questionCreator("Known as French Dog?","Bichon Frise","Labrador","Husky","Jack Russell","Bichon Frise","The Bichon Frise is often depicted as a French dog. Although the bichon breed type are originally Spanish, used as sailing dogs, the French developed them into a gentle lap-dog variety. The bichon type arose from the water dogs, and is descended from the poodle-type dogs and either the Barbet or one of the water spaniel class of breeds. Modern bichons have developed into four categories: the Bichon Frise or Tenerife, the Maltese, the Bolognaise, and the Havanese, often treated as separate breeds.","m_a_1.webp",3,2);

//Found
        this.questionCreator("What kind of animal is the emblem of the US republican political party?","Elephant","Dog","Cat","Deer","Elephant","The Republican party is represented by an elephant and the Democratic party is recognised by the symbol of a donkey.","m_a_2.webp",3,2);

//Found
        this.questionCreator("Which type of animals have more teeth?","Mammals","Reptiles","Sponge","Chordate","Mammals","Mammals are the vertebrates within the class Mammalia (/məˈmeɪliə/ from Latin mamma \"breast\"), a clade of endothermic amniotes distinguished from reptiles (including birds) by the possession of a neocortex (a region of the brain), hair, three middle ear bones, and mammary glands. Females of all mammal species nurse their young with milk, secreted from the mammary glands","m_a_3.webp",3,2);

//Found
        this.questionCreator("A cow normally has how many teats?","Four","Three","Six","Eight","Four","Many people believe that cows only have four teats; however, approximately 50% of cows have one or more extra teats. Most accessory teats have a slightly different structure compared to regular teats. They are usually smaller and located at the back of the udder. An udder is divided into four sections called quarter","m_a_4.webp",3,2);

//Found
        this.questionCreator("What is the only venomous snake found in Britain?","Adder","Anaconda","Cobra","Python","Adder","Adders, Vipera berus, are the only venomous snakes which are native to the UK. Among all snakes, they are also noted for their highly developed venom injecting mechanism. However, adders are not aggressive snakes. They only bite or use their venom as a last resort.","m_a_5.webp",3,2);

//Found
        this.questionCreator("What type of leaves does a Koala use for food?","Eucalyptus","Conifer","Angiosperm","Lycophytes","Eucalyptus","An adult koala eats between 200 to 500 grams of leaves each day. Koalas eat mainly eucalyptus leaves (gum leaves). Occasionally they will eat the leaves from some other native Australian trees, and they also use certain trees just for resting in. Koalas live in tall open eucalypt (gum tree) forests.","m_a_6.webp",3,2);

//Found
        this.questionCreator("What kind of animals live in an apiary?","Bees","Mosquito","Snake","Tiger","Bees","An apiary (also known as a bee yard) is a location where beehives of honey bees are kept. Apiaries come in many sizes and can be rural or urban depending on the honey production operation. Furthermore, an apiary may refer to a hobbyist\'s hives or those used for commercial or educational usage. It can also be a wall-less, roofed structure, similar to a gazebo which houses hives.","m_a_7.webp",3,2);

//Found
        this.questionCreator("What type of animal is a Tasmanian Devil?","Marsupial","Vertebrate","Mollucs","Tardigrade","Marsupial","All extant marsupials are endemic to Australasia and the Americas. A distinctive characteristic common to these species is that most of the young are carried in a pouch. Well-known marsupials include kangaroos, wallabies, koalas, possums, opossums, wombats, and Tasmanian devils.","m_a_8.webp",3,2);

//Found
        this.questionCreator("Animals living in what type of habitat are arboreal animals?","In or amongst trees","Desert","Savanna","Tundra","In or amongst trees","Arboreal animals are creatures who spend the majority of their lives in trees. They eat, sleep and play in the tree canopy. There are thousands of species that live in trees, including monkeys, koalas, possums, sloths, various rodents, parrots, chameleons, geckos, tree snakes and a variety of insects.","m_a_9.webp",3,2);

//Found
        this.questionCreator("What kind of animal is the source of mohair?","Angora Goat","Boer Goat","Saanen Goat","Alpine Goat","Angora Goat","Mohair is one of the oldest textile fibers in use. The Angora goat is thought to originate from the mountains of Tibet, reaching Turkey in the 16th century. However, fabric made of mohair was known in England as early as the 8th century.","m_a_10.webp",3,2);

//Found
        this.questionCreator("Lupus is the Latin name for what animal?","Wolf","Tiger","Lion","Dog","Wolf","Lupus is a constellation located in the deep Southern Sky. Its name is Latin for wolf.","m_a_11.webp",3,2);

//Found
        this.questionCreator("In the Jungle Book, what kind of creature was Baloo?","Bear","Monkey","Tiger","Leopard","Bear","Baloo may have been inspired by sloth bears, like this rescue animal. The true identity of Kipling\'s “sleepy brown bear” is a bit of an enigma: Baloo\'s physical description in the book would suggest a sloth bear, but his diet of nuts and honey runs counter to that species\' insect-eating preferences.","m_a_12.webp",3,2);

//Found
        this.questionCreator("How do bees communicate with each other?","Dancing","Talk","Chemical Communication","Auditory","Dancing","Bees cannot talk to each other so they communicate through dances, vibrations and body chemical signals. The scout bees have the task of finding new pollen, nectar and water sources. Once they have sourced these they return to the hive and perform either a round or waggle dance across the honeycomb.","m_a_13.webp",3,2);

//Found
        this.questionCreator("When caterpillar changes into an adult butterfly what is the change called?","Metamorphosis","Chrysalis","Chorion","None of these","Metamorphosis","Butterfly Life Cycle. The butterfly and moth develop through a process called metamorphosis. This is a Greek word that means transformation or change in shape. Insects have two common types of metamorphosis.","m_a_14.webp",3,2);

//Found
        this.questionCreator("The longest beetle in the world is how long?","6 inches","4 inches","3 inches","9 inches","6 inches","The longest species of beetle in terms of body size alone is the titan beetle Titanus giganteus of South America, with a body length of 15 cm (6 in)","m_a_15.webp",3,2);

//Found
        this.questionCreator("Animals without backbones are called what?","Invertebrates","Mammals","Reptiles","None of the above","Invertebrates","Animals without backbones are called invertebrates. They range from well known animals such as jellyfish, corals, slugs, snails, mussels, octopuses, crabs, shrimps, spiders, butterflies and beetles to much less well known animals such as flatworms, tapeworms, siphunculids, sea-mats and ticks.","m_a_16.webp",3,2);

//Found
        this.questionCreator("A fluke is what kind of animal?","Worm","Insect","Bryozoa","Kray","Worm","Trematoda is a class within the phylum Platyhelminthes. It includes two groups of parasitic flatworms, known as flukes.","m_a_17.webp",3,2);

//Found
        this.questionCreator("An abalone is what kind of animal?","Marine Snail","Leech","Snake","Dog","Marine Snail","Abalone are marine snails. Their taxonomy puts them in the family Haliotidae which contains only one genus, Haliotis, which once contained six subgenera. These subgenera have become alternate representations of Haliotis. The number of species recognized worldwide ranges between 30 and 130 with over 230 species-level taxa described. The most comprehensive treatment of the family considers 56 species valid, with 18 additional subspecies.","m_a_18.webp",3,2);

//Found
        this.questionCreator("The study of birds eggs is called what?","Oology","Abiology","Aceology","Acology","Oology","Oology (or oölogy) is a branch of ornithology studying bird eggs, nests and breeding behaviour. The word is derived from the Greek \"oion\", meaning egg. Oology can also refer to the hobby of collecting wild birds\' eggs, sometimes called egg collecting, birdnesting or egging, which is now illegal in many jurisdictions.","m_a_19.webp",3,2);

//Found
        this.questionCreator("On a rabbit where would you find a scut?","Tail","Forehead","Shoulder","Nose","Tail","Also known as a \'scut\' the rabbit\'s tail is more than decoration! In wild rabbits the underside is pale and is used as a danger signal and for communication when several rabbits are feeding over a big area.","m_a_20.webp",3,2);

//Found
        this.questionCreator("What kind of animal is mojo jojo","Monkey","Boar","Dog","Giraffe","Monkey","He is often called a monkey. This is scientifically incorrect as Mojo is a chimpanzee, one of the species of apes. Also noted, Mojo Jojo is also responsible for creating the Powerpuff Girls by pushing Professor Utonium to hit the Chemical X and create the Powerpuff Girls.","m_a_21.webp",3,2);

//Found
        this.questionCreator("What kind of dog is Hachiko?","Akita","Bulldog","Pitbull","German Shepherd","Akita","Akita. The Akita is a large Japanese breed known for its extreme loyalty to its family. The American film, Hachi: A Dog\'s Tale, is the true story of an Akita named Hachiko.","m_a_22.webp",3,2);

//Found
        this.questionCreator("Where you can find Hachiko statue?","Shibuya, Tokyo ,Japan","Africa","North Korea","Sourth Korea","Shibuya, Tokyo ,Japan","Akita dog \"Hachi\" which kept waiting at Shibuya station for the owner\'s return. In front of Shibuya station, the bronze statue of the bee was set up as \"Hagi-kun Hachiko statue\" and it is a symbol of the meeting place in Shibuya.","m_a_23.webp",3,2);

//Found
        this.questionCreator("Which type of semi aquatic animal is a lutra-lutra?","Otter","Sea cows","Sea lion","Walrus","Otter","Otters are carnivorous mammals in the subfamily Lutrinae. The 13 extant otter species are all semiaquatic, aquatic or marine, with diets based on fish and invertebrates. Lutrinae is a branch of the weasel family Mustelidae, which also includes badgers, honey badgers, martens, minks, polecats, and wolverines.","m_a_24.webp",3,2);

//Found
        this.questionCreator("How many times does domestic cat smell have?","14","2","3","10","14","Smell. A domestic cat\'s sense of smell is about fourteen times as strong as humans\'. Cats have twice as many receptors in the olfactory epithelium (i.e. smell-sensitive cells in their noses) as people do, meaning that cats have a more acute sense of smell than humans.","m_a_25.webp",3,2);

//Found
        this.questionCreator("What animal is the symbol of long life in Korea?","Deer","Venom","Camel","Cat","Deer","People considered the deer as a holy animal due to its beautiful appearance and mild temper. They always travel in herds and whenever they move to a different location, they raise their heads to search for an stragglers that do not follow. When an image of a deer was painted with pine trees, maples, rocks, or herbs, it usually meant longevity.","m_a_26.webp",3,2);

//Found
        this.questionCreator("The llama belongs to what family to what family of animals?","Camel","Cat","Lion","Tiger","Camel","This South American animal is related to camels and, like camels, they were were domesticated. The guanaco is the largest wild member of the camelid family in South America, and is believed to be the ancestor of the domestic llama.","m_a_27.webp",3,2);

//Found
        this.questionCreator("In Peru, what animal provides 50% of all the protein eatin?","The Guinea Pig","Middle White pig","Mulefoot","Chester White","The Guinea Pig","Since its initial role in religious ceremonies, its consumption as a general foodstuff has become far more widespread. In fact, an estimated 65 million guinea pigs are consumed annually in Peru. That\'s a lot of guinea pig! There\'s now even an annual festival in the town of Huacho where guinea pigs are dressed up and judged in various categories including “fattest” and “best dressed.” There\'s even a fashion show! You can guess what becomes of the models at the after-show party!","m_a_28.webp",3,2);

//Found
        this.questionCreator("What animal pollinates banana plants in the wild?","Bats","Monkeys","Tigers","Lions","Bats","Bats pollinate wild bananas and disperse their seeds. But the commercial bananas we eat have been seedless and without the need of pollination for thousands of years. They\'re grown only from suckers cut off a mother plant and transplanted around the tropics.","m_a_29.webp",3,2);

//Found
        this.questionCreator("A fennec is what type of animal?","Desert Fox","Giraffe","Blue Whale","Sperm Whale","Desert Fox","The fennec fox or fennec (Vulpes zerda) is a small nocturnal fox found in the Sahara of North Africa, the Sinai Peninsula, South West Israel (Arava desert) and the Arabian desert. Its most distinctive feature is its unusually large ears, which also serve to dissipate heat. ... The fennec is the smallest species of canid.","m_a_30.webp",3,2);

//Found
        this.questionCreator("What kind of creature always gives birth to same sex twins?","Armadillo","Komodo Dragon","Lizards","Bees","Armadillo","So no one knows, without a DNA test. Only one animal consistently gives birth to identical twins, Durrant says, and that\'s the nine-banded armadillo. \"They ovulate only one egg,\" Durrant says, \"and then the embryo splits four ways, and four identical quadruplets are born.\"","m_a_31.webp",3,2);

//Found
        this.questionCreator("A markhor is what type of animal?","Wild goat","Bear","Clown Fish","Police Dog","Wild goat","The markhor is the National animal of Pakistan. The word \"Markhor\" is Persian for \"Snake Eater\". They mostly live in the Northern Areas of Pakistan especially in the Chitral, Ghizar and Hunza regions.","m_a_32.webp",3,2);

//Found
        this.questionCreator("What type of insect has the best eyesight?","Dragonfly","Dragons","Dragonest","Dragonica","Dragonfly","Dragonflies have very large compound eyes that take up most of their heads. They require very good vision to catch small flying insects on the wing for food.","m_a_33.webp",3,2);

//Found
        this.questionCreator("A cow\'s stomach has how many chambers?","4","3","2","1","4","A cow does, indeed, have four stomachs. Or, at least, it has a stomach divided into four separate chambers, which amounts to the same thing. Nor, of course, are cows alone in this.","m_a_34.webp",3,2);

//Found
        this.questionCreator("How many humps does an African camel have?","1","3","4","5","1","The \"D\" in Dromedary makes one hump, and the \"B\" in Bactrian makes two humps! There are other differences between Dromedary and Bactrian camels besides the number of humps they have. For example, Dromedaries come from the deserts of West Asia (the Arabian Peninsula) and Africa.","m_a_35.webp",3,2);

//Found
        this.questionCreator("Who are the queen bee\'s closest servants in a beehive?","Drones","Carpenter bee","Apidae","Andrena","Drones","A drone is a male bee. Unlike the female worker bee, drones do not have stingers and gather neither nectar nor pollen. A drone\'s primary role is to mate with an unfertilized queen.","m_a_36.webp",3,2);

//Found
        this.questionCreator("What does a carpophagus animal feed on?","Fruit","Leaves","Wood","Cans","Fruit","carpophagus is a species of typical bark beetle in the family Curculionidae , They are usually found around the Gulf of Mexico and the upper regions of South America.","m_a_37.webp",3,2);

//Found
        this.questionCreator("Which animal has rectangular pupils?","Goat","Pig","Dog","Domestic Cat","Goat","Rectangular: Sheep, Goats, Octopuses and Toads have these rectangular shaped pupils. Typically classified as prey, these animals need to have a defense both day and night.","m_a_38.webp",3,2);

//Found
        this.questionCreator("What kind of animal mates only once for 12 hours and can sleep for three years?","Snail","Bear","Snake","Dog","Snail","Snail can sleep for 3 years. The reason snails sleep are they alike many other animals hibernate. Hibernation means that snails sleep when weather becomes excessively cold; it is a necessary for the survival of snails. ... Snails will bury themselves in the ground and close their shells off with their own slime.","m_a_39.webp",3,2);

//Found
        this.questionCreator("A typical mayfly lives for how many days?","1","2","3","4","1","The subimago stage does not survive for long, rarely for more than 24 hours. In some species, it may last for just a few minutes, while the mayflies in the family Palingeniidae have sexually mature subimagos and no true adult form at all.","m_a_40.webp",3,2);

//Found
        this.questionCreator("What is a fox\'s home called?","Den","Don","Dens","Din","Den","A fox\'s home is called a “den”. It can be a hole in the ground or elsewhere, such as under a garden shed. Hunters often try to scare foxes out of their dens by sending in dogs so that the hunter can then shoot the foxes.","m_a_41.webp",3,2);

//Found
        this.questionCreator("This flightless bird reaches height of 5 feet (1.5 m). It lives up to 40 years in captivity, but only around 20 in the wild.","Rhea","Vulture","Ostrich","Eagle","Rhea","American Rhea can reach 5 feet in height and weight of up to 55 pounds. Darwin\'s Rhea can reach 3 feet in height and 22 pounds in weight. Body of rhea is covered with grey-brown plumage with dark patches on the neck and back.","m_a_42.webp",3,2);

//Found
        this.questionCreator("This is the largest bird on earth and lays the largest eggs. Its lifespan reaches 40 years.","Ostrich","Swallow","Eagle","Falcon","Ostrich","Eggs laid by the ostrich can weigh 1.4 kg (3.1 lb) and are the largest eggs in the world today. The largest living bird by wingspan is the Wandering Albatross (Diomedea exulans) of the sub-Antarctic oceans.","m_a_43.webp",3,2);

//Found
        this.questionCreator("This bird can live for up to 33 years in captivity. You can find it around Malaya, Sumatra, Java and Borneo.","Rhinoceros hornbill","Swan","Rhea","Macaws","Rhinoceros hornbill","The rhinoceros hornbill, lives up to 33 years in captivity. the rhea, a large, flightless bird that lives up to 40 years in captivity, 20 year in the wild. the vulture, a scavenger which can live up to 30 years in captivity. the ostrich , which has a life span of up to 40 years!","m_a_44.webp",3,2);

//Found
        this.questionCreator("This scavenger bird can live for up to 30 years. It is now considered an endangered species.","Vulture","Eagle","Swift","Falcon","Vulture","This vulture is a scavenger and it often makes the initial cut into a fresh carcass. It also displaces smaller New World vulture species from a carcass. King Vultures have been known to live for up to 30 years in captivity.","m_a_45.webp",3,2);

//Found
        this.questionCreator("This water bird is monogamous and pairs for life. It can live for up to 50 years in captivity.","Swan","Duck","Flamingo","Goose","Swan","The idea that swans only sing when they are dying, the so-called swan song, is a myth. ... Once courtship is complete, male and female swans really are bonded for life, with few exceptions. This is unusual. Most other birds will raise their young as a pair for one season, but move on to new mates the next.","m_a_46.webp",3,2);

//Found
        this.questionCreator("The plumage on this bird\'s head is dark at first, but turns white when the bird grows up. It can have a lifespan of up to 50 years.","Bald eagle","Hawk","Turkey vulture","Peregrine falcon","Bald eagle","The average lifespan of bald eagles in the wild is around 20 years, with the oldest confirmed one having been 38 years of age. In captivity, they often live somewhat longer. In one instance, a captive individual in New York lived for nearly 50 years.","m_a_47.webp",3,2);

//Found
        this.questionCreator("These birds have a bald spot on their heads, covered by an erectile crest. They may reach 75 years of age.","Cockatoo","Macaw","Parrot","Pigeon","Cockatoo","All Cockatoos have a bald spot on the top their head, covered by an erectile crest . ... Cockatoos are natural born escape artists and puzzle solvers. Their powerful beaks can break open most nuts and destroy the hardest of woods. They are flock birds who mate for life and often bond to their owner as to a mate.","m_a_48.webp",3,2);

//Found
        this.questionCreator("These birds have string toes and use them to catch stuff. Their beaks are strong, so that they can break nut shells. They live for up to 60 - 70 years.","Macaw","Swift","Sparrow","Parrot","Macaw","Macaws belong to the Parrot Family. The Hyacinth Macaw is the largest of all the parrots and has a wingspan of approximately 4 feet. There are roughly 20 species of macaws, many of which are endangered and critically endangered. Macaws are long-lived, living for as long as 60 years in the wild.","m_a_49.webp",3,2);

//Found
        this.questionCreator("This fish, the largest member of the sunfish family, generally has light greenish to brownish sides with a dark lateral line, which tends to break into blotches towards the tail.","Largemouth Bass","Trout","Crappie","Bluegill","Largemouth Bass","The largemouth bass is the largest member of the sunfish family and has been known to reach weights in excess of ten pounds. It closely resembles the smallmouth, but differs by its long upper jaw which extends well beyond the eye, and its pronounced wide, solid black lateral band. In addition, the largemouth is more of a dark green color than the smallmouth.","m_a_50.webp",3,2);

//Found
        this.questionCreator("This fish is olive-green to blue-gray on the back with silvery to brassy sides and white belly. It is easily recognized by the seven or eight prominent black horizontal stripes along the sides.","Striper (Striped Bass)","Trout","Large Mouth Bass","Crappie","Striper (Striped Bass)","The striper is the largest member of the temperate bass family. Body coloration is olive-green to blue-gray on the back with silvery to brassy sides and white on the belly. It is easily recognized by the seven or eight prominent black uninterrupted horizontal stripes along the sides. The stripes are often interrupted or broken and are usually absent on young fish of less than six inches. The striper is longer and sleeker and has a larger head than its close and similar looking relative, the white bass, which rarely exceeds three pounds.","m_a_51.webp",3,2);

//Found
        this.questionCreator("This freshwater fish, with two rows of teeth in the upper jaw, has a long olive or greenish brown body. The sides are mottled toward the head, with large black spots toward the rear and on the rear fins.","Alligator Gar","Chain Prickel","Southern Pike","Bowfin","Alligator Gar","The alligator gar can be distinguished from all other gars by the two rows of teeth in the upper jaw, its broader snout, and its large size when fully grown. The alligator gar most closely resembles members of the pike family in body shape and fin placement, although the tail of these fishes is forked, not rounded.","m_a_52.webp",3,2);

//Found
        this.questionCreator("This fish is distinguished by its scaleless body, broad flat head, sharp heavy pectoral and dorsal spines, and long whisker-like barbels about the mouth.","Channel Catfish","Walleye","Large Mouth Bass","Grouper","Channel Catfish","Catfish have a very keen sense of smell and taste. The whiskers, known as “barbels,” are around their mouth with the purpose of helping them locate food in the dark waters. In addition, they have taste buds all over the surface of their body.","m_a_53.webp",3,2);

//Found
        this.questionCreator("This fish, known as shellcracker, is light olive-green to gold, with red or orange flecks on the breast.","Redear Sunfish","Crappie","Red Bass","Blue Gill","Redear Sunfish","Redear sunfish. The redear sunfish (Lepomis microlophus, also known as the shellcracker, Georgia bream, cherry gill, chinquapin, improved bream, rouge ear sunfish and sun perch) is a freshwater fish in the Centrarchidae family and is native to the southeastern United States.","m_a_54.webp",3,2);

//Found
        this.questionCreator("This fish has a small mouth and oval-shaped, almost rounded body. Body coloration is highly variable depending on size, sex, spawning, water color and bottom type.","Bluegills","Bass","Redear Sunfish","Crappie","Bluegills","The bluegill is a species of freshwater fish sometimes referred to as bream, brim, or copper nose. It is a member of the sunfish family Centrarchidae of the order Perciformes. It is native to North America and lives in streams, rivers, lakes, and ponds. It is commonly found east of the Rockies.","m_a_55.webp",3,2);

//Found
        this.questionCreator("This fish, often referred to as mudfish, is easily recognized by its flattened head, long, stout body and large mouth full of small, sharp teeth.","Bowfin","Pike","Gar","Grass Pickerel","Bowfin","The fishes mouth opening is broad, extending all the way to beneath the eyes, and is also perfectly horizontal. All these physical attributes have remained since prehistoric times, but as environments have changed the bowfin have shown that their form not need to, because it is so advantageous in the places they inhabit.","m_a_56.webp",3,2);

//Found
        this.questionCreator("This freshwater game fish spawns in spring in shallow water over sand and gravel substrates and is easily caught at that time. After spawning, it moves offshore into deeper, cooler areas.","Crappie","Trout","Smallmouth Bass","Bluegill","Crappie","The Crappie is also known as the strawberry bass, speckled bass (or \"specks\"), calico bass, papermouth, and sauc-au-lait (translation \"bag of milk\") also one of the largest crappie ever caught weighed 6 pounds.","m_a_57.webp",3,2);

//Found
        this.questionCreator("This fish, with a distinct dark, vertical bar below the eye, is deep olive-green on the back, shading to a creamy yellow on the belly. Olive green blotches are present within distinct black chain-like or interwoven markings on the sides.","Chain Pickerel","Alligator Gar","Northern Pike","Redear Sunfish","Chain Pickerel","In fact, chain pickerel have been known to enter brackish water in winter. Pickerel are solitary fish, hunting by sight, and rarely travel far for food. Instead, you\'ll usually find them hiding in aquatic vegetation, holding motionless until they ambush their prey.","m_a_58.webp",3,2);

//Found
        this.questionCreator("This fish, which has a few irregular, large dark spots on the body, is olive-brown or deep green along the back and upper sides, with a silver-white belly","Longnose Gar","Black chain pike","River pike","Alligator Gar","Longnose Gar","The longnose gar is a primitive ray-finned fish of the gar family. It is also known as the needlenose gar. L. osseus is found along the east coast of North and Central America in freshwater lakes and as far west as Kansas and Texas and southern New Mexico.","m_a_59.webp",3,2);

//Found
        this.questionCreator("What medical condition can be improved due to interactions with cats?","High blood pressure","Diabetes","Epilepsy","Osteoporosis","High blood pressure","Therapy cats have been used as companions to help the recovery and well-being of people who have had strokes, high blood pressure, anxiety, or depression. Therapy cats have also been used as companions at juvenile detention centers and for children with developmental disabilities and for children with language, speech and hearing problems. Therapy cats are also sometimes used in hospitals to relax children who are staying there.","m_a_60.webp",3,2);

//Found
        this.questionCreator("The \"Beckoning Cat\", also known as \"Welcoming Cat\", is a common sculpture which originated in this country?","Japan","USA","China","Korea","Japan","Maneki-neko​The Maneki-neko ( literally \"Beckoning Cat\"; also known as Welcoming Cat, Lucky Cat, Money Cat, or Fortune Cat) is a common Japanese figurine (lucky charm, talisman), usually made of ceramic, which is believed to bring good luck to the owner.","m_a_61.webp",3,2);

//Found
        this.questionCreator("Which of the following supposedly owned a female cat named Muezza?","Prophet Mohammad","Gautama Buddha","Jesus Christ","None of these","Prophet Mohammad","The Islamic prophet Muhammad always loved cats. His favorite feline of all was Muezza. Muhammad was so attached to him, he\'d let the cat sit on his lap while he gave his sermons. He\'d even drink water that Muezza had previously been lapping up","m_a_62.webp",3,2);

//Found
        this.questionCreator("Who composed the famous musical \"Cats\"?","Andrew Lloyd Webber","Irving Berlin","Stephen Sondheim","Leonard Bernstein","Andrew Lloyd Webber","Cats is a sung-through musical composed by Andrew Lloyd Webber, based on Old Possum\'s Book of Practical Cats by T. S. ... Directed by Trevor Nunn and choreographed by Gillian Lynne, Cats first opened in the West End in 1981 and then with the same creative team on Broadway in 1982.","m_a_63.webp",3,2);

//Found
        this.questionCreator("A baby swan is called a what?","Cygnet","Swanling","Duckling","Goosling","Cygnet","Swans are birds of the family Anatidae within the genus Cygnus. The swans\' close relatives include the geese and ducks. Swans are grouped with the closely related geese in the subfamily Anserinae where they form the tribe Cygnini. Sometimes, they are considered a distinct subfamily, Cygninae.","m_a_64.webp",3,2);

//Found
        this.questionCreator("What is the leader of a heard of elephants called?","Matriarch","Bull","Cow","Calf","Matriarch","The elephant herd is led by the oldest and largest female cow known as the matriarch. She is usually the one who was the most closely related to the previous matriarch. The rest of the herd is made up of the matriarch\'s other daughters and their calves.","m_a_65.webp",3,2);

//Found
        this.questionCreator("What is a John Dory","Fish","Cat","Dog","Person","Fish","John Dory, St Pierre or Peter\'s Fish, refers to fish of the genus Zeus, especially Zeus faber, of widespread distribution. It is an edible benthic coastal marine fish with a laterally compressed olive-yellow body which has a large dark spot, and long spines on the dorsal fin.","m_a_66.webp",3,2);

//Found
        this.questionCreator("What is a baby hippo called","Calf","Baby hippo","Pup","Ivory","Calf","More than one hippopotamus are called hippopotami, and \'hippopotamuses\', or \'hippos\' is also used. ... About 30 hippos live together in groups. A male hippopotamus is known as a bull. A female hippopotamus is called a cow, and a baby hippo is called a calf.","m_a_67.webp",3,2);

//Found
        this.questionCreator("Which of following creatures has the power to grow lost parts?","Starfish","Crab","Squirrel","Squid","Starfish","Some species of starfish have the ability to regenerate lost arms and can regrow an entire new limb given time. A few can regrow a complete new disc from a single arm, while others need at least part of the central disc to be attached to the detached part.","m_a_68.webp",3,2);

//Found
        this.questionCreator("Which of the following creatures has the most toxic venom?","Krait","Kukri snake","Scorpion","Cobra","Krait","Its venom is highly haemotoxic. The Saw-scaled Viper is responsible for more human deaths in Asia that all the other venomous Asian snakes combined. Its highly haemotoxic venom is said to be 5 times more toxic than cobras and 16 more toxic than the Russell's Viper.","m_a_69.webp",3,2);

//Found
        this.questionCreator("Which living being has on an average the highest life-span?","Tortoise","Man","Pelican","Cat","Tortoise","Tortoises generally have one of the longest lifespans of any animal, and some individuals are known to have lived longer than 150 years. Because of this, they symbolize longevity in some cultures such as China.","m_a_70.webp",3,2);

//Found
        this.questionCreator("Which animal can cross-breed with wolf?","Domestic dog","Prairie dog","Dhole","Fox","Domestic dog","Wolves and dogs are interfertile meaning they can breed and produce viable offspring. In other words, wolves can interbreed with any type of dog, and their offspring are capable of producing offspring themselves.","m_a_71.webp",3,2);

//Found
        this.questionCreator("Where are orangutans found?","Borneo","Italy","South America","North Africa","Borneo","The orangutans are three extant species of great apes native to Indonesia and Malaysia. Orangutans are currently only found in the rainforests of Borneo and Sumatra. Classified in the genus Pongo, orangutans were originally considered to be one species.","m_a_72.webp",3,2);

//Found
        this.questionCreator("Which is the slowest moving animal?","Three-toed sloth","Polar bear","Hippopotamus","Elephant seal","Three-toed sloth","The Slowest Mammal in the World. Three-toed sloths are some of the slowest and seemingly laziest creatures in the world. Instead of evolving to eat more, they evolved to do less.","m_a_73.webp",3,2);

//Found
        this.questionCreator("Which is the world largest and rarest lizard?","Komodo dragon","Chameleon","Regal-horned lizard","Garden lizard","Komodo dragon","The Komodo dragon is the largest living lizard in the world. Komodo dragons are limited to a few Indonesian islands of the Lesser Sunda group, including Rintja, Padar and Flores, and of course the island of Komodo, the largest at 22 miles (35 kilometers) long.","m_a_74.webp",3,2);

//Found
        this.questionCreator("Which of the following animal's teeth are strong enough to fell a tree?","Beaver","Squirrel","Chipmunk","Shrew","Beaver","Even without brushing their teeth or drinking fluoridated water, beavers have remarkably strong teeth good for gnawing on wood. A new study shows that their tough teeth are all thanks to a key component built into their chemical structure, and its iron.","m_a_75.webp",3,2);

//Found
        this.questionCreator("Where are skunks found?","North America","South America","Africa","Europe","North America","Striped skunks are native to North America, and can be found in Northern Mexico, throughout the United States, and as far north as Central Canada. Other species of skunks, such as the spotted skunk and the hog-nosed skunk, can be found further south, ranging from Canada to Central and South America.","m_a_76.webp",3,2);

//Found
        this.questionCreator("Where is toucan found?","South America","China","Australia","England","South America","Toucans are native to the Neotropics, from Southern Mexico, through Central America, into South America south to northern Argentina. They mostly live in the lowland tropics, but the montane species from the genus Andigena reach temperate climates at high altitudes in the Andes and can be found up to the tree line.","m_a_77.webp",3,2);

//Found
        this.questionCreator("Which birds helps in conditioning timber pests?","Woodpecker","Indian grey shrike","Tailor-bird","Hoopoe","Woodpecker","An interesting and familiar group of birds. Their ability to peck into trees in search of food or excavate nest cavities is well known. They prefer snags or partially dead trees for nesting sites, and readily peck holes in trees and wood structures in search of insects beneath the surface","m_a_78.webp",3,2);

//Found
        this.questionCreator("The horns of a goat are made out of what substance?","Keratin","Insulin","Amylase","Progesterone","Keratin","The main composition of horns is keratin, the same material that makes up hair and fingernails. The core of a horn is, however, made of bone.","m_a_79.webp",3,2);

//Found
        this.questionCreator("The cotton top of tamarin is a small species of what type of animal?","Monkey","Bear","Goat","Sheep","Monkey","The Cotton top Tamarin (Saguinus oedipus), also known as the Pinche Tamarin, is a small New World monkey weighing less than 1lb (0.5 kg). It is an endangered species found in tropical forest edges and secondary forests where it is arboreal and diurnal.","m_a_80.webp",3,2);

//Found
        this.questionCreator("Some flamingos are color pink because of what?","Eating algae","Eating shrimps","Eating plankton","Eating lotus","Eating algae","Actually, flamingos are not pink. They are born with grey feathers, which gradually turn pink in the wild because of a natural pink dye called canthaxanthin that they obtain from their diet of brine shrimp and blue-green algae.","m_a_81.webp",3,2);

//Found
        this.questionCreator("A Finnish Spitz is what type of creature?","Dog","Snake","Cat","Bird","Dog","Finnish Spitz is a breed of dog originating in Finland.","m_a_82.webp",3,2);

//Found
        this.questionCreator("How many hearts does an octopus have?","three","eight","one","six","three","Octopuses have a closed circulatory system, where the blood remains inside blood vessels. Octopuses have three hearts; a systemic heart that circulates blood round the body and two branchial hearts that pump it through each of the two gills","m_a_83.webp",3,2);

//Found
        this.questionCreator("What is the color of a polar bears skin?","Black","Pink","White","Brown","Black","The bear's stark white coat provides camouflage in surrounding snow and ice. But under their fur, polar bears have black skin the better to soak in the sun's warming rays. These powerful predators typically prey on seals.","m_a_84.webp",3,2);

//Found
        this.questionCreator("Welsh and Tamworth are breeds of which animal?","Pig","Cow","Sheep","Dog","Pig","Tamworth is a breed of a domestic pig originating in its namesake Tamworth; it is among the oldest of pig breeds.","m_a_85.webp",3,2);

//Found
        this.questionCreator("What is the top speed of a bottle nosed dolphin?","twenty one mph","twenty six mph","thirty one mph","fourty mph","twenty one mph","Bottlenose Dolphins typically swim at a speed of 5-11 kilometers per hour (3-6 mph); for short times, they can reach peak speeds of 35 kilometers per hour (21 mph).","m_a_86.webp",3,2);

//Found
        this.questionCreator("How many species of bumble bees are there around the world?","two hundred fifty","twenty-five","one hundred fifty","five hundred","two hundred fifty","There are about 25 British species according to Prys-Jones (19 species of Bombus and 6 species of cuckoo bumblebees). And about 65 species in Europe, and 250 species of bumblebee have been discovered so far worldwide","m_a_87.webp",3,2);

//Found
        this.questionCreator("Which animal can create the loudest sound among any living creature?","Humpback whale","Whale shark","Gibbon","Howler monkey","Humpback whale","Produce one hundred eighty eight decibels of sound. That's louder than a one hundred fifty decibel rock concert, which can damage hearing.","m_a_88.webp",3,2);

//Found
        this.questionCreator("Which of the following is not a true fish?","Sucker fish","Silver fish","Saw fish","Hammer fish","Sucker fish","Sawfishes, also known as carpenter sharks, are a family of rays characterized by a long, narrow, flattened rostrum, or nose extension, lined with sharp transverse teeth, arranged in a way that resembles a saw","m_a_89.webp",3,2);

//Found
        this.questionCreator("Pashmina shawl is made from hair of?","Goat","Sheep","Rabbit","Yak","Goat","Pashmina refers to a type of cashmere wool and textiles made from it. The name comes from Pashmineh, made from Persian pashm (= \"wool\"). This wool comes from changthangi or pashmina goat—a special breed of goat indigenous to high altitudes of the Himalayas","m_a_90.webp",3,2);

//Found
        this.questionCreator("Which of the following is the National aquatic animal of India?","Dolphin","Sea turtle","Shark","Dugong","Dolphin","River Dolphin is the National Aquatic Animal of India. This mammal is also said to represent the purity of the holy Ganga as it can only survive in pure and fresh water. Platanista gangetica has a long pointed snout and also have visible teeth in both the upper and lower jaws.","m_a_91.webp",3,2);

//Found
        this.questionCreator("The fish that can taste with its whole body is the?","Catfish","Clown fish","Jelly fish","Flying fish","Catfish","That's because this creature has taste buds not only in its mouth, but all over its body. Catfish (order Siluriformes), those beady-eyed fish named for their feline-like whiskers; typically have more than 100, 000 taste buds. Some large catfish can have as many as 175, 000.","m_a_92.webp",3,2);

//Found
        this.questionCreator("What kind of animal is a dhole?","Wild dog","Wild cat","Wild ass","Wild buffalo","Wild dog","The dhole is a canid native to Central, South and Southeast Asia. Other English names for the species include Asiatic wild dog, Indian wild dog, whistling dog, red dog, and mountain wolf.","m_a_93.webp",3,2);

//Found
        this.questionCreator("Which is the only bird that can fly backwards?","Hummingbird","Sunbird","Kingfisher","Honey eater","Hummingbird","Hummingbirds are incredible flyers, with the ruby-throated hummingbird beating its wings 80 times every second, an ability that inspired this blog's name. These tiny birds can fly forwards, hover, and are the only known birds to fly backwards as well.","m_a_94.webp",3,2);

//Found
        this.questionCreator("Which one of the following is not a true snake?","Glass snake","Blind snake","Sea snake","Tree snake","Glass snake","The glass lizards or glass snakes are a genus, Ophisaurus of reptiles that resemble snakes but are actually lizards. Although most species have no legs, their head shapes, movable eyelids, and external ear openings identify them as lizards.","m_a_95.webp",3,2);

//Found
        this.questionCreator("Kiwi is the native bird of which of the following countries?","New Zealand","South America","Australia","Zimbabwe","New Zealand","There are two species of Kiwis in New Zealand. Brown Kiwis are found in forested areas in the North Island, Fiord land, South Westland and Stewart Island. Spotted Kiwis are found on offshore islands and forests in the North of the South Island","m_a_96.webp",3,2);

//Found
        this.questionCreator("The Asian Elephants trunk contains up to how many muscles?","sixty thousand","six hundred","six thousand","ten thousand","sixty thousand","The trunk contains as many as 60, 000 muscles, which consist of longitudinal and radiating sets. The longitudinal are mostly superficial and subdivided into anterior, lateral, and posterior","m_a_97.webp",3,2);

//Found
        this.questionCreator("The Bichon Frise is a breed of what?","Dog","Cow","Sheep","Goat","Dog","The Bichon Frise is a very cheerful breed, with a happy, sometimes clownish disposition, accented by its tail curled up high on its rump. They were very much in vogue in sixteenth-century France; they displaced the Maltese in court as the favorite.","m_a_98.webp",3,2);

//Found
        this.questionCreator("How long can a hippo hold their breath in the water?","five minutes","ten minutes","one minute","fifteen minutes","five minutes","Their nostrils close, and they can hold their breath for 5 minutes or longer when submerged. Hippo can even underwater, using a reflex that allows them to bob up, take a breath, and sink and back down without waking up.","m_a_99.webp",3,2);

//Found
        this.questionCreator("How many different types of hyenas are there?","four","six","ten","five","four","There are 4 known species of hyena the spotted hyena , the striped hyena, the brown hyena and the aardwolf.","m_a_100.webp",3,2);


    }


    private void addAnimalsDifficult()
    {
        //check

//Found
        this.questionCreator("What kind of animal can be a minke, grey, or bowhead?","Whale","Dog","Tiger","Lion","Whale","Cetacea is a widely distributed and diverse clade of aquatic mammals that today consists of whales, dolphins, and porpoises. Cetaceans are carnivorous and finned. Most species live in the sea, some in rivers. The name is derived from the Latin cetus \"whale\", itself from the Greek κῆτος kētos \"huge fish\".","d_a_1.webp",3,3);

//Found
        this.questionCreator("What type of fish are members of the class Asteroidea?","Starfish","Aardwolf","Admiral, indian `  red","African black crake","Starfish","Starfish or sea stars are star-shaped echinoderms belonging to the class Asteroidea. Common usage frequently finds these names being also applied to ophiuroids, which are correctly referred to as brittle stars or \"basket stars\".","d_a_2.webp",3,3);

//Found
        this.questionCreator("A lumpsucker is what type of creature?","A fish","A sea lion","spiny anteater","Azara's zorro","A fish","Lumpsucker. Lumpsuckers or lumpfish are mostly small scorpaeniform marine fish of the family Cyclopteridae. They are found in the cold waters of the Arctic, North Atlantic, and North Pacific oceans. The greatest number of species are found in the North Pacific.","d_a_3.webp",3,3);

//Found
        this.questionCreator("Dolphins travel in family groups called what?","Pods","American marten","Crab-eating fox","Dabchick","Pods","Porpoises belong to the family Phocoenidae and share a common ancestry with the Delphinidae. A group of dolphins is called a \"school\" or a \"pod\". Male dolphins are called \"bulls\", females \"cows\" and young dolphins are called \"calves\".","d_a_4.webp",3,3);

//Found
        this.questionCreator("What kind of animal is called a razorshell?","A mollusk","Australian pelican","Eastern quoll","Fairy penguin","A mollusk","The razor shell, Ensis magnus, also called razor clam, razor fish, or spoot (colloquially), is a bivalve of the family Pharidae.","d_a_5.webp",3,3);

//Found
        this.questionCreator("Coral and algae have what kind of relationship?","Symbiotic","Galah","Gila monster","Hen, sage","Symbiotic","Symbiosis is any type of a close and long-term biological interaction between two different biological organisms, be it mutualistic, commensalistic, or parasitic. The organisms, each termed a symbiont, may be of the same or of different species","d_a_6.webp",3,3);

//Found
        this.questionCreator("A sea horse is what kind of creature?","A fish","House crow","Jungle cat","Jungle kangaroo","A fish","Animals sold as \"freshwater seahorses\" are usually the closely related pipefish, of which a few species live in the lower reaches of rivers. The supposed true \"freshwater seahorse\" called H. aimei is not a valid species, but a synonym sometimes used for Barbour\'s and hedgehog seahorses.","d_a_7.webp",3,3);

//Found
        this.questionCreator("Which fish family is the pilchard a member?","Herring","Kingfisher, malachite","Least chipmunk","Malabar squirrel","Herring","Typically, herring are small, streamlined, schooling \"planktivores,\" or plankton-feeders. The nearly 200 true herring species in the family Clupeidae share several distinguishing characteristics. Herring are silvery fish with a single dorsal fin, no lateral line, and a protruding, bulldog-like lower jaw.","d_a_8.webp",3,3);

//Found
        this.questionCreator("What kind of birds are trained to catch and fetch fish?","Cormorants","Native cat","Ocelot","Paca","Cormorants","Cormorant fishing is a traditional fishing method in which fishermen use trained cormorants to fish in rivers.","d_a_9.webp",3,3);

//Found
        this.questionCreator("What is the smartest animal(except humans)?","Dolphin","Smoked herring","Native cat","Cormorants","Dolphin","Dolphins are often regarded as one of Earth\'s most intelligent animals, though it is hard to say just how intelligent. Comparing species\' relative intelligence is complicated by differences in sensory apparatus, response modes, and nature of cognition. Furthermore, the difficulty and expense of experimental work with large aquatic animals has so far prevented some tests and limited sample size and rigor in others.","d_a_10.webp",3,3);

//Found
        this.questionCreator("A squid has how many arms?","8","20","15","2","8","Squid are cephalopods of the order Teuthida, which comprises around 304 species. Like all other cephalopods, squid have a distinct head, bilateral symmetry, a mantle, and arms. Squid, like cuttlefish, have eight arms arranged in pairs and two, usually longer, tentacles.","d_a_11.webp",3,3);

//Found
        this.questionCreator("What form was the Egyptian god Sobek?","Crocodile","Pigeon, wood","Raccoon dog","Sable antelope","Crocodile","Sobek (also called Sebek, Sochet, Sobk, and Sobki), in Greek, Suchos (Σοῦχος) and from Latin Suchus, was an ancient Egyptian deity with a complex and fluid nature. He is associated with the Nile crocodile or the West African crocodile and is represented either in its form or as a human with a crocodile head.","d_a_12.webp",3,3);

//Found
        this.questionCreator("The only other animals other than humans that get pleasure out of sex?","Dolphins","Sidewinder","Tarantula","Vervet monkey","Dolphins","Dolphins are a widely distributed and diverse group of aquatic mammals. They are an informal grouping within the order Cetacea, excluding whales and porpoises, so to zoologists the grouping is paraphyletic.","d_a_13.webp",3,3);

//Found
        this.questionCreator("What kind of animal is a mud puppy?","Fish","Wallaby, agile","Yak","Zebra, common","Fish","The common mudpuppy (Necturus maculosus) is a species of salamander in the genus Necturus. They live an entirely aquatic lifestyle in the eastern part of North America in lakes, rivers, and ponds. They go through paedomorphosis and retain their external gills, thus resembling axolotls.","d_a_14.webp",3,3);

//Found
        this.questionCreator("Where would one find a fish\'s caudal fin?","Tail","Body","Teeth","None of these","Tail","The caudal fin is the tail fin (from the Latin cauda meaning tail), located at the end of the caudal peduncle and is used for propulsion. See body-caudal fin locomotion. (A) - Heterocercal means the vertebrae extend into the upper lobe of the tail, making it longer (as in sharks).","d_a_15.webp",3,3);

//Found
        this.questionCreator("The Anchovy belongs to what fish family?","Herring","Yellow-billed stork","Zorro, azara's","Wallaby, red-necked","Herring","An anchovy is a small, common forage fish of the family Engraulidae. Most species are found in marine waters, but several will enter brackish water and some in South America are restricted to fresh water.","d_a_16.webp",3,3);

//Found
        this.questionCreator("One of the most dangerous fish in the world.","Puffer","Snouth","Dorsal Fin","Caudal Fin","Puffer","The puffer, which is also called swellfish, or blowfish, is any member of a group of about 90 species of fishes of the family Tetraodontidae, noted for their ability when disturbed to inflate themselves so greatly with air and water that they become globular in form.","d_a_17.webp",3,3);

//Found
        this.questionCreator("One of the most dangerous fish in the world.","Red Lionfish","Catfish","Haddock","Herring","Red Lionfish","Lionfishes (Pterois) make up any of several species of showy Indo-Pacific fishes of the scorpion fish family, Scorpaenidae (order Scorpaeniformes). They are noted for their venomous fin spines, which are capable of producing painful, though rarely fatal, puncture wounds. The fishes have enlarged pectoral fins and elongated dorsal fin spines, and each species bears a particular pattern of bold, zebralike stripes.","d_a_18.webp",3,3);

//Found
        this.questionCreator("What kind of fish is a sardine also know as?","A young Pilchard","Tilapia","Salmon","Catfish","A young Pilchard","\"Sardine\" and \"pilchard\" are common names used to refer to various small, oily fish in the herring family Clupeidae. The term \"sardine\" was first used in English during the early 15th century and may come from the Mediterranean island of Sardinia, around which sardines were once abundant.","d_a_19.webp",3,3);

//Found
        this.questionCreator("What type of creature has the most teeth at one time?","Dolphins","Timber wolf","Tiger snake","Suricate","Dolphins","The Spinner Dolphin. The Spinner Dolphin, known for its acrobatic spins above the water, can have as many as 252 teeth in its long, thin jaws","d_a_20.webp",3,3);

//Found
        this.questionCreator("What animal has the biggest nose?","Goblin shark","Dover sole","Monoclonius.","Hammer head bat.","Goblin shark","The Goblin Shark is not a pretty shark, and that is being polite. It is a large shark, usually 3-4 m (10-13 ft), with a long, protruding snout and an unusual jaw.","d_a_21.webp",3,3);

//Found
        this.questionCreator("A male koala does what to attract a mate?","Belches","Tailless tenrec","Suricate","Rhea, gray","Belches","Females and males are ready to mate between 2 and 3 years of age. Males and females attract each other for mating through deep bellowing calls and scents. The males usually will initiate them and wait for females to respond. Mating can use up energy and the Koala doesn\'t have much of it to spare.","d_a_22.webp",3,3);

//Found
        this.questionCreator("What species of mammal can come in fairy or giant size?","Banded Armadillo","Rat, desert kangaroo","Purple moorhen","Orca","Banded Armadillo","Armadillos are small to medium-sized mammals. The smallest species, the pink fairy armadillo, is roughly chipmunk-sized at 85 g (3.0 oz) and 13–15 cm (5.1–5.9 in) in total length. The largest species, the giant armadillo, can be the size of a small pig and weigh up to 54 kg (119 lb), and can be 150 cm (59 in) long.","d_a_23.webp",3,3);

//Found
        this.questionCreator("Ninety percent of bird species are what?","Monogamous","Cormorant","King Fisher","Hummingbird","Monogamous","Monogamy is a form of relationship in which an individual has only one partner during their lifetime — alternately, only one partner at any one time — as compared to non-monogamy. The term is also applied to the social behavior of some animals, referring to the state of having only one mate at any one time.","d_a_24.webp",3,3);

//Found
        this.questionCreator("What is a group of woodpeckers called?","A Descent","Nilgai","Mouflon","Lily trotter","A Descent","A group of Blue Jays is often referred to as a “party” or a “band.” This may explain why Blue Jays often seem like they are hollering out, maybe they just might be singing or doing the Shout. A group of Woodpeckers hanging together is known as a \"descent\".","d_a_25.webp",3,3);

//Found
        this.questionCreator("What animal comes in woolly and spider types?","Monkey","Land iguana","Kangaroo, jungle","Kangaroo, red","Monkey","Monkeys range in size from the pygmy marmoset, which can be as small as 117 millimetres (4.6 in) with a 172-millimetre (6.8 in) tail and just over 100 grams (3.5 oz) in weight,[25] to the male mandrill, almost 1 metre (3.3 ft) long and weighing up to 36 kilograms (79 lb). Some are arboreal (living in trees) while others live on the savanna; diets differ among the various species but may contain any of the following: fruit, leaves, seeds, nuts, flowers, eggs and small animals (including insects and spiders).","d_a_26.webp",3,3);

//Found
        this.questionCreator("Furritus, or Latin for \"little thieves\" is the name for what animal?","Ferret","Indian tree pie","Huron","Great skua","Ferret","The ferret is the domesticated form of the European polecat, a mammal belonging to the same genus as the weasel, Mustela of the family Mustelidae. They typically have brown, black, white, or mixed fur.","d_a_27.webp",3,3);

//Found
        this.questionCreator("The pica pica is another name for what common bird?","Magpie","Gambel's quail","Egyptian viper","Dove, white-winged","Magpie","The Eurasian magpie or common magpie is a resident breeding bird throughout northern part of Eurasian continent. It is one of several birds in the crow family designated magpies, and belongs to the Holarctic radiation of \"monochrome\" magpies","d_a_28.webp",3,3);

//Found
        this.questionCreator("What kind of animals head is printed on the label of Gordon\'s Gin?","Boar","Deer, mule","Crab, red lava","Buttermilk snake","Boar","Every label and bottle top of Gordon\'s gin bears a depiction of a wild boar. According to legend a member of Clan Gordon saved the King of Scotland from the animal while hunting.","d_a_29.webp",3,3);

//Found
        this.questionCreator("At the ancient Olympics,  what did archers use as targets?","Tethered Doves","Black kite","Azara's zorro","White spoonbill","Tethered Doves","The Games featured archery with tethered doves as the targets. Target archery is also seen in the legends of Robin Hood and William Tell, which show the respect that the English had for great archers. In Japan, the practice of Kyudo and Yabusame raised archery from mere discipline to an art form and a philosophy of life.","d_a_30.webp",3,3);

//Found
        this.questionCreator("The Bald Eagle is Americas bird, what is Britain\'s bird?","The Robin","Bare-faced go away bird","Bird, pied butcher","Bird, red-billed tropic","The Robin","America has the bald eagle, France has the Gallic rooster, and now Britain has the robin. A nationwide ballot saw more than 200,000 people elect the robin as Britain\'s national bird, after it swooped away with 34 per cent of the vote.","d_a_31.webp",3,3);

//Found
        this.questionCreator("What type of jungle animal produces its own sun tan lotion?","The Hippopotamus","Bent-toed gecko","Cat, civet","Eagle, african fish","The Hippopotamus","When a hippo sweats, its skin releases colored substances that may prevent infection and sunburn.","d_a_32.webp",3,3);

//Found
        this.questionCreator("The name of what type of animal means \"does not drink\"?","Koala","Eastern box turtle","Four-spotted skimmer","Giant armadillo","Koala","The koala (Phascolarctos cinereus) is a tree-living Australian marsupial, or pouched mammal, which early English settlers in Australia called the native bear. ... The name is derived from an Aboriginal word meaning \"animal that does not drink,\" for koalas get their water from the leaves they chew.","d_a_33.webp",3,3);

//Found
        this.questionCreator("What kind of animal is the mascot of the US Naval Academy?","Goat","Golden eagle","Harbor seal","Jaguarundi","Goat","Bill the Goat is the mascot of the United States Naval Academy. The mascot is a live goat and is also represented by a costumed midshipman. There is also a bronze statue of the goat in the north end zone of Navy–Marine Corps Memorial Stadium.","d_a_34.webp",3,3);

//Found
        this.questionCreator("An elephant is called a pachyderm but what does pachyderm mean?","Thick Skinned","Black Skinned","Thin Skinned","Soft Skinned","Thick Skinned","The expression of elephants being pachyderms is correct only in part. On vulnerable spots like the trunk embouchure, legs and back, skin can in fact be 2.5 to 3 cm thick, but behind the ears, by the eye, on the abdomen, chest and shoulders it is as thin as paper.","d_a_35.webp",3,3);

//Found
        this.questionCreator("In Sweden,  what\'s involved in 20% of car accidents ?","A moose","A Hyrax","A Griffon vulture","A Frogmouth, tawny","A moose","In 2006 police were notified of 4,940 traffic accidents involving elk, known as moose in North America, an increase of 848 from 2005, according to data published by the police.","d_a_36.webp",3,3);

//Found
        this.questionCreator("One of the most dangerous fish in the world.","Candiru","A Griffon vulture","A Frogmouth, tawny","A moose","Candiru","The candiru, (Vandellia cirrhosa), is a scaleless, parasitic catfish of the family Trichomycteridae found in the Amazon River region. It is translucent and eellike, and it grows to a length of about 2.5 cm (1 inch). The candiru feeds on blood and is commonly found in the gill cavities of other fishes.","d_a_37.webp",3,3);

//Found
        this.questionCreator("What type of animal originated Groundhog Day in Germany ?","Badgers","Galah","Hen, sage","Killer whale","Badgers","The Pennsylvania Dutch were immigrants from German-speaking areas of Europe. The Germans already had a tradition of marking Candlemas (February 2) as \"Badger Day\" (Dachstag), where if a badger emerging found it to be a sunny day thereby casting a shadow, it foreboded the prolonging of winter by four more weeks.","d_a_38.webp",3,3);

//Found
        this.questionCreator("A Kiwi has how many tail feathers?","None","5","3","1","None","It has no wings or tail. It does not have feathers like other birds. Its feathers look like hair. Each foot has four toes.","d_a_39.webp",3,3);

//Found
        this.questionCreator("In which country is flying fish the national dish?","Barbados","U.S.A","Korea","Japan","Barbados","Flying fish is part of the national dish of Barbados, cou-cou and flying fish. The taste is close to that of a sardine. In the Solomon Islands, the fish are caught while they are flying, using nets held from outrigger canoes.","d_a_40.webp",3,3);

//Found
        this.questionCreator("One of the most dangeroues fish in the world.","Great White Shark","Clown Shark","Goblin Shark","Flash Fish","Great White Shark","The white shark (Carcharodon carcharias), which is also called great white shark or white pointer, may be the fish that needs no introduction, because it is one of the most powerful and potentially dangerous predatory sharks in the world.","d_a_41.webp",3,3);

//Found
        this.questionCreator("One of the most ugliest fish in the world.","BlobFish","Cat fish","Sink Fish","Algaca Fish","BlobFish","This ugly fish, 30 cm long, made up of a gelatinous mass, is only found in the abyss between 600 and 1200 meters deep.","d_a_42.webp",3,3);

//Found
        this.questionCreator("What is A mosquitoes main food is what?","Nectar from flowers","Blood","Mole","Small mosquitoes","Nectar from flowers","Female mosquitoes use two very different food sources. They need sugar for energy, which is taken from sources such as nectar, and they need blood as a source of protein for egg development.","d_a_43.webp",3,3);

//Found
        this.questionCreator("What kind of dog is the only kind that blushes?","The pharaoh hound","German Shepherd","Akita","Bulldog","The pharaoh hound","Pharaoh Hound. The Pharaoh Hound is a Maltese breed of dog and the national dog of Malta. In Maltese it is called Kelb tal-Fenek, which means \"rabbit dog\". It is traditionally used for hunting rabbit in the Maltese Islands.","d_a_44.webp",3,3);

//Found
        this.questionCreator("The quokka is a member of what family of animals?","Wallaby","Legaan, water","Malabar squirrel","Onager","Wallaby","Quokka. The quokka (/ˈkwɒkə/, Setonix brachyurus), the only member of the genus Setonix, is a small macropod about the size of a domestic cat. Like other marsupials in the macropod family (such as kangaroos and wallabies), the quokka is herbivorous and mainly nocturnal.","d_a_45.webp",3,3);

//Found
        this.questionCreator("This animal has 18 to 20 inches whopping tongue","Giraffe","Cat","Sea horse","Bull","Giraffe","A giraffe’s tongue is a whopping 18 to 20 inches long to access hard-to-reach leaves. It’s also blue-black in color, which may keep the tongue from getting sunburned. Talk about a cool adaptation.","d_a_46.webp",3,3);

//Found
        this.questionCreator("What animal has the biggest ears?","Elephant","Malabar squirrel","Eagle","Bird manure for fertilizer","Elephant","The African elephant has the largest ears of any animal on the planet.","d_a_47.webp",3,3);

//Found
        this.questionCreator("What type of dog is named for the German word for muzzle?","Schnauzer","German Shepherd","Pitbull","Domestic Dog","Schnauzer","Literally meaning \"growler\" from the German schnauzen (\"to growl, snarl\"), Schnauzers are a terrier known for their gruff growls and rough ruffs. However, the word Schnauzer is also related to Schnauze (\"snout, muzzle\") from which we get \"schnoz\" meaning a large nose.","d_a_48.webp",3,3);

//Found
        this.questionCreator("This animal is one of the most loudest on earth.","Tiger pistol shrimp","Ovenbird","Owl","Eagle","Tiger pistol shrimp","Native to the Mediterranean, this shrimp (pictured above with a gobie fish) leads the list as the loudest living animal, topping over 200dB – louder than a gunshot.","d_a_49.webp",3,3);

//Found
        this.questionCreator("What type of fish can hold objects in its tail?","Sea Horse","Clown Fish","John Dory","Piranha","Sea Horse","\"Seahorses all have a prehensile tail. This allows the fish to hold on to objects such as seagrass.\"","d_a_50.webp",3,3);

//Found
        this.questionCreator("One of the beautiful fish in the world.","8 Symphysodon Discus","Blobfish","Wolf fish","Spotted handfish","8 Symphysodon Discus","Symphysodon, colloquially known as discus, is a genus of cichlids native to the Amazon river basin in South America. Due to their distinctive shape and bright colors, discus are popular as freshwater aquarium fish, and their aquaculture in several countries in Asia is a major industry. They are sometimes referred to as pompadour fish","d_a_51.webp",3,3);

//Found
        this.questionCreator("One of the longest sleeping animals","Tree Shrew","Tiger","Lion","Dog","Tree Shrew","The average sleep time of this animal is 14.h hrs","d_a_52.webp",3,3);

//Found
        this.questionCreator("A group of Badgers are called a what?","Sett","Kelp gull","Huron","Grison","Sett","A male European badger is a boar, a female is a sow, and a young badger is a cub. ... A collective name suggested for a group of colonial badgers is a cete, but badger colonies are more often called clans. A badger\'s home is called a sett.","d_a_53.webp",3,3);

//Found
        this.questionCreator("What creature can turn its stomach inside out?","Starfish","Bear","Koala","Spider","Starfish","Starfish Feeding Secrets Revealed. Starfish have a feeding method that is unlike any other. To eat, the echinoderm ejects its stomach from its own body — placing it over the digestible parts of its prey, typically a mussel or clam","d_a_54.webp",3,3);

//Found
        this.questionCreator("A Samoyed is what kind of animal?","Dog","Tiger","Lion","Cat","Dog","The Samoyed is a primitive dog belonging to the spitz or northern dog group, specifically the laikas: a Eurasian dog type used for a variety of purposes, namely hunting, herding, guarding, and sledding.","d_a_55.webp",3,3);

//Found
        this.questionCreator("What type of creature eats rests and sleeps on its back?","Sea Otter","Sea Snake","Lion, australian sea","Turtle","Sea Otter","Sea otters often use their stomachs as tables and are known to sleep while floating on their backs in the water, but this is not always the case.","d_a_56.webp",3,3);

//Found
        this.questionCreator("What breed of dog did Sigmund Freud use to help his psychoanalysis ?","Chow","Beagle","Labrador Retriever","Pug","Chow","Sigmund Freud, the founding father of psychoanalysis, was a pioneer in the field of psychology and renowned for his theories on human development. Little known fact: he was also a dog person. Freud had a love for chow chows in particular.","d_a_57.webp",3,3);

//Found
        this.questionCreator("What character in the Jungle Books name means frog?","Mowgli","Mouggle","Moug","Mou","Mowgli","Lost by his parents as a baby in the Indian jungle during a tiger attack, he is adopted by the Wolf Mother (Raksha) and Father Wolf, who call him Mowgli (frog) because of his lack of fur and his refusal to sit still. Shere Khan the tiger demands that they give him the baby but the wolves refuse.","d_a_58.webp",3,3);

//Found
        this.questionCreator("Which breed of dog gets its name from the French for \"earth\"?","Terrier","Siberian Husky","Dobermann","English Mastiff","Terrier","“Terrier” comes from the old French chien terrier, literally “earth dog.","d_a_59.webp",3,3);

//Found
        this.questionCreator("The snow leopard is also known as what?","Ounce","Warthog","Urial","Tern, royal","Ounce","Snow leopard. A large wild cat (Panthera uncia syn. Uncia uncia) of the highlands of Central and South Asia, having long, thick, whitish-gray fur with dark markings like those of a leopard. Also called ounce.","d_a_60.webp",3,3);

//Found
        this.questionCreator("A group of swine is called a what?","Spunder","Woylie","Zorilla","Topi","Spunder","The name for a group of pigs depends on the animals\' ages. A group of young pigs is called a drift, drove or litter. Groups of older pigs are called a sounder of swine, a team or passel of hogs or a singular of boars.","d_a_61.webp",3,3);

//Found
        this.questionCreator("What order of insects contains the most species?","Beetles","Insect, stick","Butterfly","Cricket","Beetles","The largest numbers of described species in the U.S. fall into four insect Orders: Coleoptera (beetles) at 23,700, Diptera (flies) at 19,600, Hymenoptera (ants, bees, wasps) at 17,500, and Lepidoptera (moths and butterflies) at 11,500.","d_a_62.webp",3,3);

//Found
        this.questionCreator("The average Caterpillar has 248 muscles in what?","Head","Tentacles","Spiracles","Prolegs","Head","Caterpillars have 4,000 muscles (compare humans, with 629). They move through contraction of the muscles in the rear segments pushing the blood forward into the front segments elongating the torso. The average caterpillar has 248 muscles in the head segment alone","d_a_63.webp",3,3);

//Found
        this.questionCreator("Lagomorphs refer to what kind of animal?","Rabbits","Fox, savanna","Dassie","Cougar","Rabbits","Lagomorphs are preyed upon by many other animals, including wolves, dogs, coyotes, foxes, lynxes, bobcats, weasels, eagles, owls, hawks, and people. Classification: Lagomorphs belong to the class Mammalia (mammals) and the order Lagomorpha (rabbits, pikas, and hares).","d_a_64.webp",3,3);

//Found
        this.questionCreator("Which is the most primitive living mammal?","Duck-billed platypus","Seal","Weasel","Hedgehog","Duck-billed platypus","Egg-laying Mammals There are only five living monotreme species: the duck-billed platypus and four species of echidna (also known as spiny anteaters). In some ways, monotremes are very primitive for mammals because, like reptiles and birds, they lay eggs rather than having live birth.","d_a_65.webp",3,3);

//Found
        this.questionCreator("Which living being has the heaviest brain?","Sperm whale","African elephant","Killer whale","Sea cow","Sperm whale","The average adult human brain weighs about 3 pounds - the same weight as the average brain of a dolphin (which is also a very intelligent animal). But there are animals with larger brains that are not considered to be as intelligent as a dolphin. For instance, a sperm whale has a brain that weighs about 17 pounds.","d_a_66.webp",3,3);

//Found
        this.questionCreator("Which is the mythical being believed to have become extinct?","Nautilus","Narwhal","Unicorn","Dodo","Nautilus","According to fossil records, animals similar to the chambered nautilus have existed for about 500 million years. Although no regulations currently exist to protect them, the six living species of chambered nautilus appear to be in decline.","d_a_67.webp",3,3);

//Found
        this.questionCreator("Which living being whistle?","Bat","Whale","Dolphin","Shark","Bat","Whistling Like a Bat: Development of an Ultrasonic Whistle to Deter Bats from Wind Turbines. The whistles will produce sounds mimicking the spectrotemporal patterns of bat echolocation pulses, thereby enhancing the bats' ability to detect, localize and avoid the moving blades","d_a_68.webp",3,3);

//Found
        this.questionCreator("Which of the following living being has also been found to be a tool-user?","Sea otter","Gorilla","Beaver","Spider","Sea otter","Sea otters are known for their ability to use stones as anvils or hammers to facilitate access to hard-to-reach prey items","d_a_69.webp",3,3);

//Found
        this.questionCreator("Which category creatures contain a type that can fly?","Lizards","Cats","Hedgehogs","Rats","Lizards","Draco, also known as flying lizard or flying dragon, is a reptile that belongs to the family Agamidae. There are 31 species of flying lizards that can be found in South and Southeast Asia (Philippines, Borneo, India, Malaysia, Indonesia…). Flying lizards live in tropical rainforests.","d_a_70.webp",3,3);

//Found
        this.questionCreator("Which of the following animals is on the verge of extinction?","Great Indian rhinoceros","Badger","Kangaroo","Gibbon","Great Indian rhinoceros","There are about 2,600 Indian Rhino left in the wild, but their numbers were less than 200 early in the 20th century. This demise was mainly caused by poaching and habitat loss. There recovery is one of two success stories in rhino conservation, the other being the Southern White Rhino.","d_a_71.webp",3,3);

//Found
        this.questionCreator("Which has longest gestation period among mammals?","Asiatic elephant","Giant panda","Pronghorn","Prairie dog","Asiatic elephant","The largest of all land animals have the longest gestation period of all living mammals. Yes, the gestation period of elephants lasts for 18 to 22 months. For an African elephant, the gestation period is 22 months where the Asian elephant's is between 18 and 22 months.","d_a_72.webp",3,3);

//Found
        this.questionCreator("Where are giants anteaters found?","South America","North America","South Africa","Japan","South America","Wild giant anteaters live in grasslands, deciduous forests and rain forests of South and Central America. Though most common in South America, they can be found anywhere from the southern tip of Mexico through Central and South America","d_a_73.webp",3,3);

//Found
        this.questionCreator("Where are aardvarks found?","Africa","Australia","Denmark","Ireland","Africa","Aardvarks are found in sub-Saharan Africa, where suitable habitat and food is available. They spend the daylight hours in dark underground burrows to avoid the heat of the day.","d_a_74.webp",3,3);

//Found
        this.questionCreator("Which rodent is found in remote desert areas, in shifting sand dunes and extreme temperature?","Jerboa","Capybara","Hamster","Murree vole","Jerboa","Jerboas are hopping desert rodents found throughout Arabia, Northern Africa and Asia east to northern China and Manchuria. They tend to live in hot deserts.","d_a_75.webp",3,3);

//Found
        this.questionCreator("Which animal female curls up around its baby to protect it from any attacking animal?","pangolin","Tortoise","Armadillo","hedgehog","pangolin","Pangolin also known as scaly anteaters because of their appearance, Pangolin Female curl up its baby to protect it from enemy","d_a_76.webp",3,3);

//Found
        this.questionCreator("Which of the following animals is the fastest burrower?","Aardvark","Mole","Kangaroo rat","Prairie dog","Aardvark","Aardvarks are not fast runners but they can quickly dig a defensive burrow. The aardvark's tail is thick and strong and they will use it as a club. Their sharp claws are formidable weapons, and if caught in the open, the aardvark will roll on its back to engage all four feet in the fight.","d_a_77.webp",3,3);

//Found
        this.questionCreator("Where are badgers found?","Europe","Australia","South Africa","Iraq","Europe","Badgers are found in much of North America, Ireland, Great Britain and most of the rest of Europe as far north as southern Scandinavia. They live as far east as Japan and China. The Javan ferret-badger lives in Indonesia, and the Bornean ferret-badger lives in Malaysia.","d_a_78.webp",3,3);

//Found
        this.questionCreator("Which is the largest rodent in the world?","Capybara","Kangaroo","Rabbit","Squirrel","Capybara","The capybara is a mammal native to South America. It is the largest living rodent in the world","d_a_79.webp",3,3);

//Found
        this.questionCreator("Where is rhea found?","South America","North America","China","Ireland","South America","This ratite is the largest bird in the Americas. It is a fast runner; and when it runs, its neck is almost horizontal to the ground. Rheas congregate in flocks of 20 to 30 birds. The lesser or Darwin's rhea, Rhea pennata, is mostly found in the southern part of South America","d_a_80.webp",3,3);

//Found
        this.questionCreator("Which bird locates its prey by smell?","Kiwi","Woodpecker","Crow","Stonechat","Kiwi","A kiwi's olfactory bulb is the second largest among all birds relative to the size of its forebrain, giving it an exceptional sense of smell, just second to the condor. This helps kiwi locate food beneath the soil and in leaf lite","d_a_81.webp",3,3);

//Found
        this.questionCreator("Which bird mimics the calls and even songs of other birds?","Drongo","Koel","Eagle","Woodpecker","Drongo","The Drongos are able to mimic the sounds made by many different species that inhabit its desert environment. Drongo, seen here in flight, impersonates the calls of other birds in order to steal food","d_a_82.webp",3,3);

//Found
        this.questionCreator("Which bird uses its beak as a filter to gather food from water?","Flamingo","Grey heron","Ibis","Sarus crane","Flamingo","Flamingos are filter feeders. ... Because the flamingo must use its beak in an upside-down manner, the beak has evolved to reflect this. The flamingo's top beak functions like the bottom beak of most birds, and vice versa. Flamingos are among the very few animals that are able to move their top jaw while eating","d_a_83.webp",3,3);

//Found
        this.questionCreator("Which bird keeps its mouth open while flying so that it can catch flying insects?","Nightjar","Vulture","Owl","Crow","Nightjar","Nightjars are very agile in flight, able to hunt and catch aerial insects such as moths in those huge mouths. They also eat beetles, spiders and various other insects. The shape of a bird's beak almost always determines the type of food they will eat.","d_a_84.webp",3,3);

//Found
        this.questionCreator("Some birds do not catch their own prey. They steal it from another bird. Which bird is it?","Skua","Sparrow","River tem","Bulbul","Skua","Skuas steal much of their food from terns, puffins, and other birds that are carrying fish or other prizes back to their nests and young. The swashbuckling birds sometimes team up to overwhelm their victims, and they are relentless in chasing down their adversaries.","d_a_85.webp",3,3);

//Found
        this.questionCreator("Which holds the record for longest migration?","Arctic tern","Shearwater","River tern","Bar headed geese","Arctic tern","Canada geese fly in a distinctive V-shaped flight formation, with an altitude of 1 km (3,000 feet) for migration flight. The maximum flight ceiling of Canada geese is unknown, but they have been reported at 9 km (29,000 feet)","d_a_86.webp",3,3);

//Found
        this.questionCreator("Which bird can travel very long distances without flapping its wings?","Andean condor","Peregrine falcon","Coot","Eagle","Andean condor","Condors can glide over large areas while using little energy. These huge birds are too heavy to fly without help. They use warm air currents (thermals) to help them gain altitude and soar through the sky. By gliding from thermal to thermal, a condor may need to flap its wings only once every hour","d_a_87.webp",3,3);

//Found
        this.questionCreator("Which is heaviest flying bird?","Kori bustard","Ostrich","Vulture","Condor","Kori bustard","The largest (heaviest) flying bird today is the Kori Bustard (Ardeotis kori) of Africa, males weigh about 18kg, females about half that. The largest bird ever to fly were the Teratorns (a type of Condor), the largest of which, Argentavis magnificens, had a wingspan of 3 metres, and weighed 120kg","d_a_88.webp",3,3);

//Found
        this.questionCreator("The bandicoot is native to which country?","Australia","Africa","North America","Cambodia","Australia","There are also a few rare species such as the rabbit-eared bandicoots. Bandicoots are one of the few native mammals to have remained abundant close to the major cities of Australia. In suburban Sydney it is the long-nosed species that can be seen.","d_a_89.webp",3,3);

//Found
        this.questionCreator("What is the common name for the animal of the genus vulpes.","Fox","Wolf","Owl","Dog","Fox","Vulpes is a genus of the Canidae the members of this genus are colloquially referred to as a true foxes, meaning they form a proper clade.","d_a_90.webp",3,3);

//Found
        this.questionCreator("What type of animal is cuscus?","Mammal","Bird","Reptile","Amphibian","Mammal","The cuscus is a large marsupial native to the Northern forest of Australia and the large, tropical island of Papua New Guinea. The cuscus is a subspecies of possum with the cuscus being the largest of the world's possum species. The cuscus is an arboreal mammal and spends its life almost exclusively in the trees.","d_a_91.webp",3,3);

//Found
        this.questionCreator("In which of the following kinds of organism is the phenomenon found wherein the female kills the male after copulation?","Honeybee","Dragonfly","Spider","Pit viper","Honeybee","The next male honey bee to mate with the queen will remove the previous endophallus and eventually lose his own after ejaculation. Male honey bees are only able to mate seven to 10 times during a mating flight, and after mating, a drone dies quickly, as his abdomen rips open when his endophallus is removed","d_a_92.webp",3,3);

//Found
        this.questionCreator("In which of the following animals is respiration done by skin?","Frog","Flying fish","Sea horse","Chameleon","Frog","Though they have functional lungs, much of a frog's respiration occurs through the skin. A frog's moist skin is thin and marbled with blood vessels and capillaries close to the surface. The moisture on the skin dissolves oxygen from the air and water surrounding the frog and transmits it into the blood","d_a_93.webp",3,3);

//Found
        this.questionCreator("The country in which Yak is found?","Tibet","Africa","South America","Indian","Tibet","The domestic yak (Bos grunniens) is a long-haired domesticated bovid found throughout the Himalayan region of the Indian subcontinent, the Tibetan Plateau and as far north as Mongolia and Russia. It is descended from the wild yak (Bos mutus)","d_a_94.webp",3,3);

//Found
        this.questionCreator("The Bhindawas bird sanctuary is located in which country?","Haryana","Madhya Pradesh","Bihar","Odisha","Haryana","The Bhindawas Bird Sanctuary (BBS) is located in Jhajjar district, Haryana.","d_a_95.webp",3,3);

//Found
        this.questionCreator("What is largest predator found on the land of Australia?","Dingo","Coyote","Gray wolf","Australian Cattle dog","Dingo","The Dingo is the largest terrestrial predator in Australia.","d_a_96.webp",3,3);

//Found
        this.questionCreator("What type of animal is an Avocet?","Bird","Reptile","Mammal","Amphibian","Bird","The avocet is a type of wading bird that is found across mudflats in the world's warmer climates. There are four different species of avocet which are the pied avocet, the American avocet, the Red-necked avocet and the Andean avocet","d_a_97.webp",3,3);

//Found
        this.questionCreator("Aardvark is South Africa's Afrikaans language which means what?","Ground pig","Land animal","Sea mammal","Monkey eating","Ground pig","Aardvark is the first word in your English dictionary; however the name aardvark is not even English! It comes from South Africa's Afrikaans language and means 'earth pig' or 'ground pig'.","d_a_98.webp",3,3);

//Found
        this.questionCreator("What is the largest terrier?","Airedale","Scottish","Bull","Boston","Airedale","Airedale it is traditionally called \"King of Terriers\" because it is the largest of the terrier breeds. The Airedale was bred from the old English black and tan terrier (now extinct).","d_a_99.webp",3,3);

//Found
        this.questionCreator("Where would find a tuatara?","New Zealand","Africa","Sweden","Australia","New Zealand","This New Zealand native has unique, ancient lineage that goes back to the time of the dinosaurs.","d_a_100.webp",3,3);


    }


    private void addEntertainmentEasy()
    {
        //check

//Found
        this.questionCreator("Jodie Foster sings ‘My Name is Tallulah’ in which 1976 film?","Bugsy Malone","Kymani Lucero","Amira Carey","Ashton Ruiz","Bugsy Malone","Bugsy Malone is all about unusual spoof of old gangster movies in which a cast made up entirely of children sings and dances its way around Prohibition-era sets, substituting toy guns and whipped cream for machine guns and bullets.","e_e_1.webp",8,1);

//Found
        this.questionCreator("Kirk Douglas played the title role in which 1960 film about Roman slaves?","Spartacus","Isabelle","Shepherd","Kamari","Spartacus","The rebellious Thracian Spartacus, born and raised a slave, is sold to Gladiator trainer Batiatus. After weeks of being trained to kill for the arena, Spartacus turns on his owners and leads the other slaves in rebellion. As the rebels move from town to town, their numbers swell as escaped slaves join their ranks.","e_e_2.webp",8,1);

//Found
        this.questionCreator("Who played Fred Flinstone in the 1994 film ‘The Flintstones’?","John Goodman","Lacey West","Patience Castro","Tia Roach","John Goodman","Big-hearted, dim-witted factory worker Fred Flintstone (John Goodman) lends money to his friend Barney Rubble (Rick Moranis) so that he can adopt a baby. As thanks, Barney swaps his IQ test for Fred\'s during an executive search program. After getting promoted, however, Fred becomes embroiled in the dastardly scheming of his boss Cliff Vandercave (Kyle MacLachlan), who enlists his secretary, Sharon Stone (Halle Berry), to seduce Fred, angering Fred\'s wife, Wilma (Elizabeth Perkins).","e_e_3.webp",8,1);

//Found
        this.questionCreator("What is the name of the hospital in the 1954 film ‘Doctor in the House’?","St. Swithin’s Hospital","Armstrong","Jayden","Crane","St. Swithin’s Hospital","The story follows the fortunes of Simon Sparrow (Dirk Bogarde), starting as a new medical student at the fictional St Swithin\'s Hospital in London.","e_e_4.webp",8,1);

//Found
        this.questionCreator("Which 2002 animated film is set during the ‘Pleistocene Era’?","Ice Age","Monster","Boss Baby","Nemo","Ice Age","Produced by Blue Sky Studios as its first feature film, it was released by 20th Century Fox on March 15, 2002. The film features the voices of Ray Romano, John Leguizamo, Denis Leary, and Wedge. The film is set during the days of the ice age; animals begin migrating south to escape the winters.","e_e_5.webp",8,1);

//Found
        this.questionCreator("Who directed and starred in the 1969 film ‘Easy Rider’?","Dennis Hopper","Lana Dodson","Noah Payne","London Hansen","Dennis Hopper","Easy Rider is a 1969 American independent road drama film written by Peter Fonda, Dennis Hopper, and Terry Southern, produced by Fonda, and directed by Hopper.","e_e_6.webp",8,1);

//Found
        this.questionCreator("Which 1993 film is about the affair between author C S Lewis and poet Joy Grisham?","Shadowlands","Sorry to Bother You","Gone Girl","Interstellar","Shadowlands","Shadowlands is a 1993 British biographical drama film about the relationship between Irish academic C. S. Lewis and American poet Joy Davidman, her death from cancer, and how this challenged Lewis\'s Christian faith.","e_e_7.webp",8,1);

//Found
        this.questionCreator("Which 1997 film, directed by Quentin Tarantino, is based on Elmore Leonard’s novel ‘Rum Punch’?","Jackie Brown","Anya Williams","Christina Stein","Joslyn Ramirez","Jackie Brown","When flight attendant Jackie Brown (Pam Grier) is busted smuggling money for her arms dealer boss, Ordell Robbie (Samuel L. Jackson), agent Ray Nicolette (Michael Keaton) and detective Mark Dargus (Michael Bowen) want her help to bring down Robbie. Facing jail time for her silence or death for her cooperation, Brown decides instead to double-cross both parties and make off with the smuggled money.","e_e_8.webp",8,1);

//Found
        this.questionCreator("Red Grant is the name of the villain in which James Bond film?","From Russia With Love","Coco","Alien: Covenant","Kin","From Russia With Love","Donald \"Red\" Grant is a fictional SPECTRE professional assassin and the quaternary antagonist of the EON Productions\' 1963 James Bond film From Russia with Love.","e_e_9.webp",8,1);

//Found
        this.questionCreator("The 1988 film ‘Frantic’, starring Harrison Ford, is set in which European city?","Paris","London","Berlin","Rome","Paris","Filming took place on location in Paris with exteriors filmed outside Le Grand Hotel in rue Scribe in the 9th arrondissement. The hotel\'s lobby also appeared in the film. Filming also took place at the Île aux Cygnes island in the Seine for the Lady Liberty scenes.","e_e_10.webp",8,1);

//Found
        this.questionCreator("What is the title of the 1971 film in which Clint Eastwood plays DJ Dave Garver?","Play Misty For Me","The Matrix Reloaded","Smallfoot","Thor","Play Misty For Me","Play Misty for Me is a 1971 American psychological thriller film, directed by and starring Clint Eastwood, in his directorial debut. Jessica Walter and Donna Mills co-star. The original music score was composed by Dee Barton. In the film, Eastwood plays the role of a radio disc jockey being stalked by an obsessed female fan.","e_e_11.webp",8,1);

//Found
        this.questionCreator("In the Star Wars series of films what is the name of Han Solo’s Wookie co-pilot?","Chewbacca","Fletcher","Wall","Anthony","Chewbacca","Han Solo is a fictional character in the Star Wars franchise, who is a pilot from the planet Corellia. In the original film trilogy, Han is the captain of the Millennium Falcon, along with his Wookiee co-pilot Chewbacca, whereby both pilots became involved in the Rebel Alliance\'s struggle against the Galactic Empire.","e_e_12.webp",8,1);

//Found
        this.questionCreator("Who played Captain Jack Sparrow in the ‘Pirates of the Caribbean’ series of films?","Johnny Depp","Ezequiel Jarvis","Kaylie Fritz","Lilyana Nolan","Johnny Depp","John Christopher Depp II is an American actor, producer, and musician. He has been nominated for three Academy Awards and has won the Golden Globe and Screen Actors Guild Awards for Best Actor. Depp rose to prominence on the 1980s television series 21 Jump Street, becoming a teen idol.","e_e_13.webp",8,1);

//Found
        this.questionCreator("Helena Bonham Carter and Meat Loaf appear in which 1999 film starring Brad Pitt?","Fight Club","Justice League","Air Strike","Juliet, Naked","Fight Club","Fight Club , starring Brad Pitt, Edward Norton, Helena Bonham Carter, Meat Loaf. An insomniac office worker looking for a way to change his life crosses paths with a devil-may-care soap maker and they form an underground fight club that evolves into something much,","e_e_14.webp",8,1);

//Found
        this.questionCreator("The Bernstein and Sondheim song ‘Somewhere’ was written for which musical?","West Side Story","Gone Girl","Rampage","Peppermint","West Side Story","\"Somewhere\", sometimes referred to as \"Somewhere (There\'s a Place for Us)\" or simply \"There\'s a Place for Us\", is a song from the 1957 Broadway musical West Side Story that was made into a film in 1961.","e_e_15.webp",8,1);

//Found
        this.questionCreator("Who wrote the children’s story ‘James and the Giant Peach’?","Roald Dahl","Jonas Flowers","Abram Christian","Malaki Schwartz","Roald Dahl","Roald Dahl was a British novelist, short story writer, poet, screenwriter, and fighter pilot. His books have sold more than 250 million copies worldwide. Born in Wales to Norwegian immigrant parents, Dahl served in the Royal Air Force during the Second World War.","e_e_16.webp",8,1);

//Found
        this.questionCreator("Who was the second wife of US actor Tom Cruise?","Nicole Kidman","Marin Henson","Giovanna Moreno","Halle Ho","Nicole Kidman","Nicole Mary Kidman AC is an Australian actress and producer. She is the recipient of multiple awards, including an Academy Award, two Primetime Emmy Awards, five Golden Globe Awards, the Silver Bear for Best Actress, and a Cannes Film Festival special award","e_e_17.webp",8,1);

//Found
        this.questionCreator("Which film, starring Yul Brynner and Deborah Kerr, was released in June 1956?","The King and I","Twilight","Ant-Man","Papillon","The King and I","The King and I is a 1956 American musical film made by 20th Century Fox, directed by Walter Lang and produced by Charles Brackett and Darryl F. Zanuck. The screenplay by Ernest Lehman is based on the Richard Rodgers and Oscar Hammerstein II musical The King and I, based in turn on the novel Anna and the King of Siam by Margaret Landon.","e_e_18.webp",8,1);

//Found
        this.questionCreator("Which trio made their last feature film appearance in the 1949 film ‘Love Happy’?","The Marx Brothers","Halle","Moshe","Reese","The Marx Brothers","The Marx Brothers were an American family comedy act that was successful in vaudeville, on Broadway, and in motion pictures from 1905 to 1949. Five of the Marx Brothers\' thirteen feature films were selected by the American Film Institute (AFI) as among the top 100 comedy films, with two of them (Duck Soup and A Night at the Opera) in the top twelve.","e_e_19.webp",8,1);

//Found
        this.questionCreator("Who plays Daphne in the 2002 film ‘Scooby-Doo’?","Sarah Michelle Gellar","Owen Sampson","Parker Burke","Kaitlyn Pittman","Sarah Michelle Gellar","Scooby-Doo (also known as Scooby-Doo: The Movie) is a 2002 American live-action/computer-animated family adventure film based on the long-running Hanna-Barbera animated television series of the same name. It is the first installment in the Scooby-Doo live-action film series, directed by Raja Gosnell, written by James Gunn, and starring Freddie Prinze Jr., Sarah Michelle Gellar, Matthew Lillard, Linda Cardellini and Rowan Atkinson.","e_e_20.webp",8,1);

//Found
        this.questionCreator("Which actor played the President in the 1997 film ‘Air Force One’?","Harrison Ford","Paxton Arroyo","Alijah Mcclure","Fernando Norman","Harrison Ford","The film stars Harrison Ford and Gary Oldman, as well as Glenn Close, Xander Berkeley, William H. Macy, Dean Stockwell, and Paul Guilfoyle.","e_e_21.webp",8,1);

//Found
        this.questionCreator("Which artist is the subject of the 1956 film ‘Lust For Life’?","Vincent van Gogh","Jamir Glenn","Demetrius Olson","Arielle Barrera","Vincent van Gogh","Lust for Life is a 1956 American biographical film about the life of the Dutch painter Vincent van Gogh, based on the 1934 novel of the same name by Irving Stone and adapted by Norman Corwin. It was directed by Vincente Minnelli and produced by John Houseman.","e_e_22.webp",8,1);

//Found
        this.questionCreator("On which fictional planet was Superman born?","Krypton","Dayami","Becker","Pennington","Krypton","The origin story of Superman relates that he was born Kal-El on the planet Krypton, before being rocketed to Earth as an infant by his scientist father Jor-El, moments before Krypton\'s destruction.","e_e_23.webp",8,1);

//Found
        this.questionCreator("Who played the title role in the 1971 film ‘Klute’?","Donald Sutherland","Hassan Cruz","Giuliana Howe","Nancy Camacho","Donald Sutherland","Klute is a 1971 American neo-noir crime-thriller film directed and produced by Alan J. Pakula, written by Andy and Dave Lewis, and starring Jane Fonda, Donald Sutherland, Charles Cioffi, and Roy Scheider. It tells the story of a high-priced prostitute who assists a detective in solving a missing person case.","e_e_24.webp",8,1);

//Found
        this.questionCreator("Dancer and actress Virginia McMath was better known by what name?","Ginger Rogers","Manuel Sanders","Andrew Carlson","Josephine Rodgers","Ginger Rogers","Ginger Rogers (born Virginia Katherine McMath; July 16, 1911 – April 25, 1995) was an American actress, dancer, and singer. She is best known for her starring role in Kitty Foyle (1940).","e_e_25.webp",8,1);

//Found
        this.questionCreator("Which notorious murderer is depicted in the 2001 film ‘From Hell’?","Jack the Ripper","The Purge: Anarchy","The Shining","The Maze Runner","Jack the Ripper","From Hell is a 2001 American mystery horror film directed by the Hughes brothers and loosely based on the graphic novel From Hell by Alan Moore and Eddie Campbell about the Jack the Ripper murders.","e_e_26.webp",8,1);

//Found
        this.questionCreator("Who wrote the 1936 novel ‘Jamaica Inn’?","Daphne du Maurier","Savannah Sutton","Selina Hobbs","Chana Crawford","Daphne du Maurier","Jamaica Inn is a novel by the English writer Daphne du Maurier, first published in 1936. It was later made into a film, also called Jamaica Inn, directed by Alfred Hitchcock. It is a period piece set in Cornwall in 1820.","e_e_27.webp",8,1);

//Found
        this.questionCreator("Which 2009 animated film features a floating house suspended by helium balloons?","Up","Spider-Man: Homecoming","Death Wish","Red Sparrow","Up","The film centers on an elderly widower named Carl Fredricksen (Ed Asner) and an earnest boy named Russell (Jordan Nagai). By tying thousands of balloons to his house, Carl sets out to fulfill his dream to see the wilds of South America and complete a promise made to his late wife, Ellie.","e_e_28.webp",8,1);

//Found
        this.questionCreator("What is the name of the private investigator, played by Humphrey Bogart, in the 1941 film ‘The Maltese Falcon’?","Sam Spade","Mireya Baker","Sonny Hayes","Julie Stark","Sam Spade","The Maltese Falcon (1941 film) The Maltese Falcon is a 1941 film noir written and directed by John Huston in his directorial debut, and based on Dashiell Hammett\'s 1930 novel of the same name. The film stars Humphrey Bogart as private investigator Sam Spade and Mary Astor as his femme fatale client.","e_e_29.webp",8,1);

//Found
        this.questionCreator("Which American actress was born Ruth Elizabeth Davis?","Bette Davis","Roy Shaw","Kaylen Pratt","Rose Hogan","Bette Davis","Ruth Elizabeth \"Bette\" Davis was an American actress of film, television, and theater. With a career spanning 60 years, she is regarded as one of the greatest actresses in Hollywood history.","e_e_30.webp",8,1);

//Found
        this.questionCreator("Bert and Ernie are the names of the policeman and the taxi driver in which Frank Capra film?","It’s a Wonderful Life","Terminator Genisys","Bohemian Rhapsody","Ready Player One","It’s a Wonderful Life","The cop and cab driver in It\'s a Wonderful Life are named Bert and Ernie, respectively.","e_e_31.webp",8,1);

//Found
        this.questionCreator("Milos Forman directed which 1975 film starring Jack Nicholson?","One Flew Over the Cuckoos Nest","The Godfather","Jurassic World","The Equalizer","One Flew Over the Cuckoos Nest","One Flew Over the Cuckoo\'s Nest is a 1975 American comedy-drama film directed by Miloš Forman, based on the 1962 novel One Flew Over the Cuckoo\'s Nest by Ken Kesey. The film stars Jack Nicholson, and features a supporting cast of Louise Fletcher, William Redfield, Will Sampson, and Brad Dourif.","e_e_32.webp",8,1);

//Found
        this.questionCreator("Who played the role of Mrs Lovett in the 2007 film ‘Sweeney Todd: The Demon Barber of Fleet Street’?","Helena Bonham Carter","Angelina Macias","Kadyn Meza","Eliza Ryan","Helena Bonham Carter","Helena Bonham Carter CBE is an English actress. She is known for her roles in both low-budget independent art films and large-scale blockbusters. She was nominated for the Academy Award for Best Actress for her role as Kate Croy in The Wings of the Dove.","e_e_33.webp",8,1);

//Found
        this.questionCreator("Who directed the 1997 film ‘Jackie Brown’?","Quentin Tarantino","Maya Tran","Alison Lamb","Hamza Faulkner","Quentin Tarantino","Quentin Jerome Tarantino (born March 27, 1963) is an American director, writer, and actor. His films are characterized by nonlinear storylines; satirical subject matter; an aestheticization of violence.","e_e_34.webp",8,1);

//Found
        this.questionCreator("In the 1994 film ‘Speed’ starring Keanu Reeves, what speed must the bus not fall below?","Miles per hour","Kilometer per hour","Metre per second","All of the above","Miles per hour","Speed is a 1994 American action thriller film directed by Jan de Bont in his feature film directorial debut. The film stars Keanu Reeves, Dennis Hopper, Sandra Bullock, Joe Morton, Alan Ruck, and Jeff Daniels. The film tells the story of an LAPD cop who tries to rescue civilians on a city bus rigged with a bomb programmed to explode if the bus slows down below 50 mph.","e_e_35.webp",8,1);

//Found
        this.questionCreator("Who played detective Virgil Tibbs in the 1967 film ‘In the Heat of the Night’?","Sidney Poitier","Alison Lamb","Hamza Faulkner","Arjun Riddle","Sidney Poitier","In the Heat of the Night is a 1967 American mystery drama film directed by Norman Jewison. It is based on John Ball\'s 1965 novel of the same name and tells the story of Virgil Tibbs, a black police detective from Philadelphia, who becomes involved in a murder investigation in a small town in Mississippi. It stars Sidney Poitier and Rod Steiger, and was produced by Walter Mirisch. The screenplay was by Stirling Silliphant.","e_e_36.webp",8,1);

//Found
        this.questionCreator("What is the title of the only film for which actor John Wayne won an Oscar?","True Grit","A.X.L.","Hereditary","The Commuter","True Grit","John Wayne wins Best Actor Oscar. On this day in 1970, the legendary actor John Wayne wins his first–and only–acting Academy Award, for his star turn in the director Henry Hathaway\'s Western True Grit. Wayne appeared in some 150 movies over the course of his long and storied career.","e_e_37.webp",8,1);

//Found
        this.questionCreator("What is the sub-title of the 1984 film ‘Star Trek 3′?","The Search For Spock","Panther","Unfriended","Dark","The Search For Spock","Star Trek III: The Search for Spock is a 1984 American science fiction film directed by Leonard Nimoy and based on the television series of the same name created by Gene Roddenberry.","e_e_38.webp",8,1);

//Found
        this.questionCreator("Actor Eddie Bunker played which ‘colour’ in the 1991 film ‘Reservoir Dogs’?","Mr Blue","Mr Red","Mr Green","Mr White","Mr Blue","The Reservoir Dogs a 1992 American heist film written and directed by Quentin Tarantino in his feature-length debut, Eddie Bunker played as Mr. Blue on this movie.","e_e_39.webp",8,1);

//Found
        this.questionCreator("Who directed the 2009 film ‘Avatar’?","James Cameron","Kelvin Blake","Hannah Jordan","Dixie Duncan","James Cameron","Avatar, marketed as James Cameron\'s Avatar, is a 2009 American epic science fiction film directed, written, produced, and co-edited by James Cameron, and stars Sam Worthington, Zoe Saldana, Stephen Lang, Michelle Rodriguez, and Sigourney Weaver. The film is set in the mid-22nd century, when humans are colonizing Pandora, a lush habitable moon of a gas giant in the Alpha Centauri star system, in order to mine the mineral unobtanium, a room-temperature superconductor.","e_e_40.webp",8,1);

//Found
        this.questionCreator("In the Star Wars series of films what is the name of the fictional weapon, consisting of a polished hilt which projects a blade of plasma, used by the Jedi and the Sith?","Lightsaber","Light Sword","Giant Sword","Thin Sword","Lightsaber","A lightsaber is a fictional energy sword featured in the Star Wars universe. A typical lightsaber is depicted as a luminescent blade of magnetically contained plasma about 3 feet (91 cm) in length emitted from a metal hilt around 10.5 inches (27 cm) in length.","e_e_41.webp",8,1);

//Found
        this.questionCreator("Which Florence won Olympic gold on the athletics track?","Griffith Joyner","Leila Velasquez","Hugo Schultz","Evie Meyer","Griffith Joyner","Sprinter Florence Griffith Joyner, who won three Olympic gold medals and set two world records last year, won the 59th AAU Sullivan Award Monday night as the nation\'s outstanding amateur athlete in 1988.","e_e_42.webp",8,1);

//Found
        this.questionCreator("Which former Soviet leader was born on exactly the same day as author Tom \"Wolfe?\"","Mikhail Gorbachev","Gaven Dawson","Lillie Hunter","Martin Mora","Mikhail Gorbachev","Mikhail Sergeyevich Gorbachev (born 2 March 1931)is a Russian and formerly Soviet politician. He was the eighth and last leader of the Soviet Union, having been General Secretary of the Communist Party of the Soviet Union from 1985 until 1991.","e_e_43.webp",8,1);

//Found
        this.questionCreator("Who assassinated Bobby Kennedy?","Sirhan Sirhan","Rigoberto Cisneros","Reagan Bowers","Ryan Peters","Sirhan Sirhan","Robert F. Kennedy assassination. Around 12:15 a.m. PDT on June 5, 1968, Sirhan fired a .22 caliber Iver-Johnson Cadet revolver at Senator Robert Kennedy and the crowd surrounding him in the Ambassador Hotel in Los Angeles shortly after Kennedy had finished addressing supporters in the hotel\'s main ballroom.","e_e_44.webp",8,1);

//Found
        this.questionCreator("Who wrote the lyrics for the 90s Candle In the Wind?","Bernie Taupin","Jordyn Briggs","Scarlett Banks","Lilianna Shah","Bernie Taupin","\"Candle in the Wind\" is a threnody with music and lyrics by Elton John and Bernie Taupin. It was originally written in 1973, in honour of Marilyn Monroe, who had died 11 years earlier.","e_e_45.webp",8,1);

//Found
        this.questionCreator("What was the first name of Miss Lewinsky whose White House?","Monica","Cory","Lucas","Ayana","Monica","Monica Samille Lewinsky (born July 23, 1973) is an American activist, television personality, fashion designer, and former White House intern.","e_e_46.webp",8,1);

//Found
        this.questionCreator("Pablo Casals played which stringed musical instrument?","Cello","Banjo","Clarinet","Cymbal","Cello","At the age of four Casals could play the violin, piano and flute; at the age of six he played the violin well enough to perform a solo in public. His first encounter with a cello-like instrument was from witnessing a local travelling Catalan musician, who played a cello-strung broom handle.","e_e_47.webp",8,1);

//Found
        this.questionCreator("Who succeeded Franklin D Roosevelt as President?","Harry S Truman","Demarcus Thornton","Hallie Macdonald","Bridget Odonnell","Harry S Truman","Harry S. Truman. Harry S. Truman (May 8, 1884 – December 26, 1972) was the 33rd President of the United States (1945–1953), taking office upon the death of Franklin D. Roosevelt. A World War I veteran, he assumed the presidency during the waning months of World War II and the beginning of the Cold War.","e_e_48.webp",8,1);

//Found
        this.questionCreator("It\'s Impossible! Which relaxed singer was actually born in 1912 in Pennsylvania?","Perry Como","Rosa Cordova","Anderson Freeman","Marc Dickson","Perry Como","Born in 1912 in Canonsburg, PA, Como was working as a singing barber in his hometown when he began touring with local bandleader Freddie Carlone at the age of 21.","e_e_49.webp",8,1);

//Found
        this.questionCreator("In 1963 who said \"Ich bin ein Berliner\" ?","John F. Kennedy","Bryan Farley","Craig Harrington","Cael Schmidt","John F. Kennedy","\"Ich bin ein Berliner\" (\"I am a Berliner\") is a quotation of U.S. President John F. Kennedy, in a speech given on June 26, 1963, in West Berlin.","e_e_50.webp",8,1);

//Found
        this.questionCreator("In which country did Gadaffi seize power in the 60s?","Libya","Canada","America","Africa","Libya","Born near Sirte, Italian Libya to a poor Bedouin family, Gaddafi became an Arab nationalist while at school in Sabha, later enrolling in the Royal Military Academy, Benghazi. Within the military, he founded a revolutionary cell which deposed the Western-backed Senussi monarchy of Idris in a 1969 coup.","e_e_51.webp",8,1);

//Found
        this.questionCreator("Which gangster offered a reward of $10,000 when the Lindbergh baby was kidnapped?","Al Capone","Devan Whitehead","Tyrone Huber","Isaias Kemp","Al Capone","On March 2, 1932, from his cell in Cook County Jail in Chicago, one day after the son of famous aviator Charles Lindbergh had been kidnapped, “Scarface” Al Capone offered an award of $10,000 for information that would lead to the capture of the kidnappers and the baby\'s safe return","e_e_52.webp",8,1);

//Found
        this.questionCreator("What kind of boys had an 80s No 1 with West End Girls?","Pet Shop Boys","Candy Shop boys","Guitar Shop boys","Shoe Shop boys","Pet Shop Boys","\"West End Girls\" is a song by the British pop duo Pet Shop Boys. Written by Neil Tennant and Chris Lowe, the song was released twice as a single. The song is influenced by hip hop music, with lyrics concerned with class and the pressures of inner-city life which were inspired partly by T. S. Eliot\'s poem The Waste Land.","e_e_53.webp",8,1);

//Found
        this.questionCreator("F W De Klerk was the last white president of which country?","South Africa","Berlin","Paris","Rome","South Africa","F. W. de Klerk. Frederik Willem de Klerk (born 18 March 1936) is a South African politician who served as State President of South Africa from 1989 to 1994 and as Deputy President from 1994 to 1996.","e_e_54.webp",8,1);

//Found
        this.questionCreator("Which 90s leader said, \"I did not have sexual relations with that woman\"?","Bill Clinton","Alan Hammond","Dorian Atkinson","Rishi Braun","Bill Clinton","The Clinton–Lewinsky scandal was an American political sex scandal that involved 49-year-old President Bill Clinton and 22-year-old White House intern Monica Lewinsky.","e_e_55.webp",8,1);

//Found
        this.questionCreator("Which presidential hopeful withdrew from his election campaign after reports of events on the yacht Monkey Business?","Gary Hart","Frankie Casey","Abdiel Lowery","Bethany Carney","Gary Hart","Monkey Business is an American yacht built for the use of the Turnberry Isle Resort Marina in southern Florida. It is best known for its role in scuttling the campaign of Gary Hart for President of the United States.","e_e_56.webp",8,1);

//Found
        this.questionCreator("Who were singing sisters Patti, Maxine and Lavern?","Andrews Sisters","Julian Daniels","Omari Ortiz","Jasmine Wolf","Andrews Sisters","The Andrews Sisters were an American close harmony singing group of the swing and boogie-woogie eras. The group consisted of three sisters: contralto LaVerne Sophia, soprano Maxene Anglyn, and mezzo-soprano Patricia Marie \"Patty\". Throughout their career, the sisters sold over 75 million records.","e_e_57.webp",8,1);

//Found
        this.questionCreator("Which eastern European country did Pope John Paul II come from?","Poland","Paris","Rome","Berlin","Poland","John Paul II is recognised as helping to end Communist rule in his native Poland and eventually all of Europe.","e_e_58.webp",8,1);

//Found
        this.questionCreator("In which year was Nelson Mandela sent to prison in South Africa?","1962","1965","1963","1968","1962","He was arrested and imprisoned in 1962, and subsequently sentenced to life imprisonment for conspiring to overthrow the state following the Rivonia Trial. Mandela served 27 years in prison, split between Robben Island, Pollsmoor Prison, and Victor Verster Prison.","e_e_59.webp",8,1);

//Found
        this.questionCreator("What was the occupation of Nick Leeson when he brought down Barings?","Bank trader","Waiting","Bagger","Pilot","Bank trader","Nicholas William \"Nick\" Leeson (born 25 February 1967) is an English former derivatives broker famous for his time at Barings Bank, the United Kingdom\'s oldest merchant bank. A rogue trader who made fraudulent, unauthorised and speculative moves, his actions led directly to the 1995 collapse of Barings Bank, for which he was sentenced to prison.","e_e_60.webp",8,1);

//Found
        this.questionCreator("Who led the Blonde Ambition world tour of 1990?","Madonna","Cindy","Addyson","Michaela","Madonna","Blond Ambition World Tour 90 in the video sequence) is a video album by American singer-songwriter Madonna. It was released only on LaserDisc by Pioneer Artists as part of the sponsorship deal of the 1990 \"Blond Ambition World Tour\".","e_e_61.webp",8,1);

//Found
        this.questionCreator("J Paul Getty founded a museum in which state?","California","Africa","London","Paris","California","History. In 1974, J. Paul Getty opened a museum in a re-creation of the Villa of the Papyri at Herculaneum on his property in Malibu, California. In 1982, the museum became the richest in the world when it inherited US$1.2 billion.","e_e_62.webp",8,1);

//Found
        this.questionCreator("David Ben-Gurion was the first Prime Minister of which new state?","Israel","Alabama","Delaware","Colorado","Israel","David Ben-Gurion (formerly David Green) was Israel\'s first Prime Minister (from 1948-1954 and 1955-1963) and is considered the architect behind the modern State of Israel.","e_e_63.webp",8,1);

//Found
        this.questionCreator("Calamity Jane died in which decade of the 20th century?","1st (1903)","2nd (1905)","3rd (1901)","4th (1902)","1st (1903)","Martha Jane Canary or Cannary (May 1, 1852 – August 1, 1903), better known as Calamity Jane, was an American frontierswoman and professional scout known for being an acquaintance of Wild Bill Hickok and fighting against Indians. Late in her life, she appeared in Buffalo Bill\'s Wild West Show and at the 1901 Pan-American Exposition.","e_e_64.webp",8,1);

//Found
        this.questionCreator("Which superstar duetted with Celine Dion on Tell Him?","Barbara Streisand","Damion Lawrence","Liam Stephenson","Theresa Atkins","Barbara Streisand","\"Tell Him\" is a song by American singer Barbra Streisand and Canadian singer Celine Dion, recorded as a duet for their 1997 albums, Higher Ground and Let\'s Talk About Love. It was written by Linda Thompson and its producers Walter Afanasieff and David Foster, and released as the lead single from both albums on November 3, 1997.","e_e_65.webp",8,1);

//Found
        this.questionCreator("How was Charles Hardin Holley better known?","Buddy Holly","Donna Benjamin","Jerry Calderon","Jagger Holland","Buddy Holly","Charles Hardin Holley (September 7, 1936 – February 3, 1959), known as Buddy Holly, was an American musician, singer-songwriter and record producer who was a central and pioneering figure of mid-1950s rock and roll.","e_e_66.webp",8,1);

//Found
        this.questionCreator("In which country did Lech Walesa lead the conflict against the communist government?","Poland","Armenia","Belize","China","Poland","Soviet power swept across Eastern Europe in the mid-1940s, imposing Communist governments. The leadership of Lech Walesa helped bring down the Communist government in Poland, which influenced reforms against Communism throughout Eastern Europe.","e_e_67.webp",8,1);

//Found
        this.questionCreator("Who seized power in Cuba in the late 50s?","Castro","Brock","Ayers","Pruitt","Castro","The Cuban Revolution was an armed revolt conducted by Fidel Castro\'s revolutionary 26th of July Movement and its allies against the authoritarian government of Cuban President Fulgencio Batista.","e_e_68.webp",8,1);

//Found
        this.questionCreator("Which island nation is pop star Rihanna from?","Barbados","Bahamas","Jamaica","Dominican Republic","Barbados","Rihanna is from the beautiful island of Barbados. Also, her first name is actually Robyn — Rihanna is her middle name!","e_e_69.webp",8,1);

//Found
        this.questionCreator("In what year did Angelina Jolie and Brad Pitt get together?","Two thousand Five","Two thousand Six","Two thousand Seven","Two thousand Four","Two thousand Five","Brad Pitt is said to be furious that his estranged wife Angelina Jolie has started going public with their divorce because it's \"terrible\" for their children.","e_e_70.webp",8,1);

//Found
        this.questionCreator("Which One Direction member is Ed Sheeran's \"Don't\" reportedly about?","Niall Horan","Harry Styles","Zayn Malik","Louis Tomlinson","Niall Horan","Niall James Horan was born on 13 September 1993 in Mullingar, Ireland. His parents, Bobby Horan and Maura Gallagher, divorced when he was five years old, so he and his brother Greg Horan lived with their mother for a year.","e_e_71.webp",8,1);

//Found
        this.questionCreator("Who was the first person to have a No. 1 album and a No. 1 film in the same week?","Jennifer Lopez","Beyonce","Jennifer Hudson","Will Smith","Jennifer Lopez","Jennifer Lynn Lopez is an American singer, actress, dancer and producer. In 1991, Lopez began appearing as a Fly Girl dancer on In Living Color, where she remained a regular until she decided to pursue an acting career in 1993.","e_e_72.webp",8,1);

//Found
        this.questionCreator("Which NBA star did actor Gabrielle Union marry earlier this year?","Dwyane Wade","Kobe Bryant","LeBron James","Blake Griffin","Dwyane Wade","Well, considering the fact that Gabrielle is pretty smokin', it only makes sense that her husband is Dwyane Wade (he plays for the Miami Heat). The two lovebirds got married on August 30, 2014.","e_e_73.webp",8,1);

//Found
        this.questionCreator("You know that Katy Perry is a California girl. But do you know which city she's from?","Santa Barbara","San Diego","Santa Cruz","San Francisco","Santa Barbara","Katheryn Elizabeth Hudson, known professionally as Katy Perry, is an American singer, songwriter, and television personality. After singing in church during her childhood, she pursued a career in gospel music as a teenager.","e_e_74.webp",8,1);

//Found
        this.questionCreator("Who's the youngest Kardashian?","Rob","Kendall","Kim","Khloe","Rob","Robert Arthur Kardashian is an American television personality and businessman. He is known for appearing on Keeping Up with the Kardashians, a reality television series that centers upon his family, as well as its spin-offs.","e_e_75.webp",8,1);

//Found
        this.questionCreator("Who was the king of rock and roll?","Elvis Presley","Bon Jovi","Nirvana","Metallica","Elvis Presley","Elvis Aaron Presley was an American singer and actor. Regarded as one of the most significant cultural icons of the 20th century, he is often referred to as the \"King of Rock and Roll\" or simply \"the King\".","e_e_76.webp",8,1);

//Found
        this.questionCreator("In 1991, which famous rock n roll band resurrected Bob Dylan's Knockin on Heaven's Door?","Guns and Roses","Red hot chili pepper","Nirvana","Green Day","Guns and Roses","Guns N' Roses, often abbreviated as GNR, is an American hard rock band from Los Angeles, California, formed in 1985. When they signed to Geffen Records in 1986, the band comprised vocalist Axl Rose, lead guitarist Slash, rhythm guitarist Izzy Stradlin, bassist Duff McKagan, and drummer Steven Adler.","e_e_77.webp",8,1);

//Found
        this.questionCreator("Miss Universe 2015","Pia Wurtzbach","Paulina Vega","Iris Mittenaere","Olivia Culpo","Pia Wurtzbach","Pia Alonzo Wurtzbach, formerly known in the Philippine entertainment and modeling industry as Pia Romero, is a German-Filipina beauty pageant titleholder, model and actress.","e_e_78.webp",8,1);

//Found
        this.questionCreator("In the brilliant 2008 movie The Dark Knight who plays the bad guy The Joker?","Heath Ledger","Morgan Freeman","Michael Caine","Maggie Gyllenhaal","Heath Ledger","Heath Andrew Ledger an Australian actor and director. After performing roles in several Australian television and film productions during the 1990s, Ledger left for the United States in 1998 to further develop his film career.","e_e_79.webp",8,1);

//Found
        this.questionCreator("In the movie Inception from 2010 who played the main actor?","Leonardo DiCaprio","Vin Diesel","Robert De Niro","Laurence Fishburne","Leonardo DiCaprio","Leonardo Wilhelm DiCaprio is an American actor and film producer. DiCaprio began his career by appearing in television commercials in the late 1980s.","e_e_80.webp",8,1);

//Found
        this.questionCreator("In the 1999 movie The Matrix the lead male role was played by?","Keanu Reeves","Elijah Wood","Orlando Bloom","Andy Wachowski","Keanu Reeves","Keanu Charles Reeves is a Canadian actor, director, producer, and musician.","e_e_81.webp",8,1);

//Found
        this.questionCreator("Who is the lead singer of The Vamps?","Bradley Simpson","James McVey","Connor Ball","Tistan Evans","Bradley Simpson","Bradley grew up in Birmingham and was born in The Royal Town of Sutton Coldfield, United Kingdom. His favorite band is Artic Monkeys.He is in a band with James McVey, Connor Ball, and Tristan Evans. He is the lead singer for The Vamps.","e_e_82.webp",8,1);

//Found
        this.questionCreator("Which American female singer features in Major Lazer's 2017 song 'Know No Better'?","Camila Cabello","Katy Perry","Britney Spears","Shakira","Camila Cabello","Karla Camila Cabello Estrabao is a Cuban-American singer and songwriter. She rose to prominence as a member of the girl group Fifth Harmony, which was formed on the second season of the American edition of The X Factor in 2012.","e_e_83.webp",8,1);

//Found
        this.questionCreator("What year did Zayn Malik release an album called ' Mind of Mine'?","Twenty Sixteen","Twenty Fifteen","Twenty Seventeen","Twenty Eighteen","Twenty Sixteen","Zain Javadd \"Zayn\" Malik, recording mononymously as Zayn, is an English singer and songwriter. Born and raised in Bradford, West Yorkshire, Malik auditioned as a solo contestant for the British music competition The X Factor in 2010.","e_e_84.webp",8,1);

//Found
        this.questionCreator("How many members were there originally in the boy band One Direction?","Five","Four","Six","Seven","Five","One Direction are an English-Irish pop boy band based in London, composed of Niall Horan, Liam Payne, Harry Styles, Louis Tomlinson, and, until his departure from the band in 2015, Zayn Malik.","e_e_85.webp",8,1);

//Found
        this.questionCreator("What is Luke's surname from 5 Seconds of Summer:","Hemmings","Bradley","Irwin","Hood","Hemmings","Luke Robert Hemmings (born July 16, 1996) is the rhythm guitarist and lead vocalist of 5 Seconds of Summer, along with band members Calum Hood, Michael Clifford, and Ashton Irwin.","e_e_86.webp",8,1);

//Found
        this.questionCreator("Which American hip hop artist features in DJ Khaled's 2017 hit 'I'm the One' with Quavo, Chance the rapper and Justin Bieber?","Lil Wayne","Jay-Z","Kanye West","PSY","Lil Wayne","Lil Wayne. Lil Wayne, byname of Dwayne Michael Carter, Jr., also called Weezy, is an American rapper who became one of the top-selling artists in hip-hop in the early 21st century.","e_e_87.webp",8,1);

//Found
        this.questionCreator("Which British male singer released the following songs 'Shape of You', 'Perfect' and 'Castle on the Hill'?","Ed Sheeran","Charly Poth","Jason Derulo","Bruno Mars","Ed Sheeran","Edward Christopher Sheeran, MBE is an English singer, songwriter, guitarist, record producer, and actor. Sheeran was born in Halifax, West Yorkshire, and raised in Framlingham, Suffolk.","e_e_88.webp",8,1);

//Found
        this.questionCreator("Who released a song in 2016 called 'Side to Side'?","Ariana Grande","Camila Cabello","Selena Gomez","Becky G.","Ariana Grande","Ariana Grande-Butera is an American singer, songwriter and actress. She began her career in 2008 in the Broadway musical 13, before playing the role of Cat Valentine in the Nickelodeon television series Victorious and in the spinoff Sam & Cat.","e_e_89.webp",8,1);

//Found
        this.questionCreator("She won two Tonys for her performance as Kim in \"Miss Saigon\". You can also listen to her beautiful voice as Jasmine in Disney's \"Aladdin\" and singing Mulan's song, \"Reflections\".","Lea Salonga","Britney Spears","Regina Belle","Christina Aguilera","Lea Salonga","Maria Lea Carmen Imutan Salonga, KLD is a Filipina singer and actress best known for her roles in musical theatre, for supplying the singing voices of two Disney Princesses, and as a recording artist and television performer","e_e_90.webp",8,1);

//Found
        this.questionCreator("Top 1 World highest paid entertainer in year 2018.","Floyd Mayweather","Kylie Jenner","George Clooney","Dwayne Johnson","Floyd Mayweather","Floyd Joy Mayweather Jr. is an American professional boxing promoter and former professional boxer. He competed from 1996 to 2007 and 2009 to 2015, and made a one-fight comeback in 2017.","e_e_91.webp",8,1);

//Found
        this.questionCreator("Who is known as the greatest boxer of all time?","Manny Pacquiao","Floyd Mayweather","Nonito Donaire","Gerry Peñalosa","Manny Pacquiao","Emmanuel Dapidran Pacquiao, PLH is a Filipino professional boxer and politician, currently serving as a Senator of the Philippines. He currently ranks #4 in BoxRec's ranking of the greatest pound for pound boxers of all time.","e_e_92.webp",8,1);

//Found
        this.questionCreator("This hallmark of Philippine Cinema died after a long-time battle with Chronic Obstructive Pulmonary Disease.","Dolphy Quizon","Francis Magalona","Rudy Fernandez","Rico Yan","Dolphy Quizon","Rodolfo Vera Quizon Sr. known by his screen names Dolphy, Pidol, and Golay, was a Filipino comedian-actor in the Philippines and He is widely regarded as the country's King of Comedy.","e_e_93.webp",8,1);

//Found
        this.questionCreator("What was the top-grossing film of 2016, according to Box Office Mojo?","Captain America Civil War","Deadpool","Zootopia","Finding Dory","Captain America Civil War","Iron Man and Captain America's main conflict in \"Civil War\" is sparked by Bucky Barnes (Sebastian Stan), also known as the Winter Soldier.","e_e_94.webp",8,1);

//Found
        this.questionCreator("Which X-Men film was released in 2016?","X-Men Apocalypse","X-Men: Days of Future Past","X-Men: First Class","X-Men: The Last Stand","X-Men Apocalypse","Apocalypse (En Sabah Nur) is a fictional supervillain appearing in comic books published by Marvel Comics. He is one of the world's first mutants, and was originally a principal villain for the original X-Factor team and now for the X-Men and related spinoff teams.","e_e_95.webp",8,1);

//Found
        this.questionCreator("Which 2016 film began life as a video game?","The Angry Birds Movie","Trolls","Pete’s Dragon","Storks","The Angry Birds Movie","Angry Birds will officially launch—via slingshot—onto the big screen for “The Angry Birds Movie,” starring the voices of comedians Jason Sudeikis, Josh Gad, Bill Hader, Maya Rudolph and Danny McBride.","e_e_96.webp",8,1);

//Found
        this.questionCreator("Kate Winslet starred as Rose DeWitt Bukater in this story of an ill fated voyage. Name the film.","Titanic","The Reader","The Mountain between us","Divergent","Titanic","Kate Elizabeth Winslet is an English actress. She made her movie debut as Juliet Hulme in Peter Jackson's Heavenly Creatures (1994). She is best known for her starring role in the American movie, Titanic.","e_e_97.webp",8,1);

//Found
        this.questionCreator("Asia’s Primetime Queen.","Marian Rivera","Barbie Forteza","Angel Locsin","Yassi Pressman","Marian Rivera","Marian Rivera Gracia-Dantes or Marian Rivera y Gracia y Dantes, known professionally as Marian Rivera, is a Spanish Filipino commercial model and actress","e_e_98.webp",8,1);

//Found
        this.questionCreator("Asia’s Song Bird.","Regine Velasquez","Lea Salonga","Sarah Geronimo","Yeng Constantino","Regine Velasquez","Regina Encarnacion Ansong Velasquez is a Filipino singer, actress and record producer. She gained recognition by winning both the 1984 Ang Bagong Kampeon and the 1989 Asia-Pacific Song Contest, representing the Philippines in the latter.","e_e_99.webp",8,1);

//Found
        this.questionCreator("The longest running noontime show in the Philippines.","Eat Bulaga","Asap","Wowowie","It’s Showtime","Eat Bulaga","Eat Bulaga! is a Philippine television variety show broadcast by GMA Network. Produced by Television And Production Exponents Inc., it is the longest running noontime show in the Philippines. The show has been hosted since its inception by Tito Sotto, Vic Sotto and Joey De Leon.","e_e_100.webp",8,1);

//Found
        this.questionCreator("The longest-running Filipino news program in the Philippines.","TV Patrol","Saksi","Bandila","Aksyon","TV Patrol","TV Patrol is the longest-running Filipino news program has been on air since March 2, 1987. Its original anchors were Noli de Castro, Robert Arevalo, Mel Tiangco, and Angelique Lazo. Currently, the show is now anchored by Noli de Castro ,Ted Failon, and Bernadette Sembrano .Noli de Castro ends the program with his famous catchphrase, “Magandang Gabi, Bayan”.","e_e_101.webp",8,1);

//Found
        this.questionCreator("Who is the eldest son of Princess Diana and Prince Charles of Wales?","Prince William","Prince Charles","Prince Harry","Prince George","Prince William","Prince William, Duke of Cambridge, KG, KT, PC, ADC is a member of the British royal family. He is the elder son of Charles, Prince of Wales, and Diana, Princess of Wales.","e_e_102.webp",8,1);

//Found
        this.questionCreator("The Filipino actress dubbed as Queen of all Media.","Kris Aquino","Sharon Cuneta","Mariel Rodriguez","Angel Locsin","Kris Aquino","Kristina Bernadette Cojuangco Aquino is a Filipino talk show host, actress and producer. She has hosted talk shows and game shows, and has also starred in films and television series.","e_e_103.webp",8,1);

//Found
        this.questionCreator("First darna of the Philippines.","Rosa del Rosario","Marneth Ann","Rodelyn Sanoria","Michelle Biol","Rosa del Rosario","Two Darna films were made by Royal Films, both starring Rosa del Rosario in \"Darna\" (1951) and \"Darna at ang Babaing Lawin\" (1952). A year after Darna was first serialized in 1950 in Bulaklak Komiks, Filipinos witnessed the first marvelous flight of Darna in the big screen.","e_e_104.webp",8,1);

//Found
        this.questionCreator("A television adaptation of Fernando Poe, Jr.'s action drama film, topbilled by Coco Martin.","Ang Probinsyano","Akala mo lang wala","Sino ka?","Bakit ako mahihiya","Ang Probinsyano","Ang Probinsyano has three story arcs. Book 1, which contained the first and second seasons, ran from its debut on September 28, 2015 until May 24, 2017. Book 2 opened with the third season and fourth season which officially began on May 25, 2017. Book 3 opened with the series' fifth season and focuses on a larger political drama, now involving the President and Vice President of the Philippines.","e_e_105.webp",8,1);


    }

    private void addEntertainmentModerate()
    {
        //check

//Found
        this.questionCreator("Which comedian played Kevin Turvey in the British tv sketch series ‘A Kick Up the Eighties?","Rik Mayall","Felipe Mathews","Aleah Haas","Barbara Preston","Rik Mayall","Richard Michael Mayall (7 March 1958 – 9 June 2014) was an English comedian, actor and writer. Mayall formed a close partnership with Ade Edmondson while they were students at Manchester University, and became a pioneer of alternative comedy in the 1980s.","m_e_1.webp",8,2);

//Found
        this.questionCreator("Who wrote the song ‘Mr Tambourine Man’, which was a hit for The Byrds in 1965?","Bob Dylan","Everett Vincent","Kian Kelley","Arielle Osborn","Bob Dylan","Bob Dylan (born Robert Allen Zimmerman, May 24, 1941) is an American singer-songwriter, author, and artist who has been an influential figure in popular music and culture for more than five decades. Much of his most celebrated work dates from the 1960s, when he became a reluctant \"voice of a generation\" with songs such as \"Blowin\' in the Wind\" and \"The Times They Are a-Changin\'\" which became anthems for the Civil Rights Movement and anti-war movement.","m_e_2.webp",8,2);

//Found
        this.questionCreator("Who played football coach Dave Dodds in the 2001 film ‘Mike Bassett:England Manager’?","Bradley Walsh","Ronnie Mcmahon","Cailyn Dennis","June Hawkins","Bradley Walsh","Bradley John Walsh (born 4 June 1960) is an English actor, comedian, singer, television presenter and former professional footballer, known for his roles as Danny Baldwin in Coronation Street and the lead role of DS Ronnie Brooks in Law & Order: UK, as well as hosting ITV game shows The Chase and Cash Trapped.","m_e_3.webp",8,2);

//Found
        this.questionCreator("Which British singer had a lily named after her in 2010, which is officially registered as ‘Popstar’?","Lily Allen","Maurice Orozco","Tatiana Bowen","Dean Hancock","Lily Allen","Lily Rose Beatrice Cooper (née Allen; born 2 May 1985), known professionally as Lily Allen, is an English singer, songwriter, and television presenter. She is the daughter of actor Keith Allen and film producer Alison Owen.","m_e_4.webp",8,2);

//Found
        this.questionCreator("George Cowling presented the first what on British television in 1954?","Weather forecast","Actor","Actress","Comedian","Weather forecast","eorge Cowling (2 March 1920 – 24 December 2009) was the BBC\'s first television weatherman. Cowling joined the Met Office in 1939 and worked as a forecaster for the RAF before joining the BBC in 1954. On 11 January 1954, he gave the first televised weather broadcast. He continued to present televised weather broadcasts for the BBC until 1957 when he rejoined the RAF. He later worked at the Met Office College and at Heathrow Airport before retiring from the Met Office in 1981.","m_e_5.webp",8,2);

//Found
        this.questionCreator("Which Mediterranean island do Kevin and Perry holiday on in the 2000 film ‘Kevin and Perry Go Large’?","Ibiza","Palawan Island","Boracay","Russian Secrets","Ibiza","Ibiza (Catalan: Eivissa) is an island in the Mediterranean Sea off the eastern coast of Spain. It is 150 kilometres (93 miles) from the city of Valencia.","m_e_6.webp",8,2);

//Found
        this.questionCreator("Anthony Hopkins and Emma Thompson play the head butler and housekeeper in which 1993 film?","The Remains of the Day","Man in the sky","Tailor the sailor","Captain Hawk","The Remains of the Day","The Remains of the Day is a 1989 novel by the Nobel Prize-winning British author Kazuo Ishiguro. The story is told from a first-person point of view, as were Ishiguro\'s two previous novels.","m_e_7.webp",8,2);

//Found
        this.questionCreator("What is the name of the fictional school in the UK television series ‘Please Sir’?","Fenn Street School","Feen School","Fun School","Fiction School","Fenn Street School","The programme was set in the fictional Fenn Street Secondary Modern School, and starred John Alderton as Bernard Hedges, a young teacher fresh out of training college.","m_e_8.webp",8,2);

//Found
        this.questionCreator("Which actor starred opposite Claire Bloom in the 1952 film ‘Limelight’?","Charlie Chaplin","Alana Love","Madilynn James","London Merritt","Charlie Chaplin","Sir Charles Spencer Chaplin (16 April 1889 – 25 December 1977) was an English comic actor, filmmaker, and composer who rose to fame in the era of silent film. Chaplin became a worldwide icon through his screen persona \"the Tramp\" and is considered one of the most important figures in the history of the film industry.","m_e_9.webp",8,2);

//Found
        this.questionCreator("What was is the name of Penelope Pitstop’s car in the children’s tv cartoon series ‘Wacky Races’?","Compact Pussycat","Pussycat","Cat","Compact","Compact Pussycat","Penelope Pitstop in 05 The Compact Pussycat. Sergeant Blast and Private Meekley in 06 The Army Surplus Special. The Ant Hill Mob in 07 The Bulletproof Bomb. Lazy Luke and Blubber Bear in 08 The Arkansas Chug-a-Bug.","m_e_10.webp",8,2);

//Found
        this.questionCreator("Which US musician played the role of Alias in the 1973 film ‘Pat Garrett and Billy the Kid’?","Bob Dylan","Lincoln Kramer","Kylie Ibarra","Ruth Frederick","Bob Dylan","1","m_e_11.webp",8,2);

//Found
        this.questionCreator("Who wrote the 1942 song ‘White Christmas’?","Irving Berlin","Phillip Fischer","Leia Carson","Raphael Case","Irving Berlin","Irving Berlin (born Israel Beilin (Russian: Израиль Моисеевич Бейлин) May 23 [O.S. May 11] 1888 – September 22, 1989) was an American composer and lyricist, widely considered one of the greatest songwriters in American history. His music forms a great part of the Great American Songbook.","m_e_12.webp",8,2);

//Found
        this.questionCreator("Who released a 2009 album entitled ‘The Element of Freedom’?","Alicia keys","Deshawn Lane","Mikayla Crosby","Maggie Cardenas","Alicia keys","The Element of Freedom is the fourth studio album by American singer and songwriter Alicia Keys, released on December 11, 2009, by J Records.","m_e_13.webp",8,2);

//Found
        this.questionCreator("What is the name of the convenience store in the tv cartoon show ‘The Simpsons’?","Kwik-E-Mart","Phoenix Rangel","Rachael Flynn","Carl Wilkinson","Kwik-E-Mart","The Kwik-E-Mart (spelled \"Quick-E-Mart\" in \"Bart the General\") is a convenience store in the animated television series The Simpsons. It is a parody of American convenience stores, such as 7-Eleven and Wawa Inc., and depicts many of the stereotypes about them.","m_e_14.webp",8,2);

//Found
        this.questionCreator("Who played Phil Archer in the BBC radio series ‘The Archers’?","Norman Painting","Paisley Lowe","Addisyn Buchanan","Markus Choi","Norman Painting","Norman George Painting (23 April 1924 – 29 October 2009) was an English actor, broadcaster and writer. He played Phil Archer in the BBC Radio 4 soap opera The Archers from the pilot episodes aired on the BBC Midlands Home Service in summer 1950, after the series went national on the Light Programme on 1 January 1951, until his death in 2009, when he was the longest-serving member of the cast.","m_e_15.webp",8,2);

//Found
        this.questionCreator("Lightning McQueen, Doc Hudson and Fillmore are all characters in which 2006 animated Disney film?","Cars","Wall-E","Monster","Up","Cars","Lightning McQueen is one of the main characters of the Disney/Pixar Cars franchise. He is a famous race car. He is the protagonist of Cars, the deuteragonist of Cars 2, and the protagonist of Cars 3.","m_e_16.webp",8,2);

//Found
        this.questionCreator("In which 1984 film did Johnny Depp make his debut film appearance?","A Nightmare On Elm Street","Happy Halloween","Hallow man","Hunger games","A Nightmare On Elm Street","Born in Kentucky in 1963, Johnny Depp landed his first legitimate movie role in Nightmare on Elm Street (1984). He began studying acting in earnest, the lessons paying off by 1987, when he landed a role on the TV show 21 Jump Street.","m_e_17.webp",8,2);

//Found
        this.questionCreator("Which Irish musician released a 1988 album entitled ‘Watermark’?","Enya","Eliana","Terry","Lorena","Enya","Eithne Pádraigín Ní Bhraonáin (anglicised as Enya Patricia Brennan (); born 17 May 1961), known professionally as Enya, is an Irish singer, songwriter and musician. Born into a musical family and raised in the Irish-speaking area of Gweedore in County Donegal, Enya began her music career when she joined her family\'s Celtic band Clannad in 1980 on keyboards and backing vocals.","m_e_18.webp",8,2);

//Found
        this.questionCreator("In 1964 who became the first black actor to win an Academy Award in the category ‘Best Actor in a leading Role’?","Sidney Poitier","Payten Rojas","Isabella Henderson","Gabriela Oneal","Sidney Poitier","Sir Sidney Poitier, (; born February 20, 1927) is a Bahamian-American actor, film director, author, and diplomat. In 1964, Poitier became the first Bahamian and first black actor to win an Academy Award for Best Actor, and the Golden Globe Award for Best Actor for his role in Lilies of the Field.","m_e_19.webp",8,2);

//Found
        this.questionCreator("Which ex-Playboy Bunny sang in a band called ‘Wind in the Willows’ before becoming the singer in one of the most famous bands of the 1970′s and 80′s?","Debbie Harry","Jan Richardson","Rihanna Mcclure","Kaleigh Hooper","Debbie Harry","Deborah Ann Harry (born Angela Trimble; July 1, 1945) is an American singer, songwriter, model and actress, known as the lead singer of the new wave band Blondie. Her recordings with the band reached the number-one charts place in the United States and the United Kingdom on many occasions through 1979 to 1981 (plus a sixth UK number-one in 1999).","m_e_20.webp",8,2);

//Found
        this.questionCreator("Which band released a 1979 album entitled ‘The Great Rock and Roll Swindle’?","Sex Pistols","Pistols","Happy","Sad","Sex Pistols","The Sex Pistols were an English punk rock band that formed in London in 1975. They were responsible for initiating the punk movement in the United Kingdom and inspiring many later punk and alternative rock musicians.","m_e_21.webp",8,2);

//Found
        this.questionCreator("What was the name of the high school in the US tv series ‘Happy Days’?","Jefferson High School","Common High School","Careless High School","Miracle High School","Jefferson High School","Milwaukee\'s Washington High School provided the inspiration for the exteriors of the fictional Jefferson.","m_e_22.webp",8,2);

//Found
        this.questionCreator("Which singer plays Chicago police officer Sharon Pogue in the 2001 film ‘Angel Eyes’?","Jennifer Lopez","Kallie Chang","Danny Church","Demetrius Novak","Jennifer Lopez","Jennifer Lynn Lopez (born July 24, 1969) is an American singer, actress, dancer and producer. In 1991, Lopez began appearing as a Fly Girl dancer on In Living Color, where she remained a regular until she decided to pursue an acting career in 1993.","m_e_23.webp",8,2);

//Found
        this.questionCreator("What is the name of the theme tune to the Uk television series ‘Monty Python’?","The Liberty Bell (March)","Bell (March)","Liberty (March)","Arc (March)","The Liberty Bell (March)","Four decades after Monty Python\'s Flying Circus changed the course of comedy, the rousing strains of the Liberty Bell march remain instantly recognisable. As Python\'s visionary animator Terry Gilliam said: \"Now you only think of it as the Python theme; nobody thinks of it as the Liberty Bell.\"","m_e_24.webp",8,2);

//Found
        this.questionCreator("In November 2011, which rock star was made an ‘Officer of the Order of Arts and Letters’ in Paris, one of France’s highest cultural awards?","Lenny Kravitz","Teagan Callahan","Zoie Fuentes","Jayden Gonzalez","Lenny Kravitz","Leonard Albert Kravitz (born May 26, 1964) is an American singer, songwriter, actor, and record producer. His \"retro\" style incorporates elements of rock, blues, soul, R&B, funk, jazz, reggae, hard rock, psychedelic, pop, folk, and ballads.","m_e_25.webp",8,2);

//Found
        this.questionCreator("Which musical instrument does fictional detective Sherlock Holmes play?","Violin","Drum","Guitar","Flute","Violin","The violin, also known informally as a fiddle, is a wooden string instrument in the violin family. Most violins have a hollow wooden body.","m_e_26.webp",8,2);

//Found
        this.questionCreator("Which UK children’s tv show, launched in 1968, had a mascot called Murgatroyd?","Magpie","Robot","Book","Computer","Magpie","Magpies are birds of the Corvidae (crow) family. The black and white Eurasian magpie is widely considered one of the most intelligent animals in the world and one of only a few non-mammal species able to recognize itself in a mirror test.","m_e_27.webp",8,2);

//Found
        this.questionCreator("Who plays Debbie Aldridge in the BBC 4 radio series ‘The Archers’?","Tamsin Greig","Tianna Christian","Annalise Ritter","Vicente Holt","2","Tamsin Margaret Mary Greig (; born 12 July 1966) is an English actress. She played Fran Katzenjammer in Black Books, Dr Caroline Todd in Green Wing and Beverly Lincoln in the British-American sitcom Episodes.","m_e_28.webp",8,2);

//Found
        this.questionCreator("What is the title of the 1959 film in which Hayley Mills plays a young girl who befriends a murderous Polish sailor?","Tiger Bay","The Sleep","Criminals","Walk","Tiger Bay","Tiger Bay (Welsh: Bae Teigr) was the local name for an area of Cardiff which covered Butetown and Cardiff Docks. It was rebranded as Cardiff Bay, following the building of the Cardiff Barrage, which dams the tidal rivers, Ely and Taff, to create a body of water.","m_e_29.webp",8,2);

//Found
        this.questionCreator("In which British tv sitcom did Tony Britten and Nigel Havers play father and son doctors Toby and Tom Latimer?","Don’t Wait Up","Don\'t Give Up","Stay","Chaos","Don’t Wait Up","Don\'t Wait Up is a British sitcom that was broadcast for six series from 1983 to 1990 on BBC1. It starred Nigel Havers, Tony Britton and Dinah Sheridan, and was written by George Layton. It was directed and produced by Harold Snoad, who also directed and produced another sitcom, Keeping Up Appearances.","m_e_30.webp",8,2);

//Found
        this.questionCreator("In which Oscar-winning 1964 film did actress Julie Andrews make her feature film debut?","Mary Poppins","Wesley Mccall","Jaylene Yoder","Mikaela Shannon","Mary Poppins","Mary Poppins is a series of eight children\'s books written by British writer P. L. Travers and published over the period 1934 to 1988. Mary Shepard was the illustrator throughout the series.The books centre on the magical English nanny Mary Poppins, who is blown by the East wind to Number 17 Cherry Tree Lane, London, and into the Banks\' household to care for their children.","m_e_31.webp",8,2);

//Found
        this.questionCreator("In the Harry Potter series of books during which month of the year is Harry Potter’s birthday?","July","June","May","April","July","July is the seventh month of the year (between June and August) in the Julian and Gregorian Calendars and the fourth of seven months to have a length of 31 days. It was named by the Roman Senate in honour of Roman general Julius Caesar, it being the month of his birth.","m_e_32.webp",8,2);

//Found
        this.questionCreator("Which English singer/songwriter released a 2010 album entitled ‘Lights’?","Ellie Goulding","Jaylyn Nielsen","Hudson Ho","Alexander Paul","Ellie Goulding","Elena Jane Goulding ( GOHL-ding; born 30 December 1986) is an English singer and songwriter. Her career began when she met record producers Starsmith and Frankmusik, and she was later spotted by Jamie Lillywhite, who later became her manager and A&R. After signing to Polydor Records in July 2009, Goulding released her debut extended play, An Introduction to Ellie Goulding later that year.","m_e_33.webp",8,2);

//Found
        this.questionCreator("Which celebrity won the first series of the UK television show ‘Strictly Come Dancing’?","Natasha Kaplinsky","Leonardo Jackson","Yosef Baird","Halle Jimenez","Natasha Kaplinsky","Natasha Margaret Kaplinsky OBE (born 9 September 1972) is an English newsreader, TV presenter and journalist, best known for her roles as a studio anchor on Sky News, BBC News, Channel 5 and ITV News.After two years at Sky News, Kaplinsky joined BBC News in 2002 where she co-hosted Breakfast until 2005, when she became the host of the Six O\'Clock News.","m_e_34.webp",8,2);

//Found
        this.questionCreator("What are the names in the two rival taxicab firms in the 1963 film ‘Carry On Cabby’?","Speedee Taxis and Glamcabs","Cabs and Taxs","Taxy and Speedy","S and T","Speedee Taxis and Glamcabs","Charlie (Sid James), owner of Speedee Taxis, finds himself up against serious competition when a new firm, Glamcabs driven by glamorous women, is set up and starts stealing all his business.","m_e_35.webp",8,2);

//Found
        this.questionCreator("What is the surname of Scottish-born singer and musician Donovan?","Leitch","Herring","Fry","Tyler","Leitch","Donovan Philips Leitch is a Scottish singer, songwriter and guitarist. He developed an eclectic and distinctive style that blended folk, jazz, pop, psychedelia, and world music. He has lived in Scotland, Hertfordshire, London, California, and since at least 2008 in County Cork, Ireland, with his family.","m_e_36.webp",8,2);

//Found
        this.questionCreator("What is the title of the 1944 film starring Barbara Stanwyck as the wife who seduces an insurance salesman into killing her husband?","Double Indemnity","The Man","Human","Ash boy","Double Indemnity","Double Indemnity is a 1944 film noir crime drama directed by Billy Wilder, co-written by Wilder and Raymond Chandler, and produced by Buddy DeSylva and Joseph Sistrom.","m_e_37.webp",8,2);

//Found
        this.questionCreator("Who was TV puppet Lenny the Lion’s human partner?","Terry Hall","Danielle Choi","Britney Escobar","Sofia Duarte","Terry Hall","Terence \"Terry\" Hall (20 November 1926 – 3 April 2007) was an English ventriloquist. He appeared regularly on television with his puppet, Lenny the Lion, whose catchphrase was \"Aw, don\'t embawass me!\" Hall is credited with having been one of the first ventriloquists to use a non-human puppet.","m_e_38.webp",8,2);

//Found
        this.questionCreator("Who was the 2003 winner of the UK tv show ‘I’m a Celebrity, Get Me Out of Here’?","Phil Tufnell","Jaxon Bradley","Dalton Beck","Nylah Peck","Phil Tufnell","Philip Clive Roderick Tufnell (born 29 April 1966) is a former English Test and ODI cricketer turned television personality. A slow left-arm orthodox spin bowler, he played 42 Tests and 20 One Day International matches for England, as well as playing for Middlesex from 1986 to 2002.","m_e_39.webp",8,2);

//Found
        this.questionCreator("Which songwriter and rapper’s real name is Timothy Z Mosley?","Timbaland","Busta Rhyme","Travis Scott","Drake","Timbaland","Timothy Zachary Mosley (born March 10, 1972), known professionally as Timbaland, is an American record producer, rapper, singer, songwriter and DJ.Timbaland\'s first full credit production work was in 1996 on Ginuwine...the Bachelor for R&B singer Ginuwine. After further work on Aaliyah\'s 1996 album One in a Million and Missy Elliott\'s 1997 album Supa Dupa Fly, Timbaland became a prominent producer for R&B and hip hop artists.","m_e_40.webp",8,2);

//Found
        this.questionCreator("Which song by Irish band U2 is a tribute to Martin Luther King Jr?","Pride (In the Name of Love)","Catch","Tell Me Once More","Eat All you can","Pride (In the Name of Love)","Pride (In the Name of Love) is a song by Irish rock band U2. The second track on the band\'s 1984 album, The Unforgettable Fire, it was released as the album\'s lead single in September 1984.","m_e_41.webp",8,2);

//Found
        this.questionCreator("In the game ‘Doom’, which planet is the space marine posted to after assaulting his commanding officer?","Mars","Pluto","Jupiter","Earth","Mars","Mars is the fourth planet from the Sun and the second-smallest planet in the Solar System after Mercury. In English, Mars carries a name of the Roman god of war, and is often referred to as the \"Red Planet\" because the reddish iron oxide prevalent on its surface gives it a reddish appearance that is distinctive among the astronomical bodies visible to the naked eye.","m_e_42.webp",8,2);

//Found
        this.questionCreator("Which actor replaced Charlie Sheen in the US television series ‘Two and a Half Men’?","Ashton Kutcher","Dana Vega","Ezekiel Fox","Amy Herman","Ashton Kutcher","Christopher Ashton Kutcher (; born February 7, 1978) is an American actor and investor. He began his acting career portraying Michael Kelso in the Fox sitcom That \'70s Show (1998–2006).","m_e_43.webp",8,2);

//Found
        this.questionCreator("Late American actor Bernard Schwarz was better known by what name?","Tony Curtis","Bryanna Casey","Tomas Friedman","Gaven Pineda","Tony Curtis","Tony Curtis (born Bernard Schwartz; June 3, 1925 – September 29, 2010) was an American film actor whose career spanned six decades but who achieved the height of his popularity in the 1950s and early 1960s. He acted in more than 100 films in roles covering a wide range of genres, from light comedy to serious drama.","m_e_44.webp",8,2);

//Found
        this.questionCreator("In which 1979 film did Pierce Brosnan make his movie debut as an IRA hitman?","The Long Good Friday","The Good One","Help me","Wait a second","The Long Good Friday","The Long Good Friday is a British gangster film starring Bob Hoskins and Helen Mirren. It was completed in 1979, but because of release delays, it is generally credited as a 1980 film.","m_e_45.webp",8,2);

//Found
        this.questionCreator("The US tv show ‘All in the Family’ was based on which British tv sitcom?","Till Death Us Do Part","Till Make One","Gambling","Once Again","Till Death Us Do Part","Till Death Us Do Part is a British television sitcom that aired on BBC1 from 1965 to 1975. The show was first broadcast as a Comedy Playhouse pilot, then in seven series until 1975.","m_e_46.webp",8,2);

//Found
        this.questionCreator("Which British presenter spoke the first words on GMTV when it was launched in January 1993?","Eamonn Holmes","Gaven Pineda","Kyleigh Davila","Kenneth Sosa","Eamonn Holmes","Eamonn Holmes (born 3 December 1959) is a journalist and broadcaster from Northern Ireland, best known for presenting Sky News Sunrise and This Morning. Holmes currently presents drivetime (4–7pm) weekdays on Talkradio, and is the lead relief anchor for Good Morning Britain.","m_e_47.webp",8,2);

//Found
        this.questionCreator("What is the title of the only ‘Dirty Harry’ film directed by Clint Eastwood?","Sudden Impact","Suddenly","Step two","Sit","Sudden Impact","Sudden Impact is a 1983 American action thriller and the fourth film in the Dirty Harry series, directed by Clint Eastwood (making it the only Dirty Harry film to be directed by Eastwood himself), and starring Eastwood and Sondra Locke. The film tells the story of a gang rape victim (Locke) who decides to seek revenge on the rapists ten years after the attack by killing them one by one.","m_e_48.webp",8,2);

//Found
        this.questionCreator("Actor Harris Glenn Milstead was better known by what name?","Divine","Crane","Jeremiah","Soto","Divine","Harris Glenn Milstead, better known by his stage name Divine (October 19, 1945 – March 7, 1988), was an American actor, singer, and drag queen.","m_e_49.webp",8,2);

//Found
        this.questionCreator("What make and model car did James Bond drive in the 1962 film ‘Dr No’?","Sunbe","Honda","Toyota","Ferrari","Sunbe","in this film we see Bond (Sean Connery) driving a Sunbeam Alpine, and the trip to Bond it comes in different tops most famous chase scenes in cinema history. And this was very first car which drove 007 in James Bond movie series","m_e_50.webp",8,2);

//Found
        this.questionCreator("In which 1932 film does Greta Garbo say the line ‘I want to be alone’?","Grand Hotel","Gransino","Green Island","Green Land","Grand Hotel","A grand hotel is a large and luxurious hotel, especially one housed in a building with traditional architectural style.","m_e_51.webp",8,2);

//Found
        this.questionCreator("‘The Hunting of the …’what’? is a poem by Lewis Carroll?","Snark","Shark","Shake","Snorlax","Snark","It is typically categorised as a nonsense poem. Written from 1874 to 1876, the poem borrows the setting, some creatures, and eight portmanteau words from Carroll\'s earlier poem \"Jabberwocky\" in his children\'s novel Through the Looking-Glass (1871).","m_e_52.webp",8,2);

//Found
        this.questionCreator("Lenny Small and George Milton are the main characters in which novel by John Steinbeck?","Of Mice and Men","Of men","Of Mice","Of Cat and Mice","Of Mice and Men","Of Mice and Men is a novella written by author John Steinbeck. Published in 1937, it tells the story of George Milton and Lennie Small, two displaced migrant ranch workers, who move from place to place in California in search of new job opportunities during the Great Depression in the United States.","m_e_53.webp",8,2);

//Found
        this.questionCreator("Which US actor is the voice of Gru in the 2010 animated film ‘Despicable Me’?","Steve Carell","Wyatt Moss","Zackary Hendrix","Paisley Lowe","Steve Carell","Steven John Carell (; born August 16, 1962) is an American actor, comedian, producer, writer and director known for playing a variety of characters, including Michael Scott on the American version of The Office (2005–2013), on which he also worked as an occasional producer, writer and director.Carell was a correspondent on The Daily Show with Jon Stewart from 1999 to 2005.","m_e_54.webp",8,2);

//Found
        this.questionCreator("What is the title of the fourth ‘Dirty Harry’ film starring Clint Eastwood?","Sudden Impact","Impact of Eagle","Shrink","Big Eagle","Sudden Impact","Sudden Impact is a 1983 American action thriller and the fourth film in the Dirty Harry series, directed by Clint Eastwood (making it the only Dirty Harry film to be directed by Eastwood himself), and starring Eastwood and Sondra Locke. The film tells the story of a gang rape victim (Locke) who decides to seek revenge on the rapists ten years after the attack by killing them one by one.","m_e_55.webp",8,2);

//Found
        this.questionCreator("Which late actor played Albus Dumbledore in the first two Harry potter films?","Richard Harris","Jaylen Bentley","Shaniya Mack","Bryant Zimmerman","Richard Harris","Richard St. John Harris (1 October 1930 – 25 October 2002) was an Irish actor and singer.","m_e_56.webp",8,2);

//Found
        this.questionCreator("Who directed the 1962 film ‘Lawrence of Arabia’?","David Lean","Kristian Benson","Abel Norris","Lorenzo Brennan","David Lean","Sir David Lean (25 March 1908 – 16 April 1991) was an English film director, producer, screenwriter and editor, responsible for large-scale epics such as The Bridge on the River Kwai (1957), Lawrence of Arabia (1962), Doctor Zhivago (1965) and A Passage to India (1984). He also directed adaptations of Charles Dickens novels Great Expectations (1946) and Oliver Twist (1948), as well as the romantic drama Brief Encounter (1945).","m_e_57.webp",8,2);

//Found
        this.questionCreator("In which Oscar-winning 1964 film did actress Julie Andrews make her feature film debut?","Mary Poppins","Jonathon Griffith","Jared Robbins","Peyton Boyer","Mary Poppins","Mary Poppins is a series of eight children\'s books written by British writer P. L. Travers and published over the period 1934 to 1988. Mary Shepard was the illustrator throughout the series.","m_e_58.webp",8,2);

//Found
        this.questionCreator("In the Harry Potter series of books what is the name of the driver of the Knight Bus?","Ernie Prang","Stephanie Andrews","Clayton Melendez","Elisabeth Khan","Ernie Prang","The driver and conductor of the Knight Bus in Harry Potter and the Prisoner of Azkaban are named after my two grandfathers, Ernest and Stanley.","m_e_59.webp",8,2);

//Found
        this.questionCreator("Actor Sid James played the character Sid Boggle in which ‘Carry On’ film?","Carry On Camping","Carry","On Camping","Camp with Fire","Carry On Camping","Carry On Camping is a 1969 British comedy film, the seventeenth in the series of Carry On films to be made. It features series regulars Sid James, Kenneth Williams, Charles Hawtrey, Joan Sims, Terry Scott, Hattie Jacques, Barbara Windsor, Bernard Bresslaw and Peter Butterworth.","m_e_60.webp",8,2);

//Found
        this.questionCreator("In the James Bond film ‘Goldfinger’, who is Goldfinger’s bodyguard?","Oddjob","Calvin","Okrey","Hansel","Oddjob","Oddjob (often written as \"Odd Job\") is a fictional character in the espionage novels and films featuring James Bond. He is a henchman to the villain Auric Goldfinger in the 1959 James Bond novel Goldfinger and its 1964 film adaptation.","m_e_61.webp",8,2);

//Found
        this.questionCreator("Who played waitress Maggie Fitzgerald in the 2004 film ‘Million Dollar Baby’?","Hilary Swank","Anabelle George","Aimee Pena","Jayla Taylor","Hilary Swank","Hilary Ann Swank (born July 30, 1974) is an American actress and producer. She has received two Academy Awards, two Golden Globe Awards, two Critics Choice Awards, and a Screen Actors Guild Award.","m_e_62.webp",8,2);

//Found
        this.questionCreator("Bert Handy, Penny Panting and Lily Duveen are all characters in which ‘Carry On’ film?","Carry On Regardless","Carry On Man","Carry On Street","Carry On Guilt","Carry On Regardless","Carry On Regardless was the fifth in the series of Carry On films to be made being released in 1961. By now a fairly regular team was established with Sid James, Kenneth Connor, Charles Hawtrey, Joan Sims and Kenneth Williams all having appeared in previous entries.","m_e_63.webp",8,2);

//Found
        this.questionCreator("The 2005 film ‘Wolf Creek’ is set in which country?","Australia","Alabama","U.S.A","Africa","Australia","Australia, officially the Commonwealth of Australia, is a sovereign country comprising the mainland of the Australian continent, the island of Tasmania and numerous smaller islands. It is the largest country in Oceania and the world\'s sixth-largest country by total area.","m_e_64.webp",8,2);

//Found
        this.questionCreator("Gobinda is the henchman in which James Bond film?","Octopussy","007","In the name of love","Call Me Bond","Octopussy","Octopussy is a 1983 British spy film, the thirteenth in the James Bond series produced by Eon Productions, and the sixth to star Roger Moore as the fictional MI6 agent James Bond.The film\'s title is taken from a short story in Ian Fleming\'s 1966 short story collection Octopussy and The Living Daylights, although the film\'s plot is original.","m_e_65.webp",8,2);

//Found
        this.questionCreator("What is the name of the title character in the 2005 animated film ‘Corpse Bride’?","Emily","Anabelle","Jacob","Amanda","Emily","Emily \"The Corpse Bride\", is a living corpse who was a talented and wealthy lady in her lifetime, as well as the self-proclaimed bride of the young Victor Van Dort after their encounter at the woods. She was murdered by her ex-fiancee Lord Barkis Bittern, and indirectly killed him shortly before gaining her freedom.","m_e_66.webp",8,2);

//Found
        this.questionCreator("In the Star Wars series of films who became Jedi Knight Qui-Gon Jinn’s apprentice on his 13th birthday?","Obi-Wan Kenobi","Obi","Wan","Kenobi","Obi-Wan Kenobi","Obi-Wan Kenobi, later known as Ben Kenobi, is a fictional character in the Star Wars franchise. Within the original trilogy he is portrayed by English actor Alec Guinness, while in the prequel trilogy a younger version of the character is portrayed by Scottish actor Ewan McGregor.","m_e_67.webp",8,2);

//Found
        this.questionCreator("In the Star Wars series of films who played Sabe, one of Padme Amidala’s handmaidens, in ‘The Phantom Menace’?","Keira Knightly","Stephanie Andrews","Clayton Melendez","Elisabeth Khan","Keira Knightly","Keira Knightley (born March 26, 1985) played Queen Amidala\'s handmaiden Sabé in Star Wars: Episode I The Phantom Menace at the age of twelve during August 1997 at Leavesden Film Studios, England.","m_e_68.webp",8,2);

//Found
        this.questionCreator("In the novel ‘The Lord of the Rings’ by Tolkein, what is the name of the horse given to Gandalf by King Theoden?","Shadowfax","Jayla","George","Phillips","Shadowfax","Shadowfax was \"the Lord of all horses\". He was a descendant of Felaróf, of the race of the long-lived Mearas, the greatest horses of Middle-earth. Shadowfax was capable of comprehending human speech and was said to run faster than the wind.","m_e_69.webp",8,2);

//Found
        this.questionCreator("In the Harry Potter series of books which animal is James Potter’s ‘Patronus’","A Dog","A Boy","A Girl","A Pig","A Dog","James Potter was an animagus. Animaguses are related to patronuses. Like Sirius\'s patronus is a black dog and his animagus is a black dog too. So his nickname “Padfoot” goes with Sirius like James nickname is “Prongs”, a type of Deer which was his patronus and animagus.","m_e_70.webp",8,2);

//Found
        this.questionCreator("In the Harry Potter series of books what is the Hogwarts School motto in English?","Never Tickle a Sleeping Dragon","Never Ever Sleep","Never Make a Mistake","Never Try to Kill","Never Tickle a Sleeping Dragon","Hogwarts, the School of Witchcraft and Wizardry in the Harry Potter books, has the following Latin motto: Draco dormiens numquam titillandus. Most online sources translate this as \"Never tickle a sleeping dragon\".","m_e_71.webp",8,2);

//Found
        this.questionCreator("Tennis player Vijay Amritraj appeared in which James Bond film?","Octopussy","Careless","Bang bang","Shotgun","Octopussy","Octopussy is a 1983 British spy film, the thirteenth in the James Bond series produced by Eon Productions, and the sixth to star Roger Moore as the fictional MI6 agent James Bond.","m_e_72.webp",8,2);

//Found
        this.questionCreator("What is the name of the sorcerer in Disney’s ‘Fantasia’?","Yen Sid","Mila Huff","Aryan Harrell","Cornelius Roach","Yen Sid","The nickname Yen Sid (\"Disney\" spelled backwards) was given by Disney animators, as the sorcerer in \"The Sorcerer\'s Apprentice\" segment of Fantasia had no onscreen name, nor does the character in the original Goethe poem \"Der Zauberlehrling\", the inspiration for the music piece by Paul Dukas.","m_e_73.webp",8,2);

//Found
        this.questionCreator("Who played the role of Georgiana Cavendish in the 2008 film ‘The Duchess’?","Keira Knightly","Charlize Frye","Gretchen Lam","Matthew Holland","Keira Knightly","Keira Christina Knightley OBE (born 26 March 1985) is an English actress. She has worked in the British and American film industries, and has starred in Broadway and West End theatre productions. She has won an Empire Award and multiple nominations for British Academy, Golden Globe, and Academy Awards.","m_e_74.webp",8,2);

//Found
        this.questionCreator("Drizella Tremaine, Gus, Bruno and Lucifer are all characters in which Disney film?","Cinderella","Alladin","Nemo","Beauty & The Beast","Cinderella","Cinderella (Italian: Cenerentola; French: Cendrillon; German: Aschenputtel), or The Little Glass Slipper, is a folk tale embodying a myth-element of unjust oppression and triumphant reward. Thousands of variants are known throughout the world.","m_e_75.webp",8,2);

//Found
        this.questionCreator("Who plays groundsman Carl Spackler in the 1980 film ‘Caddyshack’?","Bill Murray","Catalina Finley","Cloe Winters","Pedro Bryan","Bill Murray","William James Murray (born September 21, 1950) is an American actor, comedian, and writer. He first gained exposure on Saturday Night Live, a series of performances that earned him his first Emmy Award, and later starred in comedy films—including Meatballs (1979), Caddyshack (1980), Stripes (1981), Tootsie (1982), Ghostbusters (1984), Scrooged (1988), Ghostbusters II (1989), What About Bob? (1991), and Groundhog Day (1993).","m_e_76.webp",8,2);

//Found
        this.questionCreator("Who wrote the 1885 novel ‘King Solomon’s Mines’?","H. Rider Haggard","Sonia Carey","Evangeline Bradshaw","Lindsey Villegas","H. Rider Haggard","Sir Henry Rider Haggard, (; 22 June 1856 – 14 May 1925), known as H. Rider Haggard, was an English writer of adventure novels set in exotic locations, predominantly Africa, and a pioneer of the Lost World literary genre. He was also involved in agricultural reform throughout the British Empire.","m_e_77.webp",8,2);

//Found
        this.questionCreator("The 2006 film ‘Once’, starring Glen Hansard as a busker, is set in which European city?","Dublin","Paris","Alibama","London","Dublin","Dublin (; Irish: Baile Átha Cliath [ˈbˠalʲə aːhə ˈclʲiə; ˌbʲlʲaː ˈclʲiə]) is the capital and largest city in Ireland. Dublin is in the province of Leinster on the east coast of Ireland, at the mouth of the River Liffey and bordered on the south by the Wicklow Mountains.","m_e_78.webp",8,2);

//Found
        this.questionCreator("Jason Vorhees is a fictional character, who uses a machete on his victims, in which series of films?","Friday the 13th","Michael Myers","Jason X","Cannibal","Friday the 13th","Friday the 13th is considered an unlucky day in Western superstition. It occurs when the 13th day of the month in the Gregorian calendar falls on a Friday, which happens at least once every year but can occur up to three times in the same year,for example in 2015, on Feb 13, March 13 and October 13.","m_e_79.webp",8,2);

//Found
        this.questionCreator("In Italian translation who is Mr. Kiss Kiss Bang Bang?","James Bond","Keanu Reeves","Chris Pratt","Adam Sandler","James Bond","James Bond is an intelligence officer in the Secret Intelligence Service, commonly known as MI6. Bond is known by his code number, 007, and was a Royal Naval Reserve Commander.","m_e_80.webp",8,2);

//Found
        this.questionCreator("Plenty of celebs find love through work. But which couple DIDN'T meet on set?","Ryan Reynolds and Scarlett Johansson","Ben Affleck and Jennifer Garner","Bradley Cooper and Zoe Saldana","Matthew Rhys and Keri Russell","Ryan Reynolds and Scarlett Johansson","Ryan Reynolds and Scarlett Johansson — married from 2008 until 2011 — didn't meet while working together. Actually, how did they meet? Anyway, Ben and Jen met on Daredevil, though it took them a few years to figure out they were right for each other.","m_e_81.webp",8,2);

//Found
        this.questionCreator("Meryl Streep, Shaquille O'Neal, and Whitney Houston are all from which state?","New Jersey","New York","California","Massachusetts","New Jersey","New Jersey is a state in the Mid-Atlantic region of the Northeastern United States.","m_e_82.webp",8,2);

//Found
        this.questionCreator("What is the last name of fashion designer Donatella, sister of the murdered Gianni?","Versace","Gucci","Dior","Lacoste","Versace","Donatella Francesca Versace is an Italian fashion designer and current vice president of the Versace Group, as well as its chief designer.","m_e_83.webp",8,2);

//Found
        this.questionCreator("In the 1972 movie The Godfather who played the Godfather?","Marlon Brando","James Dean","Robert Duvall","James Caan","Marlon Brando","Marlon Brando Jr. was an American actor and film director.","m_e_84.webp",8,2);

//Found
        this.questionCreator("In the 1994 movie Forrest Gump, who played the lead actor?","Tom Hanks","Jean Reno","Clint Eastwood","Gary Oldman","Tom Hanks","Thomas Jeffrey Hanks is an American actor and filmmaker. Hanks is known for his comedic and dramatic roles in such films.","m_e_85.webp",8,2);

//Found
        this.questionCreator("Aishwarya Rai was crowned Miss World in which year?","Nineteen ninety four-1994","Nineteen ninety five-1995","Nineteen ninety three-1993","Nineteen ninety six-1996","Nineteen ninety four-1994","Aishwarya Rai, also known by her married name Aishwarya Rai Bachchan, is an Indian actress, model and the winner of the Miss World 1994 pageant.","m_e_86.webp",8,2);

//Found
        this.questionCreator("This Philippine singer won international attention by starring in an episode of The Ellen Degenere's Show.","Zendee Tenefere","KZ Tandingan","Jed Madela","Yeng Constantino","Zendee Tenefere","Zendee Rose Tenerefe is a Filipina singer, who rose to prominence after a video of her singing a karaoke version of Whitney Houston's \"I Will Always Love You\" was put on YouTube.","m_e_87.webp",8,2);

//Found
        this.questionCreator("According to Forbes 2012, who is the world's most powerful celebrity?","Jennifer Lopez","Justin Beiber","Ophrah Winfrey","Katty Perry","Jennifer Lopez","Jennifer Lynn Lopez is an American singer, actress, dancer and producer. In 1991, Lopez began appearing as a Fly Girl dancer on In Living Color, where she remained a regular until she decided to pursue an acting career in 1993.","m_e_88.webp",8,2);

//Found
        this.questionCreator("What is the top grossing Philippine film of all time?","Praybeyt Benjamin","Tanging Ina","No Other Woman","Bonifacio ang unang Pangulo","Praybeyt Benjamin","The Amazing Praybeyt Benjamin is a 2014 comedy film written and directed by Wenn V. Deramas. It is the sequel to The Unkabogable Praybeyt Benjamin. The film is one of the official entries to the 40th Metro Manila Film Festival. Vice Ganda reprised his role as Colonel Benjamin \"Benjie\" Santos VIII.","m_e_89.webp",8,2);

//Found
        this.questionCreator("Which Spice girl was the first to marry?","Mel B","Victoria Beckham","Emma Bunton","Melanie C","Mel B","Melanie Janine Brown, also known as Mel B or Melanie B, is an English singer, songwriter, actress, and television personality.","m_e_90.webp",8,2);

//Found
        this.questionCreator("Giorgio Armani is famous in which field?","Fashion Designer","Director","Model","Actor","Fashion Designer","Giorgio Armani is an Italian fashion designer. He is known today for his clean, tailored lines. He formed his company, Armani, in 1975, and by 2001 was acclaimed as the most successful designer of Italian origin.","m_e_91.webp",8,2);

//Found
        this.questionCreator("This Latin superstar took the whole world by storm in 1999. He is the \"hero\" you might wish to \"escape\" with, and he is also the son of a famous crooner and a Filipina.","Enrique Iglesias","Ricky Martin","Enrique Gil","Bamboo","Enrique Iglesias","Enrique Miguel Iglesias Preysler is a Spanish singer, songwriter, actor and record producer. He is widely regarded as the King of Latin Pop.","m_e_92.webp",8,2);
//Not Found
        this.questionCreator("He is American pop star made a sensation in Europe, and you've probably kept on \"trackin\" with him. He started out, however, as a child star in the Philippines before moving to America.","Billy Crawford","Ricky Martin","Nick Carter","Justin Timberlake","Billy Crawford","Billy Joe Ledesma Crawford is a Filipino-American singer, dancer, songwriter, actor and TV host.","m_e_93.webp",8,2);

//Found
        this.questionCreator("He kicked some butt in the TV Series \"Mortal Kombat\" and was Brandy's Prince Charming in \"Cinderella\", where Whitney Houston was the Fairy Godmother.","Paolo Montalban","Ryan Gosling","Zen Gesner","Krista Ranillo","Paolo Montalban","Paolo also made a Filipino film, set in the US, about Filipino migrants who search for the American Dream. Its title is \"American Adobo\".","m_e_94.webp",8,2);

//Found
        this.questionCreator("Calvin Klein best fits which of these descriptions?","Fashion Designer","Played Detective On NYPD Blue","A Detective","Newspaper Advice Columnist","Fashion Designer","Calvin Richard Klein is a Hungarian-American fashion designer who launched the company that would later become Calvin Klein Inc., in 1968.","m_e_95.webp",8,2);

//Found
        this.questionCreator("This genius was born in Italy in 1452 and died in France in 1519. He was a painter and inventor, among many other things. He is reported to have been left handed.","Leonardo da Vinci","Leonardo di Caprio","Leonardo Bistolfi","Leonardo Sandri","Leonardo da Vinci","Leonardo da Vinci, one of the most brilliant men in history, wrote from right to left and in mirror image. This is certainly easier for a person using their left hand, especially writing with ink, although it appears he may have been ambidextrous.","m_e_96.webp",8,2);

//Found
        this.questionCreator("Who was the most googled celeb of 2017?","Meghan Markle","Beyoncé","Kevin Spacey","Selena Gomez","Meghan Markle","Meghan, Duchess of Sussex, is an American-born member of the British royal family and a former film and television actress. Markle was born and raised in Los Angeles, California, and is of mixed-race heritage.","m_e_97.webp",8,2);

//Found
        this.questionCreator("Which celeb produced 13 Reasons Why?","Selena Gomez","Demi Lovato","Taylor Swift","Katherine Langford","Selena Gomez","Selena Marie Gomez is an American singer, actress, and producer.","m_e_98.webp",8,2);

//Found
        this.questionCreator("Who was the most tweeted about celeb of 2017?","Camila Cabello","Donald Trump","Meghan Markle","Harry Styles","Camila Cabello","Camila Cabello is mainly a pop and R&B singer, influenced by Latin music. She incorporated elements of reggaeton, hip hop and dancehall in her first album.","m_e_99.webp",8,2);

//Found
        this.questionCreator("Who was announced to play Nala in the upcoming live action remake of The Lion King?","Beyonce","Meryl Streep","Blake Lively","Gigi Hadid","Beyonce","Beyoncé Giselle Knowles-Carter is an American singer, songwriter, performer, and actress. Born and raised in Houston, Texas, Beyoncé performed in various singing and dancing competitions as a child.","m_e_100.webp",8,2);

//Found
        this.questionCreator("Who was the highest paid model of the year 2017?","Kendall Jenner","Jourdan Dunn","Gigi Hadid","Alessandra Ambrosia","Kendall Jenner","Kendall Nicole Jenner is an American model and television personality.","m_e_101.webp",8,2);

//Found
        this.questionCreator("What British actor was linked to Taylor Swift following her breakup with Calvin Harris?","Tom Hiddleston","Benedict Cumberbatch","James Corden","Daniel Craig","Tom Hiddleston","Thomas William Hiddleston is an English actor, film producer and musician.","m_e_102.webp",8,2);

//Found
        this.questionCreator("Who returned from the dead on \"Game of Thrones\"?","Jon Snow","Jon Hamm","Ramsay","Michonne","Jon Snow","Jon Snow, born Aegon Targaryen, is the son of Lyanna Stark and Rhaegar Targaryen, the late Prince of Dragonstone.","m_e_103.webp",8,2);

//Found
        this.questionCreator("Who had the top-grossing tour of 2016, according to StubHub?","Adele","Beyonce","Bruce Springsteen","Luke Bryan","Adele","Adele Laurie Blue Adkins MBE is an English singer and songwriter.","m_e_104.webp",8,2);

//Found
        this.questionCreator("British actress famous as the female star of the Underworld series of films.","Kate Beckinsale","Emily Blunt","Emma Watson","Julie Andrews","Kate Beckinsale","Kathrin Romary Beckinsale is an English actress.","m_e_105.webp",8,2);

//Found
        this.questionCreator("Where was Oprah Winfrey born?","Mississippi","New York","Illinois","Georgia","Mississippi","Oprah Winfrey was born in the rural town of Kosciusko, Mississippi, on January 29, 1954. In 1976, Winfrey moved to Baltimore, where she hosted a hit television chat show, People Are Talking.","m_e_106.webp",8,2);

//Found
        this.questionCreator("A 2001 Taiwanese drama starring Barbie Shu, Jerry Yan, Vic Zhou, Vanness Wu and Ken Chu. It is loosely based on Japanese shōjo manga.","Meteor Garden","The Grudge","Sadako","The Ring","Meteor Garden","The story centers around a poor teenage girl Shān Cài (Barbie Shu), who at the insistence of her parents goes to a university for rich people. The university is dominated by Dào Míng Sì (Jerry Yan), Huā Zé Lèi (Vic Zhou), Měi Zuò (Vanness Wu) and Xī Mén (Ken Chu)—four rich, handsome but arrogant students collectively known as the \'F4\', short for \'Flower 4\'. They are heirs to four rich and influential families in Taiwan. They terrorize the school by handing out red cards to those they do not like, which allows other students to bully the victims until they leave the school.","m_e_107.webp",8,2);


    }

    private void addEntertainmentDifficult()
    {
        //check

//Found
        this.questionCreator("\"Start spreading the news, I’m leaving today\" are the opening lines of which song?","New York, New York","California Love","Is this love","I Love you","New York, New York","\"Theme from New York, New York\" (or \"New York, New York\") is the theme song from the Martin Scorsese film New York, New York (1977), composed by John Kander, with lyrics by Fred Ebb. It was written for and performed in the film by Liza Minnelli. It remains one of the best-known songs about New York City. In 2004 it finished #31 on AFI\'s 100 Years. 100 Songs survey of top tunes in American Cinema.","d_e_1.webp",8,3);

//Found
        this.questionCreator("In which television programme was \"I don’t believe it\" a catchphrase?","One Foot in the Grave","One Foot Away","Come again","Goodluck","One Foot in the Grave","One Foot in the Grave is a British television sitcom written by David Renwick. There were six series and seven Christmas specials over an eleven-year period, from early 1990 to late 2000.","d_e_2.webp",8,3);

//Found
        this.questionCreator("You Give Love a Bad Name and Livin’ on a Prayer were early hits for which band?","Bon Jovi","Steve Wonder","Alice Wonder","Shane Madrid","Bon Jovi","Bon Jovi is an American rock band formed in 1983 in Sayreville, New Jersey. It consists of singer Jon Bon Jovi, keyboardist David Bryan, drummer Tico Torres, guitarist Phil X, and bassist Hugh McDonald.","d_e_3.webp",8,3);

//Found
        this.questionCreator("What was the surname of Del Boy and Rodney in Only Fools and Horses?","Trotter","Melendez","Simpson","Bright","Trotter","The original Only Fools and Horses line-up of (left to right) Grandad (Lennard Pearce), Del Boy (David Jason) and Rodney (Nicholas Lyndhurst) lasted from 1981 to 1984.","d_e_4.webp",8,3);

//Found
        this.questionCreator("In which year was a cinematic Space Odyssey set?","01","02","05","03","01","2001: A Space Odyssey is a 1968 epic science fiction film produced and directed by Stanley Kubrick. The screenplay was written by Kubrick and Arthur C. Clarke, and was inspired by Clarke\'s short story \"The Sentinel\".","d_e_5.webp",8,3);

//Found
        this.questionCreator("Who was the lead singer of T. Rex?","Marc Bolan","Mylie Gilbert","Darren Melendez","Shayla Munoz","Marc Bolan","Marc Bolan ( BOH-lən; born Mark Feld; 30 September 1947 – 16 September 1977) was an English singer-songwriter, musician, guitarist, and poet. He was best known as the lead singer of the glam rock band T. Rex.","d_e_6.webp",8,3);

//Found
        this.questionCreator("Which silent movie actor created “The Little Tramp” character?","Charlie Chaplin","Frankie Burke","Teagan Meadows","Erica Elliott","Charlie Chaplin","Sir Charles Spencer Chaplin (16 April 1889 – 25 December 1977) was an English comic actor, filmmaker, and composer who rose to fame in the era of silent film. Chaplin became a worldwide icon through his screen persona \"the Tramp\" and is considered one of the most important figures in the history of the film industry.","d_e_7.webp",8,3);

//Found
        this.questionCreator("In which film does the line “Toto, I’ve a feeling we’re not in Kansas anymore” appear?","The Wizard of Oz","The Magician","The Race","The Human","The Wizard of Oz","From the 1939 film The Wizard of Oz, in which Dorothy states, \"Toto, I\'ve a feeling we\'re not in Kansas anymore.\"","d_e_8.webp",8,3);

//Found
        this.questionCreator("Who plays Lewis in Morse and its spin-off, Lewis?","Kevin Whately","Angie Osborne","Duncan Simpson","Ann Rivers","Kevin Whately","Kevin Whately (born 6 February 1951) is an English actor. Whately is primarily known for his role as Robert \"Robbie\" Lewis in the crime dramas Inspector Morse and Lewis, his role as Neville \"Nev\" Hope in the British television comedy Auf Wiedersehen, Pet, and his role as Jack Kerruish in the drama series Peak Practice, although he has appeared in numerous other roles.","d_e_9.webp",8,3);

//Found
        this.questionCreator("What is Indiana Jones’s profession?","Archaeologist","anthropologist","Hunter","Egyptologist","Archaeologist","The father of renowned archaeologist Indiana Jones, Henry was born in Scotland on December 12, 1872. He is a professor of medieval literature, having received his degree from the University of Oxford on June 5, 1899 and is, according to his son, \"the (professor) the students hope they don\'t get\".","d_e_10.webp",8,3);

//Found
        this.questionCreator("Which policeman was at the centre of the Pink Panther movies?","Inspector Clouseau","Ann Rivers","Jaylene Bright","Rodrigo Chambers","Inspector Clouseau","Inspector Jacques Clouseau ([ʒak klu.zo]) is a fictional character in Blake Edwards's farcical The Pink Panther series. He is portrayed by Peter Sellers in the original series, and also by Alan Arkin in the 1968 film Inspector Clouseau and, in a cameo, by Roger Moore (credited as Turk Thrust II) in the 1983 film Curse of the Pink Panther.","d_e_11.webp",8,3);

//Found
        this.questionCreator("Which musical instruments are also called Kettledrums?","Timpani","Drums","Guitar","Flute","Timpani","Timpani (; Italian pronunciation: [ˈtimpani]) or kettledrums (also informally called timps) are musical instruments in the percussion family. A type of drum, they consist of a membrane called a head stretched over a large bowl traditionally made of copper.","d_e_12.webp",8,3);

//Found
        this.questionCreator("Some Enchanted Evening, Happy Talk and There is Nothing Like a Dame are songs from which musical?","South Pacific","North Pacific","California","China","South Pacific","\"There Is Nothing Like a Dame\" (for 4 part male voices, 2 tenors and 2 basses) is one of the songs from the musical South Pacific. The song was written by Richard Rodgers with lyrics by Oscar Hammerstein II. It is widely popular in the musical arts, often sung by men\'s choirs.","d_e_13.webp",8,3);

//Found
        this.questionCreator("Which long-running Australian series is set in Summer Bay?","Home and Away","Home and Come","Come Again","Think again","Home and Away","Home and Away (often abbreviated as H&A) is an Australian television soap opera. It was created by Alan Bateman and commenced broadcast on the Seven Network on 17 January 1988.","d_e_14.webp",8,3);

//Found
        this.questionCreator("In which film does a farmer played by Kevin Costner build a baseball diamond in his cornfield?","Field of Dreams","Field of Fear","Field of Boys","Field of Girls","Field of Dreams","Field of Dreams is a 1989 American fantasy-drama sports film directed by Phil Alden Robinson, who also wrote the screenplay, adapting W. P. Kinsella's novel Shoeless Joe. It stars Kevin Costner, Amy Madigan, James Earl Jones, Ray Liotta and Burt Lancaster in his final film role.","d_e_15.webp",8,3);

//Found
        this.questionCreator("Fox Mulder and Dana Scully were the main characters in which television series?","The X Files","The Controversy","The Island","The Escape","The X Files","FBI Special Agent Fox William Mulder is a fictional character in the Fox science fiction-supernatural television series The X-Files, played by David Duchovny. Mulder\'s peers consider his (often correct) theories on extraterrestrial activity as spooky and far-fetched.","d_e_16.webp",8,3);

//Found
        this.questionCreator("Which former schoolteacher from Neath has become a big-selling singer?","Katherine Jenkins","Baron Fitzpatrick","Gaven Perkins","Danny Cook","Katherine Jenkins","Katherine Maria Jenkins (born 29 June 1980) is a Welsh lyric mezzo-soprano singer and songwriter. She is a classical-crossover singer who performs across a spectrum of operatic arias, popular songs, musical theatre and hymns.After winning singing competitions in her youth, Jenkins studied at the Royal Academy of Music, modelled and taught voice.","d_e_17.webp",8,3);

//Found
        this.questionCreator("Who played Maximus Decimus Meridius in a 2000 Ridley Scott film?","Russell Crowe","Nickolas Mcdowell","Bridget Ray","Waylon Fry","Russell Crowe","Russell Ira Crowe (born 7 April 1964) is an actor, film producer and musician. Although a New Zealand citizen, he has lived most of his life in Australia.","d_e_18.webp",8,3);

//Found
        this.questionCreator("“Who loves ya, baby” was a catchphrase of which lollipop-sucking detective?","Lt Theo Kojak","Braxton Sullivan","Marianna Bullock","Asa Evans","Lt Theo Kojak","Kojak\'s catchphrase, \"Who loves ya, baby?\" became part of the American vernacular. Even without conventional Hollywood looks — and a bald head before it was fashionable — both Savalas and Kojak projected a smooth, lady-killer vibe.","d_e_19.webp",8,3);

//Found
        this.questionCreator("How many red balloons did Nena take into the pop charts?","99","50","30","20","99","Nena (born Gabriele Susanne Kerner, 24 March 1960) is a German singer-songwriter, actress, and comedian who rose to international fame in 1983 with the New German Wave song \"99 Luftballons\". In 1984, she re-recorded this song in English as \"99 Red Balloons\". Nena was also the name of the band with whom she released the song. The re-recording of some of her old songs rekindled her career in 2002 and she has sold over 25 million records, making her one of Germany\'s most successful music artists","d_e_20.webp",8,3);

//Found
        this.questionCreator("“Well, here’s another nice mess you’ve gotten me into” is a quote associated with which duo?","Laurel & Hardy","Clay & Robbins","Evie & Kaitlin","Marquis & Jaylen","Laurel & Hardy","Laurel and Hardy made the 1930 film \'Another Fine Mess \' , during their film career neither Mr Laurel nor Mr Hardy never said \" Another Fine Mess \". Just to put the record straight below are the films that contain \" Well , here\'s another nice mess you\'ve gotten me into !\"","d_e_21.webp",8,3);

//Found
        this.questionCreator("Which of the Muppets did a stand-up comedy routine which tended not to be particularly good?","Fozzie Bear","Lazy Bear","Sleepy Bear","Corny Bear","Fozzie Bear","Fozzie Bear is a Muppet character known for his lack of innate and effective comedy skills. Fozzie is an orange bear who often wears a brown pork pie hat and a red and white polka dot necktie.","d_e_22.webp",8,3);

//Found
        this.questionCreator("Which film character said: “My momma always said life was like a box of chocolates… you never know what you’re gonna get”?","Forrest Gump","Spiderman","Avengers","Captain America","Forrest Gump","Forrest Gump is a 1994 American romantic comedy-drama film based on the 1986 novel of the same name by Winston Groom. It was directed by Robert Zemeckis and written by Eric Roth, and stars Tom Hanks, Robin Wright, Gary Sinise, Mykelti Williamson, and Sally Field.","d_e_23.webp",8,3);

//Found
        this.questionCreator("In what sort of music might you hear scat singing?","Jazz","Lovesong","Rock","Ballad","Jazz","Jazz is a music genre that originated in the African-American communities of New Orleans, United States, in the late 19th and early 20th centuries, and developed from roots in blues and ragtime. Jazz is seen by many as \"America's classical music\".","d_e_24.webp",8,3);

//Found
        this.questionCreator("Which television series is set in William McKinley High School, Lima, Ohio?","Glee","Superstars","Calm","Teardrops","Glee","Glee means delight, a form of happiness.","d_e_25.webp",8,3);

//Found
        this.questionCreator("With which band did Pete Doherty come to fame?","The Libertines","The Band","The Caliber","The Sync","The Libertines","The Libertines are an English rock band, formed in London in 1997 by frontmen Carl Barât (vocals/guitar) and Pete Doherty (vocals/guitar). The band, centered on the songwriting partnership of Barât and Doherty, has also included John Hassall (bass) and Gary Powell (drums) for most of its recording career.","d_e_26.webp",8,3);

//Found
        this.questionCreator("Which soap opera started in 1972, and lost a word from its title in 1989?","Emmerdale","Emerald","Evergreen","Forever","Emmerdale","Emmerdale (known as Emmerdale Farm until 1989) is a British soap opera set in Emmerdale (known as Beckindale until 1994), a fictional village in the Yorkshire Dales. Created by Kevin Laffan, Emmerdale Farm was first broadcast on 16 October 1972.","d_e_27.webp",8,3);

//Found
        this.questionCreator("What started with Sergeant in 1958 and ended with Columbus in 1992?","Carry on films","Thinking on films","Dreaming films","Stating films","Carry on films","\"Carry on Columbus\" is a whoopee cushion under the 1492 hoopla. Cheekily berthing its U.K. release, beginning today, between the Salkind and Ridley Scott blockbusters, pic resuscitates the bawdy, vaud-like humor of the original low-budget \"Carry On\" series with nary a nod to changing fashions. Fast playoff could reap rewards on home turf but offshore chances will need careful nurturing.","d_e_28.webp",8,3);

//Found
        this.questionCreator("What is the name of the character played by Michael Douglas in the Wall Street films?","Gordon Gekko","Maddox Flores","Teresa Bender","Blaze Mullen","Gordon Gekko","Gordon Gekko is a fictional character in the 1987 film Wall Street and its 2010 sequel Wall Street: Money Never Sleeps, both directed by Oliver Stone. Gekko was portrayed by actor Michael Douglas, whose performance in the first film won him an Oscar for Best Actor.","d_e_29.webp",8,3);

//Found
        this.questionCreator("From which opera does the March of the Toreadors come?","Carmen","Yasmin","Nelson","Mclean","Carmen","Carmen (French pronunciation: ​[kaʁmɛn]; Spanish: [ˈkaɾmen]) is an opera in four acts by French composer Georges Bizet. The libretto was written by Henri Meilhac and Ludovic Halévy, based on a novella of the same title by Prosper Mérimée.","d_e_30.webp",8,3);

//Found
        this.questionCreator("Who played the Vicar of Dibley?","Dawn French","Bethany Sanford","Abram Bartlett","Angel Miller","Dawn French","Dawn Roma French (born 11 October 1957) is a British actress, writer, comedian and presenter from Holyhead, Wales. She is best known for starring in and writing for the BBC comedy sketch show French and Saunders with comedy partner Jennifer Saunders and for playing the lead role as Geraldine Granger in the BBC sitcom The Vicar of Dibley.","d_e_31.webp",8,3);

//Found
        this.questionCreator("What are the surnames of the title characters in Romeo and Juliet?","Montague & Capulet","Zamora & Prince","Vargas & Hawkins","Hassan & Nunez","Montague & Capulet","Lady Montague is the matriarch of the house of Montague. Romeo Montague, the son of Montague, is the play\'s male protagonist. Abram and Balthasar are servants of the Montague househol","d_e_32.webp",8,3);

//Found
        this.questionCreator("Which London thoroughfare shares its name with a 1978 Gerry Rafferty hit?","Baker Street","Baker Boys","Gangster","Simple Boys","Baker Street","Baker Street is a street in the Marylebone district of the City of Westminster in London. It is named after builder William Baker, who laid out the street in the 18th century.","d_e_33.webp",8,3);

//Found
        this.questionCreator("From which animated film did the song Bright Eyes come?","Watership Down","The Baby","Single Man","Gentle","Watership Down","Watership Down is a survival and adventure novel by English author Richard Adams, published by Rex Collings Ltd of London in 1972. Set in southern England, the story features a small group of rabbits.","d_e_34.webp",8,3);

//Found
        this.questionCreator("For which film did Maurice Jarre compose Lara’s Theme?","Doctor Zhivago","Mr.Silva","Nurse Joy","Doctor Rami","Doctor Zhivago","Doctor Zhivago is the title of a novel by Boris Pasternak and its various adaptations.","d_e_35.webp",8,3);

//Found
        this.questionCreator("What 1976 album gave Jean Michel Jarre his breakthrough?","Oxygène","Alia","Lawrence","Reese","Oxygène","Oxygène (English: Oxygen) is the third studio album by French electronic musician and composer Jean-Michel Jarre and his first album not intended for use as a soundtrack. Oxygène consists of six tracks, numbered simply \"Oxygène Part I\" to \"Part VI\".","d_e_36.webp",8,3);

//Found
        this.questionCreator("First performed in 1918, who composed The Planets suite?","(Gustav) Holst","Wall","Terrell","Harper","(Gustav) Holst","Gustav Theodore Holst (born Gustavus Theodore von Holst; 21 September 1874 – 25 May 1934) was an English composer, arranger and teacher. Best known for his orchestral suite The Planets, he composed a large number of other works across a range of genres, although none achieved comparable success.","d_e_37.webp",8,3);

//Found
        this.questionCreator("Which 1990 comedy horror film had the tag-line “Eight legs, two fangs and an attitude”?","Arachnophobia","Calm","Sit","Silent","Arachnophobia","Arachnophobia is the unreasonable fear of spiders and other arachnids such as scorpions.Treatment is typically by exposure therapy, where the person is presented with pictures of spiders or the spiders themselves.","d_e_38.webp",8,3);

//Found
        this.questionCreator("Of which television series was Jack Bauer the protagonist?","24","32","51","39","24","Jack Bauer is a fictional character and the lead protagonist of the Fox television series 24. His character has worked in various capacities on the show, often as a member of the Counter Terrorist Unit (CTU) based in Los Angeles, and working with the FBI in Washington, D.C. during season 7.","d_e_39.webp",8,3);

//Found
        this.questionCreator("What connects Sandie Shaw, Lulu, Brotherhood of Man, Bucks Fizz and Katrina and the Waves?","They’ve all won","They’ve all lose","Draw","Go home","They’ve all won","They\'ve all woon in Eurovision Song","d_e_40.webp",8,3);

//Found
        this.questionCreator("The series Smallville is about the early years of which superhero?","Superman","Spiderman","Ironman","Jr. man","Superman","Superman is a fictional superhero created by writer Jerry Siegel and artist Joe Shuster. He first appeared in Action Comics #1, a comic book published on April 18, 1938.","d_e_41.webp",8,3);

//Found
        this.questionCreator("What was the follow-up to Jurassic Park called?","The Lost World","The Fairy's World","Dino Island","Geek Islands","The Lost World","From an ambiguous term: This is a redirect from an ambiguous page name to a page or list that disambiguates it. These redirects are pointed to by links that should always be disambiguated.","d_e_42.webp",8,3);

//Found
        this.questionCreator("Played by Hugh Laurie, who is Chief of Diagnostic Medicine at Princeton-Plainsboro Teaching Hospital?","Gregory House MD","Gregor House MD","Gre House MD","Gorgi House MD","Gregory House MD","House (also called House, M.D.) is an American television medical drama that originally ran on the Fox network for eight seasons, from November 16, 2004 to May 21, 2012. The series\' main character is Dr. Gregory House (Hugh Laurie), an unconventional, misanthropic medical genius who, despite his dependence on pain medication, leads a team of diagnosticians at the fictional Princeton–Plainsboro Teaching Hospital (PPTH) in New Jersey.","d_e_43.webp",8,3);

//Found
        this.questionCreator("Tiger Feet was which band’s first number one single, in 1974?","Mud","Rain","Sun","Wind","Mud","Mud is a liquid or semi-liquid mixture of water and any combination of different kinds of soil (loam, silt, and clay). It usually forms after rainfall or near water sources.","d_e_44.webp",8,3);

//Found
        this.questionCreator("The 1975 film Rooster Cogburn, starring John Wayne as the title character, was a sequel to which film released six years earlier?","True Grit","True Colors","Black","White","True Grit","True Grit is a 2010 American Revisionist Western film directed, written, produced, and edited by the Coen brothers and executively produced by Steven Spielberg.","d_e_45.webp",8,3);

//Found
        this.questionCreator("Which DCI lives in Causton?","Tom Barnaby (in Midsomer Murders)","Avery Clayton","Maxim Hoover","Seth Perry","Tom Barnaby (in Midsomer Murders)","Midsomer Murders is a detective drama set in modern-day England. The stories revolve around the efforts of Detective Chief Inspector Tom Barnaby (and later his successor, John Barnaby) to solve numerous murders that take place in the picturesque but deadly villages of the fictional county of Midsomer.","d_e_46.webp",8,3);

//Found
        this.questionCreator("Which trio’s albums include Dookie, Insomniac and Warning?","Green Day","White Day","Black Parade","Snake","Green Day","Green Day is an American rock band formed in 1986 by lead vocalist and guitarist Billie Joe Armstrong and bassist Mike Dirnt. For much of the band's career, they have been a trio with drummer Tré Cool, who replaced John Kiffmeyer in 1990 prior to the recording of the band's second studio album, Kerplunk (1991).","d_e_47.webp",8,3);

//Found
        this.questionCreator("David Dickinson is a television expert on which subject?","Antiques","Origami","Bags","Belts","Antiques","From the plural form: This is a redirect from a plural noun to its singular form. Either {{R from plural}} or {{R to singular}} may be used to tag plural redirects.","d_e_48.webp",8,3);

//Found
        this.questionCreator("From which musical does the song Memory come?","Cats","Snake","Dog","Zebra","Cats","\"Memory\" is a show tune from the 1981 musical Cats. It is sung by the character Grizabella, a one-time glamour cat who is now only a shell of her former self. The song is a nostalgic remembrance of her glorious past and a declaration of her wish to start a new life.","d_e_49.webp",8,3);

//Found
        this.questionCreator("What is the name of the lion cub in Born Free?","Elsa","Zariah","Melissa","Alexis","Elsa","Born Free is a 1966 British drama film starring Virginia McKenna and Bill Travers as Joy and George Adamson, a real-life couple who raised Elsa the Lioness, an orphaned lion cub, to adulthood, and released her into the wilderness of Kenya.","d_e_50.webp",8,3);

//Found
        this.questionCreator("Who composed 12 variations on the tune of Twinkle Twinkle Little Star?","Wolfgang Amadeus Mozart","Snakegang","Lizards","Simple plan","Wolfgang Amadeus Mozart","Wolfgang Amadeus Mozart (27 January 1756 – 5 December 1791), baptised as Johannes Chrysostomus Wolfgangus Theophilus Mozart, was a prolific and influential composer of the classical era.Born in Salzburg, Mozart showed prodigious ability from his earliest childhood.","d_e_51.webp",8,3);

//Found
        this.questionCreator("Who made his screen debut in the 1928 film Steamboat Willie?","Mickey Mouse","Tom & Jerry","Barbie","Nemo","Mickey Mouse","Mickey Mouse is a funny animal cartoon character and the mascot of The Walt Disney Company. He was created by Walt Disney and Ub Iwerks at the Walt Disney Studios in 1928.","d_e_52.webp",8,3);

//Found
        this.questionCreator("How was Jaime Sommers known in a television series of the Seventies and 2000s?","The Bionic Woman","The Killer","Paranoid Woman","Single Mom","The Bionic Woman","The Bionic Woman is an American television science fiction action series starring Lindsay Wagner that aired between 1976 and 1978. The Bionic Woman series features Jaime Sommers, who takes on special high-risk government missions using her superhuman bionic powers.","d_e_53.webp",8,3);

//Found
        this.questionCreator("Who played Buffy the Vampire Slayer?","Sarah Michelle Gellar","Myla Wagner","Diana Farley","Lillian Madden","Sarah Michelle Gellar","Sarah Michelle Gellar (born April 14, 1977) is an American actress, producer, and entrepreneur. After being spotted by an agent at the age of four in New York City, she made her acting debut in the made-for-television film An Invasion of Privacy (1983).","d_e_54.webp",8,3);

//Found
        this.questionCreator("What was Elvis Presley’s middle name?","Aron","Moyer","Small","Scarlet","Aron","Elvis Aaron Presley was an American singer and actor. Regarded as one of the most significant cultural icons of the 20th century, he is often referred to as the \"King of Rock and Roll\" or simply the \"King\"","d_e_55.webp",8,3);

//Found
        this.questionCreator("Which instrument did jazz legend Miles Davis play?","Trumpet","Drums","Guitar","Flute","Trumpet","A trumpet is a brass instrument commonly used in classical and jazz ensembles. The trumpet group contains the instruments with the highest register in the brass family.","d_e_56.webp",8,3);

//Found
        this.questionCreator("Lulu accompanied which band on the 1993 hit Relight My Fire?","Take That","Smake That","Give That","That way","Take That","Take That are an English pop group formed in Manchester in 1990. The group currently consists of Gary Barlow, Howard Donald and Mark Owen.","d_e_57.webp",8,3);

//Found
        this.questionCreator("Who is “Queen of Shops” in a BBC Two series?","Mary Portas","Raegan Frey","Stephany Whitehead","Coby Krause","Mary Portas","Mary Portas (née Newton; born 28 May 1960) is an English retail consultant and broadcaster, known for her retail- and business-related television shows, founding her creative agency Portas and her appointment by David Cameron, the British Prime Minister, to lead a review into the future of Britain's high streets.","d_e_58.webp",8,3);

//Found
        this.questionCreator("Despite the legend, in which film is “Play it again Sam” never actually said?","Casablanca","The coffe","Green Inferno","Secret Land","Casablanca","Casablanca (Arabic: الدار البيضاء‎, translit. ad-dār al-bayḍāʾ; Berber languages: ⴰⵏⴼⴰ, translit. anfa; local informal name: Kaẓa), located in the central-western part of Morocco bordering the Atlantic Ocean, is the largest city in Morocco. It is also the largest city in the Maghreb, as well as one of the largest and most important cities in Africa, both economically and demographically.","d_e_59.webp",8,3);

//Found
        this.questionCreator("“The Lass that Loved a Sailor” is the subtitle of which Gilbert and Sullivan work?","HMS Pinafore","Pinafore","HMS","HMSP","HMS Pinafore","From a modification: This is a redirect from a modification of the target's title or a closely related title. For example, the words may be rearranged, or punctuation may be different.","d_e_60.webp",8,3);

//Found
        this.questionCreator("What is Don Diego de la Vega’s alter ego?","Zorro","Coby","Bridger","Andres","Zorro","Zorro (Spanish for \"Fox\") is a fictional character created in 1919 by American pulp writer Johnston McCulley, and appearing in works set in the Pueblo of Los Angeles during the era of Spanish California (1769–1821). He is typically portrayed as a dashing masked vigilante who defends the commoners and indigenous peoples of California against corrupt and tyrannical officials and other villains.","d_e_61.webp",8,3);

//Found
        this.questionCreator("Here we come/ Walking down the street/ We get the funniest looks from/ Everyone we meet... Which band did this apply to?","The Monkees","Paramose","Boys like Girls","The Used","The Monkees","The Monkees are an American rock and pop band originally active between 1966 and 1971, with reunion albums and tours in the decades that followed. They were formed in Los Angeles in 1965 by Bob Rafelson and Bert Schneider for the American television series The Monkees which aired from 1966 to 1968.","d_e_62.webp",8,3);

//Found
        this.questionCreator("Who lived at 52 Festive Road and regularly visited a fez-wearing shopkeeper?","Mr Benn","Mr Bean","Mr Sam","Mr Earl","Mr Benn","Mr Benn is a character created by David McKee who appears in several children's books, and an animated television series of the same name originally transmitted by the BBC in 1971 and 1972.Whether in a book, or on television, Mr Benn's adventures take on a similar pattern.","d_e_63.webp",8,3);

//Found
        this.questionCreator("What type of music did John Philip Sousa write?","Marches","Used","Second","First","Marches","From the plural form: This is a redirect from a plural noun to its singular form. Either {{R from plural}} or {{R to singular}} may be used to tag plural redirects.","d_e_64.webp",8,3);

//Found
        this.questionCreator("“You had your time/ You had the power/ You’ve yet to have your finest hour” are lines from which Queen song?","Radio GaGa","Killer Queen","Boheiman Rhapsody","Under Pressure","Radio GaGa","\"Radio Ga Ga\" is a 1984 song performed and recorded by the British rock band Queen, written by their drummer Roger Taylor . It was released as a single with \"I Go Crazy\" by Brian May as the B-side (3:42). It was included on the album The Works","d_e_65.webp",8,3);

//Found
        this.questionCreator("Bill and Ben Porter and their offspring were the main characters in which sit-com?","2 Point 4 Children","2 Joints","2 Points","4 Children","2 Point 4 Children","2point4 Children is a BBC television sitcom that was created and written by Andrew Marshall. It follows the lives of the Porters, a seemingly average family whose world is frequently turned upside-down by bad luck and bizarre occurrences.","d_e_66.webp",8,3);

//Found
        this.questionCreator("Which acclaimed singer-songwriter began his career with Fairport Convention and made a","Richard Thompson","Kason Burgess","Brittany Bell","Joyce Ochoa","Richard Thompson","He made his début as a recording artist as a member of Fairport Convention in September 1967. He continues to write and record new material regularly and frequently performs live at venues throughout the world.","d_e_67.webp",8,3);

//Found
        this.questionCreator("Which insects topped the chart for Owl City with their debut single?","Fireflies","Nonstop","Vanilla Ice","The Scripts","Fireflies","From the plural form: This is a redirect from a plural noun to its singular form. Either {{R from plural}} or {{R to singular}} may be used to tag plural redirects.","d_e_68.webp",8,3);

//Found
        this.questionCreator("Which sit-com was set in Walmington-on-Sea?","Dad's Army","Mom's Army","Bother","Father","Dad's Army","Dad's Army is a BBC television sitcom about the British Home Guard during the Second World War. It was written by Jimmy Perry and David Croft, and broadcast on the BBC from 1968 to 1977.","d_e_69.webp",8,3);

//Found
        this.questionCreator("Craig Phillips was the first winner of which television series?","Big Brother","Britain Got Talent","Survivor","Lipsync","Big Brother","Craig Phillips (born 16 October 1971 in Liverpool, Merseyside, England) is an English builder, DIY expert, television personality and presenter. He is best known for winning the first series of the reality television show Big Brother in 2000.","d_e_70.webp",8,3);

//Found
        this.questionCreator("What was the name of the Pet Detective in a couple of 1990s films?","Ace Ventura","Layton Berg","Ireland Pollard","Jagger Conner","Ace Ventura","Ace Ventura is a fictional character created by Vancouver-born screenwriter Jack Bernstein. Ace was played by Jim Carrey in the films Ace Ventura: Pet Detective, released in 1994, and Ace Ventura: When Nature Calls, released in 1995, and was voiced by Michael Daingerfield (credited as Michael Hall) in the Ace Ventura: Pet Detective (TV series).","d_e_71.webp",8,3);

//Found
        this.questionCreator("Which word precedes both Kitten and Rooster in the name of bands who’ve had top 10 singles?","Atomic","Dynamic","Talks","Sweet","Atomic","Atomic is a Norwegian/Swedish jazz band formed in 1999, composed of musicians from the top stratum of the European jazz circuit. Atomic has established itself as one of the most respected \"new\" constellations in jazz. In 2014, original drummer Paal Nilssen-Love was replaced by Hans Hulbækmo.","d_e_72.webp",8,3);

//Found
        this.questionCreator("Who went Singin’ in the Rain in a 1952 film?","Gene Kelly","Esperanza Miles","Aspen Brock","Harley Austin","Gene Kelly","Eugene Curran Kelly (August 23, 1912 – February 2, 1996) was an American dancer, actor of film, stage, and television, singer, film director, producer, and choreographer. He was known for his energetic and athletic dancing style, his good looks, and the likable characters that he played on screen.","d_e_73.webp",8,3);

//Found
        this.questionCreator("Who got soaked portraying a policeman in a comic television adaptation of the song – Singin’ in the Rain?","Eric Morecambe","Alexus Mathews","Hailie Skinner","Antoine Chandler","Eric Morecambe","John Eric Bartholomew, (14 May 1926 – 28 May 1984), known by his stage name Eric Morecambe, was an English comedian who together with Ernie Wise formed the award-winning double act Morecambe and Wise. The partnership lasted from 1941 until Morecambe's death in 1984.","d_e_74.webp",8,3);

//Found
        this.questionCreator("According to the Guinness Book of Records, who is the world’s most successful and popular pianist?","Richard Clayderman","Cassandra Burton","Cason Duke","Aisha Horn","Richard Clayderman","Richard Clayderman (French pronunciation: ​[ʁiʃaʁ klɛidɛʁman]; born Philippe Pagès French pronunciation: ​[filip paʒɛs], 28 December 1953 in Paris) is a French pianist who has released numerous albums including the compositions of Paul de Senneville and Olivier Toussaint, instrumental renditions of popular music, rearrangements of movie soundtracks, ethnic music, and easy-listening arrangements of popular works of classical music.","d_e_75.webp",8,3);

//Found
        this.questionCreator("Against whom did Captain Scarlet fight?","The Mysterons","The Mystery","Mystery Man","The Collaboration","The Mysterons","\"The Mysterons\" (or, rarely, \"Mars – 2068 A.D.\") is the first episode of the 1960s Supermarionation television series Captain Scarlet and the Mysterons. It was first officially broadcast in the UK on ATV Midlands on 29 September 1967, although it had been given an unscheduled test screening on ATV London five months earlier on 29 April 1967.","d_e_76.webp",8,3);

//Found
        this.questionCreator("The birdcatcher Papageno and the Queen of the Night appear in which Mozart opera?","The Magic Flute","The Cape","The Secrets","The Keyboard","The Magic Flute","The Magic Flute (German: Die Zauberflöte pronounced [ˈdiː ˈt͡saʊ̯bɐˌfløːtə]), K. 620, is an opera in two acts by Wolfgang Amadeus Mozart to a German libretto by Emanuel Schikaneder. The work is in the form of a Singspiel, a popular form that included both singing and spoken dialogue.","d_e_77.webp",8,3);

//Found
        this.questionCreator("What was Fatty Arbuckle the first cinematic recipient of, in the 1913 film A Noise from the Deep?","A custard pie","Pie","Pie & Cream","Custard","A custard pie","Cinema\'s first custard-pie-in-the-face was in Sennett\'s silent film comedy A Noise From the Deep (1913), in which comedian Mabel Normand, a farmgirl threw a pie into the kisser of obese farmhand Roscoe “Fatty” Arbuckle.","d_e_78.webp",8,3);

//Found
        this.questionCreator("Monkswell Manor is the location for which famous play?","The Mousetrap","The Detective","The Gun","The Dark","The Mousetrap","The Mousetrap is a murder mystery play by Agatha Christie. The Mousetrap opened in London's West End in 1952, and has been running continuously since then.","d_e_79.webp",8,3);

//Found
        this.questionCreator("Yours Truly Angry Mob was a chart-topping album for which band?","Kaiser Chiefs","Alaina Richard","Andrew Riley","Sanai Mccarthy","Kaiser Chiefs","Kaiser Chiefs are an English indie rock band from Leeds who formed in 2000 as Parva, releasing one studio album, 22, in 2003, before renaming and establishing themselves in their current name that same year. Since their formation the band consists of lead vocalist Ricky Wilson, guitarist Andrew \"Whitey\" White, bassist Simon Rix, keyboardist and occasional drummer Nick \"Peanut\" Baines and since 2013 drummer Vijay Mistry, who replaced founding drummer Nick Hodgson who left the band in late 2012.","d_e_80.webp",8,3);

//Found
        this.questionCreator("Which film depicts the defence of Rorke’s Drift?","Zulu","Defense","Call","Dial","Zulu","The Battle of Rorke\'s Drift, also known as the Defence of Rorke\'s Drift, was a battle in the Anglo-Zulu War. The defence of the mission station of Rorke\'s Drift, under the command of Lieutenants John Chard of the Royal Engineers and Gonville Bromhead, immediately followed the British Army\'s defeat at the Battle of Isandlwana on 22 January 1879, and continued into the following day.","d_e_81.webp",8,3);

//Found
        this.questionCreator("Which 1985 film had the tagline “He’s the only kid ever to get into trouble before he was born”?","Back to the Future","Back to the Past","Back to you","Back to him","Back to the Future","Back to the Future is a 1985 American science fiction film directed by Robert Zemeckis and written by Zemeckis and Bob Gale. It stars Michael J. Fox as teenager Marty McFly, who accidentally travels back in time to 1955, where he meets his future parents and becomes his mother's romantic interest.","d_e_82.webp",8,3);

//Found
        this.questionCreator("Audrey fforbes-Hamilton and Richard de Vere were the protagonists of which sit-com?","To the Manor Born","The Only One","I Love You","Secret On","To the Manor Born","To the Manor Born is a BBC television sitcom that first aired on BBC1 from 1979 to 1981. A special edition appeared in 2007.","d_e_83.webp",8,3);

//Found
        this.questionCreator("Whose 2010 debut album is called Lights?","Ellie Goulding","Lucille Zhang","Ali Rivas","Xavier Cobb","Ellie Goulding","Elena Jane Goulding ( GOHL-ding; born 30 December 1986) is an English singer and songwriter. Her career began when she met record producers Starsmith and Frankmusik, and she was later spotted by Jamie Lillywhite, who later became her manager and A&R. After signing to Polydor Records in July 2009, Goulding released her debut extended play, An Introduction to Ellie Goulding later that year.In 2010, she became the second artist to top the BBC's annual Sound of...","d_e_84.webp",8,3);

//Found
        this.questionCreator("What was the surname of siblings John Boy, Jason, Mary Ellen, Erin, Jim-Bob, Ben and Elizabeth, residents in Virginia, USA?","Walton","Zackery","Dickson","Carson","Walton","The Walton family is an American family whose collective fortune makes them the richest family in the United States and among the richest in the world. The majority of their wealth derives from the heritage of Bud and Sam Walton, who were the co-founders of the world\'s largest retailer, Walmart.","d_e_85.webp",8,3);

//Found
        this.questionCreator("Dick Grayson is the real name of which superhero?","Robin","Batman","Spiderman","Ironman","Robin","Richard John Grayson is a fictional superhero appearing in American comic books published by DC Comics, commonly in association with Batman. Created by writer Bill Finger and artist Bob Kane, he first appeared in Detective Comics #38 in April 1940 as the original incarnation of Robin.","d_e_86.webp",8,3);

//Found
        this.questionCreator("Which band comprises vocalist Tom Chaplin, pianist Tim Rice-Oxley and drummer Richard Hughes?","Keane","Barajas","Higgins","Leon","Keane","Keane (band), an English band The Keane Brothers, American duo, a.k.a.","d_e_87.webp",8,3);

//Found
        this.questionCreator("What did Joseph possess in a Tim Rice/Andrew Lloyd Webber musical?","An amazing technicolour dreamcoat","An amazing necklace","An amazing ring","An Amazing shoes","An amazing technicolour dreamcoat","Several of his songs have been widely recorded and were hits outside of their parent musicals, notably \"The Music of the Night\" and \"All I Ask of You\" from The Phantom of the Opera, \"I Don\'t Know How to Love Him\" from Jesus Christ Superstar, \"Don\'t Cry for Me, Argentina\" from Evita, \"Any Dream Will Do\" from Joseph and the Amazing Technicolor Dreamcoat and \"Memory\" from Cats.","d_e_88.webp",8,3);

//Found
        this.questionCreator("In which James Bond film was there the following exchange: “You expect me to talk?” “No, Mr Bond, I expect you to die!”?","Goldfinger","The Boys","Secret Dust","Carjackers","Goldfinger","The line from 1964 film Goldfinger is in response to Sean Connery as Bond saying: \'You expect me to talk?\' It comes as Bond appears to be about to meet his maker at the hands of a giant laser and is uttered by his evil nemesis Auric Goldfinger played by Gert Fröbe.","d_e_89.webp",8,3);

//Found
        this.questionCreator("Which rock star was Jennifer Lawrence rumored to have been dating this year?","Chris Martin","Adam Lavine","Dave Grohl","Fabrizio Moretti","Chris Martin","Christopher Anthony John Martin is an English singer, songwriter, musician, record producer, and philanthropist. He is the lead singer and co-founder of the British rock band Coldplay.","d_e_90.webp",8,3);

//Found
        this.questionCreator("Do you know which magical Italian city George Clooney and his new wife Amal decided to get married in?","Venice","Milan","Rome","Paris","Venice","Venice, of course! The two lovebirds invited their closest friends and family members to the beautiful city for a week to celebrate their nuptials.","d_e_91.webp",8,3);

//Found
        this.questionCreator("What\'s Natalie Portman\'s actual last name?","Hershlag","Douglas","Portman","Hershing","Hershlag","Natalie\'s real last name is Hershlag. She changed it to Portman, which is her grandmother\'s maiden name, when she started to act.","d_e_92.webp",8,3);

//Found
        this.questionCreator("Which accomplished actor DIDN\'T change her first name?","Shailene Woodley","Julianne Moore","Meryl Streep","Tina Fey","Shailene Woodley","Rising star Shailene Woodley is the only star on this list to have kept her first name. Julianne\'s real name is Julie Anne Smith, Meryl\'s is Mary, and Tina\'s is Elizabeth StamatinaEmil.","d_e_93.webp",8,3);

//Found
        this.questionCreator("Do you know which Indie front man Zooey Deschanel used to be married to?","Ben Gibbard","Justin Vernon","Mat Kearney","Luke Pritchard","Ben Gibbard","Ben Gibbard is an American singer, songwriter and guitarist. He is best known as the lead vocalist and guitarist of the indie rock band Death Cab for Cutie, with which he has recorded eight studio albums, and as one half of the electronica act the Postal","d_e_94.webp",8,3);

//Found
        this.questionCreator("Which actor was country singer Lyle Lovett once married to?","Julia Roberts","Catherine Zeta-Jones","Melanie Griffith","Meg Ryan","Julia Roberts","Julia Roberts is an Academy Award-winning actress and one of Hollywood\'s top stars, known for such films as \'Steel Magnolias,\' \'Pretty Woman\' and \'Erin Brockovich.","d_e_95.webp",8,3);

//Found
        this.questionCreator("What about Taylor Swift\'s song \"We Are Never Ever Getting Back Together\"?","Jake Gyllenhaal","Joe Jonas","Conor Kennedy","Harry Styles","Jake Gyllenhaal","Jacob Benjamin Gyllenhaal was born in Los Angeles, California, to producer/screenwriter Naomi Foner (née Achs) and director Stephen Gyllenhaal. He is the brother of actress Maggie Gyllenhaal, who played his sister in Donnie Darko (2001).","d_e_96.webp",8,3);

//Found
        this.questionCreator("Do you know which funnyman officiated Adam Levine\'s wedding?","Jonah Hill","Michael Cera","Channing Tatum","Seth Rogen","Jonah Hill","Turns out Jonah Hill and Adam Levine have been so close since high school that the Maroon 5 singer asked Hill to officiate his 2014 wedding to Victoria\'s Secret model Behati Prinsloo.","d_e_97.webp",8,3);

//Found
        this.questionCreator("Do you know which of these actors was born outside of the United States?","Amy Adams","Lucy Liu","Brad Pitt","Zoe SalPitt","Amy Adams","Amy Lou Adams is an American actress. Known for both her comedic and dramatic performances, Adams is, as of 2017, among the highest-paid actresses in the world.","d_e_98.webp",8,3);

//Found
        this.questionCreator("In the 1994 movie The Shawshank Redemption one of the lead male roles was played by?","Tim Robbins","Jack Robinson","Elijah Wood","Josh Hartnett","Tim Robbins","Timothy Francis Robbins is an American actor, screenwriter, director, producer, and musician. He is well known for his portrayal of Andy Dufresne in the prison drama film The Shawshank Redemption.","d_e_99.webp",8,3);

//Found
        this.questionCreator("In the 1966 classic western The Good, the Bad and the Ugly, who played the key actor?","Clint Eastwood","Tom Felton","Chuck Norris J","James Caan","Clint Eastwood","Clinton Eastwood Jr. is an American actor, filmmaker, musician, and political figure.","d_e_100.webp",8,3);

//Found
        this.questionCreator("In the 1995 movie Se7en the lead female actress was?","Gwyneth Paltrow","Selena Gomez","Demi Lovato","Jane Fonda","Gwyneth Paltrow","Gwyneth Kate Paltrow is an American actress, singer, and food writer.","d_e_101.webp",8,3);

//Found
        this.questionCreator("In the 1960 movie Psycho who played Norman Bates?","Anthony Perkins","Sam Loomis","Simon Oakland","Frank Albertson","Anthony Perkins","Anthony Perkins was an American actor and singer. He was nominated for the Academy Award for Best Supporting Actor for his second film, Friendly Persuasion.","d_e_102.webp",8,3);

//Found
        this.questionCreator("In the 1994 movie Leon who played the role of Leon?","Jean Reno","Danny Aiello","Peter Appel","Gary Oldman","Jean Reno","Juan Moreno y Herrera-Jiménez, known as Jean Reno, is a French actor of Spanish descent.","d_e_103.webp",8,3);

//Found
        this.questionCreator("The Oscar winning short documentary film ‘Smile Pinki’ (2008) was directed by which director?","Megan Mylan","Michael Curtiz","Charlie Chaplin","Alfred Hitchcock","Megan Mylan","Megan Mylan is an American documentary film director, known for her films Lost Boys of Sudan and the 2008 Academy Award-winning Smile Pinki.","d_e_104.webp",8,3);

//Found
        this.questionCreator("Who is known as the \'father of Indian Cinema\'","Dadasaheb Phalke","Dadasaheb Torne","Satyajit Ray","V. Shantaram","Dadasaheb Phalke","Dhundiraj Govind Phalke, popularly known as Dadasaheb Phalke, was an Indian producer-director-screenwriter, known as the Father of Indian cinema.","d_e_105.webp",8,3);

//Found
        this.questionCreator("Who played the role of Emperor Akbar in the movie \'Mughal e Azam\'","Prithviraj Kapoor","Raj Kapoor","Dilip Kumar","Murad","Prithviraj Kapoor","Prithviraj Kapoor was an actor and director, known for Mughal-E-Azam (1960), Maharathi Karna (1944) and Bidyapati (1937). He was married to Rama Kapoor.","d_e_106.webp",8,3);

//Found
        this.questionCreator("First Indian to win an Oscar award.","Bhanu Athaiya","Resul Pookutty","Amitabh Bachchan","AR Rahman","Bhanu Athaiya","Bhanu Athaiya is an Indian costume designer, having worked in over 100 films, since the 1950s, with noted filmmakers like Guru Dutt, Yash Chopra, Raj Kapoor, Ashutosh Gowariker, and international directors like Conrad Rooks and Richard Attenborough.","d_e_107.webp",8,3);

//Found
        this.questionCreator("Which supermodel Naomi wrote a novel called Swan?","Campbell","Watts","Rowling","Austen","Campbell","Naomi Elaine Campbell is an English model, actress, and singer.","d_e_108.webp",8,3);

//Found
        this.questionCreator("This \"Male Gigolo\" and \"Animal\" is also in \"50 First Dates\"... and he happens to have Filipino blood, can you name him?","Rob Schneider","Nick Hawk","Brace Land","Vin Armani","Rob Schneider","Robert Michael Schneider is an American actor, comedian, screenwriter, and director.","d_e_109.webp",8,3);

//Found
        this.questionCreator("Who didn’t announce she was pregnant in 2017?","Kim Kardashian","Binky Felstead","Heidi Montag","Chrissy Teigen","Kim Kardashian","Kimberly Noel Kardashian West is an American reality television personality, entrepreneur and socialite","d_e_110.webp",8,3);

//Found
        this.questionCreator("Which reality star didn’t make music in 2017?","Andy Jordan","Steph Davis","Megan McKenna","Chris Hughes and Kem Cetinay","Andy Jordan","Andrew Mark “Andy” Jordan is an English actor and singer-songwriter.","d_e_111.webp",8,3);

//Found
        this.questionCreator("What film won Best Picture at the Oscars?","Moonlight","La La Land","Call Me By Your Name","Dunkirk","Moonlight","Moonlight is a 2016 American coming-of-age drama film written and directed by Barry Jenkins, based on Tarell Alvin McCraney\'s unpublished semi-autobiographical play In Moonlight Black Boys Look Blue.","d_e_112.webp",8,3);

//Found
        this.questionCreator("Which US TV show did Meghan Markle announce she was leaving?","Suits","The Sinner","Dynasty","Grey's Anatomy","Suits","Suits is an American drama series which Meghan Markle plays Rachel Zane.","d_e_113.webp",8,3);

//Found
        this.questionCreator("How much was Kylie Cosmetics estimated at in 2017?","Four Hundred Twenty Million","Three Hundred Million","One Billion","Seven Hunder Sixty Million","Four Hundred Twenty Million","Kylie Cosmetics... It has made Kylie a fortune—and the company is only two years old. Kylie\'s recent Forbes profile revealed that she\'s sold more than $630 million worth of makeup, \"including an estimated $330 million in 2017.\"","d_e_114.webp",8,3);

//Found
        this.questionCreator("Who does Forbes magazine list as the highest-paid actress of 2016?","Jennifer Lawrence","Julia Roberts","Gwyneth Paltrow","Jennifer Aniston","Jennifer Lawrence","Jennifer Shrader Lawrence is an American actress. Her films have grossed over $5.7 billion worldwide, and she was the highest-paid actress in the world in 2015 and 2016.","d_e_115.webp",8,3);

//Found
        this.questionCreator("Who directed “Miss Peregrine’s Home for Peculiar Children”?","Tim Burton","Wes Anderson","Martin Scorsese","Quentin Tarantino","Tim Burton","Tim Burton was born on August 25, 1958, in Burbank, California. After majoring in animation at the California Institute of Arts, he worked as a Disney animator for less than a year before striking out on his own.","d_e_116.webp",8,3);

//Found
        this.questionCreator("Who stars as former First Lady Jacqueline Kennedy in the 2016 release “Jackie”?","Natalie Portman","Sandra Bullock","Renee Zellweger","Nicole Kidman","Natalie Portman","Natalie Portman is an actress, film producer and director with dual Israeli and American citizenship. She is the recipient of several accolades, including an Academy Award and two Golden Globe Awards.","d_e_117.webp",8,3);

//Found
        this.questionCreator("Which comic hosted the 2016 Academy Awards?","Chris Rock","Steve Martin","Jon Stewart","Billy Crystal","Chris Rock","Christopher Julius Rock III is an American comedian, actor, writer, producer, and director.","d_e_118.webp",8,3);

//Found
        this.questionCreator("Which Kardashian broke up with their partner following the birth of their child?","Rob","Kim","Kourtney","Khloe","Rob","Robert Arthur Kardashian is an American television personality and businessman. He is known for appearing on Keeping Up with the Kardashians, a reality television series that centers upon his family, as well as its spin-offs.","d_e_119.webp",8,3);

//Found
        this.questionCreator("Who killed Glen on \"The Walking Dead\" in one of the worst-kept secrets in television?","Negan","Carol","The Governor","Rick Grimes","Negan","Negan is a fictional character in the comic book series The Walking Dead and in the television series of the same name.","d_e_120.webp",8,3);

//Found
        this.questionCreator("What Olympic gymnast took center stage at the Rio Olympics in 2016?","Simone Biles","Lebron James","Mary Lou Retton","Tonya Harding","Simone Biles","Simone Arianne Biles is an American artistic gymnast. Biles is the 2016 Olympic individual all-around, vault and floor gold medalist, and balance-beam bronze medalist.","d_e_121.webp",8,3);

//Found
        this.questionCreator("On what day was Ellen DeGeneres born?","January 26, 1958","March 05, 1960","April 20, 1956","December 25, 1961","January 26, 1958","Ellen Lee DeGeneres is an American comedian, television host, actress, writer, producer, and LGBT activist.","d_e_122.webp",8,3);


    }


    private void addGeographyEasy()
    {
        //checked

//Found
        this.questionCreator("Monaco is the capital of ?","Monaco","France","Luxembourg","Greece","Monaco","Monaco is a city-state so the capital of Monaco is Monaco. Monte Carlo is a region in Monaco (the most populated).","e_g_1.webp",4,1);

//Found
        this.questionCreator("Kingston is the capital of ?","Jamaica","Guatemala","The Bahamas","Ecuador","Jamaica","Kingston is the capital of the island of Jamaica, lying on its southeast coast. In the city center, the Bob Marley Museum is housed in the reggae singer’s former home.","e_g_2.webp",4,1);

//Found
        this.questionCreator("Quito is the capital of ?","Ecuador","Chile","Argentina","Brazil","Ecuador","Quito, Ecuador\'s capital, sits high in the Andean foothills at an altitude of 2,850m. Constructed on the foundations of an ancient Incan city, it’s known for its well-preserved colonial center, rich with 16th- and 17th-century churches and other structures blending European, Moorish and indigenous styles.","e_g_3.webp",4,1);

//Found
        this.questionCreator("Manila is the capital of ?","Philippines","Vietnam","Bangladesh","Thailand","Philippines","Manila, the capital of the Philippines, is a densely populated bayside city on the island of Luzon, which mixes Spanish colonial architecture with modern skyscrapers.","e_g_4.webp",4,1);

//Found
        this.questionCreator("Dhaka is the capital of ?","Bangladesh","India","Pakistan","Philippines","Bangladesh","Dhaka is the capital city of Bangladesh, in southern Asia. Set beside the Buriganga River, it’s at the center of national government, trade and culture. The 17th-century old city was the Mughal capital of Bengal, and many palaces and mosques remain.","e_g_5.webp",4,1);

//Found
        this.questionCreator("Lisbon is the capital of ?","Portugal","Germany","Morocco","Spain","Portugal","Lisbon is Portugal’s hilly, coastal capital city. From imposing São Jorge Castle, the view encompasses the old city’s pastel-colored buildings, Tagus Estuary and Ponte 25 de Abril suspension bridge.","e_g_6.webp",4,1);

//Found
        this.questionCreator("Seoul is the capital of ?","South Korea","North Korea","China","Japan","South Korea","Officially the Seoul Special City, is the capital and largest metropolis of South Korea. With surrounding Incheon metropolis and Gyeonggi province, Seoul forms the heart of the Seoul Capital Area, home to roughly half of the country\'s population.","e_g_7.webp",4,1);

//Found
        this.questionCreator("Sofia is the capital of ?","Bulgaria","Poland","Lativa","Russia","Bulgaria","Sofia is the capital of the Balkan nation of Bulgaria. It’s in the west of the country, below Vitosha Mountain.","e_g_8.webp",4,1);

//Found
        this.questionCreator("Washington DC is the capital city of ?","USA","Canada","Mexico","Philippines","USA","Washington, DC, the U.S. capital, is a compact city on the Potomac River, bordering the states of Maryland and Virginia. It’s defined by imposing neoclassical monuments and buildings – including the iconic ones that house the federal government’s 3 branches: the Capitol, White House and Supreme Court.","e_g_9.webp",4,1);

//Found
        this.questionCreator("Stockholm is the capital of ?","Sweden","Denmark","Finland","Iceland","Sweden","Stockholm, the capital of Sweden, encompasses 14 islands and more than 50 bridges on an extensive Baltic Sea archipelago. The cobblestone streets and ochre-colored buildings of Gamla Stan (the old town) are home to the 13th-century Storkyrkan Cathedral, the Kungliga Slottet Royal Palace and the Nobel Museum, which focuses on the Nobel Prize. Ferries and sightseeing boats shuttle passengers between the islands.","e_g_10.webp",4,1);

//Found
        this.questionCreator("Belgrade is the capital of ?","Serbia","Luxembourg","Macedonia","Slovakia","Serbia","Belgrade is the capital of the southeast European country of Serbia. Its most significant landmark is the Beogradska Tvrđava, an imposing fortress at the confluence of the Danube and the Sava rivers","e_g_11.webp",4,1);

//Found
        this.questionCreator("Vatican City is the capital of ?","Vatican City","Laos","North Korea","Thailand","Vatican City","The Vatican City is an ecclesiastical or sacerdotal-monarchicalstate (a type of theocracy) ruled by the pope who is, religiously speaking, the bishop of Rome and head of the Catholic Church.","e_g_12.webp",4,1);

//Found
        this.questionCreator("Baghdad is the capital of ?","Iraq","Iran","Saudi Arabia","Bahrain","Iraq","Baghdad is the capital of Iraq. The population of Baghdad, as of 2016, is approximately 8,765,000, making it the largest city in Iraq, the second largest city in the Arab world, and the second largest city in Western Asia.","e_g_13.webp",4,1);

//Found
        this.questionCreator("Tokyo is the capital of ?","Japan","China","Laos","Jordan","Japan","Tokyo, Japan’s busy capital, mixes the ultramodern and the traditional, from neon-lit skyscrapers to historic temples. The opulent Meiji Shinto Shrine is known for its towering gate and surrounding woods. The Imperial Palace sits amid large public gardens.","e_g_14.webp",4,1);

//Found
        this.questionCreator("Berlin is the capital of ?","Germany","Austria","France","Italy","Germany","Berlin, Germany’s capital, dates to the 13th century. Reminders of the city\'s turbulent 20th-century history include its Holocaust memorial and the Berlin Wall\'s graffitied remains.","e_g_15.webp",4,1);

//Found
        this.questionCreator("Helsinki is the capital of ?","Finland","Iceland","Sweden","Norway","Finland","Helsinki, Finland’s southern capital, sits on a peninsula in the Gulf of Finland. Its central avenue, Mannerheimintie, is flanked by institutions including the National Museum, tracing Finnish history from the Stone Age to the present.","e_g_16.webp",4,1);

//Found
        this.questionCreator("Ankara is the capital of ?","Turkey","Egypt","Ukraine","Lithuania","Turkey","Ankara is the capital city of the country of Turkey. It is in the center of Anatolia. Ankara is the second largest city after Istanbul.","e_g_17.webp",4,1);

//Found
        this.questionCreator("Warsaw is the capital of ?","Poland","Russia","Ukraine","Macedonia","Poland","Warsaw is the sprawling capital of Poland. Its widely varied architecture reflects the city\'s long, turbulent history, from Gothic churches and neoclassical palaces to Soviet-era blocks and modern skyscrapers.","e_g_18.webp",4,1);

//Found
        this.questionCreator("Vilnius is the capital of ?","Lithuania","Poland","Ukraine","Georgia","Lithuania","Vilnius, Lithuania’s capital, is known for its baroque architecture, seen especially in its medieval Old Town. But the buildings lining this district’s partially cobblestoned streets reflect diverse styles and eras, from the neoclassical Vilnius Cathedral to Gothic St. Anne\'s Church.","e_g_19.webp",4,1);

//Found
        this.questionCreator("Ottawa is the capital of ?","Canada","Iceland","Denmark","Mexico","Canada","Ottawa is Canada’s capital, in the east of southern Ontario, near the city of Montréal and the U.S. border. Sitting on the Ottawa River, it has at its centre Parliament Hill, with grand Victorian architecture and museums such as the National Gallery of Canada, with noted collections of indigenous and other Canadian art.","e_g_20.webp",4,1);

//Found
        this.questionCreator("Kigali is the capital of ?","Rwanda","Samoa","Swaziland","Sierra Leone","Rwanda","Kigali is the capital city of Rwanda, roughly in the center of the country. It sprawls across numerous hills, ridges and valleys, and has a vibrant restaurant and nightlife scene.","e_g_21.webp",4,1);

//Found
        this.questionCreator("San Salvador is the capital of ?","El Salvador","Ecuador","Boliva","Dominican Republic","El Salvador","San Salvador (\"Holy Savior\") is the capital and the most populous city of El Salvador and its eponymous department. It is the country\'s political, cultural, educational and financial center.","e_g_22.webp",4,1);

//Found
        this.questionCreator("Rome is the capital of ?","Italy","Belgium","France","Wales","Italy","Rome. Rome, Italian Roma, historic city and capital of Roma provincia (province), of Lazio regione (region), and of the country of Italy. Rome is located in the central portion of the Italian peninsula, on the Tiber River about 15 miles (24 km) inland from the Tyrrhenian Sea.","e_g_23.webp",4,1);

//Found
        this.questionCreator("Beijing is the capital of ?","China","Cambodia","Thailand","Japan","China","Beijing, China’s massive capital, has history stretching back 3 millennia. Yet it’s known as much for its modern architecture as its ancient sites such as the grand Forbidden City complex, the imperial palace during the Ming and Qing dynasties.","e_g_24.webp",4,1);

//Found
        this.questionCreator("Athens is the capital of ?","Greece","Turkey","France","Luxembourg","Greece","Athens is the capital and largest city of Greece. The city dominates the Attica region and is one of the world\'s oldest cities, with its recorded history spanning around 3,400 years.","e_g_25.webp",4,1);

//Found
        this.questionCreator("Moscow is the capital of ?","Russia","Romania","Poland","Liechtenstein","Russia","Moscow, on the Moskva River in western Russia, is the nation’s cosmopolitan capital. In its historic core is the Kremlin, a complex that’s home to the president and tsarist treasures in the Armoury.","e_g_26.webp",4,1);

//Found
        this.questionCreator("Canberra is the capital of ?","Australia","Dominica","Austria","Fiji","Australia","Canberra is the capital city of Australia. With a population of 403,468, it is Australia\'s largest inland city and the eighth-largest city overall. The city is located at the northern end of the Australian Capital Territory, 280 km south-west of Sydney, and 660 km north-east of Melbourne.","e_g_27.webp",4,1);

//Found
        this.questionCreator("Prague is the capital of ?","Czech Republic","Greece","Turkey","Estonia","Czech Republic","Prague, capital city of the Czech Republic, is bisected by the Vltava River. Nicknamed “the City of a Hundred Spires,” it\'s known for its Old Town Square, the heart of its historic core, with colorful baroque buildings, Gothic churches and the medieval Astronomical Clock, which gives an animated hourly show.","e_g_28.webp",4,1);

//Found
        this.questionCreator("Lima is the capital of ?","Peru","Chile","Brazil","Dominican Republic","Peru","Lima, the capital of Peru, lies on the country\'s arid Pacific coast. Though its colonial center is preserved, it\'s a bustling metropolis and one of South America’s largest cities. It\'s home to the Museo Larco collection of pre-Columbian art and the Museo de la Nación, tracing the history of Peru’s ancient civilizations. The Plaza de Armas and the 16th-century cathedral are the heart of old Lima Centro.","e_g_29.webp",4,1);

//Found
        this.questionCreator("Lome is the capital of ?","Togo","Senegal","Tonga","Chad","Togo","Lomé is the capital of Togo, in West Africa. It\'s known for its palm-lined Atlantic coastline. The central Independence Monument is in a landscaped traffic circle. The nearby Congressional Palace houses the National Museum, exhibiting West African jewelry, masks, musical instruments and pottery. T","e_g_30.webp",4,1);

//Found
        this.questionCreator("Cairo is the capital of ?","Egypt","Serbia","Jordan","Chad","Egypt","Cairo. Cairo, Arabic Al-Qāhirah (“The Victorious”), city, capital of Egypt, and one of the largest cities in Africa.","e_g_31.webp",4,1);

//Found
        this.questionCreator("London is the capital of ?","England","Scotland","Wales","Ireland","England","London is the capital city of England and the United Kingdom. Standing on the River Thames in the southeast of Great Britain, London has been a major settlement for two millennia. Londinium was founded by the Romans.","e_g_32.webp",4,1);

//Found
        this.questionCreator("Brussels is the capital of ?","Belgium","Sweden","Georgia","Denmark","Belgium","Brussels , officially the Brussels-Capital Region (French: Région de Bruxelles-Capitale, Dutch: Brussels Hoofdstedelijk Gewest), is a region of Belgium comprising 19 municipalities, including the City of Brussels, which is the capital of Belgium.","e_g_33.webp",4,1);

//Found
        this.questionCreator("Kuala Lumpur is the capital of ?","Malaysia","Malawi","Mauritius","Moldova","Malaysia","Kuala Lumpur is the capital of Malaysia. Its modern skyline is dominated by the 451m-tall Petronas Twin Towers, a pair of glass-and-steel-clad skyscrapers with Islamic motifs.","e_g_34.webp",4,1);

//Found
        this.questionCreator("Minsk is the capital of ?","Belarus","Georgia","Bulgaria","Poland","Belarus","Minsk, capital of Belarus, is a modern city dominated by monumental Stalinist architecture. Many of its museums, theaters and other cultural attractions line Independence Avenue (Praspyekt Nyezalyezhnastsi), a wide, 15km-long thoroughfare leading to vast Independence Square.","e_g_35.webp",4,1);

//Found
        this.questionCreator("Hanoi is the capital of ?","Northern Vietnam","Laos","India","Thailand","Northern Vietnam","Hanoi, also spelled Ha Noi, city, capital of Vietnam. The city is situated in northern Vietnam on the western bank of the Red River, about 85 miles (140 km) inland from the South China Sea.","e_g_36.webp",4,1);

//Found
        this.questionCreator("Pristina is the capital of ?","Kosovo","Lithuania","Georgia","Bosnia","Kosovo","Pristina or Priština, is the capital and largest city of Kosovo. It is the administrative center of the homonymous municipality and district. The city has a majority Albanian population, alongside other smaller communities.","e_g_37.webp",4,1);

//Found
        this.questionCreator("Tbilisi is the capital of ?","Georgia","Liberia","Kyrgyzstan","Macedonia","Georgia","Tbilisi is the capital of the country of Georgia. Its cobblestoned old town reflects a long, complicated history, with periods under Persian and Russian rule. Its diverse architecture encompasses Eastern Orthodox churches, ornate art nouveau buildings and Soviet Modernist structures.","e_g_38.webp",4,1);

//Found
        this.questionCreator("Taipei is the capital of ?","Taiwan","Tajikistan","Tunisia","Samoa","Taiwan","Taipei, the capital of Taiwan, is a modern metropolis with Japanese colonial lanes, busy shopping streets and contemporary buildings. The skyline is crowned by the 509m-tall, bamboo-shaped Taipei 101 skyscraper, with upscale shops at the base and a rapid elevator to an observatory near the top.","e_g_39.webp",4,1);

//Found
        this.questionCreator("Oslo is the capital of ?","Norway","Sweden","Denmark","Belgium","Norway","Oslo, the capital of Norway, sits on the country’s southern coast at the head of the Oslofjord. It’s known for its green spaces and museums. Many of these are on the Bygdøy Peninsula, including the waterside Norwegian Maritime Museum and the Viking Ship Museum, with Viking ships from the 9th century.","e_g_40.webp",4,1);

//Found
        this.questionCreator("Copenhagen is the capital of ?","Denmark","Finland","Iceland","Sweden","Denmark","Copenhagen, Denmark’s capital, sits on the coastal islands of Zealand and Amager. It’s linked to Malmo in southern Sweden by the Öresund Bridge. Indre By, the city\'s historic center, contains Frederiksstaden, an 18th-century rococo district, home to the royal family’s Amalienborg Palace.","e_g_41.webp",4,1);

//Found
        this.questionCreator("Kampala is the capital of ?","Uganda","Namibia","Nigeria","Tonga","Uganda","Kampala is Uganda\'s national and commercial capital bordering Lake Victoria, Africa\'s largest lake. Hills covered with red-tile villas and trees surround an urban centre of contemporary skyscrapers. In this downtown area, the Uganda Museum explores the country\'s tribal heritage through an extensive collection of artefacts.","e_g_42.webp",4,1);

//Found
        this.questionCreator("Georgetown is the capital of ?","Guyana","Haiti","South Africa","New Zealand","Guyana","Georgetown is Guyana’s capital, on South America’s North Atlantic coast. The city is culturally connected to the English-speaking Caribbean region and home to British colonial architecture, including the tall, Gothic-style St. George\'s Anglican Cathedral.","e_g_43.webp",4,1);

//Found
        this.questionCreator("Madrid is the capital of ?","Spain","Uruguary","Portugal","Italy","Spain","Madrid is the capital of Spain and the largest municipality in both the Community of Madrid and Spain as a whole. The city has almost 3.2 million inhabitants and a metropolitan area population of approximately 6.5 million.","e_g_44.webp",4,1);

//Found
        this.questionCreator("Bern is the capital of ?","Switzerland","Austria","Germany","Uruguary","Switzerland","Bern, the capital city of Switzerland, is built around a crook in the Aare River. It traces its origins back to the 12th century, with medieval architecture preserved in the Altstadt (Old Town).","e_g_45.webp",4,1);

//Found
        this.questionCreator("Paris is the capital of ?","France","Germany","Portugal","Spain","France","Paris, city and capital of France, situated in the north-central part of the country. People were living on the site of the present-day city, located along the Seine River some 233 miles (375 km) upstream from the river\'s mouth on the English Channel (La Manche), by about 7600 bce.","e_g_46.webp",4,1);

//Found
        this.questionCreator("Wellington is the capital of ?","New Zealand","Fiji","Australia","Portugal","New Zealand","Wellington, the capital of New Zealand, sits near the North Island’s southernmost point on the Cook Strait. A compact city, it encompasses a waterfront promenade, sandy beaches, a working harbour and colourful timber houses on surrounding hills.","e_g_47.webp",4,1);

//Found
        this.questionCreator("Islamabad is the capital of ?","Pakistan","India","Morocco","Afghanistan","Pakistan","Islamabad is the capital city of Pakistan, and is federally administered as part of the Islamabad Capital Territory. Built as a planned city in the 1960s to replace Karachi as Pakistan\'s capital, Islamabad is noted for its high standards of living, safety, and abundant greenery.","e_g_48.webp",4,1);

//Found
        this.questionCreator("Vienna is the capital of ?","Austria","Switzerland","Germany","Italy","Austria","Vienna, Austria’s capital, lies in the country’s east on the Danube River. Its artistic and intellectual legacy was shaped by residents including Mozart, Beethoven and Sigmund Freud. The city is also known for its Imperial palaces, including Schönbrunn, the Habsburgs’ summer residence.","e_g_49.webp",4,1);

//Found
        this.questionCreator("Amman is the capital of ?","Jordan","Lebanon","Kenya","Egypt","Jordan","Amman, the capital of Jordan, is a modern city with numerous ancient ruins. Atop Jabal al-Qala’a hill, the historic Citadel includes the pillars of the Roman Temple of Hercules and the 8th-century Umayyad Palace complex, known for its grand dome.","e_g_50.webp",4,1);

//Found
        this.questionCreator("Which ocean lies between Australia and Africa ?","Indian","Atlantic","Southern","Arctic","Indian","The Indian Ocean lies between Africa and Australia.","e_g_51.webp",4,1);

//Found
        this.questionCreator("Which ocean lies between North America and Europe ?","Atlantic","Arctic","Indian","Southern","Atlantic","The delineation between Asia and Europe in the Arctic Ocean. the delineation between Europe and North America in the Atlantic Ocean. the delineation between North and South America in the Caribbean Sea. the delineation of Asia from North America in the North Pacific Ocean.","e_g_52.webp",4,1);

//Found
        this.questionCreator("What continent is Ireland part of ?","Europe","Asia","Australia","North America","Europe","Ireland is an island country in the North Atlantic Ocean, and is part of the continent of Europe.","e_g_53.webp",4,1);

//Found
        this.questionCreator("Is Greece located on the continent of Europe or Asia?","Europe","Asia","Pacific","Arctic","Europe","Greece is located in the southern region of the continent of Europe. Of the countries that surround the Mediterranean Sea, Greece has the longest coastline.","e_g_54.webp",4,1);

//Found
        this.questionCreator("Which ocean lies between North America and Asia ?","Pacific","Indian","Altantic","Northern","Pacific","The delineation of Asia from North America in the North Pacific Ocean.","e_g_55.webp",4,1);

//Found
        this.questionCreator("What is the hottest continent on Earth?","Africa","Arctic","Indian","Southern","Africa","Asia includes 50 countries, and it is the most populated continent, the 60% of the total population of the Earth live here. Africa comprises 54 countries. It is the hottest continent and home of the world\'s largest desert, the Sahara, occupying the 25% of the total area of Africa.","e_g_56.webp",4,1);

//Found
        this.questionCreator("What is the capital of Bulgaria?","Sofia","Indian","Southern","Atlantic","Sofia","Sofia is the capital of the Balkan nation of Bulgaria. It’s in the west of the country, below Vitosha Mountain","e_g_57.webp",4,1);

//Found
        this.questionCreator("What is the capital of Australia ?","Canberra","Indian","Altantic","Northern","Canberra","Canberra is the capital city of Australia. With a population of 403,468, it is Australia\'s largest inland city and the eighth-largest city overall. The city is located at the northern end of the Australian Capital Territory, 280 km south-west of Sydney, and 660 km north-east of Melbourne.","e_g_58.webp",4,1);

//Found
        this.questionCreator("What is the capital of Germany ?","Berlin","Atlantic","Southern","Arctic","Berlin","Berlin, Germany’s capital, dates to the 13th century. Reminders of the city\'s turbulent 20th-century history include its Holocaust memorial and the Berlin Wall\'s graffitied remains.","e_g_59.webp",4,1);

//Found
        this.questionCreator("Which country is Washington DC the capital of ?","America","Russia","Ukraine","Macedonia","America","The Americas comprise the totality of the continents of North and South America. Together, they make up most of the land in Earth\'s western hemisphere and comprise the New World. Along with their associated islands, they cover 8% of Earth\'s total surface area and 28.4% of its land area.","e_g_60.webp",4,1);

//Found
        this.questionCreator("Which country is Madrid the capital of ?","Spain","India","Morocco","Afghanistan","Spain","As the capital city of Spain, seat of government, and residence of the Spanish monarch, Madrid is also the political, economic and cultural centre of the country.","e_g_61.webp",4,1);

//Found
        this.questionCreator("What is the capital of Cuba ?","Havana","Rwanda","Samoa","Swaziland","Havana","Havana is Cuba’s capital city. Spanish colonial architecture in its 16th-century Old Havana core includes the Castillo de la Real Fuerza, a fort and maritime museum. The National Capitol Building is an iconic 1920s landmark. Also in Old Havana is the baroque Catedral de San Cristóbal and Plaza Vieja, whose buildings reflect the city’s vibrant architectural mix.","e_g_62.webp",4,1);

//Found
        this.questionCreator("What is the capital of New Zealand ?","Wellington","Belarus","Georgia","Bulgaria","Wellington","Wellington, the capital of New Zealand, sits near the North Island’s southernmost point on the Cook Strait. A compact city, it encompasses a waterfront promenade, sandy beaches, a working harbour and colourful timber houses on surrounding hills.","e_g_63.webp",4,1);

//Found
        this.questionCreator("What is the capital of Thailand ?","Bangkok","Southern","Arctic","Atlantic","Bangkok","Bangkok, Thailand’s capital, is a large city known for ornate shrines and vibrant street life. The boat-filled Chao Phraya River feeds its network of canals, flowing past the Rattanakosin royal district, home to opulent Grand Palace and its sacred Wat Phra Kaew Temple.","e_g_64.webp",4,1);

//Found
        this.questionCreator("Which country is Cairo the capital of ?","Egypt","Iceland","Denmark","Mexico","Egypt","Satellite View is showing Cairo, the national capital of Egypt. Cairo is the largest city in the Arab World and the center of the largest metropolitan area in whole Africa.","e_g_65.webp",4,1);

//Found
        this.questionCreator("What is the capital of England ?","London","Ukraine","Lithuania","Turkey","London","London, the capital of England and the United Kingdom, is a 21st-century city with history stretching back to Roman times. At its centre stand the imposing Houses of Parliament, the iconic ‘Big Ben’ clock tower and Westminster Abbey, site of British monarch coronations.","e_g_66.webp",4,1);

//Found
        this.questionCreator("What is the national capital city of Canada ?","Ottawa","Denmark","Mexico","Canada","Ottawa","Ottawa is Canada’s capital, in the east of southern Ontario, near the city of Montréal and the U.S. border. Sitting on the Ottawa River, it has at its centre Parliament Hill, with grand Victorian architecture and museums such as the National Gallery of Canada, with noted collections of indigenous and other Canadian art.","e_g_67.webp",4,1);

//Found
        this.questionCreator("What is the capital of Mexico ?","Mexico City","Egypt","Ukraine","Lithuania","Mexico City","Mexico City is the densely populated, high-altitude capital of Mexico. It\'s known for its Templo Mayor (a 13th-century Aztec temple), the baroque Catedral Metropolitana de México of the Spanish conquistadors and the Palacio Nacional, which houses historic murals by Diego Rivera.","e_g_68.webp",4,1);

//Found
        this.questionCreator("Which country is Tokyo the capital of ?","Japan","China","Cambodia","Thailand","Japan","Tokyo is the capital city of Japan, a title the city has held since 1868 after it was renamed from Edo. Historically, the city became the country\'s capital after the then Emperor established his seat of authority in the city.","e_g_69.webp",4,1);

//Found
        this.questionCreator("Which country is Lisbon the capital of ?","Portugal","Greece","Turkey","France","Portugal","Satellite view is showing Lisbon, the national capital of Portugal. The city lies on the north bank of the Tejo (Tagus) Estuary, more or less in the western center of the country on the Atlantic coast. It is the westernmost city in continental Europe.","e_g_70.webp",4,1);

//Found
        this.questionCreator("What is the capital of Morocco ?","Rabat","Sweden","Norway","Finland","Rabat","Morocco\'s capital city is Rabat; its largest city is its main port, Casablanca. Other cities recording a population over 500,000 in the 2014 Moroccan census are Fes, Marrakesh, Meknes, Salé and Tangier.","e_g_71.webp",4,1);

//Found
        this.questionCreator("Which European country has a capital city called Bucharest?","Romania","Saturn","Pluto","Earth","Romania","Romania is a South-Eastern country of Europe, with a population of almost 23,000,000 and an area of 237,500 square kilometers. The capital of Romania is Bucharest, one of the most beautiful cities in the country, situated in the South part of Romania","e_g_72.webp",4,1);
//Not Found
        this.questionCreator("What is the name of the active volcano in Sicily ?","Mount Etna","Luxembourg","Greece","Monaco","Mount Etna","Mount Etna, Europe\'s Most Active Volcano. Mount Etna, on the Italian island of Sicily, is not only the highest active volcano in Europe at 10,810 feet (3,295 meters), but is one of the most active in the world.","e_g_73.webp",4,1);

//Found
        this.questionCreator("One of the longest rivers in the world ?","The Amazon","The Argarta","The Samuel","The Necro","The Amazon","The Amazon River in South America is the largest river by discharge volume of water in the world, and by some definitions it is the longest.","e_g_74.webp",4,1);

//Found
        this.questionCreator("In which country would you find the pyramids ?","Egypt","Germany","Portugal","Spain","Egypt","While pyramids are associated with Egypt, the nation of Sudan has 220 extant pyramids, the most numerous in the world. The Great Pyramid of Giza is one of the Seven Wonders of the Ancient World. It is the only one to survive into modern times.","e_g_75.webp",4,1);

//Found
        this.questionCreator("In which State of America would you find Las Vegas ?","Nevada","France","Jordan","Japan","Nevada","Nevada is a western U.S. state defined by its great expanses of desert, and by the 24-hour casinos and entertainment for which its largest city, Las Vegas, is famed.","e_g_76.webp",4,1);

//Found
        this.questionCreator("What does the Richter scale measure ?","Earthquake","Tsunami","Thunder","Lighting","Earthquake","Richter scale (ML), quantitative measure of an earthquake\'s magnitude (size), devised in 1935 by American seismologists Charles F. Richter and Beno Gutenberg. The earthquake\'s magnitude is determined using the logarithm of the amplitude (height) of the largest seismic wave calibrated to a scale by a seismograph.","e_g_77.webp",4,1);

//Found
        this.questionCreator("Which country is the second biggest in the world ?","Canada","Laos","Jordan","Japan","Canada","Canada is a big country, so it’s no surprise that we like our tourist attractions sized extra large. Here’s a travelogue of photo-friendly big things that are dotted across our home and native land.","e_g_78.webp",4,1);

//Found
        this.questionCreator("Which continent is Mount Kilimanjaro located on ?","Africa","Austria","France","Italy","Africa","Mount Kilimanjaro is the highest mountain on the African continent. Its summit is 19,340 feet (5,895 meters) above sea level. It is located in Tanzania.","e_g_79.webp",4,1);

//Found
        this.questionCreator("What is the name of the river that runs through the Grand Canyon ?","Colorado","Poland","Ukraine","Georgia","Colorado","Starting in the central Rocky Mountains of Colorado, the river flows generally southwest across the Colorado Plateau and through the Grand Canyon before reaching Lake Mead on the Arizona–Nevada border, where it turns south toward the international border.","e_g_80.webp",4,1);

//Found
        this.questionCreator("What is the largest country in Scandinavia ?","Sweden","Texas","Norway","Finland","Sweden","Sweden is one of the largest countries in Europe in area but only has some 10 million inhabitants. The landscapes and nature do, however, vary across the Nordic countries. Denmark is a flat lowland like the Netherlands and Northern Germany. Iceland is both volcanic and arctic.","e_g_81.webp",4,1);

//Found
        this.questionCreator("Which city is nicknamed The Big Apple ?","New York","Texas","Seattle","Canada","New York","\"Big Apple\" is a nickname for New York City. It was first popularized in the 1920s by John J. Fitz Gerald, a sports writer for the New York Morning Telegraph. Its popularity since the 1970s is due in part to a promotional campaign by the New York tourist authorities.","e_g_82.webp",4,1);

//Found
        this.questionCreator("What is the capital city of Spain?","Madrid","Barcelona","Seville","Helsinki","Madrid","Spain is a sovereign state mostly located on the Iberian Peninsula in Europe.","e_g_83.webp",4,1);

//Found
        this.questionCreator("Taj Mahal is located in which country?","India","Pakistan","Indonesia","Singapore","India","India is a vast South Asian country with diverse terrain – from Himalayan peaks to Indian Ocean coastline – and history reaching back 5 millennia.","e_g_84.webp",4,1);

//Found
        this.questionCreator("What country has a capital of Seoul?","Korea, South","Kosovo","Kenya","Korea, North","Korea, South","South Korea, an East Asian nation on the southern half of the Korean Peninsula, shares one of the world’s most heavily militarized borders with North Korea.","e_g_85.webp",4,1);

//Found
        this.questionCreator("What is the continent of Israel?","Asia","America","Africa","Oceania","Asia","Asia is Earth\'s largest and most populous continent, located primarily in the Eastern and Northern Hemispheres.","e_g_86.webp",4,1);

//Found
        this.questionCreator("The largest continent is?","Asia","North America","Africa","Australia","Asia","Asia spans 17,139,445 square miles – 29.1 percent of Earth\'s total land mass — and has a population of 4.1 billion people.","e_g_87.webp",4,1);

//Found
        this.questionCreator("The smallest continent is?","Australia","North America","Oceania","Asia","Australia","The continent of Australia, sometimes known in technical contexts by the names Sahul, Australinea or Meganesia to distinguish it from the country of Australia, consists of the land masses which sit on Australia\'s continental shelf. Situated in the geographical region of Oceania, it is the smallest of the seven traditional continents in the English conception.","e_g_88.webp",4,1);

//Found
        this.questionCreator("In which continent will you find Brazil?","South America","Africa","Europe","North America","South America","Officially the Federative Republic of Brazil is the largest country in both South America and Latin America.","e_g_89.webp",4,1);

//Found
        this.questionCreator("In which continent you find Laos?","Asia","Africa","Europe","North America","Asia","Laos is a landlocked country in the heart of the Indochinese peninsula of Mainland Southeast Asia, bordered by Myanmar (Burma) and China to the northwest, Vietnam to the east, Cambodia to the southwest, and Thailand to the west and southwest.","e_g_90.webp",4,1);

//Found
        this.questionCreator("On which continent will you find Egypt?","Africa","Europe","South America","North America","Africa","Africa is the world\'s second largest and second most-populous continent. At about 30.3 million km² including adjacent islands, it covers 6% of Earth\'s total surface area and 20% of its land area. With 1.2 billion people as of 2016, it accounts for about 16% of the world\'s human population.","e_g_91.webp",4,1);

//Found
        this.questionCreator("In the following what is the official name of Mexico?","United Mexican States","Kingdom of Mexico","Greater Republic of Central America","United Latin States","United Mexican States","The name of México has several hypotheses that entail the origin, history, and use of the name México, which dates back to 14th century Mesoamerica.","e_g_92.webp",4,1);

//Found
        this.questionCreator("In the following what is the official name of North Korea?","Democratic People Republic of Korea","Republic of Korea","Communist People’s Republic of Korea","Supreme Republic of Korea","Democratic People Republic of Korea","North Korea, officially the Democratic People\'s Republic of Korea, is a country in East Asia constituting the northern part of the Korean Peninsula.","e_g_93.webp",4,1);

//Found
        this.questionCreator("Which of the following cities is not located in Russia?","Yokohama","Saint Petersburg","Samara","Sochi","Yokohama","Yokohama - is the second largest city in Japan by population, after Tokyo, and the most populous municipality of Japan.","e_g_94.webp",4,1);

//Found
        this.questionCreator("In geography, what word describes a sea with numerous islands or a group of many islands?","Archipelago","Riverine","Moraine","Presidio","Archipelago","Archipelago - sometimes called an island group or island chain, is a chain, cluster or collection of islands, or sometimes a sea containing a small number of scattered islands.","e_g_95.webp",4,1);

//Found
        this.questionCreator("One of England\'s greatest Elizabethan houses, Burghley House is located in which English city?","Peterborough","Plymouth","Oxford","Nottingham","Peterborough","Peterborough - Construction on the Burghley House was finished in 1587. Peterborough is a cathedral city in Cambridgeshire, England, with a population of 183,31 in 2011.","e_g_96.webp",4,1);

//Found
        this.questionCreator("Which California city stands on the hilly peninsula immediately south of the Golden Gate Bridge?","San Francisco","Sacramento","San Diego","San Jose","San Francisco","San Francisco - officially the City and County of San Francisco, is the cultural, commercial, and financial center of Northern California.","e_g_97.webp",4,1);

//Found
        this.questionCreator("In which continent can you find the active volcano \"Mount Erebus\"?","Antartica","South America","Africa","Asia","Antartica","Antarctica - The volcano has been active for 1.3 million years ago.","e_g_98.webp",4,1);

//Found
        this.questionCreator("Located in the United States, what kind of geographic feature are the \"Florida Keys\"?","Islands","Mountains","Volcano Chain","Flatlands","Islands","Islands - The islands lie along the Florida Straits.","e_g_99.webp",4,1);


    }

    private void addGeographyModerate()
    {
        //checked

//Found
        this.questionCreator("What is the most safe country in the world?","Iceland","China","African","U.S.A","Iceland","According to the Global Peace Index Iceland is the safest country in the world, for the 5th time in a row.","m_g_1.webp",4,2);

//Found
        this.questionCreator("In what year was the Erie Canal opened?","1825","1925","1850","1875","1825","West of Troy, 83 canal locks were built to accommodate the 500-foot rise in elevation. After more than two years of digging, the 425-mile Erie Canal was opened on October 26, 1825, by Governor Clinton.","m_g_2.webp",4,2);

//Found
        this.questionCreator("What is the most famous ghost town?","Pripyat, Ukraine","Manila","L.A","China Town","Pripyat, Ukraine","At 1:23 a.m. on April 26, 1986, a catastrophic meltdown took place inside reactor number four at the Soviet nuclear power plant at Chernobyl. The explosion that followed sent flames and radioactive material soaring into the skies over Pripyat, a nearby city built to house the plant’s scientists and workers.","m_g_3.webp",4,2);

//Found
        this.questionCreator("What is the highest point in New York?","Mount Marcy","Mount Doom","Mount Olympos","Mount Everest","Mount Marcy","Mount Marcy (Mohawk: Tewawe\'éstha) is the highest point in New York State, with an elevation of 5,343 feet (1,629 m). and an isolation of 130 mile. It is located in the Town of Keene in Essex County. The mountain is in the heart of the Adirondack High Peaks Region of the High Peaks Wilderness Area.","m_g_4.webp",4,2);

//Found
        this.questionCreator("(Blank) Diving is a popular sport in which one floats over Niagara Falls.","Barrel","Kayak","Jet","Tube","Barrel","On this day in 1901, a 63-year-old schoolteacher named Annie Edson Taylor becomes the first person to take the plunge over Niagara Falls in a barrel.","m_g_5.webp",4,2);

//Found
        this.questionCreator("What is the maximum depth of Lake Erie","210 feet","200 yards","500 feet","3 kilometers","210 feet","Deepest point of Lake Erie is 210 feet (64 metres) deep.","m_g_6.webp",4,2);

//Found
        this.questionCreator("What is the biggest ghost town?","Bodie, California, USA","New York City","Paris","Rochester","Bodie, California, USA","The town of Bodie is probably one of the best-preserved ghost towns in California. It lies about 75 miles south of Lake Tahoe and was originally a mining town founded for its discovery of gold in 1859. Back in 1865, the town was home to more than 10,000 gold-seekers.","m_g_7.webp",4,2);

//Found
        this.questionCreator("How many Dutch Guilders did Peter Minuit purchase New York for?","60","65","55","75","60","He sailed to North America and arrived in the colony on May 4, 1626. Minuit is credited with purchasing the island of Manhattan from the Native Americans in exchange for traded goods valued at 60 guilders.","m_g_8.webp",4,2);

//Found
        this.questionCreator("What is the hottest recorded temperature in New York?","222 . 8 Fahrenheit","203 Celsius","181 Kelvin","201 Fahrenheit","222 . 8 Fahrenheit","The record high temperature for July in New York is 106 degrees which occurred on July 9, 1936. The 106 degrees is also the record hottest temperature ever recorded in Central Park in New York City.","m_g_9.webp",4,2);

//Found
        this.questionCreator("One of the best country in the whole world?","Switzerland","Philippines","America","Africa","Switzerland","Well known for its history of neutrality, stable Switzerland also ranks second in the \"open for business,\" \"green living,\" and citizenship\" lists. Seems like they\'re doing something right.","m_g_10.webp",4,2);

//Found
        this.questionCreator("What is the capitol of New York","Albany","New York City","Paris","Rochester","Albany","Albany is the capital city of New York State. Downtown’s huge Empire State Plaza has reflecting pools, an art-filled underground shopping concourse and The Egg, a striking performing arts center. The plaza is bookended by the 1800s New York State Capitol and the New York State Museum, focusing on natural and cultural history.","m_g_11.webp",4,2);

//Found
        this.questionCreator("What is the average depth of Lake Ontario","283 feet","438 meters","682 feet","810 yards","283 feet","Exceeds Lake Erie in volume (393 cubic miles, 1639 km). Is the 14th largest lake in the world and has a shoreline 712 miles (1146 km) long. Has an elevation of 246 feet (75 m) above sea level. The average depth of Lake Ontario is 283 feet (86 m), with a maximum depth of 802 feet (244 m).","m_g_12.webp",4,2);

//Found
        this.questionCreator("When was the Statue of Liberty designated?","1886","1492","2003","1901","1886","President Grover Cleveland accepted the statue for the United States on Oct. 28, 1886. The Statue of Liberty was designated a National Monument in 1924 and a World Heritage Site in 1984.","m_g_13.webp",4,2);

//Found
        this.questionCreator("Top one richest countries in the world.","Qatar","U.S.A","Philippines","Ice Land","Qatar","Qatar ($124,930) The small Middle Eastern country often ranks as one of the richest countries in the world per capita.","m_g_14.webp",4,2);

//Found
        this.questionCreator("One of the poorest country in the world.","Central African Republic","America","Philippines","China","Central African Republic","The Central African Republic remains one of the poorest countries in Africa. Tens of thousands of vulnerable children are without access to basic services. Many of them have been orphaned by AIDS or civil war. SOS Children\'s Villages supports and protects the children of this country in two different locations.","m_g_15.webp",4,2);

//Found
        this.questionCreator("What is the poorest continent in the world?","Africa","South America","North America","North Korea","Africa","Eighteen of the poorest countries by GDP per capita are in Africa. That is not surprising given the famine and war that have racked the continent for the better part of the last four decades. Contributing to these hardships is that many of these countries were recently territories or protectorates of European nations.","m_g_16.webp",4,2);

//Found
        this.questionCreator("How long is the Hudson River?","315 Miles","315 Parsecs","315 lightyears","315 Kilometers","315 Miles","The Hudson River is a 315-mile river that flows from north to south primarily through eastern New York in the United States.","m_g_17.webp",4,2);

//Found
        this.questionCreator("What is the state flower for New York?","Rose","Gardenia","Tulip","Daisy","Rose","The rose has long been used as a symbol. \"Rose\" means pink or red in a variety of languages.","m_g_18.webp",4,2);

//Found
        this.questionCreator("What is the Philippines known for?","Boracay","Mt. Mayon","Enchanted River","Manila Bay","Boracay","With thousands of islands, there\'s a patch of sand for everyone. Boracay Most widely known and visited, White Beach is ground zero for the Philippines\' beachfront party scene.","m_g_19.webp",4,2);

//Found
        this.questionCreator("What is the official language of the Philippines?","Tagalog","Mandarin Chinese","Japanese","Arabic","Tagalog","Most Filipinos speak both English and Tagalog, Cebuano if they\'re from a place south of Luzon. There are many other regional languages that Filipinos might know in addition to English and Filipino depending where they are from, such as Ilocano, Chavacano, Waray, Hiligaynon, and 150+ more.","m_g_20.webp",4,2);

//Found
        this.questionCreator("What is the function of Ellis Island?","Immigration gateway","A theme park","A nuclear bunker","Batman's lair","Immigration gateway","The main function of Ellis Island changed from that of an immigrant processing station, to a center of the assembly, detention, and deportation of aliens who had entered the U.S. illegally or had violated the terms of admittance. The buildings at Ellis Island began to fall into disuse and disrepair.","m_g_21.webp",4,2);

//Found
        this.questionCreator("What Mountain range starts in Georgia and ends in Maine","Appalachian Mountains","Himalayas","Adirondack range","Rocky Mountains","Appalachian Mountains","The Eastern Continental Divide follows the Appalachian Mountains from Pennsylvania to Georgia. The Appalachian Trail is a 2,175-mile (3,500 km) hiking trail that runs all the way from Mount Katahdin in Maine to Springer Mountain in Georgia, passing over or past a large part of the Appalachian system.","m_g_22.webp",4,2);

//Found
        this.questionCreator("On what famous street is the New York Stock Extchange?","Wall Street","Constitution Avenue","Main Street","Wall Road","Wall Street","The New York Stock Exchange (NYSE, nicknamed \"The Big Board\") is an American stock exchange located at 11 Wall Street, Lower Manhattan, New York City, New York. It is by far the world\'s largest stock exchange by market capitalization of its listed companies at US$21.3 trillion as of June 2017.","m_g_23.webp",4,2);

//Found
        this.questionCreator("What is the island that the immigrants passed through?","Ellis Island","Sicily","Hawaii","Easter Island","Ellis Island","From 1892 to 1954, over twelve million immigrants entered the United States through the portal of Ellis Island, a small island in New York Harbor. Ellis Island is located in the upper bay just off the New Jersey coast, within the shadow of the Statue of Liberty.","m_g_24.webp",4,2);

//Found
        this.questionCreator("What is the biggest river in Mexico?","Rio Grande","Colorado river","Nazas Aguanaval rivers","Amazon River","Rio Grande","The Rio Grande is one of the principal rivers in the southwest United States and northern Mexico. Has a length of 3,034 km and depth of 18 m (59 ft)","m_g_25.webp",4,2);

//Found
        this.questionCreator("What is the wettest month in the Philippines?","August","January","Febuary","May","August","Manila, Philippines: Annual Weather Averages. May is the hottest month in Manila with an average temperature of 85°F (30°C) and the coldest is January at 78°F (26°C) with the most daily sunshine hours at 9 in April. The wettest month is August with an average of 140mm of rain.","m_g_26.webp",4,2);

//Found
        this.questionCreator("What is the oldest City in Colombia?","Santa Marta","Bogota","Medellin","Rio Grande","Santa Marta","Santa Marta. Santa Marta, city, northern Colombia. It is situated on a small bay of the Caribbean Sea, 40 miles (64 km) east-northeast of the mouth of the Magdalena River, to which it is connected by swampy channels and lakes. Founded in 1525, it is the oldest city in Colombia.","m_g_27.webp",4,2);

//Found
        this.questionCreator("How do you say hello in Mexico?","Hola","Ayo","Ey","Waz","Hola","Hola is a general greeting used to express “hi” to people, however they also use different ones depending on the time of the day. On the telephone you can use alo, hola, bueno, diga. These are words which are used as \'hello\'. Adios is used by Mexicans when they are saying goodbye.","m_g_28.webp",4,2);

//Found
        this.questionCreator("What is the smallest country in the world?","Vatican City","Monaco","Iiechtenstein","Sawatch Range","Vatican City","The world\'s smallest country is the Vatican, also known as the Holy See. With only 0.44 squared kilometers, Vatican City is the world\'s smallest country by land area.","m_g_29.webp",4,2);

//Found
        this.questionCreator("Total dialects of Mexico.","350","100","150","400","350","The total of languages amounts to around 68 and 350 dialects, with a large majority of the population fluent in Spanish while some Indigenous Mexicans are monolingual in indigenous languages. Today, Mexicans predominantly speak Spanish.","m_g_30.webp",4,2);

//Found
        this.questionCreator("What is the biggest museum in Europe?","Louvre","Prado","Van Gogh","Norway","Louvre","The Louvre, located in Paris, France, is the largest art museum in the world with an area size of 782,910 square feet. This museum is considered a historic monument in Paris and is part of the Louvre Palace, which was built in the 12th century. This building first served as a fortress before becoming a royal residence in 1546.","m_g_31.webp",4,2);

//Found
        this.questionCreator("What is Mexico known for?","La Cucaracha","LLa Cucaracha","LaLa Cucaracha","Cucaracha","La Cucaracha","\"La Cucaracha\" is a well-known Mariachi staple. Two of Mexico\'s most famous artists are Frida Kahlo and Diego Rivera. Their paintings include vibrant colors and depictions of life in Mexico. Rivera was a pioneer of Muralism, a movement that used expansive wall art to educate the people.","m_g_32.webp",4,2);

//Found
        this.questionCreator("Which is the coldest country in the world ?","Antarctica","Canada","Finland","Britain","Antarctica","Undoubtedly, Antarctica is the coldest country of the world where the lowest temperatures touches even minus 90 degrees Celsius. Also known as snowy desert, the place gets no or very little sunshine. It is an uphill task for the people in Russia to survive under the harshest winter observed in the world.","m_g_33.webp",4,2);

//Found
        this.questionCreator("What is the smallest city in the USA?","Buford","San Francisco","Los Angeles","Chicago","Buford","Buford, Wyoming: Smallest Town In America. Boasting on the town limits sign its population of 1, Buford is a single house, gas station, and post office. It\'s also the highest spot for a town along the entire span of I-80.","m_g_34.webp",4,2);

//Found
        this.questionCreator("What is the smallest state in America?","Rhode Island","North Island","Big Island","Tiny Island","Rhode Island","The United States is made up of 50 individual states that vary greatly in size. When talking about land area, Rhode Island ranks as the smallest. Yet, when we discuss population, Wyoming — the 10th largest state in area — comes in with the smallest population.","m_g_35.webp",4,2);

//Found
        this.questionCreator("What state has the largest black population?","Detroit, Michigan","California , USA","San Francisco","Africa","Detroit, Michigan","This city has the highest percentage of Black or African-Americans with the total of 713,777 population.","m_g_36.webp",4,2);

//Found
        this.questionCreator("What is the HAPPIEST country in the world?","Finland","Germany","Argentina","Norway","Finland","As per the 2018 Happiness Index, Finland is the happiest country in the world. Norway, Denmark, Iceland and Switzerland hold the next top positions.","m_g_37.webp",4,2);

//Found
        this.questionCreator("How many Hispanic people are in America?","52 million","32 million","45 million","12 million","52 million","The demographics of Hispanic and Latino Americans depict a population that is the second-largest ethnic group in the United States, 52 million people or 16.7% of the national population, of them, 47 Million are American citizens.","m_g_38.webp",4,2);

//Found
        this.questionCreator("How many countries are there in Africa?","54","32","13","25","54","54 countries This includes all internationally recognized territories and states on the continent. The variation is a result of disputed territories and inconsistencies around the inclusion of island nations off the coast of Africa.","m_g_39.webp",4,2);

//Found
        this.questionCreator("Which is the taller mountain in the world?","Mount Everest","Cho Oyu","Philippines","Kilimanjaro-Everest","Mount Everest","Mount Everest is called the world\'s highest mountain because it has the \"highest elevation above sea level.\" We could also say that it has the \"highest altitude.\" The peak of Mount Everest is 8,850 meters (29,035 feet) above sea level. No other mountain on Earth has a higher altitude.","m_g_40.webp",4,2);

//Found
        this.questionCreator("Which is the most echnological city in South America ?","San Francisco","Brazilia","Santiago de Chile","Cusco","San Francisco","San Francisco, California. If every city claims to be the “Silicon Valley” of its particular home country, you can guarantee Silicon Valley is the gold standard for tech. Since 2thinknow defines the region by its largest neighboring city, San Francisco takes the top spot.","m_g_41.webp",4,2);

//Found
        this.questionCreator("How many tribes are in Africa?","3,000","2,000","4,500","1,500","3,000","There are an estimated 3,000 African tribes spread out through the 54 countries of Africa. The exact number of tribes isn\'t known, but there are probably some undocumented ones in Central Africa.","m_g_42.webp",4,2);

//Found
        this.questionCreator("Which tribe is the most intelligent in Nigeria?","Igbo","Alok","Ikol","Ektar","Igbo","This community is one of the biggest in Nigeria. There are many farmers and traders among Igbo people. It is believed that this tribe is very educated. By the way, many representatives of this tribe love to gain new knowledge, self-develop and implement creative business ideas into life.","m_g_43.webp",4,2);

//Found
        this.questionCreator("Which tribe in Nigeria is the richest?","Igbo","Hausa","Ektar","Ikol","Igbo","Which tribe in Nigeria is the richest? Igbo people are considered to be the richest people in the whole Nigeria. Some of them are the most popular people in Nigeria.","m_g_44.webp",4,2);

//Found
        this.questionCreator("What is the largest and most important river in Colombia?","Magdalena river","Amazon river","Cauca river","Black river","Magdalena river","The major ones are: the Magdalena River, Cauca River, Caquetá, Putumayo, Guaviare, Meta and the Atrato Rivers. THE MAGDALENA RIVER is the most important commercial waterway in Colombia, as well as a source of electric power and natural beauty.","m_g_45.webp",4,2);

//Found
        this.questionCreator("What is the second most populous continent?","Africa","Asia","Europe","America","Africa","Estimated population in Africa 1,216,130,000.","m_g_46.webp",4,2);

//Found
        this.questionCreator("What is the nickname of the Philippines?","Pearl of the Orient","Palau","Pearl","Promise Land","Pearl of the Orient","Nicknames of Manila. Manila, the capital city and largest metropolitan area in the Philippines, is known by a number of nicknames, aliases, sobriquets and slogans, both officially and unofficially, now and in the past. The city is most popularly referred to as the Pearl of the Orient.","m_g_47.webp",4,2);

//Found
        this.questionCreator("What is the capital of Estonia?","Tallinn","Helsinki","Riga","St. Petersburg","Tallinn","Tallinn, Estonia’s capital on the Baltic Sea, is the country’s cultural hub. It retains its walled, cobblestoned Old Town, home to cafes and shops, as well as Kiek in de Kök, a 15th-century defensive tower.","m_g_48.webp",4,2);

//Found
        this.questionCreator("What is the most populous city on Earth (metro)?","Tokyo","Jakarta","New York","Beijing","Tokyo","The largest city in the world by population is Tokyo, Japan, with a population of 38,001,000. Tokyo is followed by Delhi, India (population 25,703,168) and Shanghai, China (population 23,740,778).","m_g_49.webp",4,2);

//Found
        this.questionCreator("What is the first religion in the Philippines?","Islam","Christianity","Buddhism","Hinduism","Islam","Islam is the oldest recorded monotheistic religion in the Philippines. Islam reached the Philippines in the 13th century with the arrival of Muslim traders from the Persian Gulf, Southern India, and their followers from several sultanate governments in the Malay Archipelago.","m_g_50.webp",4,2);

//Found
        this.questionCreator("What is famous in Manila for shopping?","SM Mall of Asia","Commonwealth Market","Quiapo","Divisoria","SM Mall of Asia","SM Mall of Asia, also abbreviated as SM MoA or simply MoA, is a shopping mall in Bay City, Pasay, Philippines, near the SM Central Business Park, the Manila Bay, and the southern end of Epifanio de los Santos Avenue","m_g_51.webp",4,2);

//Found
        this.questionCreator("You can explore the vast jungles of the Amazon Rain Forest of Brazil and what other South American country?","Peru","New York","Vermont","United States","Peru","The vast forest plays a critical role in keeping the local and regional climate in check. The Amazon rainforest spreads across nine countries namely Brazil, Ecuador, Venezuela, Suriname, Peru, Colombia, Bolivia, Guyana, and French Guiana.","m_g_52.webp",4,2);

//Found
        this.questionCreator("What is Japan\'s nickname?","Nippon","Aola","Aomi","Chiso","Nippon","Both Nippon and Nihon literally mean \"the sun\'s origin\", that is, where the sun originates, and are often translated as the Land of the Rising Sun. This nomenclature comes from Imperial correspondence with the Chinese Sui Dynasty and refers to Japan\'s eastern position relative to China.","m_g_53.webp",4,2);

//Found
        this.questionCreator("You can go hiking in the Himalaya Mountains, the mountains that split Mount Everest between China and which other country?","Nepal","Bhutan","India","New York","Nepal","Mount Everest is located on the border between Tibet and Nepal in the Himalayas in Asia. Everest is situated in the Mahalangur Range on the Tibetan Plateau known as Qing Zang Gaoyuan. The summit is directly between Tibet and Nepal. Mount Everest keeps some tall company.","m_g_54.webp",4,2);

//Found
        this.questionCreator("You can visit both the Greek and Turkish cultures on the island nation of Cyprus, an island in what sea?","Mediterranean Sea","Red Sea","Black Sea","Bhutan","Mediterranean Sea","Island in the Mediterranean, located south of Turkey, west of Syria and Lebanon, northwest of Israel, north of Egypt, and southeast of Greece.","m_g_55.webp",4,2);

//Found
        this.questionCreator("What is the music of Indonesia?","Gamelan","Game-Game","Gamellan","Aolk","Gamelan","Gamelan. The most popular and famous form of Indonesian music is probably gamelan, an ensemble of tuned percussion instruments that include metallophones, drums, gongs and spike fiddles along with bamboo flutes.","m_g_56.webp",4,2);

//Found
        this.questionCreator("What is the nickname of Tokyo?","Electric","Electric Town","Town with Electric","Electricu","Electric","The Sony Plaza is one of the most popular shopping destinations in the district. A part of Tokyo whose nickname of \'Electric Town\' is very befitting. The Tokyo district of Akihabara gets its name after the shrine to a fire extinguishing god that was built in the area after fires destroyed it in 1869.","m_g_57.webp",4,2);

//Found
        this.questionCreator("Why is Thailand famous?","Foodies","Massage","Beach","Caves","Foodies","Thailand is famous for foodies as a street food heaven. Its restaurant scene is not to be sneezed at! It is famous for road safety specialists as its roads are the most dangerous. It is famous for holiday makers for its beaches, temples, jungles and elephants.","m_g_58.webp",4,2);

//Found
        this.questionCreator("What is the nickname of Singapore?","The Lion City","Lion","The Tiger","The Elephant","The Lion City","One of the most popular stories in Singapore\'s history is the tale of how it earned the nickname of The Lion City. In 1299 there was a Prince named Sang Nila Utama. He was the emperor of the Srivijayan Empire, which included Malaysia, Singapore and all of Sumatra","m_g_59.webp",4,2);

//Found
        this.questionCreator("Which state gets the most EF5 tornadoes?","Oklahoma","Zimbabwe","Texas","Brazil","Oklahoma","Oklahoma shares the top of the list of states with the most tornadoes rated either F5 or EF5 since 1950. The Moore tornado on May 20, 2013 was the state\'s 7th, tying it with Alabama, which saw two in one day in 2011. Kansas, Texas and Iowa round out the Top 5 big tornado producers, with six each.","m_g_60.webp",4,2);

//Found
        this.questionCreator("What is the biggest city in the world?","Shanghai","Brazil","Indonesia","Texas","Shanghai","Shanghai\'s population of 24 million makes it the top populous city proper not only in Asia but in he world as well.","m_g_61.webp",4,2);

//Found
        this.questionCreator("What city is known as the city of love?","Paris","America","Philippines","Africa","Paris","Paris is called the \"City of Love\" for a number of reasons, including its sights, its native language and its popularity as a honeymoon destination. Standing above the city is the most romantic of places, the Eiffel Tower.","m_g_62.webp",4,2);

//Found
        this.questionCreator("Petra is located in which country?","Jordan","Iran","Lebanon","Iraq","Jordan","Jordan, an Arab nation on the east bank of the Jordan River, is defined by ancient monuments, nature reserves and seaside resorts. Petra earns its nickname, the \"Rose City.\"","m_g_63.webp",4,2);

//Found
        this.questionCreator("The Colosseum is located in which country?","Italy","France","Norway","Germany","Italy","Italy, a European country with a long Mediterranean coastline, has left a powerful mark on Western culture and cuisine.","m_g_64.webp",4,2);

//Found
        this.questionCreator("What country has a capital of Paris?","France","Georgia","Fiji","Finland","France","France, in Western Europe, encompasses medieval cities, alpine villages and Mediterranean beaches. Paris, its capital, is famed for its fashion houses, classical art museums including the Louvre and monuments like the Eiffel Tower.","m_g_65.webp",4,2);

//Found
        this.questionCreator("What country has a capital of Moscow?","Russia","Republic of the Congo","Rwanda","Romania","Russia","Russia, the world’s largest nation, borders European and Asian countries as well as the Pacific and Arctic oceans.","m_g_66.webp",4,2);

//Found
        this.questionCreator("What is the continent of Australia?","Oceania","America","Europe","Asia","Oceania","Oceania is a geographic region comprising Melanesia, Micronesia, Polynesia and Australasia. Spanning the eastern and western hemispheres, Oceania covers an area of 8,525,989 square kilometres and has a population of 40 million.","m_g_67.webp",4,2);

//Found
        this.questionCreator("What is the continent of Argentina?","South America","Europe","Oceania","Asia","South America","South America is a continent in the Western Hemisphere, mostly in the Southern Hemisphere, with a relatively small portion in the Northern Hemisphere.","m_g_68.webp",4,2);

//Found
        this.questionCreator("What is the continent of Costa Rica?","North America","Europe","Oceania","Asia","North America","North America is a continent entirely within the Northern Hemisphere and almost all within the Western Hemisphere; it is also considered by some to be a northern subcontinent of the Americas.","m_g_69.webp",4,2);

//Found
        this.questionCreator("What is the continent of Libya?","Africa","America","Oceania","Asia","Africa","Africa is the world's second largest and second most-populous continent. At about 30.3 million km² including adjacent islands, it covers % of Earth's total surface area and 20% of its land area.","m_g_70.webp",4,2);

//Found
        this.questionCreator("What is the continent of Norway?","Europe","America","Africa","Asia","Europe","Europe is a continent located entirely in the Northern Hemisphere and mostly in the Eastern Hemisphere. It is bordered by the Arctic Ocean to the north, the Atlantic Ocean to the west and the Mediterranean Sea to the south.","m_g_71.webp",4,2);

//Found
        this.questionCreator("What is the continent of Pakistan?","Asia","America","Africa","Oceania","Asia","Pakistan, officially the Islamic Republic of Pakistan, is a country in South Asia. It is the fifth-most populous country with a population exceeding 212,742,31 people.","m_g_72.webp",4,2);

//Found
        this.questionCreator("What is the continent of Romania?","Europe","America","Africa","Oceania","Europe","Europe is a continent located entirely in the Northern Hemisphere and mostly in the Eastern Hemisphere. It is bordered by the Arctic Ocean to the north, the Atlantic Ocean to the west and the Mediterranean Sea to the south.","m_g_73.webp",4,2);

//Found
        this.questionCreator("What is the continent of Qatar?","Asia","America","Africa","Oceania","Asia","Qatar is a peninsular Arab country whose terrain comprises arid desert and a long Persian (Arab) Gulf shoreline of beaches and dunes.","m_g_74.webp",4,2);

//Found
        this.questionCreator("What is the continent of Portugal?","Europe","America","Africa","Oceania","Europe","Portugal is a southern European country on the Iberian Peninsula, bordering Spain.","m_g_75.webp",4,2);

//Found
        this.questionCreator("What is the continent of Myanmar?","Asia","America","Africa","Oceania","Asia","Myanmar (formerly Burma) is a Southeast Asian nation of more than 100 ethnic groups, bordering India, Bangladesh, China, Laos and Thailand.","m_g_76.webp",4,2);

//Found
        this.questionCreator("What is the continent of Netherlands?","Europe","America","Africa","Oceania","Europe","The Netherlands, a country in northwestern Europe, is known for a flat landscape of canals, tulip fields, windmills and cycling routes.","m_g_77.webp",4,2);

//Found
        this.questionCreator("What is the continent of Jamaica?","North America","Africa","Oceania","Asia","North America","Jamaica, a Caribbean island nation, has a lush topography of mountains, rainforests and reef-lined beaches.","m_g_78.webp",4,2);

//Found
        this.questionCreator("It is the largest church in the wolrd","St.Peter Basilica","Saints Martin and Sebastian of the Swiss","Saint Anne in the Vatican","Sant'Egidio in Borgo","St.Peter Basilica","St. Peter's is the most renowned work of Renaissance architecture and the largest church in the world. It has been described as \"holding a unique position in the Christian world and as \"the greatest of all churches of Christendom\".","m_g_79.webp",4,2);

//Found
        this.questionCreator("Which of the following rivers is the longest?","Congo River","Lena River","Niger River","Irtysh River","Congo River","At two-thousand nine-hundred twenty miles, the Congo River is the second longest river in Africa.","m_g_80.webp",4,2);

//Found
        this.questionCreator("What country does not use the dollar?","India","New Zealand","Australia","Canada","India","India is a country in South Asia. It is the seventh-largest country by area, the second-most populous country (with over 1.2 billion people), and the most populous democracy in the world","m_g_81.webp",4,2);

//Found
        this.questionCreator("In the following which continent will you find Yemen?","Asia","Australia","Africa","Europe","Asia","Yemen, officially known as the Republic of Yemen, is an Arab sovereign state in Western Asia at the southern end of the Arabian Peninsula.","m_g_82.webp",4,2);

//Found
        this.questionCreator("Which continent will you find Turkey?","Asia","Europe","Africa","North America","Asia","Turkey, by land mass, is positioned 95% in Asia and 5% in Europe. The Anatolia section covers the 95% while the 5% represents Thrace in the Balkan Peninsula located in Southeast Europe.","m_g_83.webp",4,2);

//Found
        this.questionCreator("In the following which continent will you find Portugal?","Europe","Austalia","North America","South America","Europe","Portugal is a southern European country on the Iberian Peninsula, bordering Spain.","m_g_84.webp",4,2);

//Found
        this.questionCreator("Which of the following continent you find Malta?","Europe","Africa","South America","North America","Europe","Republic of Malta, is a Southern European island country consisting of an archipelago in the Mediterranean Sea.","m_g_85.webp",4,2);

//Found
        this.questionCreator("Which continent will you find Lesotho?","Asia","Africa","Europe","South America","Asia","Kingdom of Lesotho (Sotho: 'Muso oa Lesotho), is an enclaved country in southern Africa. It is just over 30,000 km2 (11,583 sq mi) in size and has a population of around 2 million. Its capital and largest city is Maseru.","m_g_86.webp",4,2);

//Found
        this.questionCreator("In the following which country has “the Abode of Peace” as an official part of its name?","Brunei","Thailand","Bahrain","Saudia Arabia","Brunei","It was renamed \"Barunai\" in the 14th century, possibly influenced by the Sanskrit word \"varu?\" , meaning \"seafarers\". The word \"Borneo\" is of the same origin. In the country's full name, Negara Brunei Darussalam, darussalam means \"abode of peace\", while negara means \"country\" in Malay.","m_g_87.webp",4,2);

//Found
        this.questionCreator("Which of the following is the Maori name for New Zealand?","Aotearoa","Nui","Moana","Motu","Aotearoa","New Zealand is a sovereign island country in the southwestern Pacific Ocean.","m_g_88.webp",4,2);

//Found
        this.questionCreator("Which country called by the name of “Gold Coast” ?","Ghana","South Africa","Zimbabwe","Argentina","Ghana","The Gold Coast was originally known as the South Coast (because it was south of Brisbane). However, inflated prices for real estate and other goods and services led to the nickname of \"Gold Coast\" from 1950.","m_g_89.webp",4,2);

//Found
        this.questionCreator("In the following what does France officially call itself?","Republique Francaise","Republique Caledonie","Frankreich","Republique Gaule","Republique Francaise","Officially the French Republic, is a sovereign state whose territory consists of metropolitan France in Western Europe, as well as several overseas regions and territories.","m_g_90.webp",4,2);

//Found
        this.questionCreator("In the following what is the familiar name of the Kingdom of Eswatini?","Swaziland","Estonia","Eritrea","The Gambia","Swaziland","The new name, eSwatini, means \"land of the Swazis\". The change was unexpected, but King Mswati has been referring to Swaziland for years as eSwatini.","m_g_91.webp",4,2);

//Found
        this.questionCreator("Tell the official name of Germany in the following?","Federal Republic of Germany","Grand Duchy of Prussia","Unified Republic of Germany","German Democratic Republic","Federal Republic of Germany","\"Germany\" came from the Latin \"Germania\", \"Allemagne\" from the Alemanni tribe, and \"Deutschland\" from the old High German word \"diutisc\" meaning \"of the people\".","m_g_92.webp",4,2);

//Found
        this.questionCreator("Which of the following is the official name of Bolivia?","Plurinational State of Bolivia","Co-operative Republic of Bolivia","Federative Republic of Bolivia","The democratic Socialist Republic of Bolivia","Plurinational State of Bolivia","The name was approved by the Republic on 3 October 1825. In 2009, a new constitution changed the country's official name to \"Plurinational State of Bolivia\" in recognition of the multi-ethnic nature of the country and the enhanced position of Bolivia's indigenous peoples under the new constitution.","m_g_93.webp",4,2);

//Found
        this.questionCreator("In the following what do the French call Australia?","Australie","Australien","Nouveau Holland","Australe","Australie","Australia is a country and continent surrounded by the Indian and Pacific oceans. Its major cities – Sydney, Brisbane, Melbourne, Perth, Adelaide – are coastal.","m_g_94.webp",4,2);

//Found
        this.questionCreator("At its broadest point, how wide is the \"Grand Canyon\"?","Eighteen miles","Forty Four miles","Nine miles","two miles","Eighteen miles","eighteen miles - The Grand Canyon is a steep-sided canyon carved by the Colorado River in Arizona, United States. The Grand Canyon is 277 miles (44 km) long, up to 18 miles (29 km) wide and attains a depth of over a mile (,093 feet or 1,857 meters).","m_g_95.webp",4,2);

//Found
        this.questionCreator("A region of France, what city is the capital of \"French Guiana\"?","Cayenne","Versailles","Bordeaux","Paris","Cayenne","Cayenne - is the capital city of French Guiana, an overseas region and department of Francelocated in South America. The city stands on a former island at the mouth of the Cayenne River on the Atlantic coast. The city's motto is \"fert aurum industria\", which means \"work brings wealth\".","m_g_96.webp",4,2);

//Found
        this.questionCreator("Which great Asian desert covers parts of northern and northwestern China, and southern Mongolia?","Gobi Desert","Kyzyl Kum Desert","Thar Desert","Kara Kum Desert","Gobi Desert","Gobi Desert - is a large desert region in Asia. It covers parts of northern and northwestern China, and of southern Mongolia.","m_g_97.webp",4,2);

//Found
        this.questionCreator("What is the largest saltwater lake or inland sea in the world?","Caspian Sea","Lake Eyre","Lake Balkhash","Lake Van","Caspian Sea","Caspian Sea - is the largest enclosed inland body of water on Earth by area, variously classed as the world's largest lake or a full-fledged sea.","m_g_98.webp",4,2);

//Found
        this.questionCreator("In geography, what name is given to a narrow strip of land that joins two large areas of land across an expanse of water?","Isthmus","Fjord","Moraine","Lacustrine Plain","Isthmus","Isthmus - is a narrow piece of land connecting two larger areas across an expanse of water by which they are otherwise separated. A tombolo is an isthmus that consists of a spit or bar, and a strait is the sea counterpart of an isthmus.","m_g_99.webp",4,2);

//Found
        this.questionCreator("In which Australian state is the important mining center of \"Broken Hill\"?","New South Wales","South Australia","Western Australia","Queensland","New South Wales","New South Wales - The closest major city to Broken Hill is Adelaideat, which is about 311 miles away.","m_g_100.webp",4,2);


    }

    private void addGeographyDifficult()
    {
        //checked

//Found
        this.questionCreator("What is the nickname of Norway?","Land of the Midnight Sun","Land of the Sun","Land of the Sunrise","Land of the Moon","Land of the Midnight Sun","Norway is not called the mid-night sun, but it have the nickname \"Land of the midnight sun\" \" Close, but not cigar.\"","d_g_1.webp",4,3);

//Found
        this.questionCreator("What type of weather is the best for gider pilot?","Convectional","Continental","Maritime","Aviation","Convectional","Convection is a major factor in weather. The sun heats the earth\'s surface, then, when cooler air comes into contact with it, the air warms and rises, creating an upward current in the atmosphere. That current can result in wind, clouds, or other weather.","d_g_2.webp",4,3);

//Found
        this.questionCreator("What is the geologic foundation of Canada?","Canadian Shield","Innuitian Mountains","Western Cordillera","Arctic and Hudson Bay Lowlands.","Canadian Shield","The Canadian Shield, also called the Laurentian Plateau, or Bouclier canadien, is a large area of exposed Precambrian igneous and high-grade metamorphic rocks that forms the ancient geological core of the North American continent.","d_g_3.webp",4,3);

//Found
        this.questionCreator("What year was the Mesozoic Era?","250 - 65","345 - 63","244 - 62","105 - 61","250 - 65","The Mesozoic Era is the age of the dinosaurs and lasted almost 180 million years from approximately 250 to 65 million years ago. This era includes 3 well known periods called the Triassic, Jurassic, and Cretaceous periods. A mass-extinction marked the beginning and end of the Mesozoic Era.","d_g_4.webp",4,3);

//Found
        this.questionCreator("How many Terrestrial Ecozones does Canada have?","15","13","25","17","15","The ecozones of Canada consist of fifteen terrestrial and five marine ecozones in Canada. These are further subdivided into 53 ecoprovinces, 194 ecoregions, and 1021 ecodistricts.","d_g_5.webp",4,3);

//Found
        this.questionCreator("What is the study of population numbers, distribution, trends and issues.","Demography","Populogy","Seismology","Oology","Demography","Demography The scientific study of human populations, including their sizes, compositions, distributions, densities, growth, and other characteristics, as well as the causes and consequences of changes in these factors.","d_g_6.webp",4,3);

//Found
        this.questionCreator("How long did the Spanish stay in the Philippines?","333 year","332 year","331 year","340 year","333 year","During Spain\'s 333 year rule in the Philippines, the colonists had to fight off the Chinese pirates (who lay siege to Manila, the most famous of which was Limahong in 1574), Dutch forces, Portuguese forces, and indigenous revolts.","d_g_7.webp",4,3);

//Found
        this.questionCreator("Where is the Great Bear Lake?","Northwest Territories","Nunavut","British Columbia","Alberta","Northwest Territories","The Great Bear Lake is the largest lake entirely in Canada the fourth-largest in North America, and the eighth-largest in the world.","d_g_8.webp",4,3);

//Found
        this.questionCreator("How many Muslims are there in the Philippines?","10.7 million","10.2 million","10.1 million","10.3 million","10.7 million","However, A 2012 estimate by the National Commission on Muslim Filipinos (NCMF) stated that there were 10.7 million Muslims, or approximately 11 percent of the total population. Most Muslims live in parts of Mindanao, Palawan, and the Sulu Archipelago – an area known as Bangsamoro or the Moro region.","d_g_9.webp",4,3);

//Found
        this.questionCreator("Why did the Irish settlers come to Canada?","Potato crop failed","Poor economy","War","Games","Potato crop failed","Pre-Confederation British North America became home to thousands of people fleeing poverty or oppression in their homelands with hopes to build a better life. In the 1840s, Irish peasants came to Canada in vast numbers to escape a famine that swept Ireland. Year after year, the potato crop failed in Ireland.","d_g_10.webp",4,3);

//Found
        this.questionCreator("The cenozoic era is the age of ?","Mammals","Insects","Reptiles","Sponge","Mammals","The other two are the Mesozoic and Paleozoic Eras. The Cenozoic spans only about 65 million years, from the end of the Cretaceous Period and the extinction of non-avian dinosaurs to the present. The Cenozoic is sometimes called the Age of Mammals, because the largest land animals have been mammals during that time.","d_g_11.webp",4,3);

//Found
        this.questionCreator("What year did the Italians come to Canada?","1870-1914","1912-1924","1424-1786","1956-1989","1870-1914","The mass immigrations of Italians to Canada from 1870-1914, 1920-1930 and 1950-1970 are part of the broader history of the Italian Diaspora, a migratory movement prompted by poor economic conditions in Italy that arose in the 1860s and lasted for over a century.","d_g_12.webp",4,3);

//Found
        this.questionCreator("How many points do you need to be accepted as a skilled worker?","67","34","89","57","67","You will need to complete an Express Entry profile and meet the minimum criteria to be accepted into the pool. If you are interested in the Federal Skilled Worker stream, you will need to meet, at minimum, the pass mark of 67 points out of 100 on the Federal Skilled Worker grid.","d_g_13.webp",4,3);

//Found
        this.questionCreator("How many counties are in Los Angeles California?","88","82","83","85","88","There are 88 cities in Los Angeles County, California.","d_g_14.webp",4,3);

//Found
        this.questionCreator("Which hemisphere is ottawa located in?","North east","South east","North west","North","North east","Ottawa is 3,139.44 mi (5,052.45 km) north of the equator, so it is located in the northern hemisphere.","d_g_15.webp",4,3);

//Found
        this.questionCreator("What does GPS stand for?","Global Positioning System","Gadget Positioning Software","Global Packaging Software","Global Personal Software","Global Positioning System","GPS stands for Global Positioning System. GPS uses satellites that orbit Earth to send information to GPS receivers that are on the ground. The information helps people determine their location.","d_g_16.webp",4,3);

//Found
        this.questionCreator("What percentage of California is Mexican?","39%","32%","33%","34%","39%","Latinos are expected to make up 39 percent of California\'s population, edging past non-Hispanic whites who will make up 38.8 percent. This places California as the second state, behind New Mexico, where Latinos make up the largest percentage of any racial or ethnic group.","d_g_17.webp",4,3);

//Found
        this.questionCreator("What color represents wood on a Topographic Map?","Green","Red","Brown","Blue","Green","The color brown is used to denote most contour lines on a map, which are relief features and elevations. Topographic maps use green to denote vegetation such as woods, while blue is used to denote water features like lakes, swamps, rivers, and drainage.","d_g_18.webp",4,3);

//Found
        this.questionCreator("Mountain barriers create?","Relief Precipatation","Condensation","Air","Rain shadow","Relief Precipatation","Mountain Barriers create Relief Precipitation. As moist air rises up the windward (The side of a mountain that faces the prevailing wind) slope of a mountain, it expands and cools. This cooling produces the rate of condensation to increase. When the droplets of water that form get too heavy they fall as precipitation.","d_g_19.webp",4,3);

//Found
        this.questionCreator("What shaped the Interior Plains","Erosion","Water","Air","Fire","Erosion","During the formation of the Interior Plains, the Interior Plains were often covered by shallow inland seas. ... The landscape of the Interior Plains has been shaped by the forces of erosion. Sedimentary rocks are hard and resistant; others are quite soft.","d_g_20.webp",4,3);

//Found
        this.questionCreator("What is the capital city of Nova Scotia?","Halifax","St.John's","Charlottetown","Victoria","Halifax","Halifax, also known as the Halifax Regional Municipality (HRM), is the capital of the Canadian province of Nova Scotia. The municipality had a population of 403,131 in 2016, with 316,701 in the urban area centred on Halifax Harbour.","d_g_21.webp",4,3);

//Found
        this.questionCreator("What era was the Appalachian Mountains formed?","Paleozoic","Precambrian","Mesozoic","Middle Ages","Paleozoic","As mountains rose, erosion began to wear them down. Streams carried rock debris downslope to be deposited in nearby lowlands. NASA image of the Appalachian Valley and Ridge province. These rock layers were folded during the series of continental collisions that formed the Appalachians during the Paleozoic Era.","d_g_22.webp",4,3);

//Found
        this.questionCreator("How many languages are spoken in Los Angeles?","224","222","221","225","224","English is the most regularly spoken language in the United States, but there are 224 other languages that are spoken in Los Angeles, which can make it easier for non-English speaking expats to get acclimated. Spanish is also a widely spoken first language in Los Angeles.","d_g_23.webp",4,3);

//Found
        this.questionCreator("What is France\'s nickname?","l'Hexagone","Hexogone","Hexxogo","Hexo","l'Hexagone","l\'Hexagone. The nickname “Hexagon” refers to the shape of the mainland\'s borders and so does not include the DOM-TOM, though curiously it does include Corsica.","d_g_24.webp",4,3);

//Found
        this.questionCreator("What is Finland\'s nickname?","Land of the Thousand Lakes","Land of the Thousand Mountain","Land of the Thousand Gem","Land of the Fin","Land of the Thousand Lakes","Finland has thousands of lakes (about 188,000) and islands (about 179,500) leading to the nickname for the country \"Land of the Thousand Lakes\".","d_g_25.webp",4,3);

//Found
        this.questionCreator("When were the Innuitian Mountains formed?","Middle of Mesozoic","Precambrian","Middle of Cenozoic","Middle of Paleozoic","Middle of Mesozoic","The Innuitian Mountains present form was shaped during the Innuitian orogeny in the middle of the Mesozoic Era when the North American Plate moved northward.","d_g_26.webp",4,3);

//Found
        this.questionCreator("What is the most northerly vegetation region in Canada?","Tundra","West Coast Forest","Boreal and Taiga","Mixed forest","Tundra","Boreal Forest or Taiga. The boreal forest or taiga encircles the Northern Hemisphere between the treeless Arctic tundra and the more southerly, mid-latitude broad-leaved forest zones.","d_g_27.webp",4,3);

//Found
        this.questionCreator("What is the area of Paris in square miles?","41","24","32","36","41","Paris is the capital and most populous city of France, with an area of 105 square kilometres (41 square miles) and a population of 2,206,488.","d_g_28.webp",4,3);

//Found
        this.questionCreator("When did the French leave Cambodia?","11 August 1863","13 August 1863","14 August 1863","15 August 1863","11 August 1863","To save Cambodia from being incorporated into Vietnam and Siam, King Ang Duong agreed to colonial France\'s offers of protection, which took effect with King Norodom Prohmbarirak signing and officially recognising the French protectorate on 11 August 1863.","d_g_29.webp",4,3);

//Found
        this.questionCreator("When was Paris founded?","3rd century BC.","1st century BC.","2nd century BC.","4th century BC.","3rd century BC.","Paris was founded around the end of the 3rd century BC by the Gauls who were called Parisii. In 52 BC Julius Caesar\'s legions conquered the territory, founding the Roman city, Lutetia on the earlier settlement.","d_g_30.webp",4,3);

//Found
        this.questionCreator("What is the national animal flower of France?","Iris","Rose","Afri","Cato","Iris","The iris is the national flower of France and is represented in the Fleur-de-lis emblem, the symbol of the French monarchy.","d_g_31.webp",4,3);

//Found
        this.questionCreator("Which is the deepest freshwater lake in the world?","Lake Baikal","Captain Sea","Lake Superior","Lake Huron","Lake Baikal","Lake Baikal in southern Russia is the world\'s deepest lake. It is an estimated 5,387 feet deep (1,642 meters), and its bottom is approximately 3,893 feet (1,187 meters) below sea level. Lake Baikal is also the world\'s largest freshwater lake in terms of volume.","d_g_32.webp",4,3);

//Found
        this.questionCreator("How many lakes does Canada have?","561","563","564","565","561","The number of lakes larger than three square kilometres is estimated at close to 31,752 by the Atlas of Canada, with 561 lakes with a surface area larger than 100 km2,including four of the Great Lakes. All in all, a whopping 9% of Canada\'s surface is covered in fresh water.","d_g_33.webp",4,3);

//Found
        this.questionCreator("Paris is often referred to as the what?","The City of Light","The City of Night","The City of Wealth","The City of Green","The City of Light","Paris is often referred to as The City of Light (La Ville Lumière), both because of its leading role during the Age of Enlightenment, and more literally because Paris was one of the first European cities to adopt gas street lighting.","d_g_34.webp",4,3);

//Found
        this.questionCreator("How many countries are suffering from water scarcity?","43","42","41","45","43","Around 700 million people in 43 countries suffer today from water scarcity. By 2025, 1.8 billion people will be living in countries or regions with absolute water scarcity, and two-thirds of the world\'s population could be living under water stressed conditions.","d_g_35.webp",4,3);

//Found
        this.questionCreator("What town in Massachusetts did witch trials take place in 1892?","Salem","Tainan","Karachi","Mumbai","Salem","The Salem Witch Trials took place in a settlement within the Massachusetts Bay Colony named Salem which, at the time of the trials in 1692, consisted of two sections: Salem town, which is now modern-day Salem, and Salem Village, which is now modern-day Danvers.","d_g_36.webp",4,3);

//Found
        this.questionCreator("Michael Nesmith of the Monkees came from where?","Houston, Texas","Yaoundé , Cameroon","Kabul,Afghanistan","Lucknow,India","Houston, Texas","Robert Michael Nesmith (born December 30, 1942) is an American musician, songwriter, actor, producer, novelist, businessman, and philanthropist, best known as a member of the pop rock band the Monkees and co-star of the TV series The Monkees (1966–1968).","d_g_37.webp",4,3);

//Found
        this.questionCreator("What body of water is off the California coast?","Pacific ocean","Saint Petersburg","Kabul","Antartic Ocean","Pacific ocean","The California Current is a Pacific Ocean current that moves southward along the western coast of North America, beginning off southern British Columbia and ending off southern Baja California Peninsula.","d_g_38.webp",4,3);

//Found
        this.questionCreator("Florida\'s Everglades are famous for its what type of animal?","Alligators","Lions","Zebras","Tigers","Alligators","The Florida Everglades ecosystem is also the only place in the world where alligators and crocodiles exist side by side. Many animals live in the Everglades including the raccoon, skunk, opossum, Eastern Cottontail bobcat, Red Fox and white-tail deer.","d_g_39.webp",4,3);

//Found
        this.questionCreator("The volcano Mount St. Helens, in Washington state, erupted in what year?","1980","1982","1935","1857","1980","1980 eruption of Mount St. Helens. On May 18, 1980, a major volcanic eruption occurred at Mount St. Helens, a volcano located in Skamania County, in the U.S. state of Washington.","d_g_40.webp",4,3);

//Found
        this.questionCreator("One of the richies cities in the Philippines.","Makati","Asok","Boracay","Tandag","Makati","Makati’s role as the country’s business center is undeniable. The reason for this is clear: there are a lot of international, regional and local corporations and offices of significance in the city. In fact, the number of business corporations in the city has been known to be the highest in the country.","d_g_41.webp",4,3);

//Found
        this.questionCreator("How many islands Philippines have?","7,107","7,102","7,105","7,103","7,107","As central Philippines recovers from the devastation of Super Typhoon Haiyan, many of the 7,107 Philippine islands are ready to receive visitors. The monsoon season has come to a close, making now the ideal time for a beach getaway.","d_g_42.webp",4,3);

//Found
        this.questionCreator("What state is Wichita international airport in?","Kansas","Semarang","Tunis","Guada","Kansas","Wichita Dwight D. Eisenhower National Airport is a commercial airport located about 7 miles west of downtown Wichita, Kansas, United States. It is the largest and busiest airport in the state of Kansas. ICT covers 3,248 acres.","d_g_43.webp",4,3);

//Found
        this.questionCreator("What state is called the Coyote State?","South Dakota","Wenzhou","Jakarta","Dalian","South Dakota","The Coyote State. This nickname simply refers to the large coyote population in the state. Coyotes were so numerous in South Dakota that they were adopted as the official state animal in 1949.","d_g_44.webp",4,3);

//Found
        this.questionCreator("The German airship Hindenburg crashed in what state in 1937?","New Jersey","Lima","Lanzhou","Almaty","New Jersey","The Hindenburg disaster occurred on May 6, 1937, in Manchester Township, New Jersey, United States. The German passenger airship LZ 129 Hindenburg caught fire and was destroyed during its attempt to dock with its mooring mast at Naval Air Station Lakehurst.","d_g_45.webp",4,3);

//Found
        this.questionCreator("What state is called the gem state?","Idaho","Montevideo","Phoenix","Nagoya","Idaho","Idaho was first presented to Congress, by mining lobbyist George M. Willing, as a name for a new territory around Pike\'s Peak. He told Congress that Idaho was a Shoshone Indian word that meant \"Gem of the Mountains.\"","d_g_46.webp",4,3);

//Found
        this.questionCreator("Puerto Ricans popularized what kind of dancing in New York in the 1980s?","Salsa dancing","Rumba","Hiphop","Jazz","Salsa dancing","Salsa is a style of Latin music that incorporates multiple styles and variations. It was developed by mid-1960s groups of New York City-area Cuban and Puerto Rican immigrants to the United States, such as Machito and Tito Puente, with later variants such as 1980s.","d_g_47.webp",4,3);

//Found
        this.questionCreator("How many cities are in the Philippines?","122","123","145","90","122","There are 122 cities in the Philippines as of August 28, 2010. Thirty-eight cities are independent: 33 are classified as \"highly urbanized\" and 5 as \"independent component;\" the rest are component cities of the provinces in which they are geographically located.","d_g_48.webp",4,3);

//Found
        this.questionCreator("What was the last state to return to the Union after the Civil War?","Georgia","Quanzhou","Ulsan","Rome","Georgia","Georgia readmitted to Union, July 15, 1870. On this day in 1870, Georgia became the last former Confederate state to be readmitted into the Union after agreeing to seat some black members in the state Legislature.","d_g_49.webp",4,3);

//Found
        this.questionCreator("As of June 2015h how many barangays do we have in the Philippines?","42,029","42,022","42,027","42,021","42,029","As of June 2015, there were 42,029 barangays throughout the Philippines.","d_g_50.webp",4,3);

//Found
        this.questionCreator("What state is called the cornhusker State?","Nebraska","Ekurhuleni","Islamabad","Jaipur","Nebraska","Nebraska is located in the middle of the U.S. in a part called the Great Plains. The state got its nickname, the Cornhusker State, from the University of Nebraska athletic teams. Corn is a major vegetable that is farmed in Nebraska, and the term \"cornhusker\" comes from how the corn is harvested.","d_g_51.webp",4,3);

//Found
        this.questionCreator("Who is the largest landowner in the United States?","John Malone","Aiki Malone","Issac Kingston","Joe Klayton","John Malone","John Malone, who has been nicknamed the \"Cable Cowboy\" for his telecommunications ventures, is the single largest landowner in the United States with 2.2 million acres of land.","d_g_52.webp",4,3);

//Found
        this.questionCreator("What state is called the Gopher State?","Minnesota","Faisalabad","Warsaw","London","Minnesota","Minnesota is a midwestern U.S. state bordering Canada and Lake Superior, the largest of the Great Lakes. The state contains more than 10,000 other lakes, including Lake Itasca, the Mississippi River’s primary source. The “Twin Cities” of Minneapolis and state capital Saint Paul are dense with cultural landmarks like the Science Museum of Minnesota and the Walker Art Center, a modern art museum.","d_g_53.webp",4,3);

//Found
        this.questionCreator("The Enola Gay dropped an atomic bomb in where in WWII?","Hiroshima","Luanda","Accra","Sydney","Hiroshima","Hiroshima was the primary target of the first nuclear bombing mission on 6 August, with Kokura and Nagasaki as alternative targets. Enola Gay, piloted by Tibbets, took off from North Field, in the Northern Mariana Islands, about six hours\' flight time from Japan, accompanied by two other B-29s, The Great Artiste, carrying instrumentation, and a then-nameless aircraft later called Necessary Evil, commanded by Captain George Marquardt.","d_g_54.webp",4,3);

//Found
        this.questionCreator("What Texan city lies opposite Ciudad Juarez?","El Paso","El Chapo","El Caso","El Uika","El Paso","It is sometimes called just Juárez. The city\'s importance is due largely to its position on Mexico\'s border with the United States. Ciudad Juárez lies on the Rio Grande opposite El Paso, Texas, to which it is connected by bridges.","d_g_55.webp",4,3);

//Found
        this.questionCreator("How much land does the US government own?","640 Million","632 Million","623 Million","651 Million","640 Million","The federal government owns about 640 million acres of land in the United States, about 28% of the total land area of 2.27 billion acres.","d_g_56.webp",4,3);

//Found
        this.questionCreator("What is the capital city of Slovenia?","Ljubljana","Bucharest","Kiev","Madrid","Ljubljana","There are 7 towns in Slovenia. According to the Local Self-Government Act of the Republic of Slovenia, a town is a larger urban settlement with more than 3,000 residents and differing from other settlements in its size, economical structure, population, population density and historical development","d_g_57.webp",4,3);

//Found
        this.questionCreator("What is the capital city of Sweden?","Stockholm","Helsinki","Oslo","Vienna","Stockholm","Sweden is a Scandinavian nation with thousands of coastal islands and inland lakes, along with vast boreal forests and glaciated mountains.","d_g_58.webp",4,3);

//Found
        this.questionCreator("What is the capital city of Romania?","Bucharest","Budapest","Vienna","Muscat","Bucharest","Romania is a southeastern European country known for the forested region of Transylvania, ringed by the Carpathian Mountains.","d_g_59.webp",4,3);

//Found
        this.questionCreator("What is the capital city of Hungary?","Budapest","Bucharest","Prague","Oslo","Budapest","Budapest, Hungary’s capital, is bisected by the River Danube. Its 19th-century Chain Bridge connects the hilly Buda district with flat Pest. A funicular runs up Castle Hill to Buda’s Old Town, where the Budapest History Museum traces city life from Roman times onward. Trinity Square is home to 13th-century Matthias Church and the turrets of the Fishermen’s Bastion, which offer sweeping views.","d_g_60.webp",4,3);

//Found
        this.questionCreator("What is the capital city of Germany?","Berlin","Bonn","Munich","Muscat","Berlin","Germany is a Western European country with a landscape of forests, rivers, mountain ranges and North Sea beaches.","d_g_61.webp",4,3);

//Found
        this.questionCreator("What is the capital city of Latvia?","Riga","Tallinn","Vilnius","Bonn","Riga","Latvia is a country on the Baltic Sea between Lithuania and Estonia. Its landscape is marked by wide beaches as well as dense, sprawling forests.","d_g_62.webp",4,3);

//Found
        this.questionCreator("What is the capital city of Bulgaria?","Sofia","Tirana","Berlin","Prague","Sofia","Bulgaria is a Balkan nation with diverse terrain encompassing Black Sea coastline, a mountainous interior and rivers, including the Danube.","d_g_63.webp",4,3);

//Found
        this.questionCreator("Chichen Itza is located in which country?","Mexico","United States","Chile","Argentina","Mexico","Mexico is a country between the U.S. and Central America that's known for its Pacific and Gulf of Mexico beaches and its diverse landscape of mountains, deserts and jungles.","d_g_64.webp",4,3);

//Found
        this.questionCreator("Christ The Redeemer Statue on Corcovado Mountain is located in which country?","Brazil","China","Canada","Norway","Brazil","Christ the Redeemer is an Art Deco statue of Jesus Christ in Rio de Janeiro, Brazil, created by French sculptor Paul Landowski and built by Brazilian engineer Heitor da Silva Costa, in collaboration with French engineer Albert Caquot.","d_g_65.webp",4,3);

//Found
        this.questionCreator("What is the capital city of Afghanistan?","Kabul","Tirana","Luanda","Yerevan","Kabul","Afghanistan, officially the Islamic Republic of Afghanistan, is a landlocked country located within South Asia and Central Asia.","d_g_66.webp",4,3);

//Found
        this.questionCreator("What is the capital city of Algeria?","Algiers","Baku","Vienna","Canberra","Algiers","Algeria is a North African country with a Mediterranean coastline and a Saharan desert interior.","d_g_67.webp",4,3);

//Found
        this.questionCreator("What country has a capital of Dhaka?","Bangladesh","Barbados","Belarus","Belgium","Bangladesh","Bangladesh, to the east of India on the Bay of Bengal, is a South Asian country marked by lush greenery and many waterways.","d_g_68.webp",4,3);

//Found
        this.questionCreator("What country has a capital of Phnom Penh?","Cambodia","Canada","Cape Verde","Cameroon","Cambodia","Cambodia is a Southeast Asian nation whose landscape spans low-lying plains, the Mekong Delta, mountains and Gulf of Thailand coastline.","d_g_69.webp",4,3);

//Found
        this.questionCreator("What country has a capital of Praia?","Cape Verde","Central African Republic","Chad","Canada","Cape Verde","Cape Verde, or Cabo Verde, is a nation on a volcanic archipelago off the northwest coast of Africa.","d_g_70.webp",4,3);

//Found
        this.questionCreator("What country has a capital of Santiago?","Chile","Comoros","Colombia","China","Chile","Chile is a long, narrow country stretching along South America's western edge, with more than ,000km of Pacific Ocean coastline.","d_g_71.webp",4,3);

//Found
        this.questionCreator("What country has a capital of Bogota?","Colombia","Comoros","Costa Rica","Croatia","Colombia","Colombia is a country at the northern tip of South America. It's landscape is marked by rainforests, Andes mountains and numerous coffee plantations.","d_g_72.webp",4,3);

//Found
        this.questionCreator("What country has a capital of Brussels?","Belgium","Belarus","Burundi","Brazil","Belgium","Belgium, a country in Western Europe, is known for medieval towns, Renaissance architecture and as headquarters of the European Union and NATO.","d_g_73.webp",4,3);

//Found
        this.questionCreator("What country has a capital of Cairo?","Egypt","Eritrea","Ethiopia","Estonia","Egypt","Egypt, a country linking northeast Africa with the Middle East, dates to the time of the pharaohs. The capital, Cairo, is home to Ottoman landmarks like Muhammad Ali Mosque and the Egyptian Museum, a trove of antiquities.","d_g_74.webp",4,3);

//Found
        this.questionCreator("What country has a capital of Pyongyang?","Korea, North","Kosovo","Kenya","Korea, South","Korea, North","North Korea, officially the Democratic People's Republic of Korea, is a country in East Asia constituting the northern part of the Korean Peninsula. Pyongyang is the capital and largest city.","d_g_75.webp",4,3);

//Found
        this.questionCreator("What country has a capital of Malé?","Maldives","Mali","Malaysia","Malawi","Maldives","The Maldives is a tropical nation in the Indian Ocean composed of 2 ring-shaped atolls, which are made up of more than 1,000 coral islands.","d_g_76.webp",4,3);

//Found
        this.questionCreator("What country has a capital of Wellington?","New Zealand","Netherlands","Niger","Nicaragua","New Zealand","New Zealand is a country in the southwestern Pacific Ocean consisting of 2 main islands, both marked by volcanoes and glaciation.","d_g_77.webp",4,3);

//Found
        this.questionCreator("What country has a capital of Ankara?","Turkey","Tuvalu","Tunisia","Turkmenistan","Turkey","Turkey is a nation straddling Eastern Europe and western Asia with cultural connections to ancient Greek, Persian, Roman, Byzantine and Ottoman empires.","d_g_78.webp",4,3);

//Found
        this.questionCreator("What country has a capital of Pretoria?","South Africa","South Sudan","Somalia","Solomon Islands","South Africa","South Africa is a country on the southernmost tip of the African continent, marked by several distinct ecosystems.","d_g_79.webp",4,3);

//Found
        this.questionCreator("What is the continent of Comoros?","Africa","America","Oceania","Asia","Africa","Africa is the world's second largest and second most-populous continent. At about 30.3 million km² including adjacent islands, it covers % of Earth's total surface area and 20% of its land area.","d_g_80.webp",4,3);

//Found
        this.questionCreator("What is the continent of Ethiopia?","Africa","America","Oceania","Asia","Africa","Africa is the world's second largest and second most-populous continent. At about 30.3 million km² including adjacent islands, it covers % of Earth's total surface area and 20% of its land area.","d_g_81.webp",4,3);

//Found
        this.questionCreator("What is the continent of Botswana?","Africa","America","Oceania","Asia","Africa","Republic of Botswana is a landlocked country located in Southern Africa. Formerly the British protectorate of Bechuanaland, Botswana adopted its new name after becoming independent within the Commonwealth on 30 September 19.","d_g_82.webp",4,3);

//Found
        this.questionCreator("What is the continent of Palau?","Oceania","America","Africa","Asia","Oceania","Palau is an archipelago of over 500 islands, part of the Micronesia region in the western Pacific Ocean. Koror Island is home to the former capital, also named Koror, and is the islands’ commercial center.","d_g_83.webp",4,3);

//Found
        this.questionCreator("What is the continent of Ireland?","Europe","America","Africa","Oceania","Europe","Ireland is an island in the North Atlantic. It is separated from Great Britain to its east by the North Channel, the Irish Sea, and St George's Channel. Ireland is the third-largest island in Europe.","d_g_84.webp",4,3);

//Found
        this.questionCreator("What is the continent of Honduras?","North America","Africa","Oceania","Asia","North America","Honduras is a Central American country with Caribbean Sea coastlines to the north and the Pacific Ocean to the south.","d_g_85.webp",4,3);

//Found
        this.questionCreator("Which country is in 2 continents?","Turkey","Japan","Egypt","Russia","Turkey","The larger portion of Turkey is located in Western Asia while the remaining portion is located in South Eastern Europe. The city of Istanbul lies on both sides of one of the Turkish Strait (Bosporus) hence making it a transcontinental city. Canakkale City in Turkey also situated in two continents, Europe and Asia.","d_g_86.webp",4,3);

//Found
        this.questionCreator("The largest religious monument in the world","Angkor Watt","St. Peter’s Basilica","Saint Anne in the Vatican","Angkor Wat","Angkor Watt","Angkor wat is originally constructed as a Hindu temple dedicated to the god Vishnu for the Khmer Empire, gradually transforming into a Buddhist temple towards the end of the 12th century.","d_g_87.webp",4,3);

//Found
        this.questionCreator("In the following which country is the death penalty for crime forbidden?","Australia","India","The United States","Belarus","Australia","No Funfacts","d_g_88.webp",4,3);

//Found
        this.questionCreator("In what country are the world’s ten coldest cities located?","Russia","The United States","Chile","Canada","Russia","The enormous size of Russia and the remoteness of many areas from the sea result in the dominance of the humid continental climate, which is prevalent in all parts of the country except for the tundra and the extreme southwest.","d_g_89.webp",4,3);

//Found
        this.questionCreator("What kind of government is that of Oman?","Sultanate","Plebiscite","Democracy","Caliphate","Sultanate","Oman is a unitary state and an absolute monarchy, in which all legislative, executive and judiciary power ultimately rests in the hands of the hereditary Sultan.","d_g_90.webp",4,3);

//Found
        this.questionCreator("In which country you will find the Loch Ness monster?","Scotland","England","Canada","Belgium","Scotland","Scotland, the U.K.’s northernmost country, is a land of mountain wildernesses such as the Cairngorms and Northwest Highlands, interspersed with glacial glens (valleys) and lochs (lakes).","d_g_91.webp",4,3);

//Found
        this.questionCreator("What is the world’s second-largest country?","Canada","China","India","Russia","Canada","Canada is a country located in the northern part of North America. Its ten provinces and three territories extend from the Atlantic to the Pacific and northward into the Arctic Ocean, covering 9.98 million square kilometres (3.85 million square miles), making it the world's second-largest country by total area.","d_g_92.webp",4,3);

//Found
        this.questionCreator("Which of these is an Indian Ocean republic?","Maldives","Malta","Saint Helena","Easter Island","Maldives","Maldives is a country of South Asia, situated in the Indian Ocean, south-southwest of India.","d_g_93.webp",4,3);

//Found
        this.questionCreator("In what country does Arsenal play?","England","Scotland","India","Ireland","England","Arsenal was the first club from the South of England to join The Football League, in 1893, and they reached the First Division in 1904.","d_g_94.webp",4,3);

//Found
        this.questionCreator("What nation is divided into cantons?","Switzerland","Italy","France","Germany","Switzerland","Cantons of Switzerland are the member states of the Swiss Confederation. The nucleus of the Swiss Confederacy in the form of the first three confederate allies used to be referred to as the Waldstätte.","d_g_95.webp",4,3);

//Found
        this.questionCreator("In the following what do Japanese people call Japan?","Nippon","Honshu","Kyoto","Nisshoki","Nippon","Nippon literally mean the sun's origin, that is, where the sun originates, and are often translated as the Land of the Rising Sun.","d_g_96.webp",4,3);

//Found
        this.questionCreator("On which continent will you find Guatemala?","North America","Australia","Africa","South America","North America","Guatemala, a Central American country south of Mexico, is home to volcanoes, rainforests and ancient Mayan sites.","d_g_97.webp",4,3);

//Found
        this.questionCreator("Which continent will you find Belize?","North America","South America","Europe","Africa","North America","Belize is a nation on the eastern coast of Central America, with Caribbean Sea shorelines to the east and dense jungle to the west.","d_g_98.webp",4,3);

//Found
        this.questionCreator("On which continent will you find Nicaragua?","North America","South America","Asia","Africa","North America","Nicaragua, set between the Pacific Ocean and the Caribbean Sea, is a Central American nation known for its dramatic terrain of lakes, volcanoes and beaches.","d_g_99.webp",4,3);

//Found
        this.questionCreator("In which of the continent will you find Sierra Leone?","Africa","South America","North America","Europe","Africa","Officially the Republic of Sierra Leone, is a country in West Africa. It is bordered by Guinea to the northeast, Liberia to the southeast and the Atlantic Ocean to the southwest.","d_g_100.webp",4,3);


    }

    private void addMusicEasy()
    {
        //check

//Found
        this.questionCreator("Who won the 2010 BRIT Award for British Male Solo Artist?","Dizzee Rascal","Britney Spears","New Order","Franz Ferdinand","Dizzee Rascal","At 19, rapper Dizzee Rascal became the youngest winner of the Mercury Prize in 2003, when his debut album Boy in da Corner beat efforts from The Darkness and Radiohead (who lost out despite making the shortlist for a third time). The Londoner, whose real name is Dylan Mills, was nominated again in 2007.","e_m_1.webp",6,1);

//Found
        this.questionCreator("Which band released a 2003 album entitled ‘Youth and Young Manhood’?","Kings of Leon","Talking Heads","Marshmello","The Chainsmokers","Kings of Leon","Youth & Young Manhood is the debut album from American rock band Kings of Leon, released on July 7, 2003, in the United Kingdom and on August 19, 2003, in the United States.","e_m_2.webp",6,1);

//Found
        this.questionCreator("Former Spice Girl Melanie Chisholm dueted with which Canadian singer-songwriter on the 1998 single ‘When You’re Gone’?","Bryan Adams","Ronan Rangel","Ciara Armstrong","Emmalee Roy","Bryan Adams","Chisholm began her solo career in late 1998 by singing with Canadian rock singer Bryan Adams on the worldwide hit \"When You're Gone\". Her solo debut album Northern Star was released in 1999, reaching number 1 in Sweden and number 4 on the UK Albums Chart.","e_m_3.webp",6,1);

//Found
        this.questionCreator("Which band released an 1974 album entitled ‘Crime of the Century’?","The Cranberries","Eagles","Jimi Hendrix","Supertramp","The Cranberries","Crime of the Century is the third studio album by the English rock band Supertramp, released in September 1974 on A&M Records.","e_m_4.webp",6,1);

//Found
        this.questionCreator("Elvis Presley’s manager, Andreas Cornelis van Kujik, was better known by what name?","Colonel Tom Parker","Addison Valentine","Haven Hines","Hallie Robinson","Colonel Tom Parker","Thomas Andrew \"Colonel Tom\" Parker (born Andreas Cornelis van Kuijk; June 26, 1909 – January 21, 1997) was the Dutch-born manager of Elvis Presley.","e_m_5.webp",6,1);

//Found
        this.questionCreator("Which singer released a 1989 album entitled ‘Foreign Affair’?","Tina Turner","Keenan Keller","Esperanza Barry","Payten Mueller","Tina Turner","Foreign Affair is the seventh solo studio album by Tina Turner, released on Capitol Records in 1989. It was Turner's third album release after her massively successful global comeback six years earlier, and although the album was not a major success in Turner's native United States, it was a huge international success in Europe.","e_m_6.webp",6,1);

//Found
        this.questionCreator("In the song, how many ships came sailing by on Christmas day?","Three","Two","One","Five","Three","\"I Saw Three Ships (Come Sailing In)\" is a traditional and popular Christmas carol from England. The earliest printed version of \"I Saw Three Ships\" is from the 17th century, possibly Derbyshire, and was also published by William Sandys in 1833.","e_m_7.webp",6,1);

//Found
        this.questionCreator("Which English model was married to musicians George Harrison and Eric Clapton?","Pattie Boyd","Darrell Powell","Alexis Finley","Deanna Page","Pattie Boyd","Boyd was married to George Harrison and experienced the height of the Beatles' popularity as well as sharing in their embrace of Indian spirituality. She later married and divorced Harrison's friend, guitarist Eric Clapton.","e_m_8.webp",6,1);

//Found
        this.questionCreator("What colour ‘Onions’ is the title of a 1962 hit for Booker T and the MG’s?","Green","Blue","Red","Pink","Green","They released instrumental records under their own name, of which the best known is the 1962 hit single \"Green Onions\". As originators of the unique Stax sound, the group was one of the most prolific, respected, and imitated of its era. By the mid-1960s, bands on both sides of the Atlantic were trying to sound like Booker T. & the M.G.'s.","e_m_9.webp",6,1);

//Found
        this.questionCreator("Which US singer did Lisa Marie Presley marry in May 1994?","Michael Jackson","Augustus Manning","Braedon Barron","Isiah Yu","Michael Jackson","Lisa Marie Presley (born February 1, 1968) is an American singer-songwriter. She is the only child of singer and actor Elvis Presley and actress Priscilla Presley, as well as the sole heir to her father's estate. Presley has developed a career in the music business and has issued three albums. She has been married four times, including to singer Michael Jackson and actor Nicolas Cage, before marrying music producer Michael Lockwood, father of her twin girls.","e_m_10.webp",6,1);

//Found
        this.questionCreator("Which US singing duo started out under the names Caesar and Cleo?","Sonny and Cher","Casty and Mer","Jake and Mon","Krisha and Sam","Sonny and Cher","Sonny & Cher were an American duo of entertainers made up of husband-and-wife Sonny Bono and Cher in the 1960s and 1970s. The couple started their career in the mid-1960s as R&B backing singers for record producer Phil Spector.","e_m_11.webp",6,1);

//Found
        this.questionCreator("Which US singer released a 2000 album entitled ‘Music’?","Madonna","Courtney Esparza","Draven Gilbert","Mitchell Browning","Madonna","Music is the eighth studio album by American singer Madonna, released on September 18, 2000 by Maverick and Warner Bros. Records. Following the success of her previous album Ray of Light (1998), she intended to embark on a tour. However, her record company encouraged her to return to the studio and record new music before going on the road.","e_m_12.webp",6,1);

//Found
        this.questionCreator("What was the title of The Beatles first single for EMI, released in 1962?","Love Me Do","The Green Stuff","Hey Jude","Come","Love Me Do","In the United States the single was a No. 1 hit in 1964. In 2013, recordings of the song that were released in 1962 entered the public domain in Europe. The song was written several years before it was recorded, and prior to the existence of the Beatles.","e_m_13.webp",6,1);

//Found
        this.questionCreator("What is the title of singer Bruno Mars debut album, released in October 2010?","Doo-Wops and Hooligans","Creeping inside","Walk to water","Sale my soul","Doo-Wops and Hooligans","Mars released his first solo single, \"Just the Way You Are,\" from his debut studio album, Doo-Wops & Hooligans, released in October 2010. The song quickly proved to be another hit for the artist, putting him back at the top of the Billboard singles chart.","e_m_14.webp",6,1);

//Found
        this.questionCreator("Larry Mullen Jr is the drummer in which Irish band?","U2","Fleetwood Mac","ABBA","Aerosmith","U2","Laurence Joseph Mullen Jr. is an Irish musician and actor, best known as the drummer and co-founder of the rock band U2. Mullen's distinctive drumming style developed from his playing martial beats in a childhood marching band, the Artane Boys Band.","e_m_15.webp",6,1);

//Found
        this.questionCreator("Which jazz pianist, bandleader and composer was backed by the Red Hot Peppers?","Jelly Roll Morton","Connor York","Deborah Hayden","Konnor Shepard","Jelly Roll Morton","Ferdinand Joseph LaMothe (October 20, 1890 – July 10, 1941), known professionally as Jelly Roll Morton, was an American ragtime and early jazz pianist, bandleader and composer who started his career in New Orleans, Louisiana.","e_m_16.webp",6,1);

//Found
        this.questionCreator("Which member of Take That replaced Simon Cowell as a judge on the British ‘X Factor’?","Gary Barlow","Sylvia Michael","Casey Dixon","Krista Bowman","Gary Barlow","Barlow also served as head judge of The X Factor UK in 2011, 2012 and 2013 and Let It Shine in 2017. Barlow is one of Britain's most successful songwriters, having written fourteen number one singles and twenty-four top 10 hits.","e_m_17.webp",6,1);

//Found
        this.questionCreator("What is the name of the land where ‘Puff the Magic Dragon’ lived?","Honalee","France","Italy","U.S.A","Honalee","\"Puff, the Magic Dragon\" (or \"Puff\") is a song written by Leonard Lipton and Peter Yarrow, and made popular by Yarrow's group Peter, Paul and Mary in a 1962 recording released in January 1963.","e_m_18.webp",6,1);

//Found
        this.questionCreator("Which country singer was known as ‘The Man in Black’?","Johnny Cash","Terrell Khan","Bradley Harvey","Emelia Leon","Johnny Cash","\"Man in Black\" (or \"The Man in Black\") is a protest song written and recorded by singer-songwriter Johnny Cash, originally released on his 1971 album of the same name. Cash himself was known as \"The Man in Black\" for his distinctive style of on-stage costuming.","e_m_19.webp",6,1);

//Found
        this.questionCreator("US singer and musician William James Adams is better known by what name?","will.i.am","MGMT","The Who","Weezer","will.i.am","William James Adams (born March 15, 1975), professionally known by his stage name will.i.am (pronounced \"Will I am\"), is an American rapper, musician, songwriter, singer, entrepreneur, actor, DJ and producer. He came to prominence in the late 1990s as a member of the hip hop group The Black Eyed Peas.","e_m_20.webp",6,1);

//Found
        this.questionCreator("‘When are you gonna come down, When are you going to land’, are the opening lyrics to which Elton John song?","Goodbye Yellow Brick Road","Goodbye My Friend","Telling you something","Please wait","Goodbye Yellow Brick Road","\"Goodbye Yellow Brick Road\" is a ballad performed by musician Elton John. Lyrics for the song were written by Bernie Taupin and the music composed by John for his album Goodbye Yellow Brick Road. Its musical style and production was heavily influenced by 1970s soft rock. It was widely praised by critics, and some critics have named it John's best song.","e_m_21.webp",6,1);

//Found
        this.questionCreator("Julia and Alfred were the names of parents of which member of British pop band The Beatles?","John Lennon","Javier Hodge","Jaylah Mann","Denisse Perkins","John Lennon","Lennon was born on 9 October 1940 at Liverpool Maternity Hospital, to Julia (née Stanley) (1914–1958) and Alfred Lennon (1912–1976). Alfred was a merchant seaman of Irish descent who was away at the time of his son's birth. His parents named him John Winston Lennon after his paternal grandfather, John \"Jack\" Lennon, and Prime Minister Winston Churchill.","e_m_22.webp",6,1);

//Found
        this.questionCreator("What is the title of singer Beyonce’s album, released in June 2011?","4","B\'Day","Everything is love","Happy","4","4 is the fourth studio album by American singer Beyoncé. It was released on June 24, 2011 by Parkwood Entertainment and Columbia Records.","e_m_23.webp",6,1);

//Found
        this.questionCreator("Batterhead, Flam and Rimshot are all terms associated with which musical instrument?","Drum","Guitar","Piano","Cymbal","Drum","The drum is a member of the percussion group of musical instruments. In the Hornbostel-Sachs classification system, it is a membranophone.","e_m_24.webp",6,1);

//Found
        this.questionCreator("What is the title of the first record ever broadcast on BBC Radio 1 in the UK?","Flowers in the Rain","Sunlight","Plants","Sunflower Bloom","Flowers in the Rain","Radio 1 launched at 7am on September 30, 1967. Tony Blackburn was the first DJ on air, launching the station with his new programme Daily Disc Delivery with Robin Scott, then Controller of Radio 1, standing over him. 2. The first record played on Radio 1 was Flowers in the Rain by The Move.","e_m_25.webp",6,1);

//Found
        this.questionCreator("‘Hey Ya’ was a hit for which US duo?","OutKast","The Who","Pink Floyd","Moby","OutKast","\"Hey Ya!\" is a song written and produced by André 3000. Along with \"The Way You Move\", recorded by OutKast's other member Big Boi, \"Hey Ya!\". The song popularized the phrase \"shake it like a Polaroid picture\", and the Polaroid Corporation used the song to revitalize the public's perception of its products.","e_m_26.webp",6,1);

//Found
        this.questionCreator("How many reeds does an oboe have?","2","4","3","1","2","First, there are the parts into which the player blows. The clarinet has a mouthpiece which is connected to a single reed. Conversely, while the oboe has no mouthpiece it does have two reeds-the oboe is a double-reed instrument.","e_m_27.webp",6,1);

//Found
        this.questionCreator("US singer Thomas DeCarlo Callaway is better known by what name?","Cee-Lo Green","Miah Villanueva","Jace Owens","Celeste Mcdonald","Cee-Lo Green","Thomas DeCarlo Callaway (born May 30, 1974) better known by his stage name Cee Lo Green, is an American singer-songwriter, rapper, record producer, and actor.","e_m_28.webp",6,1);

//Found
        this.questionCreator("Which US singer received the Legend Award at the 1994 Grammy Awards?","Frank Sinatra","Juliana Riddle","Todd Anthony","Carissa Clarke","Frank Sinatra","The first Grammy Legend Awards were issued in 1990 to Smokey Robinson, Willie Nelson, Andrew Lloyd Webber, and Liza Minnelli. The honor was inaugurated to recognize \"ongoing contributions and influence in the recording field\". The next year four more musicians (Aretha Franklin, Billy Joel, Johnny Cash and Quincy Jones) were acknowledged with Grammy Legend Awards. The award was given to Barbra Streisand in 1992 and Michael Jackson in 1993.","e_m_29.webp",6,1);

//Found
        this.questionCreator("Pop band ABBA wrote the song ‘Chiquitita’ to commemorate the 1979 ‘International Year of the….’what’?","Child","Humans","Plants","Animals","Child","\"Chiquitita\" (a Spanish term of endearment for a woman meaning \"little one\") is a song recorded by Swedish pop group ABBA. It was released in January 1979 as the first single from the group's Voulez-Vous album. Agnetha Fältskog sang the lead vocals.","e_m_30.webp",6,1);

//Found
        this.questionCreator("Harry, Liam, Niall, Louis and Zayn are all members of which British boy band?","One Direction","Blur","The National","One Republic","One Direction","One Direction are an English-Irish pop music band based in London, composed of Niall Horan, Liam Payne, Harry Styles, and Louis Tomlinson. (Zayn Malik left the band in 2015).","e_m_31.webp",6,1);

//Found
        this.questionCreator("David Evans is the real name of which Irish musician?","The Edge","Cyclone","Player","Fast","The Edge","David Howell Evans, better known by his stage name the Edge, is an Irish musician and songwriter best known as the lead guitarist, keyboardist and backing vocalist of the rock band U2. A member of the group since its inception, he has recorded 14 studio albums with the band as well as one solo record.","e_m_32.webp",6,1);

//Found
        this.questionCreator("‘Back to Bedlam’ was the debut album for which British singer?","James Blunt","Austin Moreno","Emilia Ortega","Nadia Pierce","James Blunt","Back to Bedlam is the debut studio album by the English singer-songwriter James Blunt, released on 11 October 2004 through Atlantic Records.","e_m_33.webp",6,1);

//Found
        this.questionCreator("What colour ‘Betty’ is the title of a 1977 hit by Ram Jam?","Black","Yellow","Green","Orange","Black","The origin and meaning of the lyrics are subject to debate. Historically the \"Black Betty\" of the title may refer to the nickname given to a number of objects: a musket, a bottle of whiskey, a whip, or a penitentiary transfer wagon.","e_m_34.webp",6,1);

//Found
        this.questionCreator("Which English singer-songwriter’s pet spaniel was a special guest at his civil partnership ceremony?","Elton John","Rishi Hickman","Theresa Downs","Lizbeth Vargas","Elton John","John, who announced he was bisexual in 1976 and has been openly gay since 1988, entered into a civil partnership with David Furnish on 21 December 2005, and after same-sex marriage was legalized in England and Wales in 2014, wed Furnish on 21 December 2014.","e_m_35.webp",6,1);

//Found
        this.questionCreator("Singer Elly Jackson is one half of which pop duo?","La Roux","Green Day","Disturbed","Radiohead","La Roux","Eleanor Kate \"Elly\" Jackson (born 12 March 1988) is an English singer, songwriter and the solo member of La Roux, a former synth-pop duo which is now a solo project. Jackson is well known for having reddish toned hair and androgynous style. Jackson has a soprano vocal range.","e_m_36.webp",6,1);

//Found
        this.questionCreator("Who duetted with Madonna on the 2008 single ‘Four Minutes’?","Justin Timberlake","Rogelio Mcknight","Shirley Ware","Jorden Bishop","Justin Timberlake","\"4 Minutes\" is a song by American singer Madonna from her eleventh studio album Hard Candy (2008), featuring vocals by American singers Justin Timberlake and Timbaland. It was released as the lead single from the album on March 17, 2008, by Warner Bros. Records. According to Madonna, the song is about saving the environment and \"having a good time while we are doing it\".","e_m_37.webp",6,1);

//Found
        this.questionCreator("Which US singer is known by the nickname J Lo?","Jennifer Lopez","Jaidyn Kane","Jarrett Henderson","Braedon Reeves","Jennifer Lopez","Jennifer Lynn Lopez is an American singer, actress, dancer and producer. In 1991, Lopez began appearing as a Fly Girl dancer on In Living Color, where she remained a regular until she decided to pursue an acting career in 1993.","e_m_38.webp",6,1);

//Found
        this.questionCreator("Which British singer released a 1972 album entitled ‘Blondes Have More Fun’?","Rod Stewart","Clare Colon","Joe Krause","Slade Howe","Rod Stewart","Blondes Have More Fun is Rod Stewart's ninth album, released in November 1978. As was the popular musical trend at the time, it is Stewart's foray into disco music, which although commercially successful, was critically divisive.","e_m_39.webp",6,1);

//Found
        this.questionCreator("What is the English title of the Welsh national anthem?","Land of My Fathers","Land of Fatherss","Land of Fathers","Land of My Faathers","Land of My Fathers","\"Hen Wlad Fy Nhadau\" is the national anthem of Wales. The title – taken from the first words of the song – means \"Old Land of My Fathers\" in Welsh, usually rendered in English as simply \"Land of My Fathers\". The words were written by Evan James and the tune composed by his son, James James, both residents of Pontypridd, Glamorgan, in January 1856.","e_m_40.webp",6,1);

//Found
        this.questionCreator("In 1966, in an interview with a London Evening Standard reporter, which Beatle stated that they had become ‘More popular than Jesus’?","John Lennon","Amanda Rios","Lee Lam","Jay Nunez","John Lennon","\"More popular than Jesus\" a was part of a longer remark made by the Beatles' John Lennon during a 1966 interview, in which he argued that Christianity would end, possibly before rock music. His opinions drew no controversy when originally published in the United Kingdom, but angry reactions flared up in Christian communities when the comment was republished in the United States five months later.","e_m_41.webp",6,1);

//Found
        this.questionCreator("‘The Divine Miss M’ was the debut album of which US singer?","Bette Midler","Aldo Lawrence","Ari May","Cristal Ross","Bette Midler","The Divine Miss M is the debut studio album by American singer and actress Bette Midler, released in 1972 on the Atlantic Records label. The title of the album refers to Midler's famous stage persona.","e_m_42.webp",6,1);

//Found
        this.questionCreator("Which US singer/guitarist was on the ‘Winter Dance Tour’ when he was killed in a plane crash along with Ritchie Valens and The Big Bopper?","Buddy Holly","Eli Webb","Yareli Bender","Litzy Mccormick","Buddy Holly","Valens died in a 1959 plane crash while on tour, along with Buddy Holly and J.P. \"The Big Bopper\" Richardson. Growing in Pacoima, Valens developed a love of music early on and learned to play a number of different instruments. But the guitar soon became his passion.","e_m_43.webp",6,1);

//Found
        this.questionCreator("What is the name of the last Beatles album to be recorded before the band split up?","Abbey Road","Long Road","Chalk","Pen","Abbey Road","Let It Be became the last album to be finished by the Beatles, even though its recording had begun before Abbey Road. By September 1969, after the recording of Abbey Road, Lennon had formed a new group, the Plastic Ono Band, in part because the Beatles had rejected his song \"Cold Turkey\".","e_m_44.webp",6,1);

//Found
        this.questionCreator("Steve Harley was the lead singer in which 1970′s band?","Cockney Rebel","Dire Straits","The Cure","AC/DC","Cockney Rebel","The original Cockney Rebel consisted of Harley, Crocker, drummer Stuart Elliott, bassist Paul Jeffreys and guitarist Nick Jones. Jones was soon replaced by Pete Newnham, however Harley felt the band did not need electric guitar, particularly with the arrival of keyboardist Milton Reame-James.","e_m_45.webp",6,1);

//Found
        this.questionCreator("Which singer released the single ‘Lonely’ in 2005?","Akon","Lizeth Sparks","Nathalia Velasquez","Jadyn Wiggins","Akon","\"Lonely\" (also known as \"Mr. Lonely\") is a song by Senegalese-American R&B and rapper Akon; it appears on his debut album, Trouble. The single was released in 2005 and was his first worldwide hit. It reached number one in several countries, including in the United Kingdom and Germany (where it stayed there for eight weeks), and Australia.","e_m_46.webp",6,1);

//Found
        this.questionCreator("Which Mariah Carey and Boyz II Men song became the first to spend 16 weeks at No 1?","One Sweet Day","Morning","Good Night","Midnight Sky","One Sweet Day","\"One Sweet Day\" is a song by American singer Mariah Carey and R&B group Boyz II Men. Both Carey and Boyz II Men wrote the song about specific people in their lives, being inspired by sufferers of the AIDS epidemic, which was globally prevalent at that time.","e_m_47.webp",6,1);

//Found
        this.questionCreator("What type of train did Veteran Rod Stewart chart with?","Downtown Train","Light Rail","Rapid Transit","Maglev","Downtown Train","\"Downtown Train\" is a song by Tom Waits released on his album Rain Dogs in 1985.","e_m_48.webp",6,1);

//Found
        this.questionCreator("Who had hits with El Paso and Devil Woman?","Marty Robbins","Zoie Cowan","Saige Glover","Neil Ryan","Marty Robbins","Devil Woman is a song written and performed by American country music artist Marty Robbins. It was released in June 1962 as the first single and title track from the album Devil Woman. \"Devil Woman\" was Robbins' seventh single to reach number one on the country chart, spending eight weeks at the top spot.","e_m_49.webp",6,1);

//Found
        this.questionCreator("Who co-wrote Fame with David Bowie?","John Lennon","Alanna Lucas","Aisha Malone","Lilly Peterson","John Lennon","Carlos Alomar had developed a guitar riff for Bowie's cover of \"Footstompin'\" by the Flairs, which Bowie thought was \"a waste\" to give to a cover. Lennon, who was in the studio with them, sang \"aim\" over the riff, which Bowie turned into \"Fame\" and he thereafter wrote the rest of the lyrics to the song.","e_m_50.webp",6,1);

//Found
        this.questionCreator("What does the V stand for in SWV?","Voices","Violin","Vertical","Vert","Voices","SWV stands for Sisters With Voices (R&B Group)","e_m_51.webp",6,1);

//Found
        this.questionCreator("Victoria Principal sang All I Have To Do Is Dream with which Gibb?","Andy","Aisha","Lilly","Leia","Andy","Andrew Roy Gibb (5 March 1958 – 10 March 1988) was an English singer, songwriter, performer, and teen idol.[3] He was the younger brother of the Bee Gees: Barry, Robin, and Maurice Gibb.","e_m_52.webp",6,1);

//Found
        this.questionCreator("Who recorded Macarena (bayside boys mix)?","Los Del Rio","Ingrid Bautista","Ramon Vazquez","Emily Boyer","Los Del Rio","Los del Río, also known as The Del Rios, are a Spanish Latin pop and dance duo which formed in 1962 by members Antonio Romero Monge and Rafael Ruíz Perdigones. They are best known for their smash-hit dance single \"Macarena\", originally released in early 1994. The song went on to become a worldwide success.","e_m_53.webp",6,1);

//Found
        this.questionCreator("Who reached No 1 with Unbelievable?","EMF","Khalid","Blink-182","Oasis","EMF","Unbelievable is a song written and recorded by British band EMF, originally appearing on their debut album Schubert Dip. It was released as a single in the UK in 1990, peaking in the UK Singles Chart at number three on 1 December 1990","e_m_54.webp",6,1);

//Found
        this.questionCreator("Which group had 80s hits with Hard To Say I'm Sorry and Look Away?","Chicago","U2","Slipknot","CHVRCHES","Chicago","\"Hard to Say I'm Sorry\" is a song written by bassist Peter Cetera, who also sang lead on the track, and producer David Foster, for the group Chicago. It was released on May 16, 1982, as the lead single from the album Chicago 16. The song reached No. 1 for two weeks on the Billboard Hot 100 chart on September 11 of that year.","e_m_55.webp",6,1);

//Found
        this.questionCreator("What was the first No 1 single for Hall & Oates?","Rich Girl","Poor Dad","Royal","Saints","Rich Girl","Released in late 1984, the first single from the LP, \"Out of Touch\", became the group's sixth number 1 hit on December 8, 1984. \"Method of Modern Love\", which debuted on the pop charts while \"Out of Touch\" was at number 1, reached number 5 in February 1985.","e_m_56.webp",6,1);

//Found
        this.questionCreator("Who hit the top ten with the Unskinny Bop?","Poison","Medicine","Stomach","Ill","Poison","\"Unskinny Bop\" is a song by Poison. According to Poison guitarist C.C. DeVille, the phrase \"Unskinny Bop\" has no particular meaning. DeVille wrote the music and used the phrase as a placeholder for the lyrics, which lead singer Bret Michaels would write.","e_m_57.webp",6,1);

//Found
        this.questionCreator("What was Heart's first No 1 hit?","These Dreams","Those Dreams","Her Dreams","His Dreams","These Dreams","\"These Dreams\" is a 1986 song by the American rock band Heart. It was released as a single in 1986 from their 1985 self-titled album. It was the first song by the band to become a number one hit on the Billboard Hot 100.","e_m_58.webp",6,1);

//Found
        this.questionCreator("Which Gibb brother is the eldest?","Barry","Carleigh","Sariah","Leonard","Barry","The oldest son of a bandleader, Barry Gibb grew up surrounded by music. He, along with his younger twin brothers Robin and Maurice, became one of the top pop music acts of the 1970s. The trio started performing together as children.","e_m_59.webp",6,1);

//Found
        this.questionCreator("I'll Be There For You was the theme to which hit TV show?","Friends","Young Wilds","Enemies","Secrets","Friends","\"I'll Be There for You\" is a song recorded by the American duo The Rembrandts. It is best known as the theme song to the American sitcom Friends, which premiered in September 1994 and ended in May 2004. The song was also released as the first single from the group's third studio album LP.","e_m_60.webp",6,1);

//Found
        this.questionCreator("Which liquid product featured the hit Like A Prayer in its advertisement?","Pepsi","Coca-Cola","Virgin","Monster","Pepsi","In January 1989, while the music video was still being filmed, Pepsi-Cola announced that they had signed Madonna for US$5 million deal to feature the singer and \"Like a Prayer\" for the company's television commercial.","e_m_61.webp",6,1);

//Found
        this.questionCreator("Which year was the title of Zager & Evans' only No 1?","2525","2059","2052","2040","2525","Zager and Evans was a US rock-pop duo of the late 1960s and early 1970s named after its two members, Denny Zager (born 1944, Wymore, Nebraska) and Rick Evans (born 1943, Lincoln, Nebraska).","e_m_62.webp",6,1);

//Found
        this.questionCreator("Which girl got \"Kiss\" into the singles top ten for the first time?","Beth","Kael","Danika","Demarcus","Beth","\"Beth\" is a ballad by American hard rock band Kiss, originally released on their 1976 album, Destroyer.","e_m_63.webp",6,1);

//Found
        this.questionCreator("Who duetted with Peabo Bryson on Beauty and the Beast?","Celine Dion","Laylah Santiago","Desmond Hatfield","Edgar Carlson","Celine Dion","\"Beauty and the Beast\" was subsequently recorded as a pop duet by Canadian singer Celine Dion and American singer Peabo Bryson, and released as the only single from the film's soundtrack on November 25, 1991.","e_m_64.webp",6,1);

//Found
        this.questionCreator("Who knocked themselves off No 1 position with their follow up On Bended Knee?","Boyz II Men","Beach House","Toto","The Strokes","Boyz II Men","\"On Bended Knee\" is a 1994 number-one hit single by Boyz II Men for the Motown label. It is the second single from their second album, II.","e_m_65.webp",6,1);

//Found
        this.questionCreator("On which label does Mariah Carey record?","Columbia","Math","Rocket Science","Rostrum","Columbia","After signing to Columbia Records, she released her debut album, Mariah Carey (1990), which spawned four number-one singles on the U.S. Billboard Hot 100 chart: \"Vision of Love\", \"Love Takes Time\", \"Someday\", and \"I Don't Wanna Cry\". She followed this chart success with the number-one single \"Emotions\".","e_m_66.webp",6,1);

//Found
        this.questionCreator("Who had a 1978 No 1 with Boogie Oogie Oogie?","A Taste Of Honey","A Blind Man","A Cool Kid","A Rich Son","A Taste Of Honey","\"Boogie Oogie Oogie\" is a song by the American band A Taste of Honey from their 1978 self-titled debut album. Released as their debut single in the summer of 1978, the song became an extremely popular \"crossover\"[clarification needed] disco song. The lyrics urge listeners to \"boogie oogie oogie till you just can't boogie no more\".","e_m_67.webp",6,1);

//Found
        this.questionCreator("Who wrote under the pseudonym Bernard Webb?","Paul McCartney","Diana Morgan","Jayleen Owen","Rubi Steele","Paul McCartney","\"Woman\" is a 1966 single by Peter and Gordon, written by Paul McCartney under a pseudonym (to see if it would be a success without the Lennon-McCartney label).","e_m_68.webp",6,1);

//Found
        this.questionCreator("Which country does Eddy Grant originate from?","Guyana","Africa","China","America","Guyana","Born in the Caribbean nation of Guyana in 1948, Eddy was exposed during his childhood to the distinctive sounds of African and Indian music which is indigenous to Guyana along with the music of the surrounding countries such as Surinam and in particular Trinidad and Tobago.","e_m_69.webp",6,1);

//Found
        this.questionCreator("Who wrote the song The Last Thing On My Mind?","Tom Paxton","Felipe Welch","Maddison Clark","Yaritza Solis","Tom Paxton","Thomas Richard Paxton is an American folk singer-songwriter who has had a music career spanning more than fifty years. In 2009, Paxton received a Grammy Lifetime Achievement Award. He is noteworthy as a music educator as well as an advocate for folk singers to combine traditional songs with new compositions.","e_m_70.webp",6,1);

//Found
        this.questionCreator("What was Nirvana's third album called?","In Utero","In Pluto","Earth","Plane Crash","In Utero","In Utero is the third and final studio album by American rock band Nirvana, released on September 21, 1993, by DGC Records. Nirvana intended for the record to diverge significantly from the polished, refined production of its previous album, Nevermind (1991).","e_m_71.webp",6,1);

//Found
        this.questionCreator("In which state did Kurt Cobain die?","Washington","France","Italy","U.S.A","Washington","On April 8, 1994, Kurt Cobain, the lead singer of the grunge band Nirvana, was found dead at his home in Seattle, Washington. Forensic analysis at the time determined he had died by suicide on April 5.","e_m_72.webp",6,1);

//Found
        this.questionCreator("What was Michael Jackson's debut hit away from the Jackson 5?","Got To Be There","Believe me","Call me","4 AM","Got To Be There","Got to Be There is the debut studio album by Michael Jackson, released by Motown on January 24, 1972. It includes the song of the same name, which was released on October 7, 1971, as Jackson's debut solo single.","e_m_73.webp",6,1);

//Found
        this.questionCreator("Which female vocalist was a member of the Eurythimics?","Annie Lennox","Colin Moss","Yazmin Durham","Jace Walls","Annie Lennox","Between 1977 and 1980, she was the lead singer of The Tourists, a British pop band and her first collaboration with Dave Stewart. Lennox and Stewart's second collaboration, the 1980s synthpop duo Eurythmics, resulted in her most notable fame, as the duo's alto, soul-tinged lead singer.","e_m_74.webp",6,1);

//Found
        this.questionCreator("What was the first single of The Spice Girls?","Wannabe","Going Hard","Work Hard","Boom","Wannabe","Formed in 1994, the group was made up of singers Victoria Beckham (\"Posh Spice\"), Geri Halliwell (\"Ginger Spice\"), Emma Bunton (\"Baby Spice\"), Melanie Brown (\"Scary Spice\") and Melanie Chisholm (\"Sporty Spice\"). The Spice Girls' debut single, \"Wannabe\", was released by Virgin Records in the United Kingdom in July 1996.","e_m_75.webp",6,1);

//Found
        this.questionCreator("What 2017 Billboard Top 100 song starts with the lyric - \"The club isn\'t the best place to find a lover, so the bar is where I go\"?","Shape of You","Tunnel Vision","Castle on the Hill","I Feel it Coming","Shape of You","\"Shape of You\" peaked at number-one on the singles charts of 34 countries, including the US Billboard Hot 100 (later becoming the best performing song of 2017), as well as the UK, Australian and Canadian singles charts.","e_m_76.webp",6,1);

//Found
        this.questionCreator("Released on her fifth studio album, which American singer had a hit with the song \"Million Reasons\"?","Lady Gaga","Kesha","Katy Perry","Christina Aguilera","Lady Gaga","Stefani Joanne Angelina Germanotta, known professionally as Lady Gaga, is an American singer, songwriter, and actress. She is known for her unconventionality and provocative work as well as visual experimentation. Lady Gaga - Found on the album \'Joanne\'.","e_m_77.webp",6,1);

//Found
        this.questionCreator("New Zealand singer and songwriter Ella Marija Lani Yelich-O\'Connor is better known as?","Lorde","Kimbra","Charli XCX","Lana Del Rey","Lorde","Ella Marija Lani Yelich-O\'Connor, known professionally as Lorde, is a New Zealand singer, songwriter, and record producer. She holds both New Zealand and Croatian citizenship. As of 2017, Lorde has earned two Grammy Awards.","e_m_78.webp",6,1);

//Found
        this.questionCreator("What Disney film soundtrack peaked at number three on the Billboard 200 chart in March of 2017?","Beauty and the Beast","Coco","Cars Three","Ghost in the Shell","Beauty and the Beast","Beauty and the Beast is a live-action remake of Disney\'s 1991 animated film of the same name.","e_m_79.webp",6,1);

//Found
        this.questionCreator("Released on the film soundtrack \"Fifty Shades Darker\", who collaborated with Taylor Swift on the song \"I Don\'t Wanna Live Forever\"?","Zayn Malik","Shawn Mendes","Harry Styles","Justin Bieber","Zayn Malik","The song was written by Taylor Swift, Sam Dew and Jack Antonoff.","e_m_80.webp",6,1);

//Found
        this.questionCreator("Presented in January of 2018, who won the Grammy Award for Album of the Year?","24k - Bruno Mars","The Story of O.J. - Jay-Z","Humble - Kendrick Lamar","Redbone - Childish Gambino","24k - Bruno Mars","Twenty four thousand Magic is the third studio album by American singer and songwriter Bruno Mars.","e_m_81.webp",6,1);

//Found
        this.questionCreator("American singer and songwriter Alecia Beth Moore is better known as?","Pink","Shakira","Beyonce","Rihanna","Pink","Pink was originally a member of the girl group Choice.","e_m_82.webp",6,1);

//Found
        this.questionCreator("What TV show\'s soundtrack peaked at #26 on the Billboard 200 chart in June of 2018?","Thirteen Reasons Why Season two","The Good Place - Season one","Wild Wild Country - Season one","The Good Fight - Season two","Thirteen Reasons Why Season two","Thirteen Reasons Why - Season 2 - The TV show is based on the 2007 novel Thirteen Reasons Why by Jay Asher.","e_m_83.webp",6,1);

//Found
        this.questionCreator("Who won all six Grammy Awards for which he was nominated at the 60th Annual Grammy Awards?","Bruno Mars","Rihanna","Jay-Z","Ed Sheeran","Bruno Mars","As of 2018, Mars has sold over 130 million records worldwide.","e_m_84.webp",6,1);

//Found
        this.questionCreator("With what song did Britney Spears make her breakthrough in 1998?","(Hit Me) Baby One More Time","fOoops, I Did it Again","Circus","Toxic","(Hit Me) Baby One More Time","Britney Jean Spears (born December 2, 1981) is an American singer, dancer, and actress. Born in McComb, Mississippi, and raised in Kentwood, Louisiana, she performed acting roles in stage productions and television shows as a child, before signing with Jive Recordsin 1997.","e_m_85.webp",6,1);

//Found
        this.questionCreator("Which Canadian singer-songwriter was \"Complicated\" in 2002?","Avril Lavigne","Shania Twain","Joni Mitchell","Sarah McLachlan","Avril Lavigne","“Complicated” song is From Avril Lavigne\'s album \'Let Go\'.","e_m_86.webp",6,1);

//Found
        this.questionCreator(": What popular song by Shakira hit the number #1 spot in over 50 different countries?","Hips Don't Lie","Hey You","How Do You Do","Higher Ground","Hips Don't Lie","Shakira created this hit song in 2006 with the help of another musical performer named Wyclef Jean. \"Hips Don\'t Lie\" was first released on Shakira\'s album \"Oral Fixation\".","e_m_87.webp",6,1);

//Found
        this.questionCreator("Linkin Park teamed up with which Rap star to record the song \"Numb/Encore\"?","Jay Z","Snoop Dogg","Ludacris","Nelly","Jay Z","Numb/Encore is from the 2004 album Collision Course.","e_m_88.webp",6,1);

//Found
        this.questionCreator("Who conducted and produced the charity single \"We Are the World\"?","Quincy Jones","Michael Jackson","Lionel Ritchie","Bruce Springsteen","Quincy Jones","The song was written by Michael Jackson and Lionel Richie.","e_m_89.webp",6,1);

//Found
        this.questionCreator("Katy Perry has been announced as one of the live performers for the 2014 Grammy Awards. Additionally, she has been nominated for Song of the Year. Which song?","Roar","Dark Horse","unconditionally","Part of me","Roar","Roar can be found on Katy\'s album, Prism.","e_m_90.webp",6,1);

//Found
        this.questionCreator("Co-written and produced by Oren Yoel, who recorded the hit ballad \"Adore You\"?","Miley Cyrus","Taylor Swift","Beyonce Knowles","Selena Gomez","Miley Cyrus","The song “Adore you” can be found on Cyrus\' number-one hit album \'Bangerz\'.","e_m_91.webp",6,1);

//Found
        this.questionCreator("\"Royals\" has been nominated for Song of the Year at the upcoming 2014 Grammy Awards. Who recorded the song?","Lorde","Ariana Grande","Ellie Goulding","Lana Del Rey","Lorde","Royals song can be found on Lorde\'s debut studio album, \'Pure Heroine\'.","e_m_92.webp",6,1);

//Found
        this.questionCreator("Riding on the success of her pop hit single \"Replay,\" who recently completed her first \"Swag It Out\" concert tour?","Zendaya","Debby Ryan","Stefanie Scott","Bella Thorne","Zendaya","As of January 2014, \'Replay\' has been certified gold for selling 500,000 copies in the United States.","e_m_93.webp",6,1);

//Found
        this.questionCreator("Which of the following hit songs was written by Jamie Scott, Caolan Dooley, John Ryan, and the British-Irish boy band, One Direction?","Story of my Life","Hold On, We're Going Home","Stay The Night","Drunk in Love","Story of my Life","Released by One Direction, Story of my Life is the fifth track on the album \'Now 49\'.","e_m_94.webp",6,1);

//Found
        this.questionCreator("Eminem and which star have a Billboard 100 hit with the song \"The Monster\"?","Rihanna","Kesha","Lady Gaga","Miley Cyrus","Rihanna","\'The Monster\' marks the fourth collaboration among Eminem and Rihanna.","e_m_95.webp",6,1);

//Found
        this.questionCreator("Which of the following songs was recorded by A Great Big World and Christina Aguilera?","Say Something","Counting Stars","White Walls","Let It Go","Say Something","The song was originally released in 2011 without Christina Aguilera.","e_m_96.webp",6,1);

//Found
        this.questionCreator("Slated to be released on January 1st, 2014, but delayed until March, which pop icon will release the album \"Cheek to Cheek\" with jazz singer Tony Bennett?","Lady Gaga","Madonna","Britney Spears","Katy Perry","Lady Gaga","Cheek to cheek album is Lady Gaga\'s second collaboration with Tony, the first is a duet of song \'The Lady is a Tramp\'.","e_m_97.webp",6,1);

//Found
        this.questionCreator("\"MB... all around the world... Beautiful girl, girl, girl. Uh, girl.\" First line of what Justin Bieber song?","All Around the World","The Way","Crash","Miss You More","All Around the World","This song appears on the album All Around the World, which was released in 2013.","e_m_98.webp",6,1);

//Found
        this.questionCreator("The songs \"Now\", \"Grow\" and \"Last Hope\" can be found on which of the following albums?","Paramore","Right Place Right Time","Willpower","Save Rock and Roll","Paramore","The album was put out by the pop band Paramore.","e_m_99.webp",6,1);

//Found
        this.questionCreator("Who sings with Will.i.am on his song #thatpower?","Justin Bieber","Dido","Jessica Sanchez","Wing","Justin Bieber","The song was released in April, 2013.","e_m_100.webp",6,1);

//Found
        this.questionCreator("What was the name of the album that Jessica Sanchez released in 2013?","Me, You and the Music","The Hands That Thieve","Life on a Rock","Wake Up","Me, You and the Music","This was Jessica\'s first studio album.","e_m_101.webp",6,1);

//Found
        this.questionCreator("Who recorded the song \"Heart Attack\" in 2013?","Demi Lovato","Bad Rabbits","Little Mix","Marianas Trench","Demi Lovato","The song appears on the album Demi.","e_m_102.webp",6,1);

//Found
        this.questionCreator("In 1999, who had a number one hit on the Billboard Hot 100 with \"Genie In A Bottle\"?","Christina Aguilera","Christina Perri","Rihanna","Celine Dion","Christina Aguilera","\"Genie In A Bottle \" was the only number one in the 09s by Christina Aguilera. The others all had number ones on the Hot 100 in 1999: \"Have You Ever\" (Brandy); \"Believe\" (Cher); and \"Angel Of Mine\" (Monica).","e_m_103.webp",6,1);

//Found
        this.questionCreator("Which artist recorded the song \"The Remedy?\"","Jason Mraz","Jesson Mraz","Jason Mars","Jason Maz","Jason Mraz","\"The Remedy\" was from Jason\'s first album, \"Waiting for my Rocket to Come.\" This was the first single released from this album.","e_m_104.webp",6,1);


    }

    private void addMusicModerate()
    {
        //check

//Found
        this.questionCreator("In 1972, who took his Burning Love into the charts?","Elvis Presley","Melina Curtis","David Parker","Caitlyn Swanson","Elvis Presley","\"Burning Love\" is a song written by Dennis Linde and originally recorded by country soul artist Arthur Alexander, who included it on his 1972 self-titled album. It was soon covered and brought to fame by Elvis Presley, becoming his biggest hit single in the United States since \"Suspicious Minds\" in 1969 and his last Top 10 hit in the American Hot 100 or pop charts.","m_m_1.webp",6,2);

//Found
        this.questionCreator("In 1988 what British singer had a hit tune \"Groovy Kind of Love\"?","Phil Collins","Denisse Johnson","Uriah Andrews","Annabelle Cook","Phil Collins","The song title was an early use of the then-new slang word \"groovy\". Wine, who was 17 years old when she wrote the song, said, \"Carole came up with \"Groovy kinda… groovy kinda… groovy…\" and we\'re all just saying, \'Kinda groovy, kinda groovy, kinda…\' and I don\'t exactly know who came up with \"Love\", but it was \'Groovy kind of love\'.","m_m_2.webp",6,2);

//Found
        this.questionCreator("What three words came just before \"land that I love\" in the 1938 Irving Berlin song?","God bless America","Into You","The Hype","Stressed Out","God bless America","\"God Bless America\" is an American patriotic song written by Irving Berlin during World War I in 1918 and revised by him in the run up to World War II in 1938. The later version has notably been recorded by Kate Smith, becoming her signature song.","m_m_3.webp",6,2);

//Found
        this.questionCreator("What female singer achieved a top ten hit with Another Sad Love Song?","Toni Braxton","Destiney Sullivan","Anthony Cisneros","Julien Calderon","Toni Braxton","\"Another Sad Love Song\" is the first official single from Toni Braxton\'s self-titled debut album, Toni Braxton (1993). After the success of \"Love Shoulda Brought You Home\", Braxton followed up with this single.","m_m_4.webp",6,2);

//Found
        this.questionCreator("Who was performing the song Love Sick at a Grammy Awards when it was crashed by a dancing wacko?","Bob Dylan","Erik Cooley","Yareli Mcdonald","Odin Harrison","Bob Dylan","\"Love Sick\" is a song by Bob Dylan, recorded in January 1997 and released as the opening track on his 30th studio album Time Out of Mind in September of that year.","m_m_5.webp",6,2);

//Found
        this.questionCreator("In its tribute to AIDS-victim Freddie Mercury, what band included Too Much Love Will Kill You on its Made in Heaven album?","Queen","The Lumineers","Pearl Jam","Gorillaz","Queen","\"Too Much Love Will Kill You\" is a song written by British guitarist Brian May of Queen, Frank Musker, and Elizabeth Lamers. The song reflected the breakdown of May\'s first marriage and attraction to his future wife, Anita Dobson.","m_m_6.webp",6,2);

//Found
        this.questionCreator("I Don\'t Know How To Love Him comes from what show?","Jesus Christ Superstar","Superstars","Drop the mic","Singing Bee","Jesus Christ Superstar","\"I Don\'t Know How to Love Him\" is a song from the 1970 rock opera Jesus Christ Superstar written by Andrew Lloyd Webber (music) and Tim Rice (lyrics), a torch ballad sung by the character of Mary Magdalene.","m_m_7.webp",6,2);

//Found
        this.questionCreator("What kind of Bites provided Def Leppard with a number one single hit?","Love","Angry","Jealous","Insecure","Love","Love Bites (Def Leppard song) \"Love Bites\" is a power ballad recorded by the English rock band Def Leppard in 1987 on the album Hysteria. It is Def Leppard\'s only number-one single on the Billboard Hot 100 to date.","m_m_8.webp",6,2);

//Found
        this.questionCreator("The Beatles\' All You Need Is Love from the 60s began with what?","French National Anthem","Philippine National Anthem","U.S.A Natinnal Anthem","Africa National Anthem","French National Anthem","\"All You Need Is Love\" is a song by the English rock band the Beatles that was released as a non-album single in July 1967. It was written by John Lennon and credited to Lennon–McCartney.","m_m_9.webp",6,2);

//Found
        this.questionCreator("What is the only track with a foreign title on Celine Dion\'s Let\'s Talk About Love album?","Amar Haciendo El Amor","Amar Haciendo Ell","Amor Haciendo Ell","Amor","Amar Haciendo El Amor","Let\'s Talk About Love is the fifth English-language studio album by Canadian singer Celine Dion, released on 14 November 1997, by Columbia/Epic Records. The follow-up to her commercially successful album Falling into You (1996), Let\'s Talk About Love showed a further progression of Dion\'s music.","m_m_10.webp",6,2);

//Found
        this.questionCreator("What was Bon Jovi\'s first number one hit?","You Give Love A Bad Name","Better","Yeah right","In My Mind","You Give Love A Bad Name","The first two singles from the album, \"You Give Love a Bad Name\" and \"Livin\' on a Prayer\", both reached number one on the Billboard Hot 100 chart. The follow-up album to Slippery When Wet was New Jersey (1988), which shared similar global success.","m_m_11.webp",6,2);

//Found
        this.questionCreator("What was the first single to be No 1 for 14 weeks?","I Will Always Love you","Jumpsuit","Reborn","Dance to This","I Will Always Love you","The single spent 14 weeks at the top of the US Billboard Hot 100. It\'s the first time Billboard did not rank a new number one single.","m_m_12.webp",6,2);

//Found
        this.questionCreator("What superstar had an early 90s hit with I will Always Love You?","Whitney Houston","Natalia Mcgee","Hannah Roth","Quintin Rodgers","Whitney Houston","Whitney Houston recorded her version of the song for the 1992 film The Bodyguard. Her single spent 14 weeks at number one on the Billboard Hot 100 chart making it one of the best-selling singles of all time.","m_m_13.webp",6,2);

//Found
        this.questionCreator("What was the name of the band that sang I Want to Know What Love Is?","Foreigner","Interpol","MGMT","The Who","Foreigner","Foreigner is an English-American rock band, originally formed in New York City in 1976 by veteran English musician and ex–Spooky Tooth member Mick Jones, and fellow Briton and ex–King Crimson member Ian McDonald along with American vocalist Lou Gramm.","m_m_14.webp",6,2);

//Found
        this.questionCreator("Who sang Saving All My Love For You?","Whitney Houston","Aisha Haynes","Lillianna Anderson","Leanna Armstrong","Whitney Houston","Whitney Elizabeth Houston was an American singer and actress. She was cited as the most awarded female artist of all time by Guinness World Records and remains one of the best-selling music artists of all time with 200 million records sold worldwide","m_m_15.webp",6,2);

//Found
        this.questionCreator("What was Mariah Carey\'s first British top ten hit?","Vision Of Love","The Scientist","Kiss and Make Up","Leave the City","Vision Of Love","\"Vision of Love\" is a song by American singer and songwriter Mariah Carey. Written by Carey and Ben Margulies, \"Vision of Love\" was released as the lead single from Carey\'s debut studio album Mariah Carey (1990), on May 15, 1990. After being featured on Carey\'s demo tape for Columbia, the song was re-recorded and produced by Rhett Lawrence and Narada Michael Walden.","m_m_16.webp",6,2);

//Found
        this.questionCreator("Which superstar singer had a 1980 hit with Woman In Love?","Barbra Streisand","Tristen Vang","Hallie Krause","Blake Arellano","Barbra Streisand","\"Woman in Love\" is a song performed by Barbra Streisand and taken from her 1980 album, Guilty. The song was written by Barry and Robin Gibb of the Bee Gees, who received the 1980 Ivor Novello award for Best Song Musically and Lyrically.","m_m_17.webp",6,2);

//Found
        this.questionCreator("Who made the charts with an old Supremes song, You Can\'t Hurry Love?","Phil Collins","Rolando Collins","Emery Maxwell","Maddison Zhang","Phil Collins","It was released and peaked in late summer/early autumn in 1966. Sixteen years later, it would become a number-one hit in the UK when Phil Collins re-recorded the song","m_m_18.webp",6,2);

//Found
        this.questionCreator("Paul Mauriat had a big 60s instrumental hit. What was it?","Love is Blue","Feel It Still","Come as You Are","High Hopes","Love is Blue","It has since been recorded by many other musicians, most notably French orchestra leader Paul Mauriat, whose familiar instrumental version (recorded in late 1967) became the only number-one hit by a French lead artist to top the Billboard Hot 100 in America.","m_m_19.webp",6,2);

//Found
        this.questionCreator("What artist took the song Part-Time Lover to No.1 in the 1950s?","Stevie Wonder","Cassie Paul","Osvaldo Riley","Kaylyn Sloan","Stevie Wonder","\"Part-Time Lover\" is a single by Stevie Wonder, from his 1985 album In Square Circle. The song reached number 1 on the Billboard Hot 100, R&B, dance, and adult contemporary charts. The song\'s simultaneous chart successes made Wonder the first artist to score a number-one hit on four different Billboard charts. The song was also released as a special 12\" version.","m_m_20.webp",6,2);

//Found
        this.questionCreator("Rhythm and Blues musician and vocalist Ellas Otha Bates was better known by what name?","Bo Diddley","Diddle","Big Diddle","Dids","Bo Diddley","Ellas McDaniel (born Ellas Otha Bates, December 30, 1928 – June 2, 2008), known as Bo Diddley, was an American singer, guitarist, songwriter and music producer who played a key role in the transition from the blues to rock and roll.","m_m_21.webp",6,2);

//Found
        this.questionCreator("What body part is the title of Florence and the Machine\'s debut album, released in 2009?","Lungs","Head","Nose","Mouth","Lungs","Lungs is the debut studio album by English indie rock band Florence and the Machine, released on 3 July 2009 on Island Records.","m_m_22.webp",6,2);

//Found
        this.questionCreator("Which band released a 1999 album entitled \'The Man Who\'?","Travis","The White Stripes","System of a Down","M83","Travis","The Man Who is the second studio album by Scottish rock band Travis. The album was released on 24 May 1999 through Independiente. It saw a change in musical direction for the band, moving away from the rockier tone of their debut album Good Feeling.","m_m_23.webp",6,2);

//Found
        this.questionCreator("Who released a 2009 album entitled \'The Element of Freedom\'?","Alicia keys","Kaitlyn Hooper","Calvin Cobb","Terrell Goodwin","Alicia keys","The Element of Freedom is the fourth studio album by American singer and songwriter Alicia Keys, released on December 11, 2009, by J Records. Recording sessions for the album took place during May to September 2009 at The Oven Studios in Long Island, New York. Production was primarily handled by Keys, Kerry Brothers, Jr., and Jeff Bhasker.","m_m_24.webp",6,2);

//Found
        this.questionCreator("A purfle is an ornamental border, typically inlaid on which musical instrument?","Violin","Drum","Tambourine","Guitar","Violin","The violin, also known informally as a fiddle, is a wooden string instrument in the violin family. Most violins have a hollow wooden body. It is the smallest and highest-pitched instrument in the family in regular use.","m_m_25.webp",6,2);

//Found
        this.questionCreator("Which English rock band released and album in March 2011 entitled \'Build a Rocket Boys\'?","Elbow","Coldplay","Kings of Leon","The Police","Elbow","Build a Rocket Boys! is the fifth studio album by English rock band Elbow, released on 4 March 2011 in the UK. Coinciding with the UK release, the album was available digitally in the United States on 8 March and released in the physical format on 12 April.","m_m_26.webp",6,2);

//Found
        this.questionCreator("Which band\'s 2007 album is entitled \'Send Away the Tigers\'?","Manic Street Preachers","A-ha","R.E.M.","Talking Heads","Manic Street Preachers","Send Away the Tigers is the eighth studio album by Welsh alternative rock band Manic Street Preachers, released on 7 May 2007 by record label Columbia. It reached number 2 on the UK Albums Chart. The album was due to hit number 1 in the charts, but at the end of the week 690 copies separated the Manics from the Arctic Monkeys on the top spot","m_m_27.webp",6,2);

//Found
        this.questionCreator("\'Fanfare For The Common Man\' is a work by which 20th Century composer?","Aaran Copland","Jonathon Sheppard","Rodrigo Bradford","Iyana Carlson","Aaran Copland","Fanfare for the Common Man is a musical work by the American composer Aaron Copland. It was written in 1942 for the Cincinnati Symphony Orchestra under conductor Eugene Goossens and was inspired in part by a speech made earlier that year by United States Vice President Henry A. Wallace, in which Wallace proclaimed the dawning of the \"Century of the Common Man\".","m_m_28.webp",6,2);

//Found
        this.questionCreator("Which Irish musician released a 1988 album entitled \'Watermark\'?","Enya","Lennon","Juliet","Crawford","Enya","Watermark is the second studio album by Irish singer, songwriter and musician Enya, released on 19 September 1988 by Warner Music.","m_m_29.webp",6,2);

//Found
        this.questionCreator("Which Scottish photographer took the famous 1969 photo of The Beatles on a zebra crossing on Abbey Road?","Iain MacMillan","Rayan Cooper","Ingrid Huang","Ariana Spears","Iain MacMillan","Iain Stewart Macmillan (20 October 1938 – 8 May 2006) was the Scottish photographer famous for taking the cover photograph for The Beatles\' album Abbey Road in 1969.","m_m_30.webp",6,2);

//Found
        this.questionCreator("Which group released an album in 1986 entitled \'The Queen is Dead\'?","The Smiths","Maroon 5","Paramore","The Kooks","The Smiths","The Queen Is Dead is the third studio album by English rock band the Smiths. It was released on 16 June 1986 in the United Kingdom by Rough Trade Records and released in the United States on 23 June 1986 through Sire Records.","m_m_31.webp",6,2);

//Found
        this.questionCreator("The Creamfields Dance Music Festival takes place annually in which English county?","Cheshire","France","Paris","Africa","Cheshire","Creamfields is back for 2018 with a fully immersive 360-degree arena and a host of international dance music talent. This four-day event take place in the Cheshire countryside, and this year they welcome back Pete Tong and Andy C to host stages.","m_m_32.webp",6,2);

//Found
        this.questionCreator("British singer and actor Terence Nelhams-Wright was better known by what name?","Adam Faith","Abbie Pace","Lilah Petersen","Mira Larson","Adam Faith","Terence Nelhams-Wright (23 June 1940 – 8 March 2003), known as Adam Faith, was a British teen idol, singer, actor and financial journalist. He was one of the most charted acts of the 1960s.","m_m_33.webp",6,2);

//Found
        this.questionCreator("Who composed the \'Trout Quintet\'?","Franz Schubert","Jessie Tucker","Julissa Byrd","Emily Hogan","Franz Schubert","The Trout Quintet (Forellenquintett) is the popular name for the Piano Quintet in A major, D. 667, by Franz Schubert. The piano quintet was composed in 1819, when he was 22 years old; it was not published, however, until 1829, a year after his death.","m_m_34.webp",6,2);

//Found
        this.questionCreator("\'The Dream of Gerontius\' is the 1900 work of which English composer?","Edward Elgar","Jase Ware","Alan Kaiser","Parker Werner","Edward Elgar","The Dream of Gerontius. The Dream of Gerontius, Op. 38, is a work for voices and orchestra in two parts composed by Edward Elgar in 1900, to text from the poem by John Henry Newman. It relates the journey of a pious man\'s soul from his deathbed to his judgment before God and settling into Purgatory.","m_m_35.webp",6,2);

//Found
        this.questionCreator("Which British band released a 2006 album entitled \'Beautiful World\'?","Take That","Youngblood","Pressure","Natural","Take That","Beautiful World is the fourth studio album from British pop group Take That. The album was released in November 2006, and was the band\'s first studio album in 11 years;","m_m_36.webp",6,2);

//Found
        this.questionCreator("Who was the last member of The Beatles to marry?","Paul McCartney","Leonidas Carey","Wendy Villanueva","Parker Giles","Paul McCartney","When Starr married Maureen Cox in 1965, Beatles manager Brian Epstein was best man, with Starr\'s stepfather Harry Graves and fellow Beatle George Harrison as witnesses.","m_m_37.webp",6,2);

//Found
        this.questionCreator("The Greek national anthem has a total of how many verses (or stanzas)?","158","134","90","50","158","The national anthem was originally written in 158 verses but it is now recognized to be only two verses long.","m_m_38.webp",6,2);

//Found
        this.questionCreator("On a standard violin, to which notes are the four strings normally tuned?","G D A E","G B A E","G Eb A E","G G# A E","G D A E","The violin has four strings which are tuned in fifths. The scientific pitch of the strings from lowest to highest is: G3, D4, A4, and E5. The violin is tuned in perfect fifths - each string is tuned an interval of a perfect fifth from the string (or strings) next to it.","m_m_39.webp",6,2);

//Found
        this.questionCreator("Which legendary folk singer played his first concert in Vietnam, in April 2011?","Bob Dylan","Ezra Medina","Hudson English","Charlee Orr","Bob Dylan","Legendary folk singer Bob Dylan, whose songs became anthems of the 1960s anti-Vietnam war movement, has played his first concert in the Communist country. Dylan played a concert in Ho Chi Minh City - formerly Saigon - on Sunday evening.","m_m_40.webp",6,2);

//Found
        this.questionCreator("What was Elvis Presley\'s first number one hit single, released in January 1956?","Heartbreak Hotel","Planters Inn","Four seasons","Brewery Gluch Inn","Heartbreak Hotel","February 1956. As \"Heartbreak Hotel\" makes its climb up the charts on its way to #1, \"I Forgot to Remember to Forget\" b/w \"Mystery Train,\" Elvis\' fifth and last single to be released on the Sun label, hits #1 on Billboard\'s national country singles chart. His first #1 hit on a national chart.","m_m_41.webp",6,2);

//Found
        this.questionCreator("Which band had a 1959 hit with the song \'I Only Have Eyes For You\'?","The Flamingos","Radiohead","The Beach Boys","Imagine Dragons","The Flamingos","This song was included on The Flamingos\' debut album Flamingo Serenade. The version by the Flamingos features a prominent reverb effect, creating a dreamy ambience. This version peaked at number 11 on the US Billboard Hot 100 chart and number 3 on the Billboard Hot R&B/Hip-Hop Songs chart.","m_m_42.webp",6,2);

//Found
        this.questionCreator("Which record label is named after founders Herb Alpert and Jerry Moss?","A&M Records","Columbia","After Math","Rostrum","A&M Records","Co-founder (with Herb Alpert) of A&M Records label. His horse, Giacomo, won the 2005 Kentucky Derby. Co-founder (with Herb Alpert) of Carnival Records. With partner Herb Alpert, founded the record label Almo Sounds in 1994.","m_m_43.webp",6,2);

//Found
        this.questionCreator("Which British composer based an opera on Henry James novella \'The Turn of the Screw\'?","Benjamin Britten","Jennifer Thompson","Ethan Rowland","Ashlynn Shepherd","Benjamin Britten","The Turn of the Screw is a 20th-century English chamber opera composed by Benjamin Britten with a libretto by Myfanwy Piper, \"wife of the artist John Piper, who had been a friend of the composer since 1935 and had provided designs for several of the operas\".","m_m_44.webp",6,2);

//Found
        this.questionCreator("What is the English title of the classical piece of music \'An Der Schonen Blauen Donau\'?","The Blue Danube","Pumped Up Kicks","Californication","Let It Happen","The Blue Danube","\"The Blue Danube\" is the common English title of \"An der schönen blauen Donau\", Op. 314 (German for \"By the Beautiful Blue Danube\"), a waltz by the Austrian composer Johann Strauss II, composed in 1866.","m_m_45.webp",6,2);

//Found
        this.questionCreator("Herb Alpert and Jerry Moss founded which American record label in 1962?","A & M Records","Universal Music","Sony Music","BMG","A & M Records","A&M Records was an American record label founded as an independent company by Herb Alpert and Jerry Moss in 1962.","m_m_46.webp",6,2);

//Found
        this.questionCreator("Which band released a 1997 album entitled \'White on Blonde\'?","Texas","Kygo","Blur","Pixies","Texas","White on Blonde is the fourth studio album by Scottish rock band Texas, released by Mercury Records on 3 February 1997. The album was the band\'s first number one, and became their biggest seller. It has been certified 6x Platinum by the British Phonographic Industry for shipments of over 1.8 million copies, and has sold 1.65 million as of January 2017.","m_m_47.webp",6,2);

//Found
        this.questionCreator("Entertainer Georgios Kyriacos Panayiotou is better known by what name?","George Michael","Jessie Roberson","Bryant Bradshaw","Guadalupe Sullivan","George Michael","WHAM! Gone Too Soon, British-Cypriot Singer George Michael (Georgios Panayiotou), 53, Dead: Had Faith, Artistry, Talent. He was known to millions of fans by his stage name, but George Michael was really Georgios Kyriacos Panayiotou, born to a Greek-Cypriot father living in London and an English mother.","m_m_48.webp",6,2);

//Found
        this.questionCreator("British singer Stuart Goddard is better known by what name?","Adam Ant","Marquise Franklin","Brogan Rivas","Ismael Beck","Adam Ant","Adam Ant (born Stuart Leslie Goddard; 3 November 1954) is an English singer and musician. He gained popularity as the lead singer of new wave group Adam and the Ants and later as a solo artist, scoring 10 UK top ten hits from 1980 to 1983, including three UK No. 1 singles.","m_m_49.webp",6,2);

//Found
        this.questionCreator("\'Dangerously In Love\' and \'Marquise Franklin\'Day\' are albums by which US singer?","Beyonce Knowles","Brogan Rivas","Rebecca Bennett","Carter Mcdonald","Beyonce Knowles","Dangerously in Love is the debut solo studio album by American singer Beyoncé. It was released on June 23, 2003 through Columbia Records and Music World Entertainment , B\'Day is the second solo studio album by American singer Beyoncé. It was released to coincide with her twenty-fifth birthday on September 4, 2006, in various countries and a day later in the United States by Columbia Records, Music World Entertainment, and Sony Urban Music.","m_m_50.webp",6,2);

//Found
        this.questionCreator("Who was best man at ex-Beatle Paul McCartney\'s wedding to Nancy Shevell?","Mike McCartney","Shirley Bautista","Aryanna Riggs","Roberto Zavala","Mike McCartney","Paul McCartney is best man at his brother Michael\'s wedding. Paul McCartney was the best man at his brother Michael\'s wedding on this day. Mike McCartney married Angela Fishwick, a hair stylist, at St Bridget\'s Parish Church in the small village of Carrog in Merionethshire, north Wales.","m_m_51.webp",6,2);

//Found
        this.questionCreator("Which two English footballers had a hit single with \'Diamond Lights\' in 1987?","Glenn Hoddle and Chris Waddle","Eleanor Ashley and Brent Eaton","Johan Lang and Rayna Gray","Eden Silva and Gael Lloyd","Glenn Hoddle and Chris Waddle","\"Diamond Lights\" is a 1987 single by footballers Glenn Hoddle and Chris Waddle, released under their first names, \"Glenn & Chris\".","m_m_52.webp",6,2);

//Found
        this.questionCreator("Which German composer wrote the oratorios \'Elijah\' and \'St Paul\'?","Mendelssohn","Waters","Collier","Shaw","Mendelssohn","Jakob Ludwig Felix Mendelssohn Bartholdy, born and widely known as Felix Mendelssohn, was a German composer, pianist, organist and conductor of the early romantic period. Mendelssohn wrote symphonies, concertos, oratorios, piano music and chamber music.","m_m_53.webp",6,2);

//Found
        this.questionCreator("Which band released a 1979 album entitled \'The Great Rock and Roll Swindle\'?","Sex Pistols","Death","Calm","Wake up","Sex Pistols","The Great Rock \'n\' Roll Swindle is the soundtrack album of the film of the same name by the Sex Pistols.","m_m_54.webp",6,2);

//Found
        this.questionCreator("Which songwriter and rapper\'s real name is Timothy Z Mosley?","Timbaland","Perry","Emerson","Molina","Timbaland","Timothy Zachary Mosley, better known by his stage name Timbaland, is an American record producer, rapper, singer, songwriter and DJ. Timbaland\'s first full credit production work was in 1996 on Ginuwine...the Bachelor for R&B singer Ginuwine.","m_m_55.webp",6,2);

//Found
        this.questionCreator("Which British dj and producer was the co-founder of Allido Records?","Mark Ronson","Chris Stanton","Martha Yates","Yaritza Travis","Mark Ronson","Allido Records is a record label and production company. The company was started by DJ and producer Mark Ronson and Rich Kleiman, a television, internet and music businessman. The label got its name \"Allido\" from the Stevie Wonder song \"All I Do\".","m_m_56.webp",6,2);

//Found
        this.questionCreator("Which Welsh singer was born Gaynor Hopkins?","Bonnie Tyler","Elias Riley","Humberto Gray","Kingston Lozano","Bonnie Tyler","Bonnie Tyler (born Gaynor Hopkins; 8 June 1951) is a Welsh singer, known for her distinctive husky voice.","m_m_57.webp",6,2);

//Found
        this.questionCreator("What is the title of The Rolling Stones debut album, released in 1964?","The Rolling Stones","The Roll","The Air","The Sun","The Rolling Stones","The Rolling Stones is the debut album by the Rolling Stones, released by Decca Records in the UK on 16 April 1964. The American edition of the LP, with a slightly different track list, came out on London Records on 30 May 1964, subtitled England\'s Newest Hit Makers, which later became its official title.","m_m_58.webp",6,2);

//Found
        this.questionCreator("Mary and Jim were the names of the parents of which member of The Beatles?","Paul McCartney","Harold Ramsey","Haleigh Gonzalez","Gabriel Hancock","Paul McCartney","James McCartney (7 July 1902 – 18 March 1976) and Mary Patricia McCartney (born Mohan; 29 September 1909 – 31 October 1956) were the parents of musician, author and artist Paul McCartney of the Beatles and Wings, and of the photographer and musician Mike McCartney (better known professionally as Mike McGear), who worked with the comedy rock trio the Scaffold.","m_m_59.webp",6,2);

//Found
        this.questionCreator("Which song by Irish band U2 is a tribute to Martin Luther King Jr?","Pride","Love","Sick","Help","Pride","\"Pride (In the Name of Love)\" is a song by Irish rock band U2. The second track on the band\'s 1984 album, The Unforgettable Fire, it was released as the album\'s lead single in September 1984. Written about Martin Luther King Jr.","m_m_60.webp",6,2);

//Found
        this.questionCreator("\'Here Comes the Fuzz\' was the 2003 debut album of which English dj and musician?","Mark Ronson","Kyle Davidson","Myla Burke","Alexis Valentine","Mark Ronson","Here Comes the Fuzz. Here Comes the Fuzz is the debut studio album by British producer Mark Ronson. The album was released on 8 September 2003, led by the lead single, \"Ooh Wee\".","m_m_61.webp",6,2);

//Found
        this.questionCreator("Which music act closed the Pyramid Stage on Sunday night at Glastonbury in 2011?","Beyonce","Bryan","Cora","Mosley","Beyonce","US singer Beyonce made her debut at Glastonbury, bringing the three-day festival to a close She headlined the main Pyramid Stage, making her one of a handful of women to do so over the past 40 years. \'Beyonce played all her biggest hits and a medley of Destiny\'s Child songs and got a great reception from the crowd.","m_m_62.webp",6,2);

//Found
        this.questionCreator("Composer Jean Sibelius was born in which country?","Finland","America","Jamaica","Korea","Finland","Sibelius was born on 8 December 1865 in Hämeenlinna in the Grand Duchy of Finland, an autonomous part of the Russian Empire. He was the son of the Swedish-speaking medical doctor Christian Gustaf Sibelius and Maria Charlotta Sibelius née Borg.","m_m_63.webp",6,2);

//Found
        this.questionCreator("Which band released a 2007 album entitled \'Myths of the Near Future\'?","The Klaxons","Avicii","The Offspring","Led Zeppelin","The Klaxons","Myths of the Near Future is the Mercury Prize-winning debut album by English band Klaxons. It was released on 29 January 2007 through Polydor Records.","m_m_64.webp",6,2);

//Found
        this.questionCreator("Which singer was born Gloria Fowles in September 1948?","Gloria Gaynor","Kyla Hoffman","Delilah Tucker","Rodolfo Singleton","Gloria Gaynor","Gloria Gaynor. Gloria Gaynor (born Gloria Fowles 7 September 1949) is an American singer-songwriter born in Newark, New Jersey, USA.","m_m_65.webp",6,2);

//Found
        this.questionCreator("Singer Doug Trendle is better known by what name?","Buster Bloodvessel","Young","Big D","Drop D","Buster Bloodvessel","Douglas Trendle (born 6 September 1958), better known as Buster Bloodvessel, is an English singer and the frontman of the ska revival band Bad Manners. His stage name was taken from the bus conductor played by Ivor Cutler in the Beatles\' 1967 film Magical Mystery Tour.","m_m_66.webp",6,2);

//Found
        this.questionCreator("Harold and Louise were the parents of which member of The Beatles?","George Harrison","Pete Best","Jimmie Nicol","Normal Chapman","George Harrison","Harrison was born at 12 Arnold Grove in Wavertree, Liverpool, on 25 February 1943.He was the youngest of four children of Harold Hargreaves (or Hargrove) Harrison (1909–1978) and Louise.","m_m_67.webp",6,2);

//Found
        this.questionCreator("\'Der er et yndigtland\' is the national anthem of which country?","Denmark","Belize","Cuba","Angola","Denmark","\"Der er et yndigt land\", commonly translated into English as \"There is a lovely country\", is one of the national anthems of Denmark.","m_m_68.webp",6,2);

//Found
        this.questionCreator("Which Canadian rapper had Billboard hits with the songs \"No Long Talk\", \"Get It Together\" and \"Blem\" in 2017?","Drake","Madchild","Checkmate","Kardinal Offishall","Drake","Aubrey Drake Graham (born October 24, 1986) is a Canadian rapper, singer, songwriter, record producer, actor, and entrepreneur. He is one of the most popular entertainers in the world, and one of the best-selling music artists of the 21st century.","m_m_69.webp",6,2);

//Found
        this.questionCreator("On the Billboard Hot Rock Songs chart for March/April 2017, finish the title to this Weezer song - \"Feels Like ...\".","Summer","Winter","Rain","Love","Summer","Summer - The song was released as a single in March of 2017.","m_m_70.webp",6,2);

//Found
        this.questionCreator("Coinciding with what would have been his 74th birthday, which former Beatle's entire solo album collection was released in February of 2017?","George Harrison","Paul McCartney","John Lennon","Ringo Starr","George Harrison","George Harrison MBE was an English guitarist, singer-songwriter, and producer who achieved international fame as the lead guitarist of the Beatles. The boxed set is titled George Harrison - The Vinyl Collection.","m_m_71.webp",6,2);

//Found
        this.questionCreator("Finish the title to this 2018 Shawn Mendes hit song - \"In My ...\".","Blood","Backyard","Life","Car","Blood","The song chronicles Shawn's struggle with an anxiety disorder.","m_m_72.webp",6,2);

//Found
        this.questionCreator("Which Canadian rapper had Billboard hits with the songs \"Nice For What\", \"God's Plan\" and \"I'm Upset\" in 2018?","Drake","Checkmate","Madchild","Kardinal Offishall","Drake","Drake - As of 2018, Drake holds the record for most charted songs among solo artists in the history of the Billboard Hot 100 at 154.","m_m_73.webp",6,2);

//Found
        this.questionCreator("Released on her debut studio album, which American rapper had a hit with the song \"Be Careful\"?","Cardi B","Nicki Minaj","Queen Latifah","Lil\' Kim","Cardi B","Cardi B - Belcalis Marlenis Almanzar known professionally as Cardi B, is an American rapper. Born and raised in The Bronx, New York City","m_m_74.webp",6,2);

//Found
        this.questionCreator("Released on the album \"Simi\", who collaborated with Blocboy JB on the song \"Look Alive\"?","Drake","Zayn Malik","Harry Styles","Shawn Mendes","Drake","Look Alive peaked at number five on the Billboard Hot 100.","m_m_75.webp",6,2);

//Found
        this.questionCreator("Making its debut in April of 2018, the studio album \"Ye\" was released by which American rapper?","Kanye West","50 Cent","Jay-Z","Eminem","Kanye West","Kanye Omari West is an American rapper, singer, songwriter, record producer, entrepreneur and fashion designer.","m_m_76.webp",6,2);

//Found
        this.questionCreator("Which of these Elvis Presley songs was a number one BEFORE the others?","Hound Dog","It\'s Now or Never","All Shook Up","Stuck on You","Hound Dog","Elvis released his version of Hound Dog in July of 1956.","m_m_77.webp",6,2);

//Found
        this.questionCreator("Who originally played the drums with \"The Beatles\"?","Pete Best","Ringo Starr","George Harrison","Stuart Sutcliffe","Pete Best","Randolph Peter Best is an English musician, principally known as an original member and the first drummer of the Beatles, from 1960 to 1962.","m_m_78.webp",6,2);

//Found
        this.questionCreator("What is the correct name of this 2007 chart topping band?","Plain White T's","Plain White C\'s","Plain White B's","Plain White D's","Plain White T's","Plain White T's is best known for the number-one hit song 'Hey There Delilah'.","m_m_79.webp",6,2);

//Found
        this.questionCreator("Released in 2006, complete the title to this Cascada number one hit - \"Everytime We...\"?","Touch","Kiss","Smile","Meet","Touch","Everytime we touch song is from their debut album Everytime We Touch.","m_m_80.webp",6,2);

//Found
        this.questionCreator("Which American singer-songwriter won the 1994 Grammy for Record of the Year for with the song \"All I Wanna Do\"?","Sheryl Crow","Carly Simon","Madonna","Dolly Parton","Sheryl Crow","'All I Wanna Do' song peaked at number two on the Billboard Hot 100.","m_m_81.webp",6,2);

//Found
        this.questionCreator("Sisters Nicole and Natalie Appleton are members of which all-girl group?","All Saints","Sugababes","Girls Aloud","Atomic Kitten","All Saints","All Saints have sold over 12 million records","m_m_82.webp",6,2);

//Found
        this.questionCreator("The songs \"Ting a Ling\", \"Love Potion No. 9\" and \"Nip Sip\" were hits for which vocal group?","The Clovers","The Platters","The Dominoes","The Cadillacs","The Clovers","The Clovers were one of the most popular acts of the 1950s.","m_m_83.webp",6,2);

//Found
        this.questionCreator("Released in 1974, and found on his \"Fulfillingness' First Finale\" album, who recorded the funk song \"Boogie on Reggae Woman\"?","Stevie Wonder","Marvin Gaye","Al Wilson","Bob Marley","Stevie Wonder","Stevland Hardaway Morris, known by his stage name Stevie Wonder, is an American singer, songwriter, record producer, and multi-instrumentalist.","m_m_84.webp",6,2);

//Found
        this.questionCreator("As of 2013, Don Felder has released two solo albums. Airborne and...","Road to Forever","Fly Higher","Rhythms in the Night","Guitar Man","Road to Forever","Airborne was released in 1983 and Road to Forever in 2012.","m_m_85.webp",6,2);

//Found
        this.questionCreator("What Simon and Garfunkel hit song opens with the line \"Hello darkness my old friend\"?","Sound of Silence","The Boxer","Homeward Bound","Mrs. Robinson","Sound of Silence","Released in 1965, Sound of Silence was the duo\'s second most popular hit after the song \"Bridge Over Troubled Water\".","m_m_86.webp",6,2);

//Found
        this.questionCreator("Which of the following songs was not a hit for \"The Everly Brothers\"?","Trouble in Paradise","When Will I Be Loved","All I Have to Do Is Dream","Wake Up Little Susie","Trouble in Paradise","Trouble in Paradise, The Crests recorded 'Trouble in Paradise' in 1960.","m_m_87.webp",6,2);

//Found
        this.questionCreator("Recorded by Beyonce Knowles and found on her fifth studio album \"Beyonce,\" what single features an audio sample from the Space Shuttle Challenger disaster?","XO","Drunk in Love","Flawless","Pretty Hurts","XO","The audio sample's inclusion has been heavily criticized by the families of the lost crew and the media.","m_m_88.webp",6,2);

//Found
        this.questionCreator("Which song by Bruno Mars opens with the lyrics, \"I spend all my money on a big ol' fancy car for these bright-eyed honeys. Oh yeah, you know who you are.\"?","Young Girls","Gorillas","Moonshine","Treasure","Young Girls","Young Girls is from Mars' second studio album 'Unorthodox Jukebox'.","m_m_89.webp",6,2);

//Found
        this.questionCreator("What hit song by Bastille starts with the lyrics \"I was left to my own devices. Many days fell away with nothing to show.\"?","Pompeii","Of the Night","Laura Palmer","Things We Lost in the Fire","Pompeii","Pompeii has been nominated for British Single of the Year at the 2014 BRIT Awards.","m_m_90.webp",6,2);

//Found
        this.questionCreator("Peaking at number one in 22 countries to date, Avicii introduced which of the following songs for the first time at the Ultra Music Festival in Miami?","Wake Me Up","You Make Me","Hey Brother","We Write the Story","Wake Me Up","The song can be found on Avicii's debut studio album, 'True'.","m_m_91.webp",6,2);

//Found
        this.questionCreator("Recorded by American hip hop artist Kid Ink, which song features a hook and bridge by Chris Brown?","Show Me","Iz U Down","Money and the Power","Bad Ass","Show Me","On January 7, 2014, Kid Ink made his television debut performing \"Show Me\" on Conan.","m_m_92.webp",6,2);

//Found
        this.questionCreator("What is the name of the album released by Justin Timberlake in March 2013?","The 20/20 Experience","Goldenheart","Contrast","Vessel","The 20/20 Experience","Justin Randall Timberlake (born January 31, 1981) is an American singer-songwriter, actor, dancer, and record producer.","m_m_93.webp",6,2);

//Found
        this.questionCreator("Which band released an album titled \"Vessel\" in 2013?","Twenty-One Pilots","Blink one hundred eighty two","Savage Garden","Savages","Twenty-One Pilots","Twenty-One Pilots is a band formed in 2009 in Ohio.","m_m_94.webp",6,2);

//Found
        this.questionCreator("Which of these bands officially reformed in January 2013?","Black Flag","Green Day","Boston","Linkin Park","Black Flag","The band was originally formed in 1976. They broke up in 1986.","m_m_95.webp",6,2);

//Found
        this.questionCreator("Who recorded the album Long. Live.?","ASAP","Muse","Dawn Richard","Bad Religion","ASAP","The album was released in January of 2013.","m_m_96.webp",6,2);

//Found
        this.questionCreator("The phrase \"Unrepentant vagabond\" is the opening line of which of the following songs?","True North","Troublemaker","Stutter","All Fired Up","True North","The song appears on the album True North by Bad Religion.","m_m_97.webp",6,2);

//Found
        this.questionCreator("What is the first song on the album 2.0 by 98 Degrees?","Microphone","Without the Love","Rootless","Get To Me","Microphone","The album was released in 2013.","m_m_98.webp",6,2);

//Found
        this.questionCreator("Spending a total of seventeen weeks in the Billboard Top 40, which Roxette song was also on the soundtrack for the movie \"Pretty Woman\"?","It Must Have Been Love","It must love","Be love","It must be Love","It Must Have Been Love","Roxette consisted of Marie Fredriksson and Per Gessle. \"It Must Have Been Love (Christmas for the Broken Hearted)\" was on Swedish radio during 1987, however it was rewritten in 1990 (without the Christmas lyrics) and was included on the \"Pretty Woman\" soundtrack.","m_m_99.webp",6,2);

//Found
        this.questionCreator("In 1990, which American dance group had an international hit with the song \"Groove Is in the Heart\"?","Deee-Lite","The Power","Day Lite","Black Box","Deee-Lite","The New York City band Deee-Lite achieved international stardom in late 1990 with the single \"Groove Is in the Heart.\" 103. In 1994, Nicki French released the Billboard Top 100 smash \"Total Eclipse of the Heart\".","m_m_100.webp",6,2);

//Found
        this.questionCreator("\"All you need is your own imagination/ so use it that\'s what it\'s for.\" Who sang these lyrics?","Madonna","Beyonce","Celine Dion","Whitney Houston","Madonna","Madonna's \"Vogue\" shot to number one in the UK singles chart in April 1990, commanding the top spot for four weeks. Along with Madonna, all of the other artists listed also reached the coveted number one spot that year.","m_m_101.webp",6,2);
    }

    private void addMusicDifficult()
    {
        //check

//Found
        this.questionCreator("\'Lofsongur\' is the national anthem of which country?","Iceland","Finland","Australia","Dubai","Iceland","\"Lofsöngur\", also known as \"Ó Guð vors lands\", is the national anthem of Iceland. Sveinbjörn Sveinbjörnsson composed the music, while the lyrics were authored by Matthías Jochumsson. This was adopted as the national anthem in 1944, when the country voted to end its personal union with Denmark and become a republic.","d_m_1.webp",6,3);

//Found
        this.questionCreator("Melodiya was the state-run record label of which country?","USSR","USE","USA","URS","USSR","Melodiya, is a Russian (formerly Soviet) record label. It was the state-owned major record company/label of the Soviet Union.","d_m_2.webp",6,3);

//Found
        this.questionCreator("\'Hen Wlad FY Nhadau\' is the national anthem of which European country?","Wales","Philippines","North Korea","South Korea","Wales","\"Hen Wlad Fy Nhadau\" is the national anthem of Wales.","d_m_3.webp",6,3);

//Found
        this.questionCreator("Aulophobia is the irrational fear of which musical instrument?","Flute","Drums","Tambourine","Guitar","Flute","Also known as kakorrhaphiophobia, this phobia is usually caused by an intense negative experience from the past. Aulophobia: It is an abnormal and unreasonable fear of flutes and other woodwind instruments. Aurophobia: It is an irrational or unusual fear of gold or golden things/objects.","d_m_4.webp",6,3);

//Found
        this.questionCreator("What is the highest range of the male singing voice?","Countertenor","Tenor","Baritone","Bass","Countertenor","An approximate Countertenor Vocal Range would be from a G note below the middle C (G3) to a high F one octave above the middle C (F5). Be sure to support your voice well with your breath so as to get a more accurate representation of the range in your voice.","d_m_5.webp",6,3);

//Found
        this.questionCreator("The song \'Send in the Clowns\' is from which musical?","A Little Night Music","See You Again","Maybe It's Time","Smithereens","A Little Night Music","\"Send in the Clowns\" is a song by Stephen Sondheim from the 1973 musical A Little Night Music, an adaptation of Ingmar Bergman\'s film Smiles of a Summer Night. It is a ballad from Act II in which the character Desirée reflects on the ironies and disappointments of her life.","d_m_6.webp",6,3);

//Found
        this.questionCreator("\'The Abduction from the Seraglio\' (Il Seraglio) is an opera by which composer?","Wolfgang Amadeus Mozart","Tyler, the Creator","Bradley Cooper","Twenty One Pilots","Wolfgang Amadeus Mozart","Wolfgang Amadeus Mozart, baptised as Johannes Chrysostomus Wolfgangus Theophilus Mozart, was a prolific and influential composer of the classical era. Born in Salzburg, Mozart showed prodigious ability from his earliest childhood.","d_m_7.webp",6,3);

//Found
        this.questionCreator("Two members of which group were the only people to perform on both Band Aid charity singles?","Bananarama","Silk City","Frank Ocean","Drake","Bananarama","Phil Collins (Genesis and solo artist) Chris Cross (Ultravox) Simon Crowe (The Boomtown Rats) Sarah Dallin (Bananarama)","d_m_8.webp",6,3);

//Found
        this.questionCreator("What was the name of the band, featuring members of Thin Lizzy and the Sex Pistols, which recorded a 1979 song called \'A Merry Jingle\'?","The Greedies","Kendrick Lamar","Twenty One Pilots","Pabllo Vittar","The Greedies","One-off project by Thin Lizzy and 2 Sex Pistols-members. Their only release was a Christmas single called \"A Merry Jingle\", a medley of \"Jingle Bells\" and \"We Wish You A Merry Christmas\".","d_m_9.webp",6,3);

//Found
        this.questionCreator("Which English singer/songwriter/musician wrote the Tremeloes hit \'Here Comes My Baby\' at the age of eighteen?","Cat Stevens","Kamden Walls","Osvaldo Krueger","Leonardo Day","Cat Stevens","Yusuf Islam, commonly known by his stage name Cat Stevens, is a British singer-songwriter and multi-instrumentalist. His 1967 debut album reached the top 10 in the UK, and the album\'s title song \"Matthew and Son\" charted at number 2 on the UK Singles Chart.","d_m_10.webp",6,3);

//Found
        this.questionCreator("Ian Stewart was known as the sixth member of which British rock group?","Rolling Stones","a-ha","Imagine Dragons","Metallica","Rolling Stones","Role in The Rolling Stones. Ian Andrew Robert Stewart was born at Kirklatch Farm, Pittenweem, East Neuk, Fife, Scotland, and raised in Sutton, Surrey. Stewart (often called Stu) started playing piano when he was six.","d_m_11.webp",6,3);

//Found
        this.questionCreator("Which four letter word beginning with H is a Romanian or Israeli dance in which the performers form a ring?","Hora","Hord","Hare","Heer","Hora","Hora, also known as horo and oro, is a type of circle dance originating in the Balkans but also found in other countries.","d_m_12.webp",6,3);

//Found
        this.questionCreator("What would you do with a sarod?","Play it","Observe","Destroy it","Keep it","Play it","The sarod is a stringed instrument, used mainly in Hindustani music on the Indian subcontinent.","d_m_13.webp",6,3);

//Found
        this.questionCreator("In which US state did singer Elvis Presley perform his last concert?","Indiana","Florida","Texas","Washington","Indiana","Elvis Presley : June 26, 1977 : Market Square Arena, Indianapolis, In. Ticket for Elvis in Concert June 26, 1977, his last concert. Elvis Presley : June 26, 1977 : Market Square Arena, Indianapolis, In.Jun 26, 1977","d_m_14.webp",6,3);

//Found
        this.questionCreator("Which English composer conducted the London Symphony Orchestra at the 1931 opening of the Abbey Road Studios in London?","Edward Elgar","Leila Maldonado","Patricia Clarke","Levi Mayer","Edward Elgar","Pathe filmed the opening of the studios in November 1931 when Edward Elgar conducted the London Symphony Orchestra in recording sessions of his music. In 1934, the inventor of stereo sound, Alan Blumlein, recorded Mozart\'s Jupiter Symphony which was conducted by Thomas Beecham at the studios.","d_m_15.webp",6,3);

//Found
        this.questionCreator("John Lennon and Yoko Ono recorded \'Give Peace a Chance\' at the Hotel La Reine in which city in Canada in 1969?","Montreal","Montrel","Montrael","Montael","Montreal","As the Vietnam War raged in 1969, John Lennon and his wife Yoko Ono held two week-long Bed-Ins for Peace, one at the Hilton Hotel in Amsterdam and one at Fairmont The Queen Elizabeth in Montreal, each of which were intended to be non-violent protests against wars, and experimental tests of new ways to promote peace.","d_m_16.webp",6,3);

//Found
        this.questionCreator("What was the name of the New York City club which launched \'The Twist\' in the early 1960′s and and where Go-Go dancing originated?","The Peppermint Lounge","Lounge","Pepper","Mint","The Peppermint Lounge","Go-go dancing originated in the early 1960s, by some accounts when women at the Peppermint Lounge in New York City began to get up on tables and dance the twist.","d_m_17.webp",6,3);

//Found
        this.questionCreator("Which musician/singer had a pet cat called Rupi that inspired the title song of his 2004 album \'Rupi\'s Dance\'?","Ian Anderson","Terrence Hurst","Maritza Mcneil","Alani Blackwell","Ian Anderson","Rupi\'s Dance (2003) is the fourth studio album by Jethro Tull frontman Ian Anderson. The album was released around the same time as Jethro Tull guitarist Martin Barre\'s new solo album, Stage Left.","d_m_18.webp",6,3);

//Found
        this.questionCreator("Which band released a 2000 album entitled \'Chocolate Starfish and the Hot Dog Flavored Water\'?","Limp Bizkit","The Cure","Toto","Queen","Limp Bizkit","Chocolate Starfish and the Hot Dog Flavored Water is the third studio album by American rock band Limp Bizkit, released on October 17, 2000 by Flip and Interscope Records.","d_m_19.webp",6,3);

//Found
        this.questionCreator("The singer Mary O\'Brien was better known by what name?","Dusty Springfield","Vance Joy","AC/DC","The xx","Dusty Springfield","Mary Isobel Catherine Bernadette O\'Brien OBE (16 April 1939 – 2 March 1999), professionally known as Dusty Springfield, was an English pop singer and record producer whose career extended from the late 1950s to the 1990s.","d_m_20.webp",6,3);

//Found
        this.questionCreator("What is the oldest independent record label in Britain, founded in 1939?","Topic Records","Rostrum Recrods","Columbia Records","Aftermath Records","Topic Records","Topic Records is a British folk music label, which played a major role in the second British folk revival. It began as an offshoot of the Workers\' Music Association in 1939, making it the oldest independent record label in the world.","d_m_21.webp",6,3);

//Found
        this.questionCreator("In which year was the Henry Wood Promenade Concerts, otherwise known as The Proms, founded?","1895","1832","1931","1837","1895","The Proms is an eight-week summer season of daily orchestral classical music concerts and other events held annually, predominantly in the Royal Albert Hall in central London. The Proms were founded in 1895, and are now organised and broadcast by the BBC.","d_m_22.webp",6,3);

//Found
        this.questionCreator("Who headlined at the first Glastonbury Festival in 1970?","Tyrannosaurus Rex","The White Stripes","Bradley Cooper","Blink-182","Tyrannosaurus Rex","The first festival at Worthy Farm was the Pilton Pop, Blues & Folk Festival, mounted by Michael Eavis on Saturday 19 September 1970, and attended by 1,500 people. The original headline acts were The Kinks and Wayne Fontana and the Mindbenders but these acts were replaced at short notice by Tyrannosaurus Rex, later known as T. Rex.","d_m_23.webp",6,3);

//Found
        this.questionCreator("Which US actor appears in the video for Travis\'s \'Closer\'?","Ben Stiller","Willow Meadows","Thalia Mitchell","Jaquan Conley","Ben Stiller","Ben Stiller stars in new Travis video. American actor Ben Stiller is to star in the video for the new Travis single \'Closer\'.","d_m_24.webp",6,3);

//Found
        this.questionCreator("Who composed the romantic opera \'Lohengrin\'?","Richard Wagner","Aliana Hamilton","Raina Rowe","Kyleigh Mooney","Richard Wagner","Lohengrin (opera) Lohengrin, WWV 75, is a Romantic opera in three acts composed and written by Richard Wagner, first performed in 1850.","d_m_25.webp",6,3);

//Found
        this.questionCreator("Which US disc jockey is credited with coining the phrase \'Rock and Roll\' during the 1950′s?","Alan Freed","Raina Rowe","Aaliyah Martin","Landon Mcdonald","Alan Freed","Disc jockey Alan Freed is widely credited with coining the term “rock and roll” to describe the uptempo black R&B records he played as early as 1951 on Cleveland radio station WJW.","d_m_26.webp",6,3);

//Found
        this.questionCreator("Who composed \'Eine Kleine Nachtmusik\' in the 18th Century?","Wolfgang Amadeus Mozart","Arctic Monkeys","Red Hot Chili Peppers","M83","Wolfgang Amadeus Mozart","Eine kleine Nachtmusik was a piece of classical music written by Earth composer Wolfgang Amadeus Mozart in the late 18th century.","d_m_27.webp",6,3);

//Found
        this.questionCreator("Which 1965 pop song by The Toys is based on \'Minuet in G Major\' by J S Bach?","A Lover's Concerto","A Crush's Concerto","A Fathers's Concerto","A Mothers's Concerto","A Lover's Concerto","In popular culture. The melody from the 1965 pop song \"A Lover\'s Concerto\", written by American songwriters Sandy Linzer and Denny Randell, was based on the Minuet in G major.","d_m_28.webp",6,3);

//Found
        this.questionCreator("Who had 90s hits with You Oughta Know and Ironic?","Alanis Morissette","Aaliyah Martin","Heidi Farrell","Kareem Dyer","Alanis Morissette","\"You Oughta Know\" is a song by Canadian-American singer Alanis Morissette, released as the lead single from her third studio album, Jagged Little Pill (1995) on July 7, 1995. After releasing two commercially successful studio albums through MCA Records Canada, Morissette left MCA Records Canada and was introduced to manager Scott Welch.","d_m_29.webp",6,3);

//Found
        this.questionCreator("Who recorded the Immaculate Collection?","Madonna","Ibrahim Forbes","Talon Calhoun","Edward Hester","Madonna","The Immaculate Collection is a greatest hits album by American recording artist Madonna, released on November 9, 1990 by Sire Records.","d_m_30.webp",6,3);

//Found
        this.questionCreator("Which singer/songwriter arrived with the album She\'s So Unusual?","Cyndi Lauper","Jazlynn Long","Edward Hester","Kenneth Abbott","Cyndi Lauper","She\'s So Unusual. She\'s So Unusual is the debut studio album by American singer and songwriter Cyndi Lauper, released on October 14, 1983 by Portrait Records.","d_m_31.webp",6,3);

//Found
        this.questionCreator("Which non-American group sang about Massachusetts?","Bee Gees","Childish Gambino","Earth, Wind & Fire","U2","Bee Gees","\"(The Lights Went Out In) Massachusetts\" is a song by the Bee Gees, released in 1967.[3] Written by Barry, Robin & Maurice Gibb. Robin Gibb sang lead vocals on this song and it would become one of his staple songs to perform during concerts on both Bee Gees and his solo concerts.","d_m_32.webp",6,3);

//Found
        this.questionCreator("Paul Williams who died in 1973 was a member of which vocal group?","Temptations","The Offspring","The Cure","The Climb","Temptations","In many ways, Paul Williams, who died in tragic circumstances on 17 August 1973, was the Temptations\' unsung hero. He and Eddie Kendricks were their original lead singers, but when of the Motown group broke worldwide and became soul heroes with \'My Girl\' in 1964, it was David Ruffin\'s voice that rang out.","d_m_33.webp",6,3);

//Found
        this.questionCreator("What was Madonna\'s first No 1 on both sides of the Atlantic?","Papa Don't Preach","The Strokes","The Band","The Rookies","Papa Don't Preach","the song was a commercial success. It became Madonna\'s fourth number-one single on the Billboard Hot 100, and performed well internationally, reaching the top position in the United Kingdom and Australia. It was well received by music critics and was frequently cited as a highlight in the album.","d_m_34.webp",6,3);

//Found
        this.questionCreator("How did Massachusetts brothers Joe, Gene, Vic and Ed become known in the 50s?","Ames Brothers","The Beatles","The Neighbourhood","Black Sabbath","Ames Brothers","The Ames Brothers were a singing quartet from Malden, Massachusetts, who were particularly famous in the 1950s for their traditional pop music hits.","d_m_35.webp",6,3);

//Found
        this.questionCreator("What was Neil Diamond\'s first top ten single hit?","Cherry Cherry","God is a Woman","Dedicate","Immigrant Song","Cherry Cherry","His first release on that label, \"Solitary Man\", became his first true hit as a solo artist. Diamond later followed with \"Cherry, Cherry\" and \"Kentucky Woman\". His early concerts saw him as a \"special guest\" of, or opening for, everyone from Herman\'s Hermits to, on one occasion, the Who.","d_m_36.webp",6,3);

//Found
        this.questionCreator("Who wrote The Times They Are A Changin\'?","Bob Dylan","Leila Maldonado","Kamden Walls","Osvaldo Krueger","Bob Dylan","\"The Times They Are a-Changin\'\" is a song written by Bob Dylan and released as the title track of his 1964 album of the same name. Dylan wrote the song as a deliberate attempt to create an anthem of change for the time, influenced by Irish and Scottish ballads.","d_m_37.webp",6,3);

//Found
        this.questionCreator("What color was UB40\'s Wine?","Red","Green","Black","White","Red","It has been a pleasure and honour working with Ali, Astro and Mickey to create the exclusive wine which is so befitting of the great track \'Red Red Wine\'.” The song \'Red Red Wine\' was originally written in 1967 by US singer-songwriter Neil Diamond, who imagined it as a maudlin country ballad about drinking to forget.","d_m_38.webp",6,3);

//Found
        this.questionCreator("The Eagles formed when they met as a backing group for which singer?","Linda Ronstadt","Azaria Oconnor","Paloma Villa","Slade Myers","Linda Ronstadt","Back in 2014, Ronstadt recounted how a legendary rock band came to be. Glenn Frey and Don Henley knew each other before they joined Linda Ronstadt\'s band in 1971. But it was that time together that led to the formation of the Eagles later that year","d_m_39.webp",6,3);

//Found
        this.questionCreator("In which US state was Elvis Presley\'s mansion?","Tennessee","Minnesota","Delaware","Virginia","Tennessee","Graceland. Graceland is a mansion on a 13.8-acre (5.6 ha) estate in Memphis, Tennessee, United States, once owned by the singer and actor Elvis Presley.","d_m_40.webp",6,3);

//Found
        this.questionCreator("Duran Duran were part of which New movement?","Romantics","Convicts","Shrinks","Straits","Romantics","Duran Duran are an English new wave and synth-pop band formed in Birmingham in 1978. The band grew from being alternative sensations, in 1982, to mainstream pop stars by 1984. By the end of the decade, membership and music style changes challenged the band before a resurgence in the early 1990s.","d_m_41.webp",6,3);

//Found
        this.questionCreator("What was the second No 1 single for The Doors?","Hello I Love You","Sunday Morning","Welcome","Disco Night","Hello I Love You","\"Hello, I Love You\" is a song written by Jim Morrison of the American rock band the Doors from their 1968 album Waiting for the Sun. It was released as a single that same year, reaching number one in the United States and selling over a million copies in the U.S. alone.","d_m_42.webp",6,3);

//Found
        this.questionCreator("Under which name did Bill Fries have a 70s No 1 single?","C W McCall","Fisher Li","Clark Ayala","Nash Swanson","C W McCall","\"Convoy\" is a 1975 novelty song performed by C. W. McCall (a character co-created and voiced by Bill Fries, along with Chip Davis) that became a number-one song on both the country and pop charts in the US and is listed 98th among Rolling Stone magazine\'s 100 Greatest Country Songs of All Time.","d_m_43.webp",6,3);

//Found
        this.questionCreator("On which label did The Chiffons record their string of 60s hits?","Laurie","Columbia","Amaru","Anticon","Laurie","The group was originally a trio of schoolmates: Judy Craig, Patricia Bennett and Barbara Lee; at James Monroe High School in the Bronx in 1960.","d_m_44.webp",6,3);

//Found
        this.questionCreator("Who had a No 1 with Walk Right In?","The Rooftop Singers","Paramore","he Beach Boys","Dire Straits","The Rooftop Singers","In 1962, the American folk trio the Rooftop Singers recorded a version of the song and released it as a single. The single spent two weeks at number one on the Billboard Hot 100 chart in early 1963.","d_m_45.webp",6,3);

//Found
        this.questionCreator("Who recorded Riders On The Storm?","The Doors","Marshmello","Talking Heads","Eagles","The Doors","\"Riders on the Storm\" is a song by American psychedelic rock band the Doors. It was released as the second single from their sixth studio album, L.A. Woman (1971), in June 1971. It reached number 14 on the Billboard Hot 100 in the U.S., number 22 on the UK Singles Chart, and number 7 in the Netherlands.","d_m_46.webp",6,3);

//Found
        this.questionCreator("Who collaborated with Michael Bolton on Bolton\'s Steel Bars hit?","Bob Dylan","Lewis Stanton","Celeste Owen","Remington Munoz","Bob Dylan","Before he became famous for his soul-choked voice, Michael Bolton made his living as a hit songwriter, having considerable success collaborating with other hit writers such as Dianne Warren and Desmond Child, and producing hits for everyone from Kiss to Cher.","d_m_47.webp",6,3);

//Found
        this.questionCreator("Which Brit, Phil, was at No 1 at the end of the 80s?","Collins","Smith","Owen","Banks","Collins","On December 23, 1989 Phil Collins \"Another Day in Paradise\" 4 weeks at number one.","d_m_48.webp",6,3);

//Found
        this.questionCreator("Who took his Burning Love into the charts in 1972?","Elvis Presley","Lee Frank","Isai Chase","Karina Ryan","Elvis Presley","\"Burning Love\" is a song written by Dennis Linde and originally recorded by country soul artist Arthur Alexander, who included it on his 1972 self-titled album. It was soon covered and brought to fame by Elvis Presley.","d_m_49.webp",6,3);

//Found
        this.questionCreator("Which artist had the last No 1 of the 70s?","Rupert Holmes","BROCKHAMPTON","System of a Down","Foo Fighters","Rupert Holmes","He is widely known for the hit singles \"Escape (The Piña Colada Song)\" (1979) and \"Him\" (1980). He is also known for his musicals Drood, which earned him two Tony Awards, and Curtains, and for his television series Remember WENN.","d_m_50.webp",6,3);

//Found
        this.questionCreator("Who partnered Patti Austin on Baby Come To Me?","James Ingram","Azaria Oconnor","Ruby Davies","Damaris Turner","James Ingram","\"Baby, Come to Me\" is a love ballad from Patti Austin\'s 1981 album Every Home Should Have One, sung as a duet by Austin and James Ingram. It was written by Rod Temperton (formerly of Heatwave). The song was released as a single in April 1982, peaking at #73 on the Billboard Hot 100 chart.","d_m_51.webp",6,3);

//Found
        this.questionCreator("Who had a huge No 1 with Tonight\'s The Night?","Rod Stewart","Clark Ayala","Nevaeh Grimes","Nash Swanson","Rod Stewart","Rod Stewart got his big break singing with the Jeff Beck Group in 1967. By 1969 he was fronting the rebooted Faces with his pal, former Beck sideman Ronnie Wood. Around the same time, he released his first solo album; two years later, his third LP, \'Every Picture Tells a Story,\' made him a star.","d_m_52.webp",6,3);

//Found
        this.questionCreator("Which magazine shares its name with a Madonna No 1?","Vogue","Forbes","Elle","Time","Vogue","Madonna`s real and full name is Madonna Louise Veronica Ciccone (Ritchie). Her mother was named Madonna Ciccone who died when Madonna was only six years old, which is why she uses the name Madonna.","d_m_53.webp",6,3);
//Not Found
        this.questionCreator("What was George Harrison\'s next No 1 single after My Sweet Lord?","Isn't It A Pity?","Green Light","Enter Sandman","Everlong","Isn't It A Pity?","\"Isn\'t It a Pity\" is a song by English musician George Harrison from his 1970 solo album All Things Must Pass.","d_m_54.webp",6,3);

//Found
        this.questionCreator("Who had a No 1 hit with To Sir With Love?","Lulu","Malia Walter","Elaine Morris","Katherine Wong","Lulu","\"To Sir with Love\" is the theme from James Clavell\'s 1967 film To Sir, with Love. The song was written by Don Black and Mark London (husband of Lulu\'s longtime manager Marion Massey).","d_m_55.webp",6,3);

//Found
        this.questionCreator("Maureen McGovern hit No 1 with which song?","The Morning After","High Hopes","Without Me","Stargazing","The Morning After","\"The Morning After\" (also known as \"The Song from The Poseidon Adventure\") is a song written by Al Kasha and Joel Hirschhorn for the 1972 film The Poseidon Adventure. It won the 1972 Academy Award for Best Original Song at the 45th Academy Awards in March 1973.","d_m_56.webp",6,3);

//Found
        this.questionCreator("Who was the first UK group to top the US chart after The Beatles?","Animals","Eagles","Muse","Radiohead","Animals","The Animals are an English rhythm and blues and rock band, formed in Newcastle upon Tyne in the early 1960s. The band moved to London upon finding fame in 1964.","d_m_57.webp",6,3);

//Found
        this.questionCreator("On a No 1 single who backed (Question Mark)?","The Mysterians","Led Zeppelin","Hozier","Fleetwood Mac","The Mysterians","The band was signed to Pa-Go-Go Records in 1966 and released its first and most acclaimed single, \"96 Tears\", in the early part of the year. \"96 Tears\" became a number one hit on the Billboard Hot 100 and propelled the group to a 15-month period of national prominence.","d_m_58.webp",6,3);

//Found
        this.questionCreator("Which Tommy Was Dizzy to be No 1 in the 60s?","Roe","Olson","Rivas","Stanton","Roe","Thomas David \"Tommy\" best-remembered for his hits \"Sheila\" (1962) and \"Dizzy\" (1969), Roe was \"widely perceived as one of the archetypal bubblegum artists of the late 1960s, but cut some pretty decent rockers along the way, especially early in his career,\" wrote the Allmusic journalist Bill Dahl.","d_m_59.webp",6,3);

//Found
        this.questionCreator("Who famously said Hello Muddah Hello Faddah?","Allan Sherman","Jenny Stokes","Moriah Dickson","Carly Saunders","Allan Sherman","\"Hello Muddah, Hello Fadduh (A Letter from Camp)\" is a novelty song by Allan Sherman and Lou Busch, based on letters of complaint Allan received from his son Robert while Robert attended Camp Champlain in Westport, New York.","d_m_60.webp",6,3);

//Found
        this.questionCreator("On which label did Madonna\'s first hits appear?","Sire","Star","PolyEast","Warner","Sire","Sire Records is an American record label that is owned by Warner Music Group and distributed by Warner Bros. Records.","d_m_61.webp",6,3);

//Found
        this.questionCreator("What is the first name of Michael Jackson\'s second wife?","Debbie","Carly","Raquel","Emilee","Debbie","Michael Jackson was married twice. The first marriage was a publicity stunt. His first wife was Lisa Marie Presley. His second marriage was with Debbie Rowe, a medical assistant for Michael Jackson\'s dermatologist dr Arnold Klein.","d_m_62.webp",6,3);

//Found
        this.questionCreator("Who duetted with David Bowie on the No 1 hit Dancing In The Street?","Mick Jagger","Carter Mcgrath","Valentino Norman","Adonis Tyler","Mick Jagger","\"Dancing in the Street\" is a song written by Marvin Gaye, William \"Mickey\" Stevenson and Ivy Jo Hunter. It first became popular in 1964 when recorded by Martha and the Vandellas whose version reached No. 2 on the Billboard Hot 100 chart and peaked at No. 4 in the UK Singles Chart.","d_m_63.webp",6,3);

//Found
        this.questionCreator("Whose rendition of Freedom made it an anthem of Woodstock?","Richie Havens","Miranda Wise","Ariella Mueller","Tyson Crosby","Richie Havens","Richard Pierce \"Richie\" Havens (January 21, 1941 – April 22, 2013)[1] was an American singer-songwriter and guitarist. His music encompassed elements of folk, soul, and rhythm and blues. He is best known for his intense and rhythmic guitar style (often in open tunings), soulful covers of pop and folk songs, and his opening performance at the 1969 Woodstock Festival.","d_m_64.webp",6,3);

//Found
        this.questionCreator("Which group returned House Of The Rising Sun to the top ten","Frijid Pink","Marvin Gaye","Cage the Elephant","Guns N' Roses","Frijid Pink","In 1969, the Detroit band Frijid Pink recorded a psychedelic version of \"House of the Rising Sun\", which became an international hit in 1970.","d_m_65.webp",6,3);

//Found
        this.questionCreator("Presented in February of 2017, who won the Grammy Award for Best New Artist?","Chance the Rapper","The Chainsmokers","Kelsea Ballerini","Maren Morris","Chance the Rapper","Chancelor Jonathan Bennett (born April 16, 1993), known professionally as Chance the Rapper, is an American rapper, singer, songwriter, actor, record producer, and philanthropist from the West Chatham neighborhood of Chicago, Illinois.","d_m_66.webp",6,3);

//Found
        this.questionCreator("Their 20th, what studio album did Deep Purple release on April 1st, 2017?","Infinite","Abandon","Purpendicular","Rapture of the Deep","Infinite","Infinite is the 20th studio album by English rock band Deep Purple, released on 7 April 2017.","d_m_67.webp",6,3);

//Found
        this.questionCreator("Finish the title to this 2017 Sam Hunt hit song - \"Body Like a \".","Back Road","Heavenly Goddess","Wooden Toothpick","Bronze God","Back Road","The song was written by Sam Hunt, Zach Crowell, Shane McAnally and Josh Osborne.","d_m_68.webp",6,3);

//Found
        this.questionCreator("Which artist, who died in March of 2017, had his songs \"Maybellene\" and \"You Never Can Tell\" make a reappearance on the charts?","Chuck Berry","James Brown","Jerry Lee Lewis","Little Richard","Chuck Berry","Chuck was one of the first musicians to be inducted into the Rock and Roll Hall of Fame when it made its debut in 1986.","d_m_69.webp",6,3);

//Found
        this.questionCreator("Boston's drummer died on stage while playing the band's greatest hits in March of 2017. Name him.","Sib Hashian","Tracy Ferrie","Gary Pihl","Brad Delp","Sib Hashian","Released in 1976, Boston's self-titled debut album is one of the bestselling debut albums in U.S. history.","d_m_70.webp",6,3);

//Found
        this.questionCreator("Presented posthumously, who won the 2017 Grammy Award for Best Rock Song?","Blackstar David Bowie","Unspoken – Chuck Loeb","Don't Hurt Yourself – Beyonce","Heathens - Tyler Joseph","Blackstar David Bowie","Blackstar was released on Bowie's final studio album of the same name.","d_m_71.webp",6,3);

//Found
        this.questionCreator("Starting his music career as a member of Roxy Music in 1971, who released the album \"Reflection\" on New Year's Day, 2017?","Brian Eno","Bono","John Cale","Robert Fripp","Brian Eno","Brian Peter George St John le Baptiste de la Salle Eno, RDI is an English musician, composer, record producer, singer, writer, and visual artist. - Eno is widely regarded as an ambient music pioneer.","d_m_72.webp",6,3);

//Found
        this.questionCreator("Their 18th, what studio album did Judas Priest release in March of 2018?","Firepower","Redeemer of Souls","Angel of Retribution","Nostradamus","Firepower","Firepower sold close to 50,000 copies in the United States during its first week of release.","d_m_73.webp",6,3);

//Found
        this.questionCreator("What 2018 Billboard Top 100 song starts with the lyric - \"I still see your shadows in my room. Can't take back the love that I gave you\"?","Lucid Dreams","Girls Like You","No Tears Left to Cry","Sad!","Lucid Dreams","Lucid Dreams - Released in March of 2018, Lucid Dreams was recorded by American rapper Juice WRLD.","d_m_74.webp",6,3);

//Found
        this.questionCreator("On the Billboard Hot Rock Songs chart for July 2018, finish the title for this song \"Panic! At The Disco\" song - \"King of the ...\".","Clouds","Sun","World","Night","Clouds","Panic! at the Disco is an American rock band that formed in 2004.","d_m_75.webp",6,3);

//Found
        this.questionCreator("Which artist, who died at the age of 46 in January of 2018, was the lead singer for the Cranberries??","Dolores O\'Riordan","Barbara Cope","Betty Willis","Claudia Fontaine","Dolores O\'Riordan","Dolores Mary Eileen O'Riordan was an Irish musician, singer and songwriter. She led the rock band The Cranberries from 1990 until their break-up in 2003; they reunited in 2009.","d_m_76.webp",6,3);

//Found
        this.questionCreator("Winner of 12 Grammy Awards, what former member of the White Stripes released the album \"Boarding House Reach\" in March of 2018?","Jack White","Meg White","Alison Mosshart","Brendan Benson","Jack White","Jack White Boarding House Reach is Jack's third solo studio album.","d_m_77.webp",6,3);

//Found
        this.questionCreator("What longtime Venture's guitarist passed away in March 2018 at the age of 82?","Nokie Edwards","Bob Bogle","Jerry McGee","Don Wilson","Nokie Edwards","Nole Floyd \"Nokie\" Edwards was an American musician and member of the Rock and Roll Hall of Fame. He was primarily a guitarist, best known for his work with The Ventures, and was known in Japan as the King of Guitars.","d_m_78.webp",6,3);

//Found
        this.questionCreator("\"Who Let the Dogs Out\" in 2000?","Baha Men","Snoopy","Shaggy","Lil Bow Wow","Baha Men","The Baha Men are a Bahamian band playing a modernized style of Bahamian music called junkanoo. They are best remembered for their Grammy Award-winning hit song \"Who Let the Dogs Out?\".","d_m_79.webp",6,3);

//Found
        this.questionCreator("Released in 1973, my debut album is titled \"Greetings from Asbury Park\". Who am I?","Bruce Springsteen","Eric Clapton","Jon Bon Jovi","Bryan Adams","Bruce Springsteen","Bruce Frederick Joseph Springsteen is an American singer-songwriter and musician, known for his work with the E Street Band.","d_m_80.webp",6,3);

//Found
        this.questionCreator("\"The Forces Sweetheart\" was a nickname given to which 1940s female star?","Vera Lynn","Alma Cogan","Kay Starr","Rosemary Clooney","Vera Lynn","Vera\'s musical recordings were popular during the Second World War.","d_m_81.webp",6,3);

//Found
        this.questionCreator("This \"Spice Girl\" had a number one hit with \"It's Raining Men\" in 2001.","Geri Halliwell","Victoria Beckham","Emma Bunton","Melanie C","Geri Halliwell","Geri Halliwell - The song was originally released by The Weather Girls in 1982.","d_m_82.webp",6,3);

//Found
        this.questionCreator("Mysteriously disappearing in February of 1995, Richey Edwards was a member of which rock band?","Manic Street Preachers","The Stone Roses","Stereophonics","Primal Scream","Manic Street Preachers","Richard James \"Richey\" Edwards was a Welsh musician who was the lyricist and rhythm guitarist of the alternative rock band Manic Street Preachers. Edwards was declared 'presumed dead' in November of 2008.","d_m_83.webp",6,3);

//Found
        this.questionCreator("Taylor Hawkins plays which instrument in the \"Foo Fighters\"?","Drums","Keyboards","Guitar","Bass Guitar","Drums","Hawkins was the touring drummer for Alanis Morissette on her Jagged Little Pill and Can't Not Tours.","d_m_84.webp",6,3);

//Found
        this.questionCreator("Who released a rock n' roll version of the song \"Blueberry Hill\", which reached number two on the Billboard Top 40 chart in 1956? `","Fats Domino","Little Richard","Ray Charles","Chuck Berry","Fats Domino","Antoine \"Fats\" Domino Jr. was an American pianist and singer-songwriter. One of the pioneers of rock and roll music, Domino sold more than 65 million records.","d_m_85.webp",6,3);

//Found
        this.questionCreator("Born in Oxford, England, \"Jacqueline du Pre\" was famous for the playing of which stringed instrument?","Cello","Guitar","Violin","Double Bass","Cello","Jacqueline's career was cut short by multiple sclerosis at the age of 27.","d_m_86.webp",6,3);

//Found
        this.questionCreator("Elton John spent how many weeks at the top of the US Charts with his 1997 rewritten version of the song \"Candle in the Wind\"?","Fourteen","Four","Thirty four","Twenty four","Fourteen","The song was originally written in honor of Marilyn Monroe in 1973.","d_m_87.webp",6,3);

//Found
        this.questionCreator("Dave Matthews of the \"Dave Matthews Band\" was born in which country?","South Africa","U.S.A.","Canada","Germany","South Africa","From 2000 to 2010, the Dave Matthews Band earned more money than any other band in North America.","d_m_88.webp",6,3);

//Found
        this.questionCreator("Who wrote the first hit song \"Different Drum\" for Stone Poneys and their lead-singer Linda Ronstadt?","Michael Nesmith","Paul Simon","Neil Diamond","Bob Dylan","Michael Nesmith","The song reached number 13 on the Billboard Hot 100 in 1967.","d_m_89.webp",6,3);

//Found
        this.questionCreator("Released in June of 1989, what was the name of Nirvana's debut album?","Bleach","Nevermind","Smells Like Teen Spirit","Incesticide","Bleach","Bleach has had sales of over 1.7 million units in the United States.","d_m_90.webp",6,3);

//Found
        this.questionCreator(": What worldwide hit by Nelly Furtado was #1 on over 20 different music charts?","Say it Right","Say it again","Say it","Say it all","Say it Right","Nelly Furtado's song \"Say It Right\" was released in the United States on October 31st, 2006. The song \"Say It Right\" featured backup singers named Timothy Mills and Nate Hills.","d_m_91.webp",6,3);

//Found
        this.questionCreator("Who had a hit in 1972 with the song \"Down by the Lazy River\"?","The Osmonds","Leif Garrett","Partridge Family","Jackson Five","The Osmonds","The Osmonds have sold over 102 million records worldwide.","d_m_92.webp",6,3);

//Found
        this.questionCreator("Which of these jazz composers penned, among other things, the well-known \"Charlie Brown Theme\"?","Vince Guaraldi","David Benoit","Antonin Scarlatti","Eddie Daniels","Vince Guaraldi","Vince served as an Army cook in the Korean War.","d_m_93.webp",6,3);

//Found
        this.questionCreator("What is unusual about Seals and Crofts' hits \"Summer Breeze\", \"Diamond Girl\" and \"Get Closer\"?","They all hit number six","They were all hits on April Fool\'s Day","They were all hits in the same year","They all had backing vocals by John Denver","They all hit number six","Seals and Crofts were an American soft rock duo made up of James \"Jim\" Seals and Darrell \"Dash\" Crofts. They are best known for their Hot 100 No. 6 hits \"Summer Breeze\", \"Diamond Girl\", and \"Get Closer\".","d_m_94.webp",6,3);

//Found
        this.questionCreator("Who composed the timeless hit \"Fascinating Rhythm\"?","George Gershwin","Lionel Hampton","Count Basie","Benny Goodman","George Gershwin","Fascinating Rhythm Written in 1924, the song was first heard in the Broadway musical 'Lady Be Good'.","d_m_95.webp",6,3);

//Found
        this.questionCreator("What song is this from? \"If I go crazy then will you still Call me Superman If I'm alive and well, will you be There a-holding my hand I'll keep you by my side With my superhuman might Kryptonite\"","Kryptonite","Call me Superman","I’ll keep you by my Side","My superhuman might Kryptonite","Kryptonite","The song, \"Kryptonite\", is sung by 3 Doors Down and was released on November 21, 2000.","d_m_96.webp",6,3);

//Found
        this.questionCreator("Written in 1973, who was the one-hit wonder who recorded \"Chevy Van\"?","Sammy Johns","Glen Campbell","Ricky Nelson","Starland Vocal Band","Sammy Johns","The song reached number five on the Billboard Hot 100 chart in 1975.","d_m_97.webp",6,3);

//Found
        this.questionCreator("Achieving worldwide success in 2007 with the hit single \"Love Song,\" and currently in the charts with the song \"Brave,\" what instrument does Sara Bareilles play?","Piano","Harp","Guitar","Violin","Piano","Sara has been nominated for a Grammy Award five times.","d_m_98.webp",6,3);

//Found
        this.questionCreator("What hit by \"The Neighbourhood\" opens with the lyrics, \"All I am is a man - I want the world in my hands - I hate the beach - But I stand.\"?","Sweater Weather","I Love You","Female Robbery","Afraid","Sweater Weather","Sweater Weather was the lead single from the Neighbourhood's debut studio album, 'I Love You'.","d_m_99.webp",6,3);

//Found
        this.questionCreator("Which of these bands was formed in 2013?","Fight or Flight","Attack Attack","Lifehouse","Ghost Observatory","Fight or Flight","The band's debut single was released on the 21st of May 2013.","d_m_100.webp",6,3);

//Found
        this.questionCreator("During 2013, which of these bands was on hiatus?","Wofmother","The Replacements","The Mars Volta","Green Day","Wofmother","Wolfmother has been together since 2000.","d_m_101.webp",6,3);

//Found
        this.questionCreator("Which of these songs is on the album \"Contrast\" by Conor Maynard?","Animal","The Way","Shut Up","NoNoNo","Animal","Animal song is the first song on the album.","d_m_102.webp",6,3);

//Found
        this.questionCreator("Which of the following albums was released by \"The Saturdays\"?","Chasing the Saturdays","Chasing Monday","Finding the Saturdays","Waiting for Sunday","Chasing the Saturdays","Chasing the Saturdays Released in 2013, it is their second album.","d_m_103.webp",6,3);

//Found
        this.questionCreator("Which of these songs appears on the album \"It Happens All the Time\" by Megan Hilty?","Be a Man","Heat","The Next Day","Love is Lost","Be a Man","Be a Man song has three minutes and thirty-four seconds in length, the song is the album's second track.","d_m_104.webp",6,3);

//Found
        this.questionCreator("\"Does he tell you he loves you when you least expect it?\" The first line of which song?","DNA","Run","Last Hope","Close Your Eyes","DNA","The song appears on the album DNA along with the song Wings.","d_m_105.webp",6,3);

//Found
        this.questionCreator("Sinead O'Connor sang \"Nothing Compares\", but what was the official spelling of the remainder of that song title?","Two u","You and me","I love you","You and i","Two u","Some might say her abbreviation was ahead of its time, coming before the popular use of SMS text messages, internet chats and twitter. \"Nothing Compares 2 U\" charted at number three on the US Billboard list in 1990, behind Roxette's \"It Must Have Been Love\" (number two) and Wilson Phillips' \"Hold On\" (number one).","d_m_106.webp",6,3);


    }

    private void addPeopleEasy()
    {
        //check

//Found
        this.questionCreator("The only Philippine President to have died of accident.","Ramon Magsaysay","Elpidio Quirino","Ferdinand Marcos","Jose Rizal","Ramon Magsaysay","The 1957 crash of a Douglas C-47 plane named \"Mt. Pinatubo\" on the slopes of Mount Manunggal, Cebu, Philippines, killed the 7th President of the Philippines, Ramon Magsaysay, and 24 other passengers.","e_p_1.webp",1,1);

//Found
        this.questionCreator("The age of the youngest Philippine president.","29","32","28","22","29","Emilio Aguinaldo is also the longest-lived president having survived to age 94.","e_p_2.webp",1,1);

//Found
        this.questionCreator("Who is NOT buried at the \"Libingan ng mga Bayani\"?","Sergio Osmeña","Diosdado Macapagal","Carlos Garcia","Fernando Amorsolo","Sergio Osmeña","Sergio Osmeña was buried at the Manila North Cemetery.","e_p_3.webp",1,1);

//Found
        this.questionCreator("This president was a former professor of economics.","Gloria Macapagal Arroyo","Cory Aquino","Carlos Garcia","Jose Laurel","Gloria Macapagal Arroyo","Gloria Macapagal Arroyo was a former professor of economics at Ateneo De Manila University and Benigno S. Aquino III was one of her students.","e_p_4.webp",1,1);

//Found
        this.questionCreator("The first Protestant President of the country.","Fidel Ramos","Jose Laurel","Elpidio Quirino","Manuel Quezon","Fidel Ramos","Fidel Ramos is also the only Filipino officer in history to have held every rank in the Philippine military from 2nd Lieutenant to Commander-in-Chief.","e_p_5.webp",1,1);

//Found
        this.questionCreator("Filipino man who drives around the city to ensure things are well taken care of.","Rodrigo Duterte","Manuel Quezon","Benigno Aquino III","Gloria Macapagal Arroyo","Rodrigo Duterte","When he was still a mayor, riding a Harvey big bike, Mayor Duterte would usually drive around Davao City twice a week to ensure things are well taken cared of.","e_p_6.webp",1,1);

//Found
        this.questionCreator("The longest hours of power shortage were experienced during this president's term.","Corazon Aquino","Diosdado Macapagal","Joseph Estrada","Kris Aquino","Corazon Aquino","Corazon Aquino was the first woman President of the Philippines and was the country’s president during one of its darkest and lowest era.","e_p_7.webp",1,1);

//Found
        this.questionCreator("Known for the \"Filipino First\" policy.","Carlos Garcia","Diosdado Macapagal","Manuel Roxas","Gabriel Garcia","Carlos Garcia","Carlos P. Garcia is known for his \"Filipino First\" policy, which put the interests of the Filipino people above those of foreigners and of the ruling party.","e_p_8.webp",1,1);

//Found
        this.questionCreator("Is considered by the people the \"Idol of the Masses\".","Ramon Magsaysay","Joseph Estrada","Rodrigo Duterte","Andres Bonifacio","Ramon Magsaysay","Magsaysay is one of the most loved Philippine Presidents; an estimated 5 million people attended his burial on March 31, 1957.","e_p_9.webp",1,1);

//Found
        this.questionCreator("He declared Martial Law in the country in 1944.","Jose Laurel","Emilio Aguinaldo","Ferdinand Marcos","Josh Macapagal","Jose Laurel","Jose Laurel declared martial law and war against America and the UK in September 1944. But he did so only under threat of death by Japanese authorities.","e_p_10.webp",1,1);

//Found
        this.questionCreator("Which Filipino boxer is known for his nickname “Pac-Man”","Manny Pacquiao","Onyok Velasco","John Vasquez","Mannny Pacquiao","Manny Pacquiao","Emmanuel Dapidran Pacquiao, PLH (/ˈpækiaʊ/ PAK-ee-ow; Tagalog: [pɐkˈjaʊ]; born December 17, 1978) is a Filipino professional boxer and politician, currently serving as a Senator of the Philippines.","e_p_11.webp",1,1);

//Found
        this.questionCreator("Who said this immortal words “A Filipino is worth dying for” ?","Ninoy Aquino","Cory Aquino","Santi Aquino","Bim Aquino","Ninoy Aquino","A model of bravery and self-sacrifice, he is certainly one Pinoy whom we can be truly proud of. He has uttered the words \"A Filipino is worth dying for\" and has thus proven this by offering his life for the Filipino people.","e_p_12.webp",1,1);

//Found
        this.questionCreator("Which Philippine president has an initial of MLQ?","Manuel L. Quezon","Miguel L. Quezon","Manny L. Quezon","Monte L. Quezon","Manuel L. Quezon","Manuel L. Quezon was a Filipino statesman, soldier, and politician who served as president of the Commonwealth of the Philippines from 1935 to 1944.","e_p_13.webp",1,1);

//Found
        this.questionCreator("What Filipino was nicknamed the ”iron butterfly”.","Imelda Marcos","Niña Aquino","Mascia Santiago","Nina Valtazar","Imelda Marcos","Nicknamed \"the Iron Butterfly\", Imelda commissioned luxury hotels and a cultural centre and focused her attention on increasing tourism to the country, ignoring the terrible poverty and huge differences in lifestyle between the wealthy and the poor","e_p_14.webp",1,1);

//Found
        this.questionCreator("Who is the national hero of the Philippines?","Dr. Jose Rizal","Andres Bonifacio","Emilio Aguinaldo","General Gregorio del Pilar","Dr. Jose Rizal","The Philippines national hero. Borin in Calamba, Laguna, on June 19 1861. Published his masterpiece Noli Me tangere in Berlin(Germany) in 1887 and his second novel El Filibusterismo in Ghent(Belgium) in 1891, His two novels stirred the conscience of his people.","e_p_15.webp",1,1);

//Found
        this.questionCreator("Who founded the secret society, Katipunan, on July 7, 1892 to fight Spain?","Andres Bonifacio","Apolinario Mabini","General Antonio Luna","Mechora Aquino","Andres Bonifacio","He founded the secret society, Katipunan, on July 7, 1892, to fight Spain. He was also president of the Tagalog republic from August 24,1896 to May 10,1897. Born in Tondo, Manila, on November 30, 1863.","e_p_16.webp",1,1);

//Found
        this.questionCreator("Who is the brain of the Katipunan?","Emilio Jacinto","Andres Bonifacio","Apolinario Mabini","General Antonio Luna","Emilio Jacinto","Brain of the Katipunan. Born in Trozo,Manila, on December 15,1875. He joined the Katipunan in 1894 and become Bonifacio's trusted friend and advicer.","e_p_17.webp",1,1);

//Found
        this.questionCreator("He is one of the last Filipino generals who surrender to Americans.","Miguel Malvar","Miguel Marquez","Manuel Santiago","Jose Rizal","Miguel Malvar","He surrendered to Bell on April 13, 1902 in Rosario, Batangas, mainly due to desertion of his top officers and to put an end to the sufferings of his countrymen","e_p_18.webp",1,1);

//Found
        this.questionCreator("He is the archipelago of Mactan Island and the first native fighter in the occupation of the Spaniards.","Lapu-lapu","Rajah Datu","Rajah Solaibon","Magellan","Lapu-lapu","Modern Philippine society regards him as the first Filipino hero because he was the first native to resist Imperial Spanish colonization. He is best known for the Battle of Mactan that happened at dawn on April 27, 1521, where he and his soldiers defeated Portuguese explorer Ferdinand Magellan.","e_p_19.webp",1,1);

//Found
        this.questionCreator("Known as the Philippine Revolutionary Brain.","Apolinario Mabini","Manuel L Quezon","Santiago Macailan","Jose Laurel","Apolinario Mabini","Born in Talaga, Tanauan, Batangas on July 23,1864 of poor parents, and later hampered for life by paralysis, Apolinario Mabini nevertheless grew up to be a good writer, lawyer and patriot. He is known as the Sublime Paralytic and the Brains of the Revolution","e_p_20.webp",1,1);

//Found
        this.questionCreator("He is the founder of Diariong Tagalog and is known as the great editor of the Propaganda movement.","Marcelo H. Del Pilar","Sintia Acollo","Milo Valtazar","Emilio Aguinaldo","Marcelo H. Del Pilar","Using the pen name “Plaridel” in his writings, Del Pilar advocated reforms and a greater voice for Filipinos in government during the Spanish era. He founded the first bilingual newspaper Diariong Tagalog in 1882, and edited the pro-reform newspaper La Solidaridad in 1889","e_p_21.webp",1,1);

//Found
        this.questionCreator("She is known as Tandang Sora. She helped the Katipunerong escaped the Spaniards torturing them.","Melchora Aquino","Mel Agisto","Maria Luna","Antonia Iloco","Melchora Aquino","Tandang Sora earned her nickname after taking care of Andres Bonifacio and other Katipuneros in 1896, risking her life as she provided them with food and nursed the wounded.","e_p_22.webp",1,1);

//Found
        this.questionCreator("He established the first military academy in the Philippines.","Antonio Luna","Anton Luna","Ant Luna","Luna","Antonio Luna","Luna established a military academy at Malolos, known as the Academia Militar, which was the precursor of the present Philippine Military Academy. He appointed Colonel Manuel Bernal Sityar, a mestizo who was formerly lieutenant serving the Civil Guard, as superintendent.","e_p_23.webp",1,1);

//Found
        this.questionCreator("He wrote Fray Butod who exposed the cruelty of the Spanish friars.","Graciano Lopez Jaena","Gracian Marcos","Gracito Ramos","Jose Rizal","Graciano Lopez Jaena","He is known as the first Filipino propagandist having laid the objectives of the Philippine Propaganda Movement which led to the Philippine Revolution of 1896. He wrote “Fray Butod” at age 18, founded the La Solidaridad which served as the official newspaper of the Propaganda Movement.","e_p_24.webp",1,1);

//Found
        this.questionCreator("Master Rapper of the Philippines","Francis Magalona","Andrew E.","Mista Blaze","Kane","Francis Magalona","Francis Michael Durango Magalona (October 4, 1964 – March 6, 2009), also known as FrancisM, Master Rapper, The Mouth and The Man From Manila, was a Filipino rapper, entrepreneur, songwriter, producer, actor, director, and photographer.","e_p_25.webp",1,1);

//Found
        this.questionCreator("British author of the phenomenal best selling Harry Potter series.","J.K.Rowling","Abby Rowling","Trinity Joe","Megan Alice","J.K.Rowling","British author of the phenomenal best selling Harry Potter series. The volume of sales was so high, it has been credited with leading a revival of reading by children. She wrote the first book as a single mother, struggling to make ends meet, but her writing led to her great success.","e_p_26.webp",1,1);

//Found
        this.questionCreator("American talk show host and businesswoman.","Oprah Winfrey","Sally Medio","Alice Capuco","Marie San","Oprah Winfrey","American talk show host and businesswoman. Oprah Winfrey was the first woman to own her own talk show. Her show and book club are very influential, focusing on issues facing American women.","e_p_27.webp",1,1);

//Found
        this.questionCreator("American actress who became one of the most iconic film legends.","Marilyn Monroe","Anne Frank","Rizza Sart","Alicia Mecf","Marilyn Monroe","American actress who became one of the most iconic film legends. Her films were moderately successful, but her lasting fame came through her photogenic good looks and aura of glamour and sophistication.","e_p_28.webp",1,1);

//Found
        this.questionCreator("Dutch Jewish author.","Anne Frank","Frankie Agapito","Izsha Maoli","Isha Maoli","Anne Frank","Dutch Jewish author. Anne Frank’s diary is one of the most widely read books in the world. It reveals the thoughts of a young, yet surprisingly mature 13-year-old girl, confined to a secret hiding place. “Despite everything, I believe that people are really good at heart.”","e_p_29.webp",1,1);

//Found
        this.questionCreator("He was the first Filipino to climb the highest mountain outside Asia and the highest mountain in the Americas, Mt. Aconcagua.","Romi Garduce","Jennifer Alcanto","Rome Garduce","Rommi Garduce","Romi Garduce","On January 1, 2005, he was the first Filipino to climb the highest mountain outside Asia and the highest mountain in the Americas, Mt. Aconcagua, which was then the highest altitude reach by any Filipino.","e_p_30.webp",1,1);

//Found
        this.questionCreator("A Filipina actress and aerobic instructor of Spanish descent.","Jennifer Alcanto","Pilita Corrales","Ton ton Guiterrez","Cory Quirino","Jennifer Alcanto","During the 1980s and the 1990s, she appeared in different film genres including Hihintayin Kita sa Langit, Si Aida, Si Lorna, o Si Fe, Misis mo, Misis ko, and Palabra de honor.","e_p_31.webp",1,1);

//Found
        this.questionCreator("She is a former licensee and national director of Miss World Philippines.","Cory Quirino","Jennifer Alcanto","Luchi Cruz Valdez","Bea Binene","Cory Quirino","Socorro Alicia \"Cory\" Rastrollo Quirino is a Filipino television host, author and beauty pageant titleholder. She is a former licensee and national director of Miss World Philippines and Mister World Philippines. She is also a council member of one of the branch offices of the Philippine Red.","e_p_32.webp",1,1);

//Found
        this.questionCreator("A radio and television newscaster, journalist, and television host in the Philippines also called Igan.","Arnold Clavio","Arnoldd Clavo","Ivan Mayrina","Nathaniel Cruz","Arnold Clavio","He currently co-anchors GMA Network's late-night newscast, Saksi, with Pia Arcangel (formerly with Vicky Morales), and hosts a morning radio show called Dobol A sa Dobol B on DZBB with Ali Sotto. He also writes a column entitled Hirit Na! for the tabloid newspaper Abante.","e_p_33.webp",1,1);

//Found
        this.questionCreator("An award-winning broadcast journalist and newscaster in the Philippines.","Rhea Santos-de Guzman","Sally Marcio","Marie Santan","Lovely Joy Agan","Rhea Santos-de Guzman","Rhea Santos-de Guzman (born June 1, 1979) is a broadcast journalist, Host and newscaster in the Philippines. She currently co-anchors GMA Network's Unang Hirit and hosts several news and public affairs programs.","e_p_34.webp",1,1);

//Found
        this.questionCreator("He was nicknamed \"The Worm\" in NBA.","Dennis Rodman","Michael Jordan","Magic Johnson","Lebron James","Dennis Rodman","He was nicknamed \"The Worm\" and is famous for his fierce defensive and rebounding abilities , Rodman played at the small forward position in his early years before becoming a power forward.","e_p_35.webp",1,1);
//Not Found
        this.questionCreator("Played for the Lakers his entire career, winning five NBA championships.","Kobe Bryant","Michael Jordan","Michael Johnson","Lebron James","Kobe Bryant","Bryant is a 17-time All-Star, 15-time member of the All-NBA Team, and 12-time member of the All-Defensive team.","e_p_36.webp",1,1);

//Found
        this.questionCreator("An entrepreneur, and principal owner and chairman of the Charlotte Hornets.","Michael Jordan","Tennise Romeo","Allen Iverson","Vince Carter","Michael Jordan","Jordan played 15 seasons in the National Basketball Association for the Chicago Bulls and Washington Wizards.","e_p_37.webp",1,1);

//Found
        this.questionCreator("One of the greatest NBA point guard of all time by ESPN in 2007.","Magic Johnson","Leonardo De Carpio","The Rock","Rey Mysterio","Magic Johnson","Earvin \"Magic\" Johnson Jr. is a retired American professional basketball player who played point guard for the Los Angeles Lakers of the National Basketball Association for 13 seasons.","e_p_38.webp",1,1);

//Found
        this.questionCreator("American retired professional basketball player, former rapper and actor.","Shaquille O\'Neal","Christopher Vistal","Gwyn Paul Dapiton","Joshua Safico","Shaquille O\'Neal","Standing 7 ft 1 in tall and weighing 325 pounds, he was one of the heaviest players ever to play in the NBA. O'Neal played for six teams throughout his 19-year NBA career.","e_p_39.webp",1,1);

//Found
        this.questionCreator("NBA reited play nicknamed \"Chuck\", \"Sir Charles\", and \"The Round Mound of Rebound\".","Charles Barkley","Charles Barkleyy","Allen Iverson","Sam James","Charles Barkley","Barkley established himself as one of the National Basketball Association's most dominating power forwards. He was drafted by the Philadelphia 76ers with the 5th pick of the 1984 NBA Draft.","e_p_40.webp",1,1);

//Found
        this.questionCreator("Retired NBA player who played 20 seasons in the National Basketball Association for the Milwaukee Bucks and Lakers.","Kareem Abdul-Jabbar","Rey Allen","Larry Bird","Kevin Love","Kareem Abdul-Jabbar","During his career as a center, Abdul-Jabbar was a record six-time NBA Most Valuable Player, a record 19-time NBA All-Star, a 15-time All-NBA selection, and an 11-time NBA All-Defensive Team member.","e_p_41.webp",1,1);

//Found
        this.questionCreator("Reggie Miller is a ?","NBA Player","Golf Player","Soccer Player","Baseball Player","NBA Player","One of the greatest shooters and scorers ever -- and a guy who's overrated thanks to a couple of high-profile playoff moments against the Knicks.","e_p_42.webp",1,1);

//Found
        this.questionCreator("Michael Jordan is a ?","NBA Player","Golf Player","Soccer Player","Baseball Player","NBA Player","Michael Jeffrey Jordan, also known by his initials, MJ, is an American former professional basketball player. He played 15 seasons in the National Basketball Association for the Chicago Bulls and Washington Wizards.","e_p_43.webp",1,1);

//Found
        this.questionCreator("Famous NBA player who said \"I can accept failure, everyone fails at something. But I can't accept not trying.\"","Michael Jordan","Michael Johnson","Magic Johnson","Allen Iverson","Michael Jordan","Jordan was born in Brooklyn, New York, to Deloris (née Peoples), who worked in banking, and James R. Jordan Sr., an equipment supervisor.","e_p_44.webp",1,1);

//Found
        this.questionCreator("Which famous explorer discovered Cuba?","Christopher Columbus","Christupher Columbus","George Bush","Adolf Hitler","Christopher Columbus","The island of Cuba was inhabited by various Mesoamerican cultures prior to the arrival of the Spanish explorer Christopher Columbus in 1492. After Columbus' arrival, Cuba became a Spanish colony, ruled by a Spanish governor in Havana.","e_p_45.webp",1,1);

//Found
        this.questionCreator("What was the name of the German leader during World War II?","Adolf Hitler","Poppy","Christopher Columbus","Alan Turing","Adolf Hitler","Adolf Hitler was leader of Nazi Germany, first as Chancellor from 1933 until 1934. He later became Germany's Führer from 1934 until his suicide in 1945. Hitler came to power during Germany's period of crisis after the Great War.","e_p_46.webp",1,1);

//Found
        this.questionCreator("What is Prince Harry's real first name?","Henry","Hanry","Hinry","Herry","Henry","Prince Harry, Duke of Sussex, KCVO, ADC is a member of the British royal family. He is the younger son of Charles, Prince of Wales, and Diana, Princess of Wales, and is sixth in the line of succession to the British throne.","e_p_47.webp",1,1);

//Found
        this.questionCreator("Who was the ex-peanut farmer who became a US President?","Jimmy Carter","Barack Obama","Donald Trump","Abraham Lincoln","Jimmy Carter","That all changed in 1976 when local boy and peanut farmer Jimmy Carter, who had been elected governor of the State of Georgia in 1970, won the Democratic Party's presidential nomination and, to the surprise of many, Plains became the hometown of the 39th President of the United States.","e_p_48.webp",1,1);

//Found
        this.questionCreator("What was Elvis Presley's middle name?","Aaron","Aokla","Asunta","Aoili","Aaron","Elvis was named after his father, Vernon Elvis Presley, and Mr. Presley's good friend in Tupelo, Aaron Kennedy.","e_p_49.webp",1,1);

//Found
        this.questionCreator("Who declared he had 'a dream' where all Americans would live as equals?","Martin Luther King","Abraham Lincoln","George W. Bush","Hilary Clinton","Martin Luther King","Martin Luther King Jr., gives his 'I Have a Dream' speech to a crowd before the Lincoln Memorial during the Freedom March in Washington, DC, on August 28, 1963.","e_p_50.webp",1,1);

//Found
        this.questionCreator("Which Bill formed Microsoft?","Gates","Ggates","Geed","Torvalds","Gates","Microsoft was founded by Bill Gates and Paul Allen on April 4, 1975, to develop and sell BASIC interpreters for the Altair 8800.","e_p_51.webp",1,1);

//Found
        this.questionCreator("One of the most notorious gangster of the philippines.","Emilio Changco","Mark Santio","Alas Akar","Kaka Balo","Emilio Changco","Emilio Changco was a pirate gang leader based out of Manila Bay in the Philippines. He was responsible for many of the ships that went missing in the area. He specialized in piracy-for-order, where a client would choose a ship and the Changco Gang would seize it, transfer the cargo, and then sell the ship.","e_p_52.webp",1,1);

//Found
        this.questionCreator("In the Disney film Aladdin, who did the voice for the character of Jasmine?","Linda Larkin","Anne Saner","Camille Oala","Megan Stat","Linda Larkin","Linda Larkin (born March 20, 1970) is an American actress and voice actress. She is best known for voicing Princess Jasmine in Disney's 1992 animated feature film, Aladdin.","e_p_53.webp",1,1);

//Found
        this.questionCreator("According to Walt, what Hollywood movie star was his inspiration for the witch in Snow White and the Seven Dwarfs?","Joan Crawford","Joana Craw","Kristen Stewart","Charlize Theron","Joan Crawford","The Queen has the allure of a vamp and the facial features of Joan Crawford. It was observed that the Queen’s gown resembles the outfit used by Helen Gahagan in the 1935 film,","e_p_54.webp",1,1);

//Found
        this.questionCreator("\"The first thing which I can record concerning myself is, that I was born. These are wonderful words. This life, to which neither time nor eternity can bring diminution - this everlasting living soul, began. My mind loses itself in these depths\". Which world famous person made this unexpected remark?","Groucho Marx","Donald Trump","John F. Kennedy","Mother Theresa","Groucho Marx","Groucho Marx. I think it's about time to announce that I was born at a very early age. Julius Henry Marx (2 October 1890 – 19 August 1977), primarily known as Groucho Marx, was an American comedian and actor, famous for his work in the Marx Brothers comedy team, and his solo film and television career.","e_p_55.webp",1,1);

//Found
        this.questionCreator("\"Be the change that you want to see in the world.\" Who was the great Indian leader who said this?","Mohandas Gandhi","Geronimo","Sitting Bull","Hiawatha","Mohandas Gandhi","Mohandas Karamchand Gandhi was an Indian activist who was the leader of the Indian independence movement against British rule.","e_p_56.webp",1,1);

//Found
        this.questionCreator("\"I have friends in overalls whose friendship I would not swap for the favour of the kings of the world.\" Born in 1847, who said this?","Thomas Edison","Eli Whitney","Isaac Newton","Dr Frankenstein","Thomas Edison","Inventor Thomas Edison created such great innovations as the practical incandescent electric light bulb and the phonograph. A savvy businessman, he held more than 1,000 patents for his inventions.","e_p_57.webp",1,1);

//Found
        this.questionCreator("\"The smallest act of kindness is worth more than the grandest intention.\" Which playwright and poet made this remark?","Oscar Wilde","William Wordsworth","F. Scott Fitzgerald","William Shakespeare","Oscar Wilde","Oscar Fingal O'Flahertie Wills Wilde (16 October 1854 – 30 November 1900) was an Irish poet and playwright. After writing in different forms throughout the 1880s, he became one of London's most popular playwrights in the early 1890s.","e_p_58.webp",1,1);

//Found
        this.questionCreator("\"When one door closes, another opens - but we often look so long and so regretfully upon the closed door that we do not see the one which has opened for us.\" Who said this?","Alexander Graham Bell","John Adams","Thomas Edison","Jack the Ripper","Alexander Graham Bell","was a Scottish-born scientist, inventor, engineer, and innovator who is credited with inventing and patenting the first practical telephone. He also founded the American Telephone and Telegraph Company (AT&T) in 1885.","e_p_59.webp",1,1);

//Found
        this.questionCreator("\"No pessimist ever discovered the secret of the stars, or sailed to an uncharted land, or opened a new heaven to the human spirit.\" Which great woman deprived of her sight and hearing, made this observation?","Helen Keller","Mary, Queen of Scots","Joan of Arc","Audrey Hepburn","Helen Keller","Helen Adams Keller was born on June 27, 1880 in Tuscumbia, Alabama. In 1882, she was stricken by an illness that left her blind and deaf. Beginning in 1887, Keller's teacher, Anne Sullivan, helped her make tremendous progress with her ability to communicate, and Keller went on to college, graduating in 1904.","e_p_60.webp",1,1);

//Found
        this.questionCreator("\"Forgiveness is the fragrance that the violet sheds on the heal (sic) that has crushed it.\" Which great American writer, forever linked to the Mississippi River, said this?","Mark Twain","Herman Melville","Nathaniel Hawthorne","William Faulkner","Mark Twain","Mark Twain, real name Samuel Langhorne Clemens, was an American writer, humorist, entrepreneur, publisher, and lecturer. Among his novels are The Adventures of Tom Sawyer and its sequel, the Adventures of Huckleberry Finn, the latter often called \"The Great American Novel\".","e_p_61.webp",1,1);

//Found
        this.questionCreator("\"It's not whether you get knocked down - it's whether you get up.\" Which outstanding American coach of National Football League has been credited for this remark that could apply equally to all aspects of a person's life?","Vince Lombardi","Mickey Mantle","Babe Ruth","Tommy Lasorda","Vince Lombardi","Born on November 30, 1835, in Florida, Missouri, Samuel L. Clemens wrote under the pen name Mark Twain and went on to author several novels, including two major classics of American literature: The Adventures of Tom Sawyer and Adventures of Huckleberry Finn.","e_p_62.webp",1,1);

//Found
        this.questionCreator("\"If you could kick the person in the pants responsible for most of your trouble, you wouldn't sit for a month.\" Which great American President said this?","Theodore Roosevelt","Richard Nixon","Foghorn Leghorn","Bill Clinton","Theodore Roosevelt","Theodore Roosevelt unexpectedly became the 26th president of the United States in September 1901, after the assassination of William McKinley. Young and physically robust, he brought a new energy to the White House, and won a second term on his own merits in 1904.","e_p_63.webp",1,1);

//Found
        this.questionCreator("\"To know even one life has breathed easier because you have lived - this is to have succeeded.\" Which poet and writer said this?","Ralph Waldo Emerson","William Wordsworth","William Shakespeare","F. Scott Fitzgerald","Ralph Waldo Emerson","Ralph Waldo Emerson (May 25, 1803 – April 27, 1882) was an American essayist, lecturer, philosopher, and poet who led the transcendentalist movement of the mid-19th century.","e_p_64.webp",1,1);

//Found
        this.questionCreator("Who painted the Mona Lisa?","Leonardo da Vinci","Donatello","Michelangelo","Adolf Hitler","Leonardo da Vinci","Leonardo da Vinci, (Italian: “Leonardo from Vinci”) (born April 15, 1452, Anchiano, near Vinci, Republic of Florence [Italy]—died May 2, 1519, Cloux [now Clos-Lucé], France), Italian painter, draftsman, sculptor, architect, and engineer whose genius, perhaps more than that of any other figure, epitomized the Renaissance humanist ideal. His Last Supper (1495–98) and Mona Lisa (c. 1503–19) are among the most widely popular and influential paintings of the Renaissance","e_p_65.webp",1,1);

//Found
        this.questionCreator("Where was Napoleon born?","Corsica","Majorca","Portugal","Sicily","Corsica","Napoléon Bonaparte was a Corsican statesman and military leader who rose to prominence .... Napoleon was born there on 15 August 1769, their fourth child and third son. A boy and girl were born first but died in infanc","e_p_66.webp",1,1);

//Found
        this.questionCreator("Where was Christopher Columbus born?","Italy","Spain","Portugal","France","Italy","Christopher Columbus, Italian Cristoforo Colombo, Spanish Cristóbal Colón, (born between August 26 and October 31?, 1451, Genoa [Italy]—died May 20, 1506, Valladolid, Spain), master navigator and admiral whose four transatlantic voyages (1492–93, 1493–96, 1498–1500, and 1502–04) opened the way for European exploration, exploitation, and colonization of the Americas","e_p_67.webp",1,1);

//Found
        this.questionCreator("What country was Hitler born?","Austria","Germany","Spain","Holland","Austria","Adolf Hitler was born in Braunau am Inn, Austria, on April 20, 1889.","e_p_68.webp",1,1);

//Found
        this.questionCreator("Who was the first president of America?","George Washington","Abraham Lincoln","John Adams","Thomas Jefferson","George Washington","George Washington (February 22, 1732 – December 14, 1799) was the first President of the United States (1789–1797), the commander in chief of the Continental Army during the American Revolutionary War, and one of the Founding Fathers of the United States.","e_p_69.webp",1,1);

//Found
        this.questionCreator("Father of plastic surgery.","Susruta","Hippocrates","Galen","Charaka","Susruta","Ancient Indian physician during BCE to 1000 BCE.","e_p_70.webp",1,1);

//Found
        this.questionCreator("Who discovered artificial radioactivity?","Freederic Joliot – Curie","Henri Bacquerel","Marie Curie","Pierre Curie","Freederic Joliot – Curie","A French physicist","e_p_71.webp",1,1);

//Found
        this.questionCreator("Who is independently discovered calculus?","G.W Leibniz","Francis Bacon","Christian Guygens","Leonardo Fibonacci","G.W Leibniz","A german rationalist philosopher and one of the great dendissance men of Western thought.","e_p_72.webp",1,1);

//Found
        this.questionCreator("Father of brain surgery.","Harvey Cushing","Pierre Broca","Galen","Christian Barnard","Harvey Cushing","American neuron surgeon, pathologist, writer and draftsman.","e_p_73.webp",1,1);

//Found
        this.questionCreator("Who discovered radio waves coming from the sky?","Karl Jansky","Max Weber","J.S Hey","Robert Watson-Watt","Karl Jansky","An American physicist and radio engineer.","e_p_74.webp",1,1);

//Found
        this.questionCreator("Who was the first Asian astronaut to go into space?","Phan tuan","John Glenn","Ravish Malhotra","Rakesh Sharma","Phan tuan","a retired Vietnam airforce aviator.","e_p_75.webp",1,1);

//Found
        this.questionCreator("Who was the first man to walk in space?","Alexer Leonov","John Glenn","John Young","Alan Shepard","Alexer Leonov","A retired Soviet/Russian cosmonaut , Air Force Major general, writer and artist.","e_p_76.webp",1,1);

//Found
        this.questionCreator("Who was the first Woman in space?","Valentina Tereshkova","Sally Ride","Anna Fisher","Rhea Seddon","Valentina Tereshkova","Retired Russian cosmonaut, engineer and politician.","e_p_77.webp",1,1);

//Found
        this.questionCreator("Who was the first man to reach antartica?","Fabian Gottlieb Bellingshausen","Thomas Cook","Paul Scott","John young","Fabian Gottlieb Bellingshausen","One of Russia’s most celebrated explorers and the discovered of the continent of Antarctica.","e_p_78.webp",1,1);

//Found
        this.questionCreator("Who was the father of the automobile?","Henry Ford","Rudolf Diesel","Gottlieb Daimler","Carl Benz","Henry Ford","He was so much more interested in machines and building things.","e_p_79.webp",1,1);

//Found
        this.questionCreator("Who was the father of Periodic Table of elements?","Dmitri Mendeleev","Alfred Nobel","Johann Baeyer","John Glenn","Dmitri Mendeleev","He is a Russian chemist and inventor.","e_p_80.webp",1,1);

//Found
        this.questionCreator("Who was the father of science chemotherapy, using chemical to treat diseases?","Paul Ehrlich","Leo Szilard","Hippocrates","Avicenna","Paul Ehrlich","He is a German , and won the Nobel prize in Physiology or Medicine for his exemplary work on immunology","e_p_81.webp",1,1);

//Found
        this.questionCreator("Who was the father of hydrogen bomb?","Edward Teller","Leo Szilard","J. Robert Oppenheimer","Otto Hahn","Edward Teller","On it’s 100th birthday in 1959, Edward Teller warned the oil industry about global warming.","e_p_82.webp",1,1);

//Found
        this.questionCreator("Who was the Father of geology in India?","D.N Wadia","Mihir Sen","K.S Valdiya","M.K Bose","D.N Wadia","One among the great geologist in geological.","e_p_83.webp",1,1);

//Found
        this.questionCreator("Who discovered the Third Law of thermodynamics?","Walther Nernst","L.E Boltzmann","Count Rumford","P.M.S Blackett","Walther Nernst","The 3rd law was developed by the chemist Walther Nernst during the years 1906–12, and is therefore often referred to as Nernst's theorem or Nernst's postulate. The third law of thermodynamics states that the entropy of a system at absolute zero is a well-defined constant.","e_p_84.webp",1,1);

//Found
        this.questionCreator("Programmer of this game.”?","Christopher Vistal","Gwyn Paul Dapiton","Michelle Biol","Rodelyn Sanoria","Christopher Vistal","A simple student","e_p_85.webp",1,1);

//Found
        this.questionCreator("Founder of Microsoft Corporation.","Bill Gates","Steve Jobs","Charles Babbage","Jerome Bruner","Bill Gates","William Henry Gates III an American Business magnate, investor, author, philanthropist and humanitarian.","e_p_86.webp",1,1);

//Found
        this.questionCreator("Chairman, chief execute officer, and a co-founder of Apple Inc.","Steve Paul Jobs","Thomas Savery","Charles Babbage","Henry Ford","Steve Paul Jobs","An American entrepreneur, business magnate, chairman and majority shareholder of Pixar.","e_p_87.webp",1,1);

//Found
        this.questionCreator("He was the painter of Mona Lisa.","Leonardo da Vinci","Michael Angelo","Alexander Bell","Karl Marx","Leonardo da Vinci","Was an Italian polymath of the Renaissance, whose areas of interest included invention, painting sculpting and architecture.","e_p_88.webp",1,1);

//Found
        this.questionCreator("First president of the Philippines.","Emilio Aguinaldo","Manuel L. Quezon","Apolinario Mabini","Manuel L. Roxas","Emilio Aguinaldo","He held that office until 1901 When he was captured by United Stated forces during the Philippine – American war (1899-1902)","e_p_89.webp",1,1);

//Found
        this.questionCreator("Who published the theory of special relativity on 1905.","Albert Einstein","Steven Weinberg.","Niels Bohr","Max Planck","Albert Einstein","a German-born theoretical physicist.","e_p_90.webp",1,1);

//Found
        this.questionCreator("Father of Modern Science, astronomer and physicist.","Galileo Galilei","Hands Bethe","James Chadwik","Lev Landau","Galileo Galilei","who invented telescope","e_p_91.webp",1,1);

//Found
        this.questionCreator("English poet and playwright.","William Shakespeare","Winston Churchill","Mikhail Gorbachev","Thomas Jefferson","William Shakespeare","greatest writer in English Language","e_p_92.webp",1,1);

//Found
        this.questionCreator("American President during civil War.","Abraham Lincoln","Isaac Newton","Charaka","Steven S.","Abraham Lincoln","help end slavery","e_p_93.webp",1,1);

//Found
        this.questionCreator("First President of USA (1732 0 1799).","George Washington","George W. Bush","Bill Clinton","Gerald Ford","George Washington","Was the Commander in chief of the continental forces during the American wars of independence","e_p_94.webp",1,1);

//Found
        this.questionCreator("Classical Greek Philosopher.","Socrates","John Locke","David hume","Ludwig","Socrates","one of the founders of western Philosophy.","e_p_95.webp",1,1);

//Found
        this.questionCreator("Ancient Greek Philosopher and Scientist.","Aristotle","Immanuel Kant","Albert Camus","Thomas Aquinas","Aristotle","Aristotle was an ancient Greek philosopher and scientist born in the city of Stagira, Chalkidiki, in the north of Classical Greece","e_p_96.webp",1,1);

//Found
        this.questionCreator("Developed theory of evolution.","Charles Darwin","Karl Marx","Martin Luther","Nelson Mandela","Charles Darwin","An English Natural Scientist.","e_p_97.webp",1,1);

//Found
        this.questionCreator("American Boxer human rights activist , and philanthropist.","Mohammed Ali","Mike Tyson","Joe Louis","Jack Johnson","Mohammed Ali","He is widely regarded as one of the most significant celebrated sports figures of the 20th century.","e_p_98.webp",1,1);

//Found
        this.questionCreator("According to him Music is a higher revelation than all Wisdom and Philosophy.","Ludwigvan beethoven","Henry Ford","Lao Tzu","Nikola Tesla","Ludwigvan beethoven","A german Composer and pianist.","e_p_99.webp",1,1);

//Found
        this.questionCreator("The 16th president of the Philippines.","Rodrigo Duterte","Ninoy Aquino","Gloria Macapagal Arroyo","Joseph Estrada","Rodrigo Duterte","The is known as Digong , and the 1st president from the southernmost island of country, Mindanao","e_p_100.webp",1,1);

//Found
        this.questionCreator("Father of the Philippine Revolution.","Andre’s Bonifacio","Emilio Jacinto","Juan Luna","Pedro Patemo","Andre’s Bonifacio","A Filipino Revolutionary leader and the president of the Tagalog Republic.","e_p_101.webp",1,1);

//Found
        this.questionCreator("Who led the first marine expedition to navigate around the earth?","Ferdinand Magellan","Marco Polo","Ptolemy","Nicholas of Celsa","Ferdinand Magellan","A portuguese explorer who organized the spanish expedition to the East Indies from 1519-1522","e_p_102.webp",1,1);

//Found
        this.questionCreator("He is widely considered one of the greatest heroes of the Philippines.","Jose Rizal","Ferdinand Magellan","Lapu-Lapu","Erik Acharius","Jose Rizal","Also known as jose Protasio Rizal Mercado Y Alonso Realonda, A Filipino nationalist and polymath during the tailend of the spanish colonial period.","e_p_103.webp",1,1);

//Found
        this.questionCreator("Who was the 10th President of the Philippines?","Ferdinand Marcos","Diosdado Macapagal Arroyo","Benigno Aquino III","Fidel V. Ramos","Ferdinand Marcos","A Filipino politician and kleptocrat. He ruled as a dictator.","e_p_104.webp",1,1);

//Found
        this.questionCreator("Inventor of SALT lamp.","Aisa Mijeno","Pedro Flores","Thomas Edison","Magdalena Villarus","Aisa Mijeno","A Filipino scientist and entrepreneur.","e_p_105.webp",1,1);

//Found
        this.questionCreator("Who was the Filipino broadcaster and inventor?","Ernesto Baron","Hery Omaga-Diaz","Noli de Castro","Dong Puno","Ernesto Baron","Best known as the weatherman in ABS-CBN new program TV Patrol and also known as the walking Encyclopedia.","e_p_106.webp",1,1);

//Found
        this.questionCreator("Father of Artificial Intelligence.","Alan Turing","John von Neuman","Warren Mc cullach","John mc Carthy","Alan Turing","Alan turing was a logician mathematician and comp. scientist was born in London in 1912,","e_p_107.webp",1,1);

//Found
        this.questionCreator("Who invented the term “SCIENCE”?","William whewell","Francis Bacon","Roger Bacon","Plato","William whewell","An English polymath scientist, Anglican priest, philosopher and theologian.","e_p_108.webp",1,1);

//Found
        this.questionCreator("Who was the father of Periodic Table of elements?","Dmitri Mendeleev","Alfred Nobel","Johann Baeyer","John Glenn","Dmitri Mendeleev","He is a Russian chemist and inventor.","e_p_109.webp",1,1);


    }

    private void addPeopleModerate()
    {
        //check

//Found
        this.questionCreator("Who invented Air Conditioning?","Willis Carrier","Abel Abner","Alan Abraham","Benedict Bennet","Willis Carrier","In 1902, the first modern electrical air conditioning unit was invented by Willis Carrier in Buffalo, New York.[citation needed] After graduating from Cornell University, Carrier found a job at the Buffalo Forge Company.","m_p_1.webp",1,2);

//Found
        this.questionCreator("Who invented Atomic Bomb?","J. Robert Oppenheimer","Bertram Charles","Daniel Edmund","Ferdinand Harvey","J. Robert Oppenheimer","Oppenheimer was the wartime head of the Los Alamos Laboratory and is among those who are credited with being the \"father of the atomic bomb\" for their role in the Manhattan Project, the World War II undertaking that developed the first nuclear weapons used in the atomic bombings of Hiroshima and Nagasaki.","m_p_2.webp",1,2);

//Found
        this.questionCreator("Who is the inventor of Ball-Point Pen?","Ladislo Biro","Homer Horace","Jonathan Kirk","Luther Miles","Ladislo Biro","was a Hungarian-Argentine inventor, who patented the first commercially successful modern ballpoint pen. The first ball point pen was invented roughly fifty years earlier by John J. Loud but it did not attain commercial success.","m_p_3.webp",1,2);

//Found
        this.questionCreator("Who invented the internet?","Robert E. Kahn and Vint Cerf","Milton Milo and Osmond Paul","Richard Seth and Timothy Wesley","Winfred Trenton and Rodney Oscar","Robert E. Kahn and Vint Cerf","The history of the Internet begins with the development of electronic computers in the 1950s. Initial concepts of wide area networking originated in several computer science laboratories in the United States, United Kingdom, and France.","m_p_4.webp",1,2);

//Found
        this.questionCreator("Who invented the mobile phone?","Martin Cooper","Paul Percival","Orson Osbert","Llewellyn Linus","Martin Cooper","Martin Cooper (inventor) Inventing handheld cellular mobile phone making world's first handheld cellular mobile phone call. Martin \"Marty\" Cooper (born December 26, 1928) is an American engineer.","m_p_5.webp",1,2);

//Found
        this.questionCreator("Who invented Electric Chair?","Alfred P. Southwick","Kevin Judson","Ignatius Graham","Ernest Dudley","Alfred P. Southwick","Alfred P. Southwick (1826–1898), was a steam-boat engineer, dentist and inventor from Buffalo, New York. He is credited with inventing the electric chair as a method of legal execution.","m_p_6.webp",1,2);

//Found
        this.questionCreator("Who invented ceiling fans?","Philip Diehl","Donald Elwood","Clark Burton","Arthur Casey","Philip Diehl","The electrically powered ceiling fan was invented in 1882 by Philip Diehl. He had engineered the electric motor used in the first electrically powered Singer sewing machines, and in 1882 he adapted that motor for use in a ceiling-mounted fan.","m_p_7.webp",1,2);

//Found
        this.questionCreator("Who invented Computer keyboard?","Christopher Latham Sholes","William Sturgeon","Thomas Davenport","Michael Faraday","Christopher Latham Sholes","Christopher Latham Sholes. Christopher Latham Sholes (February 14, 1819 – February 17, 1890) was an American inventor who invented the QWERTY keyboard, and along with Samuel W. Soule, Carlos Glidden and John Pratt, has been contended as one of the inventors of the first typewriter in the United States.","m_p_8.webp",1,2);

//Found
        this.questionCreator("Who is inventor of Insulin?","Frederick Banting & Charles Best","Immanuel Ivor & Jeremy Kendrick","Laurence Matthias & Neal Orson","Randall Reynold & Ryan Russell","Frederick Banting & Charles Best","Canadian physician Frederick Banting and medical student Charles H. Best discovered the hormone insulin in pancreatic extracts of dogs. On July 30, 1921, they injected the hormone into a diabetic dog and found that it effectively lowered the dog's blood glucose levels to normal.","m_p_9.webp",1,2);

//Found
        this.questionCreator("One of the inventor of Television?","John Logie Baird","Thomas Stuart","Tracy Tyrone","Vincent Walter","John Logie Baird","In 1932, while in England to raise money for his legal battles with RCA, Farnsworth met with John Logie Baird, a Scottish inventor who had given the world's first public demonstration of a working television system in London in 1926, using an electro-mechanical imaging system, and who was seeking to develop electronic television receivers.","m_p_10.webp",1,2);

//Found
        this.questionCreator("Who is the inventor of medical Thermometer?","Thomas Allbutt","William Vaughan","Trenton Walter","Stanley Sheldon","Thomas Allbutt","Sir Thomas Clifford Allbutt. Sir Thomas Clifford Allbutt, (born July 20, 1836, Dewsbury, Yorkshire, Eng.—died Feb. 22, 1925, Cambridge, Cambridgeshire), English physician, the inventor of the short clinical thermometer.","m_p_11.webp",1,2);

//Found
        this.questionCreator("Who is considered as the inventor of Video games?","Ralph Baer","Roland Silvester","Peter Preston","Nicholas Murray","Ralph Baer","Inventor Ralph Baer, The 'Father Of Video Games,' Dies At 92. German-American game developer Ralph Baer shows the prototype of the first games console which was invented by him during a press conference on the Games Convention Online in Leipzig, Germany in 2009. Baer died on Saturday.","m_p_12.webp",1,2);

//Found
        this.questionCreator("The name of the inventor of Telephone—","Alexander Graham Bell","Lynn Norbert","Jesse Lincoln","Francis Homer","Alexander Graham Bell","Alexander Graham Bell is the father of the telephone. After all it was his design that was first patented, however, he was not the first inventor to come up with the idea of a telephone","m_p_13.webp",1,2);

//Found
        this.questionCreator("Who created Automobile, internal combustion (gasoline-powered)?","Karl Benz","Jared Irvin","Isaac Kendall","Leonard Maxwell","Karl Benz","It is generally acknowledged that the first really practical automobiles with petrol/gasoline-powered internal combustion engines were completed almost simultaneously by several German inventors working independently: Karl Benz built his first automobile in 1885 in Mannheim.","m_p_14.webp",1,2);

//Found
        this.questionCreator("When was the first Fire-engine created?","in 1518 by Anthony Blatner","in 1511 by Melvin  Quentin","in 1513 by Rick  Sanford","in 1519 by Stanley  Silas","in 1518 by Anthony Blatner","The first fire pump that was recorded of was built in 1518 in Augsburg, Germany and in 1657 in Nuremberg, Germany. The fire engine was actually an Egytian invention. Boston imported the first fire engine to reach America in 1679. The first inventor in the US to build a fire engine was Thomas Lote.","m_p_15.webp",1,2);

//Found
        this.questionCreator("Who was the inventor of Remote Controls?","Nikola Tesla","Jacques Breguet","Louis Breguet","Johny Dua","Nikola Tesla","Nikola Tesla created one of the world's first wireless remote controls, which he unveiled at Madison Square Garden in New York City in 1898. He called his fledgling system, which could be used to control a range of mechanical contraptions, a \"teleautomaton.\"","m_p_16.webp",1,2);

//Found
        this.questionCreator("Known as the inventor of Typewriter?","William A Burt","Troy Tristram","Wilson Virgil","Nicholas Reuben","William A Burt","William Austin Burt was an American inventor, legislator, surveyor, and millwright. He was the inventor, maker and patentee of the first typewriter constructed in America. He is referred to as the \"father of the typewriter\".","m_p_17.webp",1,2);

//Found
        this.questionCreator("Who is credited for the invention of Chocolate bar?","Francois-Louis Cailler","Mark Monroe","Lancelot Marshall","Geoffrey Emmet","Francois-Louis Cailler","François-Louis Cailler (1796–1852) was the first Swiss producer of chocolate, founder of the Cailler chocolate company.","m_p_18.webp",1,2);

//Found
        this.questionCreator("Who invented the Postage stamp (adhesive)?","James Chalmers","Benedict Colin","Adrian Bret","Floyd Lewis","James Chalmers","James Chalmers (2 February 1782, Arbroath – 26 August 1853, Dundee) was a Scotsman (buried on 1 September 1853 in plot 526 Dundee Howff) who it was claimed, by his son, was the inventor of the adhesive postage stamps.","m_p_19.webp",1,2);

//Found
        this.questionCreator("What is the name of the inventor of Postcard?","John P Charlton","Noah Maximilian","Saul Quincy","Wilfred Theodore","John P Charlton","The first commercially produced card was created in 1861 by John P. Charlton of Philadelphia, who patented a postal card, and sold the rights to Hymen Lipman, whose postcards, complete with a decorated border, were labeled \"Lipman's postal card\".","m_p_20.webp",1,2);

//Found
        this.questionCreator("Who created the first Lego?","Kirk Christiansen","Saul Quincy","Wilfred Theodore","John P Charlton","Kirk Christiansen","The Lego Group began in the workshop of Ole Kirk Christiansen (1891–1958), a carpenter from Billund, Denmark, who began making wooden toys in 1932. In 1934, his company came to be called \"Lego\", derived from the Danish phrase leg godt, which means \"play well\".","m_p_21.webp",1,2);

//Found
        this.questionCreator("Who was the inventor of computer mouse?","Douglas Engelbart","W. F. Cooke","Charles Wheatstone","Jeffery Gillespie","Douglas Engelbart","Inventor Douglas Engelbart holding the first computer mouse, showing the wheels that make contact with the working surface","m_p_22.webp",1,2);

//Found
        this.questionCreator("Father of Rap.","Gil Scott-Heron","Francis Crick","James Watson","Rosalind Franklin","Gil Scott-Heron","Founding Father Of Rap, Gil Scott-Heron, Dead. Songwriter, performer, novelist and poet Gil Scott-Heron died Friday at age 62. He was best known for a work he first recorded in 1970, \"The Revolution Will Not Be Televised.\"","m_p_23.webp",1,2);

//Found
        this.questionCreator("What is the name of the inventor of Camera (flexible roll film)Photographic film (using celluloid)?","George Eastmann","Yosef Owens","Brandon Sutton","Amari Solis","George Eastmann","By 1889 the George Eastman company had developed a roll film of celluloid coated with photographic emulsion for use in its Kodak still camera. …the Kodak camera, introduced by George Eastman in 1888. …in Rochester in 1880 by George Eastman, who perfected the newly developed method of making photographic dry plates.","m_p_24.webp",1,2);

//Found
        this.questionCreator("The Swiss Army knife is invented by—","Karl Elsener","Karly Petersen","Kai Le","Jazmyn Estes","Karl Elsener","Originating in Ibach, Switzerland, the Swiss Army knife was first produced in 1891 after the company, Karl Elsener, which later became Victorinox, won the contract to produce the Swiss Army's Modell 1890 knife from the previous German manufacturer.","m_p_25.webp",1,2);

//Found
        this.questionCreator("When was the Electric washing machine invented?","1908","1902","1805","1902","1908","The first completely electric-powered washing machine was named \"The Thor\", invented by Alva J. Fisher. It was introduced in 1908 by the Hurley Machine Company of Chicago, Illinois.","m_p_26.webp",1,2);

//Found
        this.questionCreator("Who is the inventor of Penicillin?","Alexander Fleming","Fernanda Acosta","Martin Long","Kendal Hickman","Alexander Fleming","Sir Alexander Fleming FRS FRSE FRCS (6 August 1881 – 11 March 1955) was a Scottish physician, microbiologist, and pharmacologist. His best-known discoveries are the enzyme lysozyme in 1923 and the world's first antibiotic substance benzylpenicillin (Penicillin G) from the mould Penicillium notatum in 1928.","m_p_27.webp",1,2);

//Found
        this.questionCreator("Who invented Machine gun?","Richard Jordan Gatling","Houston Townsend","Dax Tran","Megan Lam","Richard Jordan Gatling","he Gatling gun, patented in 1861 by Richard Jordan Gatling, was the first to offer controlled, sequential fire with mechanical loading.","m_p_28.webp",1,2);

//Found
        this.questionCreator("Who invented Rocket, liquid fuelled (first launch)?","Robert Goddard","Konner Meyers","Lawrence Howe","Cortez Rice","Robert Goddard","First flight. Goddard launched the first liquid-fueled (gasoline and liquid oxygen) rocket on March 16, 1926, in Auburn, Massachusetts. Present at the launch were his crew chief, Henry Sachs, Esther Goddard, and Percy Roope, who was Clark's assistant professor in the physics department.","m_p_29.webp",1,2);

//Found
        this.questionCreator("Who is the inventor of submarine?","Cornelis Drebbel","John Crane","Trevin Buckley","Sariah Alvarez","Cornelis Drebbel","The first submersible of whose construction there exists reliable information was designed and built in 1620 by Cornelis Drebbel, a Dutchman in the service of James I of England. It was propelled by means of oars.","m_p_30.webp",1,2);

//Found
        this.questionCreator("Who is the inventor of Lighter?","Johann Wolfgang Döbereiner","Jordon Mcpherson","Antoine Leach","Callie Fields","Johann Wolfgang Döbereiner","The first lighters were converted flintlock pistols that used gunpowder. One of the first lighters was invented by the German chemist named Johann Wolfgang Döbereiner in 1823 and was often called Döbereiner's lamp.","m_p_31.webp",1,2);

//Found
        this.questionCreator("When was Pacemaker (artificial, implantable) invented?","1958","1950","1930","1924","1958","Wilson Greatbatch, an American electrical engineer, invented the first implantable cardiac pacemaker, in 1958.","m_p_32.webp",1,2);

//Found
        this.questionCreator("Who invented Microscope (compound)?","Zacharias Janssen","Jamarcus Hoffman","Duncan Shah","Nora Zamora","Zacharias Janssen","Some historians argue Hans Janssen helped build the microscope, as Zacharias was a teenager in the 1590s. Reproduction of first compound microscope made by Hans and Zacharias Janssen, circa 1590.","m_p_33.webp",1,2);

//Found
        this.questionCreator("Who is the inventor of Calculator (pocket)?","Jack Kilby","Corbin Rush","Julianna Cooley","Jaslene Glass","Jack Kilby","In December of 1972, TI files a patent application for the hand-held calculator with the inventors listed as Jack Kilby, Jerry Merryman, and James Van Tassel. HP introduces their first pocket calculator, the HP-35, the world's first pocket calculator with scientific (transcendental) functions.","m_p_34.webp",1,2);

//Found
        this.questionCreator("When was the Battery (dry cell) invented?","1866","1843","1865","1861","1866","Dry Cell Battery. In 1866, French engineer Georges Leclanché developed the first zinc-carbon battery, providing for low-cost and practical storage of electricity. ... In 1866, he patented a wet cell battery that marked a major innovation in the field.","m_p_35.webp",1,2);

//Found
        this.questionCreator("Who invented Bicycle (pedal)?","Kirkpatrick Macmillan","Sergio Taylor","Kash Brooks","Nola Monroe","Kirkpatrick Macmillan","Kirkpatrick Macmillan. Kirkpatrick Macmillan (2 September 1812 in Keir, Dumfries and Galloway – 26 January 1878 in Keir) was a Scottish blacksmith. He is generally credited with inventing the pedal driven bicycle.","m_p_36.webp",1,2);

//Found
        this.questionCreator("Retired NBA player who admit that he is a gay","Jason Collins","Artico Bryant","Joseph Michel","Jacques E. Montgolfier","Jason Collins","After the 2012–13 NBA season concluded, Collins publicly came out as gay. He became a free agent and did not play again until February 2014, when he signed with the Nets and became the first publicly gay athlete to play in any of four major North American pro sports leagues.","m_p_37.webp",1,2);

//Found
        this.questionCreator("Who is the inventor of Match (safety, phosphorous)?","Johan Edvard Lundstrom","Walter Compton","Peyton Hurst","Shaun Conrad","Johan Edvard Lundstrom","Johan Edvard Lundström (1815–1888) further developed Swedish chemist Gustaf Erik Pasch's idea and applied for the patent on the phosphor-free safety match. His younger brother, Carl Frans Lundström (1823–1917) was an entrepreneur and industrialist with bold ideas.","m_p_38.webp",1,2);

//Found
        this.questionCreator("Who is the earliest civil engineer?","Imhotep","Imhotepp","Petohmn","Plato","Imhotep","The earliest civil engineer known by name is Imhotep. As one of the officials of the Pharaoh, Djosèr, he probably designed and supervised the construction of the Pyramid of Djoser (a Step Pyramid) at Saqqara in Egypt around 2630-2611 BC.","m_p_39.webp",1,2);

//Found
        this.questionCreator("Who invented Genetics?","Gregor Johann Mendel","Noah Lindsey","Cherish Dean","Raymond Parks","Gregor Johann Mendel","Modern biology began with the work of the Augustinian friar Gregor Johann Mendel. His work on pea plants, published in 1866,what is now Mendelian inheritance. Some theories of heredity suggest in the centuries before and for several decades after Mendel's work.","m_p_40.webp",1,2);

//Found
        this.questionCreator("When was the Gas mask invented?","1915","1913","1912","1910","1915","The first effective filtering activated charcoal gas mask in the world was invented in 1915 by Russian chemist Nikolay Zelinsky. In the first gas masks of World War I, it was initially found that wood charcoal was a good absorbent of poison gases.","m_p_41.webp",1,2);

//Found
        this.questionCreator("Founder of Amazon","Jeff Bezos","Robert Norton Noyce","Gordon Moore","Elaina Werner","Jeff Bezos","It's been an eventful few months of Jeff Bezos, founder and CEO of Amazon. In July, Bezos reached a new milestone: his net worth hit $150 billion, making him, as Bloomberg put it, the richest person in modern history.","m_p_42.webp",1,2);

//Found
        this.questionCreator("Who is the inventor of Radio telescope?","Grote Reber","Fernando Berg","Kathryn Miller","Kathryn Miller","Grote Reber","Grote Reber, (born December 22, 1911, Chicago, Illinois, U.S.—died December 20, 2002, Tasmania, Australia), American astronomer and radio engineer who built the first radio telescope and was largely responsible for the early development of radio astronomy, which opened an entirely new research front in the study of the universe.","m_p_43.webp",1,2);

//Found
        this.questionCreator("What is the name of the inventor of Cash register?","James J Ritty","Danielle Farley","Renee Webster","Anderson Hayes","James J Ritty","With the help of James' brother John Ritty, they patented it in 1883. It was called Ritty's Incorruptible Cashier and it was invented for the purpose to stop cashiers of pilfering and eliminating employee theft or embezzlement. Early mechanical registers were entirely mechanical, without receipts.","m_p_44.webp",1,2);

//Found
        this.questionCreator("Who invented X-rays?","Wilhelm Roentgen","Ryann Vasquez","Angela Reid","Cora Allen","Wilhelm Roentgen","X-rays were discovered in 1895 by Wilhelm Conrad Roentgen (1845-1923) who was a Professor at Wuerzburg University in Germany. Working with a cathode-ray tube in his laboratory, Roentgen observed a fluorescent glow of crystals on a table near his tube.","m_p_45.webp",1,2);

//Found
        this.questionCreator("Who invented Aerosol can?","Erik Rotheim","Melanie Waters","Denisse Cervantes","Adison Garcia","Erik Rotheim","The concepts of aerosol probably go as far back as 1790. The first aerosol spray can patent was granted in Oslo in 1927 to Erik Rotheim, a Norwegian chemical engineer, and a United States patent was granted for the invention in 1931. The patent rights were sold to a United States company for 100,000 Norwegian kroner.","m_p_46.webp",1,2);

//Found
        this.questionCreator("Who is the inventor of Morphine?","Friedrich W.A Serturner","Edward Esparza","Daisy Kennedy","Terrence Hooper","Friedrich W.A Serturner","Friedrich Wilhelm Adam Sertürner (19 June 1783 – 20 February 1841) was a German pharmacist and a pioneer of Alkaloid chemistry. He is best known for his discovery of morphine in 1804.","m_p_47.webp",1,2);

//Found
        this.questionCreator("Who is the creator of Jet engine?","Frank Whittle","Carter Dalton","Ava Clay","Monica Gay","Frank Whittle","Air Commodore Sir Frank Whittle OM KBE CB FRS FRAeS was a British Royal Air Force air officer. He is credited with single-handedly inventing the turbojet engine. A patent was submitted by Maxime Guillaume in 1921 for a similar invention; however, this was technically unfeasible at the time.","m_p_48.webp",1,2);

//Found
        this.questionCreator("When was Albert Einstein born?","14 March 1879","14 March 1875","12 Febuary 1879","12 January 1872","14 March 1879","Albert Einstein was born in Ulm, in the Kingdom of Württemberg in the German Empire, on 14 March 1879. His parents were Hermann Einstein, a salesman and engineer, and Pauline Koch.","m_p_49.webp",1,2);

//Found
        this.questionCreator("Where was Bill Gates born?","Seattle, Washington","Deerhaven","Bygriffin","Esterbeach","Seattle, Washington","Gates was born in Seattle, Washington, on October 28, 1955. He is the son of William H. Gates Sr.(1925)","m_p_50.webp",1,2);

//Found
        this.questionCreator("Who is Bill Gates father?","William H. Gates, Sr","Alejandro Gates","Bryce Gates","Dallas Gates","William H. Gates, Sr","William Henry Gates II, better known as Bill Gates Sr., is an American retired attorney and philanthropist and author of the book Showing Up for Life: Thoughts on the Gifts of a Lifetime. He is the father of Microsoft co-founder Bill Gates.","m_p_51.webp",1,2);

//Found
        this.questionCreator("Who was Bill Gates mother?","Mary Maxwell Gates","Cindy Gates","Theresa Gates","Iris Gates","Mary Maxwell Gates","Mary Maxwell Gates was an American businesswoman, and served 18 years on the University of Washington board of regents.","m_p_52.webp",1,2);

//Found
        this.questionCreator("When did Bill Gates step down as chairman of Microsoft?","February 2014","February 2011","February 2012","February 2013","February 2014","Gates has not worked at Microsoft since 2008, when he stepped away to focus on his foundation. The Gates announcement is tied to Microsoft announcing that Satya Nadella is the new CEO of the company. John Thompson, who led the search for a new CEO, will become the chairman.","m_p_53.webp",1,2);

//Found
        this.questionCreator("Where did Ferdinand Marcos live in exile?","Hawaii","Watermount","Blackgrass","Vertmarble","Hawaii","He died on September 28, 1989 in exile in Honolulu, Hawaii.","m_p_54.webp",1,2);

//Found
        this.questionCreator("Which politician's wife was acquitted in 1990 of defrauding US banks?","Imelda Marcos","Lilia Kemp","Juliette Vega","Everett Rose","Imelda Marcos","It is also a case about fraud before a United States court, about an obstruction of justice considered in this very courthouse by the defendant Imelda Marcos and by the co-defendant Adnan Khashoggi to deceive the court and to hang on to the fruits of Imelda Marcos’s fraud: her four buildings in Manhattan.","m_p_55.webp",1,2);

//Found
        this.questionCreator("What was Mother Teresa's real first name?","Anjezë","Janelle Weeks","Taliyah Kelley","Maddox Graves","Anjezë","Mother Teresa, known in the Roman Catholic Church as Saint Teresa of Calcutta, was an Albanian-Indian Roman Catholic nun and missionary. She was born in Skopje, then part of the Kosovo Vilayet of the Ottoman Empire.","m_p_56.webp",1,2);

//Found
        this.questionCreator("Who was credited with popularizing the term rock 'n' roll?","Alan Freed","Emmett Malone","Maddox Graves","Kevin Velez","Alan Freed","Disc jockey Alan Freed is widely credited with coining the term “rock and roll” to describe the uptempo black R&B records he played as early as 1951 on Cleveland radio station WJW.","m_p_57.webp",1,2);

//Found
        this.questionCreator("A men who discovered the gravity when he saw a falling apple.","Isaac Newton","Albert Einstein","Nikola Tesla","Thomas Edison","Isaac Newton","Sir Isaac Newton was an English mathematician and mathematician and physicist who lived from 1642-1727. The legend is that Newton discovered Gravity when he saw a falling apple while thinking about the forces of nature.","m_p_58.webp",1,2);

//Found
        this.questionCreator("Who was the first black man to receive the Nobel Peace Prize?","Ralph Johnson Bunche","Marco Knox","Joaquin Strong","Nigel Miles","Ralph Johnson Bunche","For his peace mediation during the first Arab-Israeli war, American diplomat Ralph Joseph Bunche receives the Nobel Peace Prize in Oslo, Norway. Bunche was the first African American to win the prestigious award.","m_p_59.webp",1,2);

//Found
        this.questionCreator("Nigel Short was the youngest champion in which game in 1984?","Chess","Basketball","Ping pong","Archery","Chess","Short represented England in international team play for the first time at the 1983 European Team finals in Plovdiv. He was awarded the grandmaster title in 1984, aged 19—becoming the youngest grandmaster in the world at the time.","m_p_60.webp",1,2);

//Found
        this.questionCreator("What kind of games did Michelle Obama like to play?","Monopoly","Snake and ladder","Cards","Solitaire","Monopoly","Michelle LaVaughn Robinson Obama (born January 17, 1964) is an American lawyer, .... She and her family enjoyed playing games such as Monopoly, reading, and frequently saw extended family on both sides.","m_p_61.webp",1,2);

//Found
        this.questionCreator("What did Michael Jackson say instead of \"I do\" when he married Lisa Marie Presley?","Why not?","I do","Okay","Let\'s go","Why not?","While vacationing in the Dominican Republic, Jackson and Presley drove in a white minivan to the house of a local judge on May 26, 1994, paid a small fee, and tied the knot. \"Do you take this woman to be your wife?\" the officiant asked the groom. \"Why not?\".","m_p_62.webp",1,2);

//Found
        this.questionCreator("Where was Thomas Edison born?","Milan, Ohio","Montevideo","Sana\'a","Delhi","Milan, Ohio","Thomas Edison was born, in 1847, in Milan, Ohio, and grew up in Port Huron, Michigan. He was the seventh and last child of Samuel Ogden Edison Jr. (1804–1896, born in Marshalltown, Nova Scotia) and Nancy Matthews Elliott (1810–1871, born in Chenango County, New York).","m_p_63.webp",1,2);

//Found
        this.questionCreator("Edison and Mina had how many children together?","3","4","2","1","3","Mina would then have three children of her own-Madeline, Charles, and Theodore. Unlike Mary, Mina was up to the challenge of an already famous husband. While she adored him as Mary did, this adoration was not overwhelming.","m_p_64.webp",1,2);

//Found
        this.questionCreator("Chevy Chase was a professional in which sport?","Tennis","Basketball","Soccerr","Base Ball","Tennis","Cornelius Crane \"Chevy\" Chase (born October 8, 1943) is an American actor, comedian and writer. Born into a prominent New York family, Chase worked a variety of jobs before moving into comedy and began acting with National Lampoon.","m_p_65.webp",1,2);

//Found
        this.questionCreator("Shakespeare wrote \"Cruel only to be kind\" in what play?","Hamlet","Baker","Keyla","Ponce","Hamlet","\" I must be cruel only to be kind; Thus bad begins, and worse remains behind.\" The quotation is from Shakespeare's famous play Hamlet.","m_p_66.webp",1,2);

//Found
        this.questionCreator("Who first said \"The Games Afoot\" ?","William Shakespeare","Damaris Levine","Leon Benjamin","Harper Mathis","William Shakespeare","The phrase comes from Shakespeare's King Henry IV Part I, 1597: \"Before the game is afoot, thou still let'st slip.\" It is also a phrase used by Sherlock Holmes when pursuing a lead in a case.","m_p_67.webp",1,2);

//Found
        this.questionCreator("Elon Musk is co-founder, CEO, and product architect of what car company?","Tesla, Motors","Honda","Toyota","Hyundai","Tesla, Motors","Elon Musk is the co-founder, CEO and product architect at Tesla Motors, a company dedicated to producing affordable, mass-market electric cars as well as battery products and solar roofs. Musk oversees all product development, engineering and design of the company's products.","m_p_68.webp",1,2);

//Found
        this.questionCreator("Where was Elon Musk born?","Pretoria, Transvaal, South Africa","Seattle, Washington","Milan, Ohio","Nizhny Novgorod","Pretoria, Transvaal, South Africa","Elon Musk was born on June 28, 1971, in Pretoria, South Africa.","m_p_69.webp",1,2);

//Found
        this.questionCreator("During his childhood, Musk was an avid what?","Reader","Dancing","Basketball","Soccer","Reader","During his childhood he was an avid reader. At age 10, he developed an interest in computing with the Commodore VIC-20.","m_p_70.webp",1,2);

//Found
        this.questionCreator("How old was Elon Musk when he developed an interest in computing with the Commodore VIC-20?","10","15","23","7","10","During his childhood he was an avid reader. At age 10, he developed an interest in computing with the Commodore VIC-20.","m_p_71.webp",1,2);

//Found
        this.questionCreator("Michael Jackson's first wife was the daughter of which \"King\"?","Elvis Presley","Makai Mcmillan","Skyler Moreno","Joselyn Hensley","Elvis Presley","Michael Jackson's Wives. Michael Jackson got married twice. He met Lisa Marie Presley after was introduced to him by her father, Elvis Presley in 1974. Presley was supportive when he had his first set of child sexual abuse allegations and Jackson had become dependent on pain medication.","m_p_72.webp",1,2);

//Found
        this.questionCreator("What is Oprah Winfrey's birth name?","Oprah Gail Winfrey","Oprah Gil Winfrey","Oprah Gaail Winfrey","Oprah Gaeil Winfrey","Oprah Gail Winfrey","Oprah Gail Winfrey is an American media executive, actress, talk show host, television producer and philanthropist.","m_p_73.webp",1,2);

//Found
        this.questionCreator("Father of Artificial Intelligence.","Alan Turing","John von Neuman","Warren Mc cullach","John mc Carthy","Alan Turing","Alan turing was a logician mathematician and comp. scientist was born in London in 1912,","m_p_74.webp",1,2);

//Found
        this.questionCreator("Who was known as the Father of geology?","James Hutton","Alfred Wegener","Charles Lyell","Hillon","James Hutton","A Scottish geologist , physician chemical manu8facturer , naturalist and experimental , agriculture","m_p_75.webp",1,2);

//Found
        this.questionCreator("Considered to be the father of cybernatics.","Norbert Wiener","Claude Shannon","Konrad Zuse","Alan turing","Norbert Wiener","American mathematician was born on Novemb er 26, 1894 at Cambridge.","m_p_76.webp",1,2);

//Found
        this.questionCreator("Who claimed that Vitamin c can prevent common cold?","Linus Pauling","Paul Muller","Rebe Dubos","G. natta","Linus Pauling","American chemist,biochemist peace activist,author and educator.","m_p_77.webp",1,2);

//Found
        this.questionCreator("Who invented the term “SCIENCE”?","William whewell","Francis Bacon","Roger Bacon","Plato","William whewell","An English polymath scientist, Anglican priest, philosopher and theologian.","m_p_78.webp",1,2);

//Found
        this.questionCreator("Who gave some revolutionary concepts about infinity?","Georg Cantos","Kurt Goedel","G.H Hardy","S. Ramanujan","Georg Cantos","A German mathematician, created set theory which has become a fundamental theory in mathematics.","m_p_79.webp",1,2);

//Found
        this.questionCreator("Father of Psychoanalysis.","Sigmund Freud","Anna Freud","Carl Rogers","Karen Horney","Sigmund Freud","Austrian neurologist","m_p_80.webp",1,2);

//Found
        this.questionCreator("Theorist of Cognitive development.","Jean Piaget","Jerome Bruner","John Bowlby","Charles Lyell","Jean Piaget","Swiss psychologist and he was placed great importance on the education of children.","m_p_81.webp",1,2);

//Found
        this.questionCreator("Theorist of Psycho Social learning.","Erik Erikson","Lev Vygotsky","Enrico Fermi","Jack Kelby","Erik Erikson","A German-American developmental psychologist and psychoanalysis.","m_p_82.webp",1,2);

//Found
        this.questionCreator("Conducted pioneering research on radioactivity.","Marie Curie","Dmitri Mendeleev","Fritz Haber","Ernest R.","Marie Curie","Was a polish and naturalized-French Physicist and Chemist.","m_p_83.webp",1,2);

//Found
        this.questionCreator("Father of Nuclear Physics.","Ernest Rutherford","Michael Faraday","OttoHahn","Frederic Sanger","Ernest Rutherford","a New Zealand born British physicist and 1st baron Rutherford of Nelson, OM , FRS , HFRSELLD.","m_p_84.webp",1,2);

//Found
        this.questionCreator("Who said knowledge is power?","Francis Bacon","Aristotle","Euclid","Albert Einstein","Francis Bacon","An English philosopher statesman,scientist,jurist, orator and Author.","m_p_85.webp",1,2);

//Found
        this.questionCreator("Who said genius is one 1% inspiration and 99% perspiration?","Thomas A. Edison","Albert Einstein","Isaac Newton","Thomas Jefferson","Thomas A. Edison","American inventor and bussinessman.","m_p_86.webp",1,2);

//Found
        this.questionCreator("Father of Geometry.","Euclid","Max Born","Isaac Newton","Marco Polo","Euclid","Sometimes given the name Euclid of Alexandria to distinguish him from Euclides of Megara, and a Greek mathematician.","m_p_87.webp",1,2);

//Found
        this.questionCreator("Father of modern taxonomy.","Carl Linnaeus","Nikolai Varilov","Gregor Mendel","Agnes Arber","Carl Linnaeus","A Swedist botanist, physician, and zoologist and formalised binomial nomenclature, the modern system of naming organism.","m_p_88.webp",1,2);

//Found
        this.questionCreator("Father of economics.","Alexander Braun","Erik Acharius","Michael Adanson","Andrea Cesalpino","Alexander Braun","Alexander Carl Heinrich Braun was a German botanist from reqensburg Bavaria.","m_p_89.webp",1,2);

//Found
        this.questionCreator("Who was the inventor of Quink Ink used by the Parker Pen company?","Francisco Quisumbing","Ramon Barba","Dado Banatao","Jose Rizal","Francisco Quisumbing","A Filipino botanist and gained his Ph.D in Plant Taxonomy, Systematics and Morphology from the University of Chicago in 1923.","m_p_90.webp",1,2);

//Found
        this.questionCreator("Who was the inventor of the first two-way videophone?","Gregorio Y. Zara","Daniel Dingel","Roberto del Rosario","Ramon Barba","Gregorio Y. Zara","A Filipino engineer and physicist. A native of Liopa, Batangas, Philippine Islands.","m_p_91.webp",1,2);

//Found
        this.questionCreator("Father of Web.","Tim Berners-Lee","Bill Gates","Henry Ford","Alfred Russel","Tim Berners-Lee","British Engineer, computer scientist and Professor at MIT in 1991.","m_p_92.webp",1,2);

//Found
        this.questionCreator("Father of Psychology.","Alfred Adler","Jean Piaget","Lev Vygotsky","Robert Koch","Alfred Adler","Austrian media doctor, and psychotherapist","m_p_93.webp",1,2);

//Found
        this.questionCreator("Theorist of moral Development.","Lawrence Kohlberg","B.F Skinner","Rudolf Diesel","Carl Benz","Lawrence Kohlberg","American psycologist and born on October 25,1927","m_p_94.webp",1,2);

//Found
        this.questionCreator("Theorist of Social Learning.","Albert Bandura","Ramon Barbara","Galen","Francis Bacon","Albert Bandura","A psycologist who is the David Star Jordan Professor Emeritus of Social Science in Psychology at stanford University.","m_p_95.webp",1,2);

//Found
        this.questionCreator("Who was known as the Father of geology?","James Hutton","Alfred Wegener","Charles Lyell","Hillon","James Hutton","A Scottish geologist , physician chemical manu8facturer , naturalist and experimental , agriculture","m_p_96.webp",1,2);

//Found
        this.questionCreator("Considered to be the father of cybernatics.","Norbert Wiener","Claude Shannon","Konrad Zuse","Alan turing","Norbert Wiener","American mathematician was born on Novemb er 26, 1894 at Cambridge.","m_p_97.webp",1,2);

//Found
        this.questionCreator("Who is independently discovered calculus?","G.W Leibniz","Francis Bacon","Christian Guygens","Leonardo Fibonacci","G.W Leibniz","A german rationalist philosopher and one of the great dendissance men of Western thought.","m_p_98.webp",1,2);

//Found
        this.questionCreator("Who was the father of the automobile?","Henry Ford","Rudolf Diesel","Gottlieb Daimler","Carl Benz","Henry Ford","He was so much more interested in machines and building things.","m_p_99.webp",1,2);

//Found
        this.questionCreator("Who was the founding Father of modern anatomy?","Andreas Vesalius","Hippocrates","Avicenna","Galen","Andreas Vesalius","Humani Corporis Fabrica (On the fabric of the human body) revolutionized the science of medicine and laid foundation for the modern human anatomy.","m_p_100.webp",1,2);

//Found
        this.questionCreator("Who discovered that malaria is caused by a particular type of mosquito?","Ronald Ross","Christian Eijman","Charaka","Louis Pasteur","Ronald Ross","A British medical doctor who Eijkman receive Nobel Prize for physiology or Medicine in 1902 for his work in transmission of malaria.","m_p_101.webp",1,2);

//Found
        this.questionCreator("Who game the Germ Theory of diseases?","Louis Pasteur","Claude Bernard","Hugo de Vries","Luigi Galvani","Louis Pasteur","A French biologist, microbiologist and chemist","m_p_102.webp",1,2);

//Found
        this.questionCreator("Father of Psychoanalysis.","Sigmund Freud","Anna Freud","Carl Rogers","Karen Horney","Sigmund Freud","Austrian neurologist","m_p_103.webp",1,2);


    }

    private void addPeopleDifficult()
    {
        //check

//Found
        this.questionCreator("Which famous name was accused of the abduction of Stompie Seipei?","Winnie Mandela","Ayanna Haas","Tessa Simmons","Alicia Spears","Winnie Mandela","James Seipei (1974–1 January 1989), also known as Stompie Moeketsi, or Stompie Sepei was a teenage United Democratic Front (UDF) activist from Parys in South Africa. He and three other boys were kidnapped on 29 December 1988 by members of Winnie Mandela\'s bodyguards, known as the Mandela United Football Club.","d_p_1.webp",1,3);

//Found
        this.questionCreator("What was the highest rank Charles Lindbergh attained?","Brigadier General","Javon Proctor","Ryleigh Woodard","Jett Hobbs","Brigadier General","Lindbergh not only achieved the greatest individual triumph of any American citizen but demonstrated that travel across the ocean by aircraft was possible.","d_p_2.webp",1,3);

//Found
        this.questionCreator("What is Arnold Palmer\'s middle name?","Daniel","Branch","Kale","Costa","Daniel","Arnold Daniel Palmer was born on September 10, 1929, in Latrobe, Pennsylvania. The son of a golf pro, Palmer picked up the sport as a toddler after his father, Milfred J. \"Deacon\" Palmer, shortened a set of regulation clubs so that his son could use them.","d_p_3.webp",1,3);

//Found
        this.questionCreator("Who was the second person to make a solo transatlantic flight?","Amelia Earhart","Khalil Barrera","Emelia Brady","Sidney Jenkins","Amelia Earhart","Five years to the day that American aviator Charles Lindbergh became the first pilot to accomplish a solo, nonstop flight across the Atlantic Ocean, female aviator Amelia Earhart becomes the first pilot to repeat the feat, landing her plane in Ireland after flying across the North Atlantic.","d_p_4.webp",1,3);

//Found
        this.questionCreator("Commanding general of the First Armored Corps in 1941?","George Patton","Kamora Mack","Kyle Erickson","Philip Mason","George Patton","Commanding Officer, 2nd Brigade, 2nd Armored Division, July 16, 1940 to April 4, 1941","d_p_5.webp",1,3);

//Found
        this.questionCreator("In which state were Bonnie & Clyde killed?","Louisiana","L.A","Africa","South Africa","Louisiana","The gang is believed to have killed at least nine police officers and several civilians. The couple were eventually ambushed and killed by law officers near Sailes, Bienville Parish, Louisiana. Their exploits were revived and cemented in American pop folklore by Arthur Penn\'s 1967 film Bonnie and Clyde.","d_p_6.webp",1,3);

//Found
        this.questionCreator("In 1985 Terry Waite returned to Beirut after securing the release of four British hostages where?","Libya","Hawaii","Afghanistan","United States","Libya","He negotiated with Colonel Gaddafi for the release of the four remaining British hostages held in the Libyan Hostage Situation, Michael Berdinner, Alan Russell, Malcolm Anderson and Robin Plummer and was again successful.","d_p_7.webp",1,3);

//Found
        this.questionCreator("Who was the first person to fly in the \'citizen in space\' program?","Christa McAuliffe","Maurice Moody","Emanuel Levy","Kaylee Saunders","Christa McAuliffe","Christa McAuliffe was chosen as the first Teacher in Space in July 1985 from 11,400 applicants. 1,700 applied for the Journalist in Space program. An Artist in Space program was considered, and NASA expected that after McAuliffe\'s flight two to three civilians a year would fly on the shuttle.","d_p_8.webp",1,3);

//Found
        this.questionCreator("Which soul singer is Whitney Houston\'s god mother?","Aretha Franklin","Emanuel Levy","Kaylee Saunders","Christa McAuliffe","Aretha Franklin","Early Years. Born on August 9, 1963 in Newark, New Jersey, Whitney Houston almost seemed destined from birth to become a singer. Her mother Cissy Houston, cousin Dionne Warwick and godmother Aretha Franklin were all legendary figures in American gospel, soul and pop music.","d_p_9.webp",1,3);

//Found
        this.questionCreator("Which founder of the company Triad was accused by the USA of receiving bribes?","Adnan Khashoggi","Keyon Huff","Lauryn Barber","Adan Choi","Adnan Khashoggi","Khashoggi was accused, but then cleared over his dealings with dictator\'s widow Imelda Marcos (AP).","d_p_10.webp",1,3);

//Found
        this.questionCreator("Melanie Molitor is the mom of which tennis world NO 1?","Martina Hingis","Lauryn Barber","Adan Choi","Adnan Khashoggi","Martina Hingis","Coached for much of her career by her mom Melanie Molitor -- said to have named her daughter after another tennis icon, Martina Navratilova -- the five-time grand slam champion is using her mother\'s advice both on and off the court.","d_p_11.webp",1,3);

//Found
        this.questionCreator("In what year did Saddam Hussein become President of Iraq?","1979","1985","1785","1865","1979","Saddam Hussein Abd al-Majid al-Tikriti was President of Iraq from 16 July 1979 until 9 April 2003.","d_p_12.webp",1,3);

//Found
        this.questionCreator("Who became chair of Joint Chiefs of Staff in 1989?","Colin Powel","Parker Scott","Kenyon Morgan","Christopher Russo","Colin Powel","General Colin L. Powell (1989–1993) was the first and, as of 2011, the only African American to serve on the Joint Chiefs of Staff.","d_p_13.webp",1,3);

//Found
        this.questionCreator("Who became leader of the Bosnian Serbs in 1992?","Radovan Karadzic","Weston Gilmore","Nickolas Evans","Xander Mullins","Radovan Karadzic","Mr Karadzic was jointly indicted in 1995 along with the Bosnian Serb military leader, Ratko Mladic, for war crimes they had allegedly committed during the 1992-95 war. He was obliged to step down as president of the SDS in 1996 as the West threatened sanctions against Republika Srpska","d_p_14.webp",1,3);

//Found
        this.questionCreator("Who was deputy commander of the 1983 US invasion of Grenada?","Norman Schwarzkopf","Kody Schneider","Tatum Hopkins","Timothy Pratt","Norman Schwarzkopf","On October 25, 1983, Schwarzkopf was appointed to the command group for the Invasion of Grenada. He was the chief army adviser to the overall operation commander, Vice Admiral Joseph Metcalf III, Commander, United States Second Fleet/Commander Joint Task Force 120.","d_p_15.webp",1,3);

//Found
        this.questionCreator("Mao wrote a Red Book; who wrote a Green Book?","Colonel Gaddafi","Tia Holloway","Gwendolyn Savage","Isabell Warren","Colonel Gaddafi","The Green Book is a short book setting out the political philosophy of Libyan leader Muammar Gaddafi. The book was first published in 1975. It is said to have been inspired in part by The Little Red Book (Quotations from Chairman Mao).","d_p_16.webp",1,3);

//Found
        this.questionCreator("Which hospital was John Lennon taken to after he was shot?","Roosevelt Hospital","Agnes Hospital","Snow Hospital","Johns Hospital","Roosevelt Hospital","After sustaining four major gunshot wounds, Lennon was pronounced dead on arrival at Roosevelt Hospital. Shortly after local news stations reported Lennon\'s death, crowds gathered at Roosevelt Hospital and in front of the Dakota.","d_p_17.webp",1,3);

//Found
        this.questionCreator("Which famous daughter was made chief designer at Chloe in 1997?","Stella McCartney","Tia Holloway","Lilian Juarez","Sarahi Villarreal","Stella McCartney","McCartney, who has designed for Chloe since 1997, will set up her label from scratch, in time for her to show as part of the spring/summer 2002 collections, which hit the catwalks in the autumn.","d_p_18.webp",1,3);

//Found
        this.questionCreator("Which journalist first told the world about the My Lai massacre?","Seymour Hersh","Kyson Jordan","Kason Hood","Craig Calhoun","Seymour Hersh","Seymour Hersh, an independent investigative journalist, in a cable filed through Dispatch News Service and picked up by more than 30 newspapers, reveals the extent of the U.S. Army’s charges against 1st Lt. William L. Calley at My Lai. Hersh wrote: “The Army says he [Calley] deliberately murdered at least 109 Vietnamese civilians during a search-and-destroy mission in March 1968, in a Viet Cong stronghold known as ‘Pinkville.\'”","d_p_19.webp",1,3);

//Found
        this.questionCreator("Which supermodel was married to Rod Stewart?","Rachel Hunter","Jayla Ayala","Gael Sutton","Mathias Kelly","Rachel Hunter","At the age of 21, Hunter met rock star Rod Stewart, 24 years her senior, in a Los Angeles nightclub. They married three months later at the Beverly Hills Presbyterian Church in Beverly Hills, California, on 15 December 1990.","d_p_20.webp",1,3);

//Found
        this.questionCreator("Who was America\'s first world chess champion?","Bobby Fischer","Tyson White","Luna Leach","London Chan","Bobby Fischer","Bobby Fischer was born on March 9, 1943, in Chicago, Illinois. Fischer first learned the game of chess at age 6 and eventually became the youngest international grand master at the age of 15. In 1972, he became the first American-born world chess champion after defeating Boris Spassky.","d_p_21.webp",1,3);

//Found
        this.questionCreator("Which Swiss-born Californian first used an amplifier with a guitar?","Adolph Rickenbacker","Omari Hansen","Jaiden Maxwell","Darien Bailey","Adolph Rickenbacker","Adolph Rickenbacker (April 1, 1886 – March 21, 1976) was a Swiss-American electrical engineer who co-founded the Rickenbacker guitar company along with George Beauchamp and Paul Barth.","d_p_22.webp",1,3);

//Found
        this.questionCreator("Who was chairman of the Watergate hearings?","Sam Ervin","Joy Tucker","Braxton Ruiz","Neil Ochoa","Sam Ervin","From left to right: minority counsel Fred Thompson, ranking member Howard Baker, and chair Sam Ervin of the Senate Watergate Committee in 1973.","d_p_23.webp",1,3);

//Found
        this.questionCreator("In 1984 how was the baby who received the heart of a baboon known?","Baby Fae","Romeo Herrera","Anahi Rice","Randy Ryan","Baby Fae","Stephanie Fae Beauclair (October 14, 1984 – November 15, 1984), better known as Baby Fae, was an American infant born in 1984 with hypoplastic left heart syndrome. She became the first infant subject of a xenotransplant procedure and first successful infant heart transplant, receiving the heart of a baboon.","d_p_24.webp",1,3);

//Found
        this.questionCreator("What is the name of Terence and Shirley Conran\'s dress designer son?","Jasper","Aleena","Lucero","Kelly","Jasper","The son of furniture designer, Sir Terence Conran, and author, Shirley Conran, English designer Jasper Conran has been a stalwart on the London fashion scene since he designed his first womenswear collection in 1979, at the tender age of 19.","d_p_25.webp",1,3);

//Found
        this.questionCreator("Who was credited with popularizing the term rock \'n\' roll?","Alan Freed","Adrien Cabrera","Marlon Dunlap","Abril Fletcher","Alan Freed","Disc jockey Alan Freed is widely credited with coining the term “rock and roll” to describe the uptempo black R&B records he played as early as 1951 on Cleveland radio station WJW.","d_p_26.webp",1,3);

//Found
        this.questionCreator("What were Gary Gilmore\'s final words before his execution in 1977?","Let's do it","I can do it","You can do it","We can do it","Let's do it","Gilmore was executed on January 17, 1977, at 8:07 a.m. by firing squad at Utah State Prison in Draper, When asked for any last words, Gilmore simply replied, \"Let\'s do it.\"","d_p_27.webp",1,3);

//Found
        this.questionCreator("Which 80s Wimbledon Men\'s Singles champion is the father of twins?","Pat Cash","Harold Nisbet","Herbert Baddeley","Wilfred Baddeley","Pat Cash","The champions and runners-up of the Wimbledon Championships Gentlemen\'s Doubles tournament, first introduced to the championship in 1884. From 1915 to 1918, and from 1940 to 1945, no competition was held due to the two World Wars.","d_p_28.webp",1,3);

//Found
        this.questionCreator("Who direct the stage musical Cabaret?","Robert Louis Fosse","Quincy Hunter","Miranda Cohen","Valentino Merritt","Robert Louis Fosse","Robert Louis Fosse (June 23, 1927 – September 23, 1987) was an American dancer, musical theatre choreographer, director and film director. He won eight Tony Awards for choreography, more than anyone else, as well as one for direction. He was nominated for four Academy Awards, winning for his direction of Cabaret.","d_p_29.webp",1,3);

//Found
        this.questionCreator("Flamenco dancer Joaquin Cortes hit the headlines in 1996 over his relationship with which supermodel?","Naomi Campbell","Jazlyn Quinn","Halle Arellano","Reuben Salas","Naomi Campbell","Naomi Campbell dated dancer Joaquín Cortés in the mid to late 1990s.","d_p_30.webp",1,3);

//Found
        this.questionCreator("Who was the youngest brother in the Beach Boys?","Carl Wilson","Leland Singleton","Jovanny Monroe","Mohammad Powell","Carl Wilson","Early years and success. Carl Dean Wilson was born in Hawthorne, California, the youngest son of Audree Neva (née Korthof) and Murry Gage Wilson. From his pre-teens he practiced harmony vocals under the guidance of his brother Brian, who often sang in the family music room with his mother and brothers.","d_p_31.webp",1,3);

//Found
        this.questionCreator("Which Italian fashion designer was murdered on the orders of his ex-wife?","Gucci","Sebastian","Flynn","Carly","Gucci","Patrizia Reggiani Martinelli (born December 2, 1948) is the ex-wife of Maurizio Gucci. During the 1980s while she was married to Maurizio Gucci, she was a wealthy Italian socialite and high fashion personality.","d_p_32.webp",1,3);

//Found
        this.questionCreator("Which blonde model appeared in the first Tim Hudson Batman movie?","Jerry Hall","Siena Anderson","Efrain Frye","Savion Valentine","Jerry Hall","Hall performed in a smattering of movies and television shows, often playing herself. She played Alicia Hunt in 1989’s “Batman.” Expanding her creative influence, she also produced a book in 2010.","d_p_33.webp",1,3);

//Found
        this.questionCreator("Which soap star launched a perfume called Scoundrel?","Joan Collins","Kaden Bass","Reese Marsh","Nia Mayo","Joan Collins","Revlon launches Scoundrel fragrance in 1980. The fragrance was advertised by the actress Joan Collins, also known as Alexis Carrington from the very famous TV soap \"Dynasty.\" The scent is advertised under the slogan \"Seize the moment\" as witty and successful, designed for women with great personal style.","d_p_34.webp",1,3);

//Found
        this.questionCreator("How old was George Gershwin when he died?","38","28","31","45","38","In the early hours of July 11 doctors at Cedars removed a large brain tumor, believed to have been a glioblastoma, but Gershwin died on the morning of July 11, 1937, at the age of 38.The fact that he had suddenly collapsed and become comatose after he stood up on July 9, has been interpreted as brain herniation with Duret haemorrhages.","d_p_35.webp",1,3);

//Found
        this.questionCreator("Which American commander reached the North Pole at his sixth attempt in 1909?","Robert F. Peary","Edward Higgins","Nathaniel Welch","Zachery Lucas","Robert F. Peary","Both fell short of the mark, but the efforts propelled Peary to the distinction of America\'s foremost Arctic explorer. The expedition of 1908-09 was to be his last try. On September 5, 1909 Peary emerged from the Arctic wilderness and announced to the world that he had reached the North Pole on April 6.","d_p_36.webp",1,3);

//Found
        this.questionCreator("Marc Dutroux hit the headlines over a \'house of horrors\' in which country?","Belgium","America","Africa","England","Belgium","Belgium\'s house of horrors, one of the homes of Marc Dutroux, the country\'s most infamous murderer, rapist and paedophile, and the place that, eight years ago, yielded up the country\'s most appalling secret.","d_p_37.webp",1,3);

//Found
        this.questionCreator("Which golfer became only the fifth in history to win both the British and US Open championships in the same year, in 1982?","Tom Watson","Harry Mcclure","Marco Heath","Quintin Sweeney","Tom Watson","The 1982 U.S. Open was the 82nd U.S. Open, held June 17–20 at Pebble Beach Golf Links in Pebble Beach, California. Tom Watson won his only U.S. Open, two strokes ahead of runner-up Jack Nicklaus, for the sixth of his eight major titles.","d_p_38.webp",1,3);

//Found
        this.questionCreator("How many times did tennis legend Jimmy Connors win the US Open in the 1970s?","3","4","5","1","3","Connors reached the final of the US Open in five straight years from 1974 through 1978, winning three times with each win being on a different surface (1974 on grass, 1976 on clay and 1978 on hard).","d_p_39.webp",1,3);

//Found
        this.questionCreator("Who did Mike Tyson defeat when he first won the WBC world heavyweight title at the age of 20?","Trevor Berbiek","Vera Wang","Allison Ferguson","Dane Chase","Trevor Berbiek","On November 22, 1986, Tyson was given his first title fight against Trevor Berbick for the World Boxing Council (WBC) heavyweight championship. Tyson won the title by TKO in the second round, and at the age of 20 years and 4 months became the youngest heavyweight champion in history.","d_p_40.webp",1,3);

//Found
        this.questionCreator("Which pop star did model Iman marry in 1992?","David Bowie","Dennis Weaver","Kolby Todd","Alijah Jackson","David Bowie","In October 1990, a decade after his divorce from Angie, Bowie and Somali-born supermodel Iman were introduced by a mutual friend. Bowie recalled, \"I was naming the children the night we met ... it was absolutely immediate.\" They married in 1992.","d_p_41.webp",1,3);

//Found
        this.questionCreator("How old was Charles Lindbergh when he first flew solo across the Atlantic?","25","32","13","15","25","May 21, 1927, the aviator Charles A. Lindbergh landed his Spirit of St. Louis near Paris, completing the first solo airplane flight across the Atlantic Ocean. Lindbergh was just 25 years old when he completed the trip.","d_p_42.webp",1,3);

//Found
        this.questionCreator("Which First Lady had to give evidence over the Whitewater scandal?","Hillary Clinton","Salma Glenn","Chloe Kerr","Kimberly Le","Hillary Clinton","The Whitewater scandal refers to a real estate controversy involving former President Bill Clinton and his wife Hillary as relates to their failed investment into a land development venture known as Whitewater.","d_p_43.webp",1,3);

//Found
        this.questionCreator("About which British politician did Francois Mitterrand say, \"She has the mouth of Marilyn Monroe and the eyes of Caligula?\"","Margaret Thatcher","Cecilia Cortez","Abbigail Reyes","Juliana Arias","Margaret Thatcher","Margaret Hilda Thatcher, Baroness Thatcher, LG, OM, DStJ, PC, FRS, HonFRSC was a British stateswoman who served as Prime Minister of the United Kingdom from 1979 to 1990 and Leader of the Conservative Party from 1975 to 1990.","d_p_44.webp",1,3);

//Found
        this.questionCreator("President Kennedy was shot on 22nd November; what day was Lee Harvey Oswald shot?","24th November","26th November","28th November","21th November","24th November","Jack Leon Ruby (born Jacob Leon Rubenstein; April 25, 1911 – January 3, 1967) was the Dallas, Texas nightclub owner who fatally shot Lee Harvey Oswald on November 24, 1963, while Oswald was in police custody after being charged with assassinating U.S. President John F. Kennedy and the murder of Dallas policeman J. D.","d_p_45.webp",1,3);

//Found
        this.questionCreator("What did Woody Allen call his son as a tribute to Louis \'Satchmo\' Armstrong?","Satchel","Kim","Zaiden","Maldonado","Satchel","the name was also in tribute to jazz musician Louis (Satchmo) Armstrong, but Dart said she didn\'t think so. Allen is a well-known jazz buff who holds forth on the clarinet at a jazz club every Monday night.","d_p_46.webp",1,3);

//Found
        this.questionCreator("Who founded General Motors in 1908?","William C. Durant","Adelaide Nixon","Jonah Huynh","Bryce Frazier","William C. Durant","General Motors was capitalized by William C. Durant on September 16, 1908 as a holding company. The next day it purchased Buick Motor Company, and rapidly acquired more than twenty companies including Oldsmobile, Cadillac, Oakland, now known as Pontiac, and McLaughlin of Canada.","d_p_47.webp",1,3);

//Found
        this.questionCreator("An Italian fashion designer and known today for his clean, tailored lines.","Giorgio Armani","Jean Arlo","Londyn Arcoa","May Lizor","Giorgio Armani","Giorgio Armani is an Italian fashion designer. He is known today for his clean, tailored lines. He formed his company, Armani, in 1975, and by 2001 was acclaimed as the most successful designer of Italian origin, with an annual turnover of $1.6 billion and a personal fortune of $8.1 billion as of 2017.","d_p_48.webp",1,3);

//Found
        this.questionCreator("Which US journalist was released after  his arrest for spying in 1986?","Nicholas Daniloff","Kael Luna","Angelo Poole","Jovanni Alvarado","Nicholas Daniloff","Nicholas Daniloff (born December 30, 1934) is an American journalist who graduated from Harvard University and was most prominent in the 1980s for his reporting on the Soviet Union. He came to wider international attention on September 2, 1986, when he was arrested in Moscow by the KGB and accused of espionage.","d_p_49.webp",1,3);

//Found
        this.questionCreator("In which decade did Berry Gordy set up Tamla Motown?","1950s","1960s","1970s","1980s","1950s","In 1959, with an $800 loan from his family, Berry Gordy put down a deposit on a small two-storey house in a run-down area of Detroit, on a street where his neighbours included a funeral home and a beauty parlour, and laid the foundation stone for an empire.","d_p_50.webp",1,3);

//Found
        this.questionCreator("Father of physiology.","Erasistratus","Thales","Herophilus","Anaximander","Erasistratus","A Greek anatomist and royal physician. He founded a school of anatomy in Alexandria.","d_p_51.webp",1,3);

//Found
        this.questionCreator("Who was the founding Father of modern anatomy?","Andreas Vesalius","Hippocrates","Avicenna","Galen","Andreas Vesalius","Humani Corporis Fabrica (On the fabric of the human body) revolutionized the science of medicine and laid foundation for the modern human anatomy.","d_p_52.webp",1,3);

//Found
        this.questionCreator("Who considered to be the father of eugenics?","Francis Galton","Karl Pearson","Charles Darwin","Avicenna","Francis Galton","English explorer, anthropologist, eugenicists, geographer and meteorologist.","d_p_53.webp",1,3);

//Found
        this.questionCreator("Who discovered sulpha drugs?","Gerhard Domagk","Robert Wilkins","Howard Florey","Alexander Fleming","Gerhard Domagk","a German pathologist and bacteriologist","d_p_54.webp",1,3);

//Found
        this.questionCreator("Who discovered one-celled animals?","Anton Leeuwenhoek","Galen","Marcello Malpighi","Charaka","Anton Leeuwenhoek","who invented or greatly improved the microscope and discovered living organisms,.","d_p_55.webp",1,3);

//Found
        this.questionCreator("Who discovered J-receptor-the nerve terminals in lungs that cause breathlessness?","Autor Singh Paintal","Peter Medawar","B.S Anand","A.L. Hodgkin","Autor Singh Paintal","Indian Medical Scientist","d_p_56.webp",1,3);

//Found
        this.questionCreator("Who discovered that malaria is caused by a particular type of mosquito?","Ronald Ross","Christian Eijman","Charaka","Louis Pasteur","Ronald Ross","A British medical doctor who Eijkman receive Nobel Prize for physiology or Medicine in 1902 for his work in transmission of malaria.","d_p_57.webp",1,3);

//Found
        this.questionCreator("Who discovered condition reflex through his experiments on dogs?","I.P Pavlov","Sigmund Freud","B.F Skinner","Carl Jung","I.P Pavlov","A Russian physiologist known primarily for his work in classical conditioning","d_p_58.webp",1,3);

//Found
        this.questionCreator("Who coined the term ‘anesthesia’ for any pain-killer given during an operation?","Oliver Wendell holmes","Paul Ehrlich","Susruta","Carl Jung","Oliver Wendell holmes","The word \"anesthesia\", coined by Oliver Wendell Holmes (1809–1894) in 1846 from the Greek ἀν-, an-, \"without\"; and αἴσθησις, aisthēsis, \"sensation\"), refers to the inhibition of sensation.","d_p_59.webp",1,3);

//Found
        this.questionCreator("Who came close to predicting the presence of black holes?","S. Chandrasekhar","A.S Eddington","Albert Einstein","Harlow Shapley","S. Chandrasekhar","Indian American astrophysicist, who spend his professional list in the US","d_p_60.webp",1,3);

//Found
        this.questionCreator("The coment known after him split into two who is he?","Wilhelm von Biela","Francis Baily","David Brewster","Heinrich Olbers","Wilhelm von Biela","A German Austrian military officer and amateur astronomer.","d_p_61.webp",1,3);

//Found
        this.questionCreator("The telegraphic code is known after him, who is he?","Samuel Morse","Joseph henry","Andre ampere","Jean fourier","Samuel Morse","American painter and inventor.","d_p_62.webp",1,3);

//Found
        this.questionCreator("Who coined the term ‘Bit’ for a unit of information?","Claude E. Shannon","Ren Descartes","Albert Einstein","Luigi Galvani","Claude E. Shannon","American mathematician electrical engineer and cryptographer, known as the father of information theory.","d_p_63.webp",1,3);

//Found
        this.questionCreator("Who coined the term ‘meme’ any bit of information, whether it is a fact, fad or rumor?","Richard Dawking","Charles Darwin","William Hamilton","Ren Descartes","Richard Dawking","English ethnologist, evolutionary biologist and Author.","d_p_64.webp",1,3);

//Found
        this.questionCreator("Who devised an equipment to measure the charge on an electron?","Robert Millikan","S. Ramanujan","Richard Dawking","Susruta","Robert Millikan","American experimental physicist, honored with the Nobel Prize for physics.","d_p_65.webp",1,3);

//Found
        this.questionCreator("Who game the Germ Theory of diseases?","Louis Pasteur","Claude Bernard","Hugo de Vries","Luigi Galvani","Louis Pasteur","A French biologist, microbiologist and chemist","d_p_66.webp",1,3);

//Found
        this.questionCreator("She was India’s First female Prime Minister.","Indira Gandhi","Uma Bharti","Sucheta Kriplani","Sushma Swaraj","Indira Gandhi","She was the Prime Minister of the Republic of Indian From Nov. 1917 to October 1984 a total of 15 years until her assassination.","d_p_67.webp",1,3);

//Found
        this.questionCreator("First lady of Argentina (1946 – 1952).","Eva Peron","Juliana awada","Isabel De peron","Cristina Ferandez","Eva Peron","She became a powerful political figure w/a large of support.","d_p_68.webp",1,3);

//Found
        this.questionCreator("Father of Indian green revolution is:","M.S Swaminathan","Normal Borlang","K.C Mehta","B.P Pal","M.S Swaminathan","Swaminathan is known as the \"Father of Indian Green Revolution\" for his leadership and success in introducing and further developing high-yielding varieties of wheat in India. He is the founder and chairman of the About Chairman.","d_p_69.webp",1,3);

//Found
        this.questionCreator("Who gave the Germ Theory of diseases?","Louis Pastuer","Claude Bernard","Hugo de Vries","Luigi Galvani","Louis Pastuer","A French biologist Microbiologist and Chemist","d_p_70.webp",1,3);

//Found
        this.questionCreator("Who proposed the theory of anti-particles?","P.A.M Dirac","Max Planck","Plato","Albert Einstein","P.A.M Dirac","Paul Adrien Maurice Diract an English theoritical physicist and one of the most significant physicist of the 20th century","d_p_71.webp",1,3);

//Found
        this.questionCreator("Who condution the famous experiment to prove that nature abhors vacuum?","Otto Von Guericke","Joseph Black","John Dalton","Evangelista Torricelli","Otto Von Guericke","A German scientist, inventor and politician.","d_p_72.webp",1,3);

//Found
        this.questionCreator("Which astronaut has become a politician?","John Glenn","rank Borman","Thomas Stafford","German Titov","John Glenn","US Marine Corops aviator, engineer, astronaut and United State senator from Ohio, the first American to orit the earth.","d_p_73.webp",1,3);

//Found
        this.questionCreator("Who was the first woman astronaut to die during a space flight?","Judit Resnik","Mary Cleave","Rhea Seddon","Anna Fisher","Judit Resnik","An American engineer, she died when the space shuttle challenger was destroyed during the launch of mission.","d_p_74.webp",1,3);

//Found
        this.questionCreator("The last astronaut who left the Moon?","Eugene Cernan","Neil Armstrong","Harrison Schmitt","Edwin Aldrin","Eugene Cernan","An American NASA astronaut, Navan aviator and fighter pilot.","d_p_75.webp",1,3);

//Found
        this.questionCreator("Who explored the central and southern Africa and discovered Victoria Falls, among other things.","David Livingstone","Alfred Wallace","Richard Leakey","Henry Norton Stanley","David Livingstone","A scottist christian congregationalist and pioneer medical missionary.","d_p_76.webp",1,3);

//Found
        this.questionCreator("Who led the first Indian solar eclipse expedition of Juer in 1898?","K.D Naegamvala","M.K Vainu Bappu","A.L Narayan","C. Nagaraja Iyer","K.D Naegamvala","Kavasji Dadabha Naegamvala was an astrophysicist and the director of Takhtasingji observatory.","d_p_77.webp",1,3);

//Found
        this.questionCreator("Who led the expedition to Greenland and later proved it to be an island?","Robert E. Peary","John Milne","Dicaearchus","Daubree","Robert E. Peary","No Fun Facts","d_p_78.webp",1,3);

//Found
        this.questionCreator("Who is the first man to reach the South Pole?","Ronald Amundsen","Francis Drake","Ferdinand Magellan","Robert Scott","Ronald Amundsen","A norwegian explorer of polar regions and leader of the Antartic expedition of 1910-1912","d_p_79.webp",1,3);

//Found
        this.questionCreator("Who led the first expedition to stay in Antartica during the winter?","Adrien de Gerlache","Leonard Kristensen","James Clark Ross","E.Borchgrevink","Adrien de Gerlache","An officer in the Belgian Royal Navy","d_p_80.webp",1,3);

//Found
        this.questionCreator("Who is the first man to stay alone in Antarctica for five months?","Richard E. Byrd","Ernest Shackleton","Cherry Garrard","None","Richard E. Byrd","American Naval officer and explorer, He receive Medal of Honor highest honor for valor given by united states.","d_p_81.webp",1,3);

//Found
        this.questionCreator("Who laid the foundation of nuclear science the country?","J.Bhabh","Kram Sarabha","Raja Ramanna","M.G.K Menon","J.Bhabh","An Indian nuclear physicist, founding director and professor of physics at Tata Institute","d_p_82.webp",1,3);

//Found
        this.questionCreator("Who discovered millisecond pulsars?","Shrinivas Kulkarna","Shiv Kumar","Govind Swarup","Charaka","Shrinivas Kulkarna","An Indian Astronomer.","d_p_83.webp",1,3);

//Found
        this.questionCreator("Who wrote the novel Jurassic Park?","Michael Crichton","Stanley Kubrick","George Lucas","Steven Spielberg","Michael Crichton","An American, Author , Screen writer, film director, film producer, and televesion producer.","d_p_84.webp",1,3);

//Found
        this.questionCreator("Who gave the laws of robotics?","Isaac Animov","Voltaire","Karel Capek","Douglas Adams","Isaac Animov","An American writter and professor of biochemistry at Boston University.","d_p_85.webp",1,3);

//Found
        this.questionCreator("Who create the artificial coral reefs that help a lot to the Philippines aquatic ecosystem.","Angel C. Alcala","Pedro Flores","Dong Puno","Ernesto Baron","Angel C. Alcala","A Filipino biologist who was named a National Scientist of the Philippines.","d_p_86.webp",1,3);

//Found
        this.questionCreator("Woman astronomer discovered six comets.","Margaret Burbridge","Hypatia","Caroline Herschel","Christine Wilson","Margaret Burbridge","A British-born American astrophysicist noted for original research and holding mand administrative posts.","d_p_87.webp",1,3);

//Found
        this.questionCreator("Father of plastic surgery.","Susruta","Hippocrates","Galen","Charaka","Susruta","Ancient Indian physician during BCE to 1000 BCE.","d_p_88.webp",1,3);

//Found
        this.questionCreator("Who discovered artificial radioactivity?","Freederic Joliot – Curie","Henri Bacquerel","Marie Curie","Pierre Curie","Freederic Joliot – Curie","A French physicist","d_p_89.webp",1,3);

//Found
        this.questionCreator("Father of brain surgery.","Harvey Cushing","Pierre Broca","Galen","Christian Barnard","Harvey Cushing","American neuron surgeon, pathologist, writer and draftsman.","d_p_90.webp",1,3);

//Found
        this.questionCreator("Who claimed that Vitamin c can prevent common cold?","Linus Pauling","Paul Muller","Rebe Dubos","G. natta","Linus Pauling","American chemist,biochemist peace activist,author and educator.","d_p_91.webp",1,3);

//Found
        this.questionCreator("Father of physiology.","Erasistratus","Thales","Herophilus","Anaximander","Erasistratus","A Greek anatomist and royal physician. He founded a school of anatomy in Alexandria.","d_p_92.webp",1,3);

//Found
        this.questionCreator("Who discovered radio waves coming from the sky?","Karl Jansky","Max Weber","J.S Hey","Robert Watson-Watt","Karl Jansky","An American physicist and radio engineer.","d_p_93.webp",1,3);

//Found
        this.questionCreator("Who was the first Asian astronaut to go into space?","Phan tuan","John Glenn","Ravish Malhotra","Rakesh Sharma","Phan tuan","a retired Vietnam airforce aviator.","d_p_94.webp",1,3);

//Found
        this.questionCreator("Who was the first man to walk in space?","Alexer Leonov","John Glenn","John Young","Alan Shepard","Alexer Leonov","A retired Soviet/Russian cosmonaut , Air Force Major general, writer and artist.","d_p_95.webp",1,3);

//Found
        this.questionCreator("Who was the first Woman in space?","Valentina Tereshkova","Sally Ride","Anna Fisher","Rhea Seddon","Valentina Tereshkova","Retired Russian cosmonaut, engineer and politician.","d_p_96.webp",1,3);

//Found
        this.questionCreator("Who was the first man to reach antartica?","Fabian Gottlieb Bellingshausen","Thomas Cook","Paul Scott","John young","Fabian Gottlieb Bellingshausen","One of Russia’s most celebrated explorers and the discovered of the continent of Antarctica.","d_p_97.webp",1,3);

//Found
        this.questionCreator("Who was the father of science chemotherapy, using chemical to treat diseases?","Paul Ehrlich","Leo Szilard","Hippocrates","Avicenna","Paul Ehrlich","He is a German , and won the Nobel prize in Physiology or Medicine for his exemplary work on immunology","d_p_98.webp",1,3);

//Found
        this.questionCreator("Who was the father of hydrogen bomb?","Edward Teller","Leo Szilard","J. Robert Oppenheimer","Otto Hahn","Edward Teller","On it’s 100th birthday in 1959, Edward Teller warned the oil industry about global warming.","d_p_99.webp",1,3);

//Found
        this.questionCreator("Who was the Father of geology in India?","D.N Wadia","Mihir Sen","K.S Valdiya","M.K Bose","D.N Wadia","One among the great geologist in geological.","d_p_100.webp",1,3);

//Found
        this.questionCreator("Who considered to be the father of eugenics?","Francis Galton","Karl Pearson","Charles Darwin","Avicenna","Francis Galton","English explorer, anthropologist, eugenicists, geographer and meteorologist.","d_p_101.webp",1,3);

//Found
        this.questionCreator("Who discovered the Third Law of thermodynamics?","Walther Nernst","L.E Boltzmann","Count Rumford","P.M.S Blackett","Walther Nernst","The 3rd law was developed by the chemist Walther Nernst during the years 1906–12, and is therefore often referred to as Nernst's theorem or Nernst's postulate. The third law of thermodynamics states that the entropy of a system at absolute zero is a well-defined constant.","d_p_102.webp",1,3);

//Found
        this.questionCreator("Who discovered sulpha drugs?","Gerhard Domagk","Robert Wilkins","Howard Florey","Alexander Fleming","Gerhard Domagk","a German pathologist and bacteriologist","d_p_103.webp",1,3);

//Found
        this.questionCreator("Who discovered one-celled animals?","Anton Leeuwenhoek","Galen","Marcello Malpighi","Charaka","Anton Leeuwenhoek","who invented or greatly improved the microscope and discovered living organisms,.","d_p_104.webp",1,3);

//Found
        this.questionCreator("Who discovered J-receptor-the nerve terminals in lungs that cause breathlessness?","Autor Singh Paintal","Peter Medawar","B.S Anand","A.L. Hodgkin","Autor Singh Paintal","Indian Medical Scientist","d_p_105.webp",1,3);

//Found
        this.questionCreator("Who discovered condition reflex through his experiments on dogs?","I.P Pavlov","Sigmund Freud","B.F Skinner","Carl Jung","I.P Pavlov","A Russian physiologist known primarily for his work in classical conditioning","d_p_106.webp",1,3);

//Found
        this.questionCreator("Who coined the term ‘anesthesia’ for any pain-killer given during an operation?","Oliver Wendell holmes","Paul Ehrlich","Susruta","Carl Jung","Oliver Wendell holmes","The word \"anesthesia\", coined by Oliver Wendell Holmes (1809–1894) in 1846 from the Greek ἀν-, an-, \"without\"; and αἴσθησις, aisthēsis, \"sensation\"), refers to the inhibition of sensation.","d_p_107.webp",1,3);

//Found
        this.questionCreator("Who came close to predicting the presence of black holes?","S. Chandrasekhar","A.S Eddington","Albert Einstein","Harlow Shapley","S. Chandrasekhar","Indian American astrophysicist, who spend his professional list in the US","d_p_108.webp",1,3);

//Found
        this.questionCreator("The coment known after him split into two who is he?","Wilhelm von Biela","Francis Baily","David Brewster","Heinrich Olbers","Wilhelm von Biela","A German Austrian military officer and amateur astronomer.","d_p_109.webp",1,3);

//Found
        this.questionCreator("The telegraphic code is known after him, who is he?","Samuel Morse","Joseph henry","Andre ampere","Jean fourier","Samuel Morse","American painter and inventor.","d_p_110.webp",1,3);

//Found
        this.questionCreator("Who proposed the idea that stars generate energy by nuclear fusion?","Hans Bethe","Albert Einstein","M.N Sana","Bertrand Rusell","Hans Bethe","German – American nuclear physicist.","d_p_111.webp",1,3);

//Found
        this.questionCreator("Who coined the term ‘Bit’ for a unit of information?","Claude E. Shannon","Ren Descartes","Albert Einstein","uigi Galvani","Claude E. Shannon","American mathematician electrical engineer and cryptographer, known as the father of information theory.","d_p_112.webp",1,3);

//Found
        this.questionCreator("Who coined the term ‘meme’ any bit of information, whether it is a fact, fad or rumor?","Richard Dawking","Charles Darwin","William Hamilton","Ren Descartes","Richard Dawking","English ethnologist, evolutionary biologist and Author.","d_p_113.webp",1,3);

//Found
        this.questionCreator("Who devised an equipment to measure the charge on an electron?","Robert Millikan","S. Ramanujan","Richard Dawking","Susruta","Robert Millikan","American experimental physicist, honored with the Nobel Prize for physics.","d_p_114.webp",1,3);

//Found
        this.questionCreator("Who gave some revolutionary concepts about infinity?","Georg Cantos","Kurt Goedel","G.H Hardy","S. Ramanujan","Georg Cantos","A German mathematician, created set theory which has become a fundamental theory in mathematics.","d_p_115.webp",1,3);


    }


    private void addSportsEasy()
    {
        //checked

//Found
        this.questionCreator("England rugby player Mike Tindell married which member of the British royal family in 2011?","Zara Phillips","Elisha Singleton","Patrick Lynn","Lydia Keith","Zara Phillips","Tindall is married to Zara Phillips, the daughter of Anne, Princess Royal, and the eldest granddaughter of Queen Elizabeth II and Prince Philip, Duke of Edinburgh.","e_s_1.webp",5,1);

//Found
        this.questionCreator("On a standard dartboard, which number lies between 9 and 11?","14","13","12","10","14","The Dartboard Sequence. The arrangement of the numbers around the circumference of a standard dart board is as shown below 20 1 18 4 13 6 10 15 2 17 3 19 7 16 8 11 14 9 12 5 Oddly enough, no one seems to know for sure how this particular arrangement was selected.","e_s_2.webp",5,1);

//Found
        this.questionCreator("Footballer David Beckham, born 2nd May 1975, played for which English football club between 1993 and 2003?","Manchester United","Louisville Cardinals","Siena Saints","Hampton Pirates","Manchester United","David Beckham (born 2 May 1975) is an English retired association football player. The teams he played for were Manchester United, Real Madrid, Los Angeles Galaxy, Preston North End, AC Milan, Paris Saint-Germain, and the England national team.","e_s_3.webp",5,1);

//Found
        this.questionCreator("Which English darts player has the nickname ‘Old Stoneface’?","John Lowe","Javon Nelson","Izabella Rhodes","Georgia Lamb","John Lowe","John Lowe (born 21 July 1945) is a former World No. 1 English retired professional darts player who was one of the most talented and best known darts players during the 1970s and 1980s, particularly in the United Kingdom.","e_s_4.webp",5,1);

//Found
        this.questionCreator("The heptathlon is made up of how many events?","7","3","4","5","7","Heptathlon. A heptathlon is a track and field combined events contest made up of seven events. The name derives from the Greek hepta (seven) and ἄθλος (áthlos, or ἄθλον, áthlon, meaning \"feat\"). A competitor in a heptathlon is referred to as a heptathlete.","e_s_5.webp",5,1);

//Found
        this.questionCreator("During which month of the year is the British Open Golf Championship held?","July","January","Febuary","June","July","The Open is traditionally played in mid-July; beginning 2019, with the rescheduling of the PGA Championship to May, the tournament will be the final major of the golf season. It was first played in 1860 at Prestwick Golf Club in Scotland.","e_s_6.webp",5,1);

//Found
        this.questionCreator("Which English football club is television cook Delia Smith connected to?","Norwich City FC","Montana Grizzlies","Lamar Cardinals","Syracuse Orange","Norwich City FC","Delia Ann Smith CH CBE (born 18 June 1941) is an English cook and television presenter, known for teaching basic cookery skills in a no-nonsense style. One of the best known celebrity chefs in British popular culture, Smith has influenced viewers to become more culinarily adventurous. She is also famous for her role as joint majority shareholder at Norwich City F.C. Her partner in the shareholding is her husband, Michael Wynn-Jones.","e_s_7.webp",5,1);

//Found
        this.questionCreator("In 2000, who became the first British rower to win gold medals at five consecutive Olympic Games?","Steven Redgrave","George Weiss","Lilah Lowery","Imani Fields","Steven Redgrave","Sir Steven Geoffrey Redgrave CBE DL (born on 23 March 1962) is a retired British rower who won gold medals at five consecutive Olympic Games from 1984 to 2000. He has also won three Commonwealth Games gold medals and nine World Rowing Championships golds.","e_s_8.webp",5,1);

//Found
        this.questionCreator("Which English fruit merchant and amateur sailor returned to Portsmouth, England, on 4th July 1968, after sailing round the world in 354 days?","Alec Rose","Draven Hale","Belen Gonzales","Emery Whitney","Alec Rose","Sir Alec Rose (13 July 1908 – 11 January 1991) was a nursery owner and fruit merchant in England who after serving in the Royal Navy during World War II developed a passion for amateur single-handed sailing.","e_s_9.webp",5,1);

//Found
        this.questionCreator("Erica Roe, Michael O’Brien and Mark Roberts are all famous for doing what at sports venues?","Streaking","Snowboarding","Skateboarding","Wrestling","Streaking","Streaking is the act of running naked through a public place as a prank, a dare, for publicity or an act of protest.","e_s_10.webp",5,1);

//Found
        this.questionCreator("A silver medal is traditionally awarded for which place in a race or competition?","2nd","1st","3rd","4th","2nd","a medal that is given to the winner of a race or competition. The person who comes second gets a silver medal, and the person who is third gets a bronze medal.","e_s_11.webp",5,1);

//Found
        this.questionCreator("Which English football club play their home games at Ewood park?","Blackburn Rovers FC","Manhattan Jaspers","Miami RedHawks","Monmouth Hawks","Blackburn Rovers FC","Ewood Park is a football stadium in the English town of Blackburn, Lancashire, and is the home of Blackburn Rovers Football Club — one of the founder members of the Football League and Premier League. Rovers have played there since they moved from Leamington Road in the summer of 1890.","e_s_12.webp",5,1);

//Found
        this.questionCreator("How many coloured rings are on the Olympic flag?","5","3","2","4","5","The Olympic flag has a white background, with five interlaced rings in the centre: blue, yellow, black, green and red. This design is symbolic; it represents the five continents of the world, united by Olympism, while the six colours are those that appear on all the national flags of the world at the present time.","e_s_13.webp",5,1);

//Found
        this.questionCreator("The headquarters of the International Ice Hockey Federation is in which European city?","Zurich","London","Vienna","Berlin","Zurich","The International Ice Hockey Federation is a worldwide governing body for ice hockey and in-line hockey. It is based in Zurich, Switzerland, and has 76 members.","e_s_14.webp",5,1);

//Found
        this.questionCreator("The 2016 Summer Olympics will be held in which country?","Brazil","Eritrea","Kiribati","Nigeria","Brazil","The 2016 Summer Olympics, officially known as the Games of the XXXI Olympiad and commonly known as Rio 2016, was an international multi-sport event that was held from 5 to 21 August 2016 in Rio de Janeiro, Brazil, with preliminary events in some sports beginning on 3 August.","e_s_15.webp",5,1);

//Found
        this.questionCreator("Which national team won the 2011 Rugby Union World Cup?","New Zealand","South-eastern Asia","Eastern Asia","Southern Europe","New Zealand","The 2011 Rugby World Cup Final was a rugby union match between France and New Zealand, to determine the winner of the 2011 Rugby World Cup. The match took place on 23 October 2011 at Eden Park, in Auckland, New Zealand.","e_s_16.webp",5,1);

//Found
        this.questionCreator("In which city was tennis player Martina Navratilova born?","Prague","Tijuana","Manila","Montreal","Prague","Martina Navratilova is a former Czechoslovak and later American professional tennis player and coach. In 2005, Tennis magazine selected her as the greatest female tennis player for the years 1975 through 2005 and she is considered one of the best, if not the best, female tennis players of all time born in Prague, Czech Republic.","e_s_17.webp",5,1);

//Found
        this.questionCreator("Which tennis player won the 2005 Wimbledon Men’s Finals?","Roger Federer","Jessie Fleming","Alisa Cervantes","Andrea Gentry","Roger Federer","2005 Wimbledon Championships – Men\'s Singles. Roger Federer successfully defended his title again, winning Wimbledon for a third consecutive year by defeating Andy Roddick in the final for the second year in a row, 6–2, 7–6, 6–4 to win the Gentlemen\'s Singles tennis title at the 2005 Wimbledon Championships.","e_s_18.webp",5,1);

//Found
        this.questionCreator("Who did Fabio Capello replace as England Football team manager in 2008?","Steve McLaren","Fabian Rich","Mateo Estes","Carl Hull","Steve McLaren","Swedish coach Sven-Göran Eriksson became the first foreign manager of the team in January 2001 amid much acrimony; he helped the team to three successive quarter-finals in major championships. Italian manager Fabio Capello replaced Steve McClaren in December 2007, after England failed to qualify for Euro 2008.","e_s_19.webp",5,1);

//Found
        this.questionCreator("Which English football team play their home games at Old Trafford?","Manchester United","Duquesne Dukes","Iona Gaels","Drake Bulldogs","Manchester United","Manchester United\'s first trophy was the Manchester Cup, which they won as Newton Heath LYR in 1886. Their first national senior honour came in 1908, when they won the 1907–08 Football League First Division title.","e_s_20.webp",5,1);

//Found
        this.questionCreator("Which British female swimmer won a silver medal at the 1980 Moscow Olympic Games for the 400 metre medley?","Sharron Davies","Crystal Novak","Alijah Mcguire","Abdiel Keller","Sharron Davies","She was named by Swimming World magazine as World Swimmer of the Year in 1980 and 1982, but her achievements are regarded with suspicion due to the state-run systematic doping program run by East Germany. She later admitted to having been doped.","e_s_21.webp",5,1);

//Found
        this.questionCreator("Which famous racehorse is buried next to the finishing post of the Aintree Racecourse?","Red Rum","Shea Rivers","Elsie Ali","Greta Horne","Red Rum","He was buried at the winning post of the Aintree Racecourse, which is still a destination for his fans. The epitaph reads \"Respect this place / this hallowed ground / a legend here / his rest has found / his feet would fly / our spirits soar / he earned our love for evermore\".","e_s_22.webp",5,1);

//Found
        this.questionCreator("Which English snooker player is nicknamed ‘The Rocket’?","Ronnie O’Sullivan","Greta Horne","Sawyer Ho","Dulce Ballard","Ronnie O’Sullivan","Ronnie O\'Sullivan. Ronald Antonio O\'Sullivan, OBE (born 5 December 1975) is an English professional snooker player.","e_s_23.webp",5,1);

//Found
        this.questionCreator("Which English football club won the 1994 FA Cup?","Manchester United","San Diego Toreros","UMBC Retrievers","Akron Zips","Manchester United","1994 FA Cup Final. The 1994 FA Cup Final was the 49th FA Cup final to be held since the Second World War and was contested between Manchester United and Chelsea. United went into the final as Premier League champions, having won the title by eight points over Blackburn Rovers.","e_s_24.webp",5,1);

//Found
        this.questionCreator("Which British snooker player was known by the nickname ‘Hurricane’?","Alex Higgins","Conner Ritter","Kaliyah Foster","Hunter Kaufman","Alex Higgins","Alexander Gordon Higgins was a Northern Irish professional snooker player, who is remembered as one of the most iconic figures in the game. Nicknamed Hurricane Higgins because of his fast play, he was World Champion in 1972 and 1982, and runner-up in 1976 and 1980.","e_s_25.webp",5,1);

//Found
        this.questionCreator("Which sport features line-outs, scrums and conversions?","Rugby","Roller derby","Golf","Auto Racing","Rugby","They include the pushover try, scored by driving the opposition\'s scrum back over its own line; If a team scores a try, they have an opportunity to \"convert\" it for two further points by kicking the ball between the posts and above the crossbar - that is, through the goal.","e_s_26.webp",5,1);

//Found
        this.questionCreator("Which word is used for the score of zero in a game of tennis?","Love","Hate","Joy","Hope","Love","At the beginning of the game, when both sides have no score, the game is love-love because in tennis, love means having a score of zero or nil.","e_s_27.webp",5,1);

//Found
        this.questionCreator("Which tennis player was known as the Rockhampton Rocket?","Rod Laver","Nigel Frazier","Mariana Bean","Rashad Navarro","Rod Laver","Rodney George Laver AC, MBE (born 9 August 1938), better known as Rod Laver, is an Australian former tennis player.","e_s_28.webp",5,1);

//Found
        this.questionCreator("Which US golfer’s first names are Eldrick Tont?","Tiger Woods","Raegan Bonilla","Lilly Key","Kolton Cooke","Tiger Woods","Eldrick Tont \"Tiger\" Woods is an American professional golfer who is among the most successful golfers of all time, and one of the most popular athletes of the 21st century. He has been one of the highest-paid athletes in the world for several years.","e_s_29.webp",5,1);

//Found
        this.questionCreator("Which motorcycle stuntman set a world record in February 1971, by jumping 19 cars?","Evel Knievel","Roger Zavala","Gideon Carrillo","Dylan Lynch","Evel Knievel","Robert Craig Knievel 19-car motorcycle jump (world record for 27 years) in February 1971 on Ontario, California he successfully jump 129 feet using his Harley-Davidson XR-750 motorcycle.","e_s_30.webp",5,1);

//Found
        this.questionCreator("Which professional golfer is known as ‘The Big Easy’?","Ernie Els","Raven Heath","Remington Mcdonald","Angelo Taylor","Ernie Els","Ernie Els. Theodore Ernest Els (born 17 October 1969) is a South African professional golfer. A former World No. 1, he is known as \"The Big Easy\" due to his imposing physical stature (he stands 6 ft 3 in (1.91 m)) along with his fluid golf swing.","e_s_31.webp",5,1);

//Found
        this.questionCreator("Which Olympic athlete is nicknamed ‘The Lightning Bolt’?","Usain Bolt","Belinda Dickson","Kamora Briggs","Aaliyah Mullins","Usain Bolt","Usain St. Leo Bolt was born in 1986 and is a Jamaican runner and eight-time Olympic gold-medal winner. He is also known as \"Lightning Bolt\". Some people call him the fastest man in the world.","e_s_32.webp",5,1);

//Found
        this.questionCreator("Galatasaray are a football team based in which European country?","Turkey","Berlin","Paris","London","Turkey","Galatasaray Spor Kulübü is a Turkish sports club based on the European side of the city of Istanbul in Turkey, most notable for its association football department.","e_s_33.webp",5,1);

//Found
        this.questionCreator("A left-handed boxer is called a ‘what’…paw?","Southpaw","Powerpaw","Fastpaw","Jobpaw","Southpaw","In boxing a southpaw is Left-handed boxer who leads with his right hand and right foot forward. As a result, when a pitcher is on the mound facing home plate, his left hand pointed south, so lefty pitchers came to be known as southpaws.","e_s_34.webp",5,1);

//Found
        this.questionCreator("Who was the first unseeded tennis player to win the Wimbledon Men’s Finals?","Boris Becker","Esteban David","Marc Mcgrath","Antwan Ashley","Boris Becker","In 1985, Boris Becker accomplished three feats: he became the youngest male singles champ (17 years, 227 days old), the first German champ, and the first unseeded champ.","e_s_35.webp",5,1);

//Found
        this.questionCreator("What is the third event in a men’s decathlon?","Shot put","Wiffleball","Cricket","Crew","Shot put","It was introduced as a three-day event at the Olympic Games in 1912. Decathlon events are: (first day) 100-metre dash, running long (broad) jump, shot put, high jump, and 400-metre run; (second day) 110-metre hurdles, discus throw, pole vault, javelin throw, and 1,500-metre run.","e_s_36.webp",5,1);

//Found
        this.questionCreator("What is former England footballer Gary Lineker’s middle name?","Winston","Chan","Watts","Giles","Winston","Gary Winston Lineker OBE (born 30 November 1960) is an English former professional footballer and current sports broadcaster. He holds England\'s record for goals in FIFA World Cup finals, with 10 scored.","e_s_37.webp",5,1);

//Found
        this.questionCreator("Which English racehorse and Irish Grand National Winner was known as ‘Dessie’?","Desert Orchid","Desert Eagles","Desert Cougars","Desert Broncos","Desert Orchid","Desert Orchid (11 April 1979 – 13 November 2006), known as Dessie, was an English racehorse. The grey achieved a revered and estemeed status within National Hunt racing, where he was much loved by supporters for his front-running attacking style, iron will and extreme versatility.","e_s_38.webp",5,1);

//Found
        this.questionCreator("Golfer Gary Player was born in which country?","South Africa","Mongolia","Malta","Niue","South Africa","Gary Player DMS, OIG is a South African professional golfer widely considered one of the greatest golfers ever born on 1 November 1935 (age 83 years), Johannesburg, South Africa","e_s_39.webp",5,1);

//Found
        this.questionCreator("How many points are scored for a penalty goal in a game of rugby league?","2","3","4","5","2","Drop goal\' — 1 point. Scored by drop kicking the ball so that it passes between the posts and above the crossbar. \'Penalty goal\' — 2 points. After an infringement, the team awarded a penalty can take a kick at goal and will gain two points if successful.","e_s_40.webp",5,1);

//Found
        this.questionCreator("Pitch and Putt is a form of which game?","Golf","Curling","Track","Boxing","Golf","Pitch and putt is an amateur sport very similar to golf but where the hole length is typically up to 70 metres (80 yd).","e_s_41.webp",5,1);

//Found
        this.questionCreator("Which boxer wont the WBA and IBF World Heavyweight Championships in 1994, becoming the oldest heavyweight champion in history?","George Foreman","Jadon Hooper","Yaritza Jenkins","Lucille Jacobs","George Foreman","George Foreman becomes oldest heavyweight champ. On this day in 1994, George Foreman, age 45, becomes boxing\'s oldest heavyweight champion when he defeats 26-year-old Michael Moorer in the 10th round of their WBA fight in Las Vegas.","e_s_42.webp",5,1);
//Not Found
        this.questionCreator("Who were the runners-up in the 1978 FIFA World Cup?","Russia","Ethiopia","Iran","India","Russia","Rob Rensenbrink had a glorious stoppage-time opportunity to win it for the Netherlands but his effort came back off the goal post. Argentina won the final 3–1 after extra time, after Daniel Bertoni scored and Kempes, who finished as the tournament\'s top scorer with six goals, added his second of the day.","e_s_43.webp",5,1);

//Found
        this.questionCreator("Which American football player was nicknamed ‘The Juice’?","O J Simpson","Shaniya Stein","Jase Sandoval","Antwan Wheeler","O J Simpson","Orenthal James \"O. J.\" Simpson (born July 9, 1947), nicknamed The Juice, is an American former running back, broadcaster, actor, advertising spokesman, and convicted robber.","e_s_44.webp",5,1);

//Found
        this.questionCreator("The Bundesliga is a professional association football league in which country?","Germany","Gambia","Dominica","Bahamas","Germany","The Bundesliga is a professional association football league in Germany and the football league with the highest average stadium attendance worldwide.","e_s_45.webp",5,1);

//Found
        this.questionCreator("At which racecourse is the Grand National run?","Aintree","Costa Rica","Pitcairn","Guadeloupe","Aintree","The Grand National is a National Hunt horse race held annually at Aintree Racecourse in Liverpool, England. First run in 1839, it is a handicap steeplechase over 4 miles 514 yards (6.907 km) with horses jumping 30 fences over two laps.","e_s_46.webp",5,1);

//Found
        this.questionCreator("In the game of snooker, how many balls are present on the table at the beginning of a game?","22","23","25","24","22","Using a cue and 22 coloured balls, players must strike the white ball (or \"cue ball\") to pot the remaining balls in the correct sequence, accumulating points for each pot.","e_s_47.webp",5,1);

//Found
        this.questionCreator("Who was the first unseeded man to win Wimbledon\'s Mens Singles Title?","Boris Becker","Vicente Long","Brenton Holt","Salma Spencer","Boris Becker","Becker became the first unseeded player and the first German to win the Wimbledon singles title, as well as the youngest ever male Grand Slam singles champion at 17 years, 7 months and 15 days.","e_s_48.webp",5,1);

//Found
        this.questionCreator("How many players are there in a Rugby League team?","13","14","11","10","13","Rugby league. Rugby league football is a full-contact sport played by two teams of thirteen players on a rectangular field. One of the two codes of rugby, it originated in Northern England in 1895 as a split from the Rugby Football Union over the issue of payments to players.","e_s_49.webp",5,1);

//Found
        this.questionCreator("Which sport is Silverstone most associated with?","Motor Racing","Football","Badminton","Fishing","Motor Racing","Silverstone Circuit is a motor racing circuit in England located next to the Northamptonshire villages of Silverstone and Whittlebury. The circuit straddles the Northamptonshire and Buckinghamshire border, with the current main circuit entry on the Buckinghamshire side.","e_s_50.webp",5,1);

//Found
        this.questionCreator("In 2004, the Olympic Games \'came home\' to which city?","Athens","Antarctica","Nauru","Bangladesh","Athens","The 2004 Summer Olympic Games (Greek: Θερινοί Ολυμπιακοί Αγώνες 2004, Therinoí Olympiakoí Agónes 2004), officially known as the Games of the XXVIII Olympiad and commonly known as Athens 2004, was a premier international multi-sport event held in Athens, Greece, from 13 to 29 August 2004 with the motto Welcome Home.","e_s_51.webp",5,1);

//Found
        this.questionCreator("What is the name given for one stroke over par for a single hole in golf?","A Bogey","A Pitch","A Hit","A Stroke","A Bogey","Bogey means one shot more than par (+1). \"Going round in bogey\" originally meant an overall par score, starting at the Great Yarmouth Golf Club in 1890.","e_s_52.webp",5,1);

//Found
        this.questionCreator("In 1996, whose ear did Mike Tyson partially bite off in the ring?","Evander Holyfield","Darren Chavez","Kyle Bowen","Irene Christensen","Evander Holyfield","Evander Holyfield vs. Mike Tyson II, billed as \"The Sound and the Fury\" and afterwards infamously referred to as \"The Bite Fight\", was a professional boxing match contested on June 28, 1997 for the WBA Heavyweight Championship.","e_s_53.webp",5,1);

//Found
        this.questionCreator("Where do the English Rugby team play their Home Internationals?","Twickenham","IPFW Mastodons","Idaho Vandals","Samford Bulldogs","Twickenham","Their home ground is Twickenham Stadium where they first played in 1910. The team is administered by the Rugby Football Union (RFU). Four former players have been inducted into the International Rugby Hall of Fame.","e_s_54.webp",5,1);

//Found
        this.questionCreator("What is the \'perfect score\' in a single game of Ten Pin Bowling?","300","500","800","600","300","In bowling games that use 10 pins, such as ten-pin bowling, candlepin bowling, and duckpin bowling, the highest possible score is 300, achieved by bowling 12 strikes in a row in a traditional single game: one strike in each of the first nine frames, and three more in the tenth frame.","e_s_55.webp",5,1);

//Found
        this.questionCreator("What is the maximum number of clubs a golfer is allowed in their bag for a round of golf?","14","5","4","13","14","Club Limits. According to the USGA, a golfer is allowed to have 14 clubs in his bag. This may include three woods (driver, 3-wood and 5-wood), eight irons, (3-9 iron and pitching wedge), and putter. These are the standard 12 clubs in many golf bags.","e_s_56.webp",5,1);

//Found
        this.questionCreator("Which TV sports quiz show was hosted by Nick Hancock?","They Think Its All Over..","Think About","Think Clear","Think Smart","They Think Its All Over..","Nick Hancock. Nick Hancock (born 25 October 1962, Stoke-on-Trent, Staffordshire) is an English actor and television presenter. He hosted the sports quiz They Think It\'s All Over for 10 years. He also formerly presented Room 101 (1994–1999) on TV, as well as its earlier radio version (1992–1994).","e_s_57.webp",5,1);

//Found
        this.questionCreator("What nationality is jockey Frankie Dettori?","Italian","British","American","Uruguayan","Italian","Lanfranco \"Frankie\" Dettori, MBE (born 15 December 1970) is an Italian horse racing jockey in the United Kingdom. Dettori has been Champion Jockey on three occasions and has ridden the winners of more than 500 Group races.","e_s_58.webp",5,1);

//Found
        this.questionCreator("What is the name of the British Triple Jumper who won gold at the 2000 Olympics?","Jonathan Edwards","Brayan Pierce","Elle Munoz","Skyler Gibson","Jonathan Edwards","Edwards walked away with the silver after a leap of 17.88 m (the longest ever jump not to win gold). Edwards won the gold medal at the 2000 Olympic Games, and was appointed a CBE shortly thereafter. He also won golds at the 2001 World Championships and 2002 Commonwealth Games.","e_s_59.webp",5,1);

//Found
        this.questionCreator("In Snooker what colour is the ball that has a value of three points?","Green","Brown","Blue","Pink","Green","It is played using a cue and snooker balls: one white cue ball , 15 red balls worth one point each (sometimes played with fewer red balls, commonly 6 or 10), and six balls of different colours : yellow (2 points), green (3), brown (4), blue (5), pink (6), black (7).","e_s_60.webp",5,1);

//Found
        this.questionCreator("In what sport would you find a Bagel?","Tennis (A set won or lost 6-0)","Ice Hockey","Pool","Bowling","Tennis (A set won or lost 6-0)","In tennis, a bagel is a term denoting a situation when the set ends with a score of 6–0. Despite the slang character of the term, it is actively used not only in colloquial speech but also in print. An extremely rare type of bagel, where no point is lost, is called a Golden Set.","e_s_61.webp",5,1);

//Found
        this.questionCreator("Brazilian football legend Pele wore which number on his shirt?","10","7","13","28","10","Wearing his number 10 shirt, Pelé produced one of the best performances of his career, scoring a hat-trick in Lisbon as Santos won 5–2. As the defending champions, Santos qualified automatically to the semi-final stage of the 1963 Copa Libertadores.","e_s_62.webp",5,1);

//Found
        this.questionCreator("Women competed in the 3,000 metre steeplechase for the first time in the Summer Olympics in which year?","2008","2005","2003","2001","2008","The men\'s 3000 metres steeplechase has been present on the Olympic athletics programme since 1920. The women\'s event is the most recent addition to the programme, having been added at the 2008 Olympics. It is the most prestigious steeplechase track race at elite level.","e_s_63.webp",5,1);

//Found
        this.questionCreator("Which English football club play their home games at Craven Cottage?","Fulham FC","Detroit Titans","Belmont Bruins","Temple Owls","Fulham FC","The Fulham Ladies (before their demise) and Reserve teams occasionally play home matches at the Cottage. Other than this, they generally play at the club\'s training ground at Motspur Park or at Kingstonian and AFC Wimbledon\'s stadium, Kingsmeadow.","e_s_64.webp",5,1);

//Found
        this.questionCreator("The US team Miami Heat play which sport?","Basketball","Golf","Soccer","Rugby","Basketball","The Miami Heat are an American professional basketball team based in Miami. The Heat compete in the National Basketball Association (NBA) as a member of the league\'s Eastern Conference Southeast Division. They play their home games at the American Airlines Arena in downtown Miami.","e_s_65.webp",5,1);

//Found
        this.questionCreator("What\'s the mother\'s name of a former Los Angeles Lakers guard Kobe Bryant.","Pam Bryant","Jessica Bryant","Cloe Bryant","Melody Bryant","Pam Bryant","Pamela Cox Bryant (born 1954) is an American socialite and is best known for being the wife of former is married to National Basketball Association (NBA) player Joe Bryant. She is also the mother of former Los Angeles Lakers guard Kobe Bryant.","e_s_66.webp",5,1);

//Found
        this.questionCreator("What\'s the fathers\'s name of a former Los Angeles Lakers guard Kobe Bryant.","Joe Bryant","Michael Bryant","Steve Bryant","Klay Bryant","Joe Bryant","Joseph Washington \"Jellybean\" Bryant is an American retired professional basketball player, current coach, and the father of former Los Angeles Lakers guard Kobe Bryant. Bryant was the head coach of the WNBA\'s Los Angeles Sparks from August 22, 2005 until April 4, 2007.","e_s_67.webp",5,1);

//Found
        this.questionCreator("How old was Michael Jordan when he retired for good?","40","30","28","29","40","On February 21, 2003, Jordan became the first 40-year-old to tally 43 points in an NBA game. During his stint with the Wizards, all of Jordan\'s home games at the MCI Center were sold out, and the Wizards were the second most-watched team in the NBA, averaging 20,172 fans a game at home and 19,311 on the road.","e_s_68.webp",5,1);

//Found
        this.questionCreator("Who has the most rings in the NBA?","Bill Russell","Kevin Durant","Michael Jordan","Kobe Bryant","Bill Russell","Boston Celtics center Bill Russell holds the record for the most NBA championships won with 11 titles during his 13-year playing career. He won his first championship with the Boston Celtics in his rookie year.","e_s_69.webp",5,1);

//Found
        this.questionCreator("What does NBA stand for?","National Basketball Association","National Ball Association","National Basket Association","National Base Association","National Basketball Association","The BAA, Basketball Association of America was founded in 1946 and three years later, in 1949, it merged with the National Basketball League to create the National Basketball Association. The NBA regards the first ever game in league history as between the New York Knickerbockers and the Toronto Huskies. The game took place in Toronto, Canada on November 1, 1946. The Knickerbockers won the game 68-66.","e_s_70.webp",5,1);

//Found
        this.questionCreator("In 2009 how many teams were in the NBA?","30 teams","23 teams","24 teams","15 teams","30 teams","In 2009 there were two conferences in the NBA, the Western and the Eastern Conferences. In each conference there are three different divisions. In the Eastern Conference the divisions were called the Atlantic, Central, and Southeast divisions. In the Western Conference the divisions were called Northwest, Pacific, and Southwest divisions. Each division was composed of five teams apiece.","e_s_71.webp",5,1);

//Found
        this.questionCreator("What is the name of the championship series that the NBA has to end the playoffs and find a champion?","NBA Finals","NBA All-stars","NBA Draft","NBA Agents","NBA Finals","The NBA Finals is a series of basketball games that the top two teams from each conference go to after making it through the playoffs. To become the NBA Champion, a team must win four games in the NBA Finals. The maximum amount of games that can be played in the NBA Finals is seven.","e_s_72.webp",5,1);

//Found
        this.questionCreator("Midway through the regular season the NBA hosts a game between the Eastern and Western Conferences. What is this game called?","NBA All-Star Game","NBA Finals","NBA Semi-Finals","NBA Practice","NBA All-Star Game","Players are chosen first by fan vote and then the reserves are chosen by head coaches from the League. The first NBA All-Star game took place on March 2, 1951. The Eastern Conference won the first ever All-Star game. Ed Macauley won the first ever NBA All-Star Game MVP.","e_s_73.webp",5,1);

//Found
        this.questionCreator("Before the start of each season the NBA holds an event in which teams select players from colleges or other countries to play for their teams. What is this event called?","NBA Draft","NBA Semi-Finals","NBA Practice","NBA All-Star Game","NBA Draft","The first draft was held on July 1, 1947. The draft in 1947 was called the BAA Draft, BAA standing for Basketball Association of America, because this was before the merger with the NBL, National Basketball League. The first draft to be called an NBA Draft took place in 1950. The first ever #1 overall pick was Clifton McNeely. Clifton was drafted by the Pittsburgh Ironmen. He played college basketball for Texas Wesleyan.","e_s_74.webp",5,1);

//Found
        this.questionCreator("Each year the NBA awards the Most Valuable Player (MVP) award to a player. Who was the first player born outside of the United States to win the NBA MVP award?","Hakeem Olajuwon","Nash Cooke","Giovanni Pope","Julissa Bailey","Hakeem Olajuwon","Hakeem Olajuwon was born in Lagos, Nigeria. He won the MVP award in the 1993-94 NBA season. He played for the Houston Rockets when he won the MVP. In that same year the Houston Rockets also won the NBA Championship. This was his only NBA MVP award that he won in his career. The NBA MVP award was first given out in the 1955-56 NBA season. It was won by Bob Pettit.","e_s_75.webp",5,1);

//Found
        this.questionCreator("In 2010 which team in the NBA was the only team located outside of the United States?","Toronto Raptors","L.A Lakers","Miama Heat","Chicago Bulls","Toronto Raptors","The Toronto Raptors are located in Toronto, Ontario, Canada. The Raptors were founded in 1995. The Toronto Raptors went to the playoffs for the first time in their history in the 1999-2000 NBA season. They lost in the first round to the New York Knicks. The Knicks won the series three games to none.","e_s_76.webp",5,1);

//Found
        this.questionCreator("Who is the silhouette of in the 2010 NBA logo?","Jerry West","Theodore Glover","Paris Moon","Brenden Baird","Jerry West","The 2009 NBA Logo was made up of the colors blue, white, and red. Jerry West\'s silhouette is in the white portion of the logo. The acronym \"NBA\" is shown to the left of Jerry\'s image in the logo. The 2009 NBA Logo was made in the 1971-72 NBA season, while Jerry was still playing. Jerry West played for the Los Angeles Lakers for his whole career, from 1960-1974. Jerry was inducted into the Basketball Hall of Fame in 1979.","e_s_77.webp",5,1);

//Found
        this.questionCreator("What is the hall of fame for the NBA called?","Naismith Memorial Basketball Hall of Fame","Jordan Memorial Basketball Hall of Fame","Durant Memorial Basketball Hall of Fame","Curry Memorial Basketball Hall of Fame","Naismith Memorial Basketball Hall of Fame","The Naismith Memorial Basketball Hall of Fame opened in 1959. The hall of fame is located in Springfield, Massachusetts. It is named after basketball\'s founder, James Naismith. The first class inducted into the Basketball Hall of Fame consisted of James Naismith, along with the first team he ever put together.","e_s_78.webp",5,1);

//Found
        this.questionCreator("Who won the NBA Championship in the 2008-09 season?","Los Angeles Lakers","Detroit Pistons","Golden State Warriors","Boston Celtics","Los Angeles Lakers","The Los Angeles Lakers faced off against the Orlando Magic in the 2009 NBA Finals. The Lakers beat the Magic 4-1, winning the series in five games. The NBA Finals MVP was Kobe Bryant. This was their 15th NBA Championship in the history of their team.","e_s_79.webp",5,1);

//Found
        this.questionCreator("How many players are there in volleyball team?","Six","Seven","Eight","Ten","Six","Volleyball is a team sport in which two teams of six players are separated by a net. Each team tries to score points by grounding a ball on the other team's court under organized rules. It has been a part of the official program of the Summer Olympic Games since 1964.","e_s_80.webp",5,1);

//Found
        this.questionCreator("How many players are there in basketball team?","Five","Four","Nine","Six","Five","Basketball is played with two teams, with 5 players from each team on the court at one time. The maximum number of players on the bench differs by league. In international play, a maximum of 7 players are allowed on the bench, resulting in a roster of 12 players.","e_s_81.webp",5,1);

//Found
        this.questionCreator("Which is the most powerful piece in chess?","Queen","King","Horse","Bishop","Queen","“The queen is the most powerful piece in the game of chess, able to move any number of squares vertically, horizontally or diagonally. Each player starts the game with one queen, placed in the middle of the first rank next to the king.”","e_s_82.webp",5,1);

//Found
        this.questionCreator("What color are in the five Olympics ring are?","Red, blue, black, yellow and green","Brown, blue, orange, peach and yellow","Pink, white, green, violet and red","Maroon, navy blue, yellow green, purple and red","Red, blue, black, yellow and green","“The Olympic flag has a white background, with five interlaced rings in the center: Blue, Yellow, Black, Green and Red. This design is symbolic; it represent the five continents of the world, united by Olympics, while the six colors are those that appear on all the national flags of the world at the present time.”","e_s_83.webp",5,1);

//Found
        this.questionCreator("Which color belt comes after white in karate?","Yellow","Blue","Red","Black","Yellow","“Originally, the white belt was simply dyed to a new color. This repeated dying process dictated the type of belt color and the order of the colors. The standard belt color system is white, yellow, gold, orange, green, blue, purple, brown, red, and black.”","e_s_84.webp",5,1);

//Found
        this.questionCreator("In baseball, if a batter swings at a ball and misses a pitch, it is considered a what?","Strike","Misses ball","Ball","Mistake","Strike","“A strike in baseball results when a batter swings at and misses a pitch, does not swing at a pitch in the strike zone or hits a foul ball that is not caught.”","e_s_85.webp",5,1);

//Found
        this.questionCreator("How many bases are there on a baseball field including the home plate?","Four","Five","Six","Three","Four","“The bases are perhaps the most important part of the baseball field. There are four bases: home plate, first base, second base, and third base.","e_s_86.webp",5,1);

//Found
        this.questionCreator("How many players are there in a baseball team, on the field at one time?","Nine","Eleven","Ten","Twelve","Nine","“A baseball game is played between two teams, each composed of nine players, that take turns playing offense (batting and baserunning) and defense (pitching and fielding).”","e_s_87.webp",5,1);

//Found
        this.questionCreator("How many players are there in beach volleyball game?","Two","Four","Six","Five","Two","“Beach Volleyball is a team sport played by two teams of two players on a sand court divided by a net.”","e_s_88.webp",5,1);

//Found
        this.questionCreator("What belt is the highest in karate?","Red belt","Black belt","Pink belt","Yellow belt","Red belt","“In some schools, a red belt signifies ninth or tenth degree Dan rank, where the tenth degree is the highest rank attainable. In karate, even though grandmaster and non-black belts (according to whatever system) may both wear a “red belt”, the difference is obvious.”","e_s_89.webp",5,1);

//Found
        this.questionCreator("What place sumo wrestling originated from?","Japan","Thailand","Korea","Vietnam","Japan","“Sumo means “striking one another”. The sport originated in japan, the only country where it is practiced professionally.”","e_s_90.webp",5,1);

//Found
        this.questionCreator("Where did Arnis originate?","Philippine","Spain","France","China","Philippine","“The origin of Arnis can be traced back to native fighting techniques during conflicts among the various Prehispanic Filipino tribes or kingdoms, though the current from has Spanish influence from old fencing which originated in Spain in the 15th century.”","e_s_91.webp",5,1);

//Found
        this.questionCreator("Where can you find arnis being widely practice?","Philippine","India","China","France","Philippine","“Arnis is a very old Filipino martial art. It uses swords, daggers and sticks, as well as weaponless techniques using the hands and feet.”","e_s_92.webp",5,1);

//Found
        this.questionCreator("How many holes are there in a bowling ball?","Three","Two","Four","One","Three","“There are three holes in a regulation bowling ball: one for the thumb and two for the first two fingers.”","e_s_93.webp",5,1);

//Found
        this.questionCreator("How many rings are there on the Olympic flag?","Five","Three","Four","Six","Five","“The Olympic Flag has five linked rings representing the five parts of the world joined by the Games: the Americas, Europe, Asia, Africa, and Australia.”","e_s_94.webp",5,1);

//Found
        this.questionCreator("In the US, what sport is known as “the pastime”?","Baseball","Basketball","Chess","Archery","Baseball","“Baseball, a nine-player game involving throwing and hitting a ball, is one of the most popular professional sports in the US.”","e_s_95.webp",5,1);

//Found
        this.questionCreator("Which of the following sports does not involve a ball?","Badminton","Tennis","Squash","Table tennis","Badminton","Badminton is a racquet sport played using racquets to hit a shuttlecock across a net. Although it may be played with larger teams, the most common forms of the game are \"singles\" and \"doubles\"","e_s_96.webp",5,1);

//Found
        this.questionCreator("In which sport do you run and jump as far as possible into a sandpit?","Long jump","Volleyball","Javelin throw","Discus throw","Long jump","“Long jump is a track and field event in which athletes combine speed, strength and agility in an attempt to leap as far as possible from a take-off point”","e_s_97.webp",5,1);

//Found
        this.questionCreator("Which sport uses the lightest ball?","Table tennis","Softball","Baseball","Basketball","Table tennis","“Table tennis, also known as Ping-Pong, is a sport in which two or four players hit a lightweight ball back and forth across a table using small bats”","e_s_98.webp",5,1);

//Found
        this.questionCreator("Which outdoor sport involves a club, a ball and holes in the ground?","Golf","Baseball","Softball","Cricket","Golf","“Golf is a club-and-ball sport in which players use various clubs to hit balls into a series of holes on a course in as few strokes as possible”","e_s_99.webp",5,1);

//Found
        this.questionCreator("Which sport did Mike Tyson played?","Boxing","Dance sport","Sumo wrestling","Judo","Boxing","“Mike Tyson dominated boxing in the 1980s like no other in his sport ever did. With explosive power and fast knockouts, Tyson rose to stardom to become the youngest heavyweight champion in boxing history”","e_s_100.webp",5,1);

//Found
        this.questionCreator("Which the national game of Brazil?","Football","Baseball","Hockey","Fencing","Football","“Football is the most popular sport in Brazil. Other than football, sports like volleyball, mixed martial arts, basketball, and motor sports, especially Formula One, enjoy high levels of popularity”","e_s_101.webp",5,1);


    }

    private void addSportsModerate()
    {
        //checked

//Found
        this.questionCreator("What distance is the Breeders' Cup Classic?","One and a quarter miles","Two and a quarter miles","Three and a quarter miles","Four and a quarter miles","One and a quarter miles","The Breeders' Cup Classic is run at the American classic distance of 1 1/4 miles over the dirt.","m_s_1.webp",5,2);

//Found
        this.questionCreator("Where did Venus Williams make her 1274 mph serve in 1998?","European Indoor Championships Zurich","European Athletics Championships","World Athletics Championships","World European Athletics","European Indoor Championships Zurich","Williams holds the record for fastest serve in three of the four Grand Slam tournaments: 2007 French Open second round, 2008 Wimbledon final, 2007 U.S. Open first round – 129 mph (208 km/h).[21]","m_s_2.webp",5,2);

//Found
        this.questionCreator("Where was golf's 1977 US Open held?","Tulsa Oklahoma","California","Florida","Texas","Tulsa Oklahoma","The 1977 U.S. Open was the 77th U.S. Open, held June 16–19 at Southern Hills Country Club in Tulsa, Oklahoma. Hubert Green won the first of his two major titles, one stroke ahead of runner-up Lou Graham, the 1975 champion","m_s_3.webp",5,2);

//Found
        this.questionCreator("Who lost the first Super Bowl of the 70s?","Minnesota Vikings","Miami Dolphins","Cleveland Browns","San Diego Chargers","Minnesota Vikings","The Dolphins defeated the Vikings by the score of 24–7 to win their second consecutive Super Bowl, the first team to do so since the Green Bay Packers in Super Bowls I and II, and the first AFL/AFC team to do so. The game was played on January 13, 1974 at Rice Stadium in Houston, Texas.","m_s_4.webp",5,2);

//Found
        this.questionCreator("How many times did the New York Yankees win the World Series in the 1970s?","Twice","Once","Thrice","Fourth","Twice","The American League East rivals have squared off in the American League Championship Series three times, with the Yankees winning twice in 1999 and 2003 and the Sox winning in 2004.","m_s_5.webp",5,2);

//Found
        this.questionCreator("Which team in the 80s won the Super Bowl by the biggest margin?","Chicago Bears","Denver Broncos","Minnesota Vikings","New York Yankees","Chicago Bears","Their 36-point margin of victory topped the 29-point margin of victory that the Raiders had put up in Super Bowl XVIII and stood as a record until the 49ers won Super Bowl XXIV, also in New Orleans, by 45 points over the Denver Broncos.","m_s_6.webp",5,2);

//Found
        this.questionCreator("Who won baseball's first World Series of the 50s?","New York Yankees","Orioles","Marlins","Pirates","New York Yankees","The Philadelphia Phillies as 1950 champions of the National League and the New York Yankees, as 1950 American League champions, competed to win a best-of-seven game series.","m_s_7.webp",5,2);

//Found
        this.questionCreator("Who kicked a record-breaking punt 98 yards against the Denver Broncos in 1969?","Steve O\'Neal","Rodney Schneider","Amare Sosa","Harley Saunders","Steve O\'Neal","The longest punt in NFL history belongs to the Jets' Steve O'Neal, who uncorked one 98 yards, yes, at Mile High in 1969. The ball traveled approximately 75 yards through the thin air. Since the merger in 1970, only one team the Steelers has more home wins than the Broncos' 223.","m_s_8.webp",5,2);

//Found
        this.questionCreator("Which American was the youngest Olympic medalist when she won in Helsinki in 1952?","Barbara Jones","Whitney Mcdaniel","Jaiden Rojas","Sidney Farrell","Barbara Jones","The youngest athletics medallist - gold or otherwise - is Barbara Jones, who was only 15 when she was part of the American team which won the sprint relay in Helsinki in 1952. The youngest in an individual event is West Germany's Ulrike Meyfarth, who was 16 when she won the high jump in Munich in 1972","m_s_9.webp",5,2);

//Found
        this.questionCreator("Who was Jermaine O'Neal playing against when he made his debut in 1996?","Denver Nuggets","San Diego Chargers","Buffalo Bills","New York Giants","Denver Nuggets","O'Neal made his debut against the Denver Nuggets in December. At 18 years, one month and 22 days, he became the youngest player to play in an NBA game (a mark that was later eclipsed by Andrew Bynum).","m_s_10.webp",5,2);

//Found
        this.questionCreator("Who won the most Super Bowls in the 1970s?","Pittsburg Steelers","Green Bay Packers","Philadelphia Eagles","Kansas City Chiefs","Pittsburg Steelers","Twenty franchises, including teams that have relocated to another city, have won the Super Bowl. The Pittsburgh Steelers (6–2) have won the most Super Bowls with six championships, while the New England Patriots (5–5), the Dallas Cowboys (5–3), and the San Francisco 49ers (5–1) have five wins.","m_s_11.webp",5,2);

//Found
        this.questionCreator("Who's home runs record did Mark McGwire break in the 1998 season?","Roger Maris","Arthur Banks","Zoie Simpson","Callum Castaneda","Roger Maris","Saturday is the anniversary of the swing that broke the 37-year-old Major League Baseball record for most home runs in a single season. Twenty years ago, on Sept. 8, 1998, Mark McGwire won a race against Sammy Sosa to surpass Roger Maris' record of 61 home runs in a 162-game season","m_s_12.webp",5,2);

//Found
        this.questionCreator("Who was the second American to win the Indianapolis 500 four times?","Al Unser Sr","Roderick Heath","Bailey Gardner","Helen Baldwin","Al Unser Sr","Alfred \"Al\" Unser (born May 29, 1939) is an American automobile racing driver, the younger brother of fellow racing drivers Jerry and Bobby Unser, and father of Al Unser Jr. Now retired, he is the second of three men to have won the Indianapolis 500-Mile Race four times.","m_s_13.webp",5,2);

//Found
        this.questionCreator("Who was the aerobatic world champion in 1988?","Henry Haigh","Tristen Davies","Declan Owens","Dania Schwartz","Henry Haigh","Henry Haigh won the World Aerobatic Men's Championship at Red Deer, Canada in 1988, as a member of the United States Aerobatic Team. He is the 3rd and only living man to achieve this accomplishment in this country. He was a member of the team each year from 1976 until he retired after the 1990 contest.","m_s_14.webp",5,2);

//Found
        this.questionCreator("In 1978 US Masters, who was leading Gary Player by seven strokes, only to lose by a single stroke?","Hubert Green","Kyra Holt","Matias Trevino","Clarissa Lynn","Hubert Green","His first win, as a 23-year-old in 1959 at Muirfield, came after he double-bogeyed the last hole. In 1974, he became one of the few golfers in history to win two major championships in the same season. Player last won the U.S. Masters in 1978.","m_s_15.webp",5,2);

//Found
        this.questionCreator("Where were the first World Athletics Championships held?","Helsinki","Alavieska","Enonkoski","Forssa","Helsinki","The inaugural 1983 World Championships in Athletics were run under the auspices of the International Association of Athletics Federations and were held at the Olympic Stadium in Helsinki, Finland between 7 and 14 August 1983.","m_s_16.webp",5,2);

//Found
        this.questionCreator("Who was the first MVP in a Super Bowl to be on the losing side?","Chuck Howley","Tyrese Ballard","Bridger Gutierrez","Eleanor Leach","Chuck Howley","The MVP has come from the winning team every year except 1971, when Dallas Cowboys linebacker Chuck Howley won the award despite the Cowboys' loss in Super Bowl V to the Baltimore Colts.","m_s_17.webp",5,2);

//Found
        this.questionCreator("Which team has not won a World Series since Babe Ruth stopped pitching for them?","Boston Red Sox","White Sox","Tigers","Rangers","Boston Red Sox","The Curse of the Bambino was a superstition evolving from the failure of the Boston Red Sox baseball team to win the World Series in the 86-year period from 1918 to 2004. While some fans took the curse seriously, most used the expression in a tongue-in-cheek manner.","m_s_18.webp",5,2);

//Found
        this.questionCreator("What were Joe DiMaggio's two baseball-playing brothers called?","Dom & Vincent","Regan & Burton","Ben & Hill","Wesley & Morse","Dom & Vincent","Joseph Paul DiMaggio (November 25, 1914 – March 8, 1999), nicknamed \"Joltin' Joe\" and \"The Yankee Clipper\", was an American baseball center fielder who played his entire 13-year career in Major League Baseball for the New York Yankees.","m_s_19.webp",5,2);

//Found
        this.questionCreator("In golf, who was the oldest British Open winner of the century?","Robert de Vicenzo","Paulina Santiago","Richard Cobb","Myah Marquez","Robert de Vicenzo","The all-time recordholder as oldest winner of the British Open is Old Tom Morris, who won in 1867 when he was 46 years and 99 days old. Post-1900, the oldest winner is Roberto De Vicenzo, who was 44 years and 93 days old old when he won the 1967 Open Championship.","m_s_20.webp",5,2);

//Found
        this.questionCreator("Arnaud Massey is the only Frenchman to have won that?","Golf's British Open","Golf's America Open","Golf's Iceland Open","Golf's Japan Open","Golf's British Open","In 1906, Massy won the first edition of the French Open played at a Paris course. The following year he won it again, defeating a strong contingent of British players including the great Harry Vardon. But Massy wasn't through, he followed up his French national championship by becoming the first non-Briton to win The Open Championship (British Open).","m_s_21.webp",5,2);

//Found
        this.questionCreator("Which sportswoman wrote the novel Total Zone?","Martina Navratilova","Patrick Sharp","Diego Dennis","Evelin Shaffer","Martina Navratilova","Martina Navratilova (October 18, 1956) is a former Czechoslovak and later American professional tennis player and coach. In 2005, Tennis magazine selected her as the greatest female tennis player for the years 1975 through 2005 and she is considered one of the best, if not the best, female tennis players of all time.","m_s_22.webp",5,2);

//Found
        this.questionCreator("Who skippered Stars & Stripes in the America's Cup in 1987 and 1988?","Dennis Conner","Denisse Watson","Valentino Parker","Amaya Fletcher","Dennis Conner","The 1988 America's Cup was the first hostile Deed of Gift challenge. Dennis Conner had won the America's Cup for the San Diego Yacht Club on 4 February 1987 at the 1987 America's Cup.","m_s_23.webp",5,2);

//Found
        this.questionCreator("Who did Martina Navratilova beat to win her ninth Wimbledon title?","Zina Garrison","Caleb Abbott","Phoenix Newman","Isaias Mitchell","Zina Garrison","On July 7, 1990, Martina Navratilova made history at Wimbledon, beating Zina Garrison for her ninth singles title at the All England Club.","m_s_24.webp",5,2);

//Found
        this.questionCreator("Who captained the US Ryder Cup team in 1991?","Dave Stockton","Joslyn Hickman","Karsyn Koch","Giuliana Khan","Dave Stockton","Stockton played for the U.S. team in the Ryder Cup in 1971 and 1977. He was the Americans' victorious non-playing captain in the 1991 Ryder Cup at Kiawah Island.","m_s_25.webp",5,2);

//Found
        this.questionCreator("Which team in the 70s won the Super Bowl by the biggest margin?","Oakland Raiders","Minnesota Vikings","New England Patriots","Cincinnati Bengals","Oakland Raiders","The Raiders compete in the National Football League (NFL) as a member club of the league's American Football Conference (AFC) West division. Founded on January 30, 1960, they played their first regular season game on September 11, 1960, as a charter member of the American Football League (AFL) which merged with the NFL in 1970.","m_s_26.webp",5,2);

//Found
        this.questionCreator("Which head coach holds the record for most Super Bowl wins?","Chuck Noll","Kobe Mullins","Blake Henry","Derrick Warren","Chuck Noll","Noll was the first head coach to win four Super Bowls, coaching the Steelers to victory in Super Bowl IX (1975), Super Bowl X (1976), Super Bowl XIII (1979), and Super Bowl XIV (1980).","m_s_27.webp",5,2);

//Found
        this.questionCreator("Which European city hosted the first Olympic Games in which women were allowed to participate?","Paris – 1900","Prague","Madrid","Budapest","Paris – 1900","The 1900 edition of the Olympic Games was awarded to Paris during the first Olympic Congress, which took place in the French capital on 16-23 June 1894, and which also saw Athens confirmed as the host city for the 1896 Games.","m_s_28.webp",5,2);

//Found
        this.questionCreator("Which country will be hosting the 2014 Winter Olympic Games?","Russia","Bahrain","Cambodia","Denmark","Russia","The 2014 Winter Olympics, officially called the XXII Olympic Winter Games and commonly known as Sochi 2014, was an international winter multi-sport event that was held from 7 to 23 February 2014 in Sochi, Krasnodar Krai, Russia, with opening rounds in certain events held on the eve of the opening ceremony, 6 February 2014.","m_s_29.webp",5,2);

//Found
        this.questionCreator("What is the final event of a men’s decathlon?","1500 metres","900 metres","850 metres","1300 metres","1500 metres","Decathlon events are: (first day) 100-metre dash, running long (broad) jump, shot put, high jump, and 400-metre run; (second day) 110-metre hurdles, discus throw, pole vault, javelin throw, and 1,500-metre run.","m_s_30.webp",5,2);

//Found
        this.questionCreator("Who was the founder of the modern Olympic Games?","Baron Pierre de Coubertin","Peter Kerr","Emely Chan","Lia Melendez","Baron Pierre de Coubertin","Their creation was inspired by the ancient Olympic Games, which were held in Olympia, Greece, from the 8th century BC to the 4th century AD. Baron Pierre de Coubertin founded the International Olympic Committee (IOC) in 1894, leading to the first modern Games in Athens in 1896.","m_s_31.webp",5,2);

//Found
        this.questionCreator("Which city hosted the 1992 Summer Olympic Games?","Barcelona","Baghdad","Bangkok","Banjul","Barcelona","The city was also a host for the 1982 FIFA World Cup. On October 17, 1986, Barcelona was selected to host the 1992 Summer Games over Amsterdam, The Netherlands; Belgrade, Yugoslavia; Birmingham, UK; Brisbane, Australia; and Paris, France, during the 91st IOC Session in Lausanne, Switzerland.","m_s_32.webp",5,2);

//Found
        this.questionCreator("The decathlon is contested over how many days?","2","3","1","4","2","The decathlon consists of ten track and field events spread over two days. It is the most physically demanding event for athletes. On day one, the 100m, long jump, shot putt, high jump and 400m are contested. On day two, the competitors face the 110m hurdles, discus, pole vault, javelin and, finally, the 1500m","m_s_33.webp",5,2);

//Found
        this.questionCreator("Which US Olympic swimmer is nicknamed the ‘Baltimore Bullet’?","Michael Phelps","Koen Rich","Gerald Walters","Zion Ortega","Michael Phelps","Michael Phelps 'The Baltimore Bullet' Michael Phelps is an American retired competitive swimmer and the most decorated athlete in the history of the Olympic Games. 'The Baltimore Bullet' participated in 5 Olympics and won a total of 28 medals, the most ever. In all, the 28 medals include 23 gold, 3 silver and 2 bronze.Sep 27, 2018","m_s_34.webp",5,2);

//Found
        this.questionCreator("Composer Evangelos Odysseas Papathanassiou is better known by what name?","Vangelis","Valentine","Precious","Gross","Vangelis","Best known professionally as Vangelis is a Greek composer of electronic, progressive, ambient, jazz, and orchestral music. He is best known for his Academy Award–winning score for the film Chariots of Fire, composing scores for the films Blade Runner, Missing, Antarctica, 1492.","m_s_35.webp",5,2);

//Found
        this.questionCreator("\\“Faster, Higher, Stronger\" is the motto for the modern ‘what’?","Olympic Games","NBA Games","Golf Games","Ice Hockey Games","Olympic Games","The Olympic motto is Citius, Altius, Fortius, which is Latin for “Faster, Higher, Stronger.” The motto was proposed by the founder of the modern Olympics, Pierre de Coubertin, when the International Olympic Committee was formed in 1894. Coubertin said “These three words represent a programme of moral beauty.","m_s_36.webp",5,2);

//Found
        this.questionCreator("In which year were women first allowed to participate in the Olympic Games?","1900","1913","1923","1953","1900","Even in the early years of the modern Olympics, women were not well represented (consequently a rival Women's Olympics was held). Women participated for the first time at the 1900 Paris Games with the inclusion of women's events in lawn tennis and golf. Women's athletics and gymnastics debuted at the 1928 Olympics.","m_s_37.webp",5,2);

//Found
        this.questionCreator("How many gold medals did the USA win in the 2010 Winter Olympic Games?","9","10","3","5","9","The United States participated in the 2010 Winter Olympics in Vancouver, British Columbia, Canada. The U.S. team had a historic Winter Games, winning an unprecedented 37 medals. Team USA's medal haul, which included nine gold.","m_s_38.webp",5,2);

//Found
        this.questionCreator("one of the second event of a men’s decathlon?","110-metre hurdles","152-metre hurdles","125-metre hurdles","115-metre hurdles","110-metre hurdles","The first day\'s events include, in order, a 100-meter run, the long jump, shot put, high jump, and a 400-meter run. The second day\'s events, in order, include the 110-meter hurdles followed by the discus throw, pole vault, javelin throw and a 1500-meter run.","m_s_39.webp",5,2);

//Found
        this.questionCreator("Boss, Flex, Vane and Nock are terms used i  n which sport?","Archery","Gymnast","Ice Hockey","Bowling","Archery","A target, typically made from tightly compacted foam or straw , The amount of bend an arrow shaft provides; contrasted with spine , The stabilizing fin of an arrow , The notch at the rear end of an arrow; also the notches at the ends of the bow limbs to which the bowstring is attached, or looped over","m_s_40.webp",5,2);

//Found
        this.questionCreator("Which gymnast has won the most gold medals, nine in total, in Olympic history?","Larrisa Latynina","Israel Tanner","Riya Goodman","Braedon Alvarado","Larrisa Latynina","Larisa Latynina and Polina Astakhova each competed for the Soviet Union in 1956, 1960, and 1964. Latynina has the most medals of any female athlete in Olympic history, with 18. She won six medals in each Olympic Games that she competed in, winning the individual all-around titles in 1956 and 1960.","m_s_41.webp",5,2);

//Found
        this.questionCreator("In which US state were the 1960 Winter Olympic Games held?","California","Hawaii","Washington","New York","California","1960 Winter Olympics. The 1960 Winter Olympics, officially known as the VIII Olympic Winter Games, was a winter multi-sport event held between February 18–28, 1960 in Squaw Valley, California, United States. Squaw Valley was chosen to host the Games at the 1956 meeting of the International Olympic Committee (IOC).","m_s_42.webp",5,2);

//Found
        this.questionCreator("Who was the only female competitor in the 1976 Montreal Olympics not to have been subjected to a sex test?","Princess Anne","Aiden Li","Lila Dyer","Aylin Case","Princess Anne","At the 1976 Summer Olympics, it has been reported that the only female competitor not to have to submit to a sex test was Princess Anne of the UK, who was competing as a member of the UK equestrian team. As the daughter Queen Elizabeth II, such a test was seen as inappropriate.","m_s_43.webp",5,2);

//Found
        this.questionCreator("Which Olympic event is Ben Ainslie famous for competing in?","Sailing","Badminton","Basketball","Boxing","Sailing","He is the first person to win medals in five different Olympic Games in sailing, the third person to win five Olympic medals in that sport (after Torben Grael and Robert Scheidt) and also the second to win four gold medals, after Paul Elvstrom.","m_s_44.webp",5,2);

//Found
        this.questionCreator("How many Gold Medals did Canada win in the 2010 Winter Olympics?","14","13","2","5","14","The 14 gold medals also set the all-time record for most gold medals at a single Winter Olympics, one more than the previous record of 13 set by the former Soviet Union in 1976 and Norway in 2002. Canada was the first host nation to win the gold medal count at a Winter Olympics since Norway at the 1952 Winter Olympics.","m_s_45.webp",5,2);

//Found
        this.questionCreator("What are the names of the two 2012 London Olympic Games mascots?","Wenlock & Mandeville","Aylin & Gracelyn","Lucia & Jefferson","Mya & Jenkins","Wenlock & Mandeville","Wenlock is the official mascot for the 2012 Summer Olympics, and Mandeville is the official mascot for the 2012 Summer Paralympics, both held in London, England, United Kingdom. They were created by Iris, a London-based creative agency.","m_s_46.webp",5,2);

//Found
        this.questionCreator("In 1920, which Belgian sportsman was the very first competitor to take the Olympic oath on behalf of all the other competitors?","Victor Boin","Amiyah Holland","Madalynn Ponce","Roselyn Pineda","Victor Boin","The athlete to recite the oath is a member of the host team, and proclaims the oath while holding a corner of his national flag. The Olympic oath was first taken during the 1920 Olympic Games by Belgian fencer Victor Boin.","m_s_47.webp",5,2);

//Found
        this.questionCreator("Who did boxer Joe Frazier replace in the 1964 Olympics because of a hand injury?","Buster Mathis","Kyler Stout","Austin Ward","Kelly Gray","Buster Mathis","Frazier emerged as the top contender in the late 1960s, defeating opponents that included Jerry Quarry, Oscar Bonavena, Buster Mathis, Eddie Machen, Doug Jones, George Chuvalo, and Jimmy Ellis en route to becoming undisputed heavyweight champion in 1970, and followed up by defeating Muhammad Ali by unanimous decision in the highly anticipated Fight of the Century in 1971.","m_s_48.webp",5,2);

//Found
        this.questionCreator("Which European city hosted the 2004 Summer Olympic Games?","Athens","Dublin","Lisbon","Stockholm","Athens","The 2004 Summer Olympic Games (Greek: Θερινοί Ολυμπιακοί Αγώνες 2004, Therinoí Olympiakoí Agónes 2004), officially known as the Games of the XXVIII Olympiad and commonly known as Athens 2004, was a premier international multi-sport event held in Athens, Greece, from 13 to 29 August 2004 with the motto Welcome Home.","m_s_49.webp",5,2);

//Found
        this.questionCreator("Which US retired speed skater has won five gold medals at the Winter Olympic Games?","Bonnie Blair","Giovanni Douglas","Ryan Warren","Ryan Zuniga","Bonnie Blair","Bonnie Blair won the 500- and 1,000-metre events, bringing her Olympic total to three gold medals, a first for an American woman. In speed skating Bonnie Blair won two gold medals, bringing her Olympic total to five gold medals, the most for an American athlete in the history of the Winter Olympics.","m_s_50.webp",5,2);

//Found
        this.questionCreator("Which US athlete won a gold medal for the high jump in the 1968 Summer Olympics?","Dick Fosbury","Gavin Wallace","Beau Case","Shamar Farley","Dick Fosbury","Besides winning a gold medal at the 1968 Olympics, he revolutionized the high jump event, with a unique \"back-first\" technique, now known as the Fosbury Flop, adopted by almost all high jumpers today.","m_s_51.webp",5,2);

//Found
        this.questionCreator("Which female swimmer won six Olympic Gold Medals for East Germany in 1988?","Kristin Otto","Krish Rowland","Kate Robbins","Jessie Baker","Kristin Otto","Kristin Otto (born 7 February 1966) is a German Olympic swimming champion. She is most famous for being the first woman to win six gold medals at a single Olympic Games, doing so at the 1988 Seoul Olympic games.","m_s_52.webp",5,2);

//Found
        this.questionCreator("In 1924, which country hosted the first Winter Olympic Games?","France","Uganda","Spain","Latvia","France","That was in 1924, at the first Winter Olympics in Chamonix, France, where 16 countries gathered to compete in sports like figure skating, speed skating, hockey, curling and more between Jan. 25 and Feb. 5, 1924.","m_s_53.webp",5,2);

//Found
        this.questionCreator("How many medals did Germany win in the 2002 Winter Olympic Games?","36","13","35","33","36","Germany competed at the 2002 Winter Olympics in Salt Lake City, United States. In terms of gold metals, Germany finished ranking second with 12 gold medals. Meanwhile, the 36 total medals won by German athletes were the most of any nation at these Games, as well at any Winter Olympics, until this record was broken by the United States at the 2010 Winter Olympics.","m_s_54.webp",5,2);

//Found
        this.questionCreator("In which year was women’s football officially introduced into the Summer Olympics?","1996","1912","1976","1957","1996","Softball was introduced as an Olympic sport for women only in the 1996 Summer Olympics. The decision to add the sport to the 1996 Olympic programme was made in 1995. The IOC had a committee called the International Olympic Committee on Women and Sports.","m_s_55.webp",5,2);

//Found
        this.questionCreator("In which year was live television coverage of the Olympic Games available internationally for the first time?","1956","1932","1921","1945","1956","1956 Summer Games. Television service was introduced to Australia in time for the 1956 Games in Melbourne. International broadcasting institutions present were BBC, CBS, NBC, Eurovision and United Press.","m_s_56.webp",5,2);

//Found
        this.questionCreator("Which sprinter lit the Olympic flame at the 2000 Summer Olympic Games in Australia?","Cathy Freeman","Koen Love","Asia Mayo","Lily Norris","Cathy Freeman","The 2000 Summer Olympics cauldron is a heritage-listed former Olympic flame holder and now fountain at Cathy Freeman Park, near the corner of Olympic Boulevard and the Grand Parade, Sydney Olympic Park, Cumberland Council, New South Wales, Australia.","m_s_57.webp",5,2);

//Found
        this.questionCreator("The distance of the Olympic marathon is 26 miles and how many yards?","385 yards","150 yards","250 yards","265 yards","385 yards","The marathon has an official distance of 42.195 kilometres (26.219 miles; 26 miles 385 yards), usually run as a road race.","m_s_58.webp",5,2);

//Found
        this.questionCreator("During which three years were the modern Summer Olympic Games cancelled?","1916, 1940, 1944","1918, 1940, 1944","1918, 1920, 1944","1918, 1940, 1935","1916, 1940, 1944","The Olympics have been canceled five times in the past. In 1916, the Summer Olympics scheduled for Berlin, Germany, were canceled due to World War I. Then, thanks to World War II, the 1940 Winter and Summer Olympics and the 1944 Winter and Summer Olympics were canceled.","m_s_59.webp",5,2);

//Found
        this.questionCreator("Which men’s sport was transferred permanently from the Summer Olympics to the Winter Olympic Games from 1924?","Ice hockey","Basketball","Shot put","Swimming","Ice hockey","Ice hockey tournaments have been staged at the Olympic Games since 1920. The men's tournament was introduced at the 1920 Summer Olympics and was transferred permanently to the Winter Olympic Games program in 1924, in France. The women's tournament was first held at the 1998 Winter Olympics.","m_s_60.webp",5,2);

//Found
        this.questionCreator("What was the name of the horse that Zara Phillips (Tindall) should have ridden at the 2008 Summer Olympics, but had to pull out because of an injury to the horse?","Toytown","Richards","Trujillo","Timothy","Toytown","Zara Phillips will not compete in this summer's Olympic Games in Beijing after her horse Toytown was injured during training. It is the second time that an injury to the horse has forced Phillips out of the Olympics after she also missed the Games in Athens in 2004.","m_s_61.webp",5,2);

//Found
        this.questionCreator("At the 1896 Athens Summer Olympic Games, the winners were given a silver medal and a branch from which type of tree?","Olive","Cordata","Walnut","Willow","Olive","During these inaugural Olympics, winners were given a silver medal and an olive branch, while runners-up received a copper medal and a laurel branch.","m_s_62.webp",5,2);

//Found
        this.questionCreator("Who was the only Olympic medallist to have won the Nobel Peace Prize?","Philip Noel-Baker","Alejandro Haas","Fernanda Petty","Augustus Mckinney","Philip Noel-Baker","Noel-Baker is the only person to have won an Olympic medal and received a Nobel Prize.","m_s_63.webp",5,2);

//Found
        this.questionCreator("How many rounds are there in an Olympic men’s boxing match?","3","2","1","5","3","Amateur boxing bouts are short in duration, comprising three rounds of three minutes in men, and four rounds of two minutes in women, each with a one-minute interval between rounds. Men's senior bouts changed in format from four two-minute rounds to three three-minute rounds on January 1, 2009.","m_s_64.webp",5,2);

//Found
        this.questionCreator("During which year were the first Youth Olympics held?","2010","2016","2005","2002","2010","The first summer version was held in Singapore from 14 to 26 August 2010 while the first winter version was held in Innsbruck, Austria from 13 to 22 January 2012.","m_s_65.webp",5,2);

//Found
        this.questionCreator("Which female gymnast won Gold Medals in the Balance Beam and Floor Exercise events in the 1972 Summer Olympics?","Olga Korbut","Kian Allen","Danna Arnold","Chaya Jarvis","Olga Korbut","Olga Valentinovna Korbut is a Belarusian former gymnast. Nicknamed the \"Sparrow from Minsk\", she won four gold medals and two silver medals at the Summer Olympic Games, in which she competed in 1972 and 1976 for the Soviet team, and was the inaugural inductee to the International Gymnastics Hall of Fame in 1988.","m_s_66.webp",5,2);

//Found
        this.questionCreator("Which heavyweight boxer won a gold medal in the 1964 Olympic Games?","Joe Frazier","Manuel Hernandez","Vincent Becker","Jeffery Kidd","Joe Frazier","Joseph William Frazier (January 12, 1944 – November 7, 2011), nicknamed \"Smokin' Joe\", was an American professional boxer who competed from 1965 to 1981. He reigned as the undisputed heavyweight champion from 1970 to 1973, and as an amateur won a gold medal at the 1964 Summer Olympics.","m_s_67.webp",5,2);

//Found
        this.questionCreator("Austrian-born Hermann Maier is a former world champion in which sport?","Skiing","Football","Golf","Auto Racing","Skiing","Hermann Maier (born 7 December 1972) is an Austrian former World Cup champion alpine ski racer and Olympic gold medalist.","m_s_68.webp",5,2);

//Found
        this.questionCreator("Fanny Blankers-Koen was the first woman to win how many gold medals in a single Olympic Games in 1948?","4","3","5","7","4","Fanny Blankers-Koen won four of the nine women's events at the 1948 Olympics, competing in eleven heats and finals in eight days. She was the first woman to win four Olympic gold medals, and achieved the feat in a single Olympics.","m_s_69.webp",5,2);

//Found
        this.questionCreator("Which national football team won a gold medal at the 1972 Summer Olympic Games?","Poland","New Orleans","New York","Baltimore","Poland","Poland competed at the 1972 Summer Olympics in Munich, West Germany. 290 competitors, 252 men and 38 women, took part in 150 events in 22 sports.","m_s_70.webp",5,2);

//Found
        this.questionCreator("Which British sportsman was the first 2012 Olympic Torchbearer in the UK?","Ben Ainslie","Derrick Hensley","Dominique Beard","Jonathon Rhodes","Ben Ainslie","10 May 2012 - The London 2012 Organising Committee (LOCOG) have announced that three-times Olympic gold medallist Ben Ainslie will be the first Torchbearer to carry the Olympic Flame in the UK.","m_s_71.webp",5,2);

//Found
        this.questionCreator("What is the nickname of Kwame Nkrumah-Acheampong, the first Ghanian to compete in a Winter Olympic Games, in 2010?","The Snow Leopard","The Leopard","The Lion","The Jaguar","The Snow Leopard","Kwame Nkrumah-Acheampong, nicknamed \"The Snow Leopard\", is a Ghanaian skier and is the first person from Ghana to take part in the Winter Olympics, which he did at the 2010 Winter Olympics Vancouver, British Columbia. taking part in the slalom. He finished 53rd out of 102 participants, of whom 54 finished.","m_s_72.webp",5,2);

//Found
        this.questionCreator("The Olympic Marathon is held in commemoration of which ancient Greek soldier who ran from Marathon to Athens?","Pheidippides","Achermus","Acron","Abronychus","Pheidippides","The event was instituted in commemoration of the fabled run of the Greek soldier Pheidippides, a messenger from the Battle of Marathon to Athens, who reported the victory. The marathon was one of the original modern Olympic events in 1896, though the distance did not become standardized until 1921.","m_s_73.webp",5,2);

//Found
        this.questionCreator("Who was the first gymnast to score a perfect 10 seven times in a row, at the 1976 Montreal Games?","Nadia Comaneci","Hugo Jones","Lennon Reynolds","Alyson Shannon","Nadia Comaneci","The first person to score a perfect 10 at the Olympic Games was Romanian Nadia Comăneci, at the 1976 Games in Montreal. Other women who accomplished this feat at the Olympics include Nellie Kim, also in 1976, Mary Lou Retton in 1984, and Daniela Silivaș and Yelena Shushunova in 1988.","m_s_74.webp",5,2);

//Found
        this.questionCreator("The men’s football team from which country won the gold medal at the 2008 Summer Olympics?","Argentina","Oakland","Baltimore","Minnesota","Argentina","Argentina competed at the 2008 Summer Olympics held in Beijing, China, from 8 to 24 August 2008. 137 athletes qualified for the Olympic Games in 19 sports. Emanuel Ginóbili, basketball player and gold medalist at the 2004 Summer Olympics, was the nation's flag bearer at the opening ceremony.","m_s_75.webp",5,2);

//Found
        this.questionCreator("Women competed in the 3,000 metre steeplechase for the first time in the Summer Olympics in which year?","2008","2005","2002","2012","2008","The men's 3000 metres steeplechase has been present on the Olympic athletics programme since 1920. The women's event is the most recent addition to the programme, having been added at the 2008 Olympics. It is the most prestigious steeplechase track race at elite level.","m_s_76.webp",5,2);

//Found
        this.questionCreator("How many players are there in football team?","Eleven","Twelve","Eighteen","Nine","Eleven","“A match is played by two teams, each with a maximum of eleven players; one must be the goalkeeper. A match may not start or continue if either team has fewer than seven players”","m_s_77.webp",5,2);

//Found
        this.questionCreator("In rugby league team, how many players are there?","Thirteen","Fifteen","Twenty","Ten","Thirteen","“Thirteen players. Rugby league football is a full-contact sport played by two teams of thirteen players on a rectangular field. One of the two codes of rugby, it originated in Northern England in 1895 as a split from the Rugby Football Union over the issue of payments to players”","m_s_78.webp",5,2);

//Found
        this.questionCreator("What height is the center of tennis net in feet?","Three feet","One feet","Two feet","Four feet","Three feet","“If a double net is used, then the net shall be supported, at a height of three and half feet (1.07 m), by two singles sticks, the centers of which shall be three feet outside the singles court on each side. The net posts shall not be more than six inches square or six inches diameter.","m_s_79.webp",5,2);

//Found
        this.questionCreator("Which country invented volleyball?","USA","China","Africa","Japan","USA","“Volleyball has come a long way from the dusty-old YMCA gymnasium of Holyoke, Massachusetts, USA, where visionary William G. Morgan invented the sport back in 1895.”","m_s_80.webp",5,2);

//Found
        this.questionCreator("What is Pluto platter now known as?","Frisbee","Shot put","Javelin throw","Discus throw","Frisbee","“In 1955, Walter F. Morrison’s company PIPCO manufactured and sold the PLUTO PLATTER flying disc. In 1957, Walter F. Morrison signs all rights to the PLUTO PLATTER over to Wham-O. Later in 1957, Wham-O changes the name to FRISBEE.”","m_s_81.webp",5,2);

//Found
        this.questionCreator("Which color is an archery target center?","Gold","Red","Black","Silver","Gold","“The target faces are arranged with 5 colors Gold, Red, Blue, Black and white. But gold is best for target. Yellow, referred to as “gold”, is the world Archery standard.”","m_s_82.webp",5,2);

//Found
        this.questionCreator("How many pockets does a snooker table have?","Six","Seven","Ten","Four","Six","“The playing surface, eleven feet 8.5 inches by five feet ten inches for a standard full-size table, with six pocket holes, one at each corner and one at the center of each of the longer side cushion. For further information see Billiard table, specially the section Snooker and English billiards tables.”","m_s_83.webp",5,2);

//Found
        this.questionCreator("In feet, how high is a basketball hoop?","Ten feet","Twelve feet","Eleven feet","Twenty feet","Ten feet","“The top of the hoop is ten feet above the ground. Regulation backboards are seventy two inches wide by forty two inches tall. All basketball rims (hoops) are eighteen inches in diameter.”","m_s_84.webp",5,2);

//Found
        this.questionCreator("What is the maximum time limit allowed to look for a lost ball in golf?","5 minutes","2 minutes","1 minute","10 minutes","5 minutes","“So keep your golfing woes to a minimum with our guide to losing your ball. A player has five minutes to look for a ball before it is deemed to be lost.”","m_s_85.webp",5,2);

//Found
        this.questionCreator("Which sport is ‘Mariya Abakumova’ associated with?","Javelin","Shot put","Discus throw","Hockey","Javelin","“Mariya Vasiliyevna Abakumova born fifteen January 1986) is a Russian track and field athlete who competes in the javelin throw. She won gold at the 2011 world.”","m_s_86.webp",5,2);

//Found
        this.questionCreator("What was the former name of the Oklahoma City thunder basketball team?","Seattle supersonics","Thunder bolt","The beetles","Seagull of the Sea","Seattle supersonics","“The team was originally established as the Seattle SuperSonics, an expansion team that joined the NBA for the 1967-68 season.”","m_s_87.webp",5,2);

//Found
        this.questionCreator("Who is founder of Tae Kwon Do?","General Choi Hong Hi","Jigoro Kano","Dennis Hong Hi","Vincent Kano","General Choi Hong Hi","“General Choi Hong Hi (9 November 1918-15 June 2002) was a South Korean Army general and martial artist who is a controversial figure in the history of the Korean martial art of taekwondo. Choi is regarded by many as the “founder of Taekwon-Do federation (ITF) organizations.”","m_s_88.webp",5,2);

//Found
        this.questionCreator("Who created Arnis?","Remy Presas","Malulan Ramon","Patricio Malulan","Policarpio Agustin","Remy Presas","“Remy Amador Presas (December 19, 1936 – August 28, 2001) was the founder of Modern Arnis, a popular Filipino martial art. Born in the Philippines, he moved to the united states in 1974, where he taught his art via seminars and camps.”","m_s_89.webp",5,2);

//Found
        this.questionCreator("Where was fencing invented?","France","Paris","England","Spain","France","“ The foil was invented in France as a training weapon in the middle of the 18th century to practice fast and elegant thrust fencing. “","m_s_90.webp",5,2);

//Found
        this.questionCreator("What is a golf ball made of?","Rubber","Aluminum","Wood","Cotton","Rubber","“A golf ball is made mostly of rubber and has a thin, plastic cover.”","m_s_91.webp",5,2);

//Found
        this.questionCreator("In which country was golf invented?","Scotland","Paris","America","France","Scotland","“Although its name is originally Dutch, the game of golf is believed to have been invented in Scotland.”","m_s_92.webp",5,2);

//Found
        this.questionCreator("Which sport is also known as tenpins?","Bowling","Tennis","Chess","Darts","Bowling","“Bowling is called tenpins, because the object of the game is to knock down ten pins that are arranged in a triangle.”","m_s_93.webp",5,2);

//Found
        this.questionCreator("How many players are on an America football team?","Forty five","Fifty five","Eleven","Ten","Forty five","“National Football league (NFL) teams are allowed to have to forty five players. Only eleven of them can be o the field at any one time.”","m_s_94.webp",5,2);

//Found
        this.questionCreator("How often are the Olympic Games held?","Four years","Three years","Five years","Two years","Four years","“The summer Games and the Winter Games are held once every four years. Until the early 1990s the summer and winter games were held in the same year. Today they are separated by two years.”","m_s_95.webp",5,2);

//Found
        this.questionCreator("What term applies when all batsmen have all been retired?","All out","Default","Strike","Pass out","All out","“In cricket, the term “all out” means that a side’s batsmen have all been retired.”","m_s_96.webp",5,2);

//Found
        this.questionCreator("In cricket, what is the name of the player who delivers the ball?","Bowler","Pitcher","Thrower","Shooter","Bowler","“The bowler, delivering the ball with a straight arm, tries to hit the wicket with the ball so that the bails fall. The one of several ways that the batsman is dismissed.”","m_s_97.webp",5,2);

//Found
        this.questionCreator("In what game would one find a googly?","Cricket","Hockey","Tennis","Badminton","Cricket","“A googly is a kind of delivery featured in the game of cricket.”","m_s_98.webp",5,2);

//Found
        this.questionCreator("Which sport involves a bat, a ball and wickets?","Cricket","Baseball","Softball","Badminton","Cricket","“Cricket is a bat-and-ball game played between two teams of eleven players. It is set on a cricket centered on a twenty-meter twenty two-yard) pitch with two wickets each comprising a bail balanced on three stumps”.","m_s_99.webp",5,2);

//Found
        this.questionCreator("Which country hosted 2008 Olympics?","China","India","America","Mexico","China","“The 2008 Summer Olympic Games, officially known as the Games of the XXIX Olympiad and commonly known as Beijing 2008, was an international multi-sport event that was held from 8 to 24 August 2008 in Beijing, China”","m_s_100.webp",5,2);


    }

    private void addSportsDifficult()
    {
        //checked

//Found
        this.questionCreator("British sportswoman Charlotte Cooper, born in 1870, was famous in which sport?","Tennis","Basketball","Boxing","Table Tennis","Tennis","Charlotte Cooper Sterry was a female tennis player from England who won five singles titles at the Wimbledon Championships and in 1900 became Olympic champion. In winning in Paris on July 11th 1900, she became the first female Olympic tennis champion as well as the first individual female Olympic champion.","d_s_1.webp",5,3);

//Found
        this.questionCreator("Which football team did Gordon Banks play for prior to Leicester City?","Chesterfield FC","Denver Broncos","Carolina Panthers","Washington Redskins","Chesterfield FC","He was Stoke's goalkeeper in the 1972 League Cup win – the club's only major honour. He was still Stoke and England's number one when a car crash in October 1972 cost him both the sight in one eye and his professional career. He did though play in the United States for the Fort Lauderdale Strikers in 1977 and 1978.","d_s_2.webp",5,3);

//Found
        this.questionCreator("What is the name of the short red cape, or piece of cloth, suspended from a hollow staff and brandished by a matador during a bullfight?","Muleta","Cuneta","Caneta","Eta","Muleta","A muleta is a stick with a red cloth hanging from it[1] that is used in the final third of a bullfight. It is different from the cape used by the matador earlier in the fight.","d_s_3.webp",5,3);

//Found
        this.questionCreator("In sport, what does WEPA stand for?","World Elephant Polo Association","World Eagle Polo Association","Wave Electric Polo Association","World Escape Polo Association","World Elephant Polo Association","Western Economic Partnership Agreement (Canada)","d_s_4.webp",5,3);

//Found
        this.questionCreator("How many medals did the United States win at the 2010 Winter Olympics?","37","32","23","33","37","The United States participated in the 2010 Winter Olympics in Vancouver, British Columbia, Canada. The U.S. team had a historic Winter Games, winning an unprecedented 37 medals. Team USA's medal haul, which included nine gold, marked the first time since the 1932 Lake Placid Games that the U.S. earned more medals than any other participant.","d_s_5.webp",5,3);

//Found
        this.questionCreator("Which English-born Australian darts player has the nickname ‘The Silver Surfer’?","Sean Reed","Draven Gibson","Tyree Dixon","Kelton Chandler","Sean Reed","Sean Reed (born 28 June 1965 in Sydney) is a former Australian professional darts player.","d_s_6.webp",5,3);

//Found
        this.questionCreator("How long is the football pitch at Wembley Stadium in yards?","115 yards","105 yards","110 yards","120 yards","115 yards","The pitch size, as lined for association football, is 115 yd (105 m) long by 75 yd (69 m) wide, slightly narrower than the old Wembley, as required by the UEFA stadium categories for a category four stadium, the top category.","d_s_7.webp",5,3);

//Found
        this.questionCreator("In January 1957, Russell Endean became the first batsman to be dismissed from a test cricket match for doing what?","Handling the ball","Catching the ball","Kicking the ball","Puching the ball","Handling the ball","The South African Russell Endean became the first victim of this method in international cricket when he was dismissed in a 1957 Test match against England.","d_s_8.webp",5,3);

//Found
        this.questionCreator("In the game of darts, what score is known as ‘Breakfast’ or ‘Bed and Breakfast’?","26","32","15","21","26","A score of 26, made up of a single-5, single-20, single-1 in a game of x01. This is a common score in darts because players aiming for the 20 segment (which contains the highest scoring area on the board) will often accidentally hit the 1 and the 5 segments, which are located on either side of the 20. The term comes from the typical price of a bed-and-breakfast in times gone by: 2 shillings and sixpence, or \"two and six\".","d_s_9.webp",5,3);

//Found
        this.questionCreator("In 2006, who was the first football player in World Cup history to win the ‘Best Young Player Award’?","Lukas Podolski","Nigel Rodriguez","Thalia Benjamin","William Osborne","Lukas Podolski","Germany's Podolski Named World Cup's Best Young Player. Lukas Podolski, one half of Germany's prolific striking duo, has been named as FIFA's Best Young Player of the 2006 World Cup ahead of some very impressive company.","d_s_10.webp",5,3);

//Found
        this.questionCreator("British sportswoman Anita Lonsbrough won a 1960 Olympic Gold medal in which sport?","Swimming","Tennis","Badminton","Boxing","Swimming","She would also be the last British woman to win Olympic gold in swimming until Rebecca Adlington gained the gold in the 2008 Summer Olympics, 48 years later.","d_s_11.webp",5,3);

//Found
        this.questionCreator("Roquet, Tice and Pioneer are all terms used in which game?","Croquet","Basketball","Volleyball","Fencing","Croquet","Positioning a ball where the opponent is tempted (enticed) to shoot at it, usually with great risk to the opponent , In a three-ball or four-ball break, a ball that is positioned at the wicket following the one a player is attempting to score.","d_s_12.webp",5,3);

//Found
        this.questionCreator("Who is the President of The All England Lawn Tennis and Croquet Club in Wimbledon?","The Duke of Kent","The Duke of Nigel","The Duke of William","The Duke of Spencer","The Duke of Kent","Prince Edward, The Duke of Kent, K.G., G.C.M.G., G.C.V.O., A.D.C., serves as President at The All England Lawn Tennis & Croquet Club Limited.","d_s_13.webp",5,3);

//Found
        this.questionCreator("What is the name of the iron hook which is attached to the pole used by fisherman to land a heavy fish?","Gaff","Hodge","Snyder","Wheeler","Gaff","In fishing, a gaff is a pole with a sharp hook on the end that is used to stab a large fish and then lift the fish into the boat or onto shore. Ideally, the hook is placed under the backbone. Gaffs are used when the weight of the fish exceeds the breaking point of the fishing line or the fishing pole.","d_s_14.webp",5,3);

//Found
        this.questionCreator("What is the fin called underneath the rear of a surfboard?","Skeg","Burke","Hill","Lowe","Skeg","Lift (aka \"drive\") from the board and its fin(s) is what enables all maneuvers in surfing. A \"skeg\" (an upright, streamlined, often raked keel) typically denotes one centrally-mounted stabilizer foil mounted perpendicularly to the riding surface, at the rear of the surfboard.","d_s_15.webp",5,3);

//Found
        this.questionCreator("Which Australian cricket ground is known as ‘The Gabba’?","Brisbane Cricket Ground","Amara Harrison","Jamari Brooks","Danny Spence","Brisbane Cricket Ground","The Brisbane Cricket Ground, commonly known as the Gabba, is a major sports stadium in Brisbane, the capital of Queensland, Australia.","d_s_16.webp",5,3);

//Found
        this.questionCreator("Professional surfer Jordy Smith was born in which country?","South Africa","Peru","Argentina","Czechia","South Africa","Jordan Michael \"Jordy\" Smith (born 11 February 1988) is a South African professional surfer, competing on the World Championship Tour (WCT).","d_s_17.webp",5,3);

//Found
        this.questionCreator("Garryowen, Shoeing and Mulligrubber are terms used in which sport?","Rugby Union","Auto Racing","Skiing","Wiffleball","Rugby Union","Rugby union, commonly known in most of the world as rugby, is a contact team sport which originated in England in the first half of the 19th century. One of the two codes of rugby football, it is based on running with the ball in hand.","d_s_18.webp",5,3);

//Found
        this.questionCreator("Bobby Cox was manager of which US professional baseball team before his retirement in 2010?","Atlanta Braves","Giants","Blue Jays","Pirates","Atlanta Braves","Robert Joseph Cox (born May 21, 1941) is an American former professional baseball third baseman and manager in Major League Baseball (MLB). He first led the Atlanta Braves from 1978 to 1981, and then managed the Toronto Blue Jays from 1982 to 1985. He later rejoined the Braves in 1985 as a general manager.","d_s_19.webp",5,3);

//Found
        this.questionCreator("Which two colour balls were introduced into the game of ‘Snooker Plus’ in 1959?","Purple and Orange","Green and Yellow","Orange and Blue","Purple and Green","Purple and Orange","The event was played under the Snooker Plus rules, a variant of snooker with two additional colours (orange and purple). The tournament was won by Joe Davis with Fred Davis finishing in second place. It was the eleventh and final News of the World Tournament, which ran from 1949/50 to 1959.","d_s_20.webp",5,3);

//Found
        this.questionCreator("Japanese baseball player, Ichiro Suzuki, joined which US baseball team in 2001?","Seattle Mariners","Vance Richards","Angel Dominguez","Arely Jefferson","Seattle Mariners","Born on October 22, 1973, in Kasugai, Japan, baseball player Ichiro Suzuki became a seven-time batting champion in his home country. After joining Major League Baseball's Seattle Mariners in 2001, he became the second player to win Rookie of the Year and MVP Awards in the same season.","d_s_21.webp",5,3);

//Found
        this.questionCreator("In which year was the first London to Brighton veteran car run in the UK?","1896","1825","1842","1874","1896","The London to Brighton Veteran Car Run is the longest-running motoring event in the world. The first run was in 1896, and it has taken place most years since its initial revival in 1927.","d_s_22.webp",5,3);

//Found
        this.questionCreator("Pato, a game played on horseback, is the national sport of which South American country?","Argentina","Bahrain","Curaçao","Rwanda","Argentina","Pato is a game played on horseback that combines elements from polo and basketball. It is most popular and the national sport of Argentina since 1953.","d_s_23.webp",5,3);

//Found
        this.questionCreator("The Peewee Boyz were Europe’s first all-male what?","Cheerleaders","Basketball Players","Rugby Union Players","Baseball Players","Cheerleaders","First all-male cheerleaders, the Peewee Boyz. Unmistakably lads, they have just won their first major trophy at the International Cheer Championships, where they mixed it with rivals such as Gold Star Twinkles and Pink Ladies Supreme.","d_s_24.webp",5,3);

//Found
        this.questionCreator("Which sport is nicknamed ‘Chess on Ice’?","Curling","Volleyball","Polo","Gynastics","Curling","Curling is a team sport played by two teams of four players on a rectangular sheet of ice. Its nickname, “The Roaring Game”, originates from the rumbling sound the 44-pound (19.96kg) granite stones make when they travel across the ice.","d_s_25.webp",5,3);

//Found
        this.questionCreator("Which horse came second in the 1973 Grand National at Aintree?","Crisp","Ritter","Blair","Bowers","Crisp","The race is best remembered for being the first of Red Rum's three Grand National wins; Red Rum also broke the record set by Reynoldstown in 1935, and in doing so staged a spectacular comeback to beat Crisp on the run-in after having trailed by 15 lengths at the final fence.","d_s_26.webp",5,3);

//Found
        this.questionCreator("Peter Gilchrist is a world champion in which sport?","Billiards","Snowboarding","Tennis","Lacrosse","Billiards","Peter Gilchrist (born 1968 in Middlesbrough) is an English-born Singaporean English billiards player. Gilchrist won the WPBSA World Championships in 1994, 2001 and in 2013. In 2003, Gilchrist moved to Singapore to become the national billiards and snooker coach.","d_s_27.webp",5,3);

//Found
        this.questionCreator("With which athletics field event is Jan Zelezny associated?","Javelin","Mixed Martial Arts","Baseball","Weightlifting","Javelin","Jan Železný (Czech pronunciation: [jan ˈʒɛlɛzniː] ( listen); born 16 June 1966) is a retired Czech track and field athlete who competed in the javelin throw. He was a World and Olympic Champion and holds the world record with a throw of 98.48 m.","d_s_28.webp",5,3);

//Found
        this.questionCreator("The Saporta Cup was played for in which sport?","Basketball","Weightlifting","Ultimate","Tennis","Basketball","The 1998–99 FIBA Saporta Cup was the thirty-third edition of FIBA's 2nd-tier level European-wide professional club basketball competition. It occurred between September 15, 1998, and April 13, 1999. The final was held at Zaragoza, Spain.","d_s_29.webp",5,3);

//Found
        this.questionCreator("Grampus Eight football team are based in which country?","Japan (Nagoya Grampus Eight)","Korea (Republic of Eastern Asia)","Niger","Virgin Islands (U.S.)","Japan (Nagoya Grampus Eight)","Nagoya Grampus is a Japanese association football club that plays in the J1 League, following promotion from the J2 League in 2017.","d_s_30.webp",5,3);

//Found
        this.questionCreator("Tennis player Mary Pierce was born in which country in January 1975?","Canada","Virgin Islands (U.S.)","Montenegro","Nauru","Canada","Mary Pierce was born on January 15, 1975 in Montréal, Québec, Canada.","d_s_31.webp",5,3);

//Found
        this.questionCreator("In 1965, who became the first British driver to win the Indianapolis 500?","Jim Clark","Ayden Hicks","Van Pena","Bernard Garner","Jim Clark","1965: Jim Clark is the first former World Drivers' Champion to win the race, the first driver to win the race en route to winning the Formula 1 World Championship, and the first Scottish victor.","d_s_32.webp",5,3);

//Found
        this.questionCreator("In which country was athlete Eric Liddell born?","China","Bhutan","Uruguay","Mongolia","China","Eric Henry Liddell was a Scottish Olympic Gold Medalist runner, rugby union international player, and Christian missionary. Liddell was born in China to Scottish missionary parents.","d_s_33.webp",5,3);

//Found
        this.questionCreator("Which European country hosted the 1956 Winter Olympic Games?","Italy","Greece","Paraguay","Croatia","Italy","The 1956 Winter Olympics, officially known as the VII Olympic Winter Games, was a winter multi-sport event celebrated in Cortina d'Ampezzo, Italy from 26 January to 5 February 1956.","d_s_34.webp",5,3);

//Found
        this.questionCreator("What colour is Trap 1 in greyhound racing in the UK?","Red (with white number)","Blue (with White numeral)","White (with Black numeral)","Orange (with Black numeral)","Red (with white number)","Greyhound racing in Britain has a standard colour scheme. The starting traps (equipment that the greyhound starts a race in) determines the colour. There are currently no tracks that feature eight greyhound races. Trap 1 = Red with White numeral","d_s_35.webp",5,3);

//Found
        this.questionCreator("Budo is a Japanese term that describes what?","Martial arts","Dance","Sing","Sleep","Martial arts","Budō is a Japanese term describing modern Japanese martial arts. Literally translated it means the \"Martial Way\", and may be thought of as the \"Way of War\".","d_s_36.webp",5,3);

//Found
        this.questionCreator("Which, now demolished, Cincinnati sports stadium was re-named Cinergy Field in 1996?","Riverfront Stadium","Ohio Status","Kyle Field","Beaver Stadium","Riverfront Stadium","Riverfront Stadium, also known as Cinergy Field from 1996 to 2002, was a multi-purpose stadium in Cincinnati, Ohio, United States that was the home of the Cincinnati Reds of Major League Baseball from 1970 through 2002 and the Cincinnati Bengals of the National Football League from 1970 to 1999.","d_s_37.webp",5,3);

//Found
        this.questionCreator("How many feathers, traditionally, form the cone of a badminton shuttlecock?","16","13","18","15","16","The feathered shuttlecocks used in badminton players are made up of 16 feathers either from the left or right wing of a goose or duck attached to a 'semi-ellipse' shaped cork","d_s_38.webp",5,3);

//Found
        this.questionCreator("In 1938, which racehorse beat War Admiral by four lengths in their famous match race at Pimlico Race Course in Baltimore, Maryland, USA?","Seabiscuit","Hicks","Garner","Klein","Seabiscuit","Seabiscuit (May 23, 1933 – May 17, 1947) was a champion thoroughbred racehorse in the United States, who became the top money winning racehorse up to the 1940s, as noted in films and books. He beat the 1937 Triple-Crown winner, War Admiral, by 4 lengths in a 2-horse special at Pimlico, and was voted American Horse of the Year for 1938.","d_s_39.webp",5,3);

//Found
        this.questionCreator("The ‘Duckworth-Lewis method’ is used in which sport?","Cricket","Baseball","Cheerleading","Boxing","Cricket","The Duckworth–Lewis (D/L) method is a mathematical formulation designed to calculate the target score for the team batting second in a limited overs cricket match interrupted by weather or other circumstances. It is generally accepted to be the most accurate method of setting a target score.","d_s_40.webp",5,3);

//Found
        this.questionCreator("Which two American 400 metre runners were banned for life from the Olympics after being disrespectful when collecting their medals at the 1972 Summer Olympics?","Vincent & Wayne","Ayden & Hicks","Van & Pena","Bernard & Klein","Vincent & Wayne","Wayne Collett and Vince Matthews were actually standing on the winners podium at the 1972 Munich Olympics, their medals for their silver and gold medal finishes in the 400 meter sprint around their necks, and the American national anthem playing. Avery Brundage, the president of the International Olympic Committee, viewed the behavior of Collett and Matthews as abhorrent and immediately banned them from the Olympic Games.","d_s_41.webp",5,3);

//Found
        this.questionCreator("How many points did Wilt Chamberlain score in his best game versus New York?","100","200","150","110","100","Wilt Chamberlain set the single-game scoring record in the National Basketball Association (NBA) by scoring 100 points for the Philadelphia Warriors in a 169–147 win over the New York Knicks on March 2, 1962, at Hershey Sports Arena in Hershey, Pennsylvania.","d_s_42.webp",5,3);

//Found
        this.questionCreator("He is the youngest NBA player to score 10,000 points.","Kobe Bryant","Bridget Merritt","Aracely Gonzales","Deja Ward","Kobe Bryant","At 24 years and 193 days, Los Angeles Lakers' guard Kobe Bryant became the youngest player to score 10,000 points in a 97-95 victory over the Indiana Pacers at the Staples Center in L.A. Averaging 30 points per game, Bryant entered the game needing a mere 14 points to reach the milestone.","d_s_43.webp",5,3);

//Found
        this.questionCreator("He is the shortest player ever to win the Slam Dunk Contest.","Spud Webb","Ricardo Day","Kymani Villa","Rhys Bright","Spud Webb","Anthony Jerome \"Spud\" Webb (born July 13, 1963) is an American retired professional basketball point guard. Webb, who played in the National Basketball Association (NBA), is known for winning a Slam Dunk Contest despite being one of the shortest players in NBA history.","d_s_44.webp",5,3);

//Found
        this.questionCreator("He retired with the NBA's highest scoring average (30.1 points per game).","Michael Jordan","Rayna Jimenez","Kassidy Hubbard","Cameron Waller","Michael Jordan","He holds the NBA records for highest career regular season scoring average (30.12 points per game) and highest career playoff scoring average (33.45 points per game).","d_s_45.webp",5,3);

//Found
        this.questionCreator("Karl Malone received the award Most Valuable Player in 1996-97. What team did he play for?","Utah Jazz","Denver Nuggests","Atlanta Hawks","Chicago Bulls","Utah Jazz","He played his final season with the Los Angeles Lakers, with whom he played his third Finals in 2004. Malone has the most career postseason losses of any NBA player ever, with 95. Malone also competed with the United States national team in the Summer Olympic Games of 1992 and 1996; in both years he won gold medals.","d_s_46.webp",5,3);

//Found
        this.questionCreator("He is the NBA's All-Time scorer.","Kareem Abdul-Jabbar","Konnor Maynard","Jermaine Black","Darion Huffman","Kareem Abdul-Jabbar","Kareem Abdul-Jabbar has scored the most career points in NBA history.","d_s_47.webp",5,3);

//Found
        this.questionCreator("Wilt Chamberlain is a great player indeed. In a game against the Celtics he made the best rebounding effort ever. How many rebounds did he make in this famous game?","55","34","63","20","55","His career average of 22.9 rebounds per game is still the most by any player in NBA history","d_s_48.webp",5,3);

//Found
        this.questionCreator("Who is the first player to top 20,000 points in NBA?","Bob Pettit","Shannon York","Makenna Boyle","Norah Pruitt","Bob Pettit","Kevin Durant becomes second-youngest player in NBA history to reach 20,000 career points.","d_s_49.webp",5,3);

//Found
        this.questionCreator("George Muresan and Manute Bol are the tallest players in NBA history. How tall are they?","7\'7 (2.31m)","7\'3 (2.15m)","7\'4 (1.30m)","7\'5 (1.21m)","7\'7 (2.31m)","At 7-7, Manute Bol and Gheorghe Muresan are the tallest players in NBA history. At 5-3, Muggsy Bogues is the shortest.","d_s_50.webp",5,3);

//Found
        this.questionCreator("Who is the shortest player in the history of NBA?","Tyronne Bogues","Kaleb Lawrence","Avery Burton","Adyson Stone","Tyronne Bogues","At 5 feet, 3 inches, Tyrone Bogues, better known as Muggsy Bogues, holds the record as the shortest player in NBA history. He was drafted by the Washington Bullets in 1987, but he's best-known for playing with the Charlotte Hornets alongside Alonzo Mourning and Larry Johnson.","d_s_51.webp",5,3);

//Found
        this.questionCreator("How many players are there in an ice hockey team?","Six","Ten","Twenty","Five","Six","Hockey is played with six (6) players on the ice for each team; five (5) skaters, one (1) goaltender. The typical roster size of a hockey team is twenty (20) players; twelve (12) forwards, 6 defensemen, and two (2) goaltenders","d_s_52.webp",5,3);

//Found
        this.questionCreator("In darts, how high off the floor must the bulls eye measure?","5'8","6'1","4'3","10'1","5'8","Standard height from the floor to the bull seye on the dartboard is 5 feet 8 inches, while the oche (distance between the front of the board and the toe line) should measure 7 feet 9.25inches.","d_s_53.webp",5,3);

//Found
        this.questionCreator("How many red balls are used in a game of snooker?","Fifteen","Twenty","Ten","Five","Fifteen","“It is played using a cue and snooker balls: one white cue ball, fifteen red balls worth one point each (sometimes played with fewer red balls, commonly6 or 10), and six balls of different colors: yellow (2 points), green (3), brown (4), blue (5), pink (6), black (7)","d_s_54.webp",5,3);

//Found
        this.questionCreator("What is the highest possible score with three darts?","One hundred eighty","Two hundred","Twenty","Thirty","One hundred eighty","“The highest score possible with three darts is one hundred eighty, commonly known as a “ton eighty” (one hundred is called a ton), obtained when all three darts land in the triple 20. In televised game, the referee frequently announces a score of one hundred eighty exuberant style”","d_s_55.webp",5,3);

//Found
        this.questionCreator("Which sport is associated with Constantino Rocca?","Golf","Dart","Hockey","Cheese","Golf","“Constantino Rocca is a former player. He was long known as the most successful male golfer that Italy has produced, until the 2018 success of Francesco Molinari, who credited Rocca as an inspiration to him following his open victory”","d_s_56.webp",5,3);

//Found
        this.questionCreator("What is highest number on a roulette wheel?","Thirty six","Ten","Forty","Twenty","Thirty six","“The pockets of the roulette wheel are numbered from zero to thirty six. In number ranges from one to ten and nineteen to twenty-eight, odd numbers are red and even are black. In ranges from eleven to eighteen and twenty nine to thirty six, odd numbers are black and even are red. There is a green pocket numbered zero”","d_s_57.webp",5,3);

//Found
        this.questionCreator("Where was the FIFA world cup held in 1986?","Mexico","Canada","Africa","America","Mexico","“The 1986 FIFA world cup, the 13th FIFA World Cup, was held in Mexico from 31 May to 29 June 1986","d_s_58.webp",5,3);

//Found
        this.questionCreator("In darts, how many points do you get if you hit a bulls eye?","Fifty","One hundred","Ten","Sixty","Fifty","“An inner bullseye (sometimes referred to as a “double bullseye” in amateur play) is a smaller, inner circle and counts for fifty points while an outer bull is worth twenty five points. Two treble 20s when combined with an inner bullseye is worth 170 points in darts which is the highest possible checkout.”","d_s_59.webp",5,3);

//Found
        this.questionCreator("In inches, how big is the diameter of a basketball hoop?","Eighteen inches","Twenty inches","Nine inches","Eleven inches","Eighteen inches","“Regulation backgrounds are seventy two inches wide by forty two inches tall. All basketball rims (hoops) are eighteen inches in diameter. The inner rectangle on the backboard is twenty four inches wide by eighteen inches tall.”","d_s_60.webp",5,3);

//Found
        this.questionCreator("What’s the second event on day one of a men’s decathlon?","Long jump","Tennis","Basketball","Volleyball","Long jump","“Competitors earn points for their performance in each discipline and the overall winner is the man who accrues the most points. The first day consists of (in order): One hundred meter, long jump, shot put, high jump, and four hundred. The second day’s events are one hundred ten meter hurdles, discus, pole vault, javelin and one thousand five hundred.”","d_s_61.webp",5,3);

//Found
        this.questionCreator("Britain’s Desmond Douglas was world number seven in which sport during the 1980s?","Table tennis","Long jump","Darts","Chess","Table tennis","“Douglas was eleven times English table tennis champion, who peaked at equal world No. seven and the European No. three.","d_s_62.webp",5,3);

//Found
        this.questionCreator("In which year was the first Wimbledon tournament held?","1877","1899","1874","1888","1877","“The 1877 Wimbledon Championship was a men’s tennis tournament held at the All England croquet and lawn tennis club (AEC & LTC) in Wimbledon, London. It was the world’s first official lawn tennis tournament, and was later recognized as the first Grand Slam tournament or “Major”. “","d_s_63.webp",5,3);

//Found
        this.questionCreator("Which sport is played on the biggest pitch in terms of area?","Polo","Hockey","Golf","Football","Polo","“The sport with largest pitch would be polo of the horse variety. If we exclude horses then cricket has large ovals and Australian football is played on large cricket grounds or even larger Australian Football fields. Some sports don’t have standardized fields.”","d_s_64.webp",5,3);

//Found
        this.questionCreator("What type of wood is a cricket bat traditionally made from?","Willow","Acacia","Narra","Mahogany","Willow","“The bat is traditionally made from willow wood, specifically from a variety of white willow called cricket bat willow (Salix Alba var. caerulea), treated with raw (unboiled) linseed oil, which has a protective function.”","d_s_65.webp",5,3);

//Found
        this.questionCreator("Which sport is Sarah Hammer associated with?","Cycling","Snow boarding","Hockey","Race","Cycling","“Sarah Kathryn Hammer (born August 18, 1983) is a former American professional racing cyclist and four-time Olympic silver medalist.”","d_s_66.webp",5,3);

//Found
        this.questionCreator("Which month is the Australian open tennis tournament held?","January","February","September","June","January","“The Australian open is a tennis tournament held annually over the last fortnight of January in Melbourne, Australia.","d_s_67.webp",5,3);

//Found
        this.questionCreator("How many players are there in polo team?","Four","Ten","Five","Eight","Four","“Each team consists of four mounted players, which can be mixed teams of both men and women.”","d_s_68.webp",5,3);

//Found
        this.questionCreator("When did Taekwondo begin?","1945","1899","1955","1995","1945","“In 1945 Korea was liberated. In the last few years before liberation, there were many different variations of subak/Taek Kyon in Korea. This was due to all of the other martial arts influence on it. The first Taekwondo School (Kwan) was started in Yong Chun, Seoul, Korea in 1945.”","d_s_69.webp",5,3);

//Found
        this.questionCreator("Who made judo?","Jigoro Kano","Remy Presas","Christopher Vistal","Crisogono Vistal","Jigoro Kano","“He took jujitsu and adapted it to the times. His new methodology was called Judo. In 1882, Dr. Jigoro Kano (The father of judo) made a comprehensive study of these ancient self-defense forms and integrated the best of these forms into a sport which is known as Kodokan Judo.”","d_s_70.webp",5,3);

//Found
        this.questionCreator("When did Arnis become national sports?","December 11. 2009","January 26, 2000","November 5, 1941","June 2, 1936","December 11. 2009","“Arnis was declared as the Philippine National Martial Art and Sport on December 11, 2009 through Republic Act 9850 signed by Pres. Gloria Macapagal - Arroyo .”","d_s_71.webp",5,3);

//Found
        this.questionCreator("What are arnis sticks made of?","Indonesian Rattan","Filipino Rattan","Philippine Narra","Acacia","Indonesian Rattan","“All Kali/Arnis, Eskrima sticks are made of hard Indonesian Rattan which is considerably harder than Filipino Rattan.”","d_s_72.webp",5,3);

//Found
        this.questionCreator("Who is the creator of gymnasium","Friedrich Ludwig Jahn","Patricio Malulan Jr.","Patricio Malulan Sr.","Policarpio Agustin","Friedrich Ludwig Jahn","“Friedrich Ludwig Jahn (August 11, 1778 - October 15, 1852) was a German gymnastics educator and nationalist his admirers know him as Turnvater Jahn, roughly meaning “father of gymnastics” Jahn.”","d_s_73.webp",5,3);

//Found
        this.questionCreator("Where did Chess originate?","Indian","France","Spain","Japan","Indian","“Many countries claim to have invented the chess game in some incipient form. The most commonly held belief is that chess originated in India, where it was called Chaturanga, which appears to have been invented in the 6th century AD.”","d_s_74.webp",5,3);

//Found
        this.questionCreator("How many holes are there in a full round of golf?","Eighteen","Nine","Five","Twenty","Eighteen","“A traditional golf course is eighteen holes, but nine-hole courses are common and can be played twice through for a full round of eighteen holes.","d_s_75.webp",5,3);

//Found
        this.questionCreator("In what sport would one find an Albion round?","Archery","Darts","Javelin throw","Shot pot","Archery","“In women’s archery, an Albion round involves firing 36 arrows at targets at each of three distances.”","d_s_76.webp",5,3);

//Found
        this.questionCreator("In Polo, what is a period of play called?","Chukka","Inning","Game","On","Chukka","“In polo, periods of play are called chukkas. Most often there are six chukkas to a game, but sometimes there are four or eight.”","d_s_77.webp",5,3);

//Found
        this.questionCreator("What variation might one play in tennis?","Australian doubles","American doubles","African doubles","China doubles","Australian doubles","“In Australian doubles tennis, a teammate can play on the same side of the court as the server. In standard tennis, this is forbidden.”","d_s_78.webp",5,3);

//Found
        this.questionCreator("In tennis, what follows a deuce?","Advantage","Forwarded","Tie","Advance","Advantage","“Advantage, in tennis, is the point scored immediately after deuce. If the player scoring it also scores the next point, he or she is the winner of the game.”","d_s_79.webp",5,3);

//Found
        this.questionCreator("How many points is required for a win in association croquet?","Twenty six","Fifty","Thirty five","Fourteen","Twenty six","“Association croquet is a game between sides of one or two players each. The first to earn twenty six points is the winner.”","d_s_80.webp",5,3);

//Found
        this.questionCreator("How many years old are horses that run the Kentucky Derby?","Three","Ten","Four","Six","Three","“The Kentucky Derby is limited to three-year-old horses. Some of the most famous horses in racing history have run in the race.”","d_s_81.webp",5,3);

//Found
        this.questionCreator("What is the maximum number of clubs that can be used in tournament golf?","Fourteen","Twelve","Ten","Twenty","Fourteen","“No more than fourteen clubs may be used in tournament play.”","d_s_82.webp",5,3);

//Found
        this.questionCreator("How long must a billiard cue be?","Ninety centimeters","Eighty centimeters","Seventy centimeters","Sixty centimeters","Ninety centimeters","“A billiard cue must be at least thirty five inches (ninety centimeters) long. In regulation play, there is no maximum length.”","d_s_83.webp",5,3);

//Found
        this.questionCreator("What is the president of a woman’s archery tournament called?","Lady paramount","Mocking jay","Woman archer","Archeries","Lady paramount","“A lady paramount presides over a woman’s archery tournament. Sometimes the term is also used for a sponsor of an amateur match.”","d_s_84.webp",5,3);

//Found
        this.questionCreator("What was the first sport to have a world championship?","Billiards","Athlete","Tennis","Basketball","Billiards","“The first world championships event for any sport was for billiards. It was held in 1873.”","d_s_85.webp",5,3);

//Found
        this.questionCreator("In lawn bowling, what is the target ball called?","Jack","Jockey","Point","Target","Jack","“In lawn bowling, players roll bowls over a grass surface toward a small white ball called a jack.”","d_s_86.webp",5,3);

//Found
        this.questionCreator("What is the name of the prize awarded the National Hockey League champions?","The Stanley Cup","The Mayor's Cup","The Janna Cup","The Aisk Cup","The Stanley Cup","“Lord Stanley awarded the first prize named for him 1893 to Canada’s championship hockey team. The Stanley Cup was made international in 1914.”","d_s_87.webp",5,3);

//Found
        this.questionCreator("What is the object that hockey players send across the ice called?","Puck","Pack","Puckey","Pecker","Puck","“Hockey players use a rubber disc called a puck. Getting it into the opposing team’s goal earns a point.”","d_s_88.webp",5,3);

//Found
        this.questionCreator("What sport was the first to be made an event in the Winter Olympic Games?","Figure Skating","Hockey","Skiing","Snow boarding","Figure Skating","“Figure skating was named an official sport of the winter Olympics in 1908.”","d_s_89.webp",5,3);

//Found
        this.questionCreator("In what sport would one find a loppet?","Skiing","Skating","Hockey","Snow boarding","Skiing","“A loppet is a cross-country ski run.”","d_s_90.webp",5,3);

//Found
        this.questionCreator("What country hosted the 2006 FIFA World cup?","Germany","Australia","Mexico","America","Germany","“Germany hosted the 2006 FIFA World Cup football competition. The winner was Italy.”","d_s_91.webp",5,3);

//Found
        this.questionCreator("When was the first World Cup played?","1930","1940","1890","2000","1930","“In 1904 the Fédération International de Football Association (FIFA) was formed to regulate the rules of soccer. In 1930, FIFA organized the first world cup.”","d_s_92.webp",5,3);

//Found
        this.questionCreator("In what country does Mouloudia Club d’oran play?","Algeria","Afghanistan","Africa","America","Algeria","“Mouloudia Club d’oran is a well-known football team. Its headquarters are in the Algeria port city of Oran.”","d_s_93.webp",5,3);

//Found
        this.questionCreator("When was the first Super Bowl played?","1967","1980","1970","1950","1967","“The Super Bowl, the professional American football championship game, was first held in 1967. It has been played every year since then.”","d_s_94.webp",5,3);

//Found
        this.questionCreator("What is the date of the first known Olympic Games?","776 BCE","600 BCE","770 BCE","800 BCE","776 BCE","“The first Olympic Games, for which three are written records, took place in Greece in 776 BCE. Milon of Croton was a wrestler who six championships.”","d_s_95.webp",5,3);

//Found
        this.questionCreator("What year did the modern Olympics start?","1896 CE","1999 CE","1890 CE","1870 CE","1896 CE","“The First modern Olympic Games were held in 1896 in Athens, Greece.”","d_s_96.webp",5,3);

//Found
        this.questionCreator("In what year was the first World Cup of cricket held?","1975","1967","1980","1990","1975","“In 1975 the first World Cup of cricket was played in England. The event was a great success and continued at four-year intervals.”","d_s_97.webp",5,3);

//Found
        this.questionCreator("In cricket, how many throws make an over?","Six","Three","Four","Five","Six","“When the bowler has thrown the ball six times, an “over” is completed.”","d_s_98.webp",5,3);

//Found
        this.questionCreator("In what context might one see a matador?","Bullfight","Judo","Karate","Taekwondo","Bullfight","“The matador is the central human figure in a bullfight. Bullfighting was once popular in Spain and other Hispanic nations, but is less popular today.”","d_s_99.webp",5,3);

//Found
        this.questionCreator("In what city were the Asian Games first held?","Delhi","Rio","Jamaica","Mexico","Delhi","“The first Asian Games competition was held in Delhi, India, in 1951. The city also hosted the games of 1982.”","d_s_100.webp",5,3);

//Found
        this.questionCreator("Which country has Lacrosse as its national summer sport?","Canada","America","France","Australia","Canada","“It was declared a national summer sport by an act of parliament in 1994. Lacrosse was an Olympic event in 1904 and 1908 (a men's competition, both gold were won by Canada).”","d_s_101.webp",5,3);

//Found
        this.questionCreator("Who invented basketball?","James Naismith","Bladder Buddy","William G. Morgan","George Hancock","James Naismith","“James Naismith was a Canadian-American physical educator, physician, chaplain, sports coach and innovator. He invented the game of basketball at age 30 in 1891”","d_s_102.webp",5,3);

//Found
        this.questionCreator("Who were the runners-up in the 1966 World Cup?","West Germany","Africa","Australia","China","West Germany","The 1966 FIFA World Cup was the eighth FIFA World Cup and was held in England from 11 to 30 July 1966. England beat West Germany 4–2 in the final, winning the Jules Rimet Trophy.","d_s_103.webp",5,3);

//Found
        this.questionCreator("In which city the San Siro Stadium is located in Italy?","Milan","Jamaica","Rio","India","Milan","“San Siro. The Giuseppe Meazza commonly known as San Siro, is a football stadium in the San Siro district of Milan, Italy, which is the home of A.C. Milan and Inter”","d_s_104.webp",5,3);

//Found
        this.questionCreator("When did women begin competing in the Olympics?","1900","1989","1980","1889","1900","“Even in the early years of the modern Olympics, women were not well represented (consequently a rival Women's Olympics was held). Women participated for the first time at the 1900 Paris Games with the inclusion of women's events in lawn tennis and golf. Women's athletics and gymnastics debuted at the 1928 Olympics.”","d_s_105.webp",5,3);


    }

    private void addTechnologyEasy()
    {
        //checked
//Found
        this.questionCreator("Which of this website is a social media.","Facebook","Amazon","Lazada","Shopee","Facebook","Facebook, Inc. is an American online social media and social networking service company based in Menlo Park, California. Its website was launched on February 4, 2004, by Mark Zuckerberg, along with fellow Harvard College students and roommates Eduardo Saverin, Andrew McCollum, Dustin Moskovitz and Chris Hughes.","e_t_1.webp",7,1);
//Found
        this.questionCreator("OS computer abbreviation usually means ?","Operating System","Order of Significance","Open Software","Optical Sensor","Operating System","the software that supports a computer's basic functions, such as scheduling tasks, executing applications, and controlling peripherals.","e_t_2.webp",7,1);
//Found
        this.questionCreator("MOV extension refers usually to what kind of file?","Animation/movie file","Image file","Audio file","MS Office document","Animation/movie file","MOV file is a common multimedia container file format developed by Apple and compatible with both Macintosh and Windows platforms. It may contain multiple tracks that store different types of media data and is often used for saving movies and other video files. MOV files commonly use the MPEG-4 codec for compression.","e_t_3.webp",7,1);
//Found
        this.questionCreator("Most modern TV's draw power even if turned off. The circuit the power is used in does what function?","Remote control","Sound","Color balance","High voltage","Remote control","Some authorities are recommending TV's, VCR's and Stereo's be connected to power strips with switches and turned off when not in use to save energy. Your remote will not work until power is switched back on.","e_t_4.webp",7,1);
//Found
        this.questionCreator("DB computer abbreviation usually means ?","Database","Double Byte","Data Block","Driver Boot","Database","A structured set of data held in a computer, especially one that is accessible in various ways.","e_t_5.webp",7,1);
//Found
        this.questionCreator("TMP extension refers usually to what kind of file?","Temporary file","Compressed Archive file","Image file","Audio file","Temporary file","A TMP file is a temporary file created automatically by a software program that usually serves as a backup or cache file. It is sometimes created as an invisible file and is often deleted when the program is closed. TMP files may also be called temp files.","e_t_6.webp",7,1);
//Found
        this.questionCreator("JPG extension refers usually to what kind of file?","Image file","System file","Animation/movie file","MS Encarta document","Image file","The JPEG file extension is used interchangeably with JPG. JPEG stands for Joint Photographic Experts Group who created the standard. JPG files have 2 sub-formats, JPG/Exif (often used in digital cameras and photographic equipment), and JPG/JFIF (often used on the World Wide Web).","e_t_7.webp",7,1);
//Found
        this.questionCreator("TXT extension refers usually to what kind of file?","Text File","Image file","Audio file","Adobe Acrobat file","Text File","TXT is a file extension for a text file, used by a variety of text editors. Text is a human-readable sequence of characters and the words they form that can be encoded into computer-readable formats.","e_t_8.webp",7,1);
//Found
        this.questionCreator("CD computer abbreviation usually means ?","Compact Disc","Command Description","Change Data","Copy Density","Compact Disc","The Meaning of CD. CD means \"Compact Disc\" or \"Cross Dresser\" So now you know - CD means \"Compact Disc\" or \"Cross Dresser\" ","e_t_9.webp",7,1);
//Found
        this.questionCreator("BAK extension refers usually to what kind of file?","Backup file","Audio file","Animation/movie file","MS Encarta document","Backup file","BAK is a file extension for a file format typically used when creating a backup copy of a file. Normally, a BAK file is created as an automatic backup when an application is editing or making changes to the file in question.","e_t_10.webp",7,1);
//Found
        this.questionCreator("BAT extension refers usually to what kind of file?","Script File","Compressed Archive file","System file","Audio file","Script File","When a batch file is run, the shell program (usually COMMAND.COM or cmd.exe) reads the file and executes its commands, normally line-by-line. Unix-like operating systems, such as Linux, have a similar, but more flexible, type of file called a shell script. The filename extension .bat is used in DOS and Windows.","e_t_11.webp",7,1);
//Found
        this.questionCreator("Which of these is not a form of broadband?","Dial-up","Cable","Fibre","ADSL","Dial-up","Dial-up Internet access is a form of Internet access that uses the facilities of the public switched telephone network to establish a connection to an Internet service provider by dialing a telephone number on a conventional telephone line.","e_t_12.webp",7,1);
//Found
        this.questionCreator("What is help in computer?","Helps the user","Destroy OS","Increase Speed","All of the above","Helps the user","A help system (sometimes called a help file ) is a documentation component of a software program that explains the features of the program and helps the user understand its capabilities.","e_t_13.webp",7,1);
//Found
        this.questionCreator("RAM is usually sold in multiples of how many megabytes?","16","10","14","12","16","RAM is usually sold in multiples of 16 megabytes: 16, 32, 64, 128, 256, 512, 1024 (which is the same as 1GB).","e_t_14.webp",7,1);
//Found
        this.questionCreator("What is next key after F1 in computer keyboard?","F2","F3","F4","F5","F2","The function keys or F-keys on a computer keyboard, labeled F1 through F12, are keys that have a special function defined by the operating system, or by a currently running program. They may be combined with the Alt or Ctrl keys.","e_t_15.webp",7,1);
//Found
        this.questionCreator("After an online auction, payment can be made by.","Debit card","Cash","Cheque","Loan","Debit card","a card issued by a bank allowing the holder to transfer money electronically to another bank account when making a purchase.","e_t_16.webp",7,1);
//Found
        this.questionCreator("Online shopping is also referred to as?","Ecommerce","E-shopping","E-marketing","E-learning","Ecommerce","Ecommerce, also known as electronic commerce or internet commerce, refers to the buying and selling of goods or services using the internet, and the transfer of money and data to execute these transactions.","e_t_17.webp",7,1);
//Found
        this.questionCreator("Online auctions can also be paid by.","Paypal","Paymate","Paypen","Paycheck","Paypal","PayPal Holdings, Inc. is an American company operating a worldwide online payments system that supports online money transfers and serves as an electronic alternative to traditional paper methods like cheques and money orders.","e_t_18.webp",7,1);
//Found
        this.questionCreator("What is SaaS ?","Software as a service","Software as an application","Software as a system","Special Air Ambulance Service","Software as a service","Software as a service is a software licensing and delivery model in which software is licensed on a subscription basis and is centrally hosted. It is sometimes referred to as \"on-demand software\", and was formerly referred to as \"software plus services\" by Microsoft","e_t_19.webp",7,1);
//Found
        this.questionCreator("What does DNS mean ?","Domain Name Server","Dynamic Network System","Domain Name System","Dynamic Name Server","Domain Name Server","Domain Name Servers (DNS) are the Internet's equivalent of a phone book. They maintain a directory of domain names and translate them to Internet Protocol (IP) addresses. This is necessary because, although domain names are easy for people to remember, computers or machines, access websites based on IP addresses.","e_t_20.webp",7,1);
//Found
        this.questionCreator("What is a NAP ?","Network Access Point","Netcom Access Point","Network Accreditation Party","Network Access Protocol","Network Access Point","A Network Access Point (NAP) was a public network exchange facility where Internet service providers (ISPs) connected with one another in peering arrangements.","e_t_21.webp",7,1);
//Found
        this.questionCreator("What does SSL stand for ?","Secure Sockets Layer","Software System Layer","Secure System Layer","Software System Links","Secure Sockets Layer","SSL stands for Secure Sockets Layer. It is a protocol which creates a secure connection between a client and the server over which to send information. SSL works by using a cryptographic system that uses two keys to encrypt data.","e_t_22.webp",7,1);
//Found
        this.questionCreator("What does FTP mean ?","File Transfer Protocol","Format Transfer Protocol","Format Trace Protocol","File Trace Protocal","File Transfer Protocol","The File Transfer Protocol (FTP) is a standard network protocol used for the transfer of computer files between a client and server on a computer network. FTP is built on a client-server model architecture using separate control and data connections between the client and the server.","e_t_23.webp",7,1);
//Found
        this.questionCreator("What is TCP ?","Transmission Control Protocol","Transmission Condition Protocol","Transport Condition Protocol","Transport Control Protocol","Transmission Control Protocol","TCP (Transmission Control Protocol) is a standard that defines how to establish and maintain a network conversation via which application programs can exchange data. TCP works with the Internet Protocol (IP), which defines how computers send packets of data to each other.","e_t_24.webp",7,1);
//Found
        this.questionCreator("What is a WAN ?","Wide Area Network","Wide Allocated Network","Write Area Network","Write Abled Network","Wide Area Network","A wide area network is a telecommunications network or computer network that extends over a large geographical distance/place. Wide area networks are often established with leased telecommunication circuits.","e_t_25.webp",7,1);
//Found
        this.questionCreator("What is CSS ?","Cascading Style Sheets","Control Style Sheets","Control Sheet Style","Cascading Sheet Style","Cascading Style Sheets","Cascading Style Sheets is a style sheet language used for describing the presentation of a document written in a markup language like HTML. CSS is a cornerstone technology of the World Wide Web, alongside HTML and JavaScript","e_t_26.webp",7,1);
//Found
        this.questionCreator("What is the next key after I in computer keyboard?","Key O","Key A","Key D","Key F","Key O","Did you knwo that the inventor of the first keyboard is Christopher Latham Sholes.","e_t_27.webp",7,1);
//Found
        this.questionCreator("Create and change documents, letters, reports, and memos","Word processor","Database programs","Graphics software","Video software","Word processor","A program or machine for storing, manipulating, and formatting text entered from a keyboard and providing a printout.","e_t_28.webp",7,1);
//Found
        this.questionCreator("Software bought to do common jobs like play a game, allow people to speak to each other.","Application software","Web browser software","Word processor","Database programs","Application software","An application software (app or application for short) is computer software designed to perform a group of coordinated functions, tasks, or activities for the benefit of the user.","e_t_29.webp",7,1);
//Found
        this.questionCreator("To detect, identify, and remove computer viruses and malware","Anti-virus software","Communication software","Educational software","Web page authoring program","Anti-virus software","Antivirus software, or anti-virus software (abbreviated to AV software), also known as anti-malware, is a computer program used to prevent, detect, and remove malware. Antivirus software was originally developed to detect and remove computer viruses, hence the name.","e_t_30.webp",7,1);
//Found
        this.questionCreator("To get in touch with other people","Communication software","Entertainment software","Accounting software","Anti-virus software","Communication software","Communication software is used to provide remote access to systems and exchange files and messages in text, audio and/or video formats between different computers or users.","e_t_31.webp",7,1);
//Found
        this.questionCreator("Used for drawing and coloring","Graphics software","Spreadsheets programs","Video software","Presentation software","Graphics software","In computer graphics, graphics software refers to a program or collection of programs that enable a person to manipulate images or models visually on a computer. Computer graphics can be classified into distinct categories: raster graphics and vector graphics, with further 2D and 3d variants.","e_t_32.webp",7,1);
//Found
        this.questionCreator("To browse the world wide web like Microsoft Internet explorer","Web browser software","Computer aided design (cad)","Presentation software","Word processor","Web browser software","A web browser (commonly referred to as a browser) is a software application for accessing information on the World Wide Web. Each individual web page, image, and video is identified by a distinct URL, enabling browsers to retrieve and display them on the user's device.","e_t_33.webp",7,1);
//Found
        this.questionCreator("Row and columns of data organized and calculations are performed","Spreadsheets programs","Computer aided design (cad)","Web page authoring program","Web browser software","Spreadsheets programs","A spreadsheet is an interactive computer application for organization, analysis and storage of data in tabular form. Spreadsheets are developed as computerized simulations of paper accounting worksheets. The program operates on data entered in cells of a table.","e_t_34.webp",7,1);
//Found
        this.questionCreator("Play interactive games on personal computer","Entertainment software","Communication software","Educational software","Accounting software","Entertainment software","The Entertainment Software Association is the trade association of the video game industry in the United States. It was formed in April 1994 as the Interactive Digital Software Association and renamed on July 16, 2003.","e_t_35.webp",7,1);
//Found
        this.questionCreator("For drawing and designing of homes, buildingsand machineries","Computer aided design (cad)","Web page authoring program","Graphics software","Spreadsheets program","Computer aided design (cad)","CAD (computer-aided design) software is used by architects, engineers, drafters, artists, and others to create precision drawings or technical illustrations. CAD software can be used to create two-dimensional (2-D) drawings or three-dimensional (3-D) models.","e_t_36.webp",7,1);
//Found
        this.questionCreator("For integrated accounting applications for any business","Accounting software","Entertainment software","Educational software","Anti-virus software","Accounting software","Accounting software describes a type of application software that records and processes accounting transactions within functional modules such as accounts payable, accounts receivable, journal, general ledger, payroll, and trial balance.","e_t_37.webp",7,1);
//Found
        this.questionCreator("What is the number one rule in minecraft?","Don't dig straight down","never kill team-mates","Never explorer at night","Never try to drink lava","Don't dig straight down","Never dig straight down: Creators of Minecraft, Notch and Jeb will always say : “The number 1 rule of Minecraft is to never, ever dig straight down!” The reason you shouldn’t do this, is because, when you’re mining, digging straight down can leading to digging into a lava pool. Nobody likes that as not only do you die, but you lose all your gear forever because it burns!","e_t_38.webp",7,1);
//Found
        this.questionCreator("Who Created Minecraft?","Notch","Stampy","SkyDoesMinecraft","DinnerBone","Notch","Notch (Markus Alexej Persson) at the 2011 Game Developers Conference. Markus Alexej Persson (or more commonly known in the Minecraft community as Notch) is a video game developer from Stockholm, Sweden.","e_t_39.webp",7,1);
//Found
        this.questionCreator("The creator of Minecraft is Notch what's his real name?","Markus Persson","Aaron Houston","Cahal Mc Guire","Fionan Gallen","Markus Persson","Notch (Markus Alexej Persson) at the 2011 Game Developers Conference. Markus Alexej Persson (or more commonly known in the Minecraft community as Notch) is a video game developer from Stockholm, Sweden.","e_t_40.webp",7,1);
//Found
        this.questionCreator("Where did Notch the creator of mine craft come from?","Sweden","England","Germany","Africa","Sweden","While Notch was working at King.com, he programmed many various games including a port of Zuma, the game Pinball King and many more. Notch learned many programming languages which have helped him create many games over the years. The languages were Basic, C, C++, Java, Actionscript, and Basic.","e_t_41.webp",7,1);
//Found
        this.questionCreator("What is the end of Minecraft?","Enderdragons","Die in 1.9.6","The end of the game","No such thing","Enderdragons","Once you make the Eye of Ender or all twelve, you'll need to throw them into the air. They will travel towards the portal. Once you can throw one in the air and it doesn't go anywhere, dig down and you should find a brick fortress containing the End Portal","e_t_42.webp",7,1);
//Found
        this.questionCreator("Who invented the first computer?","Charles Babbage","Bill Gates","Steve Jobs","Alan Turing","Charles Babbage","We could argue that the first computer was the abacus or its descendant, the category_item rule, invented by William Oughtred in 1622. But the first computer resembling today's modern machines was the Analytical Engine, a device conceived and designed by British mathematician Charles Babbage between 1833 and 1871.","e_t_43.webp",7,1);
//Found
        this.questionCreator("Which of these are not type of a computer.","Rice Cooker","Mainframe Computer","Minicomputer","Microcomputer","Rice Cooker","Different type of computer are Supercomputer ,  Mainframe Computer ,   Minicomputer ,  Microcomputer that's all Rice cooker is not a computer it's an automated kitchen appliance designed to boil or steam rice. It consists of a heat source, a cooking bowl, and a thermostat. The thermostat measures the temperature of the cooking bowl and controls the heat.","e_t_44.webp",7,1);
//Found
        this.questionCreator("Who is the father of computer?","Charles Babbage","Steve Jobs","Bill Gates","Linus Torvalds","Charles Babbage","was considered to be the father of computing after his invention and concept of the Analytical Engine in 1837. The Analytical Engine contained an Arithmetic Logic Unit (ALU), basic flow control, and integrated memory; hailed as the first general-purpose computer concept.","e_t_45.webp",7,1);
//Found
        this.questionCreator("Who invented the first digital computer?","John Vincent Atanasoff","Alan Turing","Charles Babbage","Jessica Oris","John Vincent Atanasoff","John Vincent Atanasoff (October 4, 1903 – June 15, 1995) was an American-Bulgarian physicist and inventor, best known for being credited with inventing the first electronic digital computer. Atanasoff invented the first electronic digital computer in the 1930s at Iowa State College.","e_t_46.webp",7,1);
//Found
        this.questionCreator("Who invented laptop?","Adam Osborne","John Clint","Klay Davidson","Edison Adam","Adam Osborne","The laptop was invented by Adam Osborne in 1981. It was called 'Osborne 1 and cost $1,795. It came bundled with $1,500 worth of programmes. It had a tiny computer screen built into it.","e_t_47.webp",7,1);
//Found
        this.questionCreator("Who is the father of Internet?","Vint Cerf","Vincent Woz","Eddie Stanford","Michael Myers","Vint Cerf","Widely known as a “Father of the Internet,” Cerf is the co-designer of the TCP/IP protocols and the architecture of the Internet. In December 1997, President Bill Clinton presented the U.S. National Medal of Technology to Cerf and his colleague, Robert E. Kahn, for founding and developing the Internet.","e_t_48.webp",7,1);
//Found
        this.questionCreator("Who is the father of Facebook?","Mark Zuckerberg","Lola Meyer","Gina Yu","Parker Roman","Mark Zuckerberg","Mark Zuckerberg's Dad Proclaims Himself Father of Facebook Hollywood has it wrong: The real \"father of Facebook\" was not a young hacker working out of a Harvard dorm room; it was a technologically advanced dentist in the suburbs of New York City. Just ask Edward J. Zuckerberg, D.D.S.","e_t_49.webp",7,1);
//Found
        this.questionCreator("Who is the mother of Internet?","Radia Perlman","Redman","Micheal Drake","Will Lin","Radia Perlman","There is a woman who has been called the \"mother of the Internet.\" Radia Perlman, a network engineer and software designer with a Ph.D. in computer science from MIT, has made numerous contributions to the Internet as we know it, holding more than 80 related patents.","e_t_50.webp",7,1);
//Found
        this.questionCreator("Who is the father of email?","Raymond Tomlinson","Eugene Sutton","Madalyn Valenzuela","Jensen Dickson","Raymond Tomlinson","Remembering Raymond Tomlinson, the Father of Email. The computer engineer who sent the first piece of electronic mail died on Saturday. He was 74.","e_t_51.webp",7,1);
//Found
        this.questionCreator("Who invented the Space War?","Steve Russell","Kaylyn Ewing","Alfonso York","Markus Gardner","Steve Russell","Spacewar - The First Computer Game. It was in 1962 when a young computer programmer from MIT, Steve Russell (fueled with inspiration from the writings of E. E. \"Doc\" Smith*), created the first computer video game.","e_t_52.webp",7,1);
//Found
        this.questionCreator("Who is the father of Gmail?","Andrew Warner","Drew Warner","Jason Warner","Larry Wrick","Andrew Warner","Andrew Warner is an Internet entrepreneur and the founder of Mixergy.","e_t_53.webp",7,1);
//Found
        this.questionCreator("What letter is between Q and E on a computer keyboard?","Key W","Key A","Key D","Key F","Key W","Did you know that The most efficient keyboard layout in the world is called Turkish F. It was designed by İhsan Sıtkı Yener in 1955. He used scientific methods to measure letter frequencies in turkish language to compose a keyboard layout that would be fast to use.","e_t_54.webp",7,1);
//Found
        this.questionCreator("What kind of fruit was used to name a computer in 1984?","Apple","Guava","Grapes","Star Fruit","Apple","\"1984\" is an American television commercial that introduced the Apple Macintosh personal computer.","e_t_55.webp",7,1);
//Found
        this.questionCreator("What famous scientist introduced the concept of the electric field?","Michael Faraday","Christopher Columbus","Albert Einstein","Nikola Tesla","Michael Faraday","Michael Faraday FRS was a British scientist who contributed to the study of electromagnetism and electrochemistry. His main discoveries include the principles underlying electromagnetic induction, diamagnetism and electrolysis.","e_t_56.webp",7,1);
//Found
        this.questionCreator("An electric fish is a fish that can generate a what?","Electric field","Electric Wave","Electric Fan","Electric Shock","Electric field","An electric fish is any fish that can generate electric fields.","e_t_57.webp",7,1);
//Found
        this.questionCreator("What was the first web browser?","Mosaic","Firefox","Internet Explorer","Google Chrome","Mosaic","Invented by Marc Andreesen in 1993, Mosaic was the first graphical browser for the World Wide Web.","e_t_58.webp",7,1);
//Found
        this.questionCreator("What does the Internet prefix WWW stand for?","World Wide Web","World Wide Way","Wold Wide Webb","World Wide Wiki","World Wide Web","The World Wide Web dramatically increased the use of the Internet. Tim Berners-Lee established the convention in 1989.","e_t_59.webp",7,1);
//Found
        this.questionCreator("What is the name for a computer pointing device?","Mouse","Keyboard","RAM","Headset","Mouse","A mouse allows a computer user to move to different parts of the screen without a keyboard.","e_t_60.webp",7,1);
//Found
        this.questionCreator("When was the DVD introduced?","1995","1991","1992","1993","1995","The DVD, which means “digital video disc” or “digital versatile disc,” appeared in 1995. DVDs are often used to store movies.","e_t_61.webp",7,1);
//Found
        this.questionCreator("Who is the maker of the iPhone?","Apple","Nokia","Samsung","Microsoft","Apple","Apple Computer is the maker of the iPhone. The telephone and miniature computer first went on sale in 2007.","e_t_62.webp",7,1);
//Found
        this.questionCreator("Which of these is part of a computer?","Keyboard","Keylock","Keystone","Key lime","Keyboard","Computer keyboard is a typewriter-style device which uses an arrangement of buttons or keys to act as mechanical levers or electronic switches.","e_t_63.webp",7,1);
//Found
        this.questionCreator("Which of these operating systems is made by Microsoft?","Windows","OS X","Linux","CP/M","Windows","Microsoft Windows is a group of several graphical operating system families, all of which are developed, marketed, and sold by Microsoft. Each family caters to a certain sector of the computing industry.","e_t_64.webp",7,1);
//Found
        this.questionCreator("Which of these is not a computer operating system?","Photoshop","Microsoft Windows","Apple OS X","Linux","Photoshop","Adobe Photoshop is a raster graphics editor developed and published by Adobe Systems for macOS and Windows. Photoshop was created in 1988 by Thomas and John Knoll.","e_t_65.webp",7,1);
//Found
        this.questionCreator("Which of the following is a computer operating system?","Linux","Xenon","Xerox","Borax","Linux","Linux is a family of free and open-source software operating systems built around the Linux kernel. Typically, Linux is packaged in a form known as a Linux distribution for both desktop and server use.","e_t_66.webp",7,1);
//Found
        this.questionCreator("What graphics term describes how transparent an image is?","Opacity","Saturation","Exposure","Hue","Opacity","Definition and Usage. The opacity property sets the opacity level for an element. The opacity-level describes the transparency-level, where 1 is not transparent at all, 0.5 is 50% see-through, and 0 is completely transparent.","e_t_67.webp",7,1);
//Found
        this.questionCreator("Which of the following technologies does NOT facilitate the transferring of data between computers in adjacent buildings?","Microwave","Fibre optic","Bluetooth","Twisted pair","Microwave","Data transfer is the process of using computing techniques and technologies to transmit or transfer electronic or analog data from one computer node to another.","e_t_68.webp",7,1);
//Found
        this.questionCreator("What Big tech company celebrates there anniversary with M&Ms","Microsoft","Apple","Google","Dropbox","Microsoft","All companies have their little in-house traditions, and Microsoft is no exception. It seems it's customary for Softies to celebrate their yearly employment anniversaries with candy, and more specifically, M&Ms.","e_t_69.webp",7,1);
//Found
        this.questionCreator("What letter is between W and R on a computer keyboard?","Key E","Key A","Key D","Key F","Key E","Did you know that on QWERTY keyboard, these are stewardesses (among other 12-letter words) for left hand, and polyphony for the right hand. According to this research, Dvorak layout can produce 14-letter words with only left hand used, for example overemphasized.","e_t_70.webp",7,1);
//Found
        this.questionCreator("What letter is between F and H on a computer keyboard?","Key G","Key A","Key D","Key F","Key G","Did you know that the shortest space keys are found in japan.","e_t_71.webp",7,1);
//Found
        this.questionCreator("What letter is between I and P on a computer keyboard?","Key O","Key A","Key D","Key F","Key O","Did you know that Commands from keyboards are processed faster than those from a mouse","e_t_72.webp",7,1);
//Found
        this.questionCreator("What letter is between Z and C on a computer keyboard?","Key X","Key A","Key D","Key F","Key X","Did you know that QWERTY keyboard structure is over 100 years old and was invented to by Christopher Shole, to slow down the typing speed of the typist as faster typing would often cause the typewriter to jam.","e_t_73.webp",7,1);
//Found
        this.questionCreator("What letter is between C and B on a computer keyboard?","Key V","Key A","Key D","Key F","Key V","The \"@\" sign was very close to eing eliminated from the standard keyboard until 1971, when Ray Tomlinson wrote it into the code used to send the first email.","e_t_74.webp",7,1);
//Found
        this.questionCreator("What letter is between A and D on a computer keyboard?","Key S","Key A","Key D","Key F","Key S","The Backspace button is the third most used on your average keyboard. Just behind e and the spacebar.","e_t_75.webp",7,1);
//Found
        this.questionCreator("What letter is between D and G on a computer keyboard?","Key F","Key A","Key D","Key B","Key F","Have you noticed a slight bump (a small horizontal line)  on the J and F keys on a  QWERTY keyboard? have you ever wondered why are they present? the fact is these bumps make it easier to type.","e_t_76.webp",7,1);
//Found
        this.questionCreator("What letter is between H and K on a computer keyboard?","Key J","Key A","Key D","Key F","Key J","Did you know that Keyboards are Filthier than Toilets","e_t_77.webp",7,1);
//Found
        this.questionCreator("What letter is between B and M on a computer keyboard?","Key N","Key A","Key D","Key F","Key N","Did you know that Every 10000 Words Typed = 1 Mile Travelled by the Fingers","e_t_78.webp",7,1);
//Found
        this.questionCreator("What letter is between R and Y on a computer keyboard?","Key T","Key A","Key D","Key F","Key T","Did you know that keystrokes can be tracked by hackers so be careful","e_t_79.webp",7,1);
//Found
        this.questionCreator("What letter is between Y and I on a computer keyboard?","Key U","Key A","Key D","Key F","Key U","Did you know that QWERTY is not the most efficient keyboard layout","e_t_80.webp",7,1);
//Found
        this.questionCreator("What letter is between T and U on a computer keyboard?","Key Y","Key A","Key D","Key F","Key Y","Did you know that when you hit spacebar, 600000 people in the world did just the same","e_t_81.webp",7,1);
//Found
        this.questionCreator("What Does AM mean?","Amplitude modulation","Angelo Marconi","Amperes","Anno median","Amplitude modulation","Amplitude modulation was the first type to be used in radio it works well with HF and Morse code.","e_t_82.webp",7,1);
//Found
        this.questionCreator("Who is the present CEO of Google?","Sundar Pichai","Lawrence E. Page","Paul Allen","Larry Ellison","Sundar Pichai","“Pichai Sundararajan, also known as Sundar Pichai, is an Indian American business executive. Pichai is the chief executive officer of Google Inc.”","e_t_83.webp",7,1);
//Found
        this.questionCreator("What is a transistor?","Semiconductor","Conductor","Insulator","Superconductor","Semiconductor","“A transistor is a semiconductor device used to amplify or switch electronic signals and electrical power. It is composed of semiconductor material usually with at least three terminals for connection to an external circuit.”","e_t_84.webp",7,1);
//Found
        this.questionCreator("What is the formal term for a web address?","URL","Netsite","Website","FTP","URL","“Web Address is a URL with HTTP/HTTPS. The term \"web address\" is a synonym for a URL that uses the HTTP or HTTPS protocol. The Uniform Resource Locator (URL) was developed by Tim Berners-Lee in 1994 and the Internet Engineering Task Force (IETF) URI working group. Today, the format of the URL has not changed.”","e_t_85.webp",7,1);
//Found
        this.questionCreator("In terms of computing, what does CPU stand for?","Central Processing Unit","Center Process Unit","Collide Part Unit","Care Prepare Undone","Central Processing Unit","“A central processing unit (CPU) is the electronic circuitry within a computer that carries out the instructions of a computer program by performing the basic arithmetic, logical, control and input/output (I/O) operations specified by the instructions”","e_t_86.webp",7,1);
//Found
        this.questionCreator("What does RAM stand for?","Random Access Memory","Rent Accurate Memory","Random Act Memory","Raise Access Memo","Random Access Memory","“Random-access memory RAM is a form of computer data storage that stores data and machine code currently being used”","e_t_87.webp",7,1);
//Found
        this.questionCreator("What does ROM stand for?","Read Only Memory","React Own Memo","Right Of Memory","Read own Memory","Read Only Memory","“Read-only memory (ROM) is a type of non-volatile memory used in computers and other electronic devices”","e_t_88.webp",7,1);
//Found
        this.questionCreator("What does the abbreviation WWW stand for?","World Wide Web","Work Web Work","World Work Web","Work Wide Web","World Wide Web","“The World Wide Web dramatically increased the use of the internet. Tim Berners-Lee established the convention in 1989”","e_t_89.webp",7,1);
//Found
        this.questionCreator("Mac operating system is developed by which company?","Apple","IBM","Microsoft","Samsung","Apple","“Mac OS, operating system (OS) developed by the American computer company Apple Inc. The OS was introduced in 1984 to run the company's Macintosh line of personal computers (PCs).”","e_t_90.webp",7,1);
//Found
        this.questionCreator("What is the name for a computer pointing device?","Mouse","Hand","Pointer","Insertion Point","Mouse","“A mouse allows a computer user to move to different parts of the screen without keyboard”","e_t_91.webp",7,1);
//Found
        this.questionCreator("Who is the maker of iPhone?","Apple","Samsung","Cherry mobile","Huawei","Apple","“Apple computer is the maker of the iPhone. The telephone and miniature computer first went on sale in 2007”","e_t_92.webp",7,1);
//Found
        this.questionCreator("Which of the following web browser is developed by Apple?","Safari","Opera","Firefox","Chrome","Safari","“Safari is a web browser developed by Apple based on the WebKit engine. First released in 2003 with Mac OS X Panther, a mobile version has been included in iOS devices since the introduction of the iPhone in 2007”","e_t_93.webp",7,1);
//Found
        this.questionCreator("Which of the following is not a part of the internet?","CD-ROM","Youtube","World wide web","Email","CD-ROM","“CD-ROM (Compact Disc, read-only-memory) is an adaptation of the CD that is designed to store computer data in the form of text and graphics, as well as hi-fi stereo sound”","e_t_94.webp",7,1);
//Found
        this.questionCreator("What company invented the first digital camera?","Kodak","Cannon","Nikon","Olympus","Kodak","“Eastman Kodak; world without iPhones or Instagram, where one company reigned supreme. Such a world existed in 1973, when Steven Sasson, a young engineer, went to work for Eastman Kodak. Two years later he invented digital photography and made the first digital camera.”","e_t_95.webp",7,1);
//Found
        this.questionCreator("Which manufactured released the galaxy tab series?","Samsung","Sony","Motorola","LG","Samsung","“The Samsung Galaxy Tab is a line of Android-based tablet computers produced by Samsung Electronics. The first model in the series, the 7-inch Samsung Galaxy Tab, was presented to the public on 2 September 2010 at the IFA in Berlin”","e_t_96.webp",7,1);
//Found
        this.questionCreator("Saving a file from the internet onto your desktop is called?","Downloading","Uploading","Transferring","Storing","Downloading","“In computer networks, to download (abbreviation DL) is to receive data from a remote system, typically a server such as a web server, an FTP server, an email server, or other similar systems. This contrasts with uploading, where data is sent to a remote server”","e_t_97.webp",7,1);
//Found
        this.questionCreator("Which is an Input device?","Mouse","Printer","Monitor","Speaker","Mouse","“In computing, an input device is a piece of computer hardware equipment used to provide data and control signals to an information processing system such as a computer or information appliance. Examples of input devices include keyboards, mouse, scanners, digital cameras and joysticks”","e_t_98.webp",7,1);
//Found
        this.questionCreator("Mark Zuckerberg is the owner of?","Facebook","Google","Linux","Linkedln","Facebook","“Mark Elliot Zuckerberg is an American technology entrepreneur and philanthropist. He is known for co-founding and leading Facebook as its chairman and chief executive officer”","e_t_99.webp",7,1);
//Found
        this.questionCreator("USB port stand for?","Universal Serial Bus Port","United Serial Bus Port","Universal Sequential Bus Port","Universal Serial BIOS Port","Universal Serial Bus Port","“A Universal Serial Bus (USB) is a common interface that enables communication between devices and a host controller such as a personal computer (PC). It connects peripheral devices such as digital cameras, mice, keyboards, printers, scanners, media devices, external hard drives and flash drives.”","e_t_100.webp",7,1);


    }

    private void addTechnologyModerate()
    {
        //1-50 done
//Found
        this.questionCreator("Who invented Compact Disc?","James T Russell","James Washington","James Edison","James Camilton","James T Russell","James T. Russell is an American inventor. He earned a BA in physics from Reed College in Portland in 1953. He joined General Electric's nearby labs in Richland, Washington, where he initiated many types of experimental instrumentation. He designed and built the first electron beam welder.","m_t_1.webp",7,2);

//Found
        this.questionCreator("Which day is celebrated as world Computer Literacy Day?","December 2","December 1","December 5","December 4","December 2","World Computer Literacy Day – Dec 2nd. Launched back in 2001, World Computer Literacy Day which falls each year on December 2nd aims to curb the digital divide that exists in the world today. The Day aims to increase awareness of this 'divide' and increase access to information technology for disadvantaged communities.","m_t_2.webp",7,2);
//Found
        this.questionCreator("Who invented Java?","James A Gosling","James Hammer","Jay Ashito","Klay Cameron","James A Gosling","James Arthur Gosling, OC is a Canadian computer scientist, best known as the founder and lead designer behind the Java programming language.","m_t_3.webp",7,2);

//Found
        this.questionCreator("Longhorn was the code name of ?","Windows Vista","Windows XP","Windows 10","Windows 7","Windows Vista","Windows 7 was supposed to follow longhorn much earlier than it was actually released, and Longhorn was meant to be a stepping stone, like a minor update to Windows XP","m_t_4.webp",7,2);

//Found
        this.questionCreator("Who is known as the Human Computer of India?","Shakunthala Devi","Jayesh Lakshit","Kiaan Indranil","Bhavin Chirag","Shakunthala Devi","Shakuntala Devi (4 November 1929 – 21 April 2013) was an Indian writer and mental calculator, popularly known as the \"human computer\".","m_t_5.webp",7,2);

//Found
        this.questionCreator("Who invented the 4th generation computer?","Ted Hoff","Joaquin Lawrence","Maxwell Kirk","Ryann Clay","Ted Hoff","The first microprocessor was first introduced in 1971 by Intel, invented by their employee Ted Hoff. They named it the Intel 4004; the first computer on a chip.","m_t_6.webp",7,2);

//Found
        this.questionCreator("Which computer engineer got Nobel Prize for literature in 2003?","J.M. Coetzee","J.C Carpio","J.K Sanchez","J Cambino","J.M. Coetzee","The Nobel Prize in Literature 2003 was awarded to John M. Coetzee \"who in innumerable guises portrays the surprising involvement of the outsider.\"","m_t_7.webp",7,2);

//Found
        this.questionCreator("Weaving The Web' was written by.","Tim Burners Lee","Donald Shepard","Thomas Bishop","Nyasia Bentley","Tim Burners Lee","Sir Timothy John Berners-Lee OM KBE FRS FREng FRSA FBCS, also known as TimBL, is an English engineer and computer scientist, best known as the inventor of the World Wide Web. He is currently a professor of computer science at the University of Oxford and the Massachusetts Institute of Technology.","m_t_8.webp",7,2);

//Found
        this.questionCreator("When was Google introduced?","September 4 , 1998","December 3 , 1998","Febuary 1 , 1998","March 8 , 1998","September 4 , 1998","4 September 1998, Menlo Park, California, United States","m_t_9.webp",7,2);

//Found
        this.questionCreator("Do'nt be evil' is tag line of .","Google","Microsoft","Apple","Twitter","Google","Don't be evil. \"Don't be evil\" is a motto used within Google's corporate code of conduct. Following Google's corporate restructuring under the conglomerate Alphabet Inc. in October 2015, Alphabet took \"Do the right thing\" as its motto, also forming the opening of its corporate code of conduct.","m_t_10.webp",7,2);

//Found
        this.questionCreator("First Indian cinema released through internet is ","Vivah","Terminator","The 3 Idiots","Sinke","Vivah","Vivah is the first Indian film to be simultaneously released in cinema and on the internet (through the production company's official site). The film was also dubbed into Telugu and released as Parinayam.","m_t_11.webp",7,2);

//Found
        this.questionCreator("Rediff.com was founded by","Ajit Balakrishnan","Āryāvarta Balakrishnann","Jambudvīpa Tianzhu","Hodu Nābhivarṣa","Ajit Balakrishnan","Ajit Balakrishnan is an Indian entrepreneur, business executive and administrator. He is the founder, current Chairman and Chief Executive Officer of Rediff.com, an internet company based in Mumbai.","m_t_12.webp",7,2);

//Found
        this.questionCreator("What is the extension of PDF?","Portable document format","Portable design format","Portable diagnose format","Portable documentt format","Portable document format","The PDF file type is primarily associated with 'Acrobat' by Adobe Systems Incorporated. Adobe Acrobat is a family of computer programs developed by Adobe Systems, designed to view, create, manipulate and manage files in Adobe's Portable Document Format (PDF).","m_t_13.webp",7,2);

//Found
        this.questionCreator("The inventor of 5th generation computer.","James Maddox","Michaela Phillips","Enzo Spears","Mohamed Buck","James Maddox","James Maddox, the inventor of the Company's US Patent, 6,000,024 (the \"024 Patent\"), created a unique and novel “Parallel Computing System.” Mr. Maddox began work on the system in 1993 in the middle of a distinguished career, serving as Director of Engineering for the Company from December 1993 through December 1999.","m_t_14.webp",7,2);

//Found
        this.questionCreator("Expand RDBMS?","Relational Data Base Management System","Relational Data Management System","Relational Data Center Management System","Relational Data Camp Management System","Relational Data Base Management System","A relational database management system (RDBMS) is a database management system (DBMS) based on the relational model invented by Edgar F. Codd at IBM's San Jose Research Laboratory. Most databases in widespread use today are based on his relational database model.","m_t_15.webp",7,2);

//Found
        this.questionCreator("Difference engine was developed by","Charles Babbage","Alan Turing","Bill Gates","Steve Jobs","Charles Babbage","The Analytical Engine was a proposed mechanical general-purpose computer designed by English mathematician and computer pioneer Charles Babbage. It was first described in 1837 as the successor to Babbage's difference engine, a design for a mechanical computer.","m_t_16.webp",7,2);

//Found
        this.questionCreator("Orkut.com is now owned by .","Google","Facebook","Twitter","Snapchat","Google","Orkut.com is now owned by Google Muskaankhan07 Trendsetter Asked on August 23, 2017 in Information Technology. Orkut was a social networking website owned and operated by Google. The service was designed to help users meet new and old friends and maintain existing relationships.","m_t_17.webp",7,2);

//Found
        this.questionCreator("World's first microprocessor is ","Intel 4004","Intel 4002","Intel 4001","Intel 4003","Intel 4004","In November of 1971, a company called Intel publicly introduced the world's first single-chip microprocessor, the Intel 4004 (U.S. Patent #3,821,715), invented by Intel engineers Federico Faggin, Ted Hoff, and Stanley Mazor.","m_t_18.webp",7,2);

//Found
        this.questionCreator("What is SQL?","Structured Query Language","Structured Queryy Language","Structured Quue Language","Structured Qatar Language","Structured Query Language","SQL is a domain-specific language used in programming and designed for managing data held in a relational database management system, or for stream processing in a relational data stream management system.","m_t_19.webp",7,2);

//Found
        this.questionCreator("What is the expansion of COBOL?","Common Business Oriented Language","Common Bank Oriented Language","Common Battery Oriented Language","Common Best Oriented Language","Common Business Oriented Language","A new COBOL standard introducing object-oriented programming to COBOL, is due within the next few years. The word COBOL is an acronym that stands for COmmon Business Oriented Language. As the the expanded acronym indicates, COBOL is designed for developing business, typically file-oriented, applications.","m_t_20.webp",7,2);

//Found
        this.questionCreator("What is the expansion of SMS?","Short Message Service","Single Message Service","Same Message Service","Strategy Message Service","Short Message Service","SMS (Short Message Service), commonly referred to as \"text messaging,\" is a service for sending short messages of up to 160 characters (224 characters if using a 5-bit mode) to mobile devices, including cellular phones, smartphones and PDAs.","m_t_21.webp",7,2);

//Found
        this.questionCreator("Which IT company's nickname is ' The Big Blue ' ?","IBM","Twitter","Google","Microsoft","IBM","International Business Machines Corporation, perhaps better known by its acronym IBM, is called the Big Blue. Among the blue-chip stocks on the US stock market, IBM was a steady and consistently high performer over the years.","m_t_22.webp",7,2);

//Found
        this.questionCreator("Who is the inventor of C++ Programming Language?","Bjarne Stroustrup","Delaney Hopkins","Lucy Moreno","Lyric Bray","Bjarne Stroustrup","Bjarne Stroustrup is a Danish computer scientist, who is most notable for the creation and development of the C++ programming language. He is a visiting professor at Columbia University, and works at Morgan Stanley as a Managing Director in New York.","m_t_23.webp",7,2);

//Found
        this.questionCreator("Who developed COBOL?","Grace Murray Hopper","Krystal Li","Ingrid Huff","Shamar Fuentes","Grace Murray Hopper","From 1959 to 1961, Hopper lead the team that invented COBOL (Common Business-Oriented Language), the first user-friendly business computer software program. Later, Hopper invested a great deal of time advocating validation procedures to bring about the international standardization of computer languages.","m_t_24.webp",7,2);

//Found
        this.questionCreator("Email was developed by.","Ray Tomlinson","Jazlene Kim","Jett Clements","Michael Vasquez","Ray Tomlinson","Here, he gives his version of how he invented the email. Ray Tomlinson is universally credited as the creator of email as part of a program for ARPANET in 1971. Meanwhile in 1978, a 14-year-old boy, Shiva Ayyadurai began his work on an email system for the University of Medicine and Dentistry of New Jersey.","m_t_25.webp",7,2);

//Found
        this.questionCreator("Green dam is .","Web Filter","Website","Web Style","Web Core","Web Filter","The Green Dam software filters content by blocking URLs and website images and by monitoring text in other applications. The filtering blacklists include both political and adult content. Some of the blacklists appear to have been copied from American-made filtering software.","m_t_26.webp",7,2);

//Found
        this.questionCreator("One of the founder of Dropbox","Drew Houston","Raven Turner","Lorelai Hoover","Julianna Barnes","Drew Houston","Dropbox was founded in 2007 by MIT students Drew Houston and Arash Ferdowsi as a startup company, with initial funding from seed accelerator Y Combinator.","m_t_27.webp",7,2);

//Found
        this.questionCreator("Who is Netizen ?","Net Citizen","Peoples","Geeks","Nerds","Net Citizen","The term netizen is a portmanteau of the words Internet and citizen as in \"citizen of the net\". It describes a person actively involved in online communities or the Internet in general.","m_t_28.webp",7,2);

//Found
        this.questionCreator("What is Scareware?","Fake antivirus softwares","Original Software","Communication Software","Document Software","Fake antivirus softwares","Scareware is a type of malware that masquerades as real virus protection to trick victims into paying and downloading malicious software to clean up infections it pretends to detect. ... Currently, scareware is the sneakiest methods in use to prey on victims and as a result, it ends up compromising the system security.","m_t_29.webp",7,2);

//Found
        this.questionCreator("When was the first smart phone launched?","1992","1993","1994","1891","1992","People didn't start using the term \"smartphone\" until 1995, but the first true smartphone actually made its debut three years earlier in 1992. It was called the Simon Personal Communicator, and it was created by IBM more than 15 years before Apple released the iPhone.","m_t_30.webp",7,2);

//Found
        this.questionCreator("You’re on a Web site and you see a link called FAQ. What do the letters F A Q stand for?","Frequently asked questions","Friends asking questions","Followers asking questions","Fellows asking questions","Frequently asked questions","Frequently asked questions (FAQ) or Questions and Answers (Q&A), are listed questions and answers, all supposed to be commonly asked in some context, and pertaining to a particular topic. The format is commonly used on email mailing lists and other online forums, where certain common questions tend to recur","m_t_31.webp",7,2);

//Found
        this.questionCreator("You get an email message with a file linked to it.  This file is known as what?","An attachment","An virus","An notification","An request","An attachment","An email attachment is a computer file sent along with an email message. One or more files can be attached to any email message, and be sent along with it to the recipient. This is typically used as a simple method to share documents and images.","m_t_32.webp",7,2);

//Found
        this.questionCreator("What is the name for the new technology whereby a glass fiber carries as much information as hundreds of copper wires?","Fiber Optics","Fiber Optical","Far Operator","Fiber Operation","Fiber Optics","As the name suggests, fibre optic technology uses pulses of light to carry data along strands of glass or plastic.","m_t_33.webp",7,2);

//Found
        this.questionCreator("Solar power generates electricity from what source?","The Sun","The Moon","The Earth","The Mars","The Sun","When sunlight hits the cells, it knocks electrons loose from their atoms. As the electrons flow through the cell, they generate electricity. On a much larger scale, solar-thermal power plants employ various techniques to concentrate the sun's energy as a heat source.","m_t_34.webp",7,2);

//Found
        this.questionCreator("Did the Apple iPhone first become available?","2007","2001","2003","1990","2007","On June 29, 2007, the first iPhone was released. On June 11, 2007, Apple announced at the Apple's Worldwide Developers Conference that the iPhone would support third-party applications using the Safari engine.","m_t_35.webp",7,2);

//Found
        this.questionCreator("In terms of computing, what does CPU stand for?","Central Processing Unit","Center Process Unit","Ceil Pirate Unified","Central Nervous System","Central Processing Unit","CPU stands for central processing unit and is the main core of a computer where every command runs through and is routed to its appropriate location.","m_t_36.webp",7,2);

//Found
        this.questionCreator("The Hubble Space Telescope is named after which American astronomer?","Edwin Hubble","Jovani Patrick","Kameron Weber","Jaycee Curry","Edwin Hubble","The space telescope was named the Hubble Space Telescope, after American astronomer Edwin Hubble, who showed that the fuzzy patches of light in the night sky were actually other galaxies, far distant from our own, and went on to prove that the universe was expanding.","m_t_37.webp",7,2);

//Found
        this.questionCreator("Firefox, Opera, Chrome, Safari and Explorer are types of what?","Web browsers","Web Securities","Web Filter","Website","Web browsers","A web browser is a software application for accessing information on the World Wide Web. Each individual web page, image, and video is identified by a distinct URL, enabling browsers to retrieve and display them on the user's device.","m_t_38.webp",7,2);

//Found
        this.questionCreator("The technologically advanced humanoid robot ASIMO is made by which car company?","Honda","Toyota","Hyundai","Kia","Honda","Honda began developing humanoid robots in the 1980s, including several prototypes that preceded ASIMO. It was the company's goal to create a walking robot.","m_t_39.webp",7,2);

//Found
        this.questionCreator("In terms of computing, what does ROM stand for?","Read Only Memory","Race Only Memory","Reading Only Main","Response On Main","Read Only Memory","Pronounced rahm, acronym for read-only memory, computer memory on which data has been prerecorded. ... Unlike main memory (RAM), ROM retains its contents even when the computer is turned off. ROM is referred to as being nonvolatile, whereas RAM is volatile.","m_t_40.webp",7,2);

//Found
        this.questionCreator("AMD stands for?","Advanced Micro Devices","Advanced Mic Devices","Angle Mechanism Devices","Arc Micro Devices","Advanced Micro Devices","Advanced Micro Devices, Inc. (AMD) is an American multinational semiconductor company based in Santa Clara, California, that develops computer processors and related technologies for business and consumer markets.","m_t_41.webp",7,2);

//Found
        this.questionCreator("What is the Earth’s primary source of energy?","The Sun","The universe","The milkyway","The galaxy","The Sun","The Sun is the primary source of energy for Earth's climate system is the first of seven Essential Principles of Climate Sciences. Principle 1 sets the stage for understanding Earth's climate system and energy balance. The Sun warms the planet, drives the hydrologic cycle, and makes life on Earth possible.","m_t_42.webp",7,2);

//Found
        this.questionCreator("IBM is a well known computer and information technology company, what does IBM stand for?","International Business Machine","International Bank Machine","Internet Business Machine","Intergalactic Business Machine","International Business Machine","The company began in 1911 as the Computing-Tabulating-Recording Company (CTR) and was renamed \"International Business Machines\" in 1924. IBM manufactures and markets computer hardware, middleware and software, and provides hosting and consulting services in areas ranging from mainframe computers to nanotechnology.","m_t_43.webp",7,2);

//Found
        this.questionCreator("Along with whom did Bill Gates found Microsoft?","Paul Allen","Amelia Carroll","Alyssa Hartman","Delilah Morris","Paul Allen","Microsoft was founded on April 4, 1975, by Bill Gates and Paul Allen in Albuquerque, New Mexico.","m_t_44.webp",7,2);

//Found
        this.questionCreator("What science fiction writer wrote the three laws of robotics?","Isaac Asimov","Tessa Moyer","Reed Hensley","Faith Holland","Isaac Asimov","Isaac Asimov's \"Three Laws of Robotics\" A robot may not injure a human being or, through inaction, allow a human being to come to harm. A robot must obey orders given it by human beings except where such orders would conflict with the First Law.","m_t_45.webp",7,2);

//Found
        this.questionCreator("What does the abbreviation WWW stand for?","World Wide Web","World War Web","World Wreck Web","World War W","World Wide Web","The World Wide Web (WWW), also called the Web, is an information space where documents and other web resources are identified by Uniform Resource Locators (URLs), interlinked by hypertext links, and accessible via the Internet.English scientist Tim Berners-Lee invented the World Wide Web in 1989.","m_t_46.webp",7,2);

//Found
        this.questionCreator("Who is the inventor of Flappy Bird?","Dong Nguyen","Reed Hensley","Skylar Bauer","Barbara Mills","Dong Nguyen","Dong Nguyen created Flappy Bird then suddenly shut it down. Celebritynetworth.com Flappy Bird, the latest mobile craze, launched less than one year ago. At its peak, it was generating about $50,000 per day. It was run by one man, Dong Nguyen, an indie game developer in Vietnam.","m_t_47.webp",7,2);

//Found
        this.questionCreator("Who designed the Volkswagen Beetle?","Ferdinand Porsche","Makhi Huang","Hugo Freeman","dMayra Smith","Ferdinand Porsche","Adolf Hitler stole the idea for the iconic Volkswagen Beetle from a Jewish engineer and had him written out of history, a historian has sensationally claimed. The Nazi leader has always been given credit for sketching out the early concept for the car in a meeting with car designer Ferdinand Porsche in 1935.","m_t_48.webp",7,2);

//Found
        this.questionCreator("The bobbing device invented by Stephen Salter  to harness wave power was named after which bird?","Duck","Cat","Dog","Lion","Duck","The idea for creating Salter's duck came about from his studies on a lavatory cistern while at Edinburgh University. He invented Salter's duck in 1974 and attempted to make it the main device of choice for the Wave Energy program in the United Kingdom.","m_t_49.webp",7,2);

//Found
        this.questionCreator("Date that NVIDIA GEFORCE GTX 970 release","31 August 1999","25 August 1999","23 August 1999","45 August 1999","31 August 1999","GeForce is a brand of graphics processing units (GPUs) designed by Nvidia. As of the GeForce 20 series, there have been fifteen iterations of the design.","m_t_50.webp",7,2);

//Found
        this.questionCreator("What was the name of the three-stage rocket used in the Apollo series of moon shots?","Saturn V","Uranus","Jupiter","P-X","Saturn V","The Apollo spacecraft were launched on top of the Saturn V rocket. The Saturn V was made of three stages. The first two stages used up their fuel reaching orbit. The third stage was used to push the Apollo Command Module and Lunar Module to the moon.","m_t_51.webp",7,2);

//Found
        this.questionCreator("What is 'maglev' an abbreviation for?","Magnetic levitation","Memory levitation","Maximum levitation","Minimum levitation","Magnetic levitation","Magnetic levitation, maglev(noun) high-speed rail technology; train is suspended on a magnetic cushion above a magnetized track and so travels free of friction.","m_t_52.webp",7,2);

//Found
        this.questionCreator("Who is the first man who found bug on a computer?","Grace Hopper","Thomas Edison","Albert Einstein","Elon Musk","Grace Hopper","On September 9, 1947, computer scientist Grace Hopper reported the world's first computer bug. A bug is a flaw or glitch in a system. Thomas Edison reported “bugs” in his designs as early as the 1800s, but Hopper was the first to identify one in a computer. In Hopper's case, it was literally a bug.","m_t_53.webp",7,2);

//Found
        this.questionCreator("CEO of Toyota","Akio Toyoda","Aki Toyota","Akiro Toyio","Akira Tay","Akio Toyoda","Akio Toyoda (Toyoda Akio, born May 3, 1956) is a Japanese business executive and the current president of Toyota Motor Corporation. He is the great grandson of the father of the Japanese industrial revolution, as well as being both the grandson to the founder of Toyota motors and maternal grandson to the founder of the Takashimaya department store corporation.","m_t_54.webp",7,2);

//Found
        this.questionCreator("The technique of dialysis is used in which type of medical technology?","Kidney machine","Body machine","Foot machine","Head machine","Kidney machine","Dialysis is used as a temporary measure in either acute kidney injury or in those awaiting kidney transplant and as a permanent measure in those for whom a transplant is not indicated or not possible.","m_t_55.webp",7,2);

//Found
        this.questionCreator("Which Rolls-Royce car was produced between 1906 and 1925?","Sliver Ghost","Silver Ghost","Black Ghost","White Ghost","Sliver Ghost","The Rolls-Royce Silver Ghost name refers both to a car model and one specific car from that series. Originally named the \"40/50 h.p.\" the chassis was first made at Royce's Manchester works, with production moving to Derby in July 1908, and also, between 1921 and 1926, in Springfield, Massachusetts.","m_t_56.webp",7,2);

//Found
        this.questionCreator("What form of transport was initially a form of hobby-horse?","Bicycle","Motorcycle","Truck","Car","Bicycle","Karl von Drais patented this design in 1818, which was the first commercially successful two-wheeled, steerable, human-propelled machine, commonly called a velocipede, and nicknamed hobby-horse or dandy horse.","m_t_57.webp",7,2);

//Found
        this.questionCreator("Who produced the world's first petrol-driven motor vehicle?","Karl Benz","Yusuf Daniel","Annabella Suarez","Naomi Keith","Karl Benz","It is generally acknowledged that the first really practical automobiles with petrol/gasoline-powered internal combustion engines were completed almost simultaneously by several German inventors working independently: Karl Benz built his first automobile in 1885 in Mannheim.","m_t_58.webp",7,2);

//Found
        this.questionCreator("Where in airplanes are the ailerons positioned?","Wing","Body","Turbine Engine","Cockpit","Wing","Ailerons can be used to generate a rolling motion for an aircraft. Ailerons are small hinged sections on the outboard portion of a wing. Ailerons usually work in opposition: as the right aileron is deflected upward, the left is deflected downward, and vice versa.","m_t_59.webp",7,2);

//Found
        this.questionCreator("Which computer language is an acronym of the name of the world's first computer programmer?","ADA","ABC","ABB","AED","ADA","Ada Lovelace–born Augusta Ada Byron, later known as Ada Lovelace after becoming the Countess of Lovelace when her husband William King became the Earl of Lovelace–is best known for her work on Charles Babbage's analytical engines.","m_t_60.webp",7,2);

//Found
        this.questionCreator("What year twitter release?","2006","2002","2003","2004","2006","Twitter was created in March 2006 by Jack Dorsey, Noah Glass, Biz Stone, and Evan Williams and launched in July of that year. The service rapidly gained worldwide popularity.","m_t_61.webp",7,2);

//Found
        this.questionCreator("What name is given to the device used to produce an intense and narrow beam of light?","Laser","Light","Rays","Sunbeam","Laser","A laser is a device that emits light through a process of optical amplification based on the stimulated emission of electromagnetic radiation. The term \"laser\" originated as an acronym for \"light amplification by stimulated emission of radiation\".","m_t_62.webp",7,2);

//Found
        this.questionCreator("Who created the first computer for home use?","John Blankenbaker","Mikayla Griffith","Case Gomez","Matias Hansen","John Blankenbaker","It was designed and invented by John Blankenbaker of Kenbak Corporation in 1970, and was first sold in early 1971. Unlike a modern personal computer, the Kenbak-1 was built of small-scale integrated circuits, and did not use a microprocessor. The system first sold for US$750.","m_t_63.webp",7,2);

//Found
        this.questionCreator("When was the first computer built?","1936 - 1938","1920 - 1931","1946 - 1950","1980 - 1991","1936 - 1938","First programmable computer. The Z1 was created by German Konrad Zuse in his parents' living room between 1936 and 1938. It is considered to be the first electro-mechanical binary programmable computer, and the first really functional modern computer.","m_t_64.webp",7,2);

//Found
        this.questionCreator("When was the first computer for home use become common?","1980s","1970s","1960s","1950s","1980s","Home computers were a class of microcomputers that entered the market in 1977, that started with what Byte Magazine called the \"trinity of 1977\", (the Apple II, the TRS-80 Model I, and the Commodore PET) and which became common during the 1980s.","m_t_65.webp",7,2);

//Found
        this.questionCreator("Who invented first generation computer?","Lee De Forest","Bo Munoz","Skyler Payne","Karson Gaines","Lee De Forest","The vacuum tube was invented in 1906 by an electrical engineer named Lee De Forest (1873–1961). During the first half of the twentieth century, it was the fundamental technology that was used to construct radios, televisions, radar, X-ray machines, and a wide variety of other electronic devices.","m_t_66.webp",7,2);

//Found
        this.questionCreator("One of the inventor of the 2nd generation computer?","J. Bardeen","Jessie Villarreal","Jaidyn Franklin","Jamar Sims","J. Bardeen","John Bardeen (1908–1991), William B. Shockley (1910–1989), and Walter H. Brattain (1902–1987) invented the transistor at Bell Telephone Laboratories in the mid-1940s. By 1948 it was obvious to many that the transistor would probably replace the vacuum tube in devices such as radios, television sets, and computers.","m_t_67.webp",7,2);

//Found
        this.questionCreator("Who invented the 3rd generation computer?","Jack Kilby","J. Bardeen","Jay Ash","Janice Wallace","Jack Kilby","Transcript of 3rd Generation of Computer. (IC), signals the beginning of the third generation computers. The Integrated Circuit (IC) was invented in 1958 by Jack Kilby. It combined electronic components onto a small silicon disc, made from quartz.","m_t_68.webp",7,2);
//Not Found
        this.questionCreator("What does VVVF stand for?","Variant Voltage Variable Frequency","Variable voltage Vile Frequency","Variable Velocity Variable Fun","Very Very Vicious Frequency","Variant Voltage Variable Frequency","it is a method of controlling the speed of an AC induction motor, whereby speed, current and torque can all be accurately controlled.","m_t_69.webp",7,2);

//Found
        this.questionCreator("What does the term PLC stand for?","Programmable Logic Computer","Programmable Lift Controller","Programmable List Control","Piezo Lamp Connector","Programmable Logic Computer","Used in manufacturing, engineering, and process operations.","m_t_70.webp",7,2);

//Found
        this.questionCreator("Who invented JAVA?","James Gosling","Sabeer Bhatia","Richard Stallman","Larry Wall","James Gosling","“James Arthur Gosling, OC (born May 19, 1955) is a Canadian computer scientist, best known as the founder and lead designer behind the Java programming language.”","m_t_71.webp",7,2);

//Found
        this.questionCreator("When did the Apple iPhone first become available?","Year 2007","Year 2005","Year 1999","Year 2000","Year 2007","“On January 9, 2007, Steve Jobs announced iPhone at the Macworld convention, receiving substantial media attention. Jobs announced that the first iPhone would be released later that year. On June 29, 2007, the first iPhone was released”","m_t_72.webp",7,2);

//Found
        this.questionCreator("What does http stand for?","Hyper Text Transfer Protocol","Hide Text Transfer Protocol","Hyper Translate Transfer Protocol","Highlight Text Translate Protocol","Hyper Text Transfer Protocol","“The Hypertext Transfer Protocol (HTTP) is an application protocol for distributed, collaborative, and hypermedia information systems. HTTP is the foundation of data communication for the World Wide Web”","m_t_73.webp",7,2);

//Found
        this.questionCreator("Which one is the first search engine in internet?","Archie","Google","Altavista","WAIS","Archie","“The first few hundred web sites began in 1993 and most of them were at colleges, but long before most of them existed came Archie. The first search engine created was Archie, created in 1990 by Alan Emtage, a student at McGill University in Montreal.”","m_t_74.webp",7,2);

//Found
        this.questionCreator("Firewall in computer is used for?","Security","Data Transmission","Authentication","Monitoring","Security","“A firewall is a system designed to prevent unauthorized access to or from a private network. You can implement a firewall in either hardware or software form, or a combination of both. Firewalls prevent unauthorized internet users from accessing private networks connected to the internet, especially intranets.”","m_t_75.webp",7,2);

//Found
        this.questionCreator("Which of the following is not a database management software?","COBOL","MySQL","Sybase","Oracle","COBOL","“COBOL an acronym for \"common business-oriented language\" is a compiled English-like computer programming language designed for business use”","m_t_76.webp",7,2);

//Found
        this.questionCreator("Where is the headquarters of Microsoft office located?","Washington","Texas","California","New York","Washington","“Microsoft Corporation (abbreviated as MS) is an American multinational technology company with headquarters in Redmond, Washington”","m_t_77.webp",7,2);

//Found
        this.questionCreator("gif is an extension of?","Image file","Video file","Audio file","Word file","Image file","“GIF, or Graphic Interchange Format, is a file extension for an often animated raster graphics file and is the second most common image format used on the World Wide Web after JPEG. GIF uses the LZW compression algorithm and is owned by Unisys”","m_t_78.webp",7,2);

//Found
        this.questionCreator("Who founded Apple Computer?","Steve Jobs","Paul Allen","Mark Zuckerberg","Bill Gates","Steve Jobs","“Steve Jobs founded Apple Computer with Steve Wozniak and another partner in 1976. Jobs became the chairman and CEO of apple in 1996”","m_t_79.webp",7,2);

//Found
        this.questionCreator("In what year was the iPod first sold?","Year 2001","Year 2000","Year 1999","Year 1998","Year 2001","“Apple’s iPod portable music device was first released in 2001. The initial version had a 5GB hard drive”","m_t_80.webp",7,2);

//Found
        this.questionCreator("What company created XBOX?","Microsoft","Sony","Nintendo","Apple","Microsoft","“Xbox is a video gaming brand created and owned by Microsoft of the United States. It represents a series of video game consoles developed by Microsoft, with three consoles released in the sixth, seventh and eighth generations, respectively.”","m_t_81.webp",7,2);

//Found
        this.questionCreator("Which car company produces the Civic?","Honda","Skoda","Audi","Nissan","Honda","“The Civic is a line of compact cars developed and manufactured by Honda. In North America, the Civic is the second-longest continuously running nameplate from a Japanese manufacturer; only its perennial rival, the Toyota Corolla, introduced in 1968, has been in production longer.”","m_t_82.webp",7,2);

//Found
        this.questionCreator("What was the first company to produce a handheld mobile telephone?","Motorola","Nokia","Sony Ericson","Samsung","Motorola","“Motorola was the first company to produce a handheld mobile phone. On April 3, 1973, Martin Cooper, a Motorola researcher and executive, made the first mobile telephone call from handheld subscriber equipment, placing a call to Dr. Joel S. Engel of Bell Labs, his rival.”","m_t_83.webp",7,2);

//Found
        this.questionCreator("What does M in I.B.M stand for?","Machines","Memory","Model","Management","Machines","“International Business Machines Corporation is an American multinational technology company headquartered in Armonk, New York, United States, with operations in over 170 countries.”","m_t_84.webp",7,2);

//Found
        this.questionCreator("What does DB stand for?","Database","Data Boot","Driver Base","Driver Boot","Database","“A database is an organized collection of data, stored and accessed electronically. Database designers typically organize the data to model aspects of reality in a way that supports processes requiring information, such as (for example) modelling the availability of rooms in hotels in a way that supports finding a hotel with vacancies.”","m_t_85.webp",7,2);

//Found
        this.questionCreator("What is LCD an acronym of?","Liquid Crystal Display","Liquid Cable Display","Liquid Creative Display","Liquid Clear Display","Liquid Crystal Display","“A liquid-crystal display is a flat-panel display or other electronically modulated optical device that uses the light-modulating properties of liquid crystals”","m_t_86.webp",7,2);

//Found
        this.questionCreator("What is “L” in SQL stand for?","Language","Local","Light","Layer","Language","“SQL is a domain-specific language used in programming and designed for managing data held in a relational database management system, or for stream processing in a relational data stream management system.”","m_t_87.webp",7,2);

//Found
        this.questionCreator("Which of the following game consoles was developed by Sony?","PlayStation","Wii","Xbox","Gameboy","PlayStation","“It is created and owned by Sony Interactive Entertainment since December 3, 1994, with the launch of the original PlayStation in Japan. ... The first handheld game console in the PlayStation series, the PlayStation Portable or PSP, sold a total of 80 million units worldwide by November 2013”","m_t_88.webp",7,2);

//Found
        this.questionCreator("What type of file is .JPG file?","Image file","Movie file","System file","Text file","Image file","“JPEG is a commonly used method of lossy compression for digital images, particularly for those images produced by digital photography. The degree of compression can be adjusted, allowing a selectable tradeoff between storage size and image quality”","m_t_89.webp",7,2);

//Found
        this.questionCreator("In what country is Ericsson’s headquarters located?","Sweden","China","Finland","U.S.A","Sweden","“The company was founded in 1876 by Lars Magnus Ericsson as of 2016 it is headquartered in Stockholm, Sweden”","m_t_90.webp",7,2);

//Found
        this.questionCreator("Errors in computing programs are called?","Bugs","Follies","Mistake","Spam","Bugs","“A software bug is an error, flaw, failure or fault in a computer program or system that causes it to produce an incorrect or unexpected result, or to behave in unintended ways”","m_t_91.webp",7,2);

//Found
        this.questionCreator("Who invented mail system and @ symbol for address?","Raymond Samuel Tomlinson","Ian Murdock","David Bowie","Paul Buchheit","Raymond Samuel Tomlinson","“Ray Tomlinson is the reason your e-mail address includes a '@' symbol. For this reason – and many others – you wouldn't be remiss in calling Tomlinson the inventor of e-mail”","m_t_92.webp",7,2);

//Found
        this.questionCreator("Which one of the following is not a computer language?","LOTUS","BASIC","C++","JAVA","LOTUS","“A spreadsheet program designed for IBM-compatible personal computers by Lotus Corporation in 1982. Lotus 1-2-3 was the first publicly available program to combine graphics, spreadsheet functions and data management (three functions, hence the name).”","m_t_93.webp",7,2);

//Found
        this.questionCreator("What does the letter “S” stand for in the Web terminology “HTTPS”?","Secure","Safe","Short","Shorter","Secure","“Hypertext Transfer Protocol Secure is an extension of the Hypertext Transfer Protocol for secure communication over a computer network, and is widely used on the Internet”","m_t_94.webp",7,2);

//Found
        this.questionCreator("Who is the founder of the Bluetooth?","Ericson","IBM","Apple","Dell","Ericson","“Dr. Jaap Haartsen, who invented Bluetooth while working at Ericsson in the 1990s, has been nominated as a finalist by the European Patent Office in the industry category for its European Inventor Award.”","m_t_95.webp",7,2);

//Found
        this.questionCreator("In context of Computers, FAT stands for?","File Allocation Table","Folder Access Table","File Access Table","Folder Allocation Table","File Allocation Table","FAT stands for \"File Allocation Table,\" which keeps track of all your files and helps the computer locate them on the disk. FAT32 is an improvement to the original FAT system, since it uses more bits to identify each cluster on the disk.’”","m_t_96.webp",7,2);

//Found
        this.questionCreator("GL stand for?","Fourth Generation Language","Fourth Generation Light","Fourth Giving Life","Fourth Giving Light","Fourth Generation Language","“A fourth generation (programming) language (4GL) is a grouping of programming languages that attempt to get closer than 3GLs to human language, form of thinking and conceptualization. 4GLs are designed to reduce the overall time, effort and cost of software development.”","m_t_97.webp",7,2);

//Found
        this.questionCreator("The expert system was developed by which university?","Stanford University","Harvard University","University of Oxford","University of Cambridge","Stanford University","“The first expert system was developed in 1965 by Edward Feigenbaum and Joshua Lederberg of Stanford University in California, U.S. Dendral, as their expert system was later known, was designed to analyze chemical compounds.”","m_t_98.webp",7,2);

//Found
        this.questionCreator("Who is the father of Artificial Intelligence?","John McCarthy","Thomas Edison","George Devol","Thomas Knoll","John McCarthy","“John McCarthy is one of the \"founding fathers\" of artificial intelligence, together with Marvin Minsky, Allen Newell and Herbert A. Simon. McCarthy coined the term \"artificial intelligence\" in 1955, and organized the famous Dartmouth Conference in summer 1956. This conference started AI as a field.”","m_t_99.webp",7,2);



    }

    private void addTechnologyDifficult()
    {

//Found
        this.questionCreator("What was the name of the first home computer to be manufactured?","Altair","Crona","Okaer","Salert","Altair","The first personal computers, introduced in 1975, came as kits: The MITS Altair 8800, followed by the IMSAI 8080, an Altair clone. (Yes, cloning has been around that long!) Both used the Intel 8080 CPU. That was also the year Zilog created the Z-80 processor and MOS Technology produced the 6502.","d_t_1.webp",7,3);

//Found
        this.questionCreator("Which play by Capek introduced the word robot?","R.U.R","R.O.R","R.A.R","R.O.p","R.U.R","The Origin Of The Word \'Robot\' Robot is a relative newcomer to the English language. It was the brainchild of the Czech playwright, novelist and journalist Karel Čapek, who introduced it in his 1920 hit play, R.U.R., or Rossum\'s Universal Robots.","d_t_2.webp",7,3);

//Found
        this.questionCreator("How was Trevor Baylis\'s revolutionary radio powered?","Clockwork","ClockWerk","Clock","RadioClock","Clockwork","Trevor Graham Baylis CBE (13 May 1937 – 5 March 2018) was an English inventor best known for the wind-up radio. The radio, instead of relying on batteries or external electrical source, is powered by the user winding a crank. This stores energy in a spring which then drives an electrical generator.","d_t_3.webp",7,3);

//Found
        this.questionCreator("Sir Jagadis Chandra Bose\'s invention the crescograph measures what?","Plant movements","Animals movements","Insect movements","Human movements","Plant movements","A crescograph is a device for measuring the growth in plants. It was invented in the early 20th century by Sir Jagadish Chandra Bose. The Bose crescograph uses a series of clockwork gears and a smoked glass plate to record the movement of the tip of a plant (or its roots) at magnifications of up to 10,000.","d_t_4.webp",7,3);

//Found
        this.questionCreator("In which islands is Bikini Atoll where the first atomic bombs were tested?","Marshall Islands","Peace Islands","Nake Islands","100 Islands","Marshall Islands","The first two U.S. tests were conducted here. 23 nuclear tests were conducted on this atoll from 1946-1958. Bravo crater: At 15 megatons, the Bravo shot vaporized three islands. The Marshall Islands consist of 29 atolls spread across a sea area of over 700,000 nautical square miles.","d_t_5.webp",7,3);

//Found
        this.questionCreator("What was the codename of the first atomic bomb dropped on Hiroshima?","Little Boy","Little Girl","Young Boy","Wild Girl","Little Boy","Codename \"Little Boy\", the first atomic weapon used in war was dropped on Hiroshima from the B-29 Enola Gay, on this day August 6th 1945. This and the bombing of the city of Nagasaki three days later effectively brought an end to WW2","d_t_6.webp",7,3);

//Found
        this.questionCreator("Edward Salk developed a vaccine against what?","Polio","Post-polio","CGC","AGB","Polio","Jonas Salk. Jonas Edward Salk was an American medical researcher and virologist. He discovered and developed one of the first successful polio vaccines.","d_t_7.webp",7,3);

//Found
        this.questionCreator("Adolf Loos was a designer of what?","Buildings","House","Computer","Cars","Buildings","Adolf Loos (1870-1933) was born in Brno, Moravia to a stone mason who taught his son the importance of the utility of design and helped Adolf appreciate the time and energy that was taken in design. Loos attended college in Austria and served in the Austrian army in 1889.","d_t_8.webp",7,3);

//Found
        this.questionCreator("Who was the first Nobel Prize winner to come from Pakistan?","Abdus Salam","John Otipus","Karl Mantis","Matthew Sanchez","Abdus Salam","“Abdus Salam, \'First Muslim Nobel Laureate\'”, \'The Culture Trip\'. (Abdus Salam was a theoretical physicist who became the first Pakistani and the first Muslim to be awarded the Nobel Prize in the sciences.)","d_t_9.webp",7,3);

//Found
        this.questionCreator("Which company manufactured the first electric razor?","Schick","Click","Electro","E-Razor","Schick","Others followed suit, such as the American manufacturer Col. Jacob Schick who patented their first electric razor in 1930. The Remington Rand Corporation developed the electric razor further, first producing the electric razor in 1937.","d_t_10.webp",7,3);

//Found
        this.questionCreator("How long did Bleriot\'s first flight across the English Channel last?","36 minutes","23 minutes","15 minutes","3 minutes","36 minutes","Bleriot achieved immortality in the Type XI on July 25, 1909, when he made the first airplane crossing of the English Channel, covering the 40 kilometers between Calais and Dover in 36 minutes, 30 seconds. It was not the longest flight to date either in duration or distance.","d_t_11.webp",7,3);

//Found
        this.questionCreator("Year that Land cameras are manufactured.","1947-1983","1983-1985","1986-1991","1991-1997","1947-1983","Land Cameras are instant cameras with self-developing film named after their inventor, Edwin Land, who created them while working for Research Row in Boston, Massachusetts. They were manufactured by Polaroid between the years of 1947 and 1983.","d_t_12.webp",7,3);

//Found
        this.questionCreator("Leslie Rogge was the first person to be arrested due to what?","The Internet","The Carnival","The DeepWeeb","The Road","The Internet","Leslie Isben Rogge is most notable as the first fugitive captured by the FBI’s website. His story dates back to when the Internet was first being recognized as a crime-fighting tool. Rogge committed over 30 bank robberies. He was put on the FBI’s Most Wanted List in 1990 but fled the United States about two years later.","d_t_13.webp",7,3);

//Found
        this.questionCreator("Who led the team which invented transistors in the 1940s?","Shockley","Mercia","Bening","Neil","Shockley","In 1956 John Bardeen, Walter Houser Brattain, and William Bradford Shockley were honored with the Nobel Prize in Physics \"for their researches on semiconductors and their discovery of the transistor effect\". Twelve people are mentioned as directly involved in the invention of the transistor in the Bell Laboratory.","d_t_14.webp",7,3);

//Found
        this.questionCreator("The 2013 documentary \"TPB AFK\" was about what embattled website?","Pirate Bay","Silk Road","BotNet","MySpace","Pirate Bay","The Pirate Bay Away From Keyboard documentary, which tells the story behind the embattled Swedish Pirate Bay project, is now available on YouTube.","d_t_15.webp",7,3);

//Found
        this.questionCreator("Bill Gates and Mark Zuckerberg both appeared in a 2013 PSA for what nonprofit?","Code.org","Computation.org","Server.org","Byte.org","Code.org","After recruiting Will.i.am to help encourage students to explore the world of code, Facebook\'s Mark Zuckerberg and Microsoft chairman Bill Gates are preparing to take a more hands-on approach. As part of their mission with Code.org.","d_t_16.webp",7,3);

//Found
        this.questionCreator("In 2013 the Supreme Court ruled that what could not be patented?","DNA","Computer Code","Cell phones","Selfies","DNA","The Supreme Court\'s ruling did allow that DNA manipulated in a lab is eligible to be patented because DNA sequences altered by humans are not found in nature. The Court specifically mentioned the ability to patent a type of DNA known as complementary DNA (cDNA). This synthetic DNA is produced from the molecule that serves as the instructions for making proteins (called messenger RNA).","d_t_17.webp",7,3);

//Found
        this.questionCreator("In 2013, which company unveiled plans to deliver packages using drones?","Amazon","Ebay","Newegg","Google","Amazon","On June 21, the FAA finalized rules for commercial UAVs, or drones. While commercial applications for drones vary from aerial photography to package delivery, especially exciting is Amazon\'s Prime Air, promising to deliver a five-pound package to your doorstep (or back yard) in 30 minutes or less.","d_t_18.webp",7,3);

//Found
        this.questionCreator("Which hacker group lobbied in 2013 to have denial of service attacks made a legal form of protest?","Anonymous","The Vipers","Thunder Ninja Kids","The Dirty 12","Anonymous","Overloading a targeted website with too much traffic, says Dylan K, is “no different than any ‘occupy’ protest.” According to him and the roughly 1,100 cosigners, there is much common ground between the two. “Instead of a group of people standing outside a building to occupy the area, they are having their computer occupy a website to slow (or deny) service of that particular website for a short time,” he says.","d_t_19.webp",7,3);

//Found
        this.questionCreator("million accounts were compromised when what company was hacked in 2013?","Adobe","Google","Microsoft","Facebook","Adobe","On Octobert 3 , 2013 Adobe announced that hackers have managed to obtain information on approximately 2.9 million of its customers that have downloaded its software, including customer IDs, encrypted passwords, customer names, encrypted credit/debit card numbers, expiration dates, and other information on customer orders.","d_t_20.webp",7,3);

//Found
        this.questionCreator("Which company became the first to unveil a 5 Ghz processor in 2013?","AMD","Microsoft","Nvidia","Elcor","AMD","AMD was the first to break the 1 GHz barrier in May of 2000 and continues to set the standard in technology innovation including the first Windows compatible 64-bit PC processor and the first native dual-core and quad-core processors. AMD also introduced the first APU (unifying CPU and Radeon graphics on the same chip) and the first x86 quad-core SoC, continuing forward with HSA architectures and programming models.","d_t_21.webp",7,3);

//Found
        this.questionCreator("It was announced in 2013 that YouTube and what other website accounted for 50% of North America internet traffic?","Netflix","Tumblr","Hotmail","Yahoo","Netflix","In September, Netflix announced that they would be allowing all of their subscribers to access Super HD content in the highest bit-rate video offered, regardless of whether their Internet Service Provider (ISP) uses the Netflix Open Connect CDN on their network.","d_t_22.webp",7,3);

//Found
        this.questionCreator("The first mobile phones using what operating system were launched in 2013?","Firefox OS","Opera OS","Netscape OS","Hydra OS","Firefox OS","Research in Motion has Android, Windows Phone, and iOS firmly in its sights, but Mozilla is targeting the budget sector with its Firefox OS, therefore gunning almost exclusively for cheap Android phones.","d_t_23.webp",7,3);

//Found
        this.questionCreator("Which recently-launched email software was acquired by Dropbox in 2013?","Mailbox","Mailcrate","MailKing","MailBolt","Mailbox","On March 2013 Dropbox acquires Mailbox, a sleek email platform specifically for mobile users.","d_t_24.webp",7,3);

//Found
        this.questionCreator("Which bi-wing airplane was built in Troy, Ohio?","Waco","Fairchild","Stinson","Piper","Waco","The Waco Aircraft Company (WACO) was an aircraft manufacturer located in Troy, Ohio, United States. Between 1920 and 1947, the company produced a wide range of civilian biplanes.","d_t_25.webp",7,3);

//Found
        this.questionCreator("Ford didn\'t make the engines for the first model T\'s. Which one of the following did?","The Dodge Brothers","Farm Machinery Corporation","Ransom Olds","Louis Chevrolet","The Dodge Brothers","Dodge is an American brand of automobile manufactured by FCA US LLC, based in Auburn Hills, Michigan. Dodge vehicles currently include the lower-priced badge variants of Chrysler-badged vehicles as well as performance cars, though for much of its existence Dodge was Chrysler\'s mid-priced brand above Plymouth","d_t_26.webp",7,3);

//Found
        this.questionCreator("Who was the last of the original Mercury astronauts to go into space?","Deke Slayton","Walter Schirra","Gordon Cooper","Fred Haise","Deke Slayton","American astronauts were Scott Carpenter, Gordon Cooper, John Glenn, Gus Grissom, Wally Schirra, Alan Shepard, and Deke Slayton. Members of the group flew on all classes of NASA manned orbital spacecraft of the 20th century — Mercury, Gemini, Apollo, and the Space Shuttle.","d_t_27.webp",7,3);

//Found
        this.questionCreator("In what year did Ford switch to electronic ignition?","1973","1969","1981","1978","1973","The Duraspark II is a Ford electronic ignition system. Ford Motor Company began using electronic ignitions in 1973 with the Duraspark electronic ignition system and introduced the Duraspark II system in 1976.","d_t_28.webp",7,3);

//Found
        this.questionCreator("In 2008, what was the most common computer port?","USB","URL","Ethernet","UDP","USB","The USB 3.0 specification was published on 12 November 2008. Its main goals were to increase the data transfer rate (up to 5 Gbit/s), decrease power consumption, increase power output, and be backward compatible with USB 2.0. USB 3.0 includes a new, higher speed bus called SuperSpeed in parallel with the USB 2.0 bus. For this reason, the new version is also called SuperSpeed. The first USB 3.0 equipped devices were presented in January 2010.","d_t_29.webp",7,3);

//Found
        this.questionCreator("What is a free toolbar with online advertisements an example of?","Adware","Freeware","Adsads","Softads","Adware","Adware, or advertising-supported software, is software that generates revenue for its developer by automatically generating online advertisements in the user interface of the software or on a screen presented to the user during the installation process.","d_t_30.webp",7,3);

//Found
        this.questionCreator("A script for e-mail replying is called what?","Autoresponder","Spam","SMTP","Forwarding","Autoresponder","An autoresponder is a script that automatically replies to emails sent to a specific email address. It may be used for away messages, email confirmations, or for several other purposes. ... For example, you can add a rule that automatically replies to messages sent to a specific email address.","d_t_31.webp",7,3);

//Found
        this.questionCreator("What data, sent to a browser, tracks website use?","A Cookie","A Worm","An Applet","A Spider","A Cookie","Cookies are small files which are stored on a user\'s computer. They are designed to hold a modest amount of data specific to a particular client and website, and can be accessed either by the web server or the client computer.","d_t_32.webp",7,3);

//Found
        this.questionCreator("Core Force is an example of what protective software?","Firewall","Subnet Mask","Breakwall","Hard Drive","Firewall","In computing, a firewall is a network security system that monitors and controls incoming and outgoing network traffic based on predetermined security rules. A firewall typically establishes a barrier between a trusted internal network and untrusted external network, such as the Internet.","d_t_33.webp",7,3);

//Found
        this.questionCreator("FTP stands for file transfer what?","Protocol","Plant","Packet","Peripheral","Protocol","The File Transfer Protocol (FTP) is a standard network protocol used for the transfer of computer files between a client and server on a computer network.","d_t_34.webp",7,3);

//Found
        this.questionCreator("What is a bridge between two networks?","Gateway","Protocol","IP Address","MAC Address","Gateway","A gateway is a node (router) in a computer network, a key stopping point for data on its way to or from other networks. Thanks to gateways, we are able to communicate and send data back and forth. The Internet wouldn\'t be any use to us without gateways (as well as a lot of other hardware and software).","d_t_35.webp",7,3);

//Found
        this.questionCreator("What protocol did Tim Berners-Lee invent in 1989?","HTTP","SSL","HTML","TCP/IP","HTTP","In 1989, Tim Berners-Lee invented the World Wide Web, an Internet-based hypermedia initiative for global information sharing while at CERN, the European Particle Physics Laboratory. He wrote the first web client and server in 1990. His specifications of URIs, HTTP and HTML were refined as web technology spread.","d_t_36.webp",7,3);

//Found
        this.questionCreator("What body managed domain-name allocations until 1998?","InterNIC","Google","Live.com","Yahoo","InterNIC","The Network Information Center, also known as InterNIC from 1993 until 1998, was the organization primarily responsible for Domain Name System domain name allocations and X.500 directory services.","d_t_37.webp",7,3);

//Found
        this.questionCreator("What type of company provides access to the Internet?","ISP","Web Host","Ethernet","DNS Hosting Service","ISP","An ISP (Internet service provider) is a company that provides individuals and other companies access to the Internet and other related services such as Web site building and virtual hosting.","d_t_38.webp",7,3);

//Found
        this.questionCreator("What syntax-based language integrates into HTML pages?","JavaScript","C++","Firmware","Ruby","JavaScript","JavaScript, often abbreviated as JS, is a high-level, interpreted programming language. It is a language which is also characterized as dynamic, weakly typed, prototype-based and multi-paradigm. Alongside HTML and CSS, JavaScript is one of the three core technologies of the World Wide Web.","d_t_39.webp",7,3);

//Found
        this.questionCreator("Which of the following helps translate domains to IPs?","Name Server","URL","WHOIS","HTTP","Name Server","Nameserver is a server on the internet specialized in handling queries regarding the location of a domain name\'s various services. Nameservers are a fundamental part of the Domain Name System (DNS). They allow using domains instead of IP addresses.","d_t_40.webp",7,3);

//Found
        this.questionCreator("Attempting to steal personal data through emails is called what?","Phishing","Fraud","Identity Theft","Spamming","Phishing","A phishing website (sometimes called a \"spoofed\" site) tries to steal your account password or other confidential information by tricking you into believing you\'re on a legitimate website. You could even land on a phishing site by mistyping a URL (web address).","d_t_41.webp",7,3);

//Found
        this.questionCreator("What adjustments create higher search engine ranking?","SEO","Keywording","Web Directioning","TEO","SEO","Search engine optimization is a methodology of strategies, techniques and tactics used to increase the amount of visitors to a website by obtaining a high-ranking placement in the search results page of a search engine (SERP) — including Google, Bing, Yahoo and other search engines.","d_t_42.webp",7,3);

//Found
        this.questionCreator("What is the email equivalent of junk mail called?","Spam","Packet","Flash","Attachments","Spam","Email spam, also known as junk email, is unsolicited messages sent in bulk by email (spamming).","d_t_43.webp",7,3);

//Found
        this.questionCreator("What \"Post-it\" note company created \"Scotchgard\"?","3M","Ford","Kraft Foods","Frigidaire","3M","Scotchgard is a 3M brand of products, a stain and durable water repellent applied to fabric, furniture, and carpets to protect them from stains. The original formula for Scotchgard was discovered accidentally in 1952 by 3M chemists Patsy Sherman and Samuel Smith.","d_t_44.webp",7,3);

//Found
        this.questionCreator("He launched a public bus service in Paris, which he called \"carriages,\" in 1662.","Blaise Pascal","Randy Oz","Christian Ashton","Washington Merlin","Blaise Pascal","Blaise Pascal launched a public bus service in Paris, which he called \"carriages,\" in 1662. It ceased operation after 15 years and buses did not appear again until horse-powered multi-person carriages emerged in Paris again in about 1819 or 1820. \"Omnibus\" is a Latin word meaning \"for all.\"","d_t_45.webp",7,3);

//Found
        this.questionCreator("What is the non-alphabetical order of letters on a keyboard called?","QWERTY","Joseph Style","Quick Brown Fox","Allen Fix","QWERTY","QWERTY is a keyboard design for Latin-script alphabets. The name comes from the order of the first six keys on the top left letter row of the keyboard. The QWERTY design is based on a layout created for the Sholes and Glidden typewriter and sold to E. Remington and Sons in 1873.","d_t_46.webp",7,3);

//Found
        this.questionCreator("How many prototypes came before Dyson\'s \"Dual Cyclone\"?","5,127","314","68","402","5,127","In it, Dyson breaks down the creative process that went into creating the bag-free, \"cyclone technology\" design, including the 15 years and 5,127 prototypes it took before the first model, DC01, would ultimately prove successful in 1993. Fifteen years! For a vacuum!","d_t_47.webp",7,3);

//Found
        this.questionCreator("Which popular app added 15-second video capabilities in 2013?","Instagram","Vimeo","Twitter","Vine","Instagram","At a press event at Facebook’s Menlo Park headquarters, Instagram’s co-founder and CEO Kevin Systrom debuted a new feature called, simply, “Video On Instagram.” This lets people create 15-second videos to share on the service. The feature includes simple editing capabilities as well as 13 new filters, which were specially created for video.","d_t_48.webp",7,3);

//Found
        this.questionCreator("Date that Yahoo Messenger Launched.","January 7, 1997","Febuary 3, 1992","December 23, 1996","March 13, 2000","January 7, 1997","Yahoo! Chat was first launched on January 7, 1997, as it was confirmed to be a feature on the very first release of Yahoo! Pager. On March 9, 1998, the first public version of Yahoo! Pager was released, with Yahoo! Chat among its features.","d_t_49.webp",7,3);

//Found
        this.questionCreator("Date when the final beta of Android PIE version released.","July 25, 2018","June 14, 2018","January 15, 2018","November 23, 2018","July 25, 2018","The final beta of Android P was released on July 25, 2018. Android \"P\" was officially released on August 6, 2018 as \"Android 9 Pie\" and was initially available for Google Pixel devices and the OnePlus 6. The Sony Xperia XZ3 was the first device with Android Pie pre-installed.","d_t_50.webp",7,3);

//Found
        this.questionCreator("When did Dell start making computers?","1980s","1960s","1970s","1950s","1980s","Born on February 23, 1965, in Houston, Texas, Michael Dell helped launch the personal computer revolution in the 1980s with the creation of the Dell Computer Corporation (now known as Dell Inc.), which began in the founder\'s dorm room at the University of Texas and quickly blossomed into a megawatt computer company.","d_t_51.webp",7,3);

//Found
        this.questionCreator("In which decade was the American Institute of Electrical Engineers (AIEE) founded?","Year-1880s","1850s","1930s","1950s","Year-1880s","The IEEE (Institute of electrical and Electronics Engineer) was formed in 1963 by the merge of the institute of radio Engineer (IRE, founded 1912) and the American institute of electrical engineers (AIEE, founded 1884)","d_t_52.webp",7,3);

//Found
        this.questionCreator("In which decade with the first transatlantic radio broadcast occur?","Year-1900","1850s","1860s","1870s","Year-1900","On December 12, 1901, a radio transmission received by Gulielmo Marcon resulted in the first transmission of a transatlantic wireless signal (Morse code) from Poldhu Cornwall, To St. John’s Newfoundland","d_t_53.webp",7,3);

//Found
        this.questionCreator("In which decade was the SPICE simulator introduced?","1970's","1980's","1950's","1960's","1970's","SPICE (Simulation Program with Integrated Circuit Emphasis) was introduced in May 1972 by University of Berkeley California.","d_t_54.webp",7,3);

//Found
        this.questionCreator("Who is the present CEO of ORACLE?","Mark Hurd & Safra Catz","Larry Ellison","Paul Allen","Richard Stallman","Mark Hurd & Safra Catz","“Ellison, who is believed to be the fifth-wealthiest man in the world, will be replaced by Mark Hurd and Safra Catz, Oracle said. In an unusual move, Hurd and Catz will both be named CEO of the company—not co-CEOs. Ellison will become executive chairman and chief technology officer September 18, 2014.”","d_t_55.webp",7,3);

//Found
        this.questionCreator("Who invented PERL programming language?","Larry Wall","Sergey Brin","Sam Pitroda","Aaron Swartz","Larry Wall","“Larry Wall (born September 27, 1954) is an American Computer programmer and author. He created the PERL programming language.”","d_t_56.webp",7,3);

//Found
        this.questionCreator("Nintendo was founded after the year of?","Year 1889","Year 1990","Year 1900","Year 1880","Year 1889","“Nintendo is founded, September 23, 1889. Founded in 1889 by Fusajiro Yamauchi in Kyoto, Japan, The Nintendo Playing Card Company was a small business that would go on to revolutionize gaming around the world”","d_t_57.webp",7,3);

//Found
        this.questionCreator("Number of bit used by the IPv6 address?","One hundred twenty eight bit","Sixty four bit","Thirty two bit","Two hundred fifty six bit","One hundred twenty eight bit","“In IPv4, each octet consists of a decimal number ranging from 0 to 255. These numbers are typically separated by periods. In IPv6, addresses are expressed as a series of eight 4-character hexadecimal numbers, which represent 16 bits each (for a total of 128 bits)”","d_t_58.webp",7,3);

//Found
        this.questionCreator("Which one is the first web browser invented in 1990?","Nexus","Internet Explorer","Mosaic","Mozilla","Nexus","“The first web browser was invented in 1990 by Tim Berners-Lee. It was called WorldWideWeb (no spaces) and was later renamed Nexus”","d_t_59.webp",7,3);

//Found
        this.questionCreator("First computer virus is known as?","Creeper virus","Rabbit","Elk Cloner","SCA virus","Creeper virus","“Creeper virus is a computer virus that is most commonly recognized as the first computer virus. In 1971, Bob Thomas at BBN created Creeper as an experimental self-duplicating program that was intended not to inflict damage on, but to illustrate a mobile application. Creeper corrupted DEC PDP-10 computers operating on the TENEX operating system by messing around the installed printers, displaying the message “I’m the creeper, catch me if you can!””","d_t_60.webp",7,3);

//Found
        this.questionCreator("How many computer languages are in use?","Language 2000","Language 2500","Language 3000","Language 4000","Language 2000","“There are about 2,000 computer languages in active use, whereas there were only 15 in use in 1970”","d_t_61.webp",7,3);

//Found
        this.questionCreator("When was the DVD introduced?","Year 1995","Year 1997","Year 2000","Year 1889","Year 1995","“Compact discs that stored sound were introduced in 1982. By the mid-1980’s new discs called CD-ROMs could store pictures and computer programs”","d_t_62.webp",7,3);

//Found
        this.questionCreator("What was the first graphical browser for the World Wide Web?","Mosaic","Nexus","Internet Explorer","Mozilla","Mosaic","“Invented by Marc Andreesen in 1993, Mosaic was the first graphical browser for the World Wide Web”","d_t_63.webp",7,3);

//Found
        this.questionCreator("What game was Deep Blue skilled at?","Chess","Basketball","Darts","Volleyball","Chess","“Deep Blue, a computer built by IBM, was a whiz at chess. In fact, it beat world chess champion Garry Kasparov twice”","d_t_64.webp",7,3);

//Found
        this.questionCreator("When was the Sony PlayStation 4 released?","Year 2013","Year 1995","Year 2016","Year 2010","Year 2013","“The console was released on November 15, 2013, in the United States and Canada, followed by further releases on November 29, 2013. By the end of 2013, the PS4 was launched in more European, Asian and South American countries The PS4 released in Japan at ¥39,980 on February 22, 2014.”","d_t_65.webp",7,3);

//Found
        this.questionCreator("Where did the world’s first underground railway system open?","London","France","Hungary","U.S.A","London","“The London Underground first opened as an \"underground railway\" in 1863 and its first electrified underground line opened in 1890, making it the world's first metro system.”","d_t_66.webp",7,3);

//Found
        this.questionCreator("What was the first 32 bit microprocessor from Intel?","Intel 386DX","Intel Pentium 80 MHz","Intel 486SI","Intel Athlon XP","Intel 386DX","“The Intel 80386, also known as i386 or just 386, is a 32-bit microprocessor introduced in 1985. The first versions had 275,000 transistors and were the CPU of many work stations and high-end personal computers of the time.”","d_t_67.webp",7,3);

//Found
        this.questionCreator("Which inventor introduced the telephone, in 1876?","Graham Bell","Thomas Watson","Michael Faraday","Thomas Edison","Graham Bell","“Alexander Graham Bell was a Scottish-born scientist, inventor, engineer, and innovator who is credited with inventing and patenting the first practical telephone. He also founded the American Telephone and Telegraph Company in 1885”","d_t_68.webp",7,3);

//Found
        this.questionCreator("Who was first to fly over the English Channel?","Louis Blériot","Harry Houdini","Howard Hughes","Charles Lindbergh","Louis Blériot","“First airplane flight across the English Channel: Louis Blériot crossed the Channel on July 25, 1909, winning the Daily Mail prize of £1,000.”","d_t_69.webp",7,3);

//Found
        this.questionCreator("Which of the following game consoles was developed by Nintendo?","Gameboy","PlayStation","Xbox","Saturn","Gameboy","“In 1989, Nintendo released the Game Boy, which became the first handheld console to sell in large numbers”","d_t_70.webp",7,3);

//Found
        this.questionCreator("What company developed “The Legends of Zelda” series of video game?","Nintendo","Ubisoft","Electronic Arts","Microsoft","Nintendo","“The Legend of Zelda: A Link to the Past is an action-adventure video game developed and published by Nintendo for the Super Nintendo Entertainment System video game console. It is the third installment in The Legend of Zelda series and was released in 1991 in Japan and 1992 in North America and Europe”","d_t_71.webp",7,3);

//Found
        this.questionCreator("Who invented the diesel engine?","Rudolf Diesel","Shawn Diesel","Bill Diesel","Vin Diesel","Rudolf Diesel","“Rudolf Christian Karl Diesel 18 March 1858 – 29 September 1913) was a German inventor and mechanical engineer famous for the invention of the diesel engine, and for his mysterious death at sea. Diesel was the subject of the 1942 film Diesel”","d_t_72.webp",7,3);

//Found
        this.questionCreator("Which American Computer Company is also known by the nick name “Big blue”?","IBM","Microsoft","Apple","Compaq Corporation","IBM","“International Business Machines Corporation, perhaps better known by its acronym IBM, is called the Big Blue. Among the blue-chip stocks on the US stock market, IBM was a steady and consistently high performer over the years”","d_t_73.webp",7,3);

//Found
        this.questionCreator("What is the full form of Li-Fi network?","Light Fidelity","Laser Frequency","Limitless finite","Light field","Light Fidelity","“Li-Fi is a technology for wireless communication between devices using light to transmit data and position. In its present state only LED lamps can be used for the transmission of visible light”","d_t_74.webp",7,3);

//Found
        this.questionCreator("Who considered as the father of computing?","Charles Babbage","Von Neumann","Graham Bell","Claude Shannon","Charles Babbage","“Charles Babbage was considered to be the father of computing after his invention and concept of the Analytical Engine in 1837. The Analytical Engine contained an Arithmetic Logic Unit (ALU), basic flow control, and integrated memory; hailed as the first general-purpose computer concept”","d_t_75.webp",7,3);

//Found
        this.questionCreator("Who is known as the father of SMS?","Matti Makkonen","Samir Bhatia","Anuj Gupta","Thomas Kurian","Matti Makkonen","“Father of SMS passes away. Matti Makkonen, known as the father of SMS after developing the idea of sending messages via mobile networks, has died. LONDON Matti Makkonen, known as the father of SMS after developing the idea of sending messages via mobile networks, has died”","d_t_76.webp",7,3);

//Found
        this.questionCreator("Which company bought the popular video teleconferencing software ‘Skype’","Microsoft","Google","Accenture","Oracle","Microsoft","“Microsoft bought Skype in May 2011 for $8.5 billion. Skype division headquarters are in Luxembourg, but most of the development team and 44% of all the division's employees are still situated in Tallinn and Tartu, Estonia.”","d_t_77.webp",7,3);

//Found
        this.questionCreator("What is the port number of POP3?","Port 110","Port 58","Port 80","Port 21","Port 110","“Post Office Protocol version 3 (POP3) is a standard mail protocol used to receive emails from a remote server to a local email client”","d_t_78.webp",7,3);

//Found
        this.questionCreator("IC chips for computer are usually made of?","Silicon","Silver","Aluminum","Copper","Silicon","“An integrated circuit or monolithic integrated circuit (also referred to as an IC, a chip, or a microchip) is a set of electronic circuits on one small flat piece (or \"chip\") of semiconductor material, normally silicon”","d_t_79.webp",7,3);

//Found
        this.questionCreator("Which one of the following is the founder of ‘WikiLeaks’","Julian Assange","Evans Williams","Jack Dorsey","Noah Glass","Julian Assange","“Julian Paul Assange is an Australian computer programmer and the editor of WikiLeaks. Assange founded WikiLeaks in 2006, but came to international attention in 2010, when WikiLeaks published a series of leaks provided by Chelsea Manning”","d_t_80.webp",7,3);

//Found
        this.questionCreator("ASCII has how many codes?","ASCII 128","ASCII64","ASCII256","ASCII382","ASCII 128","“ASCII (American Standard Code for Information Interchange) is the most common format for text files in computers and on the Internet. In an ASCII file, each alphabetic, numeric, or special character is represented with a 7-bit binary number (a string of seven 0s or 1s). 128 possible characters are defined”","d_t_81.webp",7,3);

//Found
        this.questionCreator("Logarithm table was invented by?","John Napier","John Douglas","John Doe","John Harrison","John Napier","“John Napier invented logarithms, but many other scientists and mathematicians helped develop Napier's logarithms to the system we use today. The first table of common logarithms was compiled by the English mathematician Henry Briggs”","d_t_82.webp",7,3);

//Found
        this.questionCreator("UPS stands for?","Uninterruptible Power Supply","Uninterruptible Power Standby","Universal Power Supply","Universal Power","Uninterruptible Power Supply","“An uninterruptible power supply or uninterruptible power source (UPS) is an electrical apparatus that provides emergency power to a load when the input power source or mains power fails”","d_t_83.webp",7,3);

//Found
        this.questionCreator("Who is known as the inventor of Computer mouse?","Douglas Engelbart","Herman Hollerith","Tom Cranston","Jack Kilby","Douglas Engelbart","“Jack Kilby and Robert Noyce invented the integrated circuit, which is otherwise known as the chip. Invented by Douglas Engelbart of Stanford Research Center in 1964, the first prototype computer mouse was made to use with a graphical user interface 'windows'.”","d_t_84.webp",7,3);

//Found
        this.questionCreator("Which of the following is not a component in multimedia?","Pen Drive","Video","Data","Audio","Pen Drive","“A USB flash drive, also variously known as a thumb drive, pen drive, gig stick, flash stick, jump drive, disk key, disk on key flash-drive, memory stick USB stick or USB memory, is a data storage device that includes flash memory with an integrated USB interface”","d_t_85.webp",7,3);

//Found
        this.questionCreator("Caches are usually built out of?","SRAMS","DRAMS","PROM","EEPROM","SRAMS","“The Intel 80486 microprocessor, for example, contains an 8K memory cache, and the Pentium has a 16K cache. Such internal caches are often called Level 1 (L1) caches. Most modern PCs also come with external cache memory, called Level 2 (L2) caches. Like L1 caches, L2 caches are composed of SRAM but they are much larger.”","d_t_86.webp",7,3);

//Found
        this.questionCreator("Where are cookies stored?","On client","In HTML","In web.xml","None of these","On client","“A cookie is information stored on your computer by a website you visit. In some browsers, each cookie is a small file, but in Firefox, all cookies are stored in a single file, located in the Firefox profile folder. Cookies often store your settings for a website, such as your preferred language or location”","d_t_87.webp",7,3);

//Found
        this.questionCreator("Who invented the radio?","Guglielmo Marconi","Sanjay Mehrotra","Eli Harari","Jack Yuan","Guglielmo Marconi","“Guglielmo Marconi, 1st Marquis of Marconi; 25 April 1874 – 20 July 1937) was an Italian inventor and electrical engineer known for his pioneering work on long-distance radio transmission and for his development of Marconi's law and a radio telegraph system”","d_t_88.webp",7,3);

//Found
        this.questionCreator("EPROM stand for?","Erasable Programmable Read Only Memory","Enter Programmable Read Only Memory","Execute Program Read Only Memory","Erasable Program Read Only Memory","Erasable Programmable Read Only Memory","“An EPROM, or erasable programmable read-only memory, is a type of memory chip that retains its data when its power supply is switched off”","d_t_89.webp",7,3);

//Found
        this.questionCreator("Who invented Photoshop?","Thomas Knoll","Alexander Popov","Samuel Morse","Thomas Edison","Thomas Knoll","“Photoshop was developed in 1987 by the American brothers Thomas and John Knoll, who sold the distribution license to Adobe Systems Incorporated in 1988.Thomas Knoll, a PhD student at the University of Michigan, began writing a program on his Macintosh Plus to display grayscale images on a monochrome display.”","d_t_90.webp",7,3);

//Found
        this.questionCreator("Who invented the first robot in the world?","George Devol","Thomas Edison","Samuel Morse","Alexander Popov","George Devol","“In 1954 George Devol invented the first digitally operated and a programmable robot called the Unimate. In 1956, Devol and his partner Joseph Engelberger formed the world's first robot company. In 1961, the first industrial robot, Unimate, went online in a General Motors automobile factory in New Jersey”","d_t_91.webp",7,3);

//Found
        this.questionCreator("Who invented Instagram?","Kevin Systrom","Larry page","Sergey Brin","Biz Stone","Kevin Systrom","“In 2010, Systrom co founded the photo-sharing and, later, video-sharing social networking service Instagram with Mike Krieger in San Francisco, California”","d_t_92.webp",7,3);

//Found
        this.questionCreator("Who is the CEO of Youtube?","Susan Wojcicki","Jawed karim","Anne Wojcicki","Steve Chen","Susan Wojcicki","“Susan Diane Wojcicki is an American technology executive. She has been the CEO of YouTube since February 2014. She is from Los Altos, California, and has a net worth of $410 million”","d_t_93.webp",7,3);

//Found
        this.questionCreator("Who is the founder of Pinterest?","Ben Silbermann","Brian Chesky","Drew Houston","John Collison","Ben Silbermann","“Ben Silbermann (born 1982)[1] is an American Internet entrepreneur who is the co-founder and CEO of Pinterest, a virtual pinboard which lets users organize images, links, recipes and other things”","d_t_94.webp",7,3);

//Found
        this.questionCreator("Who is the founder of Dropbox?","Drew Houston","John Collison","Brian Chesky","Ben Silbermann","Drew Houston","“Dropbox was founded in 2007 by MIT students Drew Houston and Arash Ferdowsi as a startup company, with initial funding from seed accelerator Y Combinator”","d_t_95.webp",7,3);

//Found
        this.questionCreator("PC/AT stands for?","Personal Computer Advance Technology","Programming Computer Advance Technology","Program Create Advance Teachnology","Personal Cite Advance technology","Personal Computer Advance Technology","“The IBM Personal Computer AT, more commonly known as the IBM AT and also sometimes called the PC AT or PC/AT, was IBM's second-generation PC, designed around the 6 MHz Intel 80286 microprocessor and released in 1984 as System Unit 5170.”","d_t_96.webp",7,3);

//Found
        this.questionCreator("Word length of Super computers?","Sixty Four bits","Sixty Three bits","Eighty bits","Ninety bits","Sixty Four bits","“\"Word size\" refers to the number of bits processed by a computer's CPU in one go (these days, typically 32 bits or 64 bits).”","d_t_97.webp",7,3);

//Found
        this.questionCreator("Who is the founder of tik tok?","Zhang Yiming","Yi Xing","Zhang Heng","Bi Sheng","Zhang Yiming","“TikTok, also known as Douyin (Chinese: 抖音短视频; pinyin: Dǒuyīn duǎnshìpín; literally \"vibrato short video\") or Tik Tok, is a Chinese music video platform and social network that was launched in September 2016 by Zhang Yiming, founder of Toutiao.”","d_t_98.webp",7,3);

//Found
        this.questionCreator("Who is the CEO of Microsoft?","Satya Nadella","John Collison","Drew Houston","Brian Chesky","Satya Nadella","“Satya Nadella. Satya Narayana Nadella (born 19 August 1967) is an American business executive of Indian descent. He is the Chief Executive Officer (CEO) of Microsoft, succeeding Steve Ballmer in 2014”","d_t_99.webp",7,3);

//Found
        this.questionCreator("ISDN stands for?","Integrated Services Digital Networks","Integrated Service Drive Networks","Income Services Drive Networks","Integrated Success Digital Networks","Integrated Services Digital Networks","“ISDN stands for Integrated Services Digital Network. It is a design for a completely digital telephone/telecommunications network. It is designed to carry voice, data, images, and video, everything you could ever need.”","d_t_100.webp",7,3);


    }

    public void addPlantsEasy()
    {
        this.questionCreator("1Which manufactured released the galaxy tab series?","Samsung","Sony","Motorola","LG","Samsung","“The Samsung Galaxy Tab is a line of Android-based tablet computers produced by Samsung Electronics. The first model in the series, the 7-inch Samsung Galaxy Tab, was presented to the public on 2 September 2010 at the IFA in Berlin”","e_t_96.webp",2,1);
//Found
        this.questionCreator("2Saving a file from the internet onto your desktop is called?","Downloading","Uploading","Transferring","Storing","Downloading","“In computer networks, to download (abbreviation DL) is to receive data from a remote system, typically a server such as a web server, an FTP server, an email server, or other similar systems. This contrasts with uploading, where data is sent to a remote server”","e_t_97.webp",2,1);
//Found
        this.questionCreator("3Which is an Input device?","Mouse","Printer","Monitor","Speaker","Mouse","“In computing, an input device is a piece of computer hardware equipment used to provide data and control signals to an information processing system such as a computer or information appliance. Examples of input devices include keyboards, mouse, scanners, digital cameras and joysticks”","e_t_98.webp",2,1);
    }

    public void addPlantsModerate()
    {
        this.questionCreator("1In context of Computers, FAT stands for?","File Allocation Table","Folder Access Table","File Access Table","Folder Allocation Table","File Allocation Table","FAT stands for \"File Allocation Table,\" which keeps track of all your files and helps the computer locate them on the disk. FAT32 is an improvement to the original FAT system, since it uses more bits to identify each cluster on the disk.’”","m_t_96.webp",2,2);
//Found
        this.questionCreator("2GL stand for?","Fourth Generation Language","Fourth Generation Light","Fourth Giving Life","Fourth Giving Light","Fourth Generation Language","“A fourth generation (programming) language (4GL) is a grouping of programming languages that attempt to get closer than 3GLs to human language, form of thinking and conceptualization. 4GLs are designed to reduce the overall time, effort and cost of software development.”","m_t_97.webp",2,2);
//Found
        this.questionCreator("3The expert system was developed by which university?","Stanford University","Harvard University","University of Oxford","University of Cambridge","Stanford University","“The first expert system was developed in 1965 by Edward Feigenbaum and Joshua Lederberg of Stanford University in California, U.S. Dendral, as their expert system was later known, was designed to analyze chemical compounds.”","m_t_98.webp",2,2);
    }

    public void addPlantsDifficult()
    {
        this.questionCreator("1Word length of Super computers?","Sixty Four bits","Sixty Three bits","Eighty bits","Ninety bits","Sixty Four bits","“\"Word size\" refers to the number of bits processed by a computer\\\'s CPU in one go (these days, typically 32 bits or 64 bits).”","d_t_97.webp",2,3);
//Found
        this.questionCreator("2Who is the founder of tik tok?","Zhang Yiming","Yi Xing","Zhang Heng","Bi Sheng","Zhang Yiming","“TikTok, also known as Douyin (Chinese: 抖音短视频; pinyin: Dǒuyīn duǎnshìpín; literally \"vibrato short video\") or Tik Tok, is a Chinese music video platform and social network that was launched in September 2016 by Zhang Yiming, founder of Toutiao.”","d_t_98.webp",2,3);
//Found
        this.questionCreator("3Who is the CEO of Microsoft?","Satya Nadella","John Collison","Drew Houston","Brian Chesky","Satya Nadella","“Satya Nadella. Satya Narayana Nadella (born 19 August 1967) is an American business executive of Indian descent. He is the Chief Executive Officer (CEO) of Microsoft, succeeding Steve Ballmer in 2014”","d_t_99.webp",2,3);
//Found
        this.questionCreator("4ISDN stands for?","Integrated Services Digital Networks","Integrated Service Drive Networks","Income Services Drive Networks","Integrated Success Digital Networks","Integrated Services Digital Networks","“ISDN stands for Integrated Services Digital Network. It is a design for a completely digital telephone/telecommunications network. It is designed to carry voice, data, images, and video, everything you could ever need.”","d_t_100.webp",2,3);
    }

    public void insertAllQuestions()
    {
        /*ANIMALS*/
        this.addAnimalsEasy();
        this.addAnimalsModerate();
        this.addAnimalsDifficult();

        /*PLANTS*/
        this.addPlantsEasy();
        this.addPlantsModerate();
        this.addPlantsDifficult();

        /*ENTERTAINMENT*/
        this.addEntertainmentEasy();
        this.addEntertainmentModerate();
        this.addEntertainmentDifficult();

        /*GEOGRAPHY*/
        this.addGeographyEasy();
        this.addGeographyModerate();
        this.addGeographyDifficult();

        /*MUSIC*/
        this.addMusicEasy();
        this.addMusicModerate();
        this.addMusicDifficult();

        /*PEOPLE*/
        this.addPeopleEasy();
        this.addPeopleModerate();
        this.addPeopleDifficult();

        /*SPORTS*/
        this.addSportsEasy();
        this.addSportsModerate();
        this.addSportsDifficult();

        /*TECHNOLOGY*/
        this.addTechnologyEasy();
        this.addTechnologyModerate();
        this.addTechnologyDifficult();
    }



}
