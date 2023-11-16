For this project I decided to make the passanger and elevator a class.
the passangers all have names, thier current floor, thier destation floor, and 
if an elevator is already coming to them or not (so multiple elevators dont all
go to the same person). the elevators are slightly more complex and have a name,
thier current floor, destionation floor, a boolean for if they are in empty,
they also in action. Action could be 0 for stand by mode 1 or -1 for going up or 
down to drop off and 2 or -2 to go up or down to pick up. This is because if they
are going up to pick up they dont want to pick up passsangers. there are also 2
heaps with custom comparators  a passangers count so we dont go over and 
the max capacity which is set in main. in one Tick a passanger may appear and
thius part was a bit confusing but i understood it that in every tick elevator 
can unload and load, travel 5 times. 
