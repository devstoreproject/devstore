module.exports = {
  moduleNameMapper: {
    '^utils/(.*)$': '<rootDir>/src/utils/$1',
    '^api$': '<rootDir>/src/api',
  },
  transform: {
    '^.+\\.ts?$': 'ts-jest',
  },
};
