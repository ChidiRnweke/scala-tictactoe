import resolve from '@rollup/plugin-node-resolve';
import terser from '@rollup/plugin-terser';
import alias from '@rollup/plugin-alias';
import path from 'path';

export default {
    input: 'js/target/scala-3.3.0/tictactoe-opt/main.js',
    output: {
        file: 'dist/scala-tictactoe.js',
        format: 'es',
        sourcemap: true
    },
    plugins: [
        alias({
            entries: [
                { find: 'scala-tictactoe', replacement: path.resolve(process.cwd(), './') }
            ]
        }),
        resolve(),
        terser()
    ]
};
