/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./src/**/*.{js,jsx,ts,tsx}'],
  theme: {
    extend: {
      padding: {
        95: '23.75rem',
        104: '26rem',
        112: '28rem',
        120: '30rem',
        128: '32rem',
        136: '34rem',
        175: '43.75rem',
        200: '50rem',
        250: '62.5rem',
      },
      lineHeight: {
        12: '3rem',
      },
      colors: {
        'neon-green': '#00FF00',
        'light-gray': '#F7F8FA',
        'light-black': '#333333',
        'tab-gray': '#e8e8e8',
        'subtitle-gray': '#777',
        'label-gray': '#333',
      },
      width: {
        25: '6.25rem',
        26: '6.5rem',
        95: '23.75rem',
        104: '26rem',
        112: '28rem',
        120: '30rem',
        128: '32rem',
        136: '34rem',
        175: '43.75rem',
        200: '50rem',
        250: '62.5rem',
        365: '91.25rem',
        1460: '1460px',
      },
      maxWidth: {
        365: '91.25rem',
        1460: '1460px',
      },
      height: {
        100: '25rem',
        104: '26rem',
        112: '28rem',
        120: '30rem',
        128: '32rem',
        136: '34rem',
      },
      boxShadow: {
        signBox: '0px 3px 8px rgba(0, 0, 0, 0.24)',
        btn: 'rgba(0, 0, 0, 0.07) 0px 1px 1px, rgba(0, 0, 0, 0.07) 0px 2px 2px, rgba(0, 0, 0, 0.07) 0px 4px 4px, rgba(0, 0, 0, 0.07) 0px 8px 8px, rgba(0, 0, 0, 0.07) 0px 16px 16px;',
        contentsBox: '5px 10px 8px rgba(0, 0, 0, 0.3)',
      },
      transitionProperty: {
        width: 'width',
        padding: 'padding',
      },
    },
  },
  plugins: [require('tailwind-scrollbar-hide')],
};
