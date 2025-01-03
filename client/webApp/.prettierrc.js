export default {
  trailingComma: 'es5',
  semi: true,
  singleQuote: true,
  plugins: ['prettier-plugin-astro', 'prettier-plugin-tailwind'],
  overrides: [
    {
      files: '*.astro',
      options: {
        parser: 'astro',
      },
    },
  ],
};
