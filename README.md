
The game world contains a path composed of image tiles (see more details in this document) which forms a loop. The Character automatically moves clockwise from position to position through this path, starting from the Hero's Castle.
The game world contains buildings (see buildings table below), enemies (see enemies table below), gold , health potions , and the Character . You can see more information about gold and health potions in the items table below. Enemies will move around the path, and their method of doing so depends on the enemy type.
It is important to note that in this document, the Human Player and Character are distinct:

The Character refers to the main Character within the game which the Human Player wishes to help win the game, represented by a picture of a person . The Character completes many interactions such as moving around and fighting battles automatically, without input from the Human Player.
The Human Player refers to the user playing the game application. The Human Player wishes to win the game by helping the Character complete all goals, and is able to help the Character win the game by creating buildings, equipping items, purchasing and selling items, consuming health potions, and pausing the game (pausing makes it easier to drag and drop).

When the Character is attacked by an enemy, a battle is started involving nearby enemies and the Character, and either the Character will defeat all enemies within this battle and win rewards, which can consist of cards, experience, gold, and equipment. Alternatively, the Character will be killed and the Human Player loses the game, and the game ends. The battle is automatically played - the Human Player has no ability to perform any game interactions during a battle.
More precisely, when the Character moves within the battle radius of an enemy on the path (this differs by type of enemy), a battle will commence. Those enemies for which the Character is within their support radius (support radius is distinct from battle radius, and differs by type of enemy) will join the battle against the Character and its allies. Some enemies such as vampires have a larger support radius.

The Player wins the game by completing the set goals.

This program is written completely in Java with the frontend being done in JavaFX.
