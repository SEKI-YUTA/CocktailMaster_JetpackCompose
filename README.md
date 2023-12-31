## API サーバーの用意

プロダクトフレーバーを"emulator"に設定した場合は、[ここから](https://github.com/SEKI-YUTA/Cocktail_API_New)API 側のプログラムをダウンロードして用意してください
プロダクトフレーバーを"physical"に設定した場合は、別途用意する必要はありません。

| トップ画面                                               | 素材追加画面　                                                | 作れるカクテル一覧画面                                            |
| -------------------------------------------------------- | ------------------------------------------------------------- | ----------------------------------------------------------------- |
| <img src="readme/top_screen.png" alt="トップ画面"></img> | <img src="readme/ingredient_list.png" alt="トップ画面"></img> | <img src="readme/craftable_cocktails.png" alt="トップ画面"></img> |
| 現在持っている材料があればリスト形式で表示されます。     | 新しく持っている材料に追加できます。                          | 作れるカクテルの一覧が表示されます。                              |

> **注意**
> 設定画面は将来的に実装予定であり、現在はボタンを押しても何も起きません。

## アプリの使い方

素材追加画面で、既に持っている材料を選択して追加します。

| 番号 | 説明 | 画像 |
| --- | --- | --- |
|1.材料の追加 | 素材をタップするとダイアログが表示されるので補足情報(例えば銘柄など）をがあれば入力します。 | <img src="readme/add_ingredient.png" alt="トップ画面" width="300"></img> |
| 2.作れるカクテルの検索を実行 | 材料を追加するのが終わるとトップ画面に戻り、左上にある「カクテル一覧へ」ボタンを押します。 | <img src="readme/ingredient_list_added.png" alt="トップ画面" width="300"></img> |
| 3.作れるカクテルを確認 | 2 でボタンを押すと、作れるカクテル一覧画面へ遷移します。ここに表示されているのが現在作る事が出来るカクテルにになります。 | <img src="readme/craftable_cocktail_responsed.png" alt="トップ画面" width="300"></img> |

### 更新予定
- カクテルの情報の追加
- カクテルから材料を検索する機能の追加
- 作れるカクテルを選択した際に、作り方を表示する

## GraphQL参考サイト
- https://www.apollographql.com/docs/kotlin/v2/
  スキーマダウンロードののタスクを実行するときにオプションの値を'で囲って=の間をなくしたらいけた
  ./gradlew :app:downloadApolloSchema --endpoint='http://localhost:4000/graphql' --schema='app/src/main/graphql/schema.graphqls'

##  レスポンシブのレイアウト
https://www.figma.com/file/75p0dretq0v8WzRBJ5I6Jo/CocktialMaster%E3%82%BF%E3%83%96%E3%83%AC%E3%83%83%E3%83%88%E3%83%AC%E3%82%A4%E3%82%A2%E3%82%A6%E3%83%88?type=design&node-id=1%3A11&mode=design&t=Y3NePASmh57cIIPb-1
