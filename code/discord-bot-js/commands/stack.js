const { MessageActionRow, MessageEmbed, MessageSelectMenu } = require("discord.js");
const { SlashCommandBuilder } = require('@discordjs/builders');
const axios = require("axios");

module.exports = {
    data: new SlashCommandBuilder()
        .setName("stack")
        .setDescription("Replies with suggested stackoverflow threads.")
        .addStringOption(option =>
          option.setName("query")
                .setDescription("The query sent to StackOverflow")
                .setRequired(true)),

    async execute(inter) {
        const query = inter.options.getString("query", true)
        const encodedQuery = encodeURIComponent(query);
        let options = [];
        let reply = "";

        await axios
            .get(`https://api.stackexchange.com/2.3/search/advanced?order=desc&sort=relevance&q=${encodedQuery}&answers=3&site=stackoverflow`)
            .then(res => {
                console.log(`\nStackOverflow request made... ${res.data.quota_remaining} requests remaining.\n`);
                res.data.items.slice(-10).forEach((item, indx) => {
                    const date_asked = new Date(item.creation_date).toLocaleDateString();
                    const date_edited = new Date(item.last_edit_date).toLocaleDateString();

                    if (item.title.length > 100)
                      item.title = item.title.substr(0, 95) + "...";

                    options.push({
                        label: item.title,
                        description: `Accepted: ${item.is_answered ? "Yes" : "No"}. Asked: ${date_asked}. Edited: ${date_edited}.`,
                        value: `${item.question_id}`,
                    });

                });

                if (!Array.isArray(options) || !options.length)
                    throw "Illformatted Query";

                reply = `StackOverflow results for: \`${query}\``;
                const row = new MessageActionRow()
                            .addComponents(
                                new MessageSelectMenu()
                                    .setCustomId("select")
                                    .setPlaceholder("Select a result")
                                    .addOptions(options),
                );
                inter.reply({ content: reply, components: [row] });
            })
            .catch(err => {
                console.log(err)
                inter.reply({ content: "Query to StackOverflow failed." });
            });

    },
};
