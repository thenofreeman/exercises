const { SlashCommandBuilder } = require('@discordjs/builders');

module.exports = {
    data: new SlashCommandBuilder()
        .setName('ping')
        .setDescription('Replies with Pong!'),
    async execute(inter) {
        const sent = await inter.reply({ content: "Pinging...", fetchReply: true });
        inter.editReply(`Pong! (${sent.createdTimestamp - inter.createdTimestamp}ms)`);
    },
};
