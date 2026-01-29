const { SlashCommandBuilder } = require('@discordjs/builders');

module.exports = {
    data: new SlashCommandBuilder()
        .setName('user')
        .setDescription('Replies with user info!'),
    async execute(inter) {
        await inter.reply(
            `User: ${inter.user.tag}\n` +
             `Id: ${inter.user.id}`
        );
    },
};
