package com.example.StudyApp.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class StudySessionDao_Impl implements StudySessionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<StudySessionEvent> __insertionAdapterOfStudySessionEvent;

  public StudySessionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfStudySessionEvent = new EntityInsertionAdapter<StudySessionEvent>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `study_session_events` (`id`,`timestamp`,`deckId`,`flashcardId`,`responseTimeMillis`,`isCorrect`,`latitude`,`longitude`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final StudySessionEvent entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getTimestamp());
        statement.bindLong(3, entity.getDeckId());
        statement.bindLong(4, entity.getFlashcardId());
        statement.bindLong(5, entity.getResponseTimeMillis());
        final int _tmp = entity.isCorrect() ? 1 : 0;
        statement.bindLong(6, _tmp);
        if (entity.getLatitude() == null) {
          statement.bindNull(7);
        } else {
          statement.bindDouble(7, entity.getLatitude());
        }
        if (entity.getLongitude() == null) {
          statement.bindNull(8);
        } else {
          statement.bindDouble(8, entity.getLongitude());
        }
      }
    };
  }

  @Override
  public Object insert(final StudySessionEvent event,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfStudySessionEvent.insert(event);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<StudySessionEvent>> getAllEvents() {
    final String _sql = "SELECT `study_session_events`.`id` AS `id`, `study_session_events`.`timestamp` AS `timestamp`, `study_session_events`.`deckId` AS `deckId`, `study_session_events`.`flashcardId` AS `flashcardId`, `study_session_events`.`responseTimeMillis` AS `responseTimeMillis`, `study_session_events`.`isCorrect` AS `isCorrect`, `study_session_events`.`latitude` AS `latitude`, `study_session_events`.`longitude` AS `longitude` FROM study_session_events ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"study_session_events"}, new Callable<List<StudySessionEvent>>() {
      @Override
      @NonNull
      public List<StudySessionEvent> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = 0;
          final int _cursorIndexOfTimestamp = 1;
          final int _cursorIndexOfDeckId = 2;
          final int _cursorIndexOfFlashcardId = 3;
          final int _cursorIndexOfResponseTimeMillis = 4;
          final int _cursorIndexOfIsCorrect = 5;
          final int _cursorIndexOfLatitude = 6;
          final int _cursorIndexOfLongitude = 7;
          final List<StudySessionEvent> _result = new ArrayList<StudySessionEvent>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final StudySessionEvent _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final long _tmpDeckId;
            _tmpDeckId = _cursor.getLong(_cursorIndexOfDeckId);
            final long _tmpFlashcardId;
            _tmpFlashcardId = _cursor.getLong(_cursorIndexOfFlashcardId);
            final long _tmpResponseTimeMillis;
            _tmpResponseTimeMillis = _cursor.getLong(_cursorIndexOfResponseTimeMillis);
            final boolean _tmpIsCorrect;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCorrect);
            _tmpIsCorrect = _tmp != 0;
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            _item = new StudySessionEvent(_tmpId,_tmpTimestamp,_tmpDeckId,_tmpFlashcardId,_tmpResponseTimeMillis,_tmpIsCorrect,_tmpLatitude,_tmpLongitude);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<StudySessionEvent>> getEventsForDeck(final long deckId) {
    final String _sql = "SELECT * FROM study_session_events WHERE deckId = ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, deckId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"study_session_events"}, new Callable<List<StudySessionEvent>>() {
      @Override
      @NonNull
      public List<StudySessionEvent> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfDeckId = CursorUtil.getColumnIndexOrThrow(_cursor, "deckId");
          final int _cursorIndexOfFlashcardId = CursorUtil.getColumnIndexOrThrow(_cursor, "flashcardId");
          final int _cursorIndexOfResponseTimeMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "responseTimeMillis");
          final int _cursorIndexOfIsCorrect = CursorUtil.getColumnIndexOrThrow(_cursor, "isCorrect");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final List<StudySessionEvent> _result = new ArrayList<StudySessionEvent>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final StudySessionEvent _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final long _tmpDeckId;
            _tmpDeckId = _cursor.getLong(_cursorIndexOfDeckId);
            final long _tmpFlashcardId;
            _tmpFlashcardId = _cursor.getLong(_cursorIndexOfFlashcardId);
            final long _tmpResponseTimeMillis;
            _tmpResponseTimeMillis = _cursor.getLong(_cursorIndexOfResponseTimeMillis);
            final boolean _tmpIsCorrect;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCorrect);
            _tmpIsCorrect = _tmp != 0;
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            _item = new StudySessionEvent(_tmpId,_tmpTimestamp,_tmpDeckId,_tmpFlashcardId,_tmpResponseTimeMillis,_tmpIsCorrect,_tmpLatitude,_tmpLongitude);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getTotalCorrectAnswers(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM study_session_events WHERE isCorrect = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getTotalWrongAnswers(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM study_session_events WHERE isCorrect = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAverageResponseTimeForCorrect(final Continuation<? super Double> $completion) {
    final String _sql = "SELECT AVG(responseTimeMillis) FROM study_session_events WHERE isCorrect = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Double>() {
      @Override
      @Nullable
      public Double call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Double _result;
          if (_cursor.moveToFirst()) {
            final Double _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getDouble(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getTotalDecksStudied(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(DISTINCT deckId) FROM study_session_events";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getTotalFlashcardsStudied(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(DISTINCT flashcardId) FROM study_session_events";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getTotalAnswers(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM study_session_events";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<StudySessionEvent>> getEventsInRange(final long startTime, final long endTime) {
    final String _sql = "SELECT * FROM study_session_events WHERE timestamp >= ? AND timestamp <= ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, startTime);
    _argIndex = 2;
    _statement.bindLong(_argIndex, endTime);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"study_session_events"}, new Callable<List<StudySessionEvent>>() {
      @Override
      @NonNull
      public List<StudySessionEvent> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfDeckId = CursorUtil.getColumnIndexOrThrow(_cursor, "deckId");
          final int _cursorIndexOfFlashcardId = CursorUtil.getColumnIndexOrThrow(_cursor, "flashcardId");
          final int _cursorIndexOfResponseTimeMillis = CursorUtil.getColumnIndexOrThrow(_cursor, "responseTimeMillis");
          final int _cursorIndexOfIsCorrect = CursorUtil.getColumnIndexOrThrow(_cursor, "isCorrect");
          final int _cursorIndexOfLatitude = CursorUtil.getColumnIndexOrThrow(_cursor, "latitude");
          final int _cursorIndexOfLongitude = CursorUtil.getColumnIndexOrThrow(_cursor, "longitude");
          final List<StudySessionEvent> _result = new ArrayList<StudySessionEvent>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final StudySessionEvent _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final long _tmpDeckId;
            _tmpDeckId = _cursor.getLong(_cursorIndexOfDeckId);
            final long _tmpFlashcardId;
            _tmpFlashcardId = _cursor.getLong(_cursorIndexOfFlashcardId);
            final long _tmpResponseTimeMillis;
            _tmpResponseTimeMillis = _cursor.getLong(_cursorIndexOfResponseTimeMillis);
            final boolean _tmpIsCorrect;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsCorrect);
            _tmpIsCorrect = _tmp != 0;
            final Double _tmpLatitude;
            if (_cursor.isNull(_cursorIndexOfLatitude)) {
              _tmpLatitude = null;
            } else {
              _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
            }
            final Double _tmpLongitude;
            if (_cursor.isNull(_cursorIndexOfLongitude)) {
              _tmpLongitude = null;
            } else {
              _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
            }
            _item = new StudySessionEvent(_tmpId,_tmpTimestamp,_tmpDeckId,_tmpFlashcardId,_tmpResponseTimeMillis,_tmpIsCorrect,_tmpLatitude,_tmpLongitude);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
