const { SlashCommandBuilder } = require('@discordjs/builders');

module.exports = {
    data: new SlashCommandBuilder()
        .setName('server')
        .setDescription('Replies with server info!'),
    async execute(inter) {
        await inter.reply(
          `Sever name: ${inter.guild.name}\n` +
          `Total members: ${inter.guild.memberCount}`
        );
    },
};
