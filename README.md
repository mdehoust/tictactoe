# tictactoe

This is a toy project for my own amusement. Some of it is test-driven, some of it is slapped together impatiently with very little discipline.

Try these commands.

```sh
# Compile and package
./mvnw package

# Play the game using a Swing-based user interface
./mvnw exec:java -Pgui

# Play the game in your terminal
./mvnw exec:java -Pterm

# Run a simulation pitting all the player types against each other and dump out some stats
./mvnw exec:java -Pstats
```

> If you need to install Java, I recommend [sdkman](https://sdkman.io). 
> ```sh
> # Install sdkman
> curl -s "https://get.sdkman.io" | bash
> # Use sdkman to install the latest stable Java
> sdk install java
> ```