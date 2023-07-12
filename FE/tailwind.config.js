/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ['./src/**/*.{js,jsx,ts,tsx}'],
  theme: {
    screens: {
      sm: { min: '320px', max: '768px' },
      md: { min: '769px', max: '1023px' },
      lg: { min: '1024px', max: '1980px' },
    },
    extend: {
      backgroundImage: {
        'best-item-image':
          "url('https://images.unsplash.com/photo-1608377205700-249f4b48b180?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=3540&q=80')",
        'theme-item-image':
          "url('https://images.unsplash.com/photo-1644778080339-e023d2f7d063?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=3379&q=80')",
        'introduce-image':
          "url('https://images.unsplash.com/photo-1522071820081-009f0129c71c?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=3540&q=80')",
      },
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
        'box-black': '#474747',
        'tag-gray': '#d9d9d9',
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
        300: '75rem',
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
        125: '31.25rem',
        128: '32rem',
        136: '34rem',
        150: '37.5rem',
      },
      boxShadow: {
        signBox: '0px 3px 8px rgba(0, 0, 0, 0.24)',
        btn: 'rgba(0, 0, 0, 0.07) 0px 1px 1px, rgba(0, 0, 0, 0.07) 0px 2px 2px, rgba(0, 0, 0, 0.07) 0px 4px 4px, rgba(0, 0, 0, 0.07) 0px 8px 8px, rgba(0, 0, 0, 0.07) 0px 16px 16px;',
        contentsBox: '5px 10px 8px rgba(0, 0, 0, 0.3)',
        contentsBoxYminus: '5px -3px 8px rgba(0, 0, 0, 0.05)',
        border: '0 0 0 1px rgba(209, 213, 219, 1)',
      },
      transitionProperty: {
        width: 'width',
        padding: 'padding',
      },
    },
  },
  plugins: [require('tailwind-scrollbar-hide')],
};