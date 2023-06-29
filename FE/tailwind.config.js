/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./src/**/*.{js,jsx,ts,tsx}'],
  theme: {
    extend: {
      lineHeight: {
        12: '3rem',
      },
      colors: {
        'neon-green': '#00FF00',
        'light-gray': '#F7F8FA',
      },
      width: {
        104: '26rem',
        112: '28rem',
        120: '30rem',
        128: '32rem',
        136: '34rem',
      },
      height: {
        104: '26rem',
        112: '28rem',
        120: '30rem',
        128: '32rem',
        136: '34rem',
      },
      boxShadow: {
        signBox: '0px 3px 8px rgba(0, 0, 0, 0.24)',
        btn: 'rgba(0, 0, 0, 0.07) 0px 1px 1px, rgba(0, 0, 0, 0.07) 0px 2px 2px, rgba(0, 0, 0, 0.07) 0px 4px 4px, rgba(0, 0, 0, 0.07) 0px 8px 8px, rgba(0, 0, 0, 0.07) 0px 16px 16px;',
      },
    },
  },
  plugins: [require('tailwind-scrollbar-hide')],
};
