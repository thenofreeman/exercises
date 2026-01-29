const { Client, Collection, Intents } = require("discord.js");
const fs = require("node:fs");
const path = require("node:path");
const config = require("dotenv").config();

const client = new Client({ intents: [Intents.FLAGS.GUILDS] });
client.commands = new Collection();

const commandsPath = path.join(__dirname, "commands");
const commandFiles = fs.readdirSync(commandsPath).filter(file =>file.endsWith(".js"));

for (const file of commandFiles) {
  const filePath = path.join(commandsPath, file);
  const cmd = require(filePath);

  client.commands.set(cmd.data.name, cmd);
}

client.once("ready", () => {
  console.log("Ready!");
});

client.on("interactionCreate", async inter => {
  if (!inter.isCommand()) return;

  const cmd = client.commands.get(inter.commandName);

  if (!cmd) return;

  try {
    await cmd.execute(inter);
  } catch (err) {
    console.log(err);
    await inter.reply({content: "There was an error while executing this command!", ephemeral: true });
  }
});

client.on("interactionCreate", async inter => {
  if (!inter.isSelectMenu()) return;

  if (inter.customId === "select") {
    await inter.reply({ content: `https://stackoverflow.com/questions/${inter.values[0]}`, components: [] });
  }

});

client.login(process.env.TOKEN);
