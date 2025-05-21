// vitest.config.js
import { defineConfig } from 'vitest/config';
import react from '@vitejs/plugin-react';

export default defineConfig({
  plugins: [react()],
  test: {
    globals: true,
    environment: 'jsdom',         // <- gør DOM-API tilgængelig
    setupFiles: './src/setupTests.js',
  },
});

