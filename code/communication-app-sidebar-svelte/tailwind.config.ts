import type { Config } from 'tailwindcss';

export default {
	mode: 'jit',
	content: ['./src/**/*.{html,js,svelte,ts}'],

	theme: {
		extend: {}
	},

	plugins: []
} satisfies Config;
